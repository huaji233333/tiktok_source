package com.facebook.react.devsupport;

import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import g.f;
import g.h;
import g.q;
import g.x;
import g.z;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.e;
import okhttp3.f;
import okhttp3.s;
import okhttp3.y;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleDownloader {
  private final BundleDeltaClient mBundleDeltaClient = new BundleDeltaClient();
  
  private final y mClient;
  
  public e mDownloadBundleFromURLCall;
  
  public BundleDownloader(y paramy) {
    this.mClient = paramy;
  }
  
  private static void populateBundleInfo(String paramString, s params, BundleInfo paramBundleInfo) {
    paramBundleInfo.mUrl = paramString;
    paramString = params.a("X-Metro-Files-Changed-Count");
    if (paramString != null)
      try {
        paramBundleInfo.mFilesChangedCount = Integer.parseInt(paramString);
        return;
      } catch (NumberFormatException numberFormatException) {
        paramBundleInfo.mFilesChangedCount = -2;
      }  
  }
  
  private static boolean storePlainJSInFile(h paramh, File paramFile) throws IOException {
    try {
      x x = q.a(paramFile);
    } finally {
      paramh = null;
    } 
    if (paramFile != null)
      paramFile.close(); 
    throw paramh;
  }
  
  public void downloadBundleFromURL(final DevBundleDownloadListener callback, final File outputFile, String paramString, final BundleInfo bundleInfo) {
    ac ac = (new ac.a()).a(this.mBundleDeltaClient.toDeltaUrl(paramString)).c();
    this.mDownloadBundleFromURLCall = (e)a.b(this.mClient.a(ac));
    this.mDownloadBundleFromURLCall.a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            if (BundleDownloader.this.mDownloadBundleFromURLCall == null || BundleDownloader.this.mDownloadBundleFromURLCall.d()) {
              BundleDownloader.this.mDownloadBundleFromURLCall = null;
              return;
            } 
            BundleDownloader.this.mDownloadBundleFromURLCall = null;
            DevBundleDownloadListener devBundleDownloadListener = callback;
            StringBuilder stringBuilder = new StringBuilder("URL: ");
            stringBuilder.append((param1e.a()).a.toString());
            devBundleDownloadListener.onFailure((Exception)DebugServerException.makeGeneric("Could not connect to development server.", stringBuilder.toString(), param1IOException));
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            if (BundleDownloader.this.mDownloadBundleFromURLCall == null || BundleDownloader.this.mDownloadBundleFromURLCall.d()) {
              BundleDownloader.this.mDownloadBundleFromURLCall = null;
              return;
            } 
            BundleDownloader.this.mDownloadBundleFromURLCall = null;
            String str1 = param1ae.a.a.toString();
            String str2 = param1ae.b("content-type");
            Matcher matcher = Pattern.compile("multipart/mixed;.*boundary=\"([^\"]+)\"").matcher(str2);
            try {
              if (matcher.find()) {
                BundleDownloader.this.processMultipartResponse(str1, param1ae, matcher.group(1), outputFile, bundleInfo, callback);
              } else {
                BundleDownloader.this.processBundleResult(str1, param1ae.c, param1ae.f, q.a((z)param1ae.g.source()), outputFile, bundleInfo, callback);
              } 
              return;
            } finally {
              str1 = null;
            } 
          }
        });
  }
  
  public void processBundleResult(String paramString, int paramInt, s params, h paramh, File paramFile, BundleInfo paramBundleInfo, DevBundleDownloadListener paramDevBundleDownloadListener) throws IOException {
    String str;
    StringBuilder stringBuilder3;
    boolean bool;
    if (paramInt != 200) {
      str = paramh.r();
      DebugServerException debugServerException = DebugServerException.parse(str);
      if (debugServerException != null) {
        paramDevBundleDownloadListener.onFailure((Exception)debugServerException);
        return;
      } 
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append("The development server returned response error code: ");
      stringBuilder3.append(paramInt);
      stringBuilder3.append("\n\nURL: ");
      stringBuilder3.append(paramString);
      stringBuilder3.append("\n\nBody:\n");
      stringBuilder3.append(str);
      paramDevBundleDownloadListener.onFailure((Exception)new DebugServerException(stringBuilder3.toString()));
      return;
    } 
    if (paramBundleInfo != null)
      populateBundleInfo(paramString, (s)str, paramBundleInfo); 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramFile.getPath());
    stringBuilder2.append(".tmp");
    File file = new File(stringBuilder2.toString());
    if (BundleDeltaClient.isDeltaUrl(paramString)) {
      bool = this.mBundleDeltaClient.storeDeltaInFile((h)stringBuilder3, file);
    } else {
      this.mBundleDeltaClient.reset();
      bool = storePlainJSInFile((h)stringBuilder3, file);
    } 
    if (!bool || file.renameTo(paramFile)) {
      paramDevBundleDownloadListener.onSuccess();
      return;
    } 
    StringBuilder stringBuilder1 = new StringBuilder("Couldn't rename ");
    stringBuilder1.append(file);
    stringBuilder1.append(" to ");
    stringBuilder1.append(paramFile);
    throw new IOException(stringBuilder1.toString());
  }
  
  public void processMultipartResponse(final String url, final ae response, String paramString2, final File outputFile, final BundleInfo bundleInfo, final DevBundleDownloadListener callback) throws IOException {
    if (!(new MultipartStreamReader(response.g.source(), paramString2)).readAllParts(new MultipartStreamReader.ChunkListener() {
          public void onChunkComplete(Map<String, String> param1Map, f param1f, boolean param1Boolean) throws IOException {
            if (param1Boolean) {
              int i = response.c;
              if (param1Map.containsKey("X-Http-Status"))
                i = Integer.parseInt(param1Map.get("X-Http-Status")); 
              BundleDownloader.this.processBundleResult(url, i, s.a(param1Map), (h)param1f, outputFile, bundleInfo, callback);
              return;
            } 
            if (param1Map.containsKey("Content-Type")) {
              if (!((String)param1Map.get("Content-Type")).equals("application/json"))
                return; 
              try {
                JSONObject jSONObject = new JSONObject(param1f.r());
                param1Boolean = jSONObject.has("status");
                Integer integer = null;
                if (param1Boolean) {
                  String str = jSONObject.getString("status");
                } else {
                  param1Map = null;
                } 
                if (jSONObject.has("done")) {
                  Integer integer1 = Integer.valueOf(jSONObject.getInt("done"));
                } else {
                  param1f = null;
                } 
                if (jSONObject.has("total"))
                  integer = Integer.valueOf(jSONObject.getInt("total")); 
                callback.onProgress((String)param1Map, (Integer)param1f, integer);
                return;
              } catch (JSONException jSONException) {
                StringBuilder stringBuilder = new StringBuilder("Error parsing progress JSON. ");
                stringBuilder.append(jSONException.toString());
                a.c("ReactNative", stringBuilder.toString());
              } 
            } 
          }
          
          public void onChunkProgress(Map<String, String> param1Map, long param1Long1, long param1Long2) throws IOException {
            if ("application/javascript".equals(param1Map.get("Content-Type")))
              callback.onProgress("Downloading JavaScript bundle", Integer.valueOf((int)(param1Long1 / 1024L)), Integer.valueOf((int)(param1Long2 / 1024L))); 
          }
        })) {
      StringBuilder stringBuilder = new StringBuilder("Error while reading multipart response.\n\nResponse code: ");
      stringBuilder.append(response.c);
      stringBuilder.append("\n\nURL: ");
      stringBuilder.append(url.toString());
      stringBuilder.append("\n\n");
      callback.onFailure((Exception)new DebugServerException(stringBuilder.toString()));
    } 
  }
  
  public static class BundleInfo {
    public int mFilesChangedCount;
    
    public String mUrl;
    
    public static BundleInfo fromJSONString(String param1String) {
      if (param1String == null)
        return null; 
      BundleInfo bundleInfo = new BundleInfo();
      try {
        JSONObject jSONObject = new JSONObject(param1String);
        bundleInfo.mUrl = jSONObject.getString("url");
        bundleInfo.mFilesChangedCount = jSONObject.getInt("filesChangedCount");
        return bundleInfo;
      } catch (JSONException jSONException) {
        return null;
      } 
    }
    
    public int getFilesChangedCount() {
      return this.mFilesChangedCount;
    }
    
    public String getUrl() {
      String str = this.mUrl;
      return (str != null) ? str : "unknown";
    }
    
    public String toJSONString() {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("url", this.mUrl);
        jSONObject.put("filesChangedCount", this.mFilesChangedCount);
        return jSONObject.toString();
      } catch (JSONException jSONException) {
        return null;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\BundleDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */