package com.tt.miniapp.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.IAppbrandApplication;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.util.UIUtils;
import d.f.b.g;
import d.f.b.l;
import d.u;
import org.json.JSONObject;

public final class AppbrandTabController {
  public static final Companion Companion = new Companion(null);
  
  private View mBadgeMoreView;
  
  private TextView mBadgeTextView;
  
  private int mColor;
  
  private final Context mContext;
  
  public ImageView mImageView;
  
  private ImageView mRedDotView;
  
  public Bitmap mSelectDrawable;
  
  private int mSelectedColor;
  
  public String mSelectedIconPath;
  
  private final AppConfig.TabBar.TabContent mTabContent;
  
  public final int mTabIndex;
  
  public boolean mTabSelected;
  
  public String mText;
  
  private TextView mTextView;
  
  public Bitmap mUnSelectDrawable;
  
  private String mUnSelectedIconPath;
  
  private final View mView;
  
  public AppbrandTabController(View paramView, AppConfig.TabBar.TabContent paramTabContent, AppbrandTabHost.AppbrandTabInfo paramAppbrandTabInfo, int paramInt) {
    this.mView = paramView;
    this.mTabContent = paramTabContent;
    this.mTabIndex = paramInt;
    Context context = this.mView.getContext();
    l.a(context, "mView.context");
    this.mContext = context;
    this.mText = this.mTabContent.text;
    this.mColor = paramAppbrandTabInfo.getColor();
    this.mSelectedColor = paramAppbrandTabInfo.getSelectedColor();
    this.mUnSelectedIconPath = this.mTabContent.iconPath;
    this.mSelectedIconPath = this.mTabContent.selectedIconPath;
  }
  
  private final GradientDrawable getRedBadgeDrawable(Context paramContext) {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(0);
    NativeUIParamsEntity nativeUIParamsEntity = NativeUIParamsEntity.getInst();
    l.a(nativeUIParamsEntity, "NativeUIParamsEntity.getInst()");
    gradientDrawable.setColor(Color.parseColor(nativeUIParamsEntity.getTabDotColor()));
    gradientDrawable.setCornerRadius(UIUtils.dip2Px(paramContext, 6.0F));
    gradientDrawable.setStroke(1, Color.parseColor("#FFFFFF"));
    return gradientDrawable;
  }
  
  private final GradientDrawable getRedDotDrawable() {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setShape(1);
    NativeUIParamsEntity nativeUIParamsEntity = NativeUIParamsEntity.getInst();
    l.a(nativeUIParamsEntity, "NativeUIParamsEntity.getInst()");
    gradientDrawable.setColor(Color.parseColor(nativeUIParamsEntity.getTabDotColor()));
    gradientDrawable.setStroke(1, Color.parseColor("#FFFFFF"));
    return gradientDrawable;
  }
  
  private final int getTextLength(String paramString) {
    if (paramString != null) {
      char[] arrayOfChar = paramString.toCharArray();
      l.a(arrayOfChar, "(this as java.lang.String).toCharArray()");
      int k = arrayOfChar.length;
      int j = 0;
      int i = 0;
      while (j < k) {
        if (arrayOfChar[j] > '') {
          i += 2;
        } else {
          i++;
        } 
        j++;
      } 
      return i;
    } 
    u u = new u("null cannot be cast to non-null type java.lang.String");
    throw u;
  }
  
  private final void requestSelectDrawable(String paramString) {
    ThreadUtil.runOnWorkThread(new AppbrandTabController$requestSelectDrawable$1(paramString), Schedulers.longIO());
  }
  
  private final void requestUnSelectDrawable(String paramString) {
    ThreadUtil.runOnWorkThread(new AppbrandTabController$requestUnSelectDrawable$1(paramString), Schedulers.longIO());
  }
  
  private final void showBadgeMore(boolean paramBoolean) {
    ViewGroup.LayoutParams layoutParams1;
    View view2 = null;
    ViewGroup.LayoutParams layoutParams2 = null;
    if (paramBoolean) {
      view2 = this.mBadgeMoreView;
      if (view2 != null)
        view2.setVisibility(0); 
      textView3 = this.mBadgeTextView;
      if (textView3 != null)
        layoutParams2 = textView3.getLayoutParams(); 
      if (layoutParams2 != null)
        layoutParams2.width = (int)UIUtils.dip2Px(this.mContext, 18.0F); 
      textView3 = this.mBadgeTextView;
      if (textView3 != null)
        textView3.setLayoutParams(layoutParams2); 
      TextView textView = this.mBadgeTextView;
      if (textView != null)
        textView.setText(""); 
      textView = this.mBadgeTextView;
      if (textView != null)
        textView.setVisibility(0); 
      return;
    } 
    View view1 = this.mBadgeMoreView;
    if (view1 != null)
      view1.setVisibility(8); 
    TextView textView4 = this.mBadgeTextView;
    TextView textView2 = textView3;
    if (textView4 != null)
      layoutParams1 = textView4.getLayoutParams(); 
    if (layoutParams1 != null)
      layoutParams1.width = -2; 
    TextView textView3 = this.mBadgeTextView;
    if (textView3 != null)
      textView3.setLayoutParams(layoutParams1); 
    TextView textView1 = this.mBadgeTextView;
    if (textView1 != null)
      textView1.setVisibility(0); 
  }
  
  private final String trimText(char[] paramArrayOfchar) {
    boolean bool2;
    if (paramArrayOfchar.length == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (bool1)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    int k = paramArrayOfchar.length;
    int j = 0;
    int i = 0;
    boolean bool1 = false;
    while (true) {
      bool2 = bool1;
      if (j < k) {
        if (paramArrayOfchar[j] > 'ÿ') {
          i += 2;
        } else {
          i++;
        } 
        if (i >= 12) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        bool2 = bool1;
        if (!bool1) {
          stringBuilder.append(paramArrayOfchar[j]);
          j++;
          continue;
        } 
      } 
      break;
    } 
    if (!bool2) {
      String str1 = stringBuilder.toString();
      l.a(str1, "trimedText.toString()");
      return str1;
    } 
    stringBuilder.append("...");
    String str = stringBuilder.toString();
    l.a(str, "trimedText.append(AppCon…XT_TRIM_LEBEL).toString()");
    return str;
  }
  
  public final AppConfig.TabBar.TabContent getMTabContent() {
    return this.mTabContent;
  }
  
  public final void init() {
    this.mImageView = (ImageView)this.mView.findViewById(2097545393);
    this.mTextView = (TextView)this.mView.findViewById(2097545395);
    this.mRedDotView = (ImageView)this.mView.findViewById(2097545371);
    ImageView imageView = this.mRedDotView;
    if (imageView != null)
      imageView.setBackground((Drawable)getRedDotDrawable()); 
    this.mBadgeTextView = (TextView)this.mView.findViewById(2097545372);
    TextView textView = this.mBadgeTextView;
    if (textView != null)
      textView.setBackground((Drawable)getRedBadgeDrawable(this.mContext)); 
    this.mBadgeMoreView = this.mView.findViewById(2097545373);
    GestureDetector gestureDetector = new GestureDetector(this.mContext, (GestureDetector.OnGestureListener)new AppbrandTabController$init$gestureDetector$1());
    this.mView.setOnTouchListener(new AppbrandTabController$init$1(gestureDetector));
    setItem(this.mText, this.mUnSelectedIconPath, this.mSelectedIconPath);
  }
  
  public final void setBadge(boolean paramBoolean, String paramString) {
    View view;
    l.b(paramString, "text");
    if (!paramBoolean) {
      TextView textView = this.mBadgeTextView;
      if (textView != null)
        textView.setVisibility(8); 
      view = this.mBadgeMoreView;
      if (view != null)
        view.setVisibility(8); 
      return;
    } 
    CharSequence charSequence = (CharSequence)view;
    if (TextUtils.isEmpty(charSequence)) {
      showBadgeMore(true);
      return;
    } 
    setRedDotVisibility(false);
    try {
      int i = Integer.parseInt((String)view);
      if (i >= 0 && i <= 99) {
        showBadgeMore(false);
        TextView textView = this.mBadgeTextView;
        if (textView != null) {
          textView.setText((CharSequence)view);
          return;
        } 
      } else {
        showBadgeMore(true);
        return;
      } 
    } catch (NumberFormatException numberFormatException) {
      if (getTextLength((String)view) > 4) {
        showBadgeMore(true);
        return;
      } 
      showBadgeMore(false);
      TextView textView = this.mBadgeTextView;
      if (textView != null)
        textView.setText(charSequence); 
      return;
    } 
  }
  
  public final void setColor(int paramInt) {
    this.mColor = paramInt;
    if (!this.mTabSelected) {
      TextView textView = this.mTextView;
      if (textView != null)
        textView.setTextColor(this.mColor); 
    } 
  }
  
  public final void setItem(String paramString1, String paramString2, String paramString3) {
    AppBrandLogger.d("AppbrandTabController", new Object[] { "iconPath ", paramString2, " unSelectPath ", paramString3 });
    if (!TextUtils.isEmpty(paramString1)) {
      this.mText = paramString1;
      TextView textView = this.mTextView;
      if (textView != null)
        textView.setVisibility(0); 
      textView = this.mTextView;
      if (textView != null) {
        if (paramString1 == null)
          l.a(); 
        if (paramString1 != null) {
          char[] arrayOfChar = paramString1.toCharArray();
          l.a(arrayOfChar, "(this as java.lang.String).toCharArray()");
          textView.setText(trimText(arrayOfChar));
        } else {
          throw new u("null cannot be cast to non-null type java.lang.String");
        } 
      } 
    } 
    if (!TextUtils.isEmpty(paramString2))
      if (StreamLoader.findFile(paramString2) != null) {
        this.mUnSelectedIconPath = paramString2;
        if (paramString2 == null)
          l.a(); 
        requestUnSelectDrawable(paramString2);
      } else if (l.a(this.mUnSelectedIconPath, paramString2)) {
        this.mUnSelectedIconPath = null;
      }  
    if (!TextUtils.isEmpty(this.mUnSelectedIconPath) && !TextUtils.isEmpty(paramString3)) {
      if (StreamLoader.findFile(paramString3) != null) {
        this.mSelectedIconPath = paramString3;
        if (paramString3 == null)
          l.a(); 
        requestSelectDrawable(paramString3);
        return;
      } 
      if (l.a(this.mSelectedIconPath, paramString3))
        this.mSelectedIconPath = null; 
    } 
  }
  
  public final void setRedDotVisibility(boolean paramBoolean) {
    if (paramBoolean) {
      ImageView imageView1 = this.mRedDotView;
      if (imageView1 != null)
        imageView1.setVisibility(0); 
      TextView textView = this.mBadgeTextView;
      if (textView != null)
        textView.setVisibility(8); 
      View view = this.mBadgeMoreView;
      if (view != null)
        view.setVisibility(8); 
      return;
    } 
    ImageView imageView = this.mRedDotView;
    if (imageView != null)
      imageView.setVisibility(8); 
  }
  
  public final void setSelected() {
    this.mTabSelected = true;
    TextView textView = this.mTextView;
    if (textView != null)
      textView.setTextColor(this.mSelectedColor); 
    Bitmap bitmap = this.mSelectDrawable;
    if (bitmap != null) {
      ImageView imageView = this.mImageView;
      if (imageView != null)
        imageView.setImageBitmap(bitmap); 
    } 
  }
  
  public final void setSelectedColor(int paramInt) {
    this.mSelectedColor = paramInt;
    if (this.mTabSelected) {
      TextView textView = this.mTextView;
      if (textView != null)
        textView.setTextColor(this.mSelectedColor); 
    } 
  }
  
  public final void setUnSelected() {
    this.mTabSelected = false;
    TextView textView = this.mTextView;
    if (textView != null)
      textView.setTextColor(this.mColor); 
    Bitmap bitmap = this.mUnSelectDrawable;
    if (bitmap != null) {
      ImageView imageView = this.mImageView;
      if (imageView != null)
        imageView.setImageBitmap(bitmap); 
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class AppbrandTabController$init$1 implements View.OnTouchListener {
    AppbrandTabController$init$1(GestureDetector param1GestureDetector) {}
    
    public final boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      return this.$gestureDetector.onTouchEvent(param1MotionEvent);
    }
  }
  
  public static final class AppbrandTabController$init$gestureDetector$1 extends GestureDetector.SimpleOnGestureListener {
    public final boolean onDoubleTap(MotionEvent param1MotionEvent) {
      l.b(param1MotionEvent, "e");
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("index", AppbrandTabController.this.mTabIndex);
        jSONObject.put("pagePath", (AppbrandTabController.this.getMTabContent()).pagePath);
        jSONObject.put("text", AppbrandTabController.this.mText);
        AppBrandLogger.d("onTabbarDoubleClicked", new Object[] { jSONObject.toString() });
        IAppbrandApplication iAppbrandApplication = AppbrandApplication.getInst();
        l.a(iAppbrandApplication, "AppbrandApplication.getInst()");
        j j = iAppbrandApplication.getJsBridge();
        if (j == null)
          l.a(); 
        j.sendMsgToJsCore("onTabbarDoubleTap", jSONObject.toString());
      } catch (Exception exception) {
        AppBrandLogger.e("onTabbarDoubleClicked", new Object[] { "send msg to jscore: onTabbarDoubleTap", exception });
      } 
      return super.onDoubleTap(param1MotionEvent);
    }
    
    public final boolean onSingleTapConfirmed(MotionEvent param1MotionEvent) {
      l.b(param1MotionEvent, "e");
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("index", AppbrandTabController.this.mTabIndex);
        jSONObject.put("pagePath", (AppbrandTabController.this.getMTabContent()).pagePath);
        jSONObject.put("text", AppbrandTabController.this.mText);
        IAppbrandApplication iAppbrandApplication = AppbrandApplication.getInst();
        l.a(iAppbrandApplication, "AppbrandApplication.getInst()");
        j j = iAppbrandApplication.getJsBridge();
        if (j == null)
          l.a(); 
        j.sendMsgToJsCore("onTabItemTap", jSONObject.toString());
      } catch (Exception exception) {
        AppBrandLogger.e("AppbrandTabController", new Object[] { "send msg to jscore: onTabItemTap", exception });
      } 
      return super.onSingleTapConfirmed(param1MotionEvent);
    }
  }
  
  static final class AppbrandTabController$requestSelectDrawable$1 implements Action {
    AppbrandTabController$requestSelectDrawable$1(String param1String) {}
    
    public final void act() {
      byte[] arrayOfByte = StreamLoader.loadByteFromStream(this.$selectedIconPath);
      if (arrayOfByte != null) {
        boolean bool;
        if (arrayOfByte.length == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if ((bool ^ true) != 0) {
          AppbrandTabController.this.mSelectDrawable = ToolUtils.decodeByteArray(arrayOfByte);
          ThreadUtil.runOnUIThread(new Runnable() {
                public final void run() {
                  if (AppbrandTabController.this.mTabSelected) {
                    ImageView imageView = AppbrandTabController.this.mImageView;
                    if (imageView != null)
                      imageView.setImageBitmap(AppbrandTabController.this.mSelectDrawable); 
                  } 
                }
              });
        } 
      } 
    }
  }
  
  static final class null implements Runnable {
    public final void run() {
      if (AppbrandTabController.this.mTabSelected) {
        ImageView imageView = AppbrandTabController.this.mImageView;
        if (imageView != null)
          imageView.setImageBitmap(AppbrandTabController.this.mSelectDrawable); 
      } 
    }
  }
  
  static final class AppbrandTabController$requestUnSelectDrawable$1 implements Action {
    AppbrandTabController$requestUnSelectDrawable$1(String param1String) {}
    
    public final void act() {
      byte[] arrayOfByte = StreamLoader.loadByteFromStream(this.$iconPath);
      if (arrayOfByte != null) {
        boolean bool;
        if (arrayOfByte.length == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if ((bool ^ true) != 0) {
          AppbrandTabController.this.mUnSelectDrawable = ToolUtils.decodeByteArray(arrayOfByte);
          ThreadUtil.runOnUIThread(new Runnable() {
                public final void run() {
                  if (!AppbrandTabController.this.mTabSelected || TextUtils.isEmpty(AppbrandTabController.this.mSelectedIconPath)) {
                    ImageView imageView = AppbrandTabController.this.mImageView;
                    if (imageView != null)
                      imageView.setImageBitmap(AppbrandTabController.this.mUnSelectDrawable); 
                  } 
                }
              });
        } 
      } 
    }
  }
  
  static final class null implements Runnable {
    public final void run() {
      if (!AppbrandTabController.this.mTabSelected || TextUtils.isEmpty(AppbrandTabController.this.mSelectedIconPath)) {
        ImageView imageView = AppbrandTabController.this.mImageView;
        if (imageView != null)
          imageView.setImageBitmap(AppbrandTabController.this.mUnSelectDrawable); 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandTabController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */