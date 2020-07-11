package com.tt.miniapphost.game;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.IActivityProxy;
import com.tt.option.e.e;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameModuleController implements IGameModuleManagerService {
  private static volatile IGameModuleManagerService gameServiceImpl;
  
  private static volatile GameModuleController instance;
  
  private IGameModuleManagerService findImplAndBind() {
    if (gameServiceImpl == null)
      try {
        Object object = Class.forName("com.tt.miniapp.GameModuleManagerServiceImpl").newInstance();
      } finally {
        Exception exception = null;
      }  
    return gameServiceImpl;
  }
  
  private IGameModuleManagerService getImpl() {
    return gameServiceImpl;
  }
  
  public static GameModuleController inst() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/game/GameModuleController.instance : Lcom/tt/miniapphost/game/GameModuleController;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapphost/AppbrandSupport
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/game/GameModuleController.instance : Lcom/tt/miniapphost/game/GameModuleController;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapphost/game/GameModuleController
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapphost/game/GameModuleController.instance : Lcom/tt/miniapphost/game/GameModuleController;
    //   25: ldc com/tt/miniapphost/AppbrandSupport
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapphost/AppbrandSupport
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/tt/miniapphost/game/GameModuleController.instance : Lcom/tt/miniapphost/game/GameModuleController;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public void callMGNavTo(b paramb, JSONObject paramJSONObject) {
    if (getImpl() == null)
      return; 
    getImpl().callMGNavTo(paramb, paramJSONObject);
  }
  
  public IActivityProxy getGameActivity(FragmentActivity paramFragmentActivity) {
    return (getImpl() == null) ? null : getImpl().getGameActivity(paramFragmentActivity);
  }
  
  public IGameRecordManager getGameRecordManager() {
    return (getImpl() == null) ? null : getImpl().getGameRecordManager();
  }
  
  public IPreEditManager getPreEditManager() {
    return (getImpl() == null) ? null : getImpl().getPreEditManager();
  }
  
  public void handleHostClientLoginResult(int paramInt1, int paramInt2, Intent paramIntent, UserInfoManager.HostClientLoginListener paramHostClientLoginListener) {
    if (getImpl() == null)
      return; 
    getImpl().handleHostClientLoginResult(paramInt1, paramInt2, paramIntent, paramHostClientLoginListener);
  }
  
  public b invokeAsyncApi(String paramString1, String paramString2, int paramInt, e parame) {
    return (getImpl() == null) ? null : getImpl().invokeAsyncApi(paramString1, paramString2, paramInt, parame);
  }
  
  public SyncMsgCtrl invokeSyncApi(String paramString1, String paramString2, int paramInt) {
    return (getImpl() == null) ? null : getImpl().invokeSyncApi(paramString1, paramString2, paramInt);
  }
  
  public boolean isGameModuleReady() {
    return (getImpl() != null);
  }
  
  public void onGameInstall(JSONArray paramJSONArray) {
    if (getImpl() == null)
      return; 
    getImpl().onGameInstall(paramJSONArray);
  }
  
  public void onHide() {
    if (getImpl() == null)
      return; 
    getImpl().onHide();
  }
  
  public void onShow() {
    if (getImpl() == null)
      return; 
    getImpl().onShow();
  }
  
  public void registerService(AppbrandServiceManager paramAppbrandServiceManager) {
    if (getImpl() == null)
      return; 
    getImpl().registerService(paramAppbrandServiceManager);
  }
  
  public void tryInit() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial findImplAndBind : ()Lcom/tt/miniapphost/game/IGameModuleManagerService;
    //   6: pop
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\game\GameModuleController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */