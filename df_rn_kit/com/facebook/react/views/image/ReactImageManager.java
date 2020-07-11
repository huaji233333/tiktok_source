package com.facebook.react.views.image;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import com.facebook.drawee.a.a.c;
import com.facebook.drawee.c.b;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.a;
import java.util.Map;

@ReactModule(name = "RCTImageView")
public class ReactImageManager extends SimpleViewManager<ReactImageView> {
  private final Object mCallerContext;
  
  private b mDraweeControllerBuilder;
  
  private GlobalImageLoadListener mGlobalImageLoadListener;
  
  public ReactImageManager() {}
  
  public ReactImageManager(b paramb, GlobalImageLoadListener paramGlobalImageLoadListener, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mGlobalImageLoadListener = paramGlobalImageLoadListener;
    this.mCallerContext = paramObject;
  }
  
  public ReactImageManager(b paramb, Object paramObject) {
    this(paramb, null, paramObject);
  }
  
  public ReactImageView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactImageView((Context)paramThemedReactContext, getDraweeControllerBuilder(), this.mGlobalImageLoadListener, getCallerContext());
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
    return MapBuilder.of(ImageLoadEvent.eventNameForType(4), MapBuilder.of("registrationName", "onLoadStart"), ImageLoadEvent.eventNameForType(2), MapBuilder.of("registrationName", "onLoad"), ImageLoadEvent.eventNameForType(1), MapBuilder.of("registrationName", "onError"), ImageLoadEvent.eventNameForType(3), MapBuilder.of("registrationName", "onLoadEnd"));
  }
  
  public String getName() {
    return "RCTImageView";
  }
  
  protected void onAfterUpdateTransaction(ReactImageView paramReactImageView) {
    super.onAfterUpdateTransaction((View)paramReactImageView);
    paramReactImageView.maybeUpdateView();
  }
  
  @ReactProp(name = "blurRadius")
  public void setBlurRadius(ReactImageView paramReactImageView, float paramFloat) {
    paramReactImageView.setBlurRadius(paramFloat);
  }
  
  @ReactProp(customType = "Color", name = "borderColor")
  public void setBorderColor(ReactImageView paramReactImageView, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactImageView.setBorderColor(0);
      return;
    } 
    paramReactImageView.setBorderColor(paramInteger.intValue());
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
  public void setBorderRadius(ReactImageView paramReactImageView, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    if (paramInt == 0) {
      paramReactImageView.setBorderRadius(f);
      return;
    } 
    paramReactImageView.setBorderRadius(f, paramInt - 1);
  }
  
  @ReactProp(name = "borderWidth")
  public void setBorderWidth(ReactImageView paramReactImageView, float paramFloat) {
    paramReactImageView.setBorderWidth(paramFloat);
  }
  
  @ReactProp(name = "fadeDuration")
  public void setFadeDuration(ReactImageView paramReactImageView, int paramInt) {
    paramReactImageView.setFadeDuration(paramInt);
  }
  
  @ReactProp(name = "headers")
  public void setHeaders(ReactImageView paramReactImageView, ReadableMap paramReadableMap) {
    paramReactImageView.setHeaders(paramReadableMap);
  }
  
  @ReactProp(name = "shouldNotifyLoadEvents")
  public void setLoadHandlersRegistered(ReactImageView paramReactImageView, boolean paramBoolean) {
    paramReactImageView.setShouldNotifyLoadEvents(paramBoolean);
  }
  
  @ReactProp(name = "loadingIndicatorSrc")
  public void setLoadingIndicatorSource(ReactImageView paramReactImageView, String paramString) {
    paramReactImageView.setLoadingIndicatorSource(paramString);
  }
  
  @ReactProp(name = "overlayColor")
  public void setOverlayColor(ReactImageView paramReactImageView, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactImageView.setOverlayColor(0);
      return;
    } 
    paramReactImageView.setOverlayColor(paramInteger.intValue());
  }
  
  @ReactProp(name = "progressiveRenderingEnabled")
  public void setProgressiveRenderingEnabled(ReactImageView paramReactImageView, boolean paramBoolean) {
    paramReactImageView.setProgressiveRenderingEnabled(paramBoolean);
  }
  
  @ReactProp(name = "resizeMethod")
  public void setResizeMethod(ReactImageView paramReactImageView, String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      paramReactImageView.setResizeMethod(ImageResizeMethod.AUTO);
      return;
    } 
    if ("resize".equals(paramString)) {
      paramReactImageView.setResizeMethod(ImageResizeMethod.RESIZE);
      return;
    } 
    if ("scale".equals(paramString)) {
      paramReactImageView.setResizeMethod(ImageResizeMethod.SCALE);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid resize method: '");
    stringBuilder.append(paramString);
    stringBuilder.append("'");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(ReactImageView paramReactImageView, String paramString) {
    paramReactImageView.setScaleType(ImageResizeMode.toScaleType(paramString));
  }
  
  @ReactProp(name = "src")
  public void setSource(ReactImageView paramReactImageView, ReadableArray paramReadableArray) {
    paramReactImageView.setSource(paramReadableArray);
  }
  
  @ReactProp(customType = "Color", name = "tintColor")
  public void setTintColor(ReactImageView paramReactImageView, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactImageView.clearColorFilter();
      return;
    } 
    paramReactImageView.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\image\ReactImageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */