package com.tt.miniapphost.dynamic;

import android.content.Context;
import android.net.Uri;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;

public interface IBaseBundleManager {
  void checkUpdateBaseBundle(Context paramContext, boolean paramBoolean);
  
  void checkUpdateBaseBundle(Context paramContext, boolean paramBoolean, BaseBundleManager.BaseBundleUpdateListener paramBaseBundleUpdateListener);
  
  String getSdkCurrentVersionStr(Context paramContext);
  
  void handleBaseBundleWhenRestart();
  
  boolean handleTmaTest(Context paramContext, Uri paramUri);
  
  void preload(Context paramContext);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\dynamic\IBaseBundleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */