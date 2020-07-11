package com.tt.miniapp.shortcut.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.tt.miniapp.view.dialog.AlertDialogHelper;
import com.tt.miniapphost.util.UIUtils;

public class MaxWHLayout extends RelativeLayout {
  private float mMaxHeight;
  
  private float mMaxWidth;
  
  public MaxWHLayout(Context paramContext) {
    this(paramContext, null);
  }
  
  public MaxWHLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public MaxWHLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initConfig();
  }
  
  private void initConfig() {
    int[] arrayOfInt = AlertDialogHelper.getAdjustWidthAndHeight(getContext(), false);
    if (arrayOfInt != null && arrayOfInt.length == 2) {
      this.mMaxWidth = UIUtils.dip2Px(getContext(), arrayOfInt[0]);
      this.mMaxHeight = UIUtils.dip2Px(getContext(), arrayOfInt[1]);
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt1);
    int i = View.MeasureSpec.getSize(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt1);
    float f1 = i;
    float f2 = this.mMaxHeight;
    if (f1 <= f2) {
      paramInt1 = i;
    } else {
      paramInt1 = (int)f2;
    } 
    f1 = paramInt2;
    f2 = this.mMaxWidth;
    if (f1 > f2)
      paramInt2 = (int)f2; 
    super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt2, k), View.MeasureSpec.makeMeasureSpec(paramInt1, j));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\MaxWHLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */