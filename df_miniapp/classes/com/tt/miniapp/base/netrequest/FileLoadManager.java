package com.tt.miniapp.base.netrequest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import com.a;
import com.bytedance.sandboxapp.protocol.service.d.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.net.download.AbstractDownloadListener;
import com.tt.miniapp.net.download.DownloadManager;
import com.tt.miniapp.net.download.UploadManager;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.q.d;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import okhttp3.ae;
import org.json.JSONObject;

public class FileLoadManager {
  SparseArray<a.d> downloadRequestList = new SparseArray();
  
  public AbortRequestHolder mAbortRequestHolder = new AbortRequestHolder();
  
  private String mFileDownloadDir = (new File(FileManager.inst().getTempDir(), "tma/downloadfile/")).getAbsolutePath();
  
  private FileLoadManager() {}
  
  private Map<String, String> generateDownloadRequestHeaderMap(a.d<String> paramd) {
    JSONObject jSONObject = paramd.c;
    String str = paramd.b;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (jSONObject != null) {
      Iterator iterator = jSONObject.keys();
    } else {
      paramd = null;
    } 
    if (paramd == null)
      return (Map)hashMap; 
    while (paramd.hasNext()) {
      String str1 = paramd.next();
      if (str1.equalsIgnoreCase("Referer")) {
        hashMap.put(str1, RequestInceptUtil.getRequestReferer());
        continue;
      } 
      if (str1.equalsIgnoreCase("Cookie")) {
        int i = (AppbrandApplication.getInst().getAppInfo()).innertype;
        boolean bool = true;
        if (i != 1)
          bool = false; 
        boolean bool1 = NetBus.isWhiteUrl(str);
        if (bool && bool1) {
          String str2 = HostProcessBridge.getLoginCookie();
          if (!TextUtils.isEmpty(str2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(jSONObject.optString(str1));
            stringBuilder.append("; ");
            stringBuilder.append(str2);
            hashMap.put(str1, stringBuilder.toString());
            continue;
          } 
        } 
      } else if (str1.equalsIgnoreCase("User-Agent")) {
        hashMap.put("User-Agent", ToolUtils.getCustomUA());
        continue;
      } 
      hashMap.put(str1, jSONObject.optString(str1));
    } 
    return (Map)hashMap;
  }
  
  public static FileLoadManager getInst() {
    return Holder.sInstance;
  }
  
  public void addDownloadRequest(a.d paramd, a.a parama) {
    if (paramd == null)
      return; 
    if (this.downloadRequestList.get(paramd.a) != null)
      return; 
    this.downloadRequestList.put(paramd.a, paramd);
    try {
      doDownload(paramd, parama);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_FileLoadManager", new Object[] { "doDownload", exception });
      return;
    } 
  }
  
  public void addUploadTask(final b.d requestTask, final b.a callback) {
    if (requestTask == null)
      return; 
    File file = new File(FileManager.inst().getRealFilePath(requestTask.d));
    boolean bool = FileManager.inst().canRead(file);
    HashMap<Object, Object> hashMap1 = null;
    if (!bool) {
      callbackFail(requestTask, callback, 1000, null);
      return;
    } 
    HashMap<Object, Object> hashMap3 = new HashMap<Object, Object>();
    if (requestTask.f != null)
      try {
        JSONObject jSONObject = requestTask.f;
        Iterator<String> iterator = jSONObject.keys();
        while (iterator.hasNext()) {
          String str = iterator.next();
          hashMap3.put(str, jSONObject.optString(str));
        } 
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_FileLoadManager", exception.getStackTrace());
      }  
    hashMap3.put(requestTask.e, file);
    if (requestTask.c != null) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      try {
        JSONObject jSONObject = requestTask.c;
        Iterator<String> iterator = jSONObject.keys();
        while (true) {
          hashMap1 = hashMap;
          if (iterator.hasNext()) {
            String str = iterator.next();
            hashMap.put(str, jSONObject.optString(str));
            continue;
          } 
          break;
        } 
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_FileLoadManager", exception.getStackTrace());
        hashMap1 = hashMap;
      } 
    } 
    HashMap<Object, Object> hashMap2 = hashMap1;
    if (requestTask.g) {
      a a1 = (a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class);
      hashMap2 = hashMap1;
      if (a1 != null) {
        Map<?, ?> map = a1.getRequestHeader();
        hashMap2 = hashMap1;
        if (hashMap1 == null)
          hashMap2 = new HashMap<Object, Object>(); 
        hashMap2.putAll(map);
      } 
    } 
    AppBrandLogger.d("tma_FileLoadManager", new Object[] { "upLoadFile ", "file ", file });
    UploadManager.upLoadFile(requestTask.b, hashMap2, hashMap3, new UploadManager.ReqProgressCallBack<String>() {
          public void onFail(int param1Int, Throwable param1Throwable) {
            FileLoadManager.this.callbackFail(requestTask, callback, param1Int, param1Throwable);
          }
          
          public void onProgress(long param1Long1, long param1Long2) {
            int i = (int)(100L * param1Long2 / param1Long1);
            AppBrandLogger.d("tma_FileLoadManager", new Object[] { "onProgress ", "total", Long.valueOf(param1Long1), "current", Long.valueOf(param1Long2) });
            if (callback != null) {
              b.c c = new b.c();
              c.a = requestTask.a;
              c.c = param1Long2;
              c.d = param1Long1;
              c.b = i;
              callback.a(c);
            } 
          }
          
          public void onSuccess(int param1Int, String param1String1, String param1String2) {
            AppBrandLogger.d("tma_FileLoadManager", new Object[] { "onSuccess ", param1String1 });
            if (callback != null) {
              b.b b = new b.b();
              b.b = requestTask.a;
              b.a = true;
              b.c = param1Int;
              b.d = param1String1;
              callback.a(b);
            } 
          }
        }requestTask.a);
  }
  
  public void callbackFail(b.d paramd, b.a parama, int paramInt, Throwable paramThrowable) {
    if (parama != null) {
      b.b b = new b.b();
      b.b = paramd.a;
      b.a = false;
      b.f = paramThrowable;
      if (paramInt != 1000) {
        if (paramInt != 1001) {
          b.e = "unknown error";
        } else {
          b.e = "abort";
        } 
      } else {
        b.e = "network error";
      } 
      parama.a(b);
    } 
  }
  
  public void cancelDownloadTask(int paramInt) {
    if ((a.d)this.downloadRequestList.get(paramInt) != null) {
      this.mAbortRequestHolder.cancelRequest(paramInt);
      this.downloadRequestList.delete(paramInt);
    } 
  }
  
  public void cancelUploadTask(int paramInt) {
    UploadManager.cancelUploadRequest(paramInt);
  }
  
  void doDownload(a.d paramd, final a.a callback) throws Exception {
    HashMap<Object, Object> hashMap;
    final int requestId = paramd.a;
    final String url = paramd.b;
    final String filePath = paramd.d;
    final String dir = this.mFileDownloadDir;
    final File fileDir = new File(str3);
    if (!file.exists())
      file.mkdirs(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.valueOf(System.currentTimeMillis()));
    stringBuilder.append(i);
    final String fileName = stringBuilder.toString();
    if (d.b()) {
      Map<String, String> map = generateDownloadRequestHeaderMap(paramd);
    } else {
      hashMap = new HashMap<Object, Object>();
    } 
    if (paramd.e) {
      a a1 = (a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class);
      if (a1 != null) {
        Map<?, ?> map = a1.getRequestHeader();
        if (map != null)
          hashMap.putAll(map); 
      } 
    } 
    DownloadManager.get().asyncDownload(str1, hashMap, str3, str4, (DownloadManager.OnDownloadListener)new AbstractDownloadListener() {
          public void onDownloadFailed(String param1String, Throwable param1Throwable) {
            if (callback != null) {
              a.b b = new a.b();
              b.b = requestId;
              b.a = false;
              b.f = param1String;
              b.g = param1Throwable;
              callback.a(b);
            } 
          }
          
          public void onDownloadSuccess(ae param1ae) {
            a.b b = new a.b();
            b.b = requestId;
            try {
              File file2 = new File(fileDir, fileName);
              long l = file2.length();
              if (FileManager.inst().isUserDir(filePath) && FileManager.inst().isUserDirOverLimit(l))
                return; 
              String str3 = "";
              String str4 = Uri.parse(url).getPath();
              String str2 = str3;
              if (!TextUtils.isEmpty(str4)) {
                int i = str4.lastIndexOf(".");
                str2 = str3;
                if (i > 0) {
                  str2 = str4.substring(i);
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(fileName);
                  stringBuilder.append(str2);
                  str2 = stringBuilder.toString();
                } 
              } 
              str3 = str2;
              if (TextUtils.isEmpty(str2)) {
                str4 = null;
                if (param1ae.f != null)
                  str4 = param1ae.f.a("Content-Type"); 
                str3 = str2;
                if (!TextUtils.isEmpty(str4)) {
                  str2 = str4.substring(str4.lastIndexOf("/") + 1);
                  str3 = a.a("%s.%s", new Object[] { this.val$fileName, str2 });
                } 
              } 
              File file1 = file2;
              if (!TextUtils.isEmpty(str3)) {
                file1 = new File(dir, str3);
                file2.renameTo(file1);
              } 
              String str1 = file1.getPath();
              b.a = true;
              return;
            } finally {
              param1ae = null;
              b.a = false;
              b.g = (Throwable)param1ae;
              callback.a(b);
            } 
          }
          
          public void onDownloading(int param1Int, long param1Long1, long param1Long2) {
            if (callback != null) {
              a.c c = new a.c();
              c.a = requestId;
              if (param1Int <= 100) {
                c.c = param1Long1;
                c.d = param1Long2;
                c.b = param1Int;
              } 
              callback.a(c);
            } 
          }
        }new DownloadManager.TaskInfo() {
          public void cancel() {
            FileLoadManager.this.mAbortRequestHolder.cancelRequest(requestId);
          }
          
          public boolean isCancel() {
            return FileLoadManager.this.mAbortRequestHolder.isRequestCancel(requestId);
          }
        });
  }
  
  static class Holder {
    static FileLoadManager sInstance = new FileLoadManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\netrequest\FileLoadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */