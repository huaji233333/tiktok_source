package com.tt.frontendapiinterface;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.DebugUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCallResult implements Parcelable {
  public static final Parcelable.Creator<ApiCallResult> CREATOR = new Parcelable.Creator<ApiCallResult>() {
    
    };
  
  public final JSONObject a;
  
  private final String b;
  
  protected ApiCallResult(Parcel paramParcel) {
    JSONObject jSONObject;
    String str2 = paramParcel.readString();
    String str1 = str2;
    if (str2 == null) {
      DebugUtil.outputError("ApiCallResult", new Object[] { "读取到空的 Api 执行结果" });
      str1 = "";
    } 
    try {
      jSONObject = new JSONObject(str1);
    } catch (JSONException jSONException) {
      jSONObject = new JSONObject();
      DebugUtil.outputError("ApiCallResult", new Object[] { "从执行结果解析为 JsonObject 时异常 result：", str1, jSONException });
    } 
    this.b = str1;
    this.a = jSONObject;
  }
  
  private ApiCallResult(JSONObject paramJSONObject, boolean paramBoolean) {
    this.a = paramJSONObject;
    this.b = this.a.toString();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String toString() {
    return this.b;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.b);
  }
  
  public static final class a {
    private final boolean a;
    
    private final String b;
    
    private final String c;
    
    private String d;
    
    private JSONObject e;
    
    private int f;
    
    public a(String param1String1, String param1String2) {
      boolean bool;
      this.b = param1String1;
      this.c = param1String2;
      if (param1String2 == "fail") {
        bool = true;
      } else {
        bool = false;
      } 
      this.a = bool;
    }
    
    public static a a(String param1String) {
      return new a(param1String, "ok");
    }
    
    public static a a(String param1String1, String param1String2, int param1Int) {
      a a1 = (new a(param1String1, "fail")).d(param1String2);
      a1.f = param1Int;
      return a1;
    }
    
    @Deprecated
    public static a b(String param1String) {
      return new a(param1String, "fail");
    }
    
    public static a c(String param1String) {
      return new a(param1String, "cancel");
    }
    
    public final a a(String param1String, Object param1Object) {
      if (this.e == null)
        this.e = new JSONObject(); 
      try {
        this.e.put(param1String, param1Object);
        return this;
      } catch (Exception exception) {
        AppBrandLogger.e("ApiCallResult", new Object[] { "append", exception });
        return this;
      } 
    }
    
    public final a a(Throwable param1Throwable) {
      this.d = a.a(param1Throwable);
      return this;
    }
    
    public final a a(HashMap<String, Object> param1HashMap) {
      this.e = a.a(param1HashMap);
      return this;
    }
    
    public final a a(JSONObject param1JSONObject) {
      this.e = param1JSONObject;
      return this;
    }
    
    public final ApiCallResult a() {
      JSONObject jSONObject = this.e;
      if (jSONObject == null)
        jSONObject = new JSONObject(); 
      try {
        StringBuilder stringBuilder;
        String str1 = this.b;
        String str2 = this.c;
        String str3 = this.d;
        boolean bool = TextUtils.isEmpty(str3);
        if (bool) {
          stringBuilder = new StringBuilder();
          stringBuilder.append(str1);
          stringBuilder.append(":");
          stringBuilder.append(str2);
          str1 = stringBuilder.toString();
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str1);
          stringBuilder1.append(":");
          stringBuilder1.append(str2);
          stringBuilder1.append(" ");
          stringBuilder1.append((String)stringBuilder);
          str1 = stringBuilder1.toString();
        } 
        jSONObject.put("errMsg", str1);
        if (this.f != 0)
          jSONObject.put("errCode", this.f); 
      } catch (Exception exception) {
        AppBrandLogger.e("ApiCallResult", new Object[] { "build", exception });
      } 
      return new ApiCallResult(jSONObject, this.a);
    }
    
    public final a d(String param1String) {
      this.d = param1String;
      return this;
    }
    
    public final String toString() {
      AppBrandLogger.e("ApiCallResult", new Object[] { "请避免使用 Builder 的 toString" });
      return a().toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\frontendapiinterface\ApiCallResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */