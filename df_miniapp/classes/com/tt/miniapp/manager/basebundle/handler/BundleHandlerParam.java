package com.tt.miniapp.manager.basebundle.handler;

import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapphost.util.TimeMeter;
import java.io.File;

public class BundleHandlerParam {
  public BaseBundleEventHelper.BaseBundleEvent baseBundleEvent;
  
  public long bundleVersion;
  
  public boolean isIgnoreTask;
  
  public boolean isLastTaskSuccess;
  
  public File targetZipFile;
  
  public TimeMeter timeMeter;
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\BundleHandlerParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */