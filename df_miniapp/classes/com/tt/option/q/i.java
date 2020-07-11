package com.tt.option.q;

import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class i implements b {
  private String a;
  
  private boolean b;
  
  public String c;
  
  public String d;
  
  public String e;
  
  public Map<String, String> f;
  
  public Map<String, Object> g;
  
  public Map<String, b> h;
  
  public byte[] i;
  
  public long j = 10000L;
  
  public long k = 10000L;
  
  public long l = 10000L;
  
  public boolean m;
  
  public JSONObject n;
  
  public boolean o;
  
  public a p;
  
  public i(String paramString1, String paramString2) {
    this(paramString1, paramString2, true);
  }
  
  public i(String paramString1, String paramString2, boolean paramBoolean) {
    this.a = paramString1;
    this.f = new HashMap<String, String>();
    this.g = new HashMap<String, Object>();
    this.h = new HashMap<String, b>();
    this.c = paramString2;
    this.o = paramBoolean;
  }
  
  private String g() {
    StringBuilder stringBuilder = new StringBuilder(this.a);
    boolean bool = this.a.contains("?");
    for (Map.Entry<String, Object> entry : this.g.entrySet()) {
      if (bool) {
        stringBuilder.append("&");
        stringBuilder.append((String)entry.getKey());
        stringBuilder.append("=");
        stringBuilder.append(entry.getValue());
        continue;
      } 
      stringBuilder.append("?");
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append(entry.getValue());
      bool = true;
    } 
    if (this.o)
      if (bool) {
        stringBuilder.append("&device_id=");
        stringBuilder.append(d.a());
      } else {
        stringBuilder.append("?device_id=");
        stringBuilder.append(d.a());
      }  
    return stringBuilder.toString();
  }
  
  public final void a() {
    a a1 = this.p;
    if (a1 != null)
      a1.doCancel(); 
    this.b = true;
  }
  
  public final void a(String paramString1, File paramFile, String paramString2) {
    b b1 = new b(this, paramFile, paramString2);
    this.h.put(paramString1, b1);
  }
  
  public final void a(String paramString, Object paramObject) {
    this.g.put(paramString, paramObject);
  }
  
  public final void a(String paramString1, String paramString2) {
    this.f.put(paramString1, paramString2);
  }
  
  public final void a(Map<String, Object> paramMap) {
    this.g.putAll(paramMap);
  }
  
  public final boolean b() {
    return this.b;
  }
  
  public final Map<String, Object> c() {
    if (this.o && !this.g.containsKey("device_id"))
      this.g.put("device_id", d.a()); 
    return this.g;
  }
  
  public final String d() {
    JSONObject jSONObject = new JSONObject();
    try {
      for (Map.Entry<String, Object> entry : this.g.entrySet())
        jSONObject.put((String)entry.getKey(), entry.getValue()); 
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(5, "TmaRequest", jSONException.getStackTrace());
    } 
    return jSONObject.toString();
  }
  
  public final String e() {
    String str2 = this.d;
    String str1 = str2;
    if (str2 == null)
      str1 = ""; 
    return str1;
  }
  
  public final String f() {
    return "GET".equals(this.c) ? g() : this.a;
  }
  
  public static interface a {
    void doCancel();
  }
  
  public final class b {
    public File a;
    
    public String b;
    
    public b(i this$0, File param1File, String param1String) {
      this.a = param1File;
      this.b = param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */