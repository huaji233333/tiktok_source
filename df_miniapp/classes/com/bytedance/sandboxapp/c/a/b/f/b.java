package com.bytedance.sandboxapp.c.a.b.f;

import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.a.c;
import com.bytedance.sandboxapp.a.a.c.d;
import com.bytedance.sandboxapp.c.a.a.f;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import d.f.b.l;
import org.json.JSONArray;

public final class b extends d {
  private final String b = "CreateRequestTaskApiHandler";
  
  public b(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(d.b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    ApiCallbackData apiCallbackData;
    Boolean bool5;
    l.b(paramb, "paramParser");
    l.b(parama, "apiInvokeInfo");
    boolean bool2 = TextUtils.equals(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, "createInnerRequestTask");
    String str2 = paramb.d;
    l.a(str2, "paramParser.method");
    String str3 = paramb.b;
    l.a(str3, "paramParser.url");
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
      str1 = "POST"; 
    com.bytedance.sandboxapp.b.a.b.b.b.d(this.b, new Object[] { "isInner:", Boolean.valueOf(bool2), "url:", paramb.b, "method:", str1, "header:", paramb.f, "data:", paramb.e });
    if (!bool2 && !TextUtils.isEmpty(str3)) {
      com.bytedance.sandboxapp.protocol.service.l.a a3 = (com.bytedance.sandboxapp.protocol.service.l.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.l.a.class);
      String str = paramb.b;
      l.a(str, "paramParser.url");
      if (!a3.isSafeDomain("request", str)) {
        apiCallbackData = a(paramb.b);
        l.a(apiCallbackData, "buildInvalidDomain(paramParser.url)");
        return apiCallbackData;
      } 
    } 
    if (bool2) {
      str2 = "onInnerRequestTaskStateChange";
    } else {
      str2 = "onRequestTaskStateChange";
    } 
    if (bool2) {
      bool5 = ((d.b)apiCallbackData).j;
    } else {
      bool5 = Boolean.valueOf(false);
    } 
    l.a(bool5, "if (isInner) {\n         …          false\n        }");
    boolean bool3 = bool5.booleanValue();
    boolean bool4 = parama.d;
    int i = ((com.bytedance.sandboxapp.protocol.service.i.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.i.a.class)).getRequestIdentifyId();
    com.bytedance.sandboxapp.protocol.service.request.a a2 = (com.bytedance.sandboxapp.protocol.service.request.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.request.a.class);
    HttpRequest.RequestTask.a a1 = HttpRequest.RequestTask.a.a.a(str3, str1);
    a1.b = i;
    Boolean bool6 = ((d.b)apiCallbackData).c;
    l.a(bool6, "paramParser.usePrefetchCache");
    a1.a = bool6.booleanValue();
    a1 = a1.b(((d.b)apiCallbackData).e).a(((d.b)apiCallbackData).f).a(((d.b)apiCallbackData).g).a(com.bytedance.sandboxapp.d.a.a(((d.b)apiCallbackData).h, bool4)).a(bool2);
    Boolean bool1 = ((d.b)apiCallbackData).i;
    l.a(bool1, "paramParser.useCloud");
    a1.d = bool1.booleanValue();
    a1.e = bool3;
    a2.addHttpRequest(a1.a(), new a(this, bool4, parama, str2));
    return a(d.a.a().a(Integer.valueOf(i)).b());
  }
  
  public static final class a implements HttpRequest.a {
    a(b param1b, boolean param1Boolean, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a, String param1String) {}
    
    public final void onRequestAbort(HttpRequest.RequestTask param1RequestTask) {
      l.b(param1RequestTask, "requestTask");
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.c.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str = this.d;
      f f = c.a().a(Integer.valueOf(param1RequestTask.a)).a("fail").d("abort").b();
      l.a(f, "OnRequestTaskStateChange…                 .build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
    
    public final void onRequestFinish(HttpRequest.RequestResult param1RequestResult) {
      JSONArray jSONArray;
      String str1;
      String str2;
      l.b(param1RequestResult, "requestResult");
      if (TextUtils.equals(param1RequestResult.f, "arraybuffer")) {
        jSONArray = com.bytedance.sandboxapp.d.a.a(param1RequestResult.g, this.b);
      } else {
        jSONArray = null;
      } 
      if (param1RequestResult.i != null) {
        str1 = com.bytedance.sandboxapp.c.a.a.a.a.a(param1RequestResult.i);
      } else {
        str1 = param1RequestResult.h;
      } 
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.c.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str3 = this.d;
      c c2 = c.a().a(Integer.valueOf(param1RequestResult.a));
      if (param1RequestResult.b) {
        str2 = "success";
      } else {
        str2 = "fail";
      } 
      c c1 = c2.a(str2).a(param1RequestResult.e).b(String.valueOf(param1RequestResult.c));
      int i = param1RequestResult.j;
      boolean bool = true;
      if (i != 1)
        bool = false; 
      f f = c1.a(Boolean.valueOf(bool)).c(param1RequestResult.d).a(jSONArray).d(str1).b();
      l.a(f, "OnRequestTaskStateChange…                 .build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str3, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\f\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */