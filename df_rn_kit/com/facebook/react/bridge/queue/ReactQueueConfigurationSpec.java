package com.facebook.react.bridge.queue;

import android.os.Build;
import com.facebook.i.a.a;

public class ReactQueueConfigurationSpec {
  private final MessageQueueThreadSpec mJSQueueThreadSpec;
  
  private final MessageQueueThreadSpec mLayoutThreadSpec;
  
  private final MessageQueueThreadSpec mNativeModulesQueueThreadSpec;
  
  private ReactQueueConfigurationSpec(MessageQueueThreadSpec paramMessageQueueThreadSpec1, MessageQueueThreadSpec paramMessageQueueThreadSpec2, MessageQueueThreadSpec paramMessageQueueThreadSpec3) {
    this.mNativeModulesQueueThreadSpec = paramMessageQueueThreadSpec1;
    this.mJSQueueThreadSpec = paramMessageQueueThreadSpec2;
    this.mLayoutThreadSpec = paramMessageQueueThreadSpec3;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public static ReactQueueConfigurationSpec createDefault() {
    MessageQueueThreadSpec messageQueueThreadSpec;
    if (Build.VERSION.SDK_INT < 21) {
      messageQueueThreadSpec = MessageQueueThreadSpec.newBackgroundThreadSpec("native_modules", 2000000L);
    } else {
      messageQueueThreadSpec = MessageQueueThreadSpec.newBackgroundThreadSpec("native_modules");
    } 
    return builder().setJSQueueThreadSpec(MessageQueueThreadSpec.newBackgroundThreadSpec("js")).setNativeModulesQueueThreadSpec(messageQueueThreadSpec).setLayouthreadSpec(MessageQueueThreadSpec.newLayoutThreadSpec("layout", 2000000L)).build();
  }
  
  public MessageQueueThreadSpec getJSQueueThreadSpec() {
    return this.mJSQueueThreadSpec;
  }
  
  public MessageQueueThreadSpec getLayoutThreadSpec() {
    return this.mLayoutThreadSpec;
  }
  
  public MessageQueueThreadSpec getNativeModulesQueueThreadSpec() {
    return this.mNativeModulesQueueThreadSpec;
  }
  
  public static class Builder {
    private MessageQueueThreadSpec mJSQueueSpec;
    
    private MessageQueueThreadSpec mLayoutSpec;
    
    private MessageQueueThreadSpec mNativeModulesQueueSpec;
    
    public ReactQueueConfigurationSpec build() {
      return new ReactQueueConfigurationSpec((MessageQueueThreadSpec)a.b(this.mNativeModulesQueueSpec), (MessageQueueThreadSpec)a.b(this.mJSQueueSpec), (MessageQueueThreadSpec)a.b(this.mLayoutSpec));
    }
    
    public Builder setJSQueueThreadSpec(MessageQueueThreadSpec param1MessageQueueThreadSpec) {
      boolean bool;
      if (this.mJSQueueSpec == null) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool, "Setting JS queue multiple times!");
      this.mJSQueueSpec = param1MessageQueueThreadSpec;
      return this;
    }
    
    public Builder setLayouthreadSpec(MessageQueueThreadSpec param1MessageQueueThreadSpec) {
      boolean bool;
      if (this.mLayoutSpec == null) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool, "Setting layout thread multiple times!");
      this.mLayoutSpec = param1MessageQueueThreadSpec;
      return this;
    }
    
    public Builder setNativeModulesQueueThreadSpec(MessageQueueThreadSpec param1MessageQueueThreadSpec) {
      boolean bool;
      if (this.mNativeModulesQueueSpec == null) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool, "Setting native modules queue spec multiple times!");
      this.mNativeModulesQueueSpec = param1MessageQueueThreadSpec;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\ReactQueueConfigurationSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */