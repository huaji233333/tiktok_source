package com.tt.miniapp.manager;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Pair;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.e;
import okhttp3.f;
import org.json.JSONArray;

public class PreConnectCDNManager {
  private static volatile PreConnectCDNManager instance;
  
  private final List<Pair<String, Long>> mPreConnectedUrls = new ArrayList<Pair<String, Long>>();
  
  private void doPreConnect(final String url) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "StreamLoaderUtils_preConnect: ", url });
    ac ac = (new ac.a()).a(url).b().c();
    final long startTime = SystemClock.elapsedRealtime();
    NetBus.okHttpClient.a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            PreConnectCDNManager.this.onUrlPreConnected(url, 2147483647L);
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            if (param1ae.a()) {
              long l = SystemClock.elapsedRealtime();
              PreConnectCDNManager.this.onUrlPreConnected(url, l - startTime);
            } else {
              PreConnectCDNManager.this.onUrlPreConnected(url, 2147483647L);
            } 
            try {
              af af = param1ae.g;
              if (af != null)
                af.close(); 
              return;
            } catch (Exception exception) {
              return;
            } 
          }
        });
  }
  
  public static PreConnectCDNManager inst() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/manager/PreConnectCDNManager.instance : Lcom/tt/miniapp/manager/PreConnectCDNManager;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapp/manager/PreConnectCDNManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/manager/PreConnectCDNManager.instance : Lcom/tt/miniapp/manager/PreConnectCDNManager;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapp/manager/PreConnectCDNManager
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapp/manager/PreConnectCDNManager.instance : Lcom/tt/miniapp/manager/PreConnectCDNManager;
    //   25: ldc com/tt/miniapp/manager/PreConnectCDNManager
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapp/manager/PreConnectCDNManager
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/tt/miniapp/manager/PreConnectCDNManager.instance : Lcom/tt/miniapp/manager/PreConnectCDNManager;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public void onUrlPreConnected(String paramString, long paramLong) {
    null = new Pair(Uri.parse(paramString).getHost(), Long.valueOf(paramLong));
    synchronized (this.mPreConnectedUrls) {
      this.mPreConnectedUrls.add(null);
      return;
    } 
  }
  
  public void preConnectCDN() {
    try {
      Application application = AppbrandContext.getInst().getApplicationContext();
      Settings settings = Settings.BDP_TTPKG_CONFIG;
      int i = 0;
      JSONArray jSONArray = SettingsDAO.getJSONObject((Context)application, new Enum[] { (Enum)settings }).optJSONArray("urls");
      if (jSONArray != null) {
        int j = jSONArray.length();
        if (j != 0)
          while (i < j) {
            doPreConnect(jSONArray.getString(i));
            i++;
          }  
      } else {
        AppBrandLogger.i("PreConnectCDNManager", new Object[] { "preConnectCDN urls is null" });
        return;
      } 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("PreConnectCDNManager", "PreConnectCDNSettings parse error", exception);
    } 
  }
  
  public void sortUrls(List<String> paramList) {
    int i;
    if (paramList == null)
      return; 
    synchronized (this.mPreConnectedUrls) {
      Collections.sort(this.mPreConnectedUrls, new Comparator<Pair<String, Long>>() {
            public int compare(Pair<String, Long> param1Pair1, Pair<String, Long> param1Pair2) {
              return (int)(((Long)param1Pair1.second).longValue() - ((Long)param1Pair2.second).longValue());
            }
          });
      int k = this.mPreConnectedUrls.size();
      String[] arrayOfString1 = new String[k];
      String[] arrayOfString2 = new String[paramList.size()];
      Iterator<String> iterator = paramList.iterator();
      byte b = 0;
      int j = 0;
      while (true) {
        String str;
        if (iterator.hasNext()) {
          str = iterator.next();
          i = 0;
          while (true) {
            if (i < k) {
              if (str.contains((CharSequence)((Pair)this.mPreConnectedUrls.get(i)).first)) {
                arrayOfString1[i] = str;
                i = 1;
                break;
              } 
              i++;
              continue;
            } 
            i = 0;
            break;
          } 
        } else {
          paramList.clear();
          for (i = 0;; i++) {
            int m = b;
            if (i < k) {
              String str1 = arrayOfString1[i];
              if (str1 != null)
                paramList.add(str1); 
            } else {
              while (m < j) {
                paramList.add(arrayOfString2[m]);
                m++;
              } 
              return;
            } 
          } 
        } 
        if (i == 0) {
          arrayOfString2[j] = str;
          j++;
        } 
      } 
    } 
    i++;
    continue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\PreConnectCDNManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */