package com.tt.miniapp.process.extra;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.extra.ICrossProcessOperator;
import com.tt.miniapphost.util.ProcessUtil;

public class CrossProcessOperatorScheduler {
  public static void apiHandlerAct(final b apiHandler) {
    if (shouldOperateInHostProcess((ICrossProcessOperator)apiHandler) && !ProcessUtil.isMainProcess((Context)AppbrandContext.getInst().getApplicationContext())) {
      InnerHostProcessBridge.scheduleApiHandlerAct(apiHandler.getActionName(), apiHandler.mArgs, new IpcCallback() {
            public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
              String str;
              if (param1CrossProcessDataEntity != null) {
                String str1 = param1CrossProcessDataEntity.getString("apiExecuteResult");
              } else {
                param1CrossProcessDataEntity = null;
              } 
              CrossProcessDataEntity crossProcessDataEntity = param1CrossProcessDataEntity;
              if (TextUtils.isEmpty((CharSequence)param1CrossProcessDataEntity))
                str = apiHandler.makeMsgByResult(false, null, null).toString(); 
              apiHandler.callbackOnOriginProcess(str);
            }
            
            public final void onIpcConnectError() {
              b b1 = apiHandler;
              b1.callbackOnOriginProcess(b1.makeMsgByResult(false, null, "ipc fail").toString());
            }
          });
      return;
    } 
    apiHandler.doAct();
  }
  
  public static g nativeModuleInvoke(NativeModule paramNativeModule, f paramf, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    return paramNativeModule.invoke(paramf, paramNativeModuleCallback);
  }
  
  public static String nativeModuleInvoke(NativeModule paramNativeModule, String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    return paramNativeModule.invoke(paramString, paramNativeModuleCallback);
  }
  
  private static boolean shouldOperateInHostProcess(ICrossProcessOperator paramICrossProcessOperator) {
    return "hostProcess".equals(paramICrossProcessOperator.operateProcessIdentify());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\extra\CrossProcessOperatorScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */