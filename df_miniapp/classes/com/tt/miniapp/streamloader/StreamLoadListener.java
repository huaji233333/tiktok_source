package com.tt.miniapp.streamloader;

import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;

public interface StreamLoadListener {
  void onDownloadProgress(int paramInt);
  
  void onHeadInfoLoadSuccess();
  
  void onRetry(String paramString1, String paramString2, String paramString3);
  
  void onStreamLoadError(int paramInt, String paramString);
  
  void onStreamLoadFinish(TTAPkgInfo paramTTAPkgInfo);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamLoadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */