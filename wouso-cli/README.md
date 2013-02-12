# WoUSO Python API and client

## About

WoUSO has an RESTful API with OAuth v1.0 authentication as documented here: [wouso.rosedu.org/api](http://wouso.rosedu.org/api/).

This module contains a Python 2.7 wrapper (using `httplib` and `oauth` from google) over the API and also a command line
 interface sporting an interactive shell.

## Usage

### Without configuration

First you must authenticate yourself against an wouso instance, and receive an access token you will use for every call.
You can do this against a test instance like this:

    python client.py --authorize localhost:8000

It will open a browser window, requesting authentication and giving a verifier. Paste this verifier back in the terminal
 window and keep the token string, such as `'oauth_token=xxxx...'`.

Run the shell using:

    python client.py --token 'oauth_token=xxxx...' --shell


### With configuration

Configure in `/etc/wouso.ini` the `server`, `port`, `path`, `https` and `access_token` parameters, in a section of your
choice. Example configuration file:

    [localhost]
    server = localhost
    port = 8000
    access_token = 'oauth_token=xxxx...'

Run the shell using:

    python client.py --config localhost --shell

