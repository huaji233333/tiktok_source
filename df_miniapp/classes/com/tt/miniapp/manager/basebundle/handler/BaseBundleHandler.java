package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;

public abstract class BaseBundleHandler {
  protected Context mContext;
  
  protected BaseBundleHandler mHandler;
  
  protected BundleHandlerParam mParam;
  
  public void finish() {
    handle(this.mContext, this.mParam);
  }
  
  public abstract BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam);
  
  public void setInitialParam(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    this.mContext = paramContext;
    paramBundleHandlerParam.isLastTaskSuccess = true;
    this.mParam = paramBundleHandlerParam;
  }
  
  public void setNextHandler(BaseBundleHandler paramBaseBundleHandler) {
    Context context = this.mContext;
    if (context != null) {
      BundleHandlerParam bundleHandlerParam = this.mParam;
      if (bundleHandlerParam != null) {
        paramBaseBundleHandler.mContext = context;
        paramBaseBundleHandler.mParam = handle(this.mContext, bundleHandlerParam);
        this.mHandler = paramBaseBundleHandler;
        return;
      } 
    } 
    throw new IllegalArgumentException("first handler not call setInitialParam, or param is null");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\BaseBundleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */