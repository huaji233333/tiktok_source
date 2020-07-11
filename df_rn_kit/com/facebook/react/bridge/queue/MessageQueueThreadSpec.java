package com.facebook.react.bridge.queue;

public class MessageQueueThreadSpec {
  private static final MessageQueueThreadSpec MAIN_UI_SPEC = new MessageQueueThreadSpec(ThreadType.MAIN_UI, "main_ui");
  
  private final String mName;
  
  private final long mStackSize;
  
  private final ThreadType mThreadType;
  
  private MessageQueueThreadSpec(ThreadType paramThreadType, String paramString) {
    this(paramThreadType, paramString, 0L);
  }
  
  private MessageQueueThreadSpec(ThreadType paramThreadType, String paramString, long paramLong) {
    this.mThreadType = paramThreadType;
    this.mName = paramString;
    this.mStackSize = paramLong;
  }
  
  public static MessageQueueThreadSpec mainThreadSpec() {
    return MAIN_UI_SPEC;
  }
  
  public static MessageQueueThreadSpec newBackgroundThreadSpec(String paramString) {
    return new MessageQueueThreadSpec(ThreadType.NEW_BACKGROUND, paramString);
  }
  
  public static MessageQueueThreadSpec newBackgroundThreadSpec(String paramString, long paramLong) {
    return new MessageQueueThreadSpec(ThreadType.NEW_BACKGROUND, paramString, paramLong);
  }
  
  public static MessageQueueThreadSpec newLayoutThreadSpec(String paramString, long paramLong) {
    return new MessageQueueThreadSpec(ThreadType.LAYOUT, paramString, paramLong);
  }
  
  public static MessageQueueThreadSpec newUIBackgroundTreadSpec(String paramString) {
    return new MessageQueueThreadSpec(ThreadType.NEW_BACKGROUND, paramString);
  }
  
  public String getName() {
    return this.mName;
  }
  
  public long getStackSize() {
    return this.mStackSize;
  }
  
  public ThreadType getThreadType() {
    return this.mThreadType;
  }
  
  protected enum ThreadType {
    LAYOUT, MAIN_UI, NEW_BACKGROUND;
    
    static {
      $VALUES = new ThreadType[] { MAIN_UI, NEW_BACKGROUND, LAYOUT };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\MessageQueueThreadSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */