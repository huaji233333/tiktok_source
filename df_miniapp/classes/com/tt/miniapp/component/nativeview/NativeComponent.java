package com.tt.miniapp.component.nativeview;

import com.tt.option.e.k;

public interface NativeComponent {
  void addView(String paramString, k paramk);
  
  boolean onBackPressed();
  
  void onDestroy();
  
  void onViewPause();
  
  void onViewResume();
  
  void removeView(int paramInt, k paramk);
  
  void updateView(String paramString, k paramk);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\NativeComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */