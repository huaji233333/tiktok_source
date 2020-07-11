package com.facebook.react.uimanager;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.util.ReactFindViewUtil;

public abstract class BaseViewManager<T extends View, C extends LayoutShadowNode> extends ViewManager<T, C> {
  private static MatrixMathHelper.MatrixDecompositionContext sMatrixDecompositionContext = new MatrixMathHelper.MatrixDecompositionContext();
  
  private static double[] sTransformDecompositionArray = new double[16];
  
  private static void resetTransformProperty(View paramView) {
    paramView.setTranslationX(PixelUtil.toPixelFromDIP(0.0F));
    paramView.setTranslationY(PixelUtil.toPixelFromDIP(0.0F));
    paramView.setRotation(0.0F);
    paramView.setRotationX(0.0F);
    paramView.setRotationY(0.0F);
    paramView.setScaleX(1.0F);
    paramView.setScaleY(1.0F);
    paramView.setCameraDistance(0.0F);
  }
  
  private static void setTransformProperty(View paramView, ReadableArray paramReadableArray) {
    TransformHelper.processTransform(paramReadableArray, sTransformDecompositionArray);
    MatrixMathHelper.decomposeMatrix(sTransformDecompositionArray, sMatrixDecompositionContext);
    paramView.setTranslationX(PixelUtil.toPixelFromDIP((float)sMatrixDecompositionContext.translation[0]));
    paramView.setTranslationY(PixelUtil.toPixelFromDIP((float)sMatrixDecompositionContext.translation[1]));
    paramView.setRotation((float)sMatrixDecompositionContext.rotationDegrees[2]);
    paramView.setRotationX((float)sMatrixDecompositionContext.rotationDegrees[0]);
    paramView.setRotationY((float)sMatrixDecompositionContext.rotationDegrees[1]);
    paramView.setScaleX((float)sMatrixDecompositionContext.scale[0]);
    paramView.setScaleY((float)sMatrixDecompositionContext.scale[1]);
    double[] arrayOfDouble = sMatrixDecompositionContext.perspective;
    if (arrayOfDouble.length > 2) {
      float f2 = (float)arrayOfDouble[2];
      float f1 = f2;
      if (f2 == 0.0F)
        f1 = 7.8125E-4F; 
      f1 = -1.0F / f1;
      paramView.setCameraDistance((DisplayMetricsHolder.getScreenDisplayMetrics()).density * f1 * 5.0F);
    } 
  }
  
  @ReactProp(name = "accessibilityComponentType")
  public void setAccessibilityComponentType(T paramT, String paramString) {
    AccessibilityHelper.updateAccessibilityComponentType((View)paramT, paramString);
  }
  
  @ReactProp(name = "accessibilityLabel")
  public void setAccessibilityLabel(T paramT, String paramString) {
    paramT.setContentDescription(paramString);
  }
  
  @ReactProp(name = "accessibilityLiveRegion")
  public void setAccessibilityLiveRegion(T paramT, String paramString) {
    if (Build.VERSION.SDK_INT >= 19) {
      if (paramString == null || paramString.equals("none")) {
        paramT.setAccessibilityLiveRegion(0);
        return;
      } 
      if (paramString.equals("polite")) {
        paramT.setAccessibilityLiveRegion(1);
        return;
      } 
      if (paramString.equals("assertive")) {
        paramT.setAccessibilityLiveRegion(2);
        return;
      } 
    } 
  }
  
  @ReactProp(customType = "Color", defaultInt = 0, name = "backgroundColor")
  public void setBackgroundColor(T paramT, int paramInt) {
    paramT.setBackgroundColor(paramInt);
  }
  
  @ReactProp(name = "elevation")
  public void setElevation(T paramT, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      paramT.setElevation(PixelUtil.toPixelFromDIP(paramFloat)); 
  }
  
  @ReactProp(name = "importantForAccessibility")
  public void setImportantForAccessibility(T paramT, String paramString) {
    if (paramString == null || paramString.equals("auto")) {
      paramT.setImportantForAccessibility(0);
      return;
    } 
    if (paramString.equals("yes")) {
      paramT.setImportantForAccessibility(1);
      return;
    } 
    if (paramString.equals("no")) {
      paramT.setImportantForAccessibility(2);
      return;
    } 
    if (paramString.equals("no-hide-descendants"))
      paramT.setImportantForAccessibility(4); 
  }
  
  @ReactProp(name = "nativeID")
  public void setNativeId(T paramT, String paramString) {
    paramT.setTag(1979973671, paramString);
    ReactFindViewUtil.notifyViewRendered((View)paramT);
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "opacity")
  public void setOpacity(T paramT, float paramFloat) {
    paramT.setAlpha(paramFloat);
  }
  
  @ReactProp(name = "renderToHardwareTextureAndroid")
  public void setRenderToHardwareTexture(T paramT, boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    paramT.setLayerType(bool, null);
  }
  
  @ReactProp(name = "rotation")
  @Deprecated
  public void setRotation(T paramT, float paramFloat) {
    paramT.setRotation(paramFloat);
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "scaleX")
  @Deprecated
  public void setScaleX(T paramT, float paramFloat) {
    paramT.setScaleX(paramFloat);
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "scaleY")
  @Deprecated
  public void setScaleY(T paramT, float paramFloat) {
    paramT.setScaleY(paramFloat);
  }
  
  @ReactProp(name = "testID")
  public void setTestId(T paramT, String paramString) {
    paramT.setTag(1979973653, paramString);
    paramT.setTag(paramString);
  }
  
  @ReactProp(name = "transform")
  public void setTransform(T paramT, ReadableArray paramReadableArray) {
    if (paramReadableArray == null) {
      resetTransformProperty((View)paramT);
      return;
    } 
    setTransformProperty((View)paramT, paramReadableArray);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "translateX")
  @Deprecated
  public void setTranslateX(T paramT, float paramFloat) {
    paramT.setTranslationX(PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "translateY")
  @Deprecated
  public void setTranslateY(T paramT, float paramFloat) {
    paramT.setTranslationY(PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(name = "zIndex")
  public void setZIndex(T paramT, float paramFloat) {
    ViewGroupManager.setViewZIndex((View)paramT, Math.round(paramFloat));
    ViewParent viewParent = paramT.getParent();
    if (viewParent != null && viewParent instanceof ReactZIndexedViewGroup)
      ((ReactZIndexedViewGroup)viewParent).updateDrawingOrder(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\BaseViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */