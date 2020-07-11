package com.facebook.react.views.progressbar;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ProgressBar;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.b;
import java.util.HashSet;
import java.util.Set;

public class ProgressBarShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
  private final SparseIntArray mHeight;
  
  private final Set<Integer> mMeasured;
  
  private String mStyle = "Normal";
  
  private final SparseIntArray mWidth;
  
  public ProgressBarShadowNode() {
    this.mHeight = new SparseIntArray();
    this.mWidth = new SparseIntArray();
    this.mMeasured = new HashSet<Integer>();
    setMeasureFunction(this);
  }
  
  public ProgressBarShadowNode(ProgressBarShadowNode paramProgressBarShadowNode) {
    super(paramProgressBarShadowNode);
    this.mWidth = paramProgressBarShadowNode.mWidth.clone();
    this.mHeight = paramProgressBarShadowNode.mHeight.clone();
    this.mMeasured = new HashSet<Integer>(paramProgressBarShadowNode.mMeasured);
    setMeasureFunction(this);
  }
  
  public String getStyle() {
    return this.mStyle;
  }
  
  public long measure(YogaNode paramYogaNode, float paramFloat1, YogaMeasureMode paramYogaMeasureMode1, float paramFloat2, YogaMeasureMode paramYogaMeasureMode2) {
    int i = ReactProgressBarViewManager.getStyleFromString(getStyle());
    if (!this.mMeasured.contains(Integer.valueOf(i))) {
      ProgressBar progressBar = ReactProgressBarViewManager.createProgressBar((Context)getThemedContext(), i);
      int j = View.MeasureSpec.makeMeasureSpec(-2, 0);
      progressBar.measure(j, j);
      this.mHeight.put(i, progressBar.getMeasuredHeight());
      this.mWidth.put(i, progressBar.getMeasuredWidth());
      this.mMeasured.add(Integer.valueOf(i));
    } 
    return b.a(this.mWidth.get(i), this.mHeight.get(i));
  }
  
  public ProgressBarShadowNode mutableCopy() {
    return new ProgressBarShadowNode(this);
  }
  
  @ReactProp(name = "styleAttr")
  public void setStyle(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = "Normal"; 
    this.mStyle = str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\progressbar\ProgressBarShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */