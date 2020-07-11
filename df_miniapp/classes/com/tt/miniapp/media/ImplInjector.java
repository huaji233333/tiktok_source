package com.tt.miniapp.media;

import com.tt.miniapp.media.base.MediaEditor;
import com.tt.miniapp.media.impl.MediaEditImpl;

public class ImplInjector {
  public static MediaEditor getMediaEditImpl() {
    return (MediaEditor)new MediaEditImpl();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\media\ImplInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */