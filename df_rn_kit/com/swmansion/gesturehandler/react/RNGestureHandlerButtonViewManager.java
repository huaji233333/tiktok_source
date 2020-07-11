package com.swmansion.gesturehandler.react;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RNGestureHandlerButtonViewManager extends ViewGroupManager<RNGestureHandlerButtonViewManager.a> {
  public a createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new a((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "RNGestureHandlerButton";
  }
  
  protected void onAfterUpdateTransaction(a parama) {
    if (parama.g) {
      parama.g = false;
      if (parama.c == 0)
        parama.setBackground(null); 
      if (Build.VERSION.SDK_INT >= 23)
        parama.setForeground(null); 
      if (parama.d && Build.VERSION.SDK_INT >= 23) {
        parama.setForeground(parama.a());
        if (parama.c != 0) {
          parama.setBackgroundColor(parama.c);
          return;
        } 
      } else {
        if (parama.c == 0) {
          parama.setBackground(parama.a());
          return;
        } 
        PaintDrawable paintDrawable = new PaintDrawable(parama.c);
        Drawable drawable = parama.a();
        if (parama.f != 0.0F) {
          paintDrawable.setCornerRadius(parama.f);
          if (Build.VERSION.SDK_INT >= 21 && drawable instanceof RippleDrawable) {
            PaintDrawable paintDrawable1 = new PaintDrawable(-1);
            paintDrawable1.setCornerRadius(parama.f);
            ((RippleDrawable)drawable).setDrawableByLayerId(16908334, (Drawable)paintDrawable1);
          } 
        } 
        parama.setBackground((Drawable)new LayerDrawable(new Drawable[] { (Drawable)paintDrawable, drawable }));
      } 
    } 
  }
  
  @ReactProp(name = "borderRadius")
  public void setBorderRadius(a parama, float paramFloat) {
    parama.f = paramFloat * (parama.getResources().getDisplayMetrics()).density;
    parama.g = true;
  }
  
  @ReactProp(name = "borderless")
  public void setBorderless(a parama, boolean paramBoolean) {
    parama.e = paramBoolean;
  }
  
  @ReactProp(name = "enabled")
  public void setEnabled(a parama, boolean paramBoolean) {
    parama.setEnabled(paramBoolean);
  }
  
  @ReactProp(name = "foreground")
  public void setForeground(a parama, boolean paramBoolean) {
    parama.d = paramBoolean;
    parama.g = true;
  }
  
  static final class a extends ViewGroup {
    static TypedValue a = new TypedValue();
    
    static a b;
    
    int c;
    
    boolean d;
    
    boolean e;
    
    float f;
    
    boolean g;
    
    public a(Context param1Context) {
      super(param1Context);
      setClickable(true);
      setFocusable(true);
      this.g = true;
    }
    
    Drawable a() {
      String str;
      int i = Build.VERSION.SDK_INT;
      if (this.e && i >= 21) {
        str = "selectableItemBackgroundBorderless";
      } else {
        str = "selectableItemBackground";
      } 
      int j = getResources().getIdentifier(str, "attr", "android");
      getContext().getTheme().resolveAttribute(j, a, true);
      return (i >= 21) ? getResources().getDrawable(a.resourceId, getContext().getTheme()) : getResources().getDrawable(a.resourceId);
    }
    
    public final void dispatchDrawableHotspotChanged(float param1Float1, float param1Float2) {}
    
    public final void drawableHotspotChanged(float param1Float1, float param1Float2) {
      a a1 = b;
      if (a1 == null || a1 == this)
        super.drawableHotspotChanged(param1Float1, param1Float2); 
    }
    
    public final boolean onInterceptTouchEvent(MotionEvent param1MotionEvent) {
      if (super.onInterceptTouchEvent(param1MotionEvent))
        return true; 
      onTouchEvent(param1MotionEvent);
      return isPressed();
    }
    
    protected final void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public final void setBackgroundColor(int param1Int) {
      this.c = param1Int;
      this.g = true;
    }
    
    public final void setPressed(boolean param1Boolean) {
      if (param1Boolean && b == null)
        b = this; 
      if (!param1Boolean || b == this)
        super.setPressed(param1Boolean); 
      if (!param1Boolean && b == this)
        b = null; 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\RNGestureHandlerButtonViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */