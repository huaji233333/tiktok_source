package com.tt.miniapp.launchcache.meta;

import java.util.List;

public interface AppInfoBatchRequestListener {
  void requestBatchAppInfoFail(String paramString);
  
  void requestBatchAppInfoSuccess(List<? extends RequestResultInfo> paramList);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\AppInfoBatchRequestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */