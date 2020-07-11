package com.tt.miniapp.feedback;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.RemoteException;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.feedback.screenrecord.ScreenRecordServiceBindManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.feedback.IFeedbackRecordCallback;
import com.tt.miniapphost.util.StorageUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedbackLogHandler {
  public static final String UPLOADING_FLAG_PATH;
  
  public List<IFeedbackLogger> mLoggers = new ArrayList<IFeedbackLogger>();
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(FeedbackUploadHandler.PATH);
    stringBuilder.append("logging.flag");
    UPLOADING_FLAG_PATH = stringBuilder.toString();
  }
  
  private FeedbackLogHandler() {
    init();
  }
  
  private SharedPreferences getFeedbackConfig(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "feedback_config");
  }
  
  public static FeedbackLogHandler getInstance() {
    return AppbrandApplication.getInst().getAppInfo().isLocalTest() ? SingletonHolder.INSTANCE : null;
  }
  
  private void init() {
    registerLogger();
    Observable.create(new Action() {
          public void act() {
            File file = new File(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()), "TT/feedback");
            if (!file.exists())
              file.mkdirs(); 
            Iterator<IFeedbackLogger> iterator = FeedbackLogHandler.this.mLoggers.iterator();
            while (iterator.hasNext())
              ((IFeedbackLogger)iterator.next()).init(); 
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
  }
  
  private boolean isFeedbackSwitchOn(Context paramContext) {
    return getFeedbackConfig(paramContext).getBoolean("is_open", false);
  }
  
  private void registerLogger() {
    this.mLoggers.add(new AppVConsoleLogger());
    this.mLoggers.add(new FeedbackALogger());
    this.mLoggers.add(new PerformanceLogger());
    this.mLoggers.add(new EventLogger());
  }
  
  public boolean getSwitch() {
    return isFeedbackSwitchOn((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public void log() {
    Observable.create(new Action() {
          public void act() {
            FeedbackLogHandler.this.startScreenRecord();
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void setFeedbackSwitchOn(Context paramContext, boolean paramBoolean) {
    getFeedbackConfig(paramContext).edit().putBoolean("is_open", paramBoolean).apply();
  }
  
  public void setSwitchOn(boolean paramBoolean) {
    if (!paramBoolean) {
      stop();
      return;
    } 
    setFeedbackSwitchOn((Context)AppbrandContext.getInst().getApplicationContext(), true);
    if (this.mLoggers.size() <= 0)
      init(); 
    log();
  }
  
  public void startLog() {
    Observable.create(new Action() {
          public void act() {
            Iterator<IFeedbackLogger> iterator = FeedbackLogHandler.this.mLoggers.iterator();
            while (iterator.hasNext())
              ((IFeedbackLogger)iterator.next()).log(); 
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
  }
  
  public void startScreenRecord() {
    ScreenRecordServiceBindManager.getInstance().bindHostService((IFeedbackRecordCallback)new IFeedbackRecordCallback.Stub() {
          public void onFail(String param1String) throws RemoteException {
            FeedbackLogHandler.this.setFeedbackSwitchOn((Context)AppbrandContext.getInst().getApplicationContext(), false);
            StringBuilder stringBuilder = new StringBuilder("screen record can not start");
            stringBuilder.append(param1String);
            AppBrandLogger.e("tma_FeedbackLogHandler", new Object[] { stringBuilder.toString() });
          }
          
          public void onSuccess(String param1String) throws RemoteException {
            FeedbackLogHandler.this.startLog();
          }
        });
  }
  
  public void stop() {
    Observable.create(new Action() {
          public void act() {
            Iterator<IFeedbackLogger> iterator = FeedbackLogHandler.this.mLoggers.iterator();
            while (iterator.hasNext())
              ((IFeedbackLogger)iterator.next()).stop(); 
            FeedbackLogHandler.this.mLoggers.clear();
            FeedbackLogHandler.this.upload();
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
    Observable.create(new Action() {
          public void act() {
            FeedbackLogHandler.this.stopScreenRecord();
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void stopScreenRecord() {
    ScreenRecordServiceBindManager.getInstance().unBindService((IFeedbackRecordCallback)new IFeedbackRecordCallback.Stub() {
          public void onFail(String param1String) throws RemoteException {}
          
          public void onSuccess(String param1String) throws RemoteException {}
        });
  }
  
  public void upload() {
    FeedbackUploadHandler.getInstance().upload();
  }
  
  static class SingletonHolder {
    public static FeedbackLogHandler INSTANCE = new FeedbackLogHandler();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */