package com.facebook.react.modules.network;

import android.os.Build;
import com.facebook.common.e.a;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.ah;
import okhttp3.k;
import okhttp3.y;

public class OkHttpClientProvider {
  private static y sClient;
  
  private static OkHttpClientFactory sFactory;
  
  public static y createClient() {
    OkHttpClientFactory okHttpClientFactory = sFactory;
    return (okHttpClientFactory != null) ? okHttpClientFactory.createNewNetworkModuleClient() : createClientBuilder().a();
  }
  
  public static y.a createClientBuilder() {
    return enableTls12OnPreLollipop((new y.a()).a(0L, TimeUnit.MILLISECONDS).b(0L, TimeUnit.MILLISECONDS).c(0L, TimeUnit.MILLISECONDS).a(new ReactCookieJarContainer()));
  }
  
  public static y.a enableTls12OnPreLollipop(y.a parama) {
    if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19)
      try {
        parama.a(new TLSSocketFactory());
        k k = (new k.a(k.a)).a(new ah[] { ah.TLS_1_2 }).a();
        ArrayList<k> arrayList = new ArrayList();
        arrayList.add(k);
        arrayList.add(k.b);
        arrayList.add(k.c);
        parama.b(arrayList);
        return parama;
      } catch (Exception exception) {
        a.c("OkHttpClientProvider", "Error while enabling TLS 1.2", exception);
      }  
    return parama;
  }
  
  public static y getOkHttpClient() {
    if (sClient == null)
      sClient = createClient(); 
    return sClient;
  }
  
  public static void replaceOkHttpClient(y paramy) {
    sClient = paramy;
  }
  
  public static void setOkHttpClientFactory(OkHttpClientFactory paramOkHttpClientFactory) {
    sFactory = paramOkHttpClientFactory;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\OkHttpClientProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */