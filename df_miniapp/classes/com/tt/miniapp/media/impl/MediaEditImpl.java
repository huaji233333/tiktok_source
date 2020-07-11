package com.tt.miniapp.media.impl;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Size;
import android.util.SparseArray;
import com.tt.miniapp.media.base.MediaEditListener;
import com.tt.miniapp.media.base.MediaEditParams;
import com.tt.miniapp.media.base.MediaEditor;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.aa.c;
import com.tt.option.aa.d;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class MediaEditImpl implements MediaEditor {
  public SparseArray<c> mRunningEditors = new SparseArray();
  
  private String mWorkSpace;
  
  public MediaEditImpl() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandContext.getInst().getApplicationContext().getExternalCacheDir());
    stringBuilder.append(File.separator);
    stringBuilder.append("VideoEdit");
    this.mWorkSpace = stringBuilder.toString();
    File file = new File(this.mWorkSpace);
    if (!file.exists())
      file.mkdirs(); 
  }
  
  private int getMediaDuration(String paramString) {
    try {
      MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
      mediaMetadataRetriever.setDataSource(paramString);
      return Integer.valueOf(mediaMetadataRetriever.extractMetadata(9)).intValue();
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MediaEditImpl", "", exception);
      return 0;
    } 
  }
  
  public boolean cancel(int paramInt) {
    if ((c)this.mRunningEditors.get(paramInt) != null) {
      this.mRunningEditors.remove(paramInt);
      StringBuilder stringBuilder = new StringBuilder("cancel task: ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" success");
      AppBrandLogger.d("MediaEditImpl", new Object[] { stringBuilder.toString() });
      return true;
    } 
    return false;
  }
  
  public int edit(MediaEditParams paramMediaEditParams, final MediaEditListener listener) {
    final c videoEditor = HostDependManager.getInst().getVideoEditor((Context)AppbrandContext.getInst().getApplicationContext(), this.mWorkSpace);
    if (c == null) {
      if (listener != null)
        listener.onFail(-1000, "getVideoEditor fail"); 
      return -1000;
    } 
    final int taskId = TaskIDCreator.createTaskId();
    int i = paramMediaEditParams.getVideoList().size();
    if (i == 0) {
      if (listener != null)
        listener.onFail(-1001, "invalid params,videoPath can not be empty"); 
      return -1001;
    } 
    String[] arrayOfString1 = new String[i];
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    float[] arrayOfFloat = new float[i];
    int m = i - 1;
    String[] arrayOfString2 = new String[m];
    int j;
    for (j = 0; j < i; j++) {
      MediaEditParams.VideoElement videoElement = paramMediaEditParams.getVideoList().get(j);
      arrayOfString1[j] = videoElement.path;
      arrayOfInt1[j] = videoElement.startTime;
      arrayOfInt2[j] = videoElement.endTime;
      arrayOfFloat[j] = videoElement.speed;
      if (arrayOfInt1[j] < 0)
        arrayOfInt1[j] = 0; 
      if (arrayOfInt2[j] == -1)
        arrayOfInt2[j] = getMediaDuration(arrayOfString1[j]); 
      StringBuilder stringBuilder = new StringBuilder("VideoElement: ");
      stringBuilder.append(videoElement.toString());
      AppBrandLogger.d("MediaEditImpl", new Object[] { stringBuilder.toString() });
      if (j < m) {
        if (j < paramMediaEditParams.getTransitionList().size()) {
          arrayOfString2[j] = paramMediaEditParams.getTransitionList().get(j);
        } else {
          arrayOfString2[j] = "";
        } 
        StringBuilder stringBuilder1 = new StringBuilder("Transition: ");
        stringBuilder1.append(arrayOfString2[j]);
        AppBrandLogger.d("MediaEditImpl", new Object[] { stringBuilder1.toString() });
      } 
    } 
    i = c.a(arrayOfString1, arrayOfInt1, arrayOfInt2, arrayOfString2, arrayOfFloat);
    if (i != 0) {
      if (listener != null)
        listener.onFail(i, "VideoEditor init fail,maybe params invalid,please check"); 
      return -1001;
    } 
    for (MediaEditParams.AudioElement audioElement : paramMediaEditParams.getAudioList()) {
      if (audioElement.startTime < 0)
        audioElement.startTime = 0; 
      if (audioElement.endTime == -1)
        audioElement.endTime = getMediaDuration(audioElement.path); 
      if (audioElement.seqInTime < 0)
        audioElement.seqInTime = 0; 
      if (audioElement.seqOutTime == -1)
        audioElement.seqOutTime = getMediaDuration(arrayOfString1[0]); 
      StringBuilder stringBuilder = new StringBuilder("AudioElement: ");
      stringBuilder.append(audioElement);
      AppBrandLogger.d("MediaEditImpl", new Object[] { stringBuilder.toString() });
    } 
    Size size = paramMediaEditParams.getOutputSize();
    final long startTime = System.currentTimeMillis();
    final String outputPath = paramMediaEditParams.getOutputPath();
    if (!c.a(str, size, new d() {
          public void onCompileDone() {
            long l1 = System.currentTimeMillis();
            long l2 = startTime;
            MediaEditListener mediaEditListener = listener;
            if (mediaEditListener != null)
              mediaEditListener.onSuccess(outputPath, l1 - l2); 
            MediaEditImpl.this.mRunningEditors.remove(taskId);
          }
          
          public void onCompileError(int param1Int1, int param1Int2, float param1Float, String param1String) {
            MediaEditListener mediaEditListener = listener;
            if (mediaEditListener != null)
              mediaEditListener.onFail(param1Int1, param1String); 
            MediaEditImpl.this.mRunningEditors.remove(taskId);
          }
          
          public void onProgress(float param1Float) {
            MediaEditListener mediaEditListener = listener;
            if (mediaEditListener != null)
              mediaEditListener.onProgress(param1Float); 
          }
        })) {
      if (listener != null)
        listener.onFail(-1004, "VideoEditor compile return fail"); 
      return -1004;
    } 
    this.mRunningEditors.put(k, c);
    return k;
  }
  
  public int getCurTaskNum() {
    return this.mRunningEditors.size();
  }
  
  static class TaskIDCreator {
    static AtomicInteger sID = new AtomicInteger(0);
    
    static int createTaskId() {
      return sID.getAndIncrement();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\media\impl\MediaEditImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */