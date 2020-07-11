package com.facebook.g.a.a;

import android.graphics.Picture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import com.facebook.g.a.a;

public final class a implements a {
  private static a a;
  
  public final void a(Layout paramLayout) {
    if (a == null) {
      HandlerThread handlerThread = new HandlerThread("GlyphWarmer");
      handlerThread.start();
      a = new a(handlerThread.getLooper());
    } 
    a a1 = a;
    a1.sendMessage(a1.obtainMessage(1, paramLayout));
  }
  
  static final class a extends Handler {
    private final Picture a = new Picture();
    
    public a(Looper param1Looper) {
      super(param1Looper);
    }
    
    public final void handleMessage(Message param1Message) {
      Layout layout = (Layout)param1Message.obj;
      try {
        Picture picture = this.a;
        int i = 0;
        int j = 0;
        if (layout != null) {
          int k = layout.getLineCount();
          i = 0;
          while (j < k) {
            i = Math.max(i, (int)layout.getLineRight(j));
            j++;
          } 
        } 
        layout.draw(picture.beginRecording(i, com.facebook.g.a.b.a.a(layout)));
        this.a.endRecording();
        return;
      } catch (Exception exception) {
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\g\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */