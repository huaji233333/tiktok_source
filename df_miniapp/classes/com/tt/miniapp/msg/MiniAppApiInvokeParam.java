package com.tt.miniapp.msg;

import com.bytedance.sandboxapp.b.b.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import org.json.JSONObject;

public class MiniAppApiInvokeParam implements a {
  private JSONObject mJsonData;
  
  private String mStringData;
  
  public MiniAppApiInvokeParam(String paramString) {
    this.mStringData = paramString;
  }
  
  public MiniAppApiInvokeParam(JSONObject paramJSONObject) {
    this.mJsonData = paramJSONObject;
  }
  
  private void initJsonData() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJsonData : Lorg/json/JSONObject;
    //   4: ifnonnull -> 98
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mJsonData : Lorg/json/JSONObject;
    //   13: astore_1
    //   14: aload_1
    //   15: ifnonnull -> 90
    //   18: aload_0
    //   19: getfield mStringData : Ljava/lang/String;
    //   22: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   25: ifeq -> 42
    //   28: aload_0
    //   29: new org/json/JSONObject
    //   32: dup
    //   33: invokespecial <init> : ()V
    //   36: putfield mJsonData : Lorg/json/JSONObject;
    //   39: goto -> 90
    //   42: aload_0
    //   43: new org/json/JSONObject
    //   46: dup
    //   47: aload_0
    //   48: getfield mStringData : Ljava/lang/String;
    //   51: invokespecial <init> : (Ljava/lang/String;)V
    //   54: putfield mJsonData : Lorg/json/JSONObject;
    //   57: goto -> 90
    //   60: astore_1
    //   61: aload_0
    //   62: new org/json/JSONObject
    //   65: dup
    //   66: invokespecial <init> : ()V
    //   69: putfield mJsonData : Lorg/json/JSONObject;
    //   72: ldc 'ApiInvokeParam'
    //   74: iconst_2
    //   75: anewarray java/lang/Object
    //   78: dup
    //   79: iconst_0
    //   80: ldc 'ApiInvokeParam'
    //   82: aastore
    //   83: dup
    //   84: iconst_1
    //   85: aload_1
    //   86: aastore
    //   87: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   90: aload_0
    //   91: monitorexit
    //   92: return
    //   93: astore_1
    //   94: aload_0
    //   95: monitorexit
    //   96: aload_1
    //   97: athrow
    //   98: return
    // Exception table:
    //   from	to	target	type
    //   9	14	93	finally
    //   18	39	60	org/json/JSONException
    //   18	39	93	finally
    //   42	57	60	org/json/JSONException
    //   42	57	93	finally
    //   61	90	93	finally
    //   90	92	93	finally
    //   94	96	93	finally
  }
  
  public <T> T getParam(String paramString) {
    initJsonData();
    Object object2 = this.mJsonData.opt(paramString);
    Object object1 = object2;
    if (object2 == JSONObject.NULL)
      object1 = null; 
    return (T)object1;
  }
  
  public a toJson() {
    initJsonData();
    return new a(this.mJsonData);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\MiniAppApiInvokeParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */