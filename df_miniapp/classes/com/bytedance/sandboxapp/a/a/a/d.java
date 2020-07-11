package com.bytedance.sandboxapp.a.a.a;

import com.bytedance.sandboxapp.b.b.a;
import com.bytedance.sandboxapp.c.a.a.f;

public final class d {
  private String a;
  
  private Integer b;
  
  private String c;
  
  private String d;
  
  private Integer e;
  
  private Long f;
  
  private Long g;
  
  private String h;
  
  public static d a() {
    return new d();
  }
  
  public final d a(Integer paramInteger) {
    this.b = paramInteger;
    return this;
  }
  
  public final d a(Long paramLong) {
    this.f = paramLong;
    return this;
  }
  
  public final d a(String paramString) {
    this.a = paramString;
    return this;
  }
  
  public final d b(Integer paramInteger) {
    this.e = paramInteger;
    return this;
  }
  
  public final d b(Long paramLong) {
    this.g = paramLong;
    return this;
  }
  
  public final d b(String paramString) {
    this.c = paramString;
    return this;
  }
  
  public final f b() {
    a a = new a();
    a.a("state", this.a);
    a.a("uploadTaskId", this.b);
    a.a("statusCode", this.c);
    a.a("data", this.d);
    a.a("progress", this.e);
    a.a("totalBytesSent", this.f);
    a.a("totalBytesExpectedToSend", this.g);
    a.a("errMsg", this.h);
    return new f(a);
  }
  
  public final d c(String paramString) {
    this.d = paramString;
    return this;
  }
  
  public final d d(String paramString) {
    this.h = paramString;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */