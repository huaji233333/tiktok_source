package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReadableMap;
import java.util.Arrays;
import java.util.HashSet;

public class ViewProps {
  public static final int[] BORDER_SPACING_TYPES = new int[] { 8, 4, 5, 1, 3, 0, 2 };
  
  private static final HashSet<String> LAYOUT_ONLY_PROPS;
  
  public static final int[] PADDING_MARGIN_SPACING_TYPES = new int[] { 8, 7, 6, 4, 5, 1, 3, 0, 2 };
  
  public static final int[] POSITION_SPACING_TYPES = new int[] { 4, 5, 1, 3 };
  
  public static boolean sIsOptimizationsEnabled;
  
  static {
    LAYOUT_ONLY_PROPS = new HashSet<String>(Arrays.asList(new String[] { 
            "alignSelf", "alignItems", "collapsable", "flex", "flexBasis", "flexDirection", "flexGrow", "flexShrink", "flexWrap", "justifyContent", 
            "overflow", "alignContent", "display", "position", "right", "top", "bottom", "left", "start", "end", 
            "width", "height", "minWidth", "maxWidth", "minHeight", "maxHeight", "margin", "marginVertical", "marginHorizontal", "marginLeft", 
            "marginRight", "marginTop", "marginBottom", "marginStart", "marginEnd", "padding", "paddingVertical", "paddingHorizontal", "paddingLeft", "paddingRight", 
            "paddingTop", "paddingBottom", "paddingStart", "paddingEnd" }));
  }
  
  public static boolean isLayoutOnly(ReadableMap paramReadableMap, String paramString) {
    String str;
    if (LAYOUT_ONLY_PROPS.contains(paramString))
      return true; 
    if ("pointerEvents".equals(paramString)) {
      str = paramReadableMap.getString(paramString);
      return !"auto".equals(str) ? ("box-none".equals(str)) : true;
    } 
    if (sIsOptimizationsEnabled) {
      byte b;
      switch (paramString.hashCode()) {
        default:
          b = -1;
          break;
        case 1349188574:
          if (paramString.equals("borderRadius")) {
            b = 1;
            break;
          } 
        case 1288688105:
          if (paramString.equals("onLayout")) {
            b = 11;
            break;
          } 
        case 741115130:
          if (paramString.equals("borderWidth")) {
            b = 6;
            break;
          } 
        case 529642498:
          if (paramString.equals("overflow")) {
            b = 12;
            break;
          } 
        case -223992013:
          if (paramString.equals("borderLeftWidth")) {
            b = 7;
            break;
          } 
        case -242276144:
          if (paramString.equals("borderLeftColor")) {
            b = 2;
            break;
          } 
        case -1267206133:
          if (paramString.equals("opacity")) {
            b = 0;
            break;
          } 
        case -1290574193:
          if (paramString.equals("borderBottomWidth")) {
            b = 10;
            break;
          } 
        case -1308858324:
          if (paramString.equals("borderBottomColor")) {
            b = 5;
            break;
          } 
        case -1452542531:
          if (paramString.equals("borderTopWidth")) {
            b = 8;
            break;
          } 
        case -1470826662:
          if (paramString.equals("borderTopColor")) {
            b = 4;
            break;
          } 
        case -1971292586:
          if (paramString.equals("borderRightWidth")) {
            b = 9;
            break;
          } 
        case -1989576717:
          if (paramString.equals("borderRightColor")) {
            b = 3;
            break;
          } 
      } 
      switch (b) {
        default:
          return false;
        case 11:
        case 12:
          return true;
        case 10:
          return !str.isNull("borderBottomWidth") ? ((str.getDouble("borderBottomWidth") == 0.0D)) : true;
        case 9:
          return !str.isNull("borderRightWidth") ? ((str.getDouble("borderRightWidth") == 0.0D)) : true;
        case 8:
          return !str.isNull("borderTopWidth") ? ((str.getDouble("borderTopWidth") == 0.0D)) : true;
        case 7:
          return !str.isNull("borderLeftWidth") ? ((str.getDouble("borderLeftWidth") == 0.0D)) : true;
        case 6:
          return !str.isNull("borderWidth") ? ((str.getDouble("borderWidth") == 0.0D)) : true;
        case 5:
          return (str.getInt("borderBottomColor") == 0);
        case 4:
          return (str.getInt("borderTopColor") == 0);
        case 3:
          return (str.getInt("borderRightColor") == 0);
        case 2:
          return (str.getInt("borderLeftColor") == 0);
        case 1:
          return (str.hasKey("backgroundColor") && str.getInt("backgroundColor") != 0) ? false : (!(str.hasKey("borderWidth") && !str.isNull("borderWidth") && str.getDouble("borderWidth") != 0.0D));
        case 0:
          break;
      } 
      return !str.isNull("opacity") ? ((str.getDouble("opacity") == 1.0D)) : true;
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewProps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */