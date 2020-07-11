package com.facebook.react.flat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.c.a;
import com.facebook.drawee.c.b;
import com.facebook.drawee.c.d;
import com.facebook.drawee.f.a;
import com.facebook.drawee.f.b;
import com.facebook.drawee.h.a;
import com.facebook.drawee.h.b;
import com.facebook.i.a.a;
import com.facebook.imagepipeline.o.b;

final class DraweeRequestHelper {
  private static b sControllerBuilder;
  
  private static b sHierarchyBuilder;
  
  private int mAttachCounter;
  
  private final a mDraweeController;
  
  DraweeRequestHelper(b paramb1, b paramb2, d paramd) {
    b b1 = sControllerBuilder.b(paramb1).a(RCTImageView.getCallerContext()).a(paramd);
    if (paramb2 != null)
      b1.c(paramb2); 
    a a1 = b1.c();
    a1.a((b)sHierarchyBuilder.a());
    this.mDraweeController = (a)a1;
  }
  
  static void setDraweeControllerBuilder(b paramb) {
    sControllerBuilder = paramb;
  }
  
  static void setResources(Resources paramResources) {
    sHierarchyBuilder = new b(paramResources);
  }
  
  final void attach(FlatViewGroup.InvalidateCallback paramInvalidateCallback) {
    this.mAttachCounter++;
    if (this.mAttachCounter == 1) {
      getDrawable().setCallback((Drawable.Callback)paramInvalidateCallback.get());
      this.mDraweeController.f();
    } 
  }
  
  final void detach() {
    this.mAttachCounter--;
    if (this.mAttachCounter == 0)
      this.mDraweeController.g(); 
  }
  
  final Drawable getDrawable() {
    return getHierarchy().a();
  }
  
  final a getHierarchy() {
    return (a)a.a(this.mDraweeController.e());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DraweeRequestHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */