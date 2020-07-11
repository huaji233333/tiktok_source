package com.tt.miniapp.msg.onUserCaptureScreen;

import android.app.Application;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import com.bytedance.v.a.a.a.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class TakeScreenshotManager {
  static final String[] CAMERA_SNAPSHOT_KEYWORDS;
  
  static final String[] KEYWORDS = new String[] { 
      "screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", 
      "screen-cap", "screen cap", "截屏" };
  
  static final String[] MEDIA_PROJECTIONS;
  
  private Application mApplication;
  
  private ContentObserver mExternalObserver;
  
  private ContentObserver mInternalObserver;
  
  private volatile boolean mIsRegister;
  
  private TakeScreenshotObserver mObserver;
  
  static {
    CAMERA_SNAPSHOT_KEYWORDS = new String[] { "tmp.png" };
    MEDIA_PROJECTIONS = new String[] { "_data", "datetaken" };
  }
  
  public TakeScreenshotManager(TakeScreenshotObserver paramTakeScreenshotObserver) {
    this.mObserver = paramTakeScreenshotObserver;
    this.mApplication = AppbrandContext.getInst().getApplicationContext();
  }
  
  private static void com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_registerContentObserver(ContentResolver paramContentResolver, Uri paramUri, boolean paramBoolean, ContentObserver paramContentObserver) {
    paramContentResolver.registerContentObserver(paramUri, paramBoolean, paramContentObserver);
    b.a(null, paramContentResolver, new Object[] { paramUri, Boolean.valueOf(paramBoolean), paramContentObserver }, false, 100600, "android.content.ContentResolver.registerContentObserver(android.net.Uri,boolean,android.database.ContentObserver)");
  }
  
  private static void com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_unregisterContentObserver(ContentResolver paramContentResolver, ContentObserver paramContentObserver) {
    paramContentResolver.unregisterContentObserver(paramContentObserver);
    b.a(null, paramContentResolver, new Object[] { paramContentObserver }, false, 100601, "android.content.ContentResolver.unregisterContentObserver(android.database.ContentObserver)");
  }
  
  public void registerTakeScreenshotObserver() {
    boolean bool1 = this.mIsRegister;
    boolean bool = false;
    if (bool1) {
      AppBrandLogger.d("TakeScreenshotManager", new Object[] { "observers is register" });
      return;
    } 
    if (this.mInternalObserver == null)
      this.mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, this.mObserver); 
    if (this.mExternalObserver == null)
      this.mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.mObserver); 
    if (Build.VERSION.SDK_INT > 28)
      bool = true; 
    com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_registerContentObserver(this.mApplication.getContentResolver(), MediaStore.Images.Media.INTERNAL_CONTENT_URI, bool, this.mInternalObserver);
    com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_registerContentObserver(this.mApplication.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bool, this.mExternalObserver);
    this.mIsRegister = true;
  }
  
  public void unregisterTakeScreenshotObserver() {
    if (!this.mIsRegister) {
      AppBrandLogger.d("TakeScreenshotManager", new Object[] { "observers is unregister" });
      return;
    } 
    if (this.mInternalObserver != null)
      com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_unregisterContentObserver(this.mApplication.getContentResolver(), this.mInternalObserver); 
    if (this.mExternalObserver != null)
      com_tt_miniapp_msg_onUserCaptureScreen_TakeScreenshotManager_android_content_ContentResolver_unregisterContentObserver(this.mApplication.getContentResolver(), this.mExternalObserver); 
    this.mInternalObserver = null;
    this.mExternalObserver = null;
    this.mIsRegister = false;
  }
  
  static interface TakeScreenshotObserver {
    void onUserCaptureScreen(String param1String, long param1Long);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\onUserCaptureScreen\TakeScreenshotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */