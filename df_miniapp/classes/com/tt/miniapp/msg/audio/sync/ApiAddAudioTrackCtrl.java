package com.tt.miniapp.msg.audio.sync;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.media.base.MediaEditListener;
import com.tt.miniapp.media.base.MediaEditParams;
import com.tt.miniapp.media.utils.MediaEditUtil;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiAddAudioTrackCtrl extends SyncMsgCtrl {
  public ApiAddAudioTrackCtrl(String paramString) {
    super(paramString);
  }
  
  private void callback(String paramString) {
    j j = AppbrandApplicationImpl.getInst().getJsBridge();
    if (j != null)
      j.sendMsgToJsCore("addAudioTrackStateChange", paramString); 
  }
  
  private String changeSrcPath(String paramString) {
    File file1 = new File(paramString);
    String str = file1.getName();
    if (!TextUtils.equals(str, "audio_mix.mp4"))
      return paramString; 
    File file3 = file1.getParentFile();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(System.currentTimeMillis());
    stringBuilder.append(str);
    File file2 = new File(file3, stringBuilder.toString());
    if (file2.exists())
      file2.delete(); 
    if (file1.renameTo(file2))
      paramString = file2.getAbsolutePath(); 
    return paramString;
  }
  
  private String createOutputPath(String paramString) {
    File file = new File(paramString);
    paramString = file.getName();
    file = file.getParentFile();
    if (!TextUtils.equals(paramString, "audio_mix.mp4"))
      return (new File(file, "audio_mix.mp4")).getAbsolutePath(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(System.currentTimeMillis());
    stringBuilder.append(".mp4");
    return (new File(file, stringBuilder.toString())).getAbsolutePath();
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      String str = jSONObject.optString("videoPath");
      JSONArray jSONArray = jSONObject.optJSONArray("audioParams");
      if (TextUtils.isEmpty(str)) {
        str = a.b("videoPath");
        callbackFail(str);
        return makeFailMsg(str);
      } 
      if (jSONArray == null || jSONArray.length() <= 0) {
        str = a.b("audioParams");
        callbackFail(str);
        return makeFailMsg(str);
      } 
      int k = jSONArray.length();
      MediaEditParams.AudioElement[] arrayOfAudioElement = new MediaEditParams.AudioElement[k];
      int i = 0;
      int j;
      for (j = 0;; j = m) {
        int m;
        if (i < k) {
          JSONObject jSONObject1 = jSONArray.optJSONObject(i);
          String str1 = jSONObject1.optString("audioPath");
          JSONArray jSONArray1 = jSONObject1.optJSONArray("audioRange");
          JSONArray jSONArray2 = jSONObject1.optJSONArray("videoRange");
          m = j;
          if (!TextUtils.isEmpty(str1)) {
            m = j;
            if (jSONArray1 != null) {
              m = j;
              if (jSONArray1.length() >= 2) {
                m = j;
                if (jSONArray2 != null) {
                  m = j;
                  if (jSONArray2.length() >= 2) {
                    str1 = FileManager.inst().getRealFilePath(str1);
                    int i2 = jSONArray1.optInt(0);
                    int n = jSONArray1.optInt(1);
                    int i3 = jSONArray2.optInt(0);
                    int i4 = jSONArray2.optInt(1);
                    if (i2 < 0)
                      i2 = 0; 
                    int i1 = n;
                    if (n == -1)
                      i1 = MediaEditUtil.getMediaDuration(str1); 
                    m = j;
                    if (i1 != 0) {
                      m = j;
                      if (i2 < i1) {
                        if (i3 < 0)
                          i3 = 0; 
                        n = i4;
                        if (i4 == -1)
                          n = MediaEditUtil.getMediaDuration(str); 
                        m = j;
                        if (n != 0) {
                          m = j;
                          if (i3 < n) {
                            i4 = i1 - i2;
                            m = n - i3;
                            if (i4 < m) {
                              n = i4 + i3;
                            } else if (i4 > m) {
                              i1 = m + i2;
                            } 
                            arrayOfAudioElement[i] = new MediaEditParams.AudioElement(str1, i2, i1, i3, n);
                            m = j + 1;
                          } 
                        } 
                      } 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } else {
          if (j == 0) {
            callbackFail("the count of audio track is 0");
            return makeFailMsg("the count of audio track is 0");
          } 
          str = changeSrcPath(FileManager.inst().getRealFilePath(str));
          MediaEditUtil.addAudioTrack(str, createOutputPath(str), arrayOfAudioElement, new MediaEditListener() {
                int oldProgress;
                
                public void onFail(int param1Int, String param1String) {
                  StringBuilder stringBuilder = new StringBuilder("onFail: ");
                  stringBuilder.append(param1String);
                  AppBrandLogger.d("tma_ApiAddAudioTrackCtrl", new Object[] { stringBuilder.toString() });
                  if (param1Int == -1000) {
                    param1String = "feature not support";
                  } else if (param1Int == -1001) {
                    param1String = "invalid params";
                  } else {
                    param1String = "editing fail";
                  } 
                  ApiAddAudioTrackCtrl.this.callbackFail(param1String);
                }
                
                public void onProgress(float param1Float) {
                  int i = ApiAddAudioTrackCtrl.this.transformFloat2Int(param1Float);
                  if (i > this.oldProgress) {
                    ApiAddAudioTrackCtrl.this.callbackProgress(i);
                    this.oldProgress = i;
                  } 
                }
                
                public void onSuccess(String param1String, long param1Long) {
                  StringBuilder stringBuilder = new StringBuilder("onSuccess, 耗时：");
                  stringBuilder.append(param1Long / 1000L);
                  stringBuilder.append("s, 输出路径= ");
                  stringBuilder.append(param1String);
                  AppBrandLogger.d("tma_ApiAddAudioTrackCtrl", new Object[] { stringBuilder.toString() });
                  ApiAddAudioTrackCtrl.this.callbackSuccess(param1String);
                }
              });
          return makeOkMsg();
        } 
        i++;
      } 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiAddAudioTrackCtrl", "act", exception);
      return makeFailMsg(exception);
    } 
  }
  
  public void callbackFail(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("state", "fail");
      StringBuilder stringBuilder = new StringBuilder("addAudioTrackStateChange:fail ");
      stringBuilder.append(paramString);
      jSONObject.put("errMsg", stringBuilder.toString());
      callback(jSONObject.toString());
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_ApiAddAudioTrackCtrl", "callbackFail", (Throwable)jSONException);
      return;
    } 
  }
  
  public void callbackProgress(int paramInt) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("state", "progressUpdate");
      jSONObject.put("progress", paramInt);
      jSONObject.put("errMsg", "addAudioTrackStateChange:ok");
      callback(jSONObject.toString());
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_ApiAddAudioTrackCtrl", "callbackProgress", (Throwable)jSONException);
      return;
    } 
  }
  
  public void callbackSuccess(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("state", "success");
      jSONObject.put("errMsg", "addAudioTrackStateChange:ok");
      jSONObject.put("videoPath", FileManager.inst().getSchemaFilePath(paramString));
      jSONObject.put("videoDuration", MediaEditUtil.getMediaDuration(paramString));
      callback(jSONObject.toString());
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_ApiAddAudioTrackCtrl", "callbackSuccess", (Throwable)jSONException);
      return;
    } 
  }
  
  public String getName() {
    return "addAudioTrack";
  }
  
  public int transformFloat2Int(float paramFloat) {
    DecimalFormat decimalFormat = new DecimalFormat();
    decimalFormat.setMaximumFractionDigits(2);
    return (int)(Float.valueOf(decimalFormat.format(paramFloat)).floatValue() * 100.0F);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\sync\ApiAddAudioTrackCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */