package com.facebook.react.flat;

import android.content.Context;
import android.view.View;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.art.ARTSurfaceView;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;

public class FlatARTSurfaceViewManager extends BaseViewManager<ARTSurfaceView, FlatARTSurfaceViewShadowNode> {
  private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
      public final long measure(YogaNode param1YogaNode, float param1Float1, YogaMeasureMode param1YogaMeasureMode1, float param1Float2, YogaMeasureMode param1YogaMeasureMode2) {
        throw new IllegalStateException("SurfaceView should have explicit width and height set");
      }
    };
  
  public FlatARTSurfaceViewShadowNode createShadowNodeInstance() {
    FlatARTSurfaceViewShadowNode flatARTSurfaceViewShadowNode = new FlatARTSurfaceViewShadowNode();
    flatARTSurfaceViewShadowNode.setMeasureFunction(MEASURE_FUNCTION);
    return flatARTSurfaceViewShadowNode;
  }
  
  protected ARTSurfaceView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ARTSurfaceView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "ARTSurfaceView";
  }
  
  public Class<FlatARTSurfaceViewShadowNode> getShadowNodeClass() {
    return FlatARTSurfaceViewShadowNode.class;
  }
  
  public void updateExtraData(ARTSurfaceView paramARTSurfaceView, Object paramObject) {
    paramARTSurfaceView.setSurfaceTextureListener((FlatARTSurfaceViewShadowNode)paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatARTSurfaceViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */