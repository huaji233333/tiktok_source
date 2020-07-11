package com.tt.miniapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launch.MiniAppLaunchConfig;
import com.tt.miniapp.titlebar.TitleBarControl;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;

public class MiniAppContainerView extends SizeDetectFrameLayout {
  public static boolean isClickOutside;
  
  private MiniappHostBase mActivity;
  
  private boolean mBlockTouchEvent;
  
  public boolean mCloseOnClickOutside;
  
  public int mContainerHeight;
  
  private int mCurrentState = 1;
  
  private float mDownRawY;
  
  private boolean mEnableScrollInContainer;
  
  private boolean mFirstSizeChanged = true;
  
  private int mFloatRootViewGravity;
  
  boolean mHideStatusBarWhenFloat;
  
  public int mInitHeight;
  
  private boolean mInterceptTouchHandle;
  
  private boolean mIsFloatStyle;
  
  private boolean mIsTouchDownOnTitleBar;
  
  private int mMovingOffset;
  
  private TitleBarControl mTitleBarControl;
  
  private int mTouchSlop;
  
  public MiniAppContainerView(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public MiniAppContainerView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public MiniAppContainerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  public MiniAppContainerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init();
  }
  
  private void adjustVisibleSize() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mMovingOffset : I
    //   4: ifne -> 12
    //   7: aload_0
    //   8: invokevirtual resetState : ()V
    //   11: return
    //   12: aload_0
    //   13: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   16: astore #4
    //   18: aload #4
    //   20: getfield height : I
    //   23: aload_0
    //   24: getfield mContainerHeight : I
    //   27: if_icmpne -> 31
    //   30: return
    //   31: aload_0
    //   32: getfield mActivity : Lcom/tt/miniapphost/MiniappHostBase;
    //   35: invokevirtual isFilledUpContainer : ()Z
    //   38: ifeq -> 88
    //   41: aload #4
    //   43: getfield height : I
    //   46: aload_0
    //   47: getfield mInitHeight : I
    //   50: isub
    //   51: sipush #-200
    //   54: if_icmpgt -> 69
    //   57: aload_0
    //   58: getfield mActivity : Lcom/tt/miniapphost/MiniappHostBase;
    //   61: bipush #9
    //   63: invokestatic onActivityExit : (Landroid/app/Activity;I)V
    //   66: goto -> 113
    //   69: aload_0
    //   70: getfield mContainerHeight : I
    //   73: aload #4
    //   75: getfield height : I
    //   78: isub
    //   79: sipush #200
    //   82: if_icmpge -> 141
    //   85: goto -> 136
    //   88: aload #4
    //   90: getfield height : I
    //   93: aload_0
    //   94: getfield mInitHeight : I
    //   97: isub
    //   98: sipush #-200
    //   101: if_icmpgt -> 120
    //   104: aload_0
    //   105: getfield mActivity : Lcom/tt/miniapphost/MiniappHostBase;
    //   108: bipush #9
    //   110: invokestatic onActivityExit : (Landroid/app/Activity;I)V
    //   113: iconst_0
    //   114: istore_1
    //   115: iconst_0
    //   116: istore_2
    //   117: goto -> 145
    //   120: aload #4
    //   122: getfield height : I
    //   125: aload_0
    //   126: getfield mInitHeight : I
    //   129: isub
    //   130: sipush #200
    //   133: if_icmplt -> 141
    //   136: iconst_1
    //   137: istore_1
    //   138: goto -> 115
    //   141: iconst_0
    //   142: istore_1
    //   143: iconst_1
    //   144: istore_2
    //   145: aconst_null
    //   146: astore_3
    //   147: iload_1
    //   148: ifeq -> 203
    //   151: aload_0
    //   152: getstatic android/view/View.Y : Landroid/util/Property;
    //   155: iconst_2
    //   156: newarray float
    //   158: dup
    //   159: iconst_0
    //   160: aload_0
    //   161: getfield mContainerHeight : I
    //   164: aload #4
    //   166: getfield height : I
    //   169: isub
    //   170: i2f
    //   171: fastore
    //   172: dup
    //   173: iconst_1
    //   174: fconst_0
    //   175: fastore
    //   176: invokestatic ofFloat : (Ljava/lang/Object;Landroid/util/Property;[F)Landroid/animation/ObjectAnimator;
    //   179: ldc2_w 300
    //   182: invokevirtual setDuration : (J)Landroid/animation/ObjectAnimator;
    //   185: astore_3
    //   186: aload_3
    //   187: new com/tt/miniapp/view/MiniAppContainerView$1
    //   190: dup
    //   191: aload_0
    //   192: aload #4
    //   194: invokespecial <init> : (Lcom/tt/miniapp/view/MiniAppContainerView;Landroid/view/ViewGroup$LayoutParams;)V
    //   197: invokevirtual addListener : (Landroid/animation/Animator$AnimatorListener;)V
    //   200: goto -> 265
    //   203: iload_2
    //   204: ifeq -> 265
    //   207: aload_0
    //   208: getstatic android/view/View.Y : Landroid/util/Property;
    //   211: iconst_2
    //   212: newarray float
    //   214: dup
    //   215: iconst_0
    //   216: aload_0
    //   217: getfield mContainerHeight : I
    //   220: aload #4
    //   222: getfield height : I
    //   225: isub
    //   226: i2f
    //   227: fastore
    //   228: dup
    //   229: iconst_1
    //   230: aload_0
    //   231: getfield mContainerHeight : I
    //   234: aload_0
    //   235: getfield mInitHeight : I
    //   238: isub
    //   239: i2f
    //   240: fastore
    //   241: invokestatic ofFloat : (Ljava/lang/Object;Landroid/util/Property;[F)Landroid/animation/ObjectAnimator;
    //   244: ldc2_w 300
    //   247: invokevirtual setDuration : (J)Landroid/animation/ObjectAnimator;
    //   250: astore_3
    //   251: aload_3
    //   252: new com/tt/miniapp/view/MiniAppContainerView$2
    //   255: dup
    //   256: aload_0
    //   257: aload #4
    //   259: invokespecial <init> : (Lcom/tt/miniapp/view/MiniAppContainerView;Landroid/view/ViewGroup$LayoutParams;)V
    //   262: invokevirtual addListener : (Landroid/animation/Animator$AnimatorListener;)V
    //   265: aload_3
    //   266: ifnull -> 292
    //   269: aload_0
    //   270: iconst_1
    //   271: putfield mBlockTouchEvent : Z
    //   274: aload_3
    //   275: new com/tt/miniapp/view/MiniAppContainerView$3
    //   278: dup
    //   279: aload_0
    //   280: aload #4
    //   282: invokespecial <init> : (Lcom/tt/miniapp/view/MiniAppContainerView;Landroid/view/ViewGroup$LayoutParams;)V
    //   285: invokevirtual addUpdateListener : (Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V
    //   288: aload_3
    //   289: invokevirtual start : ()V
    //   292: return
  }
  
  private void changeToScrollState() {
    if (this.mCurrentState == 2)
      return; 
    this.mCurrentState = 2;
    AppBrandLogger.d("MiniAppContainerView", new Object[] { "changedToFloatState", Integer.valueOf(this.mCurrentState) });
    this.mTitleBarControl.updateStatusBarState(false);
    this.mTitleBarControl.updateCapsuleButtonState(false);
    this.mTitleBarControl.updateTitlebarRadius(true);
  }
  
  private void fillUpContainer(boolean paramBoolean) {
    if (!this.mEnableScrollInContainer)
      return; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (layoutParams.height == this.mContainerHeight && this.mActivity.isFilledUpContainer())
      return; 
    layoutParams.height = this.mContainerHeight;
    setLayoutParams(layoutParams);
    onFillUpContainer(paramBoolean);
  }
  
  private void floatStyle(boolean paramBoolean) {
    if (!this.mEnableScrollInContainer)
      return; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (layoutParams.height == this.mInitHeight && !this.mActivity.isFilledUpContainer())
      return; 
    layoutParams.height = this.mInitHeight;
    setLayoutParams(layoutParams);
    changeToInitFloatState();
  }
  
  private void init() {
    this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    isClickOutside = false;
  }
  
  public void changeToInitFloatState() {
    AppBrandLogger.i("MiniAppContainerView", new Object[] { "changeToInitFloatState", Integer.valueOf(this.mCurrentState) });
    if (this.mCurrentState == 3)
      return; 
    this.mCurrentState = 3;
    setY((this.mContainerHeight - this.mInitHeight));
    this.mMovingOffset = 0;
    this.mBlockTouchEvent = false;
    this.mInterceptTouchHandle = false;
    this.mIsTouchDownOnTitleBar = false;
    this.mActivity.setFilledUpContainer(false);
    this.mTitleBarControl.updateStatusBarState(false);
    this.mTitleBarControl.updateCapsuleButtonState(false);
    this.mTitleBarControl.updateTitlebarRadius(true);
    updateCloseWhenClickOutside(this.mCloseOnClickOutside);
    HostDependManager.getInst().dismissLiveWindowView((Activity)AppbrandContext.getInst().getCurrentActivity(), AppbrandApplicationImpl.getInst().getSchema(), false);
  }
  
  public void configStyle(Activity paramActivity, TitleBarControl paramTitleBarControl, MiniAppLaunchConfig paramMiniAppLaunchConfig) {
    this.mActivity = (MiniappHostBase)paramActivity;
    this.mTitleBarControl = paramTitleBarControl;
    if (!paramMiniAppLaunchConfig.isLaunchWithFloatStyle()) {
      this.mEnableScrollInContainer = false;
      return;
    } 
    this.mCurrentState = 3;
    this.mIsFloatStyle = true;
    this.mHideStatusBarWhenFloat = paramMiniAppLaunchConfig.getHideStatusBarWhenFloat();
    if (this.mHideStatusBarWhenFloat)
      this.mTitleBarControl.updateStatusBarState(false); 
    this.mTitleBarControl.updateCapsuleButtonState(false);
    this.mEnableScrollInContainer = paramMiniAppLaunchConfig.getEnableContainerViewScrollInContainer();
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    layoutParams.height = 0;
    setLayoutParams(layoutParams);
    ViewGroup viewGroup = (ViewGroup)getParent();
    if (paramMiniAppLaunchConfig.getOutsideColor() != 0)
      viewGroup.setBackgroundColor(paramMiniAppLaunchConfig.getOutsideColor()); 
    this.mCloseOnClickOutside = paramMiniAppLaunchConfig.getCloseOnTouchOutside();
    this.mFloatRootViewGravity = paramMiniAppLaunchConfig.getFloatRootViewGravity();
    (new ContainerSizeListener(viewGroup, paramMiniAppLaunchConfig.getContainerViewInitHeightRate())).startListenContainerChange();
  }
  
  public void directFillUpContainer() {
    fillUpContainer(true);
  }
  
  public void directFloat() {
    floatStyle(true);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    if (!this.mEnableScrollInContainer || getHeight() == 0)
      return super.dispatchTouchEvent(paramMotionEvent); 
    if (this.mBlockTouchEvent)
      return true; 
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3)
            return super.dispatchTouchEvent(paramMotionEvent); 
        } else {
          float f = this.mDownRawY - paramMotionEvent.getRawY();
          if ((Math.abs(f) > this.mTouchSlop || this.mInterceptTouchHandle) && ((f < 0.0F) ? !this.mIsTouchDownOnTitleBar : this.mActivity.isFilledUpContainer())) {
            if (!this.mInterceptTouchHandle) {
              AppBrandLogger.i("MiniAppContainerView", new Object[] { "interceptTouchHandle touchOffset:", Float.valueOf(f) });
              this.mInterceptTouchHandle = true;
              paramMotionEvent.setAction(3);
              super.dispatchTouchEvent(paramMotionEvent);
            } 
            updateVisibleSize(f);
            return true;
          } 
          return super.dispatchTouchEvent(paramMotionEvent);
        }  
      if (this.mInterceptTouchHandle) {
        adjustVisibleSize();
        this.mInterceptTouchHandle = false;
        return true;
      } 
    } else {
      this.mDownRawY = paramMotionEvent.getRawY();
      this.mIsTouchDownOnTitleBar = TitleBarControl.getInst().isTouchPointInCurrentTitleBar(paramMotionEvent);
    } 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public boolean ismFilledUpContainer() {
    return this.mActivity.isFilledUpContainer();
  }
  
  public void onFillUpContainer(boolean paramBoolean) {
    this.mCurrentState = 1;
    AppBrandLogger.i("MiniAppContainerView", new Object[] { "onFillUpContainer isDirect", Boolean.valueOf(paramBoolean) });
    setY(0.0F);
    this.mMovingOffset = 0;
    this.mBlockTouchEvent = false;
    this.mInterceptTouchHandle = false;
    this.mIsTouchDownOnTitleBar = false;
    this.mActivity.setFilledUpContainer(true);
    this.mTitleBarControl.updateStatusBarState(true);
    this.mTitleBarControl.updateCapsuleButtonState(true);
    this.mTitleBarControl.updateTitlebarRadius(false);
    if (!paramBoolean)
      HostDependManager.getInst().showLiveWindowView((Activity)AppbrandContext.getInst().getCurrentActivity(), AppbrandApplicationImpl.getInst().getSchema()); 
    updateCloseWhenClickOutside(false);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mIsFloatStyle) {
      paramInt1 = this.mContainerHeight;
      if (paramInt1 != 0) {
        if (!this.mFirstSizeChanged)
          return; 
        this.mFirstSizeChanged = false;
        AppBrandLogger.i("MiniAppContainerView", new Object[] { "onSizeChanged mContainerHeight:", Integer.valueOf(paramInt1), "mInitHeight:", Integer.valueOf(this.mInitHeight) });
        MiniappHostBase miniappHostBase = this.mActivity;
        if (miniappHostBase != null)
          miniappHostBase.getWindow().setWindowAnimations(0); 
        Property property = View.Y;
        paramInt1 = this.mContainerHeight;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, property, new float[] { paramInt1, (paramInt1 - this.mInitHeight) }).setDuration(350L);
        objectAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
              public void onAnimationEnd(Animator param1Animator) {
                MiniAppContainerView miniAppContainerView = MiniAppContainerView.this;
                miniAppContainerView.updateCloseWhenClickOutside(miniAppContainerView.mCloseOnClickOutside);
              }
            });
        objectAnimator.start();
      } 
    } 
  }
  
  public void resetState() {
    if (this.mContainerHeight == 0)
      return; 
    if (this.mActivity.isFilledUpContainer()) {
      fillUpContainer(false);
      return;
    } 
    floatStyle(false);
  }
  
  public void updateCloseWhenClickOutside(boolean paramBoolean) {
    if (paramBoolean) {
      ((View)getParent()).setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              MiniAppContainerView.isClickOutside = true;
              ToolUtils.onActivityExit((Activity)AppbrandContext.getInst().getCurrentActivity(), 9);
            }
          });
      setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {}
          });
      return;
    } 
    ((View)getParent()).setOnClickListener(null);
    setOnClickListener(null);
  }
  
  public void updateVisibleSize(float paramFloat) {
    boolean bool = this.mActivity.isFilledUpContainer();
    if (paramFloat == 0.0F) {
      i = 0;
    } else {
      if (paramFloat > 0.0F) {
        paramFloat -= this.mTouchSlop;
      } else {
        paramFloat += this.mTouchSlop;
      } 
      i = (int)paramFloat;
    } 
    this.mMovingOffset = i;
    if ((bool ^ true) != 0) {
      j = this.mInitHeight;
    } else {
      j = this.mContainerHeight;
    } 
    int j = i + j;
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    int k = this.mContainerHeight;
    int i = j;
    if (j >= k)
      i = k; 
    setY((this.mContainerHeight - i));
    if (i == this.mContainerHeight) {
      fillUpContainer(false);
    } else {
      layoutParams.height = i;
      changeToScrollState();
    } 
    setLayoutParams(layoutParams);
  }
  
  class ContainerSizeListener implements View.OnLayoutChangeListener {
    private final ViewGroup mContainer;
    
    private final float mContainerHeightRate;
    
    private ContainerSizeListener(ViewGroup param1ViewGroup, float param1Float) {
      this.mContainer = param1ViewGroup;
      this.mContainerHeightRate = param1Float;
    }
    
    public void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
      param1Int1 = param1Int4 - param1Int2;
      if (MiniAppContainerView.this.mContainerHeight != param1Int1) {
        AppBrandLogger.i("MiniAppContainerView", new Object[] { "onLayoutChange mContainerHeight:", Integer.valueOf(this.this$0.mContainerHeight), "containerHeight:", Integer.valueOf(param1Int1), "mInitHeight:", Integer.valueOf(this.this$0.mInitHeight) });
        MiniAppContainerView miniAppContainerView = MiniAppContainerView.this;
        miniAppContainerView.mContainerHeight = param1Int1;
        miniAppContainerView.mInitHeight = (int)(miniAppContainerView.mContainerHeight * this.mContainerHeightRate);
        MiniAppContainerView.this.updateVisibleSize(0.0F);
      } 
    }
    
    public void startListenContainerChange() {
      this.mContainer.addOnLayoutChangeListener(this);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\MiniAppContainerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */