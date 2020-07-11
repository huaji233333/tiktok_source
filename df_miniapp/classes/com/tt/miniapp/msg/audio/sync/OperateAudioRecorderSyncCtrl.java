package com.tt.miniapp.msg.audio.sync;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.audio.AudioRecorderManager;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONObject;

public class OperateAudioRecorderSyncCtrl extends SyncMsgCtrl {
  public OperateAudioRecorderSyncCtrl(String paramString) {
    super(paramString);
  }
  
  private void operateAudioRecorder(final Activity activity) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(13);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.RECORD_AUDIO);
    BrandPermissionUtils.requestPermissions(activity, getName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("record", "mp_reject"); 
            OperateAudioRecorderSyncCtrl.this.setState("error", "auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.RECORD_AUDIO");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("record", "system_reject"); 
                    OperateAudioRecorderSyncCtrl.this.setState("error", "system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("record"); 
                    OperateAudioRecorderSyncCtrl.this.runApi();
                  }
                });
          }
        }null);
  }
  
  private void registerRecorderCallback() {
    AudioRecorderManager.getInst().registerRecorderCallback(new AudioRecorderManager.IRecorderCallback() {
          public void onFrameRecorded(byte[] param1ArrayOfbyte, boolean param1Boolean) {
            try {
              JSONObject jSONObject = new JSONObject();
              jSONObject.put("state", "frameRecorded");
              jSONObject.put("frameBuffer", Base64.encodeToString(param1ArrayOfbyte, 0));
              jSONObject.put("isLastFrame", param1Boolean);
              AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onRecorderStateChange", jSONObject.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.eWithThrowable("tma_OperateAudioRecorderSyncCtrl", "onFrameRecorded", exception);
              return;
            } 
          }
          
          public void onRecorderStateChange(String param1String1, String param1String2) {
            AppBrandLogger.d("tma_OperateAudioRecorderSyncCtrl", new Object[] { "onRecorderStateChange state = ", param1String1, " , args = ", param1String2 });
            OperateAudioRecorderSyncCtrl.this.setState(param1String1, param1String2);
          }
        });
  }
  
  public String act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return makeFailMsg("activity is null"); 
    operateAudioRecorder((Activity)miniappHostBase);
    return makeOkMsg();
  }
  
  public String getName() {
    return "operateRecorder";
  }
  
  public void runApi() {
    registerRecorderCallback();
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      AppBrandLogger.d("tma_OperateAudioRecorderSyncCtrl", new Object[] { "mParams = ", this.mParams });
      String str = jSONObject.optString("operationType");
      if ("start".equals(str)) {
        AudioRecorderManager.AudioRecorderConfig audioRecorderConfig = new AudioRecorderManager.AudioRecorderConfig(jSONObject.optInt("duration"), jSONObject.optInt("sampleRate"), jSONObject.optInt("encodeBitRate"), (short)jSONObject.optInt("numberOfChannels"), jSONObject.optString("format"), jSONObject.optInt("frameSize"));
        AudioRecorderManager.getInst().start(audioRecorderConfig);
        return;
      } 
      if ("pause".endsWith(str)) {
        AudioRecorderManager.getInst().pause();
        return;
      } 
      if ("resume".endsWith(str)) {
        AudioRecorderManager.getInst().resume();
        return;
      } 
      if ("stop".endsWith(str)) {
        AudioRecorderManager.getInst().stop();
        return;
      } 
      setState("error", a.b("operationType"));
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_OperateAudioRecorderSyncCtrl", "runApi", exception);
      setState("error", "parse error");
      return;
    } 
  }
  
  public void setState(String paramString1, String paramString2) {
    byte b;
    JSONObject jSONObject;
    try {
      jSONObject = new JSONObject();
      jSONObject.put("state", paramString1);
      b = -1;
      int i = paramString1.hashCode();
      if (i != 3540994) {
        if (i == 96784904 && paramString1.equals("error"))
          b = 1; 
      } else if (paramString1.equals("stop")) {
        b = 0;
      } 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_OperateAudioRecorderSyncCtrl", "setState", exception);
      return;
    } 
    if (b != 0) {
      if (b == 1) {
        String str;
        StringBuilder stringBuilder = new StringBuilder("operateRecorder:fail");
        if (TextUtils.isEmpty(paramString2)) {
          paramString1 = "";
        } else {
          StringBuilder stringBuilder1 = new StringBuilder(" ");
          stringBuilder1.append(paramString2);
          str = stringBuilder1.toString();
        } 
        stringBuilder.append(str);
        jSONObject.put("errMsg", stringBuilder.toString());
      } 
    } else {
      jSONObject.put("tempFilePath", FileManager.inst().getSchemaFilePath(paramString2));
    } 
    AppBrandLogger.d("tma_OperateAudioRecorderSyncCtrl", new Object[] { "setState ", jSONObject });
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onRecorderStateChange", jSONObject.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\sync\OperateAudioRecorderSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */