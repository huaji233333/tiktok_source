package com.facebook.react.flat;

import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

class FlatReactModalShadowNode extends FlatShadowNode implements AndroidView {
  private final Point mMaxPoint = new Point();
  
  private final Point mMinPoint = new Point();
  
  private boolean mPaddingChanged;
  
  FlatReactModalShadowNode() {
    forceMountToView();
    forceMountChildrenToView();
  }
  
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    int i;
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    Display display = ((WindowManager)getThemedContext().getSystemService("window")).getDefaultDisplay();
    display.getCurrentSizeRange(this.mMinPoint, this.mMaxPoint);
    paramInt = display.getRotation();
    if (paramInt == 0 || paramInt == 2) {
      i = this.mMinPoint.x;
      paramInt = this.mMaxPoint.y;
    } else {
      i = this.mMaxPoint.x;
      paramInt = this.mMinPoint.y;
    } 
    paramReactShadowNodeImpl.setStyleWidth(i);
    paramReactShadowNodeImpl.setStyleHeight(paramInt);
  }
  
  public boolean isPaddingChanged() {
    return this.mPaddingChanged;
  }
  
  public boolean needsCustomLayoutForChildren() {
    return false;
  }
  
  public void resetPaddingChanged() {
    this.mPaddingChanged = false;
  }
  
  public void setPadding(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.POINT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
  
  public void setPaddingPercent(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.PERCENT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatReactModalShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */