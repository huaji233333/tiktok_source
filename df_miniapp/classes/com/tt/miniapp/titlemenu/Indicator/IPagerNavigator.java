package com.tt.miniapp.titlemenu.Indicator;

public interface IPagerNavigator {
  void notifyDataSetChanged();
  
  void onAttachToMagicIndicator();
  
  void onDetachFromMagicIndicator();
  
  void onPageScrollStateChanged(int paramInt);
  
  void onPageScrolled(int paramInt1, float paramFloat, int paramInt2);
  
  void onPageSelected(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\Indicator\IPagerNavigator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */