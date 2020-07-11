package com.tt.miniapphost.dynamic;

import android.content.Context;
import android.net.Uri;
import com.tt.miniapphost.entity.DisableStateEntity;

public interface IBundleManager {
  DisableStateEntity checkMiniAppDisableState(int paramInt);
  
  void checkUpdateBaseBundle(Context paramContext);
  
  boolean handleTmaTest(Context paramContext, Uri paramUri);
  
  boolean isSDKSupport(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\dynamic\IBundleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */