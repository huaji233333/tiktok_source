package com.facebook.react.modules.fresco;

import com.facebook.common.e.a;
import com.facebook.drawee.a.a.c;
import com.facebook.imagepipeline.b.a.a;
import com.facebook.imagepipeline.e.h;
import com.facebook.imagepipeline.e.i;
import com.facebook.imagepipeline.n.ai;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.common.ModuleDataCleaner;
import com.facebook.react.modules.network.CookieJarContainer;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import com.facebook.react.modules.network.OkHttpClientProvider;
import java.net.CookieHandler;
import java.util.HashSet;
import okhttp3.m;
import okhttp3.v;
import okhttp3.y;

@ReactModule(name = "FrescoModule")
public class FrescoModule extends ReactContextBaseJavaModule implements LifecycleEventListener, ModuleDataCleaner.Cleanable {
  private static boolean sHasBeenInitialized;
  
  private final boolean mClearOnDestroy;
  
  private i mConfig;
  
  public FrescoModule(ReactApplicationContext paramReactApplicationContext) {
    this(paramReactApplicationContext, true, null);
  }
  
  public FrescoModule(ReactApplicationContext paramReactApplicationContext, boolean paramBoolean) {
    this(paramReactApplicationContext, paramBoolean, null);
  }
  
  public FrescoModule(ReactApplicationContext paramReactApplicationContext, boolean paramBoolean, i parami) {
    super(paramReactApplicationContext);
    this.mClearOnDestroy = paramBoolean;
    this.mConfig = parami;
  }
  
  private static i getDefaultConfig(ReactContext paramReactContext) {
    return getDefaultConfigBuilder(paramReactContext).a();
  }
  
  public static i.a getDefaultConfigBuilder(ReactContext paramReactContext) {
    HashSet<SystraceRequestListener> hashSet = new HashSet();
    hashSet.add(new SystraceRequestListener());
    y y = OkHttpClientProvider.createClient();
    ((CookieJarContainer)y.k).setCookieJar((m)new v((CookieHandler)new ForwardingCookieHandler(paramReactContext)));
    return a.a(paramReactContext.getApplicationContext(), y).a((ai)new ReactOkHttpNetworkFetcher(y)).a(false).a(hashSet);
  }
  
  public static boolean hasBeenInitialized() {
    return sHasBeenInitialized;
  }
  
  public void clearSensitiveData() {
    h h = c.c();
    h.a();
    h.b.a();
    h.c.a();
  }
  
  public String getName() {
    return "FrescoModule";
  }
  
  public void initialize() {
    super.initialize();
    getReactApplicationContext().addLifecycleEventListener(this);
    if (!hasBeenInitialized()) {
      if (this.mConfig == null)
        this.mConfig = getDefaultConfig((ReactContext)getReactApplicationContext()); 
      c.a(getReactApplicationContext().getApplicationContext(), this.mConfig);
      sHasBeenInitialized = true;
    } else if (this.mConfig != null) {
      a.b("ReactNative", "Fresco has already been initialized with a different config. The new Fresco configuration will be ignored!");
    } 
    this.mConfig = null;
  }
  
  public void onHostDestroy() {
    if (hasBeenInitialized() && this.mClearOnDestroy)
      c.c().a(); 
  }
  
  public void onHostPause() {}
  
  public void onHostResume() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\fresco\FrescoModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */