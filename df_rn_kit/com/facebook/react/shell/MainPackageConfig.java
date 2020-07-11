package com.facebook.react.shell;

import com.facebook.imagepipeline.e.i;

public class MainPackageConfig {
  private i mFrescoConfig;
  
  private MainPackageConfig(Builder paramBuilder) {
    this.mFrescoConfig = paramBuilder.mFrescoConfig;
  }
  
  public i getFrescoConfig() {
    return this.mFrescoConfig;
  }
  
  public static class Builder {
    public i mFrescoConfig;
    
    public MainPackageConfig build() {
      return new MainPackageConfig(this);
    }
    
    public Builder setFrescoConfig(i param1i) {
      this.mFrescoConfig = param1i;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\shell\MainPackageConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */