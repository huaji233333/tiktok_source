package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import org.json.JSONObject;

public abstract class t extends c {
  public t(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("feature is not supported in app", new Object[0]), 0).a());
  }
  
  public abstract void a(a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1);
  
  public final void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    a a1 = new a(this, parama);
    if (a1.a != null) {
      callbackData(a1.a);
      return;
    } 
    a(a1, parama);
  }
  
  public final class a {
    public ApiCallbackData a;
    
    public final Number b;
    
    public final String c;
    
    public final String d;
    
    public final String e;
    
    public final String f;
    
    public final String g;
    
    public final String h;
    
    public final String i;
    
    public final String j;
    
    public final Integer k;
    
    public final Integer l;
    
    public final JSONObject m;
    
    public a(t this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      String str = param1a.b;
      Object object2 = param1a.a("cid");
      if (object2 instanceof Number) {
        this.b = (Number)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "cid");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "cid", "Number");
        } 
        this.b = null;
      } 
      object2 = param1a.a("app_name");
      if (object2 instanceof String) {
        this.c = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "app_name");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "app_name", "String");
        } 
        this.c = null;
      } 
      object2 = param1a.a("package_name");
      if (object2 instanceof String) {
        this.d = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "package_name");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "package_name", "String");
        } 
        this.d = null;
      } 
      object2 = param1a.a("source_avatar");
      if (object2 instanceof String) {
        this.e = (String)object2;
      } else {
        this.e = null;
      } 
      object2 = param1a.a("download_url");
      if (object2 instanceof String) {
        this.f = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "download_url");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "download_url", "String");
        } 
        this.f = null;
      } 
      Object object1 = param1a.a("open_url");
      if (object1 instanceof String) {
        this.g = (String)object1;
      } else {
        this.g = null;
      } 
      object1 = param1a.a("quick_app_url");
      if (object1 instanceof String) {
        this.h = (String)object1;
      } else {
        this.h = null;
      } 
      object1 = param1a.a("web_url");
      if (object1 instanceof String) {
        this.i = (String)object1;
      } else {
        this.i = null;
      } 
      object1 = param1a.a("web_title");
      if (object1 instanceof String) {
        this.j = (String)object1;
      } else {
        this.j = null;
      } 
      object1 = param1a.a("auto_open");
      if (object1 instanceof Integer) {
        this.k = (Integer)object1;
      } else {
        this.k = null;
      } 
      object1 = param1a.a("download_mode");
      if (object1 instanceof Integer) {
        this.l = (Integer)object1;
      } else {
        this.l = null;
      } 
      object1 = param1a.a("log_extra");
      if (object1 instanceof JSONObject) {
        this.m = (JSONObject)object1;
        return;
      } 
      this.m = null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */