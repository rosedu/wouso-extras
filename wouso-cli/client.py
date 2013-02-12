import cmd
import inspect
import logging
import os
import sys
import ConfigParser
import argparse
from pprint import pprint
from wousoapi import WousoClient

CONFIG_FILE = '/etc/wouso.ini'

def get_config():
    if not os.path.exists(CONFIG_FILE):
        return None
    config = ConfigParser.RawConfigParser()
    config.read(CONFIG_FILE)
    return config


def get_instance(args):
    """
    Return instance configuration, with defaults.
    Args can be:
        - a list of: server port path
        - a string like: server:port/path
    """
    https = False
    server, port, path = 'wouso-next.rosedu.org', 80, ''
    if len(args) == 1:
        if args[0].startswith('http'):
            if args[0].startswith('https'):
                https = True
                port = 443
                args[0] = args[0][8:]
            else:
                args[0] = args[0][7:]
        if ':' in args[0]:
            newargs = args[0].split(':')
            if '/' in newargs[1]:
                newargs = [newargs[0]] + newargs[1].split('/')
            args = newargs
        else:
            if '/' in args[0]:
                a = args[0].split('/')
                args = [a[0], port, a[1]]

    if len(args) > 0:
        server = args[0]
        if len(args) > 1:
            port = int(args[1])
            if len(args) > 2:
                path = args[2]
                if len(args) > 3:
                    https = args[3].lower() == 'https'
    return https, server, port, path

def get_info(wc):
    info = wc.info()
    print "Logged in as: ", info['first_name'], info['last_name'], '(%s)' % info.get('username', '')
    print "Level: ", info['level_no'], "Race:", info['race'], "Group:", info['group'], "Points:", info['points']


def get_client_from_config(options):
    server = options.get('server', 'wouso-next.rosedu.org')
    port = options.get('port', None)
    path = options.get('path', '')
    access_token = options.get('access_token', None)
    https = options.get('https', 'False').lower() == 'true'
    wc = WousoClient(server=server, port=port, path=path, access_token=access_token, https=https)
    return wc


def get_client_from_args(token, args):
    https, server, port, path = get_instance(args)
    wc = WousoClient(server=server, port=port, access_token=token, path=path, https=https)
    return wc


def run_new(args):
    https, server, port, path = get_instance(args)
    wc = WousoClient(server=server, port=port, path=path, https=https)

    wc.authorize()
    print "Access token: '%s'" % wc.access_token
    print wc.info()


class WousoShell(cmd.Cmd):
    """
    An interactive shell for wouso API
    """
    def __init__(self, client):
        cmd.Cmd.__init__(self)
        self.client = client
        info = self.client.info()
        self.prompt = '%s@%s: ' % (info.get('username'), info.get('instance'))
        # Populate shell with api calls
        methods = [m for m in dir(self.client) if not m.startswith('_') and callable(getattr(self.client, m))]
        for m in methods:
            setattr(WousoShell, 'do_%s' % m, self._make_handler(m))

    def _make_handler(self, name):
        original_func = getattr(self.client, name)
        def _handler(self, line):
            info = inspect.getargspec(original_func)
            def api_call(*args, **kwargs):
                try:
                    if len(info[0]) == 1:
                        result = original_func()
                    else:
                        result = original_func(*args, **kwargs)
                except TypeError as e:
                    logging.exception(e)
                else:
                    pprint(result)
                return False
            args = line.split(' ')
            return api_call(*args)
        _handler.__doc__ = original_func.__doc__
        return _handler

    def do_EOF(self, line):
        print "\n"
        return True


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Wouso API client')
    parser.add_argument('--config', help='Use configuration from file')
    parser.add_argument('--show_config', action='store_true')
    parser.add_argument('--authorize', nargs='+', help='Attempt OAuth authorization. Must provide server, port and path, such as: localhost:8000/2013')
    parser.add_argument('--token', help='Provide a token string')
    parser.add_argument('--show_info', action='store_true')
    parser.add_argument('--shell', action='store_true', help='Run an interactive shell of the API')
    parser.add_argument('legacy', nargs='*')
    args = parser.parse_args()
    config = get_config()
    client = None

    if args.show_config:
        if not config:
            print "No config file found at %s" % CONFIG_FILE
            sys.exit(-1)
        else:
            print "Available configurations:"
            for s in config.sections():
                print " ", s
        sys.exit(0)

    if args.authorize:
        run_new(args.authorize)
        sys.exit(0)

    if args.config:
        if not config.has_section(args.config):
            print "No such configuration: %s" % args.config
            sys.exit(-2)
        else:
            options = config.options(args.config)
            options = dict([(o, config.get(args.config, o)) for o in options])
            client = get_client_from_config(options)

    if args.token:
        client = get_client_from_args(args.token, args.legacy)

    if client is None:
        parser.print_help()
    else:
        if args.show_info:
            get_info(client)
        elif args.shell:
            shell = WousoShell(client)
            shell.cmdloop()

    sys.exit(0)