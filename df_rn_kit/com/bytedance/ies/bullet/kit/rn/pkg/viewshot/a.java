package com.bytedance.ies.bullet.kit.rn.pkg.viewshot;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.support.v4.f.k;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Locale;
import java.util.Stack;

public final class a {
  public static final Matrix a = new Matrix();
  
  public static int a(String paramString1, String paramString2) {
    int i;
    for (i = 0; paramString2.length() > 0; i++) {
      int j = paramString2.indexOf("\n");
      if (j < 0) {
        j = 200;
      } else {
        j++;
      } 
      j = Math.min(j, 200);
      j = Math.min(paramString2.length(), j);
      com.a.a(Locale.US, "%02d: %s", new Object[] { Integer.valueOf(i), paramString2.substring(0, j) });
      paramString2 = paramString2.substring(j);
    } 
    return i;
  }
  
  private static String a(Resources paramResources, View paramView) {
    if (paramResources == null)
      return ""; 
    try {
      StringBuilder stringBuilder = new StringBuilder(" / ");
      return stringBuilder.toString();
    } finally {
      paramResources = null;
    } 
  }
  
  static String a(View paramView) {
    StringBuilder stringBuilder = new StringBuilder(8192);
    stringBuilder.append("\n");
    Resources resources = paramView.getResources();
    Stack<k> stack = new Stack();
    stack.push(k.a("", paramView));
    while (!stack.empty()) {
      String str1;
      boolean bool;
      k k = stack.pop();
      View view = (View)k.b;
      String str2 = (String)k.a;
      if (stack.empty() || !str2.equals(((k)stack.peek()).a)) {
        bool = true;
      } else {
        bool = false;
      } 
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str2);
      if (bool) {
        str1 = "└── ";
      } else {
        str1 = "├── ";
      } 
      stringBuilder3.append(str1);
      String str3 = stringBuilder3.toString();
      String str4 = view.getClass().getSimpleName();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str3);
      stringBuilder1.append(str4);
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(" id=");
      stringBuilder2.append(view.getId());
      stringBuilder2.append(a(resources, view));
      int i = view.getVisibility();
      if (i != 0) {
        if (i != 4) {
          if (i != 8) {
            stringBuilder2.append(", ---");
          } else {
            stringBuilder2.append(", --G");
          } 
        } else {
          stringBuilder2.append(", -I-");
        } 
      } else {
        stringBuilder2.append(", V--");
      } 
      if (!view.getMatrix().equals(a)) {
        stringBuilder2.append(", matrix=");
        stringBuilder2.append(view.getMatrix().toShortString());
        if (0.0F != view.getRotation() || 0.0F != view.getRotationX() || 0.0F != view.getRotationY()) {
          stringBuilder2.append(", rotate=[");
          stringBuilder2.append(view.getRotation());
          stringBuilder2.append(",");
          stringBuilder2.append(view.getRotationX());
          stringBuilder2.append(",");
          stringBuilder2.append(view.getRotationY());
          stringBuilder2.append("]");
          if ((view.getWidth() / 2) != view.getPivotX() || (view.getHeight() / 2) != view.getPivotY()) {
            stringBuilder2.append(", pivot=[");
            stringBuilder2.append(view.getPivotX());
            stringBuilder2.append(",");
            stringBuilder2.append(view.getPivotY());
            stringBuilder2.append("]");
          } 
        } 
        if (0.0F != view.getTranslationX() || 0.0F != view.getTranslationY() || 0.0F != view.getTranslationZ()) {
          stringBuilder2.append(", translate=[");
          stringBuilder2.append(view.getTranslationX());
          stringBuilder2.append(",");
          stringBuilder2.append(view.getTranslationY());
          stringBuilder2.append(",");
          stringBuilder2.append(view.getTranslationZ());
          stringBuilder2.append("]");
        } 
        if (1.0F != view.getScaleX() || 1.0F != view.getScaleY()) {
          stringBuilder2.append(", scale=[");
          stringBuilder2.append(view.getScaleX());
          stringBuilder2.append(",");
          stringBuilder2.append(view.getScaleY());
          stringBuilder2.append("]");
        } 
      } 
      if (view.getPaddingStart() != 0 || view.getPaddingTop() != 0 || view.getPaddingEnd() != 0 || view.getPaddingBottom() != 0) {
        stringBuilder2.append(", padding=[");
        stringBuilder2.append(view.getPaddingStart());
        stringBuilder2.append(",");
        stringBuilder2.append(view.getPaddingTop());
        stringBuilder2.append(",");
        stringBuilder2.append(view.getPaddingEnd());
        stringBuilder2.append(",");
        stringBuilder2.append(view.getPaddingBottom());
        stringBuilder2.append("]");
      } 
      ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
      if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
        if (marginLayoutParams.leftMargin != 0 || marginLayoutParams.topMargin != 0 || marginLayoutParams.rightMargin != 0 || marginLayoutParams.bottomMargin != 0) {
          stringBuilder2.append(", margin=[");
          stringBuilder2.append(marginLayoutParams.leftMargin);
          stringBuilder2.append(",");
          stringBuilder2.append(marginLayoutParams.topMargin);
          stringBuilder2.append(",");
          stringBuilder2.append(marginLayoutParams.rightMargin);
          stringBuilder2.append(",");
          stringBuilder2.append(marginLayoutParams.bottomMargin);
          stringBuilder2.append("]");
        } 
      } 
      stringBuilder2.append(", position=[");
      stringBuilder2.append(view.getLeft());
      stringBuilder2.append(",");
      stringBuilder2.append(view.getTop());
      stringBuilder2.append("]");
      stringBuilder2.append(", size=[");
      stringBuilder2.append(view.getWidth());
      stringBuilder2.append(",");
      stringBuilder2.append(view.getHeight());
      stringBuilder2.append("]");
      if (view instanceof TextView) {
        TextView textView = (TextView)view;
        stringBuilder2.append(", text=\"");
        stringBuilder2.append(textView.getText());
        stringBuilder2.append("\"");
      } 
      stringBuilder1.append(stringBuilder2.toString());
      stringBuilder.append(stringBuilder1.toString());
      stringBuilder.append("\n");
      if (view instanceof ViewGroup) {
        ViewGroup viewGroup = (ViewGroup)view;
        for (i = viewGroup.getChildCount() - 1; i >= 0; i--) {
          String str;
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append(str2);
          if (bool) {
            str = "    ";
          } else {
            str = "│   ";
          } 
          stringBuilder2.append(str);
          stack.push(k.a(stringBuilder2.toString(), viewGroup.getChildAt(i)));
        } 
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\viewshot\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */