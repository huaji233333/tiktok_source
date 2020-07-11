package com.bytedance.sandboxapp.b.b;

import android.text.TextUtils;
import com.bytedance.sandboxapp.b.a.b.b;
import d.f.b.l;
import org.json.JSONException;
import org.json.JSONObject;

public final class a {
  public JSONObject a;
  
  private final String b;
  
  public a() {
    this.b = "SandboxJsonObject";
    this.a = new JSONObject();
  }
  
  public a(String paramString) {
    JSONObject jSONObject;
    this.b = "SandboxJsonObject";
    try {
      if (TextUtils.isEmpty(paramString)) {
        jSONObject = new JSONObject();
      } else {
        jSONObject = new JSONObject((String)jSONObject);
      } 
    } catch (JSONException jSONException) {
      b.b.e(this.b, new Object[] { jSONException });
      jSONObject = new JSONObject();
    } 
    this.a = jSONObject;
  }
  
  public a(JSONObject paramJSONObject) {
    this.b = "SandboxJsonObject";
    JSONObject jSONObject = paramJSONObject;
    if (paramJSONObject == null)
      jSONObject = new JSONObject(); 
    this.a = jSONObject;
  }
  
  public final a a(String paramString, Object paramObject) {
    l.b(paramString, "key");
    try {
      this.a.put(paramString, paramObject);
      return this;
    } catch (JSONException jSONException) {
      b.b.e(this.b, new Object[] { jSONException });
      return this;
    } 
  }
  
  public final String toString() {
    String str = this.a.toString();
    l.a(str, "toJson().toString()");
    return str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\b\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */