package com.bytedance.ies.bullet.kit.rn.internal;

import android.text.TextUtils;
import com.bytedance.ies.bullet.b.e.a.f;
import com.bytedance.ies.bullet.b.e.a.h;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.x;
import org.json.JSONException;
import org.json.JSONObject;

@ReactModule(name = "RNBridge")
public final class RnBridgeModule extends ReactContextBaseJavaModule {
  public static final a Companion = new a(null);
  
  public final d.f.a.a<h> bridgeRegistryProvider;
  
  public RnBridgeModule(ReactApplicationContext paramReactApplicationContext, d.f.a.a<? extends h> parama) {
    super(paramReactApplicationContext);
    this.bridgeRegistryProvider = (d.f.a.a)parama;
  }
  
  @ReactMethod
  public final void call(String paramString, ReadableMap paramReadableMap, Callback paramCallback) {
    l.b(paramString, "func");
    l.b(paramReadableMap, "params");
    l.b(paramCallback, "callback");
    if (TextUtils.isEmpty(paramString))
      return; 
    UiThreadUtil.runOnUiThread(new b(this, paramString, paramReadableMap, paramCallback));
  }
  
  public final String getName() {
    return "RNBridge";
  }
  
  public static final class a {
    private a() {}
  }
  
  static final class b implements Runnable {
    b(RnBridgeModule param1RnBridgeModule, String param1String, ReadableMap param1ReadableMap, Callback param1Callback) {}
    
    public final void run() {
      h h = (h)this.a.bridgeRegistryProvider.invoke();
      if (h != null) {
        String str = this.b;
        JSONObject jSONObject = com.bytedance.ies.bullet.kit.rn.d.b.a(this.c);
        l.a(jSONObject, "JsonConvertHelper.reactToJSON(params)");
        h.a(str, jSONObject, new f.b(this) {
              public final void a(int param2Int, String param2String) {
                l.b(param2String, "message");
                WritableMap writableMap = Arguments.createMap();
                writableMap.putInt("code", param2Int);
                writableMap.putString("message", param2String);
                this.a.d.invoke(new Object[] { writableMap });
              }
              
              public final void a(JSONObject param2JSONObject) {
                l.b(param2JSONObject, "data");
                try {
                  this.a.d.invoke(new Object[] { com.bytedance.ies.bullet.kit.rn.d.b.a(param2JSONObject) });
                  return;
                } catch (JSONException jSONException) {
                  jSONException.printStackTrace();
                  return;
                } 
              }
            }new d.f.a.b<Throwable, x>(this) {
            
            });
      } 
    }
  }
  
  public static final class null implements f.b {
    null(RnBridgeModule.b param1b) {}
    
    public final void a(int param1Int, String param1String) {
      l.b(param1String, "message");
      WritableMap writableMap = Arguments.createMap();
      writableMap.putInt("code", param1Int);
      writableMap.putString("message", param1String);
      this.a.d.invoke(new Object[] { writableMap });
    }
    
    public final void a(JSONObject param1JSONObject) {
      l.b(param1JSONObject, "data");
      try {
        this.a.d.invoke(new Object[] { com.bytedance.ies.bullet.kit.rn.d.b.a(param1JSONObject) });
        return;
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
        return;
      } 
    }
  }
  
  static final class null extends m implements d.f.a.b<Throwable, x> {
    null(RnBridgeModule.b param1b) {
      super(1);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\RnBridgeModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */