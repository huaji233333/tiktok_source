package com.tt.miniapp.user;

import android.content.Intent;
import com.tt.miniapp.manager.UserInfoManager;
import java.util.HashMap;

public class TmaUserManager {
  private HostClientLoginListener loginListener;
  
  public static TmaUserManager getInstance() {
    return Holder.INSTANCE;
  }
  
  public TmaUser getUser() {
    UserInfoManager.UserInfo userInfo = UserInfoManager.getHostClientUserInfo();
    if (userInfo != null) {
      TmaUser tmaUser = new TmaUser();
      tmaUser.isLogin = userInfo.isLogin;
      tmaUser.sessionId = userInfo.sessionId;
      tmaUser.userId = userInfo.userId;
      tmaUser.nickName = userInfo.nickName;
      tmaUser.avatarUrl = userInfo.avatarUrl;
      tmaUser.gender = userInfo.gender;
      tmaUser.country = userInfo.country;
      tmaUser.language = userInfo.language;
      tmaUser.isVerified = userInfo.isVerified;
      tmaUser.authInfo = userInfo.authInfo;
      return tmaUser;
    } 
    return null;
  }
  
  public boolean handleActivityLoginResult(int paramInt1, int paramInt2, Intent paramIntent) {
    final HostClientLoginListener listener = this.loginListener;
    if (hostClientLoginListener == null)
      return false; 
    this.loginListener = null;
    return UserInfoManager.handleHostClientLoginResult(paramInt1, paramInt2, paramIntent, new UserInfoManager.HostClientLoginListener() {
          public void onLoginFail() {
            listener.onLoginFail();
          }
          
          public void onLoginSuccess() {
            listener.onLoginSuccess();
          }
          
          public void onLoginUnSupport() {
            listener.onLoginUnSupport();
          }
          
          public void onLoginWhenBackground() {
            listener.onLoginWhenBackground();
          }
          
          public void onTriggerHostClientLogin(String param1String) {
            listener.onTriggerHostClientLogin();
          }
        });
  }
  
  public boolean isLogin() {
    TmaUser tmaUser = getUser();
    return (tmaUser != null) ? tmaUser.isLogin : false;
  }
  
  public void login(HostClientLoginListener paramHostClientLoginListener) {
    login(paramHostClientLoginListener, null);
  }
  
  public void login(final HostClientLoginListener listener, HashMap<String, Object> paramHashMap) {
    this.loginListener = listener;
    UserInfoManager.requestLoginHostClient(new UserInfoManager.HostClientLoginListener() {
          public void onLoginFail() {
            TmaUserManager.HostClientLoginListener hostClientLoginListener = listener;
            if (hostClientLoginListener != null)
              hostClientLoginListener.onLoginFail(); 
          }
          
          public void onLoginSuccess() {
            TmaUserManager.HostClientLoginListener hostClientLoginListener = listener;
            if (hostClientLoginListener != null)
              hostClientLoginListener.onLoginSuccess(); 
          }
          
          public void onLoginUnSupport() {
            TmaUserManager.HostClientLoginListener hostClientLoginListener = listener;
            if (hostClientLoginListener != null)
              hostClientLoginListener.onLoginUnSupport(); 
          }
          
          public void onLoginWhenBackground() {
            TmaUserManager.HostClientLoginListener hostClientLoginListener = listener;
            if (hostClientLoginListener != null)
              hostClientLoginListener.onLoginWhenBackground(); 
          }
          
          public void onTriggerHostClientLogin(String param1String) {
            TmaUserManager.HostClientLoginListener hostClientLoginListener = listener;
            if (hostClientLoginListener != null)
              hostClientLoginListener.onTriggerHostClientLogin(); 
          }
        },  paramHashMap, null);
  }
  
  static interface Holder {
    public static final TmaUserManager INSTANCE = new TmaUserManager();
  }
  
  public static interface HostClientLoginListener {
    void onLoginFail();
    
    void onLoginSuccess();
    
    void onLoginUnSupport();
    
    void onLoginWhenBackground();
    
    void onTriggerHostClientLogin();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\user\TmaUserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */