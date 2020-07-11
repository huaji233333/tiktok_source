package com.tt.option.ad;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.NativeDimenUtil;
import org.json.JSONObject;

public final class h {
  public String a;
  
  public String b;
  
  public int c;
  
  public int d;
  
  public int e;
  
  public int f;
  
  public int g;
  
  public h(String paramString) {
    try {
      JSONObject jSONObject1 = new JSONObject(paramString);
      this.a = jSONObject1.optString("adUnitId");
      this.b = jSONObject1.optString("type");
      JSONObject jSONObject2 = jSONObject1.optJSONObject("style");
      if (jSONObject2 != null) {
        this.c = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("left", 0));
        this.d = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("top", 0));
        this.e = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("width", 0));
      } 
      this.g = jSONObject1.optInt("adIntervals");
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "GameAdModel", exception.getStackTrace());
      return;
    } 
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("GameAdModel{adUnitId='");
    stringBuilder.append(this.a);
    stringBuilder.append('\'');
    stringBuilder.append(", type='");
    stringBuilder.append(this.b);
    stringBuilder.append('\'');
    stringBuilder.append(", left=");
    stringBuilder.append(this.c);
    stringBuilder.append(", top=");
    stringBuilder.append(this.d);
    stringBuilder.append(", width=");
    stringBuilder.append(this.e);
    stringBuilder.append(", height=");
    stringBuilder.append(this.f);
    stringBuilder.append(", adIntervals=");
    stringBuilder.append(this.g);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */