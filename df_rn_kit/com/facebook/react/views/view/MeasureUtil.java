package com.facebook.react.views.view;

import android.view.View;
import com.facebook.yoga.YogaMeasureMode;

public class MeasureUtil {
  public static int getMeasureSpec(float paramFloat, YogaMeasureMode paramYogaMeasureMode) {
    return (paramYogaMeasureMode == YogaMeasureMode.EXACTLY) ? View.MeasureSpec.makeMeasureSpec((int)paramFloat, 1073741824) : ((paramYogaMeasureMode == YogaMeasureMode.AT_MOST) ? View.MeasureSpec.makeMeasureSpec((int)paramFloat, -2147483648) : View.MeasureSpec.makeMeasureSpec(0, 0));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\MeasureUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */