package com.tt.miniapphost.liveplayer.network;

import com.ss.f.a.c;
import d.f;
import d.f.a.a;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.y;
import org.json.JSONException;
import org.json.JSONObject;

public final class LivePlayerNetworkClient implements c {
  private final f mHttpClient$delegate = g.a(LivePlayerNetworkClient$mHttpClient$2.INSTANCE);
  
  private final y getMHttpClient() {
    return (y)this.mHttpClient$delegate.getValue();
  }
  
  public final c.a doRequest(String paramString1, String paramString2) {
    String str2 = null;
    String str1 = null;
    try {
      ac ac2;
      ac ac3;
      ac.a a2 = new ac.a();
      if (paramString1 == null)
        l.a(); 
      ac.a a1 = a2.a(paramString1);
      if (paramString2 == null)
        l.a(); 
      ac ac1 = a1.b("host", paramString2).c();
      ae ae = getMHttpClient().a(ac1).b();
      l.a(ae, "rsp");
      if (ae.a()) {
        af af = ae.g;
        if (af != null) {
          String str = af.string();
        } else {
          af = null;
        } 
        paramString2 = str1;
        try {
          str1 = ae.f.toString();
          paramString2 = str1;
          JSONObject jSONObject2 = new JSONObject((String)af);
          JSONObject jSONObject1 = jSONObject2;
          af af1 = af;
        } catch (JSONException jSONException) {}
      } else {
        ac1 = null;
        ac3 = ac1;
        ac2 = ac1;
      } 
      c.a a = c.a.a().a((JSONObject)ac2).a((String)ac3).a();
      l.a(a, "INetworkClient.Result.ne…se).setBody(body).build()");
      return a;
    } catch (IOException iOException) {
      c.a a = c.a.a().a(iOException).a();
      l.a(a, "INetworkClient.Result.ne…).setException(e).build()");
      return a;
    } catch (JSONException jSONException) {
      paramString2 = null;
      paramString1 = str2;
      c.a a = c.a.a().a(paramString1).b(paramString2).a((Exception)jSONException).a();
      l.a(a, "INetworkClient.Result.ne…).setException(e).build()");
      return a;
    } catch (Exception exception) {
      c.a a = c.a.a().a(exception).a();
      l.a(a, "INetworkClient.Result.ne…).setException(e).build()");
      return a;
    } 
  }
  
  static final class LivePlayerNetworkClient$mHttpClient$2 extends m implements a<y> {
    public static final LivePlayerNetworkClient$mHttpClient$2 INSTANCE = new LivePlayerNetworkClient$mHttpClient$2();
    
    LivePlayerNetworkClient$mHttpClient$2() {
      super(0);
    }
    
    public final y invoke() {
      return (new y()).c().a(10L, TimeUnit.SECONDS).b(10L, TimeUnit.SECONDS).c(10L, TimeUnit.SECONDS).a();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\liveplayer\network\LivePlayerNetworkClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */