## TikTok source code

Reverse engineered source code to the android tiktok app.

### Background

This project is a bit different from my other projects. TikTok is a data collection engine disguised as a social media platform. It's legitimate spyware, so I thought I would reverse engineer the Android application

Included are the compiled classes.jar and classes.dex. You can find all of the source in the classes/ folder in each part of the app.

The main functionality of the app is in df_miniapp. The rest is just included for the sake of completeness. Most of it is useless. 

China, I'll see you when you send the hitmen to my house.

Here is the full reverse engineered source code. Enjoy


**NOTE** : this is where I got the APK. https://apkpure.com/tiktok/com.ss.android.ugc.trill

### Interesting Code

###### Location tracking: 
- [TMALocation.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/maplocate/TMALocation.java)
- [ILocation.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/maplocate/ILocator.java)

##### Phone calls:
- [PhoneCallImpl.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/call/PhoneCallImpl.java)

##### Screenshot code:
- [TakeScreenshotManager.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/msg/onUserCaptureScreen/TakeScreenshotManager.java)
tiktok_source/df_miniapp/classes/com/tt/miniapp/msg/onUserCaptureScreen/TakeScreenshotManager.java

Wierd List in the screenshot code (this is nothing particularly special)
```
  static final String[] KEYWORDS = new String[] { 
      "screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", 
      "screen-cap", "screen cap", "截屏" };
```

###### Get Wifi networks:
- [ApiGetWifiListCtrl.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/msg/wifi/ApiGetWifiListCtrl.java)

###### Facial recognition:
- [FacialVerifyProtocolActivity.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/facialverify/FacialVerifyProtocolActivity.java)

### Update 9/11/2020

###### Sketchy Address code
- [LoadAddressTask.java](https://github.com/augustgl/tiktok_source/blob/master/df_miniapp/classes/com/tt/miniapp/address/LoadAddressTask.java)


### TODO LIST
- Decode all android manifests

## Update 11/03/2020
I apologize for confusion. This is not leaked source code. This is reverse engineered. Thank you for your time.

### Done by
- [augustgl](https://github.com/augustgl)
- [quantumcore](https://github.com/quantumcore)
