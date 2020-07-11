package com.bytedance.sandboxapp.c.a.b.f;

import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.a.d;
import com.bytedance.sandboxapp.a.a.c.e;
import com.bytedance.sandboxapp.c.a.a.f;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.request.entity.b;
import d.f.b.l;
import org.json.JSONObject;

public final class c extends e {
  public c(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(e.b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    ApiCallbackData apiCallbackData;
    String str1;
    l.b(paramb, "paramParser");
    l.b(parama, "apiInvokeInfo");
    boolean bool1 = TextUtils.equals(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, "createInnerUploadTask");
    String str2 = paramb.b;
    l.a(str2, "paramParser.url");
    if (!bool1 && !TextUtils.isEmpty(str2)) {
      com.bytedance.sandboxapp.protocol.service.l.a a2 = (com.bytedance.sandboxapp.protocol.service.l.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.l.a.class);
      String str = paramb.b;
      l.a(str, "paramParser.url");
      if (!a2.isSafeDomain("upload", str)) {
        apiCallbackData = a(paramb.b);
        l.a(apiCallbackData, "buildInvalidDomain(paramParser.url)");
        return apiCallbackData;
      } 
    } 
    if (bool1) {
      str1 = "onInnerUploadTaskStateChange";
    } else {
      str1 = "onUploadTaskStateChange";
    } 
    int i = ((com.bytedance.sandboxapp.protocol.service.i.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.i.a.class)).getRequestIdentifyId();
    com.bytedance.sandboxapp.protocol.service.request.a a1 = (com.bytedance.sandboxapp.protocol.service.request.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.request.a.class);
    JSONObject jSONObject1 = ((e.b)apiCallbackData).e;
    String str3 = ((e.b)apiCallbackData).c;
    String str4 = ((e.b)apiCallbackData).d;
    l.a(str4, "paramParser.name");
    JSONObject jSONObject2 = ((e.b)apiCallbackData).f;
    Boolean bool = ((e.b)apiCallbackData).g;
    l.a(bool, "paramParser.useCloud");
    a1.addUploadRequest(new b.d(i, str2, jSONObject1, str3, str4, jSONObject2, bool.booleanValue()), new a(this, parama, str1));
    return a(e.a.a().a(Integer.valueOf(i)).b());
  }
  
  public static final class a implements b.a {
    a(c param1c, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a, String param1String) {}
    
    public final void a(b.b param1b) {
      String str1;
      String str2;
      l.b(param1b, "requestResult");
      if (param1b.f != null) {
        str1 = com.bytedance.sandboxapp.c.a.a.a.a.a(param1b.f);
      } else {
        str1 = param1b.e;
      } 
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.b.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str3 = this.c;
      d d = d.a().a(Integer.valueOf(param1b.b));
      if (param1b.a) {
        str2 = "success";
      } else {
        str2 = "fail";
      } 
      f f = d.a(str2).b(String.valueOf(param1b.c)).c(param1b.d).d(str1).b();
      l.a(f, "OnUploadTaskStateChangeA…                 .build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str3, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
    
    public final void a(b.c param1c) {
      l.b(param1c, "requestState");
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.b.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str = this.c;
      f f = d.a().a(Integer.valueOf(param1c.a)).a("progressUpdate").b(Integer.valueOf(param1c.b)).a(Long.valueOf(param1c.c)).b(Long.valueOf(param1c.d)).b();
      l.a(f, "OnUploadTaskStateChangeA…esExpectedToSend).build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\f\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */