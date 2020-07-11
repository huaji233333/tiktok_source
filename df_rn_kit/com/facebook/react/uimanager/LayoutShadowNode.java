package com.facebook.react.uimanager;

import android.content.Context;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaWrap;

public class LayoutShadowNode extends ReactShadowNodeImpl {
  private final MutableYogaValue mTempYogaValue = new MutableYogaValue();
  
  public LayoutShadowNode() {}
  
  public LayoutShadowNode(LayoutShadowNode paramLayoutShadowNode) {
    super(paramLayoutShadowNode);
  }
  
  private int maybeTransformLeftRightToStartEnd(int paramInt) {
    return !I18nUtil.getInstance().doLeftAndRightSwapInRTL((Context)getThemedContext()) ? paramInt : ((paramInt != 0) ? ((paramInt != 2) ? paramInt : 5) : 4);
  }
  
  public LayoutShadowNode mutableCopy() {
    return new LayoutShadowNode(this);
  }
  
  @ReactProp(name = "alignContent")
  public void setAlignContent(String paramString) {
    StringBuilder stringBuilder;
    if (isVirtual())
      return; 
    if (paramString == null) {
      setAlignContent(YogaAlign.FLEX_START);
      return;
    } 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 1937124468:
        if (paramString.equals("space-around"))
          b = 7; 
        break;
      case 1742952711:
        if (paramString.equals("flex-end"))
          b = 3; 
        break;
      case 441309761:
        if (paramString.equals("space-between"))
          b = 6; 
        break;
      case 3005871:
        if (paramString.equals("auto"))
          b = 0; 
        break;
      case -46581362:
        if (paramString.equals("flex-start"))
          b = 1; 
        break;
      case -1364013995:
        if (paramString.equals("center"))
          b = 2; 
        break;
      case -1720785339:
        if (paramString.equals("baseline"))
          b = 5; 
        break;
      case -1881872635:
        if (paramString.equals("stretch"))
          b = 4; 
        break;
    } 
    switch (b) {
      default:
        stringBuilder = new StringBuilder("invalid value for alignContent: ");
        stringBuilder.append(paramString);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
      case 7:
        setAlignContent(YogaAlign.SPACE_AROUND);
        return;
      case 6:
        setAlignContent(YogaAlign.SPACE_BETWEEN);
        return;
      case 5:
        setAlignContent(YogaAlign.BASELINE);
        return;
      case 4:
        setAlignContent(YogaAlign.STRETCH);
        return;
      case 3:
        setAlignContent(YogaAlign.FLEX_END);
        return;
      case 2:
        setAlignContent(YogaAlign.CENTER);
        return;
      case 1:
        setAlignContent(YogaAlign.FLEX_START);
        return;
      case 0:
        break;
    } 
    setAlignContent(YogaAlign.AUTO);
  }
  
  @ReactProp(name = "alignItems")
  public void setAlignItems(String paramString) {
    StringBuilder stringBuilder;
    if (isVirtual())
      return; 
    if (paramString == null) {
      setAlignItems(YogaAlign.STRETCH);
      return;
    } 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 1937124468:
        if (paramString.equals("space-around"))
          b = 7; 
        break;
      case 1742952711:
        if (paramString.equals("flex-end"))
          b = 3; 
        break;
      case 441309761:
        if (paramString.equals("space-between"))
          b = 6; 
        break;
      case 3005871:
        if (paramString.equals("auto"))
          b = 0; 
        break;
      case -46581362:
        if (paramString.equals("flex-start"))
          b = 1; 
        break;
      case -1364013995:
        if (paramString.equals("center"))
          b = 2; 
        break;
      case -1720785339:
        if (paramString.equals("baseline"))
          b = 5; 
        break;
      case -1881872635:
        if (paramString.equals("stretch"))
          b = 4; 
        break;
    } 
    switch (b) {
      default:
        stringBuilder = new StringBuilder("invalid value for alignItems: ");
        stringBuilder.append(paramString);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
      case 7:
        setAlignItems(YogaAlign.SPACE_AROUND);
        return;
      case 6:
        setAlignItems(YogaAlign.SPACE_BETWEEN);
        return;
      case 5:
        setAlignItems(YogaAlign.BASELINE);
        return;
      case 4:
        setAlignItems(YogaAlign.STRETCH);
        return;
      case 3:
        setAlignItems(YogaAlign.FLEX_END);
        return;
      case 2:
        setAlignItems(YogaAlign.CENTER);
        return;
      case 1:
        setAlignItems(YogaAlign.FLEX_START);
        return;
      case 0:
        break;
    } 
    setAlignItems(YogaAlign.AUTO);
  }
  
  @ReactProp(name = "alignSelf")
  public void setAlignSelf(String paramString) {
    StringBuilder stringBuilder;
    if (isVirtual())
      return; 
    if (paramString == null) {
      setAlignSelf(YogaAlign.AUTO);
      return;
    } 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 1937124468:
        if (paramString.equals("space-around"))
          b = 7; 
        break;
      case 1742952711:
        if (paramString.equals("flex-end"))
          b = 3; 
        break;
      case 441309761:
        if (paramString.equals("space-between"))
          b = 6; 
        break;
      case 3005871:
        if (paramString.equals("auto"))
          b = 0; 
        break;
      case -46581362:
        if (paramString.equals("flex-start"))
          b = 1; 
        break;
      case -1364013995:
        if (paramString.equals("center"))
          b = 2; 
        break;
      case -1720785339:
        if (paramString.equals("baseline"))
          b = 5; 
        break;
      case -1881872635:
        if (paramString.equals("stretch"))
          b = 4; 
        break;
    } 
    switch (b) {
      default:
        stringBuilder = new StringBuilder("invalid value for alignSelf: ");
        stringBuilder.append(paramString);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
      case 7:
        setAlignSelf(YogaAlign.SPACE_AROUND);
        return;
      case 6:
        setAlignSelf(YogaAlign.SPACE_BETWEEN);
        return;
      case 5:
        setAlignSelf(YogaAlign.BASELINE);
        return;
      case 4:
        setAlignSelf(YogaAlign.STRETCH);
        return;
      case 3:
        setAlignSelf(YogaAlign.FLEX_END);
        return;
      case 2:
        setAlignSelf(YogaAlign.CENTER);
        return;
      case 1:
        setAlignSelf(YogaAlign.FLEX_START);
        return;
      case 0:
        break;
    } 
    setAlignSelf(YogaAlign.AUTO);
  }
  
  @ReactProp(defaultFloat = 1.0E21F, name = "aspectRatio")
  public void setAspectRatio(float paramFloat) {
    setStyleAspectRatio(paramFloat);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderWidth", "borderStartWidth", "borderEndWidth", "borderTopWidth", "borderBottomWidth", "borderLeftWidth", "borderRightWidth"})
  public void setBorderWidths(int paramInt, float paramFloat) {
    if (isVirtual())
      return; 
    setBorder(maybeTransformLeftRightToStartEnd(ViewProps.BORDER_SPACING_TYPES[paramInt]), PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(name = "display")
  public void setDisplay(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setDisplay(YogaDisplay.FLEX);
      return;
    } 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != 3145721) {
      if (i == 3387192 && paramString.equals("none"))
        b = 1; 
    } else if (paramString.equals("flex")) {
      b = 0;
    } 
    if (b != 0) {
      if (b == 1) {
        setDisplay(YogaDisplay.NONE);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("invalid value for display: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    setDisplay(YogaDisplay.FLEX);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "flex")
  public void setFlex(float paramFloat) {
    if (isVirtual())
      return; 
    super.setFlex(paramFloat);
  }
  
  @ReactProp(name = "flexBasis")
  public void setFlexBasis(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i != 3) {
        if (i == 4)
          setFlexBasisPercent(this.mTempYogaValue.value); 
      } else {
        setFlexBasisAuto();
      } 
    } else {
      setFlexBasis(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "flexDirection")
  public void setFlexDirection(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setFlexDirection(YogaFlexDirection.COLUMN);
      return;
    } 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 1272730475:
        if (paramString.equals("column-reverse"))
          b = 1; 
        break;
      case 113114:
        if (paramString.equals("row"))
          b = 2; 
        break;
      case -1354837162:
        if (paramString.equals("column"))
          b = 0; 
        break;
      case -1448970769:
        if (paramString.equals("row-reverse"))
          b = 3; 
        break;
    } 
    if (b != 0) {
      if (b != 1) {
        if (b != 2) {
          if (b == 3) {
            setFlexDirection(YogaFlexDirection.ROW_REVERSE);
            return;
          } 
          StringBuilder stringBuilder = new StringBuilder("invalid value for flexDirection: ");
          stringBuilder.append(paramString);
          throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        } 
        setFlexDirection(YogaFlexDirection.ROW);
        return;
      } 
      setFlexDirection(YogaFlexDirection.COLUMN_REVERSE);
      return;
    } 
    setFlexDirection(YogaFlexDirection.COLUMN);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "flexGrow")
  public void setFlexGrow(float paramFloat) {
    if (isVirtual())
      return; 
    super.setFlexGrow(paramFloat);
  }
  
  @ReactProp(defaultFloat = 0.0F, name = "flexShrink")
  public void setFlexShrink(float paramFloat) {
    if (isVirtual())
      return; 
    super.setFlexShrink(paramFloat);
  }
  
  @ReactProp(name = "flexWrap")
  public void setFlexWrap(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setFlexWrap(YogaWrap.NO_WRAP);
      return;
    } 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -1039592053) {
      if (i == 3657802 && paramString.equals("wrap"))
        b = 1; 
    } else if (paramString.equals("nowrap")) {
      b = 0;
    } 
    if (b != 0) {
      if (b == 1) {
        setFlexWrap(YogaWrap.WRAP);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("invalid value for flexWrap: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    setFlexWrap(YogaWrap.NO_WRAP);
  }
  
  @ReactProp(name = "height")
  public void setHeight(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i != 3) {
        if (i == 4)
          setStyleHeightPercent(this.mTempYogaValue.value); 
      } else {
        setStyleHeightAuto();
      } 
    } else {
      setStyleHeight(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "justifyContent")
  public void setJustifyContent(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setJustifyContent(YogaJustify.FLEX_START);
      return;
    } 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 2055030478:
        if (paramString.equals("space-evenly"))
          b = 5; 
        break;
      case 1937124468:
        if (paramString.equals("space-around"))
          b = 4; 
        break;
      case 1742952711:
        if (paramString.equals("flex-end"))
          b = 2; 
        break;
      case 441309761:
        if (paramString.equals("space-between"))
          b = 3; 
        break;
      case -46581362:
        if (paramString.equals("flex-start"))
          b = 0; 
        break;
      case -1364013995:
        if (paramString.equals("center"))
          b = 1; 
        break;
    } 
    if (b != 0) {
      if (b != 1) {
        if (b != 2) {
          if (b != 3) {
            if (b != 4) {
              if (b == 5) {
                setJustifyContent(YogaJustify.SPACE_EVENLY);
                return;
              } 
              StringBuilder stringBuilder = new StringBuilder("invalid value for justifyContent: ");
              stringBuilder.append(paramString);
              throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            } 
            setJustifyContent(YogaJustify.SPACE_AROUND);
            return;
          } 
          setJustifyContent(YogaJustify.SPACE_BETWEEN);
          return;
        } 
        setJustifyContent(YogaJustify.FLEX_END);
        return;
      } 
      setJustifyContent(YogaJustify.CENTER);
      return;
    } 
    setJustifyContent(YogaJustify.FLEX_START);
  }
  
  @ReactPropGroup(names = {"margin", "marginVertical", "marginHorizontal", "marginStart", "marginEnd", "marginTop", "marginBottom", "marginLeft", "marginRight"})
  public void setMargins(int paramInt, Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    paramInt = maybeTransformLeftRightToStartEnd(ViewProps.PADDING_MARGIN_SPACING_TYPES[paramInt]);
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i != 3) {
        if (i == 4)
          setMarginPercent(paramInt, this.mTempYogaValue.value); 
      } else {
        setMarginAuto(paramInt);
      } 
    } else {
      setMargin(paramInt, this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "maxHeight")
  public void setMaxHeight(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setStyleMaxHeightPercent(this.mTempYogaValue.value); 
    } else {
      setStyleMaxHeight(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "maxWidth")
  public void setMaxWidth(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setStyleMaxWidthPercent(this.mTempYogaValue.value); 
    } else {
      setStyleMaxWidth(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "minHeight")
  public void setMinHeight(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setStyleMinHeightPercent(this.mTempYogaValue.value); 
    } else {
      setStyleMinHeight(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "minWidth")
  public void setMinWidth(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setStyleMinWidthPercent(this.mTempYogaValue.value); 
    } else {
      setStyleMinWidth(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "overflow")
  public void setOverflow(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setOverflow(YogaOverflow.VISIBLE);
      return;
    } 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -1217487446) {
      if (i != -907680051) {
        if (i == 466743410 && paramString.equals("visible"))
          b = 0; 
      } else if (paramString.equals("scroll")) {
        b = 2;
      } 
    } else if (paramString.equals("hidden")) {
      b = 1;
    } 
    if (b != 0) {
      if (b != 1) {
        if (b == 2) {
          setOverflow(YogaOverflow.SCROLL);
          return;
        } 
        StringBuilder stringBuilder = new StringBuilder("invalid value for overflow: ");
        stringBuilder.append(paramString);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
      } 
      setOverflow(YogaOverflow.HIDDEN);
      return;
    } 
    setOverflow(YogaOverflow.VISIBLE);
  }
  
  @ReactPropGroup(names = {"padding", "paddingVertical", "paddingHorizontal", "paddingStart", "paddingEnd", "paddingTop", "paddingBottom", "paddingLeft", "paddingRight"})
  public void setPaddings(int paramInt, Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    paramInt = maybeTransformLeftRightToStartEnd(ViewProps.PADDING_MARGIN_SPACING_TYPES[paramInt]);
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setPaddingPercent(paramInt, this.mTempYogaValue.value); 
    } else {
      setPadding(paramInt, this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "position")
  public void setPosition(String paramString) {
    if (isVirtual())
      return; 
    if (paramString == null) {
      setPositionType(YogaPositionType.RELATIVE);
      return;
    } 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -554435892) {
      if (i == 1728122231 && paramString.equals("absolute"))
        b = 1; 
    } else if (paramString.equals("relative")) {
      b = 0;
    } 
    if (b != 0) {
      if (b == 1) {
        setPositionType(YogaPositionType.ABSOLUTE);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("invalid value for position: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    setPositionType(YogaPositionType.RELATIVE);
  }
  
  @ReactPropGroup(names = {"start", "end", "left", "right", "top", "bottom"})
  public void setPositionValues(int paramInt, Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    (new int[6])[0] = 4;
    (new int[6])[1] = 5;
    (new int[6])[2] = 0;
    (new int[6])[3] = 2;
    (new int[6])[4] = 1;
    (new int[6])[5] = 3;
    paramInt = maybeTransformLeftRightToStartEnd((new int[6])[paramInt]);
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i == 4)
        setPositionPercent(paramInt, this.mTempYogaValue.value); 
    } else {
      setPosition(paramInt, this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  @ReactProp(name = "onLayout")
  public void setShouldNotifyOnLayout(boolean paramBoolean) {
    super.setShouldNotifyOnLayout(paramBoolean);
  }
  
  @ReactProp(name = "width")
  public void setWidth(Dynamic paramDynamic) {
    if (isVirtual())
      return; 
    this.mTempYogaValue.setFromDynamic(paramDynamic);
    int i = null.$SwitchMap$com$facebook$yoga$YogaUnit[this.mTempYogaValue.unit.ordinal()];
    if (i != 1 && i != 2) {
      if (i != 3) {
        if (i == 4)
          setStyleWidthPercent(this.mTempYogaValue.value); 
      } else {
        setStyleWidthAuto();
      } 
    } else {
      setStyleWidth(this.mTempYogaValue.value);
    } 
    paramDynamic.recycle();
  }
  
  static class MutableYogaValue {
    YogaUnit unit;
    
    float value;
    
    private MutableYogaValue() {}
    
    private MutableYogaValue(MutableYogaValue param1MutableYogaValue) {
      this.value = param1MutableYogaValue.value;
      this.unit = param1MutableYogaValue.unit;
    }
    
    void setFromDynamic(Dynamic param1Dynamic) {
      String str;
      if (param1Dynamic.isNull()) {
        this.unit = YogaUnit.UNDEFINED;
        this.value = 1.0E21F;
        return;
      } 
      if (param1Dynamic.getType() == ReadableType.String) {
        str = param1Dynamic.asString();
        if (str.equals("auto")) {
          this.unit = YogaUnit.AUTO;
          this.value = 1.0E21F;
          return;
        } 
        if (str.endsWith("%")) {
          this.unit = YogaUnit.PERCENT;
          this.value = Float.parseFloat(str.substring(0, str.length() - 1));
          return;
        } 
        StringBuilder stringBuilder = new StringBuilder("Unknown value: ");
        stringBuilder.append(str);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      this.unit = YogaUnit.POINT;
      this.value = PixelUtil.toPixelFromDIP(str.asDouble());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\LayoutShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */