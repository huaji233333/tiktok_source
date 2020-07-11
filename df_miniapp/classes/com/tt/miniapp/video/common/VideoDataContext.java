package com.tt.miniapp.video.common;

public class VideoDataContext {
  private int duration;
  
  private VideoDataContext() {}
  
  public static VideoDataContext getInstance() {
    return LazyHolder.INSTANCE;
  }
  
  public int getDuration() {
    return this.duration;
  }
  
  public void setDuration(int paramInt) {
    this.duration = paramInt;
  }
  
  static class LazyHolder {
    static VideoDataContext INSTANCE = new VideoDataContext();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\common\VideoDataContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */