package com.tt.miniapp.feedback.screenrecord;

import android.media.MediaCodecInfo;
import android.media.MediaFormat;

public class VideoEncodeConfig {
  public final int bitrate;
  
  public final String codecName;
  
  public final MediaCodecInfo.CodecProfileLevel codecProfileLevel;
  
  public final int framerate;
  
  public final int height;
  
  public final int iframeInterval;
  
  public final String mimeType;
  
  public final int width;
  
  public VideoEncodeConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString1, String paramString2, MediaCodecInfo.CodecProfileLevel paramCodecProfileLevel) {
    this.width = paramInt1;
    this.height = paramInt2;
    this.bitrate = paramInt3;
    this.framerate = paramInt4;
    this.iframeInterval = paramInt5;
    this.codecName = paramString1;
    paramString2.getClass();
    this.mimeType = paramString2;
    this.codecProfileLevel = paramCodecProfileLevel;
  }
  
  MediaFormat toFormat() {
    MediaFormat mediaFormat = MediaFormat.createVideoFormat(this.mimeType, this.width, this.height);
    mediaFormat.setInteger("color-format", 2130708361);
    mediaFormat.setInteger("bitrate", this.bitrate);
    mediaFormat.setInteger("frame-rate", this.framerate);
    mediaFormat.setInteger("i-frame-interval", this.iframeInterval);
    MediaCodecInfo.CodecProfileLevel codecProfileLevel = this.codecProfileLevel;
    if (codecProfileLevel != null && codecProfileLevel.profile != 0 && this.codecProfileLevel.level != 0) {
      mediaFormat.setInteger("profile", this.codecProfileLevel.profile);
      mediaFormat.setInteger("level", this.codecProfileLevel.level);
    } 
    return mediaFormat;
  }
  
  public String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder("VideoEncodeConfig{width=");
    stringBuilder.append(this.width);
    stringBuilder.append(", height=");
    stringBuilder.append(this.height);
    stringBuilder.append(", bitrate=");
    stringBuilder.append(this.bitrate);
    stringBuilder.append(", framerate=");
    stringBuilder.append(this.framerate);
    stringBuilder.append(", iframeInterval=");
    stringBuilder.append(this.iframeInterval);
    stringBuilder.append(", codecName='");
    stringBuilder.append(this.codecName);
    stringBuilder.append('\'');
    stringBuilder.append(", mimeType='");
    stringBuilder.append(this.mimeType);
    stringBuilder.append('\'');
    stringBuilder.append(", codecProfileLevel=");
    MediaCodecInfo.CodecProfileLevel codecProfileLevel = this.codecProfileLevel;
    if (codecProfileLevel == null) {
      str = "";
    } else {
      str = ScreenRecordUtils.avcProfileLevelToString((MediaCodecInfo.CodecProfileLevel)str);
    } 
    stringBuilder.append(str);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\VideoEncodeConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */