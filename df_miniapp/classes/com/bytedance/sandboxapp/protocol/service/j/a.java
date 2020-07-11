package com.bytedance.sandboxapp.protocol.service.j;

import android.view.View;
import com.bytedance.sandboxapp.b.b;
import com.bytedance.sandboxapp.protocol.service.j.a.a;

public interface a extends b {
  void abandonAudioFocus(a parama);
  
  a.d acquireAudioFocus(a parama);
  
  void enterFullScreen(View paramView, a parama);
  
  void exitFullScreen(View paramView);
  
  public enum a {
    LANDSCAPE, PORTRAIT, REVERSE_LANDSCAPE, REVERSE_PORTRAIT, SENSOR_LANDSCAPE, SENSOR_PORTRAIT;
    
    static {
    
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\j\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */