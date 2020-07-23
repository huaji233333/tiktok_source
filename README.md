# TikTok source code

Full source code to the android tiktok app.

# background

This project is a bit different from my other projects. TikTok is a data collection engine disguised as a social media platform. It's legitimate spyware, so I thought I would reverse engineer the Android application

Included are the compiled classes.jar and classes.dex. You can find all of the source in the classes/ folder in each part of the app.

The main functionality of the app is in df_miniapp. The rest is just included for the sake of completeness. Most of it is useless. 

China, I'll see you when you send the hitmen to my house.

Here is the full reverse engineered source code. Enjoy


[NOTE] this is where I got the APK. https://apkpure.com/tiktok/com.ss.android.ugc.trill

# interesting code

###### location tracking: 
tiktok_source/df_miniapp/classes/com/tt/miniapp/maplocate/TMALocation.java
tiktok_source/df_miniapp/classes/com/tt/miniapp/maplocate/ILocator.java

##### phone calls:
tiktok_source/df_miniapp/classes/com/tt/miniapp/call/PhoneCallImpl.java

##### screenshot code:
tiktok_source/df_miniapp/classes/com/tt/miniapp/msg/onUserCaptureScreen/TakeScreenshotManager.java

weird list in the screenshot code
```
  static final String[] KEYWORDS = new String[] { 
      "screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", 
      "screen-cap", "screen cap", "截屏" };
```

###### get wifi networks:
tiktok_source/df_miniapp/classes/com/tt/miniapp/msg/wifi/ApiGetWifiListCtrl.java

###### facial recognition:
tiktok_source/df_miniapp/classes/com/tt/miniapp/facialverify/FacialVerifyProtocolActivity.java 



# TODO LIST

decode all android manifests
