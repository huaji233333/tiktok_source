package com.ss.android.ugc.i18n.fusing;

import android.support.v4.app.Fragment;
import com.bytedance.apm.agent.v2.instrumentation.FragmentShowAgent;

public class FusingPlaceHolder extends Fragment {
  public static void test() {
    System.out.println("do nothing");
  }
  
  public void onHiddenChanged(boolean paramBoolean) {
    super.onHiddenChanged(paramBoolean);
    FragmentShowAgent.onHiddenChanged(this, paramBoolean);
  }
  
  public void onPause() {
    super.onPause();
    FragmentShowAgent.onPause(this);
  }
  
  public void onResume() {
    super.onResume();
    FragmentShowAgent.onResume(this);
  }
  
  public void setUserVisibleHint(boolean paramBoolean) {
    super.setUserVisibleHint(paramBoolean);
    FragmentShowAgent.setUserVisibleHint(this, paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_fusing\classes.jar!\com\ss\androi\\ugc\i18n\fusing\FusingPlaceHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */