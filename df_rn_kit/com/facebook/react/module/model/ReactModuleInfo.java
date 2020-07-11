package com.facebook.react.module.model;

public class ReactModuleInfo {
  private final boolean mCanOverrideExistingModule;
  
  private final boolean mHasConstants;
  
  private final String mName;
  
  private final boolean mNeedsEagerInit;
  
  public ReactModuleInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    this.mName = paramString;
    this.mCanOverrideExistingModule = paramBoolean1;
    this.mNeedsEagerInit = paramBoolean2;
    this.mHasConstants = paramBoolean3;
  }
  
  public boolean canOverrideExistingModule() {
    return this.mCanOverrideExistingModule;
  }
  
  public boolean hasConstants() {
    return this.mHasConstants;
  }
  
  public String name() {
    return this.mName;
  }
  
  public boolean needsEagerInit() {
    return this.mNeedsEagerInit;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\module\model\ReactModuleInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */