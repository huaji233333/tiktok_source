package com.facebook.react.devsupport.interfaces;

public interface DevBundleDownloadListener {
  void onFailure(Exception paramException);
  
  void onProgress(String paramString, Integer paramInteger1, Integer paramInteger2);
  
  void onSuccess();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\interfaces\DevBundleDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */