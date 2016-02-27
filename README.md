# wouso-extras

The content in this repo is licensed CC-BY-SA.

Find more about wouso at http://wouso.rosedu.org.

## Installation instructions

Go to `wouso/wouso/resources/static/themes` subfolder and make a symbolic link:

```
    ln -s [TARGET] [LINK_NAME]
```

Where TARGET is the file which contains the theme you want to install 
and LINK_NAME is the name of the link.
Themes can be found under `wouso-extras/themes` subfolder.
Try to use full paths for both TARGET and LINK_NAME.


## Wouso HTML5 App

Must be run with `--disable-web-security` option. Should register the `web+wouso` protocol handler.
