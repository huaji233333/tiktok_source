package com.tt.miniapp.media.base;

public class VideoFileInfo {
  public int bitrate;
  
  public int duration;
  
  public int fps;
  
  public int height;
  
  public int latitude;
  
  public int longitude;
  
  public int rotation;
  
  public int width;
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("VideoFileInfo{width=");
    stringBuilder.append(this.width);
    stringBuilder.append(", height=");
    stringBuilder.append(this.height);
    stringBuilder.append(", rotation=");
    stringBuilder.append(this.rotation);
    stringBuilder.append(", duration=");
    stringBuilder.append(this.duration);
    stringBuilder.append(", longitude=");
    stringBuilder.append(this.longitude);
    stringBuilder.append(", latitude=");
    stringBuilder.append(this.latitude);
    stringBuilder.append(", bitrate=");
    stringBuilder.append(this.bitrate);
    stringBuilder.append(", fps=");
    stringBuilder.append(this.fps);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\media\base\VideoFileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */