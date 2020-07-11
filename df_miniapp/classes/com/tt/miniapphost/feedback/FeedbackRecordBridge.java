package com.tt.miniapphost.feedback;

import com.tt.miniapp.feedback.screenrecord.ScreenRecordControl;

public class FeedbackRecordBridge {
  private static IFeedbackRecordControl getInstance() {
    return Holder.INSTANCE;
  }
  
  public static void start(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    if (getInstance() != null)
      getInstance().start(paramIFeedbackRecordCallback); 
  }
  
  public static void stop(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    if (getInstance() != null)
      getInstance().stop(paramIFeedbackRecordCallback); 
  }
  
  static class Holder {
    public static IFeedbackRecordControl INSTANCE = init();
    
    private static IFeedbackRecordControl init() {
      return (IFeedbackRecordControl)new ScreenRecordControl();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\feedback\FeedbackRecordBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */