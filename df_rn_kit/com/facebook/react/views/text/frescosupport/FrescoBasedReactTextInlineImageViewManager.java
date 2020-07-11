package com.facebook.react.views.text.frescosupport;

import android.view.View;
import com.facebook.drawee.a.a.c;
import com.facebook.drawee.a.a.e;
import com.facebook.drawee.c.b;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

@ReactModule(name = "RCTTextInlineImage")
public class FrescoBasedReactTextInlineImageViewManager extends ViewManager<View, FrescoBasedReactTextInlineImageShadowNode> {
  private final Object mCallerContext;
  
  private final b mDraweeControllerBuilder;
  
  public FrescoBasedReactTextInlineImageViewManager() {
    this(null, null);
  }
  
  public FrescoBasedReactTextInlineImageViewManager(b paramb, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mCallerContext = paramObject;
  }
  
  public FrescoBasedReactTextInlineImageShadowNode createShadowNodeInstance() {
    e e;
    b b1 = this.mDraweeControllerBuilder;
    if (b1 == null)
      e = c.a(); 
    return new FrescoBasedReactTextInlineImageShadowNode((b)e, this.mCallerContext);
  }
  
  public View createViewInstance(ThemedReactContext paramThemedReactContext) {
    throw new IllegalStateException("RCTTextInlineImage doesn't map into a native view");
  }
  
  public String getName() {
    return "RCTTextInlineImage";
  }
  
  public Class<FrescoBasedReactTextInlineImageShadowNode> getShadowNodeClass() {
    return FrescoBasedReactTextInlineImageShadowNode.class;
  }
  
  public void updateExtraData(View paramView, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\frescosupport\FrescoBasedReactTextInlineImageViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */