package com.bytedance.ies.bullet.kit.rn.pkg.animation;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import com.facebook.drawee.a.a.c;
import com.facebook.drawee.c.b;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.image.ImageLoadEvent;
import com.facebook.react.views.image.ImageResizeMethod;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.yoga.a;
import java.util.Arrays;
import java.util.Map;

public class AnimatedReactImageManager extends SimpleViewManager<a> {
  private final Object mCallerContext;
  
  private b mDraweeControllerBuilder;
  
  AnimatedReactImageManager() {}
  
  public AnimatedReactImageManager(b paramb, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mCallerContext = paramObject;
  }
  
  private Object getCallerContext() {
    return this.mCallerContext;
  }
  
  private b getDraweeControllerBuilder() {
    if (this.mDraweeControllerBuilder == null)
      this.mDraweeControllerBuilder = (b)c.a(); 
    return this.mDraweeControllerBuilder;
  }
  
  public a createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new a((Context)paramThemedReactContext, getDraweeControllerBuilder(), getCallerContext());
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("start", Integer.valueOf(1), "stop", Integer.valueOf(2));
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(ImageLoadEvent.eventNameForType(4), MapBuilder.of("registrationName", "onLoadStart"), ImageLoadEvent.eventNameForType(2), MapBuilder.of("registrationName", "onLoad"), ImageLoadEvent.eventNameForType(3), MapBuilder.of("registrationName", "onLoadEnd"));
  }
  
  public String getName() {
    return "RCTAnimatedImageView";
  }
  
  protected void onAfterUpdateTransaction(a parama) {
    super.onAfterUpdateTransaction((View)parama);
    parama.b();
  }
  
  public void receiveCommand(a parama, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      parama.setShouldPlay(false);
      return;
    } 
    parama.setShouldPlay(true);
  }
  
  @ReactProp(customType = "Color", name = "borderColor")
  public void setBorderColor(a parama, Integer paramInteger) {
    if (paramInteger == null) {
      parama.setBorderColor(0);
      return;
    } 
    parama.setBorderColor(paramInteger.intValue());
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
  public void setBorderRadius(a parama, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    if (paramInt == 0) {
      parama.setBorderRadius(f);
      return;
    } 
    paramInt--;
    if (parama.e == null) {
      parama.e = new float[4];
      Arrays.fill(parama.e, 1.0E21F);
    } 
    if (!FloatUtil.floatsEqual(parama.e[paramInt], f)) {
      parama.e[paramInt] = f;
      parama.g = true;
    } 
  }
  
  @ReactProp(name = "borderWidth")
  public void setBorderWidth(a parama, float paramFloat) {
    parama.setBorderWidth(paramFloat);
  }
  
  @ReactProp(name = "fadeDuration")
  public void setFadeDuration(a parama, int paramInt) {
    parama.setFadeDuration(paramInt);
  }
  
  @ReactProp(name = "shouldNotifyLoadEvents")
  public void setLoadHandlersRegistered(a parama, boolean paramBoolean) {
    parama.setShouldNotifyLoadEvents(paramBoolean);
  }
  
  @ReactProp(name = "loadingIndicatorSrc")
  public void setLoadingIndicatorSource(a parama, String paramString) {
    parama.setLoadingIndicatorSource(paramString);
  }
  
  @ReactProp(name = "overlayColor")
  public void setOverlayColor(a parama, Integer paramInteger) {
    if (paramInteger == null) {
      parama.setOverlayColor(0);
      return;
    } 
    parama.setOverlayColor(paramInteger.intValue());
  }
  
  @ReactProp(name = "progressiveRenderingEnabled")
  public void setProgressiveRenderingEnabled(a parama, boolean paramBoolean) {
    parama.setProgressiveRenderingEnabled(paramBoolean);
  }
  
  @ReactProp(name = "resizeMethod")
  public void setResizeMethod(a parama, String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      parama.setResizeMethod(ImageResizeMethod.AUTO);
      return;
    } 
    if ("resize".equals(paramString)) {
      parama.setResizeMethod(ImageResizeMethod.RESIZE);
      return;
    } 
    if ("scale".equals(paramString)) {
      parama.setResizeMethod(ImageResizeMethod.SCALE);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid resize method: '");
    stringBuilder.append(paramString);
    stringBuilder.append("'");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(a parama, String paramString) {
    parama.setScaleType(ImageResizeMode.toScaleType(paramString));
  }
  
  @ReactProp(name = "shouldPlay")
  public void setShouldPlay(a parama, boolean paramBoolean) {
    parama.setShouldPlay(paramBoolean);
  }
  
  @ReactProp(name = "src")
  public void setSource(a parama, ReadableArray paramReadableArray) {
    parama.setSource(paramReadableArray);
  }
  
  @ReactProp(customType = "Color", name = "tintColor")
  public void setTintColor(a parama, Integer paramInteger) {
    if (paramInteger == null) {
      parama.clearColorFilter();
      return;
    } 
    parama.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\animation\AnimatedReactImageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */