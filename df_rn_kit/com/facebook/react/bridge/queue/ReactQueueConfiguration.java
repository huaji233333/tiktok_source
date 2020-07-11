package com.facebook.react.bridge.queue;

public interface ReactQueueConfiguration {
  void destroy();
  
  MessageQueueThread getJSQueueThread();
  
  MessageQueueThread getLayoutThread();
  
  MessageQueueThread getNativeModulesQueueThread();
  
  MessageQueueThread getUIQueueThread();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\ReactQueueConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */