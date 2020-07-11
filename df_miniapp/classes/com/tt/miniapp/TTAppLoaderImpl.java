package com.tt.miniapp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.he.Monitor;
import com.he.loader.Loader;
import com.he.loader.Resolver;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.net.AppbrandCallback;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.e;
import okhttp3.f;
import okhttp3.y;
import org.json.JSONException;
import org.json.JSONObject;

public class TTAppLoaderImpl implements Monitor.Impl, Loader {
  private static final y okhttpClient = new y();
  
  private void report(String paramString, int paramInt, JSONObject paramJSONObject) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("event", paramString);
      jSONObject.put("status", paramInt);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    AppBrandMonitor.event("mp_he_report", jSONObject, null, paramJSONObject);
  }
  
  public void load(String paramString, final Resolver resolver) {
    try {
      File file = new File(FileManager.inst().getRealFilePath(paramString));
      final String canonicalPath = file.getCanonicalPath();
      if (StreamLoader.findFile(str) != null) {
        if (StreamLoader.findFile(str) != null) {
          ThreadUtil.runOnWorkThread(new Action() {
                public void act() {
                  byte[] arrayOfByte2 = StreamLoader.loadByteFromStream(canonicalPath);
                  byte[] arrayOfByte1 = arrayOfByte2;
                  if (arrayOfByte2 == null)
                    arrayOfByte1 = new byte[0]; 
                  resolver.resolve(arrayOfByte1, 0, arrayOfByte1.length);
                }
              }(Scheduler)LaunchThreadPool.getInst());
          return;
        } 
        AppBrandLogger.d("tma_TTAppLoaderImpl", new Object[] { "file ", paramString, " doesn't loaded, wait for resolve" });
        return;
      } 
      AppBrandLogger.d("tma_TTAppLoaderImpl", new Object[] { "loadFile from file ", paramString });
      if (file.exists() && file.isFile() && FileManager.inst().canRead(file)) {
        resolver.resolve(file);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("cannot read file ");
      stringBuilder.append(paramString);
      throw new IOException(stringBuilder.toString());
    } catch (IOException iOException) {
      AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { Log.getStackTraceString(iOException) });
      resolver.reject(iOException);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { Log.getStackTraceString(exception) });
      return;
    } 
  }
  
  public Uri loadMedia(String paramString) {
    return !TextUtils.isEmpty(paramString) ? ((paramString.startsWith("http://") || paramString.startsWith("https://")) ? Uri.parse(paramString) : Uri.fromFile(new File(StreamLoader.waitExtractFinishIfNeeded(FileManager.inst().getRealFilePath(paramString))))) : null;
  }
  
  public byte[] loadSync(String paramString) {
    try {
      File file = new File(FileManager.inst().getRealFilePath(paramString));
      String str = file.getCanonicalPath();
      if (StreamLoader.findFile(str) != null) {
        if (StreamLoader.findFile(str) != null)
          return StreamLoader.loadByteFromStream(str); 
        AppBrandLogger.d("tma_TTAppLoaderImpl", new Object[] { "file ", paramString, " doesn't loaded, wait for resolve" });
      } else {
        RandomAccessFile randomAccessFile;
        AppBrandLogger.d("tma_TTAppLoaderImpl", new Object[] { "loadFile from file ", paramString });
        if (file.exists() && file.isFile() && FileManager.inst().canRead(file)) {
          randomAccessFile = new RandomAccessFile(file, "r");
          byte[] arrayOfByte = new byte[(int)randomAccessFile.length()];
          randomAccessFile.readFully(arrayOfByte);
          randomAccessFile.close();
          return arrayOfByte;
        } 
        StringBuilder stringBuilder = new StringBuilder("cannot read file ");
        stringBuilder.append((String)randomAccessFile);
        AppBrandLogger.d("tma_TTAppLoaderImpl", new Object[] { stringBuilder.toString() });
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { Log.getStackTraceString(exception) });
    } 
    return null;
  }
  
  public void loadUrl(String paramString, final Resolver resolver) {
    StringBuilder stringBuilder;
    if (paramString.startsWith("ttfile://")) {
      load(paramString, resolver);
      return;
    } 
    try {
      ac ac = (new ac.a()).a(paramString).c();
    } catch (RuntimeException runtimeException) {
      AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { runtimeException });
      runtimeException = null;
    } 
    if (runtimeException == null) {
      if (resolver != null) {
        stringBuilder = new StringBuilder("unexpected url: ");
        stringBuilder.append(paramString);
        resolver.reject(new IOException(stringBuilder.toString()));
      } 
      return;
    } 
    okhttpClient.a((ac)stringBuilder).a((f)new AppbrandCallback() {
          public String callbackFrom() {
            return "4004";
          }
          
          public void onFailure(e param1e, IOException param1IOException) {
            AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { Log.getStackTraceString(param1IOException) });
            resolver.reject(param1IOException);
          }
          
          public void onSuccess(e param1e, ae param1ae) throws IOException {
            if (param1ae.g != null) {
              byte[] arrayOfByte = param1ae.g.bytes();
              resolver.resolve(arrayOfByte, 0, arrayOfByte.length);
              return;
            } 
            AppBrandLogger.e("tma_TTAppLoaderImpl", new Object[] { "response.body() == null" });
          }
        });
  }
  
  public void onAsyncCompile(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 != 0) {
      if (paramInt2 == 0)
        return; 
      JSONObject jSONObject = new JSONObject();
      double d2 = paramInt2;
      Double.isNaN(d2);
      double d1 = d2 / 1000.0D;
      try {
        jSONObject.put("duration", d1);
        d1 = paramInt1;
        Double.isNaN(d1);
        double d = d1 / 1.024D;
        Double.isNaN(d2);
        d2 = d / d2;
        jSONObject.put("speed", d2);
        d2 = paramInt3;
        Double.isNaN(d2);
        Double.isNaN(d1);
        d1 = d2 / d1;
        jSONObject.put("inflation", d1);
      } catch (JSONException jSONException) {}
      AppBrandMonitor.event("mp_js_async_compile", null, jSONObject, null);
    } 
  }
  
  public void onAurumInitFail(int paramInt1, int paramInt2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("result", paramInt2);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    report("aurum_init", paramInt1, jSONObject);
  }
  
  public void onCameraOpenFail(int paramInt1, int paramInt2, Throwable paramThrowable) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("id", paramInt1);
      jSONObject.put("retries", paramInt2);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    report("camera_open", 1, jSONObject);
    StringBuilder stringBuilder = new StringBuilder("com.he.Monitor.onCameraOpenFail:");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" retries:");
    stringBuilder.append(paramInt2);
    AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", stringBuilder.toString(), paramThrowable);
  }
  
  public void onHeliumAddViewFail() {
    report("addview", 1, null);
  }
  
  public void onHeliumSetupFail() {
    report("setup", 1, null);
  }
  
  public void onLoadEffectFail(Throwable paramThrowable) {
    report("load_effect", 1, null);
    AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "com.he.Monitor.onLoadEffectFail", paramThrowable);
  }
  
  public void onRTCLogReport(String paramString1, String paramString2) {
    try {
      AppBrandMonitor.log(paramString1, new JSONObject(paramString2));
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public void onSmashModelDownloadFail(String paramString, int paramInt, long paramLong, Throwable paramThrowable) {
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    try {
      jSONObject1.put("name", paramString);
      jSONObject1.put("status", paramInt);
      jSONObject2.put("duration", paramLong);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    AppBrandMonitor.event("mp_smash_download", jSONObject1, jSONObject2, null);
    StringBuilder stringBuilder = new StringBuilder("com.he.Monitor.onSmashDownLoadFail:");
    stringBuilder.append(paramString);
    stringBuilder.append(" status:");
    stringBuilder.append(paramInt);
    AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", stringBuilder.toString(), paramThrowable);
  }
  
  public void onSmashModelDownloadSuccess(String paramString, long paramLong) {
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    try {
      jSONObject1.put("name", paramString);
      jSONObject1.put("status", 0);
      jSONObject2.put("duration", paramLong);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    AppBrandMonitor.event("mp_smash_download", jSONObject1, jSONObject2, null);
  }
  
  public void onSmashModelMapFail(String paramString, Throwable paramThrowable) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("name", paramString);
      jSONObject.put("status", 4);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", "unknown json exception", (Throwable)jSONException);
    } 
    AppBrandMonitor.event("mp_smash_download", jSONObject, null, null);
    StringBuilder stringBuilder = new StringBuilder("com.he.Monitor.onSmashModelMapFail:");
    stringBuilder.append(paramString);
    AppBrandLogger.eWithThrowable("tma_TTAppLoaderImpl", stringBuilder.toString(), paramThrowable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\TTAppLoaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */