package com.facebook.react.views.text;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;

public final class DefaultStyleValuesUtil {
  private DefaultStyleValuesUtil() {
    throw new AssertionError("Never invoke this for an Utility class!");
  }
  
  private static ColorStateList getDefaultTextAttribute(Context paramContext, int paramInt) {
    TypedArray typedArray;
    null = paramContext.getTheme();
    paramContext = null;
    try {
      TypedArray typedArray1 = null.obtainStyledAttributes(new int[] { paramInt });
      typedArray = typedArray1;
      return typedArray1.getColorStateList(0);
    } finally {
      if (typedArray != null)
        typedArray.recycle(); 
    } 
  }
  
  public static ColorStateList getDefaultTextColor(Context paramContext) {
    return getDefaultTextAttribute(paramContext, 16842904);
  }
  
  public static int getDefaultTextColorHighlight(Context paramContext) {
    return getDefaultTextAttribute(paramContext, 16842905).getDefaultColor();
  }
  
  public static ColorStateList getDefaultTextColorHint(Context paramContext) {
    return getDefaultTextAttribute(paramContext, 16842906);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\DefaultStyleValuesUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */