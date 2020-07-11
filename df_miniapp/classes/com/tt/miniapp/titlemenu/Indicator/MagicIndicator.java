package com.tt.miniapp.titlemenu.Indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MagicIndicator extends FrameLayout {
  private IPagerNavigator mNavigator;
  
  public MagicIndicator(Context paramContext) {
    super(paramContext);
  }
  
  public MagicIndicator(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public IPagerNavigator getNavigator() {
    return this.mNavigator;
  }
  
  public void onPageScrollStateChanged(int paramInt) {
    IPagerNavigator iPagerNavigator = this.mNavigator;
    if (iPagerNavigator != null)
      iPagerNavigator.onPageScrollStateChanged(paramInt); 
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    IPagerNavigator iPagerNavigator = this.mNavigator;
    if (iPagerNavigator != null)
      iPagerNavigator.onPageScrolled(paramInt1, paramFloat, paramInt2); 
  }
  
  public void onPageSelected(int paramInt) {
    IPagerNavigator iPagerNavigator = this.mNavigator;
    if (iPagerNavigator != null)
      iPagerNavigator.onPageSelected(paramInt); 
  }
  
  public void setNavigator(IPagerNavigator paramIPagerNavigator) {
    IPagerNavigator iPagerNavigator = this.mNavigator;
    if (iPagerNavigator == paramIPagerNavigator)
      return; 
    if (iPagerNavigator != null)
      iPagerNavigator.onDetachFromMagicIndicator(); 
    this.mNavigator = paramIPagerNavigator;
    removeAllViews();
    if (this.mNavigator instanceof View) {
      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
      addView((View)this.mNavigator, (ViewGroup.LayoutParams)layoutParams);
      this.mNavigator.onAttachToMagicIndicator();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\Indicator\MagicIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */