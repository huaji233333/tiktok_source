package com.tt.miniapp.feedback.screenrecord;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;
import com.tt.miniapphost.AppBrandLogger;
import java.util.Objects;

public class VideoEncoder extends BaseEncoder {
  private VideoEncodeConfig mConfig;
  
  private Surface mSurface;
  
  VideoEncoder(VideoEncodeConfig paramVideoEncodeConfig) {
    super(paramVideoEncodeConfig.codecName);
    this.mConfig = paramVideoEncodeConfig;
  }
  
  protected MediaFormat createMediaFormat() {
    return this.mConfig.toFormat();
  }
  
  Surface getInputSurface() {
    return Objects.<Surface>requireNonNull(this.mSurface, "doesn't prepare()");
  }
  
  protected void onEncoderConfigured(MediaCodec paramMediaCodec) {
    this.mSurface = paramMediaCodec.createInputSurface();
    StringBuilder stringBuilder = new StringBuilder("VideoEncoder create input surface: ");
    stringBuilder.append(this.mSurface);
    AppBrandLogger.d("tma_VideoEncoder", new Object[] { stringBuilder.toString() });
  }
  
  public void release() {
    Surface surface = this.mSurface;
    if (surface != null) {
      surface.release();
      this.mSurface = null;
    } 
    super.release();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\VideoEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */