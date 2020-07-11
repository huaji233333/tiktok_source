package com.bytedance.ies.bullet.kit.rn.pkg.fastimage;

import android.app.Activity;
import com.facebook.drawee.a.a.c;
import com.facebook.imagepipeline.o.b;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class FastImageViewModule extends ReactContextBaseJavaModule {
  FastImageViewModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public String getName() {
    return "FastImageView";
  }
  
  @ReactMethod
  public void preload(ReadableArray paramReadableArray) {
    Activity activity = getCurrentActivity();
    if (activity == null)
      return; 
    activity.runOnUiThread(new Runnable(this, paramReadableArray, activity) {
          public final void run() {
            for (int i = 0; i < this.a.size(); i++) {
              ReadableMap readableMap = this.a.getMap(i);
              c.c().d(b.fromUri(readableMap.getString("uri")), this.b.getApplicationContext());
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\fastimage\FastImageViewModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */