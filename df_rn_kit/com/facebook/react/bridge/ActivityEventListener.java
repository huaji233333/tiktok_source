package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Intent;

public interface ActivityEventListener {
  void onActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent);
  
  void onNewIntent(Intent paramIntent);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ActivityEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */