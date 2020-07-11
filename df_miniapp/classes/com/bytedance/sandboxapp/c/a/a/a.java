package com.bytedance.sandboxapp.c.a.a;

import com.bytedance.sandboxapp.b.a.a.b;
import com.bytedance.sandboxapp.b.a.b.b;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.d.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import d.f.b.g;
import d.f.b.l;

public abstract class a {
  public static final a Companion = new a(null);
  
  public final com.bytedance.sandboxapp.a.a.d.a apiInfoEntity;
  
  public final String apiName;
  
  public final com.bytedance.sandboxapp.b.a context;
  
  public final b sandboxAppApiRuntime;
  
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    this.sandboxAppApiRuntime = paramb;
    this.apiInfoEntity = parama;
    String str = this.apiInfoEntity.D;
    l.a(str, "apiInfoEntity.api");
    this.apiName = str;
    this.context = this.sandboxAppApiRuntime.getContext();
  }
  
  public static final ApiCallbackData buildParamInvalid(String paramString1, String paramString2) {
    return a.b(paramString1, paramString2);
  }
  
  public static final ApiCallbackData buildParamTypeInvalid(String paramString1, String paramString2, String paramString3) {
    return a.a(paramString1, paramString2, paramString3);
  }
  
  public static final ApiCallbackData buildParamsIsRequired(String paramString1, String paramString2) {
    return a.a(paramString1, paramString2);
  }
  
  public static final String nativeExceptionExtraInfo(Throwable paramThrowable) {
    return a.a(paramThrowable);
  }
  
  public final ApiCallbackData buildAppInBackground() {
    ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
    String str = this.apiInfoEntity.D;
    l.a(str, "apiInfoEntity.api");
    return ApiCallbackData.a.a.a(a1, str, "app in background", 0, 4, null).a();
  }
  
  public final ApiCallbackData buildFeatureNotSupport() {
    ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
    String str = this.apiInfoEntity.D;
    l.a(str, "apiInfoEntity.api");
    return ApiCallbackData.a.a.a(a1, str, "feature is not supported in app", 0, 4, null).a();
  }
  
  public final ApiCallbackData buildNativeException(Throwable paramThrowable) {
    ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
    String str = this.apiInfoEntity.D;
    l.a(str, "apiInfoEntity.api");
    return ApiCallbackData.a.a.a(a1, str, a.a(paramThrowable), 0, 4, null).a();
  }
  
  public abstract b handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a parama);
  
  public static final class a {
    private a() {}
    
    public static ApiCallbackData a(String param1String1, String param1String2) {
      l.b(param1String1, "apiName");
      l.b(param1String2, "paramName");
      ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
      StringBuilder stringBuilder = new StringBuilder("params ");
      stringBuilder.append(param1String2);
      stringBuilder.append(" is required");
      return ApiCallbackData.a.a.a(a1, param1String1, stringBuilder.toString(), 0, 4, null).a();
    }
    
    public static ApiCallbackData a(String param1String1, String param1String2, String param1String3) {
      l.b(param1String1, "apiName");
      l.b(param1String2, "paramName");
      l.b(param1String3, "exceptedClassType");
      ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
      StringBuilder stringBuilder = new StringBuilder("params ");
      stringBuilder.append(param1String2);
      stringBuilder.append(" type is not ");
      stringBuilder.append(param1String3);
      stringBuilder.append(" type");
      return ApiCallbackData.a.a.a(a1, param1String1, stringBuilder.toString(), 0, 4, null).a();
    }
    
    public static String a(Throwable param1Throwable) {
      if (param1Throwable == null)
        return "null throwable"; 
      if (b.b.isDebugMode())
        b.b.e("AbsApiHandler", new Object[] { "throwableExtraInfo", param1Throwable }); 
      StringBuilder stringBuilder = new StringBuilder("native exception ");
      stringBuilder.append(param1Throwable);
      stringBuilder.append(" stack:");
      stringBuilder.append(b.a(param1Throwable, 1, 5));
      return stringBuilder.toString();
    }
    
    public static ApiCallbackData b(String param1String1, String param1String2) {
      l.b(param1String1, "apiName");
      l.b(param1String2, "paramName");
      ApiCallbackData.a.a a1 = ApiCallbackData.a.b;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String2);
      stringBuilder.append(" is invalid");
      return ApiCallbackData.a.a.a(a1, param1String1, stringBuilder.toString(), 0, 4, null).a();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */