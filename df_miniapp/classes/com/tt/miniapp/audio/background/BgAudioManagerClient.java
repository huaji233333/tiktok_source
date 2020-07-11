package com.tt.miniapp.audio.background;

import android.app.Activity;
import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;

public class BgAudioManagerClient {
  public static volatile int sAudioId = -1;
  
  private BgAudioModel mCacheAudioModel;
  
  private Queue<AudioRunnable> mTaskQueue = new LinkedList<AudioRunnable>();
  
  private BgAudioManagerClient() {}
  
  private void bindRemoteService() {
    AppBrandLogger.d("BgAudioManagerClient", new Object[] { "bindRemoteService" });
    try {
      BgAudioCallExtra bgAudioCallExtra = new BgAudioCallExtra();
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null) {
        boolean bool;
        bgAudioCallExtra.callAppId = appInfoEntity.appId;
        if (appInfoEntity.type == 2) {
          bool = true;
        } else {
          bool = false;
        } 
        bgAudioCallExtra.isGame = bool;
        bgAudioCallExtra.callProcessName = ProcessUtil.getCurProcessName((Context)AppbrandContext.getInst().getApplicationContext());
      } 
      sAudioId = sendBgCommand(sAudioId, BgAudioCommand.OBTAIN_MANAGER, bgAudioCallExtra.toJSONStr()).getInt("bgAudioId");
      ProcessCallControlBridge.callHostProcessAsync("registerBgAudioPlayState", CrossProcessDataEntity.Builder.create().put("bgAudioId", Integer.valueOf(sAudioId)).build(), new IpcCallback() {
            public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
              AppBrandLogger.d("BgAudioManagerClient", new Object[] { "bindRemoteService onIpcCallback callbackData:", param1CrossProcessDataEntity });
              if (param1CrossProcessDataEntity == null)
                return; 
              String str = param1CrossProcessDataEntity.getString("bgAudioPlayState");
              if (str != null)
                BgAudioManagerClient.sendBgAudioState(str); 
            }
            
            public void onIpcConnectError() {
              AppBrandLogger.i("BgAudioManagerClient", new Object[] { "onIpcConnectError" });
              BgAudioManagerClient.sAudioId = -1;
            }
          });
    } catch (Exception exception) {
      AppBrandLogger.e("BgAudioManagerClient", new Object[] { "bindRemoteService", exception });
    } 
    while (!this.mTaskQueue.isEmpty())
      ((AudioRunnable)this.mTaskQueue.poll()).run(); 
  }
  
  public static BgAudioManagerClient getInst() {
    return Holder.INSTANCE;
  }
  
  public static void sendBgAudioState(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("state", paramString);
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onBgAudioStateChange", jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("BgAudioManagerClient", new Object[] { "sendBgAudioState", exception });
      return;
    } 
  }
  
  private void sendRequest(AudioRunnable paramAudioRunnable) {
    sendRequest(paramAudioRunnable, false);
  }
  
  private void sendRequest(AudioRunnable paramAudioRunnable, boolean paramBoolean) {
    if (!paramBoolean && sAudioId == -1) {
      BgAudioModel bgAudioModel = this.mCacheAudioModel;
      if (bgAudioModel != null)
        setAudioModel(bgAudioModel, null); 
    } 
    paramAudioRunnable.run();
  }
  
  public BgAudioState getAudioState() {
    BgAudioState bgAudioState = new BgAudioState();
    if (sAudioId == -1) {
      bgAudioState.paused = true;
      return bgAudioState;
    } 
    try {
      return BgAudioState.parseFromJSONStr(sendBgCommand(sAudioId, BgAudioCommand.GET_AUDIO_STATE).getString("bgAudioCommondRetState"));
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
      return bgAudioState;
    } 
  }
  
  public boolean needKeepAlive() {
    if (sAudioId < 0)
      return false; 
    try {
      return sendBgCommand(sAudioId, BgAudioCommand.NEED_KEEP_ALIVE).getBoolean("bgAudioCommandRetNeedKeepAlive");
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
      return false;
    } 
  }
  
  public void obtainManager(final IBindCallback callback) {
    if (sAudioId >= 0) {
      callback.onBind(sAudioId);
      return;
    } 
    this.mTaskQueue.offer(new AudioRunnable() {
          public void run() {
            callback.onBind(BgAudioManagerClient.sAudioId);
          }
        });
    bindRemoteService();
  }
  
  public void pause(final TaskListener taskListener) {
    sendRequest(new AudioRunnable() {
          public void run() {
            try {
              BgAudioManagerClient.this.sendBgCommand(BgAudioManagerClient.sAudioId, BgAudioCommand.PAUSE);
              if (taskListener != null)
                taskListener.onSuccess(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
              BgAudioManagerClient.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onFail("exception", exception); 
              return;
            } 
          }
        });
  }
  
  public void play(final TaskListener taskListener) {
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      HostDependManager.getInst().muteLiveWindowView((Activity)AppbrandContext.getInst().getCurrentActivity(), AppbrandApplicationImpl.getInst().getSchema()); 
    sendRequest(new AudioRunnable() {
          public void run() {
            try {
              BgAudioManagerClient.this.sendBgCommand(BgAudioManagerClient.sAudioId, BgAudioCommand.PLAY);
              if (taskListener != null)
                taskListener.onSuccess(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
              BgAudioManagerClient.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onFail("exception", exception); 
              return;
            } 
          }
        });
  }
  
  public void seek(final int position, final TaskListener taskListener) {
    sendRequest(new AudioRunnable() {
          public void run() {
            try {
              BgAudioManagerClient bgAudioManagerClient = BgAudioManagerClient.this;
              int i = BgAudioManagerClient.sAudioId;
              BgAudioCommand bgAudioCommand = BgAudioCommand.SEEK;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(position);
              bgAudioManagerClient.sendBgCommand(i, bgAudioCommand, stringBuilder.toString());
              if (taskListener != null)
                taskListener.onSuccess(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
              BgAudioManagerClient.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onFail("exception", exception); 
              return;
            } 
          }
        });
  }
  
  public CrossProcessDataEntity sendBgCommand(int paramInt, BgAudioCommand paramBgAudioCommand) {
    return sendBgCommand(paramInt, paramBgAudioCommand, null);
  }
  
  public CrossProcessDataEntity sendBgCommand(int paramInt, BgAudioCommand paramBgAudioCommand, String paramString) {
    AppBrandLogger.d("BgAudioManagerClient", new Object[] { "commondType:", paramBgAudioCommand, "commondInfo:", paramString });
    return ProcessCallControlBridge.callHostProcessSync("type_bg_audio_sync_commond", CrossProcessDataEntity.Builder.create().put("bgAudioId", Integer.valueOf(paramInt)).put("bgAudioCommondType", paramBgAudioCommand.getCommand()).put("bgAudioCommondInfo", paramString).build());
  }
  
  public void setAudioId(int paramInt) {
    sAudioId = paramInt;
  }
  
  public void setAudioModel(final BgAudioModel model, final TaskListener taskListener) {
    if (model != null && model.src != null && !NetUtil.isSafeDomain("request", model.src)) {
      if (taskListener != null)
        taskListener.onFail("exception", new Exception()); 
      return;
    } 
    this.mCacheAudioModel = model;
    if (sAudioId == -1)
      bindRemoteService(); 
    sendRequest(new AudioRunnable() {
          public void run() {
            try {
              BgAudioManagerClient.this.sendBgCommand(BgAudioManagerClient.sAudioId, BgAudioCommand.SET_AUDIO_MODEL, model.toJSONStr());
              if (taskListener != null)
                taskListener.onSuccess(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
              BgAudioManagerClient.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onFail("exception", exception); 
              return;
            } 
          }
        }true);
  }
  
  public void stop(final TaskListener taskListener) {
    sendRequest(new AudioRunnable() {
          public void run() {
            try {
              BgAudioManagerClient.this.sendBgCommand(BgAudioManagerClient.sAudioId, BgAudioCommand.STOP);
              if (taskListener != null)
                taskListener.onSuccess(); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "BgAudioManagerClient", exception.getStackTrace());
              BgAudioManagerClient.TaskListener taskListener = taskListener;
              if (taskListener != null)
                taskListener.onFail("exception", exception); 
              return;
            } 
          }
        });
  }
  
  static interface AudioRunnable {
    void run();
  }
  
  static class Holder {
    public static final BgAudioManagerClient INSTANCE = new BgAudioManagerClient();
  }
  
  public static interface IBindCallback {
    void onBind(int param1Int);
  }
  
  public static interface TaskListener {
    void onFail(String param1String, Throwable param1Throwable);
    
    void onSuccess();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\background\BgAudioManagerClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */