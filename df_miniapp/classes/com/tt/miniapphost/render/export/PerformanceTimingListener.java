package com.tt.miniapphost.render.export;

import com.tt.miniapphost.render.internal.InterfaceConverter.ConvertToMethod;

public interface PerformanceTimingListener {
  @ConvertToMethod("onCustomTagNotify")
  void onCustomTagNotify(String paramString);
  
  @ConvertToMethod("onDOMContentLoaded")
  void onDOMContentLoaded();
  
  @ConvertToMethod("onFirstContentfulPaint")
  void onFirstContentfulPaint();
  
  @ConvertToMethod("onFirstMeaningfulPaint")
  void onFirstMeaningfulPaint();
  
  @ConvertToMethod("onFirstScreenPaint")
  void onFirstScreenPaint();
  
  @ConvertToMethod("onReceivedResponse")
  void onReceivedResponse(String paramString);
  
  @ConvertToMethod("onReceivedSpecialEvent")
  void onReceivedSpecialEvent(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\render\export\PerformanceTimingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */