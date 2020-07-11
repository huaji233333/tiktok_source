package com.facebook.react.views.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;

public class ReactDrawableHelper {
  private static final TypedValue sResolveOutValue = new TypedValue();
  
  public static Drawable createDrawableFromJSDescription(Context paramContext, ReadableMap paramReadableMap) {
    String str1;
    String str2 = paramReadableMap.getString("type");
    if ("ThemeAttrAndroid".equals(str2)) {
      str1 = paramReadableMap.getString("attribute");
      SoftAssertions.assertNotNull(str1);
      int i = paramContext.getResources().getIdentifier(str1, "attr", "android");
      if (i != 0) {
        if (paramContext.getTheme().resolveAttribute(i, sResolveOutValue, true))
          return (Build.VERSION.SDK_INT >= 21) ? paramContext.getResources().getDrawable(sResolveOutValue.resourceId, paramContext.getTheme()) : paramContext.getResources().getDrawable(sResolveOutValue.resourceId); 
        StringBuilder stringBuilder1 = new StringBuilder("Attribute ");
        stringBuilder1.append(str1);
        stringBuilder1.append(" couldn't be resolved into a drawable");
        throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
      } 
      stringBuilder = new StringBuilder("Attribute ");
      stringBuilder.append(str1);
      stringBuilder.append(" couldn't be found in the resource list");
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    if ("RippleAndroid".equals(str2)) {
      if (Build.VERSION.SDK_INT >= 21) {
        int i;
        if (str1.hasKey("color") && !str1.isNull("color")) {
          i = str1.getInt("color");
        } else if (stringBuilder.getTheme().resolveAttribute(16843820, sResolveOutValue, true)) {
          i = stringBuilder.getResources().getColor(sResolveOutValue.resourceId);
        } else {
          throw new JSApplicationIllegalArgumentException("Attribute colorControlHighlight couldn't be resolved into a drawable");
        } 
        if (!str1.hasKey("borderless") || str1.isNull("borderless") || !str1.getBoolean("borderless")) {
          ColorDrawable colorDrawable = new ColorDrawable(-1);
          return (Drawable)new RippleDrawable(new ColorStateList(new int[][] { {} }, new int[] { i }), null, (Drawable)colorDrawable);
        } 
        stringBuilder = null;
        return (Drawable)new RippleDrawable(new ColorStateList(new int[][] { {} }, new int[] { i }), null, (Drawable)stringBuilder);
      } 
      throw new JSApplicationIllegalArgumentException("Ripple drawable is not available on android API <21");
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid type for android drawable: ");
    stringBuilder.append(str2);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ReactDrawableHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */