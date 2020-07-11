package com.tt.miniapp.media.base;

import android.util.Size;
import java.util.ArrayList;
import java.util.List;

public class MediaEditParams {
  private List<AudioElement> audioList;
  
  private int bitRate;
  
  private int fps;
  
  private String outputPath;
  
  private Size outputSize;
  
  private List<StickerElement> stickerList;
  
  private List<String> transitionList;
  
  private List<VideoElement> videoList;
  
  public MediaEditParams(Builder paramBuilder) {
    this.outputPath = paramBuilder.outputPath;
    this.outputSize = paramBuilder.outputSize;
    this.fps = paramBuilder.outFps;
    this.bitRate = paramBuilder.outBitRate;
    this.videoList = paramBuilder.videos;
    this.audioList = paramBuilder.audios;
    this.stickerList = paramBuilder.stickers;
    this.transitionList = paramBuilder.transitions;
  }
  
  public List<AudioElement> getAudioList() {
    return this.audioList;
  }
  
  public int getBitRate() {
    return this.bitRate;
  }
  
  public int getFps() {
    return this.fps;
  }
  
  public String getOutputPath() {
    return this.outputPath;
  }
  
  public Size getOutputSize() {
    return this.outputSize;
  }
  
  public List<StickerElement> getStickerList() {
    return this.stickerList;
  }
  
  public List<String> getTransitionList() {
    return this.transitionList;
  }
  
  public List<VideoElement> getVideoList() {
    return this.videoList;
  }
  
  public static class AudioElement {
    public int endTime;
    
    public String path;
    
    public int seqInTime;
    
    public int seqOutTime;
    
    public int startTime;
    
    public AudioElement(String param1String, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.path = param1String;
      this.startTime = param1Int1;
      this.endTime = param1Int2;
      this.seqInTime = param1Int3;
      this.seqOutTime = param1Int4;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("AudioElement{path='");
      stringBuilder.append(this.path);
      stringBuilder.append('\'');
      stringBuilder.append(", startTime=");
      stringBuilder.append(this.startTime);
      stringBuilder.append(", endTime=");
      stringBuilder.append(this.endTime);
      stringBuilder.append(", seqInTime=");
      stringBuilder.append(this.seqInTime);
      stringBuilder.append(", seqOutTime=");
      stringBuilder.append(this.seqOutTime);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static class Builder {
    public List<MediaEditParams.AudioElement> audios = new ArrayList<MediaEditParams.AudioElement>();
    
    public int outBitRate;
    
    public int outFps;
    
    public String outputPath;
    
    public Size outputSize;
    
    public List<MediaEditParams.StickerElement> stickers = new ArrayList<MediaEditParams.StickerElement>();
    
    public List<String> transitions = new ArrayList<String>();
    
    public List<MediaEditParams.VideoElement> videos = new ArrayList<MediaEditParams.VideoElement>();
    
    public Builder addAudioElement(MediaEditParams.AudioElement param1AudioElement) {
      this.audios.add(param1AudioElement);
      return this;
    }
    
    public Builder addStickerElement(MediaEditParams.StickerElement param1StickerElement) {
      this.stickers.add(param1StickerElement);
      return this;
    }
    
    public Builder addTransition(String param1String) {
      this.transitions.add(param1String);
      return this;
    }
    
    public Builder addVideoElement(MediaEditParams.VideoElement param1VideoElement) {
      this.videos.add(param1VideoElement);
      return this;
    }
    
    public MediaEditParams build() {
      return new MediaEditParams(this);
    }
    
    public Builder outBitRate(int param1Int) {
      this.outBitRate = param1Int;
      return this;
    }
    
    public Builder outFps(int param1Int) {
      this.outFps = param1Int;
      return this;
    }
    
    public Builder outputPath(String param1String) {
      this.outputPath = param1String;
      return this;
    }
    
    public Builder outputSize(Size param1Size) {
      this.outputSize = param1Size;
      return this;
    }
  }
  
  public static class StickerElement {
    public float height;
    
    public String path;
    
    public float width;
    
    public float x;
    
    public float y;
    
    public StickerElement(String param1String, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this.path = param1String;
      this.x = param1Float1;
      this.y = param1Float2;
      this.width = param1Float3;
      this.height = param1Float4;
    }
  }
  
  public static class Transition {}
  
  public static class VideoElement {
    public int endTime;
    
    public String path;
    
    public float speed;
    
    public int startTime;
    
    public VideoElement(String param1String, int param1Int1, int param1Int2, float param1Float) {
      this.path = param1String;
      this.startTime = param1Int1;
      this.endTime = param1Int2;
      this.speed = param1Float;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("VideoElement{path='");
      stringBuilder.append(this.path);
      stringBuilder.append('\'');
      stringBuilder.append(", startTime=");
      stringBuilder.append(this.startTime);
      stringBuilder.append(", endTime=");
      stringBuilder.append(this.endTime);
      stringBuilder.append(", speed=");
      stringBuilder.append(this.speed);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\media\base\MediaEditParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */