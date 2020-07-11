package com.bytedance.ies.bullet.kit.rn.pkg.viewshot;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class RNViewShotModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  
  public RNViewShotModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.reactContext = paramReactApplicationContext;
  }
  
  private File createTempFile(Context paramContext, String paramString) throws IOException {
    File file1 = paramContext.getExternalCacheDir();
    File file2 = paramContext.getCacheDir();
    if (file1 != null || file2 != null) {
      StringBuilder stringBuilder1;
      File file = file2;
      if (file1 != null) {
        StringBuilder stringBuilder;
        if (file2 == null) {
          file = file1;
          stringBuilder = new StringBuilder(".");
          stringBuilder.append(paramString);
          return File.createTempFile("ReactNative-snapshot-image", stringBuilder.toString(), file);
        } 
        file = file2;
        if (stringBuilder.getFreeSpace() > file2.getFreeSpace()) {
          stringBuilder1 = stringBuilder;
          stringBuilder = new StringBuilder(".");
          stringBuilder.append(paramString);
          return File.createTempFile("ReactNative-snapshot-image", stringBuilder.toString(), (File)stringBuilder1);
        } 
      } 
      StringBuilder stringBuilder2 = new StringBuilder(".");
      stringBuilder2.append(paramString);
      return File.createTempFile("ReactNative-snapshot-image", stringBuilder2.toString(), (File)stringBuilder1);
    } 
    throw new IOException("No cache directory available");
  }
  
  @ReactMethod
  public void captureRef(int paramInt, ReadableMap paramReadableMap, Promise paramPromise) {
    boolean bool;
    boolean bool1;
    Integer integer;
    String str2;
    DisplayMetrics displayMetrics = getReactApplicationContext().getResources().getDisplayMetrics();
    String str1 = paramReadableMap.getString("format");
    if ("jpg".equals(str1)) {
      bool = false;
    } else if ("webm".equals(str1)) {
      bool = true;
    } else if ("raw".equals(str1)) {
      bool = true;
    } else {
      bool = true;
    } 
    double d = paramReadableMap.getDouble("quality");
    if (paramReadableMap.hasKey("width")) {
    
    } else {
    
    } 
  }
  
  @ReactMethod
  public void captureScreen(ReadableMap paramReadableMap, Promise paramPromise) {
    captureRef(-1, paramReadableMap, paramPromise);
  }
  
  public Map<String, Object> getConstants() {
    return Collections.emptyMap();
  }
  
  public String getName() {
    return "RNViewShot";
  }
  
  public void onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy();
    (new a((ReactContext)getReactApplicationContext())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  @ReactMethod
  public void releaseCapture(String paramString) {
    paramString = Uri.parse(paramString).getPath();
    if (paramString == null)
      return; 
    File file1 = new File(paramString);
    if (!file1.exists())
      return; 
    File file2 = file1.getParentFile();
    if (file2.equals(this.reactContext.getExternalCacheDir()) || file2.equals(this.reactContext.getCacheDir()))
      file1.delete(); 
  }
  
  static final class a extends GuardedAsyncTask<Void, Void> implements FilenameFilter {
    private final File a;
    
    private final File b;
    
    private a(ReactContext param1ReactContext) {
      super(param1ReactContext);
      this.a = param1ReactContext.getCacheDir();
      this.b = param1ReactContext.getExternalCacheDir();
    }
    
    private void a(File param1File) {
      File[] arrayOfFile = param1File.listFiles(this);
      if (arrayOfFile != null) {
        int j = arrayOfFile.length;
        for (int i = 0; i < j; i++)
          arrayOfFile[i].delete(); 
      } 
    }
    
    public final boolean accept(File param1File, String param1String) {
      return param1String.startsWith("ReactNative-snapshot-image");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\viewshot\RNViewShotModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */