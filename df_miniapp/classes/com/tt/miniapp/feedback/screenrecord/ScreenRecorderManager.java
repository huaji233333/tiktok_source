package com.tt.miniapp.feedback.screenrecord;

import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenRecorderManager {
  public Callback mCallback;
  
  private int mDpi;
  
  private String mDstPath;
  
  public CallbackHandler mHandler;
  
  private int mHeight;
  
  private AtomicBoolean mIsRunning = new AtomicBoolean(false);
  
  private MediaProjection mMediaProjection;
  
  private MediaMuxer mMuxer;
  
  private boolean mMuxerStarted;
  
  private LinkedList<Integer> mPendingVideoEncoderBufferIndices = new LinkedList<Integer>();
  
  private LinkedList<MediaCodec.BufferInfo> mPendingVideoEncoderBufferInfos = new LinkedList<MediaCodec.BufferInfo>();
  
  private MediaProjection.Callback mProjectionCallback = new MediaProjection.Callback() {
      public void onStop() {
        ScreenRecorderManager.this.quit();
      }
    };
  
  private VideoEncoder mVideoEncoder;
  
  private MediaFormat mVideoOutputFormat;
  
  private long mVideoPtsOffset;
  
  private int mVideoTrackIndex = -1;
  
  private VirtualDisplay mVirtualDisplay;
  
  private int mWidth;
  
  private HandlerThread mWorker;
  
  private ScreenRecorderManager() {}
  
  public static ScreenRecorderManager getInstance() {
    return Holder.INSTANCE;
  }
  
  private void prepareVideoEncoder() throws IOException {
    BaseEncoder.Callback callback = new BaseEncoder.Callback() {
        boolean ranIntoError;
        
        public void onError(Encoder param1Encoder, Exception param1Exception) {
          this.ranIntoError = true;
          AppBrandLogger.e("tma_ScreenRecorderManager", new Object[] { "VideoEncoder ran into an error! ", param1Exception });
          Message.obtain(ScreenRecorderManager.this.mHandler, 2, param1Exception).sendToTarget();
        }
        
        public void onOutputBufferAvailable(BaseEncoder param1BaseEncoder, int param1Int, MediaCodec.BufferInfo param1BufferInfo) {
          StringBuilder stringBuilder = new StringBuilder("VideoEncoder output buffer available: index=");
          stringBuilder.append(param1Int);
          AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
          try {
            ScreenRecorderManager.this.muxVideo(param1Int, param1BufferInfo);
            return;
          } catch (Exception exception) {
            AppBrandLogger.e("tma_ScreenRecorderManager", new Object[] { "Muxer encountered an error! ", exception });
            Message.obtain(ScreenRecorderManager.this.mHandler, 2, exception).sendToTarget();
            return;
          } 
        }
        
        public void onOutputFormatChanged(BaseEncoder param1BaseEncoder, MediaFormat param1MediaFormat) {
          ScreenRecorderManager.this.resetVideoOutputFormat(param1MediaFormat);
          ScreenRecorderManager.this.startMuxerIfReady();
        }
      };
    this.mVideoEncoder.setCallback(callback);
    this.mVideoEncoder.prepare();
  }
  
  private void resetVideoPts(MediaCodec.BufferInfo paramBufferInfo) {
    if (this.mVideoPtsOffset == 0L) {
      this.mVideoPtsOffset = paramBufferInfo.presentationTimeUs;
      paramBufferInfo.presentationTimeUs = 0L;
      return;
    } 
    paramBufferInfo.presentationTimeUs -= this.mVideoPtsOffset;
  }
  
  private void signalStop(boolean paramBoolean) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private void writeSampleData(int paramInt, MediaCodec.BufferInfo paramBufferInfo, ByteBuffer paramByteBuffer) {
    boolean bool;
    ByteBuffer byteBuffer;
    if ((paramBufferInfo.flags & 0x2) != 0) {
      AppBrandLogger.d("tma_ScreenRecorderManager", new Object[] { "Ignoring BUFFER_FLAG_CODEC_CONFIG" });
      paramBufferInfo.size = 0;
    } 
    if ((paramBufferInfo.flags & 0x4) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (paramBufferInfo.size == 0 && !bool) {
      AppBrandLogger.d("tma_ScreenRecorderManager", new Object[] { "info.size == 0, drop it." });
      byteBuffer = null;
    } else {
      if (paramBufferInfo.presentationTimeUs != 0L && paramInt == this.mVideoTrackIndex)
        resetVideoPts(paramBufferInfo); 
      StringBuilder stringBuilder = new StringBuilder("[");
      stringBuilder.append(Thread.currentThread().getId());
      stringBuilder.append("] Got buffer, track=");
      stringBuilder.append(paramInt);
      stringBuilder.append(", info: size=");
      stringBuilder.append(paramBufferInfo.size);
      stringBuilder.append(", presentationTimeUs=");
      stringBuilder.append(paramBufferInfo.presentationTimeUs);
      AppBrandLogger.d("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
      byteBuffer = paramByteBuffer;
      if (!bool) {
        Callback callback = this.mCallback;
        byteBuffer = paramByteBuffer;
        if (callback != null) {
          callback.onRecording(paramBufferInfo.presentationTimeUs);
          byteBuffer = paramByteBuffer;
        } 
      } 
    } 
    if (byteBuffer != null) {
      byteBuffer.position(paramBufferInfo.offset);
      byteBuffer.limit(paramBufferInfo.offset + paramBufferInfo.size);
      this.mMuxer.writeSampleData(paramInt, byteBuffer, paramBufferInfo);
      StringBuilder stringBuilder = new StringBuilder("Sent ");
      stringBuilder.append(paramBufferInfo.size);
      stringBuilder.append(" bytes to MediaMuxer on track ");
      stringBuilder.append(paramInt);
      AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
    } 
  }
  
  protected void finalize() throws Throwable {
    if (this.mMediaProjection != null) {
      AppBrandLogger.e("tma_ScreenRecorderManager", new Object[] { "release() not called!" });
      release();
    } 
  }
  
  public void init(VideoEncodeConfig paramVideoEncodeConfig, int paramInt, MediaProjection paramMediaProjection, String paramString) {
    this.mWidth = paramVideoEncodeConfig.width;
    this.mHeight = paramVideoEncodeConfig.height;
    this.mDpi = paramInt;
    this.mMediaProjection = paramMediaProjection;
    this.mDstPath = paramString;
    this.mVideoEncoder = new VideoEncoder(paramVideoEncodeConfig);
  }
  
  public void muxVideo(int paramInt, MediaCodec.BufferInfo paramBufferInfo) {
    if (!this.mIsRunning.get()) {
      AppBrandLogger.w("tma_ScreenRecorderManager", new Object[] { "muxVideo: Already stopped!" });
      return;
    } 
    if (!this.mMuxerStarted || this.mVideoTrackIndex == -1) {
      this.mPendingVideoEncoderBufferIndices.add(Integer.valueOf(paramInt));
      this.mPendingVideoEncoderBufferInfos.add(paramBufferInfo);
      return;
    } 
    ByteBuffer byteBuffer = this.mVideoEncoder.getOutputBuffer(paramInt);
    writeSampleData(this.mVideoTrackIndex, paramBufferInfo, byteBuffer);
    this.mVideoEncoder.releaseOutputBuffer(paramInt);
    if ((paramBufferInfo.flags & 0x4) != 0) {
      AppBrandLogger.d("tma_ScreenRecorderManager", new Object[] { "Stop encoder and muxer, since the buffer has been marked with EOS" });
      this.mVideoTrackIndex = -1;
      signalStop(true);
    } 
  }
  
  public final boolean quit() {
    if (!this.mIsRunning.get()) {
      release();
      return true;
    } 
    signalStop(false);
    return false;
  }
  
  public void record() {
    if (!this.mIsRunning.get()) {
      if (this.mMediaProjection != null) {
        this.mIsRunning.set(true);
        this.mMediaProjection.registerCallback(this.mProjectionCallback, this.mHandler);
        try {
          this.mMuxer = new MediaMuxer(this.mDstPath, 0);
          prepareVideoEncoder();
          this.mVirtualDisplay = this.mMediaProjection.createVirtualDisplay("tma_ScreenRecorderManager-display", this.mWidth, this.mHeight, this.mDpi, 1, this.mVideoEncoder.getInputSurface(), null, null);
          StringBuilder stringBuilder = new StringBuilder("created virtual display: ");
          stringBuilder.append(this.mVirtualDisplay.getDisplay());
          AppBrandLogger.d("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
          return;
        } catch (IOException iOException) {
          throw new RuntimeException(iOException);
        } 
      } 
      throw new IllegalStateException("maybe release");
    } 
    throw new IllegalStateException();
  }
  
  public void release() {
    MediaProjection mediaProjection2 = this.mMediaProjection;
    if (mediaProjection2 != null)
      mediaProjection2.unregisterCallback(this.mProjectionCallback); 
    VirtualDisplay virtualDisplay = this.mVirtualDisplay;
    if (virtualDisplay != null) {
      virtualDisplay.release();
      this.mVirtualDisplay = null;
    } 
    this.mVideoOutputFormat = null;
    this.mVideoTrackIndex = -1;
    this.mMuxerStarted = false;
    HandlerThread handlerThread = this.mWorker;
    if (handlerThread != null) {
      handlerThread.quitSafely();
      this.mWorker = null;
    } 
    VideoEncoder videoEncoder = this.mVideoEncoder;
    if (videoEncoder != null) {
      videoEncoder.release();
      this.mVideoEncoder = null;
    } 
    MediaProjection mediaProjection1 = this.mMediaProjection;
    if (mediaProjection1 != null) {
      mediaProjection1.stop();
      this.mMediaProjection = null;
    } 
    MediaMuxer mediaMuxer = this.mMuxer;
    if (mediaMuxer != null) {
      try {
        mediaMuxer.stop();
        this.mMuxer.release();
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_ScreenRecorderManager", exception.getStackTrace());
      } 
      this.mMuxer = null;
    } 
    this.mHandler = null;
  }
  
  public void resetVideoOutputFormat(MediaFormat paramMediaFormat) {
    if (this.mVideoTrackIndex < 0 && !this.mMuxerStarted) {
      StringBuilder stringBuilder = new StringBuilder("Video output format changed.\n New format: ");
      stringBuilder.append(paramMediaFormat.toString());
      AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
      this.mVideoOutputFormat = paramMediaFormat;
      return;
    } 
    throw new IllegalStateException("output format already changed!");
  }
  
  public void setCallback(Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  public void signalEndOfStream() {
    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
    ByteBuffer byteBuffer = ByteBuffer.allocate(0);
    bufferInfo.set(0, 0, 0L, 4);
    AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { "Signal EOS to muxer" });
    int i = this.mVideoTrackIndex;
    if (i != -1)
      writeSampleData(i, bufferInfo, byteBuffer); 
    this.mVideoTrackIndex = -1;
  }
  
  public void start() {
    if (this.mWorker == null) {
      this.mWorker = HandlerThreadUtil.getBackgroundHandlerThread();
      this.mHandler = new CallbackHandler(this.mWorker.getLooper());
      this.mHandler.sendEmptyMessage(0);
      return;
    } 
    throw new IllegalStateException();
  }
  
  public void startMuxerIfReady() {
    if (!this.mMuxerStarted) {
      MediaFormat mediaFormat = this.mVideoOutputFormat;
      if (mediaFormat == null)
        return; 
      this.mVideoTrackIndex = this.mMuxer.addTrack(mediaFormat);
      this.mMuxer.start();
      this.mMuxerStarted = true;
      StringBuilder stringBuilder = new StringBuilder("Started media muxer, videoIndex=");
      stringBuilder.append(this.mVideoTrackIndex);
      AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { stringBuilder.toString() });
      if (this.mPendingVideoEncoderBufferIndices.isEmpty())
        return; 
      AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { "Mux pending video output buffers..." });
      while (true) {
        MediaCodec.BufferInfo bufferInfo = this.mPendingVideoEncoderBufferInfos.poll();
        if (bufferInfo != null) {
          muxVideo(((Integer)this.mPendingVideoEncoderBufferIndices.poll()).intValue(), bufferInfo);
          continue;
        } 
        AppBrandLogger.i("tma_ScreenRecorderManager", new Object[] { "Mux pending video output buffers done." });
        break;
      } 
    } 
  }
  
  public void stopEncoders() {
    this.mIsRunning.set(false);
    this.mPendingVideoEncoderBufferInfos.clear();
    this.mPendingVideoEncoderBufferIndices.clear();
    try {
      if (this.mVideoEncoder != null)
        this.mVideoEncoder.stop(); 
      return;
    } catch (IllegalStateException illegalStateException) {
      AppBrandLogger.e("tma_ScreenRecorderManager", new Object[] { Integer.valueOf(6), illegalStateException.getStackTrace() });
      return;
    } 
  }
  
  public static interface Callback {
    void onRecording(long param1Long);
    
    void onStart();
    
    void onStop(Throwable param1Throwable);
  }
  
  class CallbackHandler extends Handler {
    CallbackHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != 0) {
        if (i != 1 && i != 2)
          return; 
      } else {
        try {
          ScreenRecorderManager.this.record();
          if (ScreenRecorderManager.this.mCallback != null)
            ScreenRecorderManager.this.mCallback.onStart(); 
          return;
        } catch (Exception exception) {
          param1Message.obj = exception;
        } 
      } 
      ScreenRecorderManager.this.stopEncoders();
      if (param1Message.arg1 != 1)
        ScreenRecorderManager.this.signalEndOfStream(); 
      if (ScreenRecorderManager.this.mCallback != null)
        ScreenRecorderManager.this.mCallback.onStop((Throwable)param1Message.obj); 
      ScreenRecorderManager.this.release();
    }
  }
  
  static class Holder {
    public static ScreenRecorderManager INSTANCE = new ScreenRecorderManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\ScreenRecorderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */