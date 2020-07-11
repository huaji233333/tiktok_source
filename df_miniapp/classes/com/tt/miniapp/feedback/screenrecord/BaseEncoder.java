package com.tt.miniapp.feedback.screenrecord;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Looper;
import com.tt.miniapphost.AppBrandLogger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public abstract class BaseEncoder implements Encoder {
  public Callback mCallback;
  
  private MediaCodec.Callback mCodecCallback = new MediaCodec.Callback() {
      public void onError(MediaCodec param1MediaCodec, MediaCodec.CodecException param1CodecException) {
        BaseEncoder.this.mCallback.onError(BaseEncoder.this, (Exception)param1CodecException);
      }
      
      public void onInputBufferAvailable(MediaCodec param1MediaCodec, int param1Int) {
        BaseEncoder.this.mCallback.onInputBufferAvailable(BaseEncoder.this, param1Int);
      }
      
      public void onOutputBufferAvailable(MediaCodec param1MediaCodec, int param1Int, MediaCodec.BufferInfo param1BufferInfo) {
        BaseEncoder.this.mCallback.onOutputBufferAvailable(BaseEncoder.this, param1Int, param1BufferInfo);
      }
      
      public void onOutputFormatChanged(MediaCodec param1MediaCodec, MediaFormat param1MediaFormat) {
        BaseEncoder.this.mCallback.onOutputFormatChanged(BaseEncoder.this, param1MediaFormat);
      }
    };
  
  private String mCodecName;
  
  private MediaCodec mEncoder;
  
  BaseEncoder() {}
  
  BaseEncoder(String paramString) {
    this.mCodecName = paramString;
  }
  
  private MediaCodec createEncoder(String paramString) throws IOException {
    try {
      if (this.mCodecName != null)
        return MediaCodec.createByCodecName(this.mCodecName); 
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder("Create MediaCodec by name '");
      stringBuilder.append(this.mCodecName);
      stringBuilder.append("' failure!");
      AppBrandLogger.w("tma_BaseEncoder", new Object[] { stringBuilder.toString(), iOException });
    } 
    return MediaCodec.createEncoderByType(paramString);
  }
  
  protected abstract MediaFormat createMediaFormat();
  
  protected final MediaCodec getEncoder() {
    return Objects.<MediaCodec>requireNonNull(this.mEncoder, "doesn't prepare()");
  }
  
  public final ByteBuffer getInputBuffer(int paramInt) {
    return getEncoder().getInputBuffer(paramInt);
  }
  
  public final ByteBuffer getOutputBuffer(int paramInt) {
    return getEncoder().getOutputBuffer(paramInt);
  }
  
  protected void onEncoderConfigured(MediaCodec paramMediaCodec) {}
  
  public void prepare() throws IOException {
    if (Looper.myLooper() != null && Looper.myLooper() != Looper.getMainLooper()) {
      if (this.mEncoder == null) {
        MediaFormat mediaFormat = createMediaFormat();
        StringBuilder stringBuilder = new StringBuilder("Create media format: ");
        stringBuilder.append(mediaFormat);
        AppBrandLogger.d("tma_BaseEncoder", new Object[] { stringBuilder.toString() });
        MediaCodec mediaCodec = createEncoder(mediaFormat.getString("mime"));
        try {
          if (this.mCallback != null)
            mediaCodec.setCallback(this.mCodecCallback); 
          mediaCodec.configure(mediaFormat, null, null, 1);
          onEncoderConfigured(mediaCodec);
          mediaCodec.start();
          this.mEncoder = mediaCodec;
          return;
        } catch (android.media.MediaCodec.CodecException codecException) {
          StringBuilder stringBuilder1 = new StringBuilder("Configure codec failure!\n  with format");
          stringBuilder1.append(mediaFormat);
          AppBrandLogger.d("tma_BaseEncoder", new Object[] { stringBuilder1.toString(), codecException });
          throw codecException;
        } 
      } 
      throw new IllegalStateException("prepared!");
    } 
    throw new IllegalStateException("should run in a HandlerThread");
  }
  
  public final void queueInputBuffer(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4) {
    getEncoder().queueInputBuffer(paramInt1, paramInt2, paramInt3, paramLong, paramInt4);
  }
  
  public void release() {
    MediaCodec mediaCodec = this.mEncoder;
    if (mediaCodec != null) {
      mediaCodec.release();
      this.mEncoder = null;
    } 
  }
  
  public final void releaseOutputBuffer(int paramInt) {
    getEncoder().releaseOutputBuffer(paramInt, false);
  }
  
  void setCallback(Callback paramCallback) {
    if (this.mEncoder == null) {
      this.mCallback = paramCallback;
      return;
    } 
    throw new IllegalStateException("mEncoder is not null");
  }
  
  public void setCallback(Encoder.Callback paramCallback) {
    if (paramCallback instanceof Callback) {
      setCallback((Callback)paramCallback);
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void stop() {
    MediaCodec mediaCodec = this.mEncoder;
    if (mediaCodec != null)
      mediaCodec.stop(); 
  }
  
  public static abstract class Callback implements Encoder.Callback {
    void onInputBufferAvailable(BaseEncoder param1BaseEncoder, int param1Int) {}
    
    void onOutputBufferAvailable(BaseEncoder param1BaseEncoder, int param1Int, MediaCodec.BufferInfo param1BufferInfo) {}
    
    void onOutputFormatChanged(BaseEncoder param1BaseEncoder, MediaFormat param1MediaFormat) {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\BaseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */