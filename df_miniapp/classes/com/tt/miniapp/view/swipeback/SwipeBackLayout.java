package com.tt.miniapp.view.swipeback;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.u;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.tt.miniapp.map.AppbrandMapActivity;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.util.UIUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
  public Context context;
  
  public boolean disableBackPress = false;
  
  float dy = 0.0F;
  
  private EdgeLevel edgeLevel;
  
  public boolean isFromActivityStackBack = false;
  
  private boolean isPointerDown;
  
  public FragmentActivity mActivity;
  
  private boolean mCheckBackgroundViewWhenTouch = false;
  
  public View mContentView;
  
  public int mCurrentSwipeOrientation;
  
  public int mEdgeFlag;
  
  private volatile boolean mEnable = true;
  
  public SwipeBackFragment mFragment;
  
  public ViewDragHelper mHelper;
  
  public List<OnSwipeListener> mListeners;
  
  public Fragment mPreFragment;
  
  private float mScrimOpacity;
  
  public float mScrollFinishThreshold = 0.25F;
  
  public float mScrollPercent;
  
  public Drawable mShadowLeft;
  
  public Drawable mShadowRight;
  
  public int mTargetOffsetX = 0;
  
  private Rect mTmpRect = new Rect();
  
  float y_init = 0.0F;
  
  public SwipeBackLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SwipeBackLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SwipeBackLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.context = paramContext;
    init();
  }
  
  private void drawScrim(Canvas paramCanvas, View paramView) {
    int i = (int)(this.mScrimOpacity * 153.0F);
    int j = this.mCurrentSwipeOrientation;
    if ((j & 0x1) != 0) {
      paramCanvas.clipRect(0, 0, paramView.getLeft(), getHeight());
    } else if ((j & 0x2) != 0) {
      paramCanvas.clipRect(paramView.getRight(), 0, getRight(), getHeight());
    } 
    paramCanvas.drawColor(i << 24);
  }
  
  private void drawShadow(Canvas paramCanvas, View paramView) {
    Rect rect = this.mTmpRect;
    paramView.getHitRect(rect);
    int i = this.mCurrentSwipeOrientation;
    if ((i & 0x1) != 0) {
      this.mShadowLeft.setBounds(rect.left - this.mShadowLeft.getIntrinsicWidth(), rect.top, rect.left, rect.bottom);
      this.mShadowLeft.setAlpha((int)(this.mScrimOpacity * 255.0F));
      this.mShadowLeft.draw(paramCanvas);
      return;
    } 
    if ((i & 0x2) != 0) {
      this.mShadowRight.setBounds(rect.right, rect.top, rect.right + this.mShadowRight.getIntrinsicWidth(), rect.bottom);
      this.mShadowRight.setAlpha((int)(this.mScrimOpacity * 255.0F));
      this.mShadowRight.draw(paramCanvas);
    } 
  }
  
  private void init() {
    this.mHelper = ViewDragHelper.create((ViewGroup)this, new ViewDragCallback());
    if (!UIUtils.isRTL((Context)AppbrandContext.getInst().getApplicationContext())) {
      setShadow(2097479785, 1);
      setEdgeOrientation(1);
    } else {
      setShadow(2097479785, 2);
      setEdgeOrientation(2);
    } 
    setEdgeLevel(EdgeLevel.MIN);
    setBackground(null);
  }
  
  private boolean isEnableReceiveTouch() {
    return (this.mEnable && (!this.mCheckBackgroundViewWhenTouch || getBackground() != null));
  }
  
  private void setContentView(View paramView) {
    this.mContentView = paramView;
  }
  
  private void validateEdgeLevel(int paramInt, EdgeLevel paramEdgeLevel) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
    if (paramInt != 0) {
      this.mHelper.setmEdgeSize(paramInt);
      return;
    } 
    if (paramEdgeLevel == EdgeLevel.MAX) {
      this.mHelper.setmEdgeSize(displayMetrics.widthPixels);
      return;
    } 
    if (paramEdgeLevel == EdgeLevel.MED) {
      this.mHelper.setmEdgeSize(displayMetrics.widthPixels / 2);
      return;
    } 
    if (paramEdgeLevel == EdgeLevel.MIN)
      this.mHelper.setmEdgeSize((int)(displayMetrics.density * 60.0F + 0.5F)); 
  }
  
  public void addSwipeListener(OnSwipeListener paramOnSwipeListener) {
    if (this.mListeners == null)
      this.mListeners = new ArrayList<OnSwipeListener>(); 
    this.mListeners.add(paramOnSwipeListener);
  }
  
  public void attachToActivity(FragmentActivity paramFragmentActivity) {
    this.mActivity = paramFragmentActivity;
    this.mCheckBackgroundViewWhenTouch = this.mActivity instanceof MiniappHostBase;
    TypedArray typedArray = paramFragmentActivity.getTheme().obtainStyledAttributes(new int[] { 16842836 });
    int i = typedArray.getResourceId(0, 0);
    typedArray.recycle();
    ViewGroup viewGroup1 = (ViewGroup)paramFragmentActivity.getWindow().getDecorView();
    ViewGroup viewGroup2 = (ViewGroup)viewGroup1.getChildAt(0);
    viewGroup2.setBackgroundResource(i);
    viewGroup1.removeView((View)viewGroup2);
    addView((View)viewGroup2);
    setContentView((View)viewGroup2);
    viewGroup1.addView((View)this);
  }
  
  public void attachToFragment(final SwipeBackFragment swipeBackFragment, View paramView) {
    if (this.mEnable) {
      swipeBackFragment.setSwipeBackEnable(false);
      AppbrandContext.mainHandler.postDelayed(new Runnable() {
            public void run() {
              swipeBackFragment.setSwipeBackEnable(true);
            }
          },  300L);
    } 
    addView(paramView);
    setFragment(swipeBackFragment, paramView);
  }
  
  public void computeScroll() {
    this.mScrimOpacity = 1.0F - this.mScrollPercent;
    if (this.mScrimOpacity >= 0.0F && this.mHelper.continueSettling(true))
      u.d((View)this); 
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    boolean bool;
    if (paramView == this.mContentView) {
      bool = true;
    } else {
      bool = false;
    } 
    boolean bool1 = super.drawChild(paramCanvas, paramView, paramLong);
    if (bool && this.mScrimOpacity > 0.0F && this.mHelper.getViewDragState() != 0) {
      drawShadow(paramCanvas, paramView);
      drawScrim(paramCanvas, paramView);
    } 
    return bool1;
  }
  
  public EdgeLevel getEdgeLevel() {
    return this.edgeLevel;
  }
  
  public void hiddenFragment() {
    Fragment fragment = this.mPreFragment;
    if (fragment != null && fragment.getView() != null)
      this.mPreFragment.getView().setVisibility(8); 
  }
  
  public boolean isEnableGesture() {
    return this.mEnable;
  }
  
  void layoutChildren(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
    int i = getChildCount();
    int j = paramInt3 - paramInt1;
    paramInt4 -= paramInt2;
    for (paramInt3 = 0; paramInt3 < i; paramInt3++) {
      View view = getChildAt(paramInt3);
      if (view.getVisibility() != 8) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        int k = view.getMeasuredWidth();
        int m = view.getMeasuredHeight();
        paramInt2 = layoutParams.gravity;
        paramInt1 = paramInt2;
        if (paramInt2 == -1)
          paramInt1 = 8388659; 
        paramInt2 = Gravity.getAbsoluteGravity(paramInt1, getLayoutDirection());
        int i1 = paramInt1 & 0x70;
        paramInt1 = paramInt2 & 0x7;
        if (paramInt1 != 1) {
          if (paramInt1 == 5 && !paramBoolean) {
            paramInt1 = j - k - layoutParams.rightMargin;
            paramInt2 = this.mTargetOffsetX;
          } else {
            paramInt1 = layoutParams.leftMargin + 0;
            paramInt2 = this.mTargetOffsetX;
          } 
        } else {
          paramInt1 = (j + 0 - k) / 2 + 0 + layoutParams.leftMargin - layoutParams.rightMargin;
          paramInt2 = this.mTargetOffsetX;
        } 
        int n = paramInt1 + paramInt2;
        if (i1 != 16) {
          if (i1 != 48) {
            if (i1 != 80) {
              paramInt1 = layoutParams.topMargin;
            } else {
              paramInt2 = paramInt4 - m;
              paramInt1 = layoutParams.bottomMargin;
              paramInt1 = paramInt2 - paramInt1;
            } 
          } else {
            paramInt1 = layoutParams.topMargin;
          } 
          paramInt1 += 0;
        } else {
          paramInt2 = (paramInt4 + 0 - m) / 2 + 0 + layoutParams.topMargin;
          paramInt1 = layoutParams.bottomMargin;
          paramInt1 = paramInt2 - paramInt1;
        } 
        view.layout(n, paramInt1, k + n, m + paramInt1);
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    if (!isEnableReceiveTouch())
      return super.onInterceptTouchEvent(paramMotionEvent); 
    boolean bool1 = this.mHelper.shouldInterceptTouchEvent(paramMotionEvent);
    boolean bool2 = this.mHelper.isEdgeTouched(1, paramMotionEvent.getPointerId(0));
    if (paramMotionEvent.getAction() == 0)
      this.y_init = paramMotionEvent.getY(); 
    if (this.mActivity instanceof AppbrandMapActivity) {
      int i = paramMotionEvent.getAction();
      if (i != 0) {
        if (i == 1 && this.isPointerDown) {
          this.isPointerDown = false;
          return false;
        } 
      } else if (!bool2) {
        this.isPointerDown = true;
      } 
      if (this.isPointerDown)
        return false; 
      boolean bool = ((AppbrandMapActivity)this.mActivity).isInBackArea(paramMotionEvent.getX(), paramMotionEvent.getY());
      if (bool2 && !bool)
        return true; 
    } 
    return bool1;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    layoutChildren(paramInt1, paramInt2, paramInt3, paramInt4, false);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    try {
      if (!isEnableReceiveTouch())
        return super.onTouchEvent(paramMotionEvent); 
      if (paramMotionEvent.getAction() == 1)
        this.dy = paramMotionEvent.getY() - this.y_init; 
      this.mHelper.processTouchEvent(paramMotionEvent);
      return true;
    } catch (IllegalArgumentException illegalArgumentException) {
      return true;
    } 
  }
  
  public void removeSwipeListener(OnSwipeListener paramOnSwipeListener) {
    List<OnSwipeListener> list = this.mListeners;
    if (list == null)
      return; 
    list.remove(paramOnSwipeListener);
  }
  
  public void resetOffsetX() {
    this.mTargetOffsetX = 0;
  }
  
  public void setEdgeLevel(int paramInt) {
    validateEdgeLevel(paramInt, (EdgeLevel)null);
  }
  
  public void setEdgeLevel(EdgeLevel paramEdgeLevel) {
    this.edgeLevel = paramEdgeLevel;
    validateEdgeLevel(0, paramEdgeLevel);
  }
  
  public void setEdgeOrientation(int paramInt) {
    this.mEdgeFlag = paramInt;
    this.mHelper.setEdgeTrackingEnabled(paramInt);
    if (paramInt == 2 || paramInt == 3)
      setShadow(2097479786, 2); 
  }
  
  public void setEnableGesture(boolean paramBoolean) {
    if (this.mEnable == paramBoolean)
      return; 
    AppBrandLogger.d("SwipeBackLayout", new Object[] { "setEnableGesture", Boolean.valueOf(paramBoolean) });
    this.mEnable = paramBoolean;
    if (paramBoolean) {
      FragmentActivity fragmentActivity = this.mActivity;
      if (fragmentActivity instanceof MiniappHostBase) {
        ((MiniappHostBase)fragmentActivity).notifyUpdateSnapShot();
        return;
      } 
    } else {
      resetOffsetX();
      if (getBackground() != null)
        ThreadUtil.runOnUIThread(new Runnable() {
              public void run() {
                SwipeBackLayout.this.setBackground(null);
              }
            },  300L); 
    } 
  }
  
  public void setFragment(SwipeBackFragment paramSwipeBackFragment, View paramView) {
    this.mFragment = paramSwipeBackFragment;
    this.mContentView = paramView;
  }
  
  public void setScrollThresHold(float paramFloat) {
    if (paramFloat < 1.0F && paramFloat > 0.0F) {
      this.mScrollFinishThreshold = paramFloat;
      return;
    } 
    throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
  }
  
  public void setShadow(int paramInt1, int paramInt2) {
    setShadow(this.context.getResources().getDrawable(paramInt1), paramInt2);
  }
  
  public void setShadow(Drawable paramDrawable, int paramInt) {
    if ((paramInt & 0x1) != 0) {
      this.mShadowLeft = paramDrawable;
    } else if ((paramInt & 0x2) != 0) {
      this.mShadowRight = paramDrawable;
    } 
    invalidate();
  }
  
  public void updateBackgroundView(BitmapDrawable paramBitmapDrawable) {
    if (paramBitmapDrawable == null || !this.mEnable) {
      AppBrandLogger.d("SwipeBackLayout", new Object[] { "clearBackground" });
      resetOffsetX();
      setBackground(null);
      return;
    } 
    AppBrandLogger.d("SwipeBackLayout", new Object[] { "updateBackground" });
    setBackground((Drawable)paramBitmapDrawable);
  }
  
  public enum EdgeLevel {
    MAX, MED, MIN;
    
    static {
      $VALUES = new EdgeLevel[] { MAX, MIN, MED };
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface EdgeOrientation {}
  
  public static interface OnSwipeListener {
    void onDragScrolled(float param1Float);
    
    void onDragStateChange(int param1Int);
    
    void onEdgeTouch(int param1Int);
  }
  
  class ViewDragCallback extends ViewDragHelper.Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      int i = SwipeBackLayout.this.mCurrentSwipeOrientation;
      param1Int2 = 0;
      if ((i & 0x1) != 0)
        return Math.min(param1View.getWidth(), Math.max(param1Int1, 0)); 
      if ((SwipeBackLayout.this.mCurrentSwipeOrientation & 0x2) != 0)
        param1Int2 = Math.min(0, Math.max(param1Int1, -param1View.getWidth())); 
      return param1Int2;
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return (SwipeBackLayout.this.mFragment != null) ? 1 : ((SwipeBackLayout.this.mActivity != null && SwipeBackLayout.this.mActivity instanceof SwipeBackActivity && ((SwipeBackActivity)SwipeBackLayout.this.mActivity).swipeBackPriority()) ? 1 : 0);
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {
      super.onEdgeTouched(param1Int1, param1Int2);
      if ((SwipeBackLayout.this.mEdgeFlag & param1Int1) != 0)
        SwipeBackLayout.this.mCurrentSwipeOrientation = param1Int1; 
    }
    
    public void onViewDragStateChanged(int param1Int) {
      super.onViewDragStateChanged(param1Int);
      if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
        Iterator<SwipeBackLayout.OnSwipeListener> iterator = SwipeBackLayout.this.mListeners.iterator();
        while (iterator.hasNext())
          ((SwipeBackLayout.OnSwipeListener)iterator.next()).onDragStateChange(param1Int); 
      } 
    }
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: iload_2
      //   3: iload_3
      //   4: iload #4
      //   6: iload #5
      //   8: invokespecial onViewPositionChanged : (Landroid/view/View;IIII)V
      //   11: aload_0
      //   12: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   15: getfield mCurrentSwipeOrientation : I
      //   18: iconst_1
      //   19: iand
      //   20: ifeq -> 65
      //   23: aload_0
      //   24: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   27: astore_1
      //   28: aload_1
      //   29: iload_2
      //   30: i2f
      //   31: aload_1
      //   32: invokevirtual getWidth : ()I
      //   35: aload_0
      //   36: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   39: getfield mShadowLeft : Landroid/graphics/drawable/Drawable;
      //   42: invokevirtual getIntrinsicWidth : ()I
      //   45: iadd
      //   46: i2f
      //   47: fdiv
      //   48: invokestatic abs : (F)F
      //   51: putfield mScrollPercent : F
      //   54: aload_0
      //   55: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   58: iload_2
      //   59: putfield mTargetOffsetX : I
      //   62: goto -> 111
      //   65: aload_0
      //   66: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   69: getfield mCurrentSwipeOrientation : I
      //   72: iconst_2
      //   73: iand
      //   74: ifeq -> 111
      //   77: aload_0
      //   78: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   81: astore_1
      //   82: aload_1
      //   83: iload_2
      //   84: i2f
      //   85: aload_1
      //   86: getfield mContentView : Landroid/view/View;
      //   89: invokevirtual getWidth : ()I
      //   92: aload_0
      //   93: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   96: getfield mShadowRight : Landroid/graphics/drawable/Drawable;
      //   99: invokevirtual getIntrinsicWidth : ()I
      //   102: iadd
      //   103: i2f
      //   104: fdiv
      //   105: invokestatic abs : (F)F
      //   108: putfield mScrollPercent : F
      //   111: aload_0
      //   112: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   115: getfield mScrollPercent : F
      //   118: f2d
      //   119: dconst_1
      //   120: dcmpl
      //   121: iflt -> 135
      //   124: aload_0
      //   125: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   128: iconst_0
      //   129: putfield disableBackPress : Z
      //   132: goto -> 167
      //   135: aload_0
      //   136: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   139: getfield mScrollPercent : F
      //   142: f2d
      //   143: dconst_0
      //   144: dcmpl
      //   145: ifne -> 159
      //   148: aload_0
      //   149: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   152: iconst_0
      //   153: putfield disableBackPress : Z
      //   156: goto -> 167
      //   159: aload_0
      //   160: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   163: iconst_1
      //   164: putfield disableBackPress : Z
      //   167: ldc 'SwipeBackLayout'
      //   169: iconst_2
      //   170: anewarray java/lang/Object
      //   173: dup
      //   174: iconst_0
      //   175: ldc 'mScrollPercent:'
      //   177: aastore
      //   178: dup
      //   179: iconst_1
      //   180: aload_0
      //   181: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   184: getfield mScrollPercent : F
      //   187: invokestatic valueOf : (F)Ljava/lang/Float;
      //   190: aastore
      //   191: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   194: aload_0
      //   195: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   198: invokevirtual invalidate : ()V
      //   201: aload_0
      //   202: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   205: getfield mListeners : Ljava/util/List;
      //   208: ifnull -> 310
      //   211: aload_0
      //   212: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   215: getfield mListeners : Ljava/util/List;
      //   218: invokeinterface isEmpty : ()Z
      //   223: ifne -> 310
      //   226: aload_0
      //   227: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   230: getfield mHelper : Lcom/tt/miniapp/view/swipeback/ViewDragHelper;
      //   233: invokevirtual getViewDragState : ()I
      //   236: iconst_1
      //   237: if_icmpne -> 310
      //   240: aload_0
      //   241: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   244: getfield mScrollPercent : F
      //   247: fconst_1
      //   248: fcmpg
      //   249: ifgt -> 310
      //   252: aload_0
      //   253: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   256: getfield mScrollPercent : F
      //   259: fconst_0
      //   260: fcmpl
      //   261: ifle -> 310
      //   264: aload_0
      //   265: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   268: getfield mListeners : Ljava/util/List;
      //   271: invokeinterface iterator : ()Ljava/util/Iterator;
      //   276: astore_1
      //   277: aload_1
      //   278: invokeinterface hasNext : ()Z
      //   283: ifeq -> 310
      //   286: aload_1
      //   287: invokeinterface next : ()Ljava/lang/Object;
      //   292: checkcast com/tt/miniapp/view/swipeback/SwipeBackLayout$OnSwipeListener
      //   295: aload_0
      //   296: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   299: getfield mScrollPercent : F
      //   302: invokeinterface onDragScrolled : (F)V
      //   307: goto -> 277
      //   310: aload_0
      //   311: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   314: getfield mScrollPercent : F
      //   317: fconst_1
      //   318: fcmpl
      //   319: ifle -> 714
      //   322: invokestatic debug : ()Z
      //   325: ifeq -> 508
      //   328: aload_0
      //   329: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   332: getfield mCurrentSwipeOrientation : I
      //   335: iconst_1
      //   336: iand
      //   337: ifeq -> 404
      //   340: bipush #6
      //   342: anewarray java/lang/Object
      //   345: astore #6
      //   347: aload #6
      //   349: iconst_0
      //   350: ldc 'left:'
      //   352: aastore
      //   353: aload #6
      //   355: iconst_1
      //   356: iload_2
      //   357: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   360: aastore
      //   361: aload #6
      //   363: iconst_2
      //   364: ldc 'getWidth():'
      //   366: aastore
      //   367: aload #6
      //   369: iconst_3
      //   370: aload_0
      //   371: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   374: invokevirtual getWidth : ()I
      //   377: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   380: aastore
      //   381: aload #6
      //   383: iconst_4
      //   384: ldc 'mShadowLeft.getIntrinsicWidth():'
      //   386: aastore
      //   387: aload_0
      //   388: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   391: getfield mShadowLeft : Landroid/graphics/drawable/Drawable;
      //   394: invokevirtual getIntrinsicWidth : ()I
      //   397: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   400: astore_1
      //   401: goto -> 496
      //   404: aload_0
      //   405: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   408: getfield mCurrentSwipeOrientation : I
      //   411: iconst_2
      //   412: iand
      //   413: ifeq -> 508
      //   416: bipush #6
      //   418: anewarray java/lang/Object
      //   421: astore #6
      //   423: aload #6
      //   425: iconst_0
      //   426: ldc 'left:'
      //   428: aastore
      //   429: aload #6
      //   431: iconst_1
      //   432: iload_2
      //   433: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   436: aastore
      //   437: aload #6
      //   439: iconst_2
      //   440: ldc 'mContentView.getWidth() :'
      //   442: aastore
      //   443: aload #6
      //   445: iconst_3
      //   446: aload_0
      //   447: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   450: getfield mContentView : Landroid/view/View;
      //   453: invokevirtual getWidth : ()I
      //   456: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   459: aastore
      //   460: aload #6
      //   462: iconst_4
      //   463: ldc 'mShadowLeft.getIntrinsicWidth():'
      //   465: aastore
      //   466: aload_0
      //   467: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   470: getfield mShadowLeft : Landroid/graphics/drawable/Drawable;
      //   473: ifnull -> 493
      //   476: aload_0
      //   477: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   480: getfield mShadowLeft : Landroid/graphics/drawable/Drawable;
      //   483: invokevirtual getIntrinsicWidth : ()I
      //   486: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   489: astore_1
      //   490: goto -> 496
      //   493: ldc ''
      //   495: astore_1
      //   496: aload #6
      //   498: iconst_5
      //   499: aload_1
      //   500: aastore
      //   501: ldc 'SwipeBackLayout'
      //   503: aload #6
      //   505: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   508: aload_0
      //   509: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   512: getfield mFragment : Lcom/tt/miniapp/view/swipeback/SwipeBackFragment;
      //   515: ifnull -> 623
      //   518: aload_0
      //   519: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   522: getfield mPreFragment : Landroid/support/v4/app/Fragment;
      //   525: instanceof com/tt/miniapp/view/swipeback/SwipeBackFragment
      //   528: ifeq -> 545
      //   531: aload_0
      //   532: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   535: getfield mPreFragment : Landroid/support/v4/app/Fragment;
      //   538: checkcast com/tt/miniapp/view/swipeback/SwipeBackFragment
      //   541: iconst_1
      //   542: putfield mLocking : Z
      //   545: aload_0
      //   546: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   549: getfield mFragment : Lcom/tt/miniapp/view/swipeback/SwipeBackFragment;
      //   552: invokevirtual isDetached : ()Z
      //   555: ifne -> 587
      //   558: aload_0
      //   559: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   562: getfield mFragment : Lcom/tt/miniapp/view/swipeback/SwipeBackFragment;
      //   565: iconst_1
      //   566: putfield mLocking : Z
      //   569: aload_0
      //   570: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   573: invokevirtual resetOffsetX : ()V
      //   576: aload_0
      //   577: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   580: getfield mFragment : Lcom/tt/miniapp/view/swipeback/SwipeBackFragment;
      //   583: iconst_0
      //   584: putfield mLocking : Z
      //   587: aload_0
      //   588: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   591: getfield mPreFragment : Landroid/support/v4/app/Fragment;
      //   594: instanceof com/tt/miniapp/view/swipeback/SwipeBackFragment
      //   597: ifeq -> 614
      //   600: aload_0
      //   601: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   604: getfield mPreFragment : Landroid/support/v4/app/Fragment;
      //   607: checkcast com/tt/miniapp/view/swipeback/SwipeBackFragment
      //   610: iconst_0
      //   611: putfield mLocking : Z
      //   614: aload_0
      //   615: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   618: iconst_0
      //   619: putfield isFromActivityStackBack : Z
      //   622: return
      //   623: aload_0
      //   624: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   627: getfield mHelper : Lcom/tt/miniapp/view/swipeback/ViewDragHelper;
      //   630: invokevirtual getViewDragState : ()I
      //   633: ifne -> 637
      //   636: return
      //   637: aload_0
      //   638: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   641: getfield mHelper : Lcom/tt/miniapp/view/swipeback/ViewDragHelper;
      //   644: iconst_0
      //   645: invokevirtual setDragState : (I)V
      //   648: aload_0
      //   649: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   652: getfield mActivity : Landroid/support/v4/app/FragmentActivity;
      //   655: invokevirtual isFinishing : ()Z
      //   658: ifne -> 714
      //   661: ldc 'slide'
      //   663: putstatic com/tt/miniapp/view/swipeback/EventParamsValue.PARAMS_EXIT_TYPE : Ljava/lang/String;
      //   666: iconst_0
      //   667: putstatic com/tt/miniapp/view/swipeback/EventParamsValue.IS_OTHER_FLAG : Z
      //   670: aload_0
      //   671: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   674: getfield mActivity : Landroid/support/v4/app/FragmentActivity;
      //   677: bipush #10
      //   679: invokestatic onActivityExit : (Landroid/app/Activity;I)V
      //   682: aload_0
      //   683: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   686: getfield mActivity : Landroid/support/v4/app/FragmentActivity;
      //   689: instanceof com/tt/miniapphost/MiniappHostBase
      //   692: ifeq -> 714
      //   695: aload_0
      //   696: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   699: iconst_1
      //   700: putfield isFromActivityStackBack : Z
      //   703: new com/tt/miniapp/view/swipeback/SwipeBackLayout$ViewDragCallback$1
      //   706: dup
      //   707: aload_0
      //   708: invokespecial <init> : (Lcom/tt/miniapp/view/swipeback/SwipeBackLayout$ViewDragCallback;)V
      //   711: invokestatic runOnUIThread : (Ljava/lang/Runnable;)V
      //   714: return
    }
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
      // Byte code:
      //   0: aload_1
      //   1: ifnonnull -> 5
      //   4: return
      //   5: aload_1
      //   6: invokevirtual getWidth : ()I
      //   9: istore #4
      //   11: aload_0
      //   12: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   15: getfield mCurrentSwipeOrientation : I
      //   18: iconst_1
      //   19: iand
      //   20: ifeq -> 74
      //   23: fload_2
      //   24: fconst_0
      //   25: fcmpl
      //   26: ifgt -> 53
      //   29: fload_2
      //   30: fconst_0
      //   31: fcmpl
      //   32: ifne -> 138
      //   35: aload_0
      //   36: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   39: getfield mScrollPercent : F
      //   42: aload_0
      //   43: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   46: getfield mScrollFinishThreshold : F
      //   49: fcmpl
      //   50: ifle -> 138
      //   53: iload #4
      //   55: aload_0
      //   56: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   59: getfield mShadowLeft : Landroid/graphics/drawable/Drawable;
      //   62: invokevirtual getIntrinsicWidth : ()I
      //   65: iadd
      //   66: bipush #10
      //   68: iadd
      //   69: istore #4
      //   71: goto -> 141
      //   74: aload_0
      //   75: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   78: getfield mCurrentSwipeOrientation : I
      //   81: iconst_2
      //   82: iand
      //   83: ifeq -> 138
      //   86: fload_2
      //   87: fconst_0
      //   88: fcmpg
      //   89: iflt -> 116
      //   92: fload_2
      //   93: fconst_0
      //   94: fcmpl
      //   95: ifne -> 138
      //   98: aload_0
      //   99: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   102: getfield mScrollPercent : F
      //   105: aload_0
      //   106: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   109: getfield mScrollFinishThreshold : F
      //   112: fcmpl
      //   113: ifle -> 138
      //   116: iload #4
      //   118: aload_0
      //   119: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   122: getfield mShadowRight : Landroid/graphics/drawable/Drawable;
      //   125: invokevirtual getIntrinsicWidth : ()I
      //   128: iadd
      //   129: bipush #10
      //   131: iadd
      //   132: ineg
      //   133: istore #4
      //   135: goto -> 141
      //   138: iconst_0
      //   139: istore #4
      //   141: iload #4
      //   143: istore #5
      //   145: iload #4
      //   147: ifle -> 205
      //   150: iload #4
      //   152: istore #5
      //   154: aload_0
      //   155: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   158: getfield mTargetOffsetX : I
      //   161: aload_0
      //   162: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   165: getfield context : Landroid/content/Context;
      //   168: invokestatic getScreenWidth : (Landroid/content/Context;)I
      //   171: iconst_4
      //   172: idiv
      //   173: if_icmpge -> 205
      //   176: iload #4
      //   178: istore #5
      //   180: aload_0
      //   181: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   184: getfield dy : F
      //   187: invokestatic abs : (F)F
      //   190: aload_0
      //   191: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   194: getfield mTargetOffsetX : I
      //   197: i2f
      //   198: fcmpl
      //   199: ifle -> 205
      //   202: iconst_0
      //   203: istore #5
      //   205: aload_0
      //   206: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   209: getfield mHelper : Lcom/tt/miniapp/view/swipeback/ViewDragHelper;
      //   212: iload #5
      //   214: iconst_0
      //   215: invokevirtual settleCapturedViewAt : (II)Z
      //   218: pop
      //   219: aload_0
      //   220: getfield this$0 : Lcom/tt/miniapp/view/swipeback/SwipeBackLayout;
      //   223: invokevirtual invalidate : ()V
      //   226: return
    }
    
    public boolean tryCaptureView(View param1View, int param1Int) {
      boolean bool = SwipeBackLayout.this.mHelper.isEdgeTouched(SwipeBackLayout.this.mEdgeFlag, param1Int);
      if (bool) {
        if (SwipeBackLayout.this.mHelper.isEdgeTouched(1, param1Int)) {
          SwipeBackLayout.this.mCurrentSwipeOrientation = 1;
        } else if (SwipeBackLayout.this.mHelper.isEdgeTouched(2, param1Int)) {
          SwipeBackLayout.this.mCurrentSwipeOrientation = 2;
        } 
        if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
          Iterator<SwipeBackLayout.OnSwipeListener> iterator = SwipeBackLayout.this.mListeners.iterator();
          while (iterator.hasNext())
            ((SwipeBackLayout.OnSwipeListener)iterator.next()).onEdgeTouch(SwipeBackLayout.this.mCurrentSwipeOrientation); 
        } 
        if (SwipeBackLayout.this.mPreFragment == null) {
          if (SwipeBackLayout.this.mFragment != null) {
            List<Fragment> list = SwipeBackLayout.this.mFragment.getFragmentManager().f();
            if (list != null && list.size() > 1)
              for (param1Int = list.indexOf(SwipeBackLayout.this.mFragment) - 1; param1Int >= 0; param1Int--) {
                Fragment fragment = list.get(param1Int);
                if (fragment != null && fragment.getView() != null) {
                  fragment.getView().setVisibility(0);
                  SwipeBackLayout.this.mPreFragment = fragment;
                  return bool;
                } 
              }  
          } 
        } else {
          param1View = SwipeBackLayout.this.mPreFragment.getView();
          if (param1View != null && param1View.getVisibility() != 0)
            param1View.setVisibility(0); 
        } 
      } 
      return bool;
    }
  }
  
  class null implements Runnable {
    public void run() {
      AppBrandLogger.i("SwipeBackLayout", new Object[] { "resetSwipeViewAfterSwipeBack" });
      View view = SwipeBackLayout.this.getChildAt(0);
      if (view != null) {
        view.setVisibility(4);
        if (view.getX() > 0.0F)
          view.layout(0, view.getTop(), view.getRight() - view.getLeft(), view.getBottom()); 
      } 
      SwipeBackLayout.this.resetOffsetX();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\swipeback\SwipeBackLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */