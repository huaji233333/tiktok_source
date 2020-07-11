package com.bytedance.sandboxapp.protocol.service.api.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import d.f.b.g;
import d.f.b.l;
import org.json.JSONException;
import org.json.JSONObject;

public final class ApiCallbackData implements Parcelable {
  public static final Parcelable.Creator<ApiCallbackData> CREATOR;
  
  public static final b c = new b(null);
  
  public final JSONObject a;
  
  public final boolean b;
  
  private final String d;
  
  static {
    CREATOR = new c();
  }
  
  protected ApiCallbackData(Parcel paramParcel) {
    boolean bool;
    JSONObject jSONObject;
    String str2 = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.b = bool;
    String str1 = str2;
    if (str2 == null) {
      com.bytedance.sandboxapp.b.a.a.b.b.logOrToast("ApiCallbackData", new Object[] { "读取到空的 Api 执行结果" });
      str1 = "";
    } 
    try {
      jSONObject = new JSONObject(str1);
    } catch (JSONException jSONException) {
      jSONObject = new JSONObject();
      com.bytedance.sandboxapp.b.a.a.b.b.logOrToast("ApiCallbackData", new Object[] { "从执行结果解析为 JsonObject 时异常 result：", str1, jSONException });
    } 
    this.d = str1;
    this.a = jSONObject;
  }
  
  private ApiCallbackData(JSONObject paramJSONObject, boolean paramBoolean) {
    this.a = paramJSONObject;
    String str = this.a.toString();
    l.a(str, "callbackDataJson.toString()");
    this.d = str;
    this.b = paramBoolean;
  }
  
  public final int describeContents() {
    return 0;
  }
  
  public final String toString() {
    return this.d;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    l.b(paramParcel, "dest");
    paramParcel.writeString(this.d);
    paramParcel.writeByte((byte)this.b);
  }
  
  public static final class a {
    public static final a b = new a(null);
    
    com.bytedance.sandboxapp.b.b.a a;
    
    private final boolean c;
    
    private String d;
    
    private int e;
    
    private final String f;
    
    private final String g;
    
    private a(String param1String1, String param1String2) {
      boolean bool;
      this.f = param1String1;
      this.g = param1String2;
      if (this.g == "fail") {
        bool = true;
      } else {
        bool = false;
      } 
      this.c = bool;
    }
    
    public static final a a(String param1String1, String param1String2, int param1Int) {
      return a.a(param1String1, param1String2, 0);
    }
    
    public final a a(int param1Int) {
      this.e = param1Int;
      return this;
    }
    
    public final a a(String param1String) {
      this.d = param1String;
      return this;
    }
    
    public final a a(String param1String, Object param1Object) {
      l.b(param1String, "key");
      if (this.a == null)
        this.a = new com.bytedance.sandboxapp.b.b.a(); 
      try {
        com.bytedance.sandboxapp.b.b.a a1 = this.a;
        if (a1 == null)
          l.a(); 
        a1.a(param1String, param1Object);
        return this;
      } catch (Exception exception) {
        com.bytedance.sandboxapp.b.a.b.b.b.e("ApiCallbackData", new Object[] { "append", exception });
        return this;
      } 
    }
    
    public final ApiCallbackData a() {
      com.bytedance.sandboxapp.b.b.a a2 = this.a;
      com.bytedance.sandboxapp.b.b.a a1 = a2;
      if (a2 == null)
        a1 = new com.bytedance.sandboxapp.b.b.a(); 
      a1.a("errMsg", a.a(this.f, this.g, this.d));
      int i = this.e;
      if (i != 0)
        a1.a("errCode", Integer.valueOf(i)); 
      return new ApiCallbackData(a1.a, this.c, null);
    }
    
    public final String toString() {
      com.bytedance.sandboxapp.b.a.b.b.b.e("ApiCallbackData", new Object[] { "请避免使用 Builder 的 toString" });
      return a().toString();
    }
    
    public static final class a {
      private a() {}
      
      public static ApiCallbackData.a a(String param2String, com.bytedance.sandboxapp.b.b.a param2a) {
        l.b(param2String, "apiName");
        ApiCallbackData.a a1 = new ApiCallbackData.a(param2String, "ok", null);
        a1.a = param2a;
        return a1;
      }
      
      public static ApiCallbackData.a a(String param2String1, String param2String2, int param2Int) {
        l.b(param2String1, "apiName");
        l.b(param2String2, "extraInfo");
        return (new ApiCallbackData.a(param2String1, "fail", null)).a(param2String2).a(param2Int);
      }
      
      public static String a(String param2String1, String param2String2, String param2String3) {
        StringBuilder stringBuilder1;
        if (TextUtils.isEmpty(param2String3)) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(param2String1);
          stringBuilder1.append(':');
          stringBuilder1.append(param2String2);
          return stringBuilder1.toString();
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(param2String1);
        stringBuilder2.append(':');
        stringBuilder2.append(param2String2);
        stringBuilder2.append(' ');
        stringBuilder2.append((String)stringBuilder1);
        return stringBuilder2.toString();
      }
    }
  }
  
  public static final class a {
    private a() {}
    
    public static ApiCallbackData.a a(String param1String, com.bytedance.sandboxapp.b.b.a param1a) {
      l.b(param1String, "apiName");
      ApiCallbackData.a a1 = new ApiCallbackData.a(param1String, "ok", null);
      a1.a = param1a;
      return a1;
    }
    
    public static ApiCallbackData.a a(String param1String1, String param1String2, int param1Int) {
      l.b(param1String1, "apiName");
      l.b(param1String2, "extraInfo");
      return (new ApiCallbackData.a(param1String1, "fail", null)).a(param1String2).a(param1Int);
    }
    
    public static String a(String param1String1, String param1String2, String param1String3) {
      StringBuilder stringBuilder1;
      if (TextUtils.isEmpty(param1String3)) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(param1String1);
        stringBuilder1.append(':');
        stringBuilder1.append(param1String2);
        return stringBuilder1.toString();
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(param1String1);
      stringBuilder2.append(':');
      stringBuilder2.append(param1String2);
      stringBuilder2.append(' ');
      stringBuilder2.append((String)stringBuilder1);
      return stringBuilder2.toString();
    }
  }
  
  public static final class b {
    private b() {}
  }
  
  public static final class c implements Parcelable.Creator<ApiCallbackData> {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\api\entity\ApiCallbackData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */