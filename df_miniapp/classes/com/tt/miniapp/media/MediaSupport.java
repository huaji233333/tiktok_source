package com.tt.miniapp.media;

import com.tt.miniapp.media.base.MediaEditListener;
import com.tt.miniapp.media.base.MediaEditParams;
import com.tt.miniapp.media.base.MediaEditor;

public class MediaSupport implements MediaEditor {
  private MediaEditor mEditor = ImplInjector.getMediaEditImpl();
  
  private MediaSupport() {}
  
  public static MediaSupport getInstance() {
    return InstanceHolder.INSTANCE;
  }
  
  public boolean cancel(int paramInt) {
    MediaEditor mediaEditor = this.mEditor;
    return (mediaEditor != null) ? mediaEditor.cancel(paramInt) : false;
  }
  
  public int edit(MediaEditParams paramMediaEditParams, MediaEditListener paramMediaEditListener) {
    StringBuilder stringBuilder;
    int i = getCurTaskNum();
    if (i >= 3) {
      stringBuilder = new StringBuilder("MediaEditor working busy, cur taskNum = ");
      stringBuilder.append(i);
      stringBuilder.append(", maxTaskNum = 3");
      paramMediaEditListener.onFail(-1002, stringBuilder.toString());
      return -1002;
    } 
    MediaEditor mediaEditor = this.mEditor;
    return (mediaEditor != null) ? mediaEditor.edit((MediaEditParams)stringBuilder, paramMediaEditListener) : -1000;
  }
  
  public int getCurTaskNum() {
    MediaEditor mediaEditor = this.mEditor;
    return (mediaEditor != null) ? mediaEditor.getCurTaskNum() : 0;
  }
  
  static class InstanceHolder {
    public static MediaSupport INSTANCE = new MediaSupport();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\media\MediaSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */