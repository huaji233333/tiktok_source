package com.facebook.react.bridge;

import android.app.Application;
import android.content.res.AssetManager;
import java.lang.ref.WeakReference;

public class RNJavaScriptRuntime {
  private static String mCommonURL;
  
  private static WeakReference<Application> mGloablAppcation;
  
  private static String mSnapshotURL = "assets://blobdata";
  
  public static AssetManager getAssetManager() {
    try {
      return ((Application)mGloablAppcation.get()).getAssets();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public static String getBaseJsURL() {
    return "assets://base.bundle";
  }
  
  public static String getCommonURL() {
    return mCommonURL;
  }
  
  public static String getSnapshotURL() {
    return mSnapshotURL;
  }
  
  public static void setApplication(Application paramApplication) {
    mGloablAppcation = new WeakReference<Application>(paramApplication);
    setCommonJsBundle("assets://common.bundle");
  }
  
  public static void setCommonJsBundle(String paramString) {
    String str = mCommonURL;
    if (str == null || !str.equals(paramString))
      mCommonURL = paramString; 
  }
  
  public static void setSnapSHotBundle(String paramString) {
    String str = mSnapshotURL;
    if (str == null || !str.equals(paramString))
      mSnapshotURL = paramString; 
  }
  
  public enum SplitCommonType {
    NONE, SPLIT_COMMONJS, SPLIT_SNAPSHOT;
    
    static {
    
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\RNJavaScriptRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */