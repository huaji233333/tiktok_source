package com.facebook.react.views.text;

import android.text.TextUtils;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.a;

public abstract class ReactTextAnchorViewManager<T extends View, C extends ReactBaseTextShadowNode> extends BaseViewManager<T, C> {
  private static final int[] SPACING_TYPES = new int[] { 8, 0, 2, 1, 3 };
  
  @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
  public void setBorderColor(ReactTextView paramReactTextView, int paramInt, Integer paramInteger) {
    float f1;
    float f2 = 1.0E21F;
    if (paramInteger == null) {
      f1 = 1.0E21F;
    } else {
      f1 = (paramInteger.intValue() & 0xFFFFFF);
    } 
    if (paramInteger != null)
      f2 = (paramInteger.intValue() >>> 24); 
    paramReactTextView.setBorderColor(SPACING_TYPES[paramInt], f1, f2);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
  public void setBorderRadius(ReactTextView paramReactTextView, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    if (paramInt == 0) {
      paramReactTextView.setBorderRadius(f);
      return;
    } 
    paramReactTextView.setBorderRadius(f, paramInt - 1);
  }
  
  @ReactProp(name = "borderStyle")
  public void setBorderStyle(ReactTextView paramReactTextView, String paramString) {
    paramReactTextView.setBorderStyle(paramString);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
  public void setBorderWidth(ReactTextView paramReactTextView, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    paramReactTextView.setBorderWidth(SPACING_TYPES[paramInt], f);
  }
  
  @ReactProp(defaultBoolean = false, name = "disabled")
  public void setDisabled(ReactTextView paramReactTextView, boolean paramBoolean) {
    paramReactTextView.setEnabled(paramBoolean ^ true);
  }
  
  @ReactProp(name = "ellipsizeMode")
  public void setEllipsizeMode(ReactTextView paramReactTextView, String paramString) {
    if (paramString == null || paramString.equals("tail")) {
      paramReactTextView.setEllipsizeLocation(TextUtils.TruncateAt.END);
      return;
    } 
    if (paramString.equals("head")) {
      paramReactTextView.setEllipsizeLocation(TextUtils.TruncateAt.START);
      return;
    } 
    if (paramString.equals("middle")) {
      paramReactTextView.setEllipsizeLocation(TextUtils.TruncateAt.MIDDLE);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid ellipsizeMode: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(defaultBoolean = true, name = "includeFontPadding")
  public void setIncludeFontPadding(ReactTextView paramReactTextView, boolean paramBoolean) {
    paramReactTextView.setIncludeFontPadding(paramBoolean);
  }
  
  @ReactProp(defaultInt = 2147483647, name = "numberOfLines")
  public void setNumberOfLines(ReactTextView paramReactTextView, int paramInt) {
    paramReactTextView.setNumberOfLines(paramInt);
  }
  
  @ReactProp(name = "selectable")
  public void setSelectable(ReactTextView paramReactTextView, boolean paramBoolean) {
    paramReactTextView.setTextIsSelectable(paramBoolean);
  }
  
  @ReactProp(customType = "Color", name = "selectionColor")
  public void setSelectionColor(ReactTextView paramReactTextView, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactTextView.setHighlightColor(DefaultStyleValuesUtil.getDefaultTextColorHighlight(paramReactTextView.getContext()));
      return;
    } 
    paramReactTextView.setHighlightColor(paramInteger.intValue());
  }
  
  @ReactProp(name = "textAlignVertical")
  public void setTextAlignVertical(ReactTextView paramReactTextView, String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      paramReactTextView.setGravityVertical(0);
      return;
    } 
    if ("top".equals(paramString)) {
      paramReactTextView.setGravityVertical(48);
      return;
    } 
    if ("bottom".equals(paramString)) {
      paramReactTextView.setGravityVertical(80);
      return;
    } 
    if ("center".equals(paramString)) {
      paramReactTextView.setGravityVertical(16);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid textAlignVertical: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextAnchorViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */