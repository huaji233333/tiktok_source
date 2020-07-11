package com.bytedance.ies.bullet.kit.rn.pkg.fastimage;

import android.content.Context;
import android.view.View;
import com.facebook.drawee.a.a.c;
import com.facebook.drawee.c.b;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageResizeMode;
import java.util.Map;

@ReactModule(name = "FastImageView")
public class TTReactImageManager extends SimpleViewManager<d> {
  private final Object mCallerContext;
  
  private b mDraweeControllerBuilder;
  
  private GlobalImageLoadListener mGlobalImageLoadListener;
  
  public TTReactImageManager() {}
  
  public TTReactImageManager(b paramb, GlobalImageLoadListener paramGlobalImageLoadListener, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mGlobalImageLoadListener = paramGlobalImageLoadListener;
    this.mCallerContext = paramObject;
  }
  
  public TTReactImageManager(b paramb, Object paramObject) {
    this(paramb, null, paramObject);
  }
  
  public d createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new d((Context)paramThemedReactContext, getDraweeControllerBuilder(), this.mGlobalImageLoadListener, getCallerContext());
  }
  
  public Object getCallerContext() {
    return this.mCallerContext;
  }
  
  public b getDraweeControllerBuilder() {
    if (this.mDraweeControllerBuilder == null)
      this.mDraweeControllerBuilder = (b)c.a(); 
    return this.mDraweeControllerBuilder;
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder().put("onFastImageLoadStart", MapBuilder.of("registrationName", "onFastImageLoadStart")).put("onFastImageProgress", MapBuilder.of("registrationName", "onFastImageProgress")).put("onFastImageLoad", MapBuilder.of("registrationName", "onFastImageLoad")).put("onFastImageError", MapBuilder.of("registrationName", "onFastImageError")).put("onFastImageLoadEnd", MapBuilder.of("registrationName", "onFastImageLoadEnd")).build();
  }
  
  public String getName() {
    return "FastImageView";
  }
  
  protected void onAfterUpdateTransaction(d paramd) {
    super.onAfterUpdateTransaction((View)paramd);
    paramd.a();
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(d paramd, String paramString) {
    paramd.setScaleType(ImageResizeMode.toScaleType(paramString));
  }
  
  @ReactProp(name = "source")
  public void setSource(d paramd, ReadableMap paramReadableMap) {
    paramd.setSource(paramReadableMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\fastimage\TTReactImageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */