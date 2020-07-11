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

public interface IGameModuleManagerService {
  void callMGNavTo(b paramb, JSONObject paramJSONObject);
  
  IActivityProxy getGameActivity(FragmentActivity paramFragmentActivity);
  
  IGameRecordManager getGameRecordManager();
  
  IPreEditManager getPreEditManager();
  
  void handleHostClientLoginResult(int paramInt1, int paramInt2, Intent paramIntent, UserInfoManager.HostClientLoginListener paramHostClientLoginListener);
  
  b invokeAsyncApi(String paramString1, String paramString2, int paramInt, e parame);
  
  SyncMsgCtrl invokeSyncApi(String paramString1, String paramString2, int paramInt);
  
  void onGameInstall(JSONArray paramJSONArray);
  
  void onHide();
  
  void onShow();
  
  void registerService(AppbrandServiceManager paramAppbrandServiceManager);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\game\IGameModuleManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */