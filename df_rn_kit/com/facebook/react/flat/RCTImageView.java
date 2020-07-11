package com.facebook.react.flat;

import android.content.Context;
import com.facebook.drawee.e.q;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;

class RCTImageView<T extends AbstractDrawCommand & DrawImage> extends FlatShadowNode {
  static Object sCallerContext = RCTImageView.class;
  
  private T mDrawImage;
  
  RCTImageView(T paramT) {
    this.mDrawImage = paramT;
  }
  
  static Object getCallerContext() {
    return sCallerContext;
  }
  
  private T getMutableDrawImage() {
    if (this.mDrawImage.isFrozen()) {
      this.mDrawImage = (T)this.mDrawImage.mutableCopy();
      invalidate();
    } 
    return this.mDrawImage;
  }
  
  static void setCallerContext(Object paramObject) {
    sCallerContext = paramObject;
  }
  
  protected void collectState(StateBuilder paramStateBuilder, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    super.collectState(paramStateBuilder, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    if (((DrawImage)this.mDrawImage).hasImageRequest()) {
      this.mDrawImage = (T)this.mDrawImage.updateBoundsAndFreeze(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      paramStateBuilder.addDrawCommand((AbstractDrawCommand)this.mDrawImage);
      paramStateBuilder.addAttachDetachListener((AttachDetachListener)this.mDrawImage);
    } 
  }
  
  boolean doesDraw() {
    return (((DrawImage)this.mDrawImage).hasImageRequest() || super.doesDraw());
  }
  
  public void setBorder(int paramInt, float paramFloat) {
    super.setBorder(paramInt, paramFloat);
    if (paramInt == 8 && ((DrawImage)this.mDrawImage).getBorderWidth() != paramFloat)
      ((DrawImage)getMutableDrawImage()).setBorderWidth(paramFloat); 
  }
  
  @ReactProp(customType = "Color", name = "borderColor")
  public void setBorderColor(int paramInt) {
    if (((DrawImage)this.mDrawImage).getBorderColor() != paramInt)
      ((DrawImage)getMutableDrawImage()).setBorderColor(paramInt); 
  }
  
  @ReactProp(name = "borderRadius")
  public void setBorderRadius(float paramFloat) {
    if (((DrawImage)this.mDrawImage).getBorderRadius() != paramFloat)
      ((DrawImage)getMutableDrawImage()).setBorderRadius(PixelUtil.toPixelFromDIP(paramFloat)); 
  }
  
  @ReactProp(name = "fadeDuration")
  public void setFadeDuration(int paramInt) {
    ((DrawImage)getMutableDrawImage()).setFadeDuration(paramInt);
  }
  
  @ReactProp(name = "progressiveRenderingEnabled")
  public void setProgressiveRenderingEnabled(boolean paramBoolean) {
    ((DrawImage)getMutableDrawImage()).setProgressiveRenderingEnabled(paramBoolean);
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(String paramString) {
    q.b b = ImageResizeMode.toScaleType(paramString);
    if (((DrawImage)this.mDrawImage).getScaleType() != b)
      ((DrawImage)getMutableDrawImage()).setScaleType(b); 
  }
  
  @ReactProp(name = "shouldNotifyLoadEvents")
  public void setShouldNotifyLoadEvents(boolean paramBoolean) {
    boolean bool;
    DrawImage drawImage = (DrawImage)getMutableDrawImage();
    if (paramBoolean) {
      bool = getReactTag();
    } else {
      bool = false;
    } 
    drawImage.setReactTag(bool);
  }
  
  @ReactProp(name = "src")
  public void setSource(ReadableArray paramReadableArray) {
    ((DrawImage)getMutableDrawImage()).setSource((Context)getThemedContext(), paramReadableArray);
  }
  
  @ReactProp(name = "tintColor")
  public void setTintColor(int paramInt) {
    ((DrawImage)getMutableDrawImage()).setTintColor(paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */