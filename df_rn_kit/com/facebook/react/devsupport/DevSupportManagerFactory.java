package com.facebook.react.devsupport;

import android.content.Context;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevSupportManager;

public class DevSupportManagerFactory {
  public static DevSupportManager create(Context paramContext, ReactInstanceManagerDevHelper paramReactInstanceManagerDevHelper, String paramString, boolean paramBoolean, int paramInt) {
    return create(paramContext, paramReactInstanceManagerDevHelper, paramString, paramBoolean, null, null, paramInt);
  }
  
  public static DevSupportManager create(Context paramContext, ReactInstanceManagerDevHelper paramReactInstanceManagerDevHelper, String paramString, boolean paramBoolean, RedBoxHandler paramRedBoxHandler, DevBundleDownloadListener paramDevBundleDownloadListener, int paramInt) {
    if (!paramBoolean)
      return new DisabledDevSupportManager(); 
    try {
      StringBuilder stringBuilder = new StringBuilder("com.facebook.react.devsupport.");
      stringBuilder.append("DevSupportManagerImpl");
      return Class.forName(stringBuilder.toString()).getConstructor(new Class[] { Context.class, ReactInstanceManagerDevHelper.class, String.class, boolean.class, RedBoxHandler.class, DevBundleDownloadListener.class, int.class }).newInstance(new Object[] { paramContext, paramReactInstanceManagerDevHelper, paramString, Boolean.valueOf(true), paramRedBoxHandler, paramDevBundleDownloadListener, Integer.valueOf(paramInt) });
    } catch (Exception exception) {
      throw new RuntimeException("Requested enabled DevSupportManager, but DevSupportManagerImpl class was not found or could not be created", exception);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevSupportManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */