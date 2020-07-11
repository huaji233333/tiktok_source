package com.tt.miniapp.impl;

import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.net.download.AbstractDownloadListener;
import com.tt.miniapp.net.download.DownloadManager;
import com.tt.miniapp.settings.net.RequestService;
import com.tt.miniapp.settings.net.RequestServiceImpl;
import com.tt.option.q.c;
import com.tt.option.q.f;
import com.tt.option.q.g;
import com.tt.option.q.h;
import com.tt.option.q.i;
import com.tt.option.q.j;
import com.tt.option.q.k;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.s;
import okhttp3.w;
import okhttp3.x;
import okhttp3.y;

public class HostOptionNetDependImpl implements c {
  private y getOkHttpClient(i parami) {
    y y2 = NetBus.okHttpClient;
    y y1 = y2;
    if (parami != null)
      y1 = y2.c().b(parami.l, TimeUnit.MILLISECONDS).c(parami.k, TimeUnit.MILLISECONDS).a(parami.j, TimeUnit.MILLISECONDS).a(); 
    return y1;
  }
  
  private void initRequestHeaders(Map<String, String> paramMap, ac.a parama) {
    if (paramMap != null && !paramMap.isEmpty())
      for (Map.Entry<String, String> entry : paramMap.entrySet()) {
        parama.b((String)entry.getKey());
        parama.b((String)entry.getKey(), (String)entry.getValue());
      }  
  }
  
  public i convertMetaRequest(i parami) {
    return parami;
  }
  
  public RequestService createSettingsResponseService() {
    return (RequestService)new RequestServiceImpl();
  }
  
  public k createWsClient(k.a parama) {
    return null;
  }
  
  public j doGet(i parami) throws Exception {
    y y = getOkHttpClient(parami);
    ac.a a = new ac.a();
    initRequestHeaders(parami.f, a);
    a.a(parami.f());
    ae ae = y.a(a.c()).b();
    j j = new j();
    if (ae != null) {
      j.b = ae.c;
      j.c = ae.d;
      if (ae.g != null)
        j.d = ae.g.string(); 
    } 
    return j;
  }
  
  public j doPostBody(i parami) throws Exception {
    y y = getOkHttpClient(parami);
    ac.a a = new ac.a();
    initRequestHeaders(parami.f, a);
    a.a(ad.create(w.a("application/json"), parami.d()));
    a.a(parami.f());
    ae ae = y.a(a.c()).b();
    j j = new j();
    if (ae != null) {
      j.b = ae.c;
      j.c = ae.d;
      if (ae.g != null)
        j.d = ae.g.string(); 
    } 
    return j;
  }
  
  public j doPostUrlEncoded(i parami) throws Exception {
    y y = getOkHttpClient(parami);
    ac.a a = new ac.a();
    initRequestHeaders(parami.f, a);
    w w = w.a("application/x-www-form-urlencoded");
    StringBuilder stringBuilder = new StringBuilder();
    int j = parami.g.entrySet().size() - 1;
    for (Map.Entry entry : parami.g.entrySet()) {
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append(URLEncoder.encode(String.valueOf(entry.getValue())));
      if (j > 0)
        stringBuilder.append("&"); 
      j--;
    } 
    a.a(ad.create(w, stringBuilder.toString()));
    a.a(parami.f());
    ae ae = y.a(a.c()).b();
    j j1 = new j();
    if (ae != null) {
      j1.b = ae.c;
      j1.c = ae.d;
      if (ae.g != null)
        j1.d = ae.g.string(); 
    } 
    return j1;
  }
  
  public j doRequest(i parami) throws Exception {
    y y = getOkHttpClient(parami);
    ac.a a = new ac.a();
    initRequestHeaders(parami.f, a);
    byte[] arrayOfByte2 = parami.i;
    int j = 0;
    byte[] arrayOfByte1 = arrayOfByte2;
    if (arrayOfByte2 == null)
      arrayOfByte1 = new byte[0]; 
    ad ad = ad.create(w.a(parami.e()), arrayOfByte1);
    if (!"GET".equals(parami.c))
      if ("POST".equals(parami.c)) {
        a.a(ad);
      } else if ("PUT".equals(parami.c)) {
        a.a("PUT", ad);
      } else if ("DELETE".equals(parami.c)) {
        a.a("DELETE", ad);
      } else {
        a.a(parami.c, ad);
      }  
    a.a(parami.f());
    ae ae = y.a(a.c()).b();
    j j1 = new j();
    if (ae != null) {
      j1.b = ae.c;
      s s = ae.f;
      if (s != null) {
        ArrayList<h> arrayList = j1.b();
        int k = s.a();
        while (j < k) {
          arrayList.add(new h(s.a(j), s.b(j)));
          j++;
        } 
      } 
      j1.c = ae.d;
      if (ae.g != null)
        j1.e = ae.g.bytes(); 
    } 
    return j1;
  }
  
  public g downloadFile(f paramf, final c.a iDownloadListener) {
    File file = new File(paramf.a);
    if (!file.exists())
      file.mkdirs(); 
    final g tmaResponse = new g();
    g.a = DownloadManager.get().syncDownload(paramf.f(), ((i)paramf).f, paramf.a, paramf.b, (DownloadManager.OnDownloadListener)new AbstractDownloadListener() {
          public void onDownloadFailed(String param1String, Throwable param1Throwable) {
            g g1 = tmaResponse;
            ((j)g1).c = param1String;
            ((j)g1).f = param1Throwable;
            iDownloadListener.downloadFailed(param1String, param1Throwable);
          }
          
          public void onDownloadSuccess(ae param1ae) {
            if (param1ae != null) {
              ((j)tmaResponse).b = param1ae.c;
              ((j)tmaResponse).c = param1ae.d;
            } 
            iDownloadListener.updateProgress(100, -1L, -1L);
            iDownloadListener.downloadSuccess(param1ae);
          }
          
          public void onDownloading(int param1Int, long param1Long1, long param1Long2) {
            iDownloadListener.updateProgress(param1Int, param1Long1, param1Long2);
          }
        }null);
    return g;
  }
  
  public j postMultiPart(i parami) throws Exception {
    y y = getOkHttpClient(parami);
    ac.a a = new ac.a();
    initRequestHeaders(parami.f, a);
    x.a a1 = (new x.a()).a(x.e);
    for (Map.Entry entry : parami.c().entrySet())
      a1.a((String)entry.getKey(), entry.getValue().toString()); 
    for (Map.Entry entry : parami.h.entrySet()) {
      i.b b = (i.b)entry.getValue();
      ad ad = ad.create(w.a(b.b), b.a);
      a1.a((String)entry.getKey(), b.a.getName(), ad);
    } 
    a.a((ad)a1.a());
    a.a(parami.f());
    ae ae = y.a(a.c()).b();
    j j = new j();
    if (ae != null) {
      j.b = ae.c;
      j.c = ae.d;
      if (ae.g != null)
        j.d = ae.g.string(); 
    } 
    return j;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionNetDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */