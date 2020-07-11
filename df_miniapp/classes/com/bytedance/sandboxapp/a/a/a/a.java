package com.bytedance.sandboxapp.a.a.a;

import com.bytedance.sandboxapp.c.a.a.f;

public final class a {
  private String a;
  
  private Integer b;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private Integer f;
  
  private Long g;
  
  private Long h;
  
  private String i;
  
  public static a a() {
    return new a();
  }
  
  public final a a(Integer paramInteger) {
    this.b = paramInteger;
    return this;
  }
  
  public final a a(Long paramLong) {
    this.g = paramLong;
    return this;
  }
  
  public final a a(String paramString) {
    this.a = paramString;
    return this;
  }
  
  public final a b(Integer paramInteger) {
    this.f = paramInteger;
    return this;
  }
  
  public final a b(Long paramLong) {
    this.h = paramLong;
    return this;
  }
  
  public final a b(String paramString) {
    this.c = paramString;
    return this;
  }
  
  public final f b() {
    com.bytedance.sandboxapp.b.b.a a1 = new com.bytedance.sandboxapp.b.b.a();
    a1.a("state", this.a);
    a1.a("downloadTaskId", this.b);
    a1.a("statusCode", this.c);
    a1.a("filePath", this.d);
    a1.a("tempFilePath", this.e);
    a1.a("progress", this.f);
    a1.a("totalBytesWritten", this.g);
    a1.a("totalBytesExpectedToWrite", this.h);
    a1.a("errMsg", this.i);
    return new f(a1);
  }
  
  public final a c(String paramString) {
    this.d = paramString;
    return this;
  }
  
  public final a d(String paramString) {
    this.e = paramString;
    return this;
  }
  
  public final a e(String paramString) {
    this.i = paramString;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */