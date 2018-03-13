# React Assets Manager
An IDEA/WebStorm/Android Studio Plugin for React Assets Manager


## Get Started
The plug-in can automatically generate resources.js files from the res directory

* download [React Assets Manager Plugin](https://github.com/whoopschat/react-assets-manager/blob/master/react-assets-manager.jar?raw=true) and install

* click `tool menu` `React Assets Manager` open manager dialog
* input `Resources Folder` and `Export Resources File`
* click `confirm` Export Resources File

```
'use strict';

export default {
	assets: 	{
		watermark: require('../../res/assets/watermark.jpg')
	},
	configs: 	{
		config_android: require('../../res/configs/config.android.js'),
		config_base: require('../../res/configs/config.base.js'),
		config_dev: require('../../res/configs/config.dev.js'),
		config_ios: require('../../res/configs/config.ios.js'),
		config_prod: require('../../res/configs/config.prod.js')
	},
	locales: 	{
		en: require('../../res/locales/en.js'),
		zh: require('../../res/locales/zh.js')
	}
}
```

