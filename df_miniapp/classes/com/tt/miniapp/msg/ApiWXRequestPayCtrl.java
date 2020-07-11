package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.notification.MiniAppNotificationManager;
import com.tt.option.e.e;

public class ApiWXRequestPayCtrl extends b {
  private MiniAppNotificationManager.NotificationEntity mNotificationEntity;
  
  public ApiWXRequestPayCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    callbackAppUnSupportFeature();
  }
  
  public String getActionName() {
    return "requestWXPayment";
  }
  
  public void onApiHandlerCallback() {
    MiniAppNotificationManager.cancelPayNotification(this.mNotificationEntity);
  }
  
  public static interface IWxPayExecutor {
    void doPay(ApiWXRequestPayCtrl.PayParamEntity param1PayParamEntity, ApiWXRequestPayCtrl.PayCallback param1PayCallback);
  }
  
  public static interface PayCallback {
    void onResult(ApiCallResult param1ApiCallResult);
  }
  
  public static class PayParamEntity {
    public int mMiniProgramType;
    
    public String mOriginArgs;
    
    public String mPath;
    
    public String mUserName;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{mUserName: ");
      stringBuilder.append(this.mUserName);
      stringBuilder.append(",mPath: ");
      stringBuilder.append(this.mPath);
      stringBuilder.append(",mMiniProgramType: ");
      stringBuilder.append(this.mMiniProgramType);
      stringBuilder.append(",mOriginArgs:");
      stringBuilder.append(this.mOriginArgs);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiWXRequestPayCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */