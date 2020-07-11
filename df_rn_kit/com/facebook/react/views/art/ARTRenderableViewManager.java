package com.facebook.react.views.art;

import android.view.View;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

public class ARTRenderableViewManager extends ViewManager<View, ReactShadowNode> {
  private final String mClassName;
  
  ARTRenderableViewManager(String paramString) {
    this.mClassName = paramString;
  }
  
  public static ARTRenderableViewManager createARTGroupViewManager() {
    return new ARTGroupViewManager();
  }
  
  public static ARTRenderableViewManager createARTShapeViewManager() {
    return new ARTShapeViewManager();
  }
  
  public static ARTRenderableViewManager createARTTextViewManager() {
    return new ARTTextViewManager();
  }
  
  public ReactShadowNode createShadowNodeInstance() {
    if ("ARTGroup".equals(this.mClassName))
      return (ReactShadowNode)new ARTGroupShadowNode(); 
    if ("ARTShape".equals(this.mClassName))
      return (ReactShadowNode)new ARTShapeShadowNode(); 
    if ("ARTText".equals(this.mClassName))
      return (ReactShadowNode)new ARTTextShadowNode(); 
    StringBuilder stringBuilder = new StringBuilder("Unexpected type ");
    stringBuilder.append(this.mClassName);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public View createViewInstance(ThemedReactContext paramThemedReactContext) {
    throw new IllegalStateException("ARTShape does not map into a native view");
  }
  
  public String getName() {
    return this.mClassName;
  }
  
  public Class<? extends ReactShadowNode> getShadowNodeClass() {
    if ("ARTGroup".equals(this.mClassName))
      return (Class)ARTGroupShadowNode.class; 
    if ("ARTShape".equals(this.mClassName))
      return (Class)ARTShapeShadowNode.class; 
    if ("ARTText".equals(this.mClassName))
      return (Class)ARTTextShadowNode.class; 
    StringBuilder stringBuilder = new StringBuilder("Unexpected type ");
    stringBuilder.append(this.mClassName);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void updateExtraData(View paramView, Object paramObject) {
    throw new IllegalStateException("ARTShape does not map into a native view");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTRenderableViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */