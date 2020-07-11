package com.facebook.react.views.toolbar;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.facebook.common.d.i;
import com.facebook.drawee.a.a.c;
import com.facebook.drawee.a.a.e;
import com.facebook.drawee.c.c;
import com.facebook.drawee.c.d;
import com.facebook.drawee.e.q;
import com.facebook.drawee.f.a;
import com.facebook.drawee.f.b;
import com.facebook.drawee.h.a;
import com.facebook.drawee.h.b;
import com.facebook.drawee.view.b;
import com.facebook.drawee.view.c;
import com.facebook.imagepipeline.j.f;
import com.facebook.imagepipeline.j.h;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;

public class ReactToolbar extends Toolbar {
  private final c<a> mActionsHolder = new c();
  
  private final Runnable mLayoutRunnable = new Runnable() {
      public void run() {
        ReactToolbar reactToolbar = ReactToolbar.this;
        reactToolbar.measure(View.MeasureSpec.makeMeasureSpec(reactToolbar.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactToolbar.this.getHeight(), 1073741824));
        reactToolbar = ReactToolbar.this;
        reactToolbar.layout(reactToolbar.getLeft(), ReactToolbar.this.getTop(), ReactToolbar.this.getRight(), ReactToolbar.this.getBottom());
      }
    };
  
  private IconControllerListener mLogoControllerListener;
  
  private final b mLogoHolder;
  
  private IconControllerListener mNavIconControllerListener;
  
  private final b mNavIconHolder;
  
  private IconControllerListener mOverflowIconControllerListener;
  
  private final b mOverflowIconHolder;
  
  public ReactToolbar(Context paramContext) {
    super(paramContext);
    this.mLogoHolder = b.a((b)createDraweeHierarchy(), paramContext);
    this.mNavIconHolder = b.a((b)createDraweeHierarchy(), paramContext);
    this.mOverflowIconHolder = b.a((b)createDraweeHierarchy(), paramContext);
    this.mLogoControllerListener = new IconControllerListener(this.mLogoHolder) {
        protected void setDrawable(Drawable param1Drawable) {
          ReactToolbar.this.setLogo(param1Drawable);
        }
      };
    this.mNavIconControllerListener = new IconControllerListener(this.mNavIconHolder) {
        protected void setDrawable(Drawable param1Drawable) {
          ReactToolbar.this.setNavigationIcon(param1Drawable);
        }
      };
    this.mOverflowIconControllerListener = new IconControllerListener(this.mOverflowIconHolder) {
        protected void setDrawable(Drawable param1Drawable) {
          ReactToolbar.this.setOverflowIcon(param1Drawable);
        }
      };
  }
  
  private void attachDraweeHolders() {
    this.mLogoHolder.b();
    this.mNavIconHolder.b();
    this.mOverflowIconHolder.b();
    c<a> c1 = this.mActionsHolder;
    if (!c1.a) {
      c1.a = true;
      for (int i = 0; i < c1.b.size(); i++)
        ((b)c1.b.get(i)).b(); 
    } 
  }
  
  private a createDraweeHierarchy() {
    return (new b(getResources())).e(q.b.c).a(0).a();
  }
  
  private void detachDraweeHolders() {
    this.mLogoHolder.c();
    this.mNavIconHolder.c();
    this.mOverflowIconHolder.c();
    c<a> c1 = this.mActionsHolder;
    if (c1.a) {
      int i = 0;
      c1.a = false;
      while (i < c1.b.size()) {
        ((b)c1.b.get(i)).c();
        i++;
      } 
    } 
  }
  
  private Drawable getDrawableByName(String paramString) {
    return (getDrawableResourceByName(paramString) != 0) ? getResources().getDrawable(getDrawableResourceByName(paramString)) : null;
  }
  
  private int getDrawableResourceByName(String paramString) {
    return getResources().getIdentifier(paramString, "drawable", getContext().getPackageName());
  }
  
  private IconImageInfo getIconImageInfo(ReadableMap paramReadableMap) {
    return (paramReadableMap.hasKey("width") && paramReadableMap.hasKey("height")) ? new IconImageInfo(Math.round(PixelUtil.toPixelFromDIP(paramReadableMap.getInt("width"))), Math.round(PixelUtil.toPixelFromDIP(paramReadableMap.getInt("height")))) : null;
  }
  
  private void setIconSource(ReadableMap paramReadableMap, IconControllerListener paramIconControllerListener, b paramb) {
    String str;
    if (paramReadableMap != null) {
      str = paramReadableMap.getString("uri");
    } else {
      str = null;
    } 
    if (str == null) {
      paramIconControllerListener.setIconImageInfo(null);
      paramIconControllerListener.setDrawable(null);
      return;
    } 
    if (str.startsWith("http://") || str.startsWith("https://") || str.startsWith("file://")) {
      paramIconControllerListener.setIconImageInfo(getIconImageInfo(paramReadableMap));
      paramb.a((a)((e)((e)c.a().a(Uri.parse(str)).a((d)paramIconControllerListener)).a(paramb.c)).c());
      paramb.e().setVisible(true, true);
      return;
    } 
    paramIconControllerListener.setDrawable(getDrawableByName(str));
  }
  
  private void setMenuItemIcon(MenuItem paramMenuItem, ReadableMap paramReadableMap) {
    b b1 = b.a((b)createDraweeHierarchy(), getContext());
    ActionIconControllerListener actionIconControllerListener = new ActionIconControllerListener(paramMenuItem, b1);
    actionIconControllerListener.setIconImageInfo(getIconImageInfo(paramReadableMap));
    setIconSource(paramReadableMap, actionIconControllerListener, b1);
    c<a> c1 = this.mActionsHolder;
    int i = c1.b.size();
    i.a(b1);
    i.a(i, c1.b.size() + 1);
    c1.b.add(i, b1);
    if (c1.a)
      b1.b(); 
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    attachDraweeHolders();
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    detachDraweeHolders();
  }
  
  public void onFinishTemporaryDetach() {
    super.onFinishTemporaryDetach();
    attachDraweeHolders();
  }
  
  public void onStartTemporaryDetach() {
    super.onStartTemporaryDetach();
    detachDraweeHolders();
  }
  
  public void requestLayout() {
    super.requestLayout();
    post(this.mLayoutRunnable);
  }
  
  void setActions(ReadableArray paramReadableArray) {
    Menu menu = getMenu();
    menu.clear();
    c<a> c1 = this.mActionsHolder;
    if (c1.a)
      for (int i = 0; i < c1.b.size(); i++)
        ((b)c1.b.get(i)).c();  
    c1.b.clear();
    if (paramReadableArray != null)
      for (int i = 0; i < paramReadableArray.size(); i++) {
        byte b1;
        ReadableMap readableMap = paramReadableArray.getMap(i);
        MenuItem menuItem = menu.add(0, 0, i, readableMap.getString("title"));
        if (readableMap.hasKey("icon"))
          setMenuItemIcon(menuItem, readableMap.getMap("icon")); 
        if (readableMap.hasKey("show")) {
          b1 = readableMap.getInt("show");
        } else {
          b1 = 0;
        } 
        int j = b1;
        if (readableMap.hasKey("showWithText")) {
          j = b1;
          if (readableMap.getBoolean("showWithText"))
            j = b1 | 0x4; 
        } 
        menuItem.setShowAsAction(j);
      }  
  }
  
  void setLogoSource(ReadableMap paramReadableMap) {
    setIconSource(paramReadableMap, this.mLogoControllerListener, this.mLogoHolder);
  }
  
  void setNavIconSource(ReadableMap paramReadableMap) {
    setIconSource(paramReadableMap, this.mNavIconControllerListener, this.mNavIconHolder);
  }
  
  void setOverflowIconSource(ReadableMap paramReadableMap) {
    setIconSource(paramReadableMap, this.mOverflowIconControllerListener, this.mOverflowIconHolder);
  }
  
  class ActionIconControllerListener extends IconControllerListener {
    private final MenuItem mItem;
    
    ActionIconControllerListener(MenuItem param1MenuItem, b param1b) {
      super(param1b);
      this.mItem = param1MenuItem;
    }
    
    protected void setDrawable(Drawable param1Drawable) {
      this.mItem.setIcon(param1Drawable);
      ReactToolbar.this.requestLayout();
    }
  }
  
  abstract class IconControllerListener extends c<f> {
    private final b mHolder;
    
    private ReactToolbar.IconImageInfo mIconImageInfo;
    
    public IconControllerListener(b param1b) {
      this.mHolder = param1b;
    }
    
    public void onFinalImageSet(String param1String, f param1f, Animatable param1Animatable) {
      super.onFinalImageSet(param1String, param1f, param1Animatable);
      ReactToolbar.IconImageInfo iconImageInfo = this.mIconImageInfo;
      if (iconImageInfo != null)
        param1f = iconImageInfo; 
      setDrawable((Drawable)new DrawableWithIntrinsicSize(this.mHolder.e(), param1f));
    }
    
    protected abstract void setDrawable(Drawable param1Drawable);
    
    public void setIconImageInfo(ReactToolbar.IconImageInfo param1IconImageInfo) {
      this.mIconImageInfo = param1IconImageInfo;
    }
  }
  
  static class IconImageInfo implements f {
    private int mHeight;
    
    private int mWidth;
    
    public IconImageInfo(int param1Int1, int param1Int2) {
      this.mWidth = param1Int1;
      this.mHeight = param1Int2;
    }
    
    public int getHeight() {
      return this.mHeight;
    }
    
    public h getQualityInfo() {
      return null;
    }
    
    public int getWidth() {
      return this.mWidth;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\toolbar\ReactToolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */