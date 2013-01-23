from wousoapi import WousoClient

def get_instance(args):
    server, port = 'wouso-next.rosedu.org', 80
    if len(args) > 0:
        server = args[0]
        if len(args) > 1:
            port = int(args[1])
    return server, port


def run_new(args):
    server, port = get_instance(args)
    wc = WousoClient(server=server, port=port)

    wc.authorize()
    print "Access token: '%s'" % wc.access_token
    print wc.info()
    print wc.notifications()

def run_existing(args):
    string = args[0]
    server, port = get_instance(args[1:])
    wc = WousoClient(server=server, port=port)
    wc.set_token_from_string(string)

    print wc.info()
    print wc.notifications()


if __name__ == '__main__':
    import sys
    if len(sys.argv) >= 2:
        if sys.argv[1] == 'help':
            print 'Usage %s [token <string>] or <server> <port>' % sys.argv[0]
            sys.exit(0)
    if len(sys.argv) >= 3 and sys.argv[1] == 'token':
        run_existing(sys.argv[2:])
    else:
        run_new(sys.argv[1:])
