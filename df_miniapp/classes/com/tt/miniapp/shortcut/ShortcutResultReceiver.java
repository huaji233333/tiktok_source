package com.tt.miniapp.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;

public class ShortcutResultReceiver extends BroadcastReceiver {
  private String mRequestId;
  
  private void callback(Intent paramIntent) {
    String str = paramIntent.getStringExtra("key_request_id");
    if (TextUtils.equals(str, this.mRequestId))
      return; 
    this.mRequestId = str;
    ShortcutService shortcutService = getService();
    if (shortcutService != null)
      shortcutService.onResult(this.mRequestId); 
  }
  
  private ShortcutService getService() {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    return (appbrandApplicationImpl == null) ? null : (ShortcutService)appbrandApplicationImpl.getService(ShortcutService.class);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent) {
    AppBrandLogger.e("ShortcutResultReceiver", new Object[] { "shortcut result callback" });
    if (paramIntent == null)
      return; 
    callback(paramIntent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutResultReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */