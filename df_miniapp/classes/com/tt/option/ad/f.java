package com.tt.option.ad;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.NativeDimenUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class f {
  public String a;
  
  public boolean b;
  
  public int c;
  
  public int d;
  
  public int e;
  
  public int f;
  
  public boolean g;
  
  public int h;
  
  public boolean i;
  
  public boolean j;
  
  public boolean k;
  
  public int l;
  
  public boolean m;
  
  public List<a> n;
  
  public f(String paramString) {
    try {
      JSONObject jSONObject1 = new JSONObject(paramString);
      this.a = jSONObject1.optString("unitId");
      JSONObject jSONObject2 = jSONObject1.optJSONObject("position");
      if (jSONObject2 != null) {
        this.b = true;
        this.c = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("left", 0));
        this.d = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("top", 0));
        this.e = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("width", 0));
        this.f = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("height", 0));
      } 
      this.g = jSONObject1.optBoolean("hide");
      if (jSONObject1.has("zIndex")) {
        this.i = true;
        this.h = jSONObject1.optInt("zIndex");
      } 
      if (jSONObject1.has("fixed")) {
        this.k = true;
        this.j = jSONObject1.optBoolean("fixed");
      } 
      this.l = jSONObject1.optInt("adIntervals");
      this.m = jSONObject1.optBoolean("isInScrollView");
      this.n = a(jSONObject1);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "AdViewModel", jSONException.getStackTrace());
      return;
    } 
  }
  
  private static List<a> a(JSONObject paramJSONObject) {
    if (!paramJSONObject.has("feedList"))
      return null; 
    try {
      JSONArray jSONArray = paramJSONObject.getJSONArray("feedList");
      ArrayList<a> arrayList = new ArrayList();
      int j = jSONArray.length();
      for (int i = 0; i < j; i++) {
        JSONObject jSONObject = jSONArray.getJSONObject(i);
        a a = new a();
        a.a = jSONObject.optString("type");
        a.b = (float)jSONObject.optDouble("scale", 1.0D);
        arrayList.add(a);
      } 
      return arrayList;
    } catch (JSONException jSONException) {
      return null;
    } 
  }
  
  private boolean c() {
    List<a> list = this.n;
    return (list != null && list.size() > 0);
  }
  
  public final boolean a() {
    return !TextUtils.isEmpty(this.a);
  }
  
  public final String b() {
    return c() ? "feed" : "banner";
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("AdViewModel{unitId='");
    stringBuilder.append(this.a);
    stringBuilder.append('\'');
    stringBuilder.append(", hasPosition=");
    stringBuilder.append(this.b);
    stringBuilder.append(", left=");
    stringBuilder.append(this.c);
    stringBuilder.append(", top=");
    stringBuilder.append(this.d);
    stringBuilder.append(", width=");
    stringBuilder.append(this.e);
    stringBuilder.append(", height=");
    stringBuilder.append(this.f);
    stringBuilder.append(", isHide=");
    stringBuilder.append(this.g);
    stringBuilder.append(", zIndex=");
    stringBuilder.append(this.h);
    stringBuilder.append(", hasZIndex=");
    stringBuilder.append(this.i);
    stringBuilder.append(", isFixed=");
    stringBuilder.append(this.j);
    stringBuilder.append(", hasFixed=");
    stringBuilder.append(this.k);
    stringBuilder.append(", adIntervals=");
    stringBuilder.append(this.l);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public static final class a {
    public String a;
    
    public float b;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */