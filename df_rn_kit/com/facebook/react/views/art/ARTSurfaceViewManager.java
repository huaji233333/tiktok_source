package com.facebook.react.views.art;

import android.content.Context;
import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;

@ReactModule(name = "ARTSurfaceView")
public class ARTSurfaceViewManager extends BaseViewManager<ARTSurfaceView, ARTSurfaceViewShadowNode> {
  private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
      public final long measure(YogaNode param1YogaNode, float param1Float1, YogaMeasureMode param1YogaMeasureMode1, float param1Float2, YogaMeasureMode param1YogaMeasureMode2) {
        throw new IllegalStateException("SurfaceView should have explicit width and height set");
      }
    };
  
  public ARTSurfaceViewShadowNode createShadowNodeInstance() {
    ARTSurfaceViewShadowNode aRTSurfaceViewShadowNode = new ARTSurfaceViewShadowNode();
    aRTSurfaceViewShadowNode.setMeasureFunction(MEASURE_FUNCTION);
    return aRTSurfaceViewShadowNode;
  }
  
  protected ARTSurfaceView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ARTSurfaceView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "ARTSurfaceView";
  }
  
  public Class<ARTSurfaceViewShadowNode> getShadowNodeClass() {
    return ARTSurfaceViewShadowNode.class;
  }
  
  public void setBackgroundColor(ARTSurfaceView paramARTSurfaceView, int paramInt) {}
  
  public void updateExtraData(ARTSurfaceView paramARTSurfaceView, Object paramObject) {
    paramARTSurfaceView.setSurfaceTextureListener((ARTSurfaceViewShadowNode)paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTSurfaceViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */