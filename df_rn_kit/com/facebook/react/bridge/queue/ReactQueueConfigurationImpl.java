package com.facebook.react.bridge.queue;

import android.os.Looper;
import com.facebook.react.common.MapBuilder;
import java.util.HashMap;

public class ReactQueueConfigurationImpl implements ReactQueueConfiguration {
  private final MessageQueueThreadImpl mJSQueueThread;
  
  private final MessageQueueThreadImpl mLayoutThread;
  
  private final MessageQueueThreadImpl mNativeModulesQueueThread;
  
  private final MessageQueueThreadImpl mUIQueueThread;
  
  private ReactQueueConfigurationImpl(MessageQueueThreadImpl paramMessageQueueThreadImpl1, MessageQueueThreadImpl paramMessageQueueThreadImpl2, MessageQueueThreadImpl paramMessageQueueThreadImpl3, MessageQueueThreadImpl paramMessageQueueThreadImpl4) {
    this.mUIQueueThread = paramMessageQueueThreadImpl1;
    this.mNativeModulesQueueThread = paramMessageQueueThreadImpl2;
    this.mJSQueueThread = paramMessageQueueThreadImpl3;
    this.mLayoutThread = paramMessageQueueThreadImpl4;
  }
  
  public static ReactQueueConfigurationImpl create(ReactQueueConfigurationSpec paramReactQueueConfigurationSpec, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    HashMap<MessageQueueThreadSpec, MessageQueueThreadImpl> hashMap = MapBuilder.newHashMap();
    MessageQueueThreadSpec messageQueueThreadSpec = MessageQueueThreadSpec.mainThreadSpec();
    MessageQueueThreadImpl messageQueueThreadImpl5 = MessageQueueThreadImpl.create(messageQueueThreadSpec, paramQueueThreadExceptionHandler);
    hashMap.put(messageQueueThreadSpec, messageQueueThreadImpl5);
    MessageQueueThreadImpl messageQueueThreadImpl2 = hashMap.get(paramReactQueueConfigurationSpec.getJSQueueThreadSpec());
    MessageQueueThreadImpl messageQueueThreadImpl1 = messageQueueThreadImpl2;
    if (messageQueueThreadImpl2 == null)
      messageQueueThreadImpl1 = MessageQueueThreadImpl.create(paramReactQueueConfigurationSpec.getJSQueueThreadSpec(), paramQueueThreadExceptionHandler); 
    MessageQueueThreadImpl messageQueueThreadImpl3 = hashMap.get(paramReactQueueConfigurationSpec.getNativeModulesQueueThreadSpec());
    messageQueueThreadImpl2 = messageQueueThreadImpl3;
    if (messageQueueThreadImpl3 == null)
      messageQueueThreadImpl2 = MessageQueueThreadImpl.create(paramReactQueueConfigurationSpec.getNativeModulesQueueThreadSpec(), paramQueueThreadExceptionHandler); 
    MessageQueueThreadImpl messageQueueThreadImpl4 = hashMap.get(paramReactQueueConfigurationSpec.getLayoutThreadSpec());
    messageQueueThreadImpl3 = messageQueueThreadImpl4;
    if (messageQueueThreadImpl4 == null)
      messageQueueThreadImpl3 = MessageQueueThreadImpl.create(paramReactQueueConfigurationSpec.getLayoutThreadSpec(), paramQueueThreadExceptionHandler); 
    return new ReactQueueConfigurationImpl(messageQueueThreadImpl5, messageQueueThreadImpl2, messageQueueThreadImpl1, messageQueueThreadImpl3);
  }
  
  public void destroy() {
    if (this.mNativeModulesQueueThread.getLooper() != Looper.getMainLooper())
      this.mNativeModulesQueueThread.quitSynchronous(); 
    if (this.mJSQueueThread.getLooper() != Looper.getMainLooper())
      this.mJSQueueThread.quitSynchronous(); 
  }
  
  public MessageQueueThread getJSQueueThread() {
    return this.mJSQueueThread;
  }
  
  public MessageQueueThread getLayoutThread() {
    return this.mLayoutThread;
  }
  
  public MessageQueueThread getNativeModulesQueueThread() {
    return this.mNativeModulesQueueThread;
  }
  
  public MessageQueueThread getUIQueueThread() {
    return this.mUIQueueThread;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\ReactQueueConfigurationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */