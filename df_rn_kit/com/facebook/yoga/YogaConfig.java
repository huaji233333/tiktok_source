package com.facebook.yoga;

public class YogaConfig {
  public static int SPACING_TYPE = 1;
  
  private YogaLogger mLogger;
  
  long mNativePointer = jni_YGConfigNew();
  
  private YogaNodeClonedFunction mNodeClonedFunction;
  
  public YogaConfig() {
    if (this.mNativePointer != 0L)
      return; 
    throw new IllegalStateException("Failed to allocate native memory");
  }
  
  private native void jni_YGConfigFree(long paramLong);
  
  private native long jni_YGConfigNew();
  
  private native void jni_YGConfigSetExperimentalFeatureEnabled(long paramLong, int paramInt, boolean paramBoolean);
  
  private native void jni_YGConfigSetHasNodeClonedFunc(long paramLong, boolean paramBoolean);
  
  private native void jni_YGConfigSetLogger(long paramLong, Object paramObject);
  
  private native void jni_YGConfigSetPointScaleFactor(long paramLong, float paramFloat);
  
  private native void jni_YGConfigSetUseLegacyStretchBehaviour(long paramLong, boolean paramBoolean);
  
  private native void jni_YGConfigSetUseWebDefaults(long paramLong, boolean paramBoolean);
  
  protected void finalize() throws Throwable {
    try {
      jni_YGConfigFree(this.mNativePointer);
      return;
    } finally {
      super.finalize();
    } 
  }
  
  public YogaLogger getLogger() {
    return this.mLogger;
  }
  
  public final void onNodeCloned(YogaNode paramYogaNode1, YogaNode paramYogaNode2, YogaNode paramYogaNode3, int paramInt) {
    this.mNodeClonedFunction.onNodeCloned(paramYogaNode1, paramYogaNode2, paramYogaNode3, paramInt);
  }
  
  public void setExperimentalFeatureEnabled(YogaExperimentalFeature paramYogaExperimentalFeature, boolean paramBoolean) {
    jni_YGConfigSetExperimentalFeatureEnabled(this.mNativePointer, paramYogaExperimentalFeature.intValue(), paramBoolean);
  }
  
  public void setLogger(YogaLogger paramYogaLogger) {
    this.mLogger = paramYogaLogger;
    jni_YGConfigSetLogger(this.mNativePointer, paramYogaLogger);
  }
  
  public void setOnNodeCloned(YogaNodeClonedFunction paramYogaNodeClonedFunction) {
    boolean bool;
    this.mNodeClonedFunction = paramYogaNodeClonedFunction;
    long l = this.mNativePointer;
    if (paramYogaNodeClonedFunction != null) {
      bool = true;
    } else {
      bool = false;
    } 
    jni_YGConfigSetHasNodeClonedFunc(l, bool);
  }
  
  public void setPointScaleFactor(float paramFloat) {
    jni_YGConfigSetPointScaleFactor(this.mNativePointer, paramFloat);
  }
  
  public void setUseLegacyStretchBehaviour(boolean paramBoolean) {
    jni_YGConfigSetUseLegacyStretchBehaviour(this.mNativePointer, paramBoolean);
  }
  
  public void setUseWebDefaults(boolean paramBoolean) {
    jni_YGConfigSetUseWebDefaults(this.mNativePointer, paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */