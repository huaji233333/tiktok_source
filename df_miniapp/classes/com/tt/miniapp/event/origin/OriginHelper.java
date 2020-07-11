package com.tt.miniapp.event.origin;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class OriginHelper {
  private static volatile JSONObject sOrigin;
  
  private static Uri addOriginIfNeeded(Uri paramUri) {
    Uri uri = paramUri;
    if (paramUri.getQueryParameter("origin_entrance") == null) {
      JSONObject jSONObject = getOriginJson();
      if (jSONObject == null)
        return paramUri; 
      MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramUri.toString());
      microSchemaEntity.addCustomField("origin_entrance", jSONObject.toString());
      uri = Uri.parse(microSchemaEntity.toSchema());
    } 
    return uri;
  }
  
  public static String addOriginIfNeeded(String paramString) {
    return TextUtils.isEmpty(paramString) ? paramString : addOriginIfNeeded(Uri.parse(paramString)).toString();
  }
  
  private static JSONObject buildOrigin() throws Exception {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    String str = AppbrandApplicationImpl.getInst().getSchema();
    if (appInfoEntity == null || TextUtils.isEmpty(str)) {
      AppBrandLogger.e("OriginHelper", new Object[] { "null appInfo or empty schema" });
      return null;
    } 
    StringBuilder stringBuilder = new StringBuilder("schema: ");
    stringBuilder.append(str);
    AppBrandLogger.d("OriginHelper", new Object[] { stringBuilder.toString() });
    str = Uri.parse(str).getQueryParameter("origin_entrance");
    return TextUtils.isEmpty(str) ? createOrigin(appInfoEntity, new JSONObject()) : new JSONObject(str);
  }
  
  private static JSONObject createOrigin(AppInfoEntity paramAppInfoEntity, JSONObject paramJSONObject) throws JSONException {
    paramJSONObject.put("oe_launch_from", CharacterUtils.null2Empty(paramAppInfoEntity.launchFrom));
    paramJSONObject.put("oe_location", CharacterUtils.null2Empty(paramAppInfoEntity.location));
    return paramJSONObject;
  }
  
  public static JSONObject getOriginJson() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   3: ifnull -> 44
    //   6: new java/lang/StringBuilder
    //   9: dup
    //   10: ldc 'cache OriginJson: '
    //   12: invokespecial <init> : (Ljava/lang/String;)V
    //   15: astore_0
    //   16: aload_0
    //   17: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   20: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 'OriginHelper'
    //   26: iconst_1
    //   27: anewarray java/lang/Object
    //   30: dup
    //   31: iconst_0
    //   32: aload_0
    //   33: invokevirtual toString : ()Ljava/lang/String;
    //   36: aastore
    //   37: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   40: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   43: areturn
    //   44: ldc com/tt/miniapp/event/origin/OriginHelper
    //   46: monitorenter
    //   47: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   50: ifnull -> 96
    //   53: new java/lang/StringBuilder
    //   56: dup
    //   57: ldc 'cache OriginJson: '
    //   59: invokespecial <init> : (Ljava/lang/String;)V
    //   62: astore_0
    //   63: aload_0
    //   64: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   67: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: ldc 'OriginHelper'
    //   73: iconst_1
    //   74: anewarray java/lang/Object
    //   77: dup
    //   78: iconst_0
    //   79: aload_0
    //   80: invokevirtual toString : ()Ljava/lang/String;
    //   83: aastore
    //   84: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   87: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   90: astore_0
    //   91: ldc com/tt/miniapp/event/origin/OriginHelper
    //   93: monitorexit
    //   94: aload_0
    //   95: areturn
    //   96: invokestatic buildOrigin : ()Lorg/json/JSONObject;
    //   99: putstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   102: goto -> 118
    //   105: astore_0
    //   106: ldc 'OriginHelper'
    //   108: ldc 'buildOriginFailed'
    //   110: aload_0
    //   111: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   114: aconst_null
    //   115: putstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   118: ldc com/tt/miniapp/event/origin/OriginHelper
    //   120: monitorexit
    //   121: new java/lang/StringBuilder
    //   124: dup
    //   125: ldc 'getOriginJson: '
    //   127: invokespecial <init> : (Ljava/lang/String;)V
    //   130: astore_0
    //   131: aload_0
    //   132: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   135: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: ldc 'OriginHelper'
    //   141: iconst_1
    //   142: anewarray java/lang/Object
    //   145: dup
    //   146: iconst_0
    //   147: aload_0
    //   148: invokevirtual toString : ()Ljava/lang/String;
    //   151: aastore
    //   152: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   155: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   158: ifnonnull -> 163
    //   161: aconst_null
    //   162: areturn
    //   163: getstatic com/tt/miniapp/event/origin/OriginHelper.sOrigin : Lorg/json/JSONObject;
    //   166: areturn
    //   167: astore_0
    //   168: ldc com/tt/miniapp/event/origin/OriginHelper
    //   170: monitorexit
    //   171: aload_0
    //   172: athrow
    // Exception table:
    //   from	to	target	type
    //   47	94	167	finally
    //   96	102	105	java/lang/Exception
    //   96	102	167	finally
    //   106	118	167	finally
    //   118	121	167	finally
    //   168	171	167	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\origin\OriginHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */