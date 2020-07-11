package com.facebook.g.a;

import android.support.v4.e.d;
import android.support.v4.e.e;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import java.lang.reflect.Field;

final class b {
  public static StaticLayout a(CharSequence paramCharSequence, int paramInt1, int paramInt2, TextPaint paramTextPaint, int paramInt3, Layout.Alignment paramAlignment, float paramFloat1, float paramFloat2, boolean paramBoolean, TextUtils.TruncateAt paramTruncateAt, int paramInt4, int paramInt5, d paramd) {
    StaticLayout staticLayout1 = b(paramCharSequence, 0, paramInt2, paramTextPaint, paramInt3, paramAlignment, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5, paramd);
    StaticLayout staticLayout2 = staticLayout1;
    if (paramInt5 > 0) {
      paramInt1 = paramInt2;
      while (true) {
        staticLayout2 = staticLayout1;
        if (staticLayout1.getLineCount() > paramInt5) {
          paramInt2 = staticLayout1.getLineStart(paramInt5);
          staticLayout2 = staticLayout1;
          if (paramInt2 < paramInt1) {
            for (paramInt1 = paramInt2; paramInt1 > 0 && Character.isSpace(paramCharSequence.charAt(paramInt1 - 1)); paramInt1--);
            staticLayout2 = b(paramCharSequence, 0, paramInt1, paramTextPaint, paramInt3, paramAlignment, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5, paramd);
            staticLayout1 = staticLayout2;
            if (staticLayout2.getLineCount() >= paramInt5) {
              staticLayout1 = staticLayout2;
              if (staticLayout2.getEllipsisCount(paramInt5 - 1) == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(paramCharSequence.subSequence(0, paramInt1));
                stringBuilder.append(" …");
                String str = stringBuilder.toString();
                StaticLayout staticLayout = b(str, 0, str.length(), paramTextPaint, paramInt3, paramAlignment, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5, paramd);
              } 
            } 
            continue;
          } 
        } 
        break;
      } 
    } 
    do {
    
    } while (!a(staticLayout2));
    return staticLayout2;
  }
  
  private static boolean a(StaticLayout paramStaticLayout) {
    int j = paramStaticLayout.getLineStart(0);
    int k = paramStaticLayout.getLineCount();
    int i = 0;
    while (i < k) {
      int m = paramStaticLayout.getLineEnd(i);
      if (m < j)
        try {
          Field field2 = StaticLayout.class.getDeclaredField("mLines");
          field2.setAccessible(true);
          Field field1 = StaticLayout.class.getDeclaredField("mColumns");
          field1.setAccessible(true);
          int[] arrayOfInt = (int[])field2.get(paramStaticLayout);
          m = field1.getInt(paramStaticLayout);
          for (j = 0; j < m; j++) {
            k = m * i + j;
            int n = k + m;
            int i1 = arrayOfInt[k];
            arrayOfInt[k] = arrayOfInt[n];
            arrayOfInt[n] = i1;
          } 
          return false;
        } catch (Exception exception) {
          return true;
        }  
      i++;
      j = m;
    } 
    return true;
  }
  
  private static StaticLayout b(CharSequence paramCharSequence, int paramInt1, int paramInt2, TextPaint paramTextPaint, int paramInt3, Layout.Alignment paramAlignment, float paramFloat1, float paramFloat2, boolean paramBoolean, TextUtils.TruncateAt paramTruncateAt, int paramInt4, int paramInt5, d paramd) {
    try {
      TextDirectionHeuristic textDirectionHeuristic;
      if (paramd == e.a) {
        textDirectionHeuristic = TextDirectionHeuristics.LTR;
      } else if (textDirectionHeuristic == e.b) {
        textDirectionHeuristic = TextDirectionHeuristics.RTL;
      } else {
        if (textDirectionHeuristic != e.c) {
          if (textDirectionHeuristic == e.d) {
            textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
          } else if (textDirectionHeuristic == e.e) {
            textDirectionHeuristic = TextDirectionHeuristics.ANYRTL_LTR;
          } else {
            if (textDirectionHeuristic == e.f) {
              textDirectionHeuristic = TextDirectionHeuristics.LOCALE;
              return new StaticLayout(paramCharSequence, paramInt1, paramInt2, paramTextPaint, paramInt3, paramAlignment, textDirectionHeuristic, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5);
            } 
            textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
          } 
          return new StaticLayout(paramCharSequence, paramInt1, paramInt2, paramTextPaint, paramInt3, paramAlignment, textDirectionHeuristic, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5);
        } 
        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
      } 
      return new StaticLayout(paramCharSequence, paramInt1, paramInt2, paramTextPaint, paramInt3, paramAlignment, textDirectionHeuristic, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4, paramInt5);
    } catch (LinkageError linkageError) {
      return new StaticLayout(paramCharSequence, paramInt1, paramInt2, paramTextPaint, paramInt3, paramAlignment, paramFloat1, paramFloat2, paramBoolean, paramTruncateAt, paramInt4);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\g\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */