package com.tt.miniapp.streamloader;

public interface StreamDownloadListener {
  void onDownloadProgress(int paramInt);
  
  void onRetry(String paramString1, String paramString2, String paramString3, int paramInt, long paramLong);
  
  void onStreamDownloadError(String paramString, int paramInt, long paramLong);
  
  void onStreamDownloadFinish(int paramInt, long paramLong);
  
  void onStreamDownloadStop();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */