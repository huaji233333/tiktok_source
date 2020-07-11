package com.facebook.react.flat;

import com.facebook.drawee.a.a.c;
import com.facebook.drawee.c.b;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.views.image.GlobalImageLoadListener;

public final class RCTImageViewManager extends FlatViewManager {
  private final Object mCallerContext;
  
  private b mDraweeControllerBuilder;
  
  private GlobalImageLoadListener mGlobalImageLoadListener;
  
  public RCTImageViewManager() {
    this(null, null);
  }
  
  public RCTImageViewManager(b paramb, GlobalImageLoadListener paramGlobalImageLoadListener, Object paramObject) {
    this.mDraweeControllerBuilder = paramb;
    this.mGlobalImageLoadListener = paramGlobalImageLoadListener;
    this.mCallerContext = paramObject;
  }
  
  public RCTImageViewManager(b paramb, Object paramObject) {
    this(paramb, null, paramObject);
  }
  
  public final RCTImageView createShadowNodeInstance() {
    return new RCTImageView<DrawImageWithDrawee>(new DrawImageWithDrawee(this.mGlobalImageLoadListener));
  }
  
  public final Object getCallerContext() {
    return this.mCallerContext;
  }
  
  public final b getDraweeControllerBuilder() {
    if (this.mDraweeControllerBuilder == null)
      this.mDraweeControllerBuilder = (b)c.a(); 
    return this.mDraweeControllerBuilder;
  }
  
  public final String getName() {
    return "RCTImageView";
  }
  
  public final Class<RCTImageView> getShadowNodeClass() {
    return RCTImageView.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTImageViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */