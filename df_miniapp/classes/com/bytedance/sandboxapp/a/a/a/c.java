package com.bytedance.sandboxapp.a.a.a;

import com.bytedance.sandboxapp.b.b.a;
import com.bytedance.sandboxapp.c.a.a.f;
import org.json.JSONArray;
import org.json.JSONObject;

public final class c {
  private String a;
  
  private Integer b;
  
  private JSONObject c;
  
  private String d;
  
  private Boolean e;
  
  private JSONArray f;
  
  private String g;
  
  private String h;
  
  public static c a() {
    return new c();
  }
  
  public final c a(Boolean paramBoolean) {
    this.e = paramBoolean;
    return this;
  }
  
  public final c a(Integer paramInteger) {
    this.b = paramInteger;
    return this;
  }
  
  public final c a(String paramString) {
    this.a = paramString;
    return this;
  }
  
  public final c a(JSONArray paramJSONArray) {
    this.f = paramJSONArray;
    return this;
  }
  
  public final c a(JSONObject paramJSONObject) {
    this.c = paramJSONObject;
    return this;
  }
  
  public final c b(String paramString) {
    this.d = paramString;
    return this;
  }
  
  public final f b() {
    a a = new a();
    a.a("state", this.a);
    a.a("requestTaskId", this.b);
    a.a("header", this.c);
    a.a("statusCode", this.d);
    a.a("isPrefetch", this.e);
    a.a("__nativeBuffers__", this.f);
    a.a("data", this.g);
    a.a("errMsg", this.h);
    return new f(a);
  }
  
  public final c c(String paramString) {
    this.g = paramString;
    return this;
  }
  
  public final c d(String paramString) {
    this.h = paramString;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */