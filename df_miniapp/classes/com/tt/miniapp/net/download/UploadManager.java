package com.tt.miniapp.net.download;

import android.util.Log;
import android.util.SparseArray;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.net.AppbrandCallback;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.process.HostProcessBridge;
import g.f;
import g.g;
import g.q;
import g.z;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.e;
import okhttp3.f;
import okhttp3.s;
import okhttp3.w;
import okhttp3.x;
import org.json.JSONObject;

public class UploadManager {
  private static SparseArray<SoftReference<e>> mUploadRequestList = new SparseArray();
  
  public static void cancelUploadRequest(int paramInt) {
    SparseArray<SoftReference<e>> sparseArray = mUploadRequestList;
    if (sparseArray != null && sparseArray.get(paramInt) != null) {
      e e = ((SoftReference<e>)mUploadRequestList.get(paramInt)).get();
      if (e != null)
        e.c(); 
    } 
  }
  
  public static <T> ad createProgressRequestBody(final w contentType, final File file, final ReqProgressCallBack<T> callBack, final int requestId) {
    return new ad() {
        public final long contentLength() {
          return file.length();
        }
        
        public final w contentType() {
          return contentType;
        }
        
        public final void writeTo(g param1g) throws IOException {
          try {
            z z = q.c(file);
            f f = new f();
            long l2 = contentLength();
            long l1 = 0L;
            while (true) {
              long l = z.read(f, 2048L);
              if (l != -1L) {
                param1g.a(f, l);
                l1 += l;
                UploadManager.progressCallBack(l2, l1, callBack);
                continue;
              } 
              break;
            } 
            return;
          } catch (Exception exception) {
            AppBrandLogger.e("tma_UploadManager", new Object[] { exception });
            UploadManager.failedCallBack(callBack, null, requestId, exception);
            return;
          } 
        }
      };
  }
  
  public static void failedCallBack(ReqProgressCallBack paramReqProgressCallBack, e parame, int paramInt, Throwable paramThrowable) {
    if (paramReqProgressCallBack != null) {
      if (parame == null || !parame.d()) {
        paramReqProgressCallBack.onFail(1000, paramThrowable);
      } else {
        paramReqProgressCallBack.onFail(1001, paramThrowable);
      } 
      SparseArray<SoftReference<e>> sparseArray = mUploadRequestList;
      if (sparseArray != null)
        sparseArray.remove(paramInt); 
    } 
  }
  
  private static void interceptHeader(ac.a parama, String paramString) {
    parama.b("User-Agent");
    parama.b("User-Agent", ToolUtils.getCustomUA());
    parama.b("Referer");
    parama.b("Referer", RequestInceptUtil.getRequestReferer());
    int i = (AppbrandApplication.getInst().getAppInfo()).innertype;
    boolean bool = true;
    if (i != 1)
      bool = false; 
    if (bool && NetBus.isWhiteUrl(paramString)) {
      paramString = HostProcessBridge.getLoginCookie();
      if (paramString != null)
        parama.b("Cookie", paramString); 
    } 
    parama.b("content-type");
    parama.b("content-type", "multipart/form-data; boundary=TMAMultipartBoundary");
    if ((DebugManager.getInst()).mRemoteDebugEnable)
      parama.b("remoteDebug", "upload"); 
  }
  
  public static <T> void progressCallBack(long paramLong1, long paramLong2, ReqProgressCallBack<T> paramReqProgressCallBack) {
    if (paramReqProgressCallBack != null)
      paramReqProgressCallBack.onProgress(paramLong1, paramLong2); 
  }
  
  private static void setupHeader(HashMap<String, String> paramHashMap, ac.a parama) {
    Set<Map.Entry<String, String>> set;
    if (paramHashMap != null) {
      set = paramHashMap.entrySet();
    } else {
      set = new HashSet();
    } 
    for (Map.Entry<String, String> entry : set)
      parama.b((String)entry.getKey(), (String)entry.getValue()); 
  }
  
  public static void successCallBack(int paramInt1, String paramString1, String paramString2, ReqProgressCallBack paramReqProgressCallBack, int paramInt2) {
    if (paramReqProgressCallBack != null)
      paramReqProgressCallBack.onSuccess(paramInt1, paramString1, paramString2); 
  }
  
  public static <T> void upLoadFile(String paramString, HashMap<String, String> paramHashMap, HashMap<String, Object> paramHashMap1, final ReqProgressCallBack<T> callBack, final int requestId) {
    try {
      long l;
      x.a a1 = new x.a();
      a1.a(x.e);
      ArrayList<x.b> arrayList = new ArrayList();
      for (String str : paramHashMap1.keySet()) {
        Object object = paramHashMap1.get(str);
        if (!(object instanceof File)) {
          a1.a(str, object.toString());
          continue;
        } 
        object = object;
        arrayList.add(x.b.a(str, object.getName(), createProgressRequestBody(w.a("application/octet-stream"), (File)object, callBack, requestId)));
      } 
      if (!arrayList.isEmpty()) {
        int i;
        for (i = 0; i < arrayList.size(); i++)
          a1.a(arrayList.get(i)); 
      } 
      x x = a1.a();
      ac.a a = (new ac.a()).a(paramString);
      setupHeader(paramHashMap, a);
      interceptHeader(a, paramString);
      ac ac = a.a((ad)x).c();
      AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
      if (appConfig != null) {
        l = (appConfig.getNetworkTimeout()).uploadFile;
      } else {
        l = 60000L;
      } 
      e e = NetBus.okHttpClient.c().a(l, TimeUnit.MILLISECONDS).b(l, TimeUnit.MILLISECONDS).c(l, TimeUnit.MILLISECONDS).a().a(ac);
      e.a((f)new AppbrandCallback() {
            public final String callbackFrom() {
              return "4005";
            }
            
            public final void onFailure(e param1e, IOException param1IOException) {
              AppBrandLogger.e("tma_UploadManager", new Object[] { "onFailure ", Log.getStackTraceString(param1IOException) });
              UploadManager.failedCallBack(callBack, param1e, requestId, param1IOException);
            }
            
            public final void onSuccess(e param1e, ae param1ae) throws IOException {
              af af = param1ae.g;
              int i = 0;
              if (af == null) {
                AppBrandLogger.e("tma_UploadManager", new Object[] { "response.body() == null" });
                UploadManager.failedCallBack(callBack, param1e, requestId, null);
                return;
              } 
              String str = param1ae.g.string();
              s s = param1ae.f;
              JSONObject jSONObject = new JSONObject();
              if (s != null)
                try {
                  if (s.a() > 0) {
                    int j = s.a();
                    while (i < j) {
                      jSONObject.put(s.a(i), s.b(i));
                      i++;
                    } 
                  } 
                } catch (Exception exception) {
                  AppBrandLogger.stacktrace(6, "tma_UploadManager", exception.getStackTrace());
                }  
              UploadManager.successCallBack(param1ae.c, str, jSONObject.toString(), callBack, requestId);
            }
          });
      mUploadRequestList.put(requestId, new SoftReference<e>(e));
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_UploadManager", new Object[] { exception });
      failedCallBack(callBack, null, requestId, exception);
      return;
    } 
  }
  
  public static interface ReqProgressCallBack<T> {
    void onFail(int param1Int, Throwable param1Throwable);
    
    void onProgress(long param1Long1, long param1Long2);
    
    void onSuccess(int param1Int, String param1String1, String param1String2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\download\UploadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */