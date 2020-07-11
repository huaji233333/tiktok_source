package com.tt.miniapp.feedback.screenrecord;

import java.io.IOException;

interface Encoder {
  void prepare() throws IOException;
  
  void release();
  
  void setCallback(Callback paramCallback);
  
  void stop();
  
  public static interface Callback {
    void onError(Encoder param1Encoder, Exception param1Exception);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */