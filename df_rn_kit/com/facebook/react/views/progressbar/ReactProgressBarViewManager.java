package com.facebook.react.views.progressbar;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

@ReactModule(name = "AndroidProgressBar")
public class ReactProgressBarViewManager extends BaseViewManager<ProgressBarContainerView, ProgressBarShadowNode> {
  private static Object sProgressBarCtorLock = new Object();
  
  public static ProgressBar createProgressBar(Context paramContext, int paramInt) {
    synchronized (sProgressBarCtorLock) {
      return new ProgressBar(paramContext, null, paramInt);
    } 
  }
  
  static int getStyleFromString(String paramString) {
    if (paramString != null) {
      if (paramString.equals("Horizontal"))
        return 16842872; 
      if (paramString.equals("Small"))
        return 16842873; 
      if (paramString.equals("Large"))
        return 16842874; 
      if (paramString.equals("Inverse"))
        return 16843399; 
      if (paramString.equals("SmallInverse"))
        return 16843400; 
      if (paramString.equals("LargeInverse"))
        return 16843401; 
      if (paramString.equals("Normal"))
        return 16842871; 
      StringBuilder stringBuilder = new StringBuilder("Unknown ProgressBar style: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    throw new JSApplicationIllegalArgumentException("ProgressBar needs to have a style, null received");
  }
  
  public ProgressBarShadowNode createShadowNodeInstance() {
    return new ProgressBarShadowNode();
  }
  
  protected ProgressBarContainerView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ProgressBarContainerView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "AndroidProgressBar";
  }
  
  public Class<ProgressBarShadowNode> getShadowNodeClass() {
    return ProgressBarShadowNode.class;
  }
  
  protected void onAfterUpdateTransaction(ProgressBarContainerView paramProgressBarContainerView) {
    paramProgressBarContainerView.apply();
  }
  
  @ReactProp(name = "animating")
  public void setAnimating(ProgressBarContainerView paramProgressBarContainerView, boolean paramBoolean) {
    paramProgressBarContainerView.setAnimating(paramBoolean);
  }
  
  @ReactProp(customType = "Color", name = "color")
  public void setColor(ProgressBarContainerView paramProgressBarContainerView, Integer paramInteger) {
    paramProgressBarContainerView.setColor(paramInteger);
  }
  
  @ReactProp(name = "indeterminate")
  public void setIndeterminate(ProgressBarContainerView paramProgressBarContainerView, boolean paramBoolean) {
    paramProgressBarContainerView.setIndeterminate(paramBoolean);
  }
  
  @ReactProp(name = "progress")
  public void setProgress(ProgressBarContainerView paramProgressBarContainerView, double paramDouble) {
    paramProgressBarContainerView.setProgress(paramDouble);
  }
  
  @ReactProp(name = "styleAttr")
  public void setStyle(ProgressBarContainerView paramProgressBarContainerView, String paramString) {
    paramProgressBarContainerView.setStyle(paramString);
  }
  
  public void updateExtraData(ProgressBarContainerView paramProgressBarContainerView, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\progressbar\ReactProgressBarViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */