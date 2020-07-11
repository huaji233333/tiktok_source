package com.tt.miniapp.feedback;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodecInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.RemoteException;
import com.tt.miniapp.feedback.screenrecord.ScreenRecordUtils;
import com.tt.miniapp.feedback.screenrecord.ScreenRecorderManager;
import com.tt.miniapp.feedback.screenrecord.VideoEncodeConfig;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.feedback.IFeedbackRecordCallback;
import com.tt.miniapphost.util.UIUtils;
import java.io.File;

public class FeedbackRecordActivity extends SwipeBackActivity {
  private static IFeedbackRecordCallback sRecordCallback;
  
  public MediaCodecInfo[] mAvcCodecInfos;
  
  public MediaProjectionManager mMediaProjectionManager;
  
  public ScreenRecorderManager mRecorder;
  
  private void callbackFail(String paramString) {
    try {
      if (sRecordCallback != null) {
        IFeedbackRecordCallback iFeedbackRecordCallback = sRecordCallback;
        StringBuilder stringBuilder = new StringBuilder("fail");
        stringBuilder.append(paramString);
        iFeedbackRecordCallback.onFail(stringBuilder.toString());
      } 
      return;
    } catch (RemoteException remoteException) {
      AppBrandLogger.stacktrace(6, "tma_FeedbackRecordActivity", remoteException.getStackTrace());
      return;
    } 
  }
  
  private void callbackSuccess(String paramString) {
    try {
      if (sRecordCallback != null) {
        IFeedbackRecordCallback iFeedbackRecordCallback = sRecordCallback;
        StringBuilder stringBuilder = new StringBuilder("ok");
        stringBuilder.append(paramString);
        iFeedbackRecordCallback.onSuccess(stringBuilder.toString());
      } 
      return;
    } catch (RemoteException remoteException) {
      AppBrandLogger.stacktrace(6, "tma_FeedbackRecordActivity", remoteException.getStackTrace());
      return;
    } 
  }
  
  private void cancelRecorder() {
    if (this.mRecorder == null)
      return; 
    AppBrandLogger.d("tma_FeedbackRecordActivity", new Object[] { "Storage Permission denied! Screen recorder is cancel" });
    stopRecorder();
  }
  
  private VideoEncodeConfig createVideoConfig() {
    String str = getVideoCodec();
    if (str == null)
      return null; 
    int[] arrayOfInt = getSelectedWithHeight();
    int i = arrayOfInt[1];
    int j = arrayOfInt[0];
    int k = getSelectedFrameRate();
    int m = getSelectedIFrameInterval();
    return new VideoEncodeConfig(i, j, getSelectedVideoBitrate(), k, m, str, "video/avc", getSelectedProfileLevel());
  }
  
  private void finishActivity() {
    sRecordCallback = null;
    finish();
  }
  
  private int getSelectedFrameRate() {
    return 30;
  }
  
  private int getSelectedIFrameInterval() {
    return 1;
  }
  
  private MediaCodecInfo.CodecProfileLevel getSelectedProfileLevel() {
    return null;
  }
  
  private int getSelectedVideoBitrate() {
    int i = UIUtils.getDeviceHeight((Context)this);
    return (i <= 480) ? 500000 : ((i <= 640) ? 1024000 : ((i <= 1280) ? 2048000 : 4096000));
  }
  
  private int[] getSelectedWithHeight() {
    return new int[] { UIUtils.getDeviceHeight((Context)this), UIUtils.getDeviceWidth((Context)this) };
  }
  
  private String getVideoCodec() {
    MediaCodecInfo[] arrayOfMediaCodecInfo = this.mAvcCodecInfos;
    int j = arrayOfMediaCodecInfo.length;
    String str = "";
    for (int i = 0; i < j; i++) {
      MediaCodecInfo mediaCodecInfo = arrayOfMediaCodecInfo[i];
      str = this.mAvcCodecInfos[0].getName();
      if (mediaCodecInfo.getName().equals("OMX.google.h264.encoder"))
        return "OMX.google.h264.encoder"; 
    } 
    return str;
  }
  
  private ScreenRecorderManager newRecorder(MediaProjection paramMediaProjection, VideoEncodeConfig paramVideoEncodeConfig, File paramFile) {
    ScreenRecorderManager screenRecorderManager = ScreenRecorderManager.getInstance();
    screenRecorderManager.init(paramVideoEncodeConfig, 1, paramMediaProjection, paramFile.getAbsolutePath());
    return screenRecorderManager;
  }
  
  public static void start(Context paramContext, IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    sRecordCallback = paramIFeedbackRecordCallback;
    Intent intent = new Intent(paramContext, FeedbackRecordActivity.class);
    intent.setFlags(268435456);
    paramContext.startActivity(intent);
  }
  
  private void startRecord() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            FeedbackRecordActivity.this.mMediaProjectionManager = (MediaProjectionManager)AppbrandContext.getInst().getApplicationContext().getSystemService("media_projection");
            ScreenRecordUtils.findEncodersByTypeAsync("video/avc", new ScreenRecordUtils.Callback() {
                  public void onResult(MediaCodecInfo[] param2ArrayOfMediaCodecInfo) {
                    FeedbackRecordActivity.this.mAvcCodecInfos = param2ArrayOfMediaCodecInfo;
                    if (FeedbackRecordActivity.this.mRecorder != null)
                      FeedbackRecordActivity.this.stopRecorder(); 
                    FeedbackRecordActivity.this.startCaptureIntent();
                  }
                });
          }
        });
  }
  
  private void startRecorder() {
    ScreenRecorderManager screenRecorderManager = this.mRecorder;
    if (screenRecorderManager == null)
      return; 
    screenRecorderManager.start();
    AppBrandLogger.d("tma_FeedbackRecordActivity", new Object[] { "start Recorder" });
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 101 && paramInt2 == -1) {
      MediaProjection mediaProjection = this.mMediaProjectionManager.getMediaProjection(paramInt2, paramIntent);
      if (mediaProjection == null) {
        AppBrandLogger.e("tma_FeedbackRecordActivity", new Object[] { "media projection is null" });
        callbackFail("media projection is null");
        finishActivity();
        return;
      } 
      VideoEncodeConfig videoEncodeConfig = createVideoConfig();
      if (videoEncodeConfig == null) {
        AppBrandLogger.e("tma_FeedbackRecordActivity", new Object[] { "Create ScreenRecorderManager failure" });
        mediaProjection.stop();
        callbackFail("Create ScreenRecorderManager failure");
        finishActivity();
        return;
      } 
      File file = new File(FeedbackUploadHandler.PATH);
      if (!file.exists() && !file.mkdirs()) {
        cancelRecorder();
        callbackFail("file path not exist");
        finishActivity();
        return;
      } 
      file = new File(file, "ScreenCapture.mp4");
      StringBuilder stringBuilder = new StringBuilder("Create recorder with :");
      stringBuilder.append(videoEncodeConfig);
      stringBuilder.append(" \n \n ");
      stringBuilder.append(file);
      AppBrandLogger.d("tma_FeedbackRecordActivity", new Object[] { stringBuilder.toString() });
      this.mRecorder = newRecorder(mediaProjection, videoEncodeConfig, file);
      startRecorder();
      callbackSuccess("");
    } else {
      callbackFail("cancel");
    } 
    finishActivity();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676293);
    startRecord();
  }
  
  public void startCaptureIntent() {
    startActivityForResult(this.mMediaProjectionManager.createScreenCaptureIntent(), 101);
  }
  
  public void stopRecorder() {
    ScreenRecorderManager screenRecorderManager = this.mRecorder;
    if (screenRecorderManager != null)
      screenRecorderManager.quit(); 
    this.mRecorder = null;
    AppBrandLogger.d("tma_FeedbackRecordActivity", new Object[] { "stop Recorder" });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackRecordActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */