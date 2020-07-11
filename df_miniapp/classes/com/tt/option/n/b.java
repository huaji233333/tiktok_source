package com.tt.option.n;

import android.app.Activity;
import android.content.Intent;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.entity.ScanResultEntity;
import java.util.List;

public interface b {
  void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b paramb, a parama);
  
  void chooseVideo(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, c paramc);
  
  c createChooseFileHandler(Activity paramActivity);
  
  ScanResultEntity handleActivityScanResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  boolean scanCode(Activity paramActivity, d paramd);
  
  public static interface a {
    void setActivityResultHandler(IActivityResultHandler param1IActivityResultHandler);
  }
  
  public static interface b {
    void onCancel();
    
    void onFail(String param1String);
    
    void onSuccess(List<MediaEntity> param1List);
  }
  
  public static interface c {
    void onCancel();
    
    void onFail(String param1String);
    
    void onSuccess(List<String> param1List);
  }
  
  public static interface d {
    void onScanResult(String param1String1, String param1String2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\n\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */