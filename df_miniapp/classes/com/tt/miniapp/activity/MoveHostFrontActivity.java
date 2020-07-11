package com.tt.miniapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppBrandLogger;

public class MoveHostFrontActivity extends Activity {
  private static String TAG = "MoveHostFrontActivity";
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (ActivityUtil.isMoveActivityToFrontSilentIntent(getIntent()))
      ActivityUtil.changeToSilentHideActivityAnimation(this); 
  }
  
  public void onResume() {
    super.onResume();
    if (!isFinishing()) {
      AppBrandLogger.i(TAG, new Object[] { "finish onResume" });
      finish();
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (paramMotionEvent != null && paramMotionEvent.getAction() == 0 && !isFinishing()) {
      AppBrandLogger.i(TAG, new Object[] { "finish onTouch" });
      finish();
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\activity\MoveHostFrontActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */