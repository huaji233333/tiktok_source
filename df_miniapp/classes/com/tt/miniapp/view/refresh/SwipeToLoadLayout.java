package com.tt.miniapp.view.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.i;
import android.support.v4.view.u;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import com.tt.miniapphost.AppBrandLogger;

public class SwipeToLoadLayout extends ViewGroup {
  private boolean disableScroll;
  
  private int mActivePointerId;
  
  private boolean mAutoLoading;
  
  private AutoScroller mAutoScroller;
  
  private boolean mDebug;
  
  private int mDefaultToLoadingMoreScrollingDuration = 300;
  
  private int mDefaultToRefreshingScrollingDuration = 500;
  
  private boolean mDisableRefresh;
  
  private float mDragRatio = 0.5F;
  
  private int mFooterHeight;
  
  private int mFooterOffset;
  
  public View mFooterView;
  
  private boolean mHasFooterView;
  
  private boolean mHasHeaderView;
  
  private int mHeaderHeight;
  
  private int mHeaderOffset;
  
  public View mHeaderView;
  
  private float mInitDownX;
  
  private float mInitDownY;
  
  private boolean mInterceptedMoveEvent;
  
  private float mLastX;
  
  private float mLastY;
  
  LoadMoreCallback mLoadMoreCallback = new LoadMoreCallback() {
      public void onFingerMove(int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isLoadMoreStatus(SwipeToLoadLayout.this.mStatus)) {
          if (SwipeToLoadLayout.this.mFooterView.getVisibility() != 0)
            SwipeToLoadLayout.this.mFooterView.setVisibility(0); 
          ((SwipeTrigger)SwipeToLoadLayout.this.mFooterView).onFingerMove(param1Int, param1Boolean1, param1Boolean2);
        } 
      }
      
      public void onFingerRelease() {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isReleaseToLoadMore(SwipeToLoadLayout.this.mStatus))
          ((SwipeTrigger)SwipeToLoadLayout.this.mFooterView).onFingerRelease(); 
      }
      
      public void onLoadMore() {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.STATUS.isLoadingMore(SwipeToLoadLayout.this.mStatus)) {
          if (SwipeToLoadLayout.this.mFooterView instanceof SwipeLoadMoreTrigger)
            ((SwipeLoadMoreTrigger)SwipeToLoadLayout.this.mFooterView).onLoadMore(); 
          if (SwipeToLoadLayout.this.mLoadMoreListener != null)
            SwipeToLoadLayout.this.mLoadMoreListener.onLoadMore(); 
        } 
      }
      
      public void onPageRefresh() {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
          SwipeToLoadLayout.this.mFooterView.setVisibility(0);
          ((SwipeTrigger)SwipeToLoadLayout.this.mFooterView).onPageRefresh();
        } 
      }
      
      public void onRefreshComplete() {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger)
          ((SwipeTrigger)SwipeToLoadLayout.this.mFooterView).onRefreshComplete(); 
      }
      
      public void onReset() {
        if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
          ((SwipeTrigger)SwipeToLoadLayout.this.mFooterView).onReset();
          SwipeToLoadLayout.this.mFooterView.setVisibility(8);
        } 
      }
    };
  
  private int mLoadMoreCompleteDelayDuration = 300;
  
  private int mLoadMoreCompleteToDefaultScrollingDuration = 300;
  
  private boolean mLoadMoreEnabled = true;
  
  private float mLoadMoreFinalDragOffset;
  
  public OnLoadMoreListener mLoadMoreListener;
  
  private float mLoadMoreTriggerOffset;
  
  RefreshCallback mRefreshCallback = new RefreshCallback() {
      public void onFingerMove(int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isRefreshStatus(SwipeToLoadLayout.this.mStatus)) {
          if (SwipeToLoadLayout.this.mHeaderView.getVisibility() != 0)
            SwipeToLoadLayout.this.mHeaderView.setVisibility(0); 
          ((SwipeTrigger)SwipeToLoadLayout.this.mHeaderView).onFingerMove(param1Int, param1Boolean1, param1Boolean2);
        } 
      }
      
      public void onFingerRelease() {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isReleaseToRefresh(SwipeToLoadLayout.this.mStatus))
          ((SwipeTrigger)SwipeToLoadLayout.this.mHeaderView).onFingerRelease(); 
      }
      
      public void onPageRefresh() {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
          SwipeToLoadLayout.this.mHeaderView.setVisibility(0);
          ((SwipeTrigger)SwipeToLoadLayout.this.mHeaderView).onPageRefresh();
        } 
      }
      
      public void onRefreshComplete() {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger)
          ((SwipeTrigger)SwipeToLoadLayout.this.mHeaderView).onRefreshComplete(); 
      }
      
      public void onRefreshTrigger() {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.STATUS.isRefreshing(SwipeToLoadLayout.this.mStatus)) {
          if (SwipeToLoadLayout.this.mHeaderView instanceof SwipeRefreshTrigger)
            ((SwipeRefreshTrigger)SwipeToLoadLayout.this.mHeaderView).onRefreshTrigger(); 
          if (SwipeToLoadLayout.this.mRefreshListener != null)
            SwipeToLoadLayout.this.mRefreshListener.onRefresh(); 
        } 
      }
      
      public void onReset() {
        if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
          ((SwipeTrigger)SwipeToLoadLayout.this.mHeaderView).onReset();
          SwipeToLoadLayout.this.mHeaderView.setVisibility(8);
        } 
      }
    };
  
  private int mRefreshCompleteDelayDuration = 300;
  
  private int mRefreshCompleteToDefaultScrollingDuration = 500;
  
  private boolean mRefreshEnabled = true;
  
  private float mRefreshFinalDragOffset;
  
  public OnRefreshListener mRefreshListener;
  
  private float mRefreshTriggerOffset;
  
  private int mReleaseToLoadMoreToLoadingMoreScrollingDuration = 200;
  
  private int mReleaseToRefreshToRefreshingScrollingDuration = 200;
  
  public int mStatus;
  
  private int mStyle;
  
  private int mSwipingToLoadMoreToDefaultScrollingDuration = 200;
  
  private int mSwipingToRefreshToDefaultScrollingDuration = 200;
  
  private int mTargetOffset;
  
  private View mTargetView;
  
  private final int mTouchSlop;
  
  public SwipeToLoadLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SwipeToLoadLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SwipeToLoadLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 
          2097283080, 2097283086, 2097283087, 2097283088, 2097283092, 2097283093, 2097283094, 2097283095, 2097283096, 2097283101, 
          2097283102, 2097283103, 2097283104, 2097283105, 2097283106, 2097283107, 2097283120, 2097283121 }, paramInt, 0);
    try {
      int i = typedArray.getIndexCount();
      for (paramInt = 0; paramInt < i; paramInt++) {
        int j = typedArray.getIndex(paramInt);
        if (j == 11) {
          setRefreshEnabled(typedArray.getBoolean(j, true));
        } else if (j == 6) {
          setLoadMoreEnabled(typedArray.getBoolean(j, true));
        } else if (j == 0) {
          setSwipeStyle(typedArray.getInt(j, 0));
        } else if (j == 3) {
          setDragRatio(typedArray.getFloat(j, 0.5F));
        } else if (j == 12) {
          setRefreshFinalDragOffset(typedArray.getDimensionPixelOffset(j, 0));
        } else if (j == 7) {
          setLoadMoreFinalDragOffset(typedArray.getDimensionPixelOffset(j, 0));
        } else if (j == 13) {
          setRefreshTriggerOffset(typedArray.getDimensionPixelOffset(j, 0));
        } else if (j == 8) {
          setLoadMoreTriggerOffset(typedArray.getDimensionPixelOffset(j, 0));
        } else if (j == 17) {
          setSwipingToRefreshToDefaultScrollingDuration(typedArray.getInt(j, 200));
        } else if (j == 15) {
          setReleaseToRefreshingScrollingDuration(typedArray.getInt(j, 200));
        } else if (j == 9) {
          setRefreshCompleteDelayDuration(typedArray.getInt(j, 300));
        } else if (j == 10) {
          setRefreshCompleteToDefaultScrollingDuration(typedArray.getInt(j, 500));
        } else if (j == 2) {
          setDefaultToRefreshingScrollingDuration(typedArray.getInt(j, 500));
        } else if (j == 16) {
          setSwipingToLoadMoreToDefaultScrollingDuration(typedArray.getInt(j, 200));
        } else if (j == 14) {
          setReleaseToLoadingMoreScrollingDuration(typedArray.getInt(j, 200));
        } else if (j == 4) {
          setLoadMoreCompleteDelayDuration(typedArray.getInt(j, 300));
        } else if (j == 5) {
          setLoadMoreCompleteToDefaultScrollingDuration(typedArray.getInt(j, 300));
        } else if (j == 1) {
          setDefaultToLoadingMoreScrollingDuration(typedArray.getInt(j, 300));
        } 
      } 
      typedArray.recycle();
      this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
      return;
    } finally {
      typedArray.recycle();
    } 
  }
  
  private void fingerScroll(float paramFloat) {
    // Byte code:
    //   0: fload_1
    //   1: aload_0
    //   2: getfield mDragRatio : F
    //   5: fmul
    //   6: fstore_2
    //   7: aload_0
    //   8: getfield mTargetOffset : I
    //   11: istore #5
    //   13: iload #5
    //   15: i2f
    //   16: fload_2
    //   17: fadd
    //   18: fstore_3
    //   19: fload_3
    //   20: fconst_0
    //   21: fcmpl
    //   22: ifle -> 30
    //   25: iload #5
    //   27: iflt -> 47
    //   30: fload_2
    //   31: fstore_1
    //   32: fload_3
    //   33: fconst_0
    //   34: fcmpg
    //   35: ifge -> 54
    //   38: fload_2
    //   39: fstore_1
    //   40: aload_0
    //   41: getfield mTargetOffset : I
    //   44: ifle -> 54
    //   47: aload_0
    //   48: getfield mTargetOffset : I
    //   51: ineg
    //   52: i2f
    //   53: fstore_1
    //   54: aload_0
    //   55: getfield mRefreshFinalDragOffset : F
    //   58: fstore_2
    //   59: fload_2
    //   60: aload_0
    //   61: getfield mRefreshTriggerOffset : F
    //   64: fcmpl
    //   65: iflt -> 85
    //   68: fload_3
    //   69: fload_2
    //   70: fcmpl
    //   71: ifle -> 85
    //   74: fload_2
    //   75: aload_0
    //   76: getfield mTargetOffset : I
    //   79: i2f
    //   80: fsub
    //   81: fstore_2
    //   82: goto -> 123
    //   85: aload_0
    //   86: getfield mLoadMoreFinalDragOffset : F
    //   89: fstore #4
    //   91: fload_1
    //   92: fstore_2
    //   93: fload #4
    //   95: aload_0
    //   96: getfield mLoadMoreTriggerOffset : F
    //   99: fcmpl
    //   100: iflt -> 123
    //   103: fload_1
    //   104: fstore_2
    //   105: fload_3
    //   106: fneg
    //   107: fload #4
    //   109: fcmpl
    //   110: ifle -> 123
    //   113: fload #4
    //   115: fneg
    //   116: aload_0
    //   117: getfield mTargetOffset : I
    //   120: i2f
    //   121: fsub
    //   122: fstore_2
    //   123: aload_0
    //   124: getfield mStatus : I
    //   127: invokestatic isRefreshStatus : (I)Z
    //   130: ifeq -> 149
    //   133: aload_0
    //   134: getfield mRefreshCallback : Lcom/tt/miniapp/view/refresh/SwipeToLoadLayout$RefreshCallback;
    //   137: aload_0
    //   138: getfield mTargetOffset : I
    //   141: iconst_0
    //   142: iconst_0
    //   143: invokevirtual onFingerMove : (IZZ)V
    //   146: goto -> 172
    //   149: aload_0
    //   150: getfield mStatus : I
    //   153: invokestatic isLoadMoreStatus : (I)Z
    //   156: ifeq -> 172
    //   159: aload_0
    //   160: getfield mLoadMoreCallback : Lcom/tt/miniapp/view/refresh/SwipeToLoadLayout$LoadMoreCallback;
    //   163: aload_0
    //   164: getfield mTargetOffset : I
    //   167: iconst_0
    //   168: iconst_0
    //   169: invokevirtual onFingerMove : (IZZ)V
    //   172: aload_0
    //   173: fload_2
    //   174: invokespecial updateScroll : (F)V
    //   177: return
  }
  
  private void fixCurrentStatusLayout() {
    if (STATUS.isRefreshing(this.mStatus)) {
      this.mTargetOffset = (int)(this.mRefreshTriggerOffset + 0.5F);
      this.mHeaderOffset = this.mTargetOffset;
      this.mFooterOffset = 0;
      layoutChildren();
      invalidate();
      return;
    } 
    if (STATUS.isStatusDefault(this.mStatus)) {
      this.mTargetOffset = 0;
      this.mHeaderOffset = 0;
      this.mFooterOffset = 0;
      layoutChildren();
      invalidate();
      return;
    } 
    if (STATUS.isLoadingMore(this.mStatus)) {
      this.mTargetOffset = -((int)(this.mLoadMoreTriggerOffset + 0.5F));
      this.mHeaderOffset = 0;
      this.mFooterOffset = this.mTargetOffset;
      layoutChildren();
      invalidate();
    } 
  }
  
  private float getMotionEventX(MotionEvent paramMotionEvent, int paramInt) {
    paramInt = i.a(paramMotionEvent, paramInt);
    return (paramInt < 0) ? -1.0F : i.c(paramMotionEvent, paramInt);
  }
  
  private float getMotionEventY(MotionEvent paramMotionEvent, int paramInt) {
    paramInt = i.a(paramMotionEvent, paramInt);
    return (paramInt < 0) ? -1.0F : i.d(paramMotionEvent, paramInt);
  }
  
  private void layoutChildren() {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getMeasuredWidth : ()I
    //   4: pop
    //   5: aload_0
    //   6: invokevirtual getMeasuredHeight : ()I
    //   9: istore #4
    //   11: aload_0
    //   12: invokevirtual getPaddingLeft : ()I
    //   15: istore #6
    //   17: aload_0
    //   18: invokevirtual getPaddingTop : ()I
    //   21: istore_3
    //   22: aload_0
    //   23: invokevirtual getPaddingRight : ()I
    //   26: pop
    //   27: aload_0
    //   28: invokevirtual getPaddingBottom : ()I
    //   31: istore #5
    //   33: aload_0
    //   34: getfield mTargetView : Landroid/view/View;
    //   37: ifnonnull -> 41
    //   40: return
    //   41: aload_0
    //   42: getfield mHeaderView : Landroid/view/View;
    //   45: astore #8
    //   47: aload #8
    //   49: ifnull -> 222
    //   52: aload #8
    //   54: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   57: checkcast android/view/ViewGroup$MarginLayoutParams
    //   60: astore #9
    //   62: aload #9
    //   64: getfield leftMargin : I
    //   67: iload #6
    //   69: iadd
    //   70: istore #7
    //   72: aload_0
    //   73: getfield mStyle : I
    //   76: istore_1
    //   77: iload_1
    //   78: ifeq -> 178
    //   81: iload_1
    //   82: iconst_1
    //   83: if_icmpeq -> 157
    //   86: iload_1
    //   87: iconst_2
    //   88: if_icmpeq -> 146
    //   91: iload_1
    //   92: iconst_3
    //   93: if_icmpeq -> 121
    //   96: aload #9
    //   98: getfield topMargin : I
    //   101: iload_3
    //   102: iadd
    //   103: aload_0
    //   104: getfield mHeaderHeight : I
    //   107: isub
    //   108: istore_1
    //   109: aload_0
    //   110: getfield mHeaderOffset : I
    //   113: istore_2
    //   114: iload_1
    //   115: iload_2
    //   116: iadd
    //   117: istore_1
    //   118: goto -> 199
    //   121: aload #9
    //   123: getfield topMargin : I
    //   126: iload_3
    //   127: iadd
    //   128: aload_0
    //   129: getfield mHeaderHeight : I
    //   132: iconst_2
    //   133: idiv
    //   134: isub
    //   135: istore_1
    //   136: aload_0
    //   137: getfield mHeaderOffset : I
    //   140: iconst_2
    //   141: idiv
    //   142: istore_2
    //   143: goto -> 114
    //   146: aload #9
    //   148: getfield topMargin : I
    //   151: iload_3
    //   152: iadd
    //   153: istore_1
    //   154: goto -> 199
    //   157: aload #9
    //   159: getfield topMargin : I
    //   162: iload_3
    //   163: iadd
    //   164: aload_0
    //   165: getfield mHeaderHeight : I
    //   168: isub
    //   169: istore_1
    //   170: aload_0
    //   171: getfield mHeaderOffset : I
    //   174: istore_2
    //   175: goto -> 114
    //   178: aload #9
    //   180: getfield topMargin : I
    //   183: iload_3
    //   184: iadd
    //   185: aload_0
    //   186: getfield mHeaderHeight : I
    //   189: isub
    //   190: istore_1
    //   191: aload_0
    //   192: getfield mHeaderOffset : I
    //   195: istore_2
    //   196: goto -> 114
    //   199: aload #8
    //   201: iload #7
    //   203: iload_1
    //   204: aload #8
    //   206: invokevirtual getMeasuredWidth : ()I
    //   209: iload #7
    //   211: iadd
    //   212: aload #8
    //   214: invokevirtual getMeasuredHeight : ()I
    //   217: iload_1
    //   218: iadd
    //   219: invokevirtual layout : (IIII)V
    //   222: aload_0
    //   223: getfield mTargetView : Landroid/view/View;
    //   226: astore #8
    //   228: aload #8
    //   230: ifnull -> 379
    //   233: aload #8
    //   235: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   238: checkcast android/view/ViewGroup$MarginLayoutParams
    //   241: astore #9
    //   243: aload #9
    //   245: getfield leftMargin : I
    //   248: iload #6
    //   250: iadd
    //   251: istore #7
    //   253: aload_0
    //   254: getfield mStyle : I
    //   257: istore_1
    //   258: iload_1
    //   259: ifeq -> 340
    //   262: iload_1
    //   263: iconst_1
    //   264: if_icmpeq -> 329
    //   267: iload_1
    //   268: iconst_2
    //   269: if_icmpeq -> 313
    //   272: iload_1
    //   273: iconst_3
    //   274: if_icmpeq -> 297
    //   277: iload_3
    //   278: aload #9
    //   280: getfield topMargin : I
    //   283: iadd
    //   284: istore_1
    //   285: aload_0
    //   286: getfield mTargetOffset : I
    //   289: istore_2
    //   290: iload_1
    //   291: iload_2
    //   292: iadd
    //   293: istore_1
    //   294: goto -> 356
    //   297: iload_3
    //   298: aload #9
    //   300: getfield topMargin : I
    //   303: iadd
    //   304: istore_1
    //   305: aload_0
    //   306: getfield mTargetOffset : I
    //   309: istore_2
    //   310: goto -> 290
    //   313: iload_3
    //   314: aload #9
    //   316: getfield topMargin : I
    //   319: iadd
    //   320: istore_1
    //   321: aload_0
    //   322: getfield mTargetOffset : I
    //   325: istore_2
    //   326: goto -> 290
    //   329: aload #9
    //   331: getfield topMargin : I
    //   334: istore_2
    //   335: iload_3
    //   336: istore_1
    //   337: goto -> 290
    //   340: iload_3
    //   341: aload #9
    //   343: getfield topMargin : I
    //   346: iadd
    //   347: istore_1
    //   348: aload_0
    //   349: getfield mTargetOffset : I
    //   352: istore_2
    //   353: goto -> 290
    //   356: aload #8
    //   358: iload #7
    //   360: iload_1
    //   361: aload #8
    //   363: invokevirtual getMeasuredWidth : ()I
    //   366: iload #7
    //   368: iadd
    //   369: aload #8
    //   371: invokevirtual getMeasuredHeight : ()I
    //   374: iload_1
    //   375: iadd
    //   376: invokevirtual layout : (IIII)V
    //   379: aload_0
    //   380: getfield mFooterView : Landroid/view/View;
    //   383: astore #8
    //   385: aload #8
    //   387: ifnull -> 577
    //   390: aload #8
    //   392: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   395: checkcast android/view/ViewGroup$MarginLayoutParams
    //   398: astore #9
    //   400: iload #6
    //   402: aload #9
    //   404: getfield leftMargin : I
    //   407: iadd
    //   408: istore_3
    //   409: aload_0
    //   410: getfield mStyle : I
    //   413: istore_1
    //   414: iload_1
    //   415: ifeq -> 531
    //   418: iload_1
    //   419: iconst_1
    //   420: if_icmpeq -> 506
    //   423: iload_1
    //   424: iconst_2
    //   425: if_icmpeq -> 491
    //   428: iload_1
    //   429: iconst_3
    //   430: if_icmpeq -> 462
    //   433: iload #4
    //   435: iload #5
    //   437: isub
    //   438: aload #9
    //   440: getfield bottomMargin : I
    //   443: isub
    //   444: aload_0
    //   445: getfield mFooterHeight : I
    //   448: iadd
    //   449: istore_1
    //   450: aload_0
    //   451: getfield mFooterOffset : I
    //   454: istore_2
    //   455: iload_1
    //   456: iload_2
    //   457: iadd
    //   458: istore_1
    //   459: goto -> 556
    //   462: iload #4
    //   464: iload #5
    //   466: isub
    //   467: aload #9
    //   469: getfield bottomMargin : I
    //   472: isub
    //   473: aload_0
    //   474: getfield mFooterHeight : I
    //   477: iconst_2
    //   478: idiv
    //   479: iadd
    //   480: istore_1
    //   481: aload_0
    //   482: getfield mFooterOffset : I
    //   485: iconst_2
    //   486: idiv
    //   487: istore_2
    //   488: goto -> 455
    //   491: iload #4
    //   493: iload #5
    //   495: isub
    //   496: aload #9
    //   498: getfield bottomMargin : I
    //   501: isub
    //   502: istore_1
    //   503: goto -> 556
    //   506: iload #4
    //   508: iload #5
    //   510: isub
    //   511: aload #9
    //   513: getfield bottomMargin : I
    //   516: isub
    //   517: aload_0
    //   518: getfield mFooterHeight : I
    //   521: iadd
    //   522: istore_1
    //   523: aload_0
    //   524: getfield mFooterOffset : I
    //   527: istore_2
    //   528: goto -> 455
    //   531: iload #4
    //   533: iload #5
    //   535: isub
    //   536: aload #9
    //   538: getfield bottomMargin : I
    //   541: isub
    //   542: aload_0
    //   543: getfield mFooterHeight : I
    //   546: iadd
    //   547: istore_1
    //   548: aload_0
    //   549: getfield mFooterOffset : I
    //   552: istore_2
    //   553: goto -> 455
    //   556: aload #8
    //   558: iload_3
    //   559: iload_1
    //   560: aload #8
    //   562: invokevirtual getMeasuredHeight : ()I
    //   565: isub
    //   566: aload #8
    //   568: invokevirtual getMeasuredWidth : ()I
    //   571: iload_3
    //   572: iadd
    //   573: iload_1
    //   574: invokevirtual layout : (IIII)V
    //   577: aload_0
    //   578: getfield mStyle : I
    //   581: istore_1
    //   582: iload_1
    //   583: ifeq -> 621
    //   586: iload_1
    //   587: iconst_1
    //   588: if_icmpne -> 594
    //   591: goto -> 621
    //   594: iload_1
    //   595: iconst_2
    //   596: if_icmpeq -> 604
    //   599: iload_1
    //   600: iconst_3
    //   601: if_icmpne -> 653
    //   604: aload_0
    //   605: getfield mTargetView : Landroid/view/View;
    //   608: astore #8
    //   610: aload #8
    //   612: ifnull -> 653
    //   615: aload #8
    //   617: invokevirtual bringToFront : ()V
    //   620: return
    //   621: aload_0
    //   622: getfield mHeaderView : Landroid/view/View;
    //   625: astore #8
    //   627: aload #8
    //   629: ifnull -> 637
    //   632: aload #8
    //   634: invokevirtual bringToFront : ()V
    //   637: aload_0
    //   638: getfield mFooterView : Landroid/view/View;
    //   641: astore #8
    //   643: aload #8
    //   645: ifnull -> 653
    //   648: aload #8
    //   650: invokevirtual bringToFront : ()V
    //   653: return
  }
  
  private void onActivePointerUp() {
    if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isRefreshing(this.mStatus)) {
      scrollSwipingToRefreshToDefault();
      return;
    } 
    if (STATUS.isSwipingToLoadMore(this.mStatus)) {
      scrollSwipingToLoadMoreToDefault();
      return;
    } 
    if (STATUS.isReleaseToRefresh(this.mStatus)) {
      this.mRefreshCallback.onFingerRelease();
      scrollReleaseToRefreshToRefreshing();
      return;
    } 
    if (STATUS.isReleaseToLoadMore(this.mStatus)) {
      this.mLoadMoreCallback.onFingerRelease();
      scrollReleaseToLoadMoreToLoadingMore();
    } 
  }
  
  private boolean onCheckCanLoadMore() {
    return (!this.mDisableRefresh && this.mLoadMoreEnabled && !canChildScrollDown() && this.mHasFooterView && this.mLoadMoreTriggerOffset > 0.0F);
  }
  
  private boolean onCheckCanRefresh() {
    boolean bool;
    if (!this.mDisableRefresh && this.mRefreshEnabled && !canChildScrollUp() && this.mHasHeaderView && this.mRefreshTriggerOffset > 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    AppBrandLogger.i("SwipeToLoadLayout", new Object[] { "onCheckCanRefresh: ", Boolean.valueOf(bool) });
    return bool;
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
    int i = i.b(paramMotionEvent);
    if (i.b(paramMotionEvent, i) == this.mActivePointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mActivePointerId = i.b(paramMotionEvent, i);
    } 
  }
  
  private void scrollDefaultToLoadingMore() {
    this.mAutoScroller.autoScroll(-((int)(this.mLoadMoreTriggerOffset + 0.5F)), this.mDefaultToLoadingMoreScrollingDuration);
  }
  
  private void scrollDefaultToRefreshing() {
    this.mAutoScroller.autoScroll((int)(this.mRefreshTriggerOffset + 0.5F), this.mDefaultToRefreshingScrollingDuration);
  }
  
  private void scrollReleaseToLoadMoreToLoadingMore() {
    this.mAutoScroller.autoScroll(-this.mFooterOffset - this.mFooterHeight, this.mReleaseToLoadMoreToLoadingMoreScrollingDuration);
  }
  
  private void scrollReleaseToRefreshToRefreshing() {
    this.mAutoScroller.autoScroll(this.mHeaderHeight - this.mHeaderOffset, this.mReleaseToRefreshToRefreshingScrollingDuration);
  }
  
  private void scrollSwipingToLoadMoreToDefault() {
    this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mSwipingToLoadMoreToDefaultScrollingDuration);
  }
  
  private void scrollSwipingToRefreshToDefault() {
    this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mSwipingToRefreshToDefaultScrollingDuration);
  }
  
  private void setStatus(int paramInt) {
    this.mStatus = paramInt;
    if (this.mDebug)
      STATUS.printStatus(paramInt); 
  }
  
  private void updateScroll(float paramFloat) {
    if (paramFloat == 0.0F)
      return; 
    this.mTargetOffset = (int)(this.mTargetOffset + paramFloat);
    if (STATUS.isRefreshStatus(this.mStatus)) {
      this.mHeaderOffset = this.mTargetOffset;
      this.mFooterOffset = 0;
    } else if (STATUS.isLoadMoreStatus(this.mStatus)) {
      this.mFooterOffset = this.mTargetOffset;
      this.mHeaderOffset = 0;
    } 
    if (this.mDebug)
      AppBrandLogger.i("SwipeToLoadLayout", new Object[] { "mTargetOffset = ", Integer.valueOf(this.mTargetOffset) }); 
    layoutChildren();
    invalidate();
  }
  
  public void autoScroll(float paramFloat) {
    if (STATUS.isSwipingToRefresh(this.mStatus)) {
      this.mRefreshCallback.onFingerMove(this.mTargetOffset, false, true);
    } else if (STATUS.isReleaseToRefresh(this.mStatus)) {
      this.mRefreshCallback.onFingerMove(this.mTargetOffset, false, true);
    } else if (STATUS.isRefreshing(this.mStatus)) {
      this.mRefreshCallback.onFingerMove(this.mTargetOffset, true, true);
    } else if (STATUS.isSwipingToLoadMore(this.mStatus)) {
      this.mLoadMoreCallback.onFingerMove(this.mTargetOffset, false, true);
    } else if (STATUS.isReleaseToLoadMore(this.mStatus)) {
      this.mLoadMoreCallback.onFingerMove(this.mTargetOffset, false, true);
    } else if (STATUS.isLoadingMore(this.mStatus)) {
      this.mLoadMoreCallback.onFingerMove(this.mTargetOffset, true, true);
    } 
    updateScroll(paramFloat);
  }
  
  public void autoScrollFinished() {
    int i = this.mStatus;
    if (STATUS.isReleaseToRefresh(i)) {
      setStatus(-3);
      fixCurrentStatusLayout();
      this.mRefreshCallback.onRefreshTrigger();
    } else if (STATUS.isRefreshing(this.mStatus)) {
      setStatus(0);
      fixCurrentStatusLayout();
      this.mRefreshCallback.onReset();
    } else if (STATUS.isSwipingToRefresh(this.mStatus)) {
      if (this.mAutoLoading) {
        this.mAutoLoading = false;
        setStatus(-3);
        fixCurrentStatusLayout();
        this.mRefreshCallback.onRefreshTrigger();
      } else {
        setStatus(0);
        fixCurrentStatusLayout();
        this.mRefreshCallback.onReset();
      } 
    } else if (!STATUS.isStatusDefault(this.mStatus)) {
      if (STATUS.isSwipingToLoadMore(this.mStatus)) {
        if (this.mAutoLoading) {
          this.mAutoLoading = false;
          setStatus(3);
          fixCurrentStatusLayout();
          this.mLoadMoreCallback.onLoadMore();
        } else {
          setStatus(0);
          fixCurrentStatusLayout();
          this.mLoadMoreCallback.onReset();
        } 
      } else if (STATUS.isLoadingMore(this.mStatus)) {
        setStatus(0);
        fixCurrentStatusLayout();
        this.mLoadMoreCallback.onReset();
      } else if (STATUS.isReleaseToLoadMore(this.mStatus)) {
        setStatus(3);
        fixCurrentStatusLayout();
        this.mLoadMoreCallback.onLoadMore();
      } else {
        StringBuilder stringBuilder = new StringBuilder("illegal state: ");
        stringBuilder.append(STATUS.getStatus(this.mStatus));
        throw new IllegalStateException(stringBuilder.toString());
      } 
    } 
    if (this.mDebug)
      AppBrandLogger.i("SwipeToLoadLayout", new Object[] { STATUS.getStatus(i), " -> ", STATUS.getStatus(this.mStatus) }); 
  }
  
  protected boolean canChildScrollDown() {
    if (Build.VERSION.SDK_INT < 14) {
      AbsListView absListView;
      View view = this.mTargetView;
      if (view instanceof AbsListView) {
        absListView = (AbsListView)view;
        return (absListView.getChildCount() > 0 && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1 || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom()));
      } 
      return !u.a((View)absListView, 1) ? ((this.mTargetView.getScrollY() < 0)) : true;
    } 
    return u.a(this.mTargetView, 1);
  }
  
  protected boolean canChildScrollUp() {
    if (Build.VERSION.SDK_INT < 14) {
      AbsListView absListView;
      View view = this.mTargetView;
      if (view instanceof AbsListView) {
        absListView = (AbsListView)view;
        return (absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
      } 
      return !u.a((View)absListView, -1) ? ((this.mTargetView.getScrollY() > 0)) : true;
    } 
    return u.a(this.mTargetView, -1);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    int i = i.a(paramMotionEvent);
    if (i == 1 || i == 3)
      onActivePointerUp(); 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return (ViewGroup.LayoutParams)new LayoutParams(-1, -1);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return (ViewGroup.LayoutParams)new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (ViewGroup.LayoutParams)new LayoutParams(paramLayoutParams);
  }
  
  public View getHeaderView() {
    return this.mHeaderView;
  }
  
  public boolean isLoadMoreEnabled() {
    return this.mLoadMoreEnabled;
  }
  
  public boolean isLoadingMore() {
    return STATUS.isLoadingMore(this.mStatus);
  }
  
  public boolean isRefreshEnabled() {
    return this.mRefreshEnabled;
  }
  
  public boolean isRefreshing() {
    return STATUS.isRefreshing(this.mStatus);
  }
  
  protected void onFinishInflate() {
    super.onFinishInflate();
    int i = getChildCount();
    if (i == 0)
      return; 
    if (i > 0 && i < 4) {
      this.mHeaderView = findViewById(2097545391);
      this.mTargetView = findViewById(2097545392);
      this.mFooterView = findViewById(2097545390);
      if (this.mTargetView == null)
        return; 
      View view = this.mHeaderView;
      if (view != null && view instanceof SwipeTrigger)
        view.setVisibility(8); 
      view = this.mFooterView;
      if (view != null && view instanceof SwipeTrigger)
        view.setVisibility(8); 
      return;
    } 
    throw new IllegalStateException("Children num must equal or less than 3");
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    int i = i.a(paramMotionEvent);
    boolean bool = false;
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3) {
            if (i == 6) {
              onSecondaryPointerUp(paramMotionEvent);
              float f = getMotionEventY(paramMotionEvent, this.mActivePointerId);
              this.mLastY = f;
              this.mInitDownY = f;
              f = getMotionEventX(paramMotionEvent, this.mActivePointerId);
              this.mLastX = f;
              this.mInitDownX = f;
            } 
            return super.onInterceptTouchEvent(paramMotionEvent);
          } 
        } else {
          i = this.mActivePointerId;
          if (i == -1)
            return false; 
          float f1 = getMotionEventY(paramMotionEvent, i);
          float f2 = getMotionEventX(paramMotionEvent, this.mActivePointerId);
          float f3 = f1 - this.mInitDownY;
          float f4 = this.mInitDownX;
          this.mLastY = f1;
          this.mLastX = f2;
          if (Math.abs(f3) > Math.abs(f2 - f4) && Math.abs(f3) > this.mTouchSlop) {
            i = 1;
          } else {
            i = 0;
          } 
          if ((f3 > 0.0F && i != 0 && onCheckCanRefresh()) || (f3 < 0.0F && i != 0 && onCheckCanLoadMore())) {
            bool1 = true;
          } else {
            bool1 = false;
          } 
          if (bool1)
            return true; 
          boolean bool1 = bool;
          if (i != 0) {
            bool1 = bool;
            if (isRefreshing())
              bool1 = true; 
          } 
          if (bool1) {
            this.mInterceptedMoveEvent = true;
            return true;
          } 
          return super.onInterceptTouchEvent(paramMotionEvent);
        }  
      this.mActivePointerId = -1;
    } else {
      this.mInterceptedMoveEvent = false;
      this.mActivePointerId = i.b(paramMotionEvent, 0);
      float f = getMotionEventY(paramMotionEvent, this.mActivePointerId);
      this.mLastY = f;
      this.mInitDownY = f;
      f = getMotionEventX(paramMotionEvent, this.mActivePointerId);
      this.mLastX = f;
      this.mInitDownX = f;
      if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isSwipingToLoadMore(this.mStatus) || STATUS.isReleaseToRefresh(this.mStatus) || STATUS.isReleaseToLoadMore(this.mStatus)) {
        this.mAutoScroller.abortIfRunning();
        if (this.mDebug)
          AppBrandLogger.i("SwipeToLoadLayout", new Object[] { "Another finger down, abort auto scrolling, let the new finger handle" }); 
      } 
      if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isReleaseToRefresh(this.mStatus) || STATUS.isSwipingToLoadMore(this.mStatus) || STATUS.isReleaseToLoadMore(this.mStatus)) {
        AppBrandLogger.i("SwipeToLoadLayout", new Object[] { "intercept1" });
        return true;
      } 
    } 
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    layoutChildren();
    View view = this.mHeaderView;
    boolean bool = true;
    if (view != null) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.mHasHeaderView = paramBoolean;
    if (this.mFooterView != null) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    } 
    this.mHasFooterView = paramBoolean;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    View view = this.mHeaderView;
    if (view != null) {
      measureChildWithMargins(view, paramInt1, 0, paramInt2, 0);
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
      this.mHeaderHeight = view.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
      float f = this.mRefreshTriggerOffset;
      int i = this.mHeaderHeight;
      if (f < i)
        this.mRefreshTriggerOffset = i; 
    } 
    view = this.mTargetView;
    if (view != null)
      measureChildWithMargins(view, paramInt1, 0, paramInt2, 0); 
    view = this.mFooterView;
    if (view != null) {
      measureChildWithMargins(view, paramInt1, 0, paramInt2, 0);
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
      this.mFooterHeight = view.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
      float f = this.mLoadMoreTriggerOffset;
      paramInt1 = this.mFooterHeight;
      if (f < paramInt1)
        this.mLoadMoreTriggerOffset = paramInt1; 
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = i.a(paramMotionEvent);
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 5) {
              if (i == 6) {
                onSecondaryPointerUp(paramMotionEvent);
                float f = getMotionEventY(paramMotionEvent, this.mActivePointerId);
                this.mLastY = f;
                this.mInitDownY = f;
                f = getMotionEventX(paramMotionEvent, this.mActivePointerId);
                this.mLastX = f;
                this.mInitDownX = f;
              } 
            } else {
              i = i.b(paramMotionEvent, i.b(paramMotionEvent));
              if (i != -1)
                this.mActivePointerId = i; 
              float f = getMotionEventY(paramMotionEvent, this.mActivePointerId);
              this.mLastY = f;
              this.mInitDownY = f;
              f = getMotionEventX(paramMotionEvent, this.mActivePointerId);
              this.mLastX = f;
              this.mInitDownX = f;
            } 
            return super.onTouchEvent(paramMotionEvent);
          } 
        } else {
          if (this.disableScroll && paramMotionEvent.getAction() == 2)
            return true; 
          float f1 = getMotionEventY(paramMotionEvent, this.mActivePointerId);
          float f2 = getMotionEventX(paramMotionEvent, this.mActivePointerId);
          float f3 = f1 - this.mLastY;
          float f4 = f2 - this.mLastX;
          this.mLastY = f1;
          this.mLastX = f2;
          if (Math.abs(f4) > Math.abs(f3) && Math.abs(f4) > this.mTouchSlop)
            return false; 
          if (STATUS.isStatusDefault(this.mStatus)) {
            if (f3 > 0.0F && onCheckCanRefresh()) {
              this.mRefreshCallback.onPageRefresh();
              setStatus(-1);
            } else if (f3 < 0.0F && onCheckCanLoadMore()) {
              this.mLoadMoreCallback.onPageRefresh();
              setStatus(1);
            } 
          } else if (STATUS.isRefreshStatus(this.mStatus)) {
            if (this.mTargetOffset <= 0) {
              setStatus(0);
              fixCurrentStatusLayout();
              return false;
            } 
          } else if (STATUS.isLoadMoreStatus(this.mStatus) && this.mTargetOffset >= 0) {
            setStatus(0);
            fixCurrentStatusLayout();
            return false;
          } 
          if (STATUS.isRefreshStatus(this.mStatus)) {
            if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isReleaseToRefresh(this.mStatus)) {
              if (this.mTargetOffset >= this.mRefreshTriggerOffset) {
                setStatus(-2);
              } else {
                setStatus(-1);
              } 
              fingerScroll(f3);
              return true;
            } 
            if (STATUS.isRefreshing(this.mStatus))
              fingerScroll(f3); 
          } else {
            if (STATUS.isLoadMoreStatus(this.mStatus) && (STATUS.isSwipingToLoadMore(this.mStatus) || STATUS.isReleaseToLoadMore(this.mStatus))) {
              if (-this.mTargetOffset >= this.mLoadMoreTriggerOffset) {
                setStatus(2);
              } else {
                setStatus(1);
              } 
            } else {
              return true;
            } 
            fingerScroll(f3);
          } 
          return true;
        } 
      } else if (this.mInterceptedMoveEvent) {
        this.mTargetView.onTouchEvent(paramMotionEvent);
        this.mInterceptedMoveEvent = false;
      } 
      this.mDisableRefresh = false;
      if (this.mActivePointerId == -1)
        return false; 
      this.mActivePointerId = -1;
      return super.onTouchEvent(paramMotionEvent);
    } 
    this.mActivePointerId = i.b(paramMotionEvent, 0);
    return true;
  }
  
  public void scrollLoadingMoreToDefault() {
    this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mLoadMoreCompleteToDefaultScrollingDuration);
  }
  
  public void scrollRefreshingToDefault() {
    this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mRefreshCompleteToDefaultScrollingDuration);
  }
  
  public void setDebug(boolean paramBoolean) {
    this.mDebug = paramBoolean;
  }
  
  public void setDefaultToLoadingMoreScrollingDuration(int paramInt) {
    this.mDefaultToLoadingMoreScrollingDuration = paramInt;
  }
  
  public void setDefaultToRefreshingScrollingDuration(int paramInt) {
    this.mDefaultToRefreshingScrollingDuration = paramInt;
  }
  
  public void setDisableRefresh(boolean paramBoolean) {
    this.mDisableRefresh = paramBoolean;
  }
  
  public void setDisableScroll(boolean paramBoolean) {
    this.disableScroll = paramBoolean;
  }
  
  public void setDragRatio(float paramFloat) {
    this.mDragRatio = paramFloat;
  }
  
  public void setLoadMoreCompleteDelayDuration(int paramInt) {
    this.mLoadMoreCompleteDelayDuration = paramInt;
  }
  
  public void setLoadMoreCompleteToDefaultScrollingDuration(int paramInt) {
    this.mLoadMoreCompleteToDefaultScrollingDuration = paramInt;
  }
  
  public void setLoadMoreEnabled(boolean paramBoolean) {
    this.mLoadMoreEnabled = paramBoolean;
  }
  
  public void setLoadMoreFinalDragOffset(int paramInt) {
    this.mLoadMoreFinalDragOffset = paramInt;
  }
  
  public void setLoadMoreFooterView(View paramView) {
    if (paramView instanceof SwipeLoadMoreTrigger) {
      View view = this.mFooterView;
      if (view != null && view != paramView)
        removeView(view); 
      if (this.mFooterView != paramView) {
        this.mFooterView = paramView;
        addView(this.mFooterView);
        return;
      } 
    } else {
      AppBrandLogger.e("SwipeToLoadLayout", new Object[] { "Load more footer view must be an implement of SwipeLoadTrigger" });
    } 
  }
  
  public void setLoadMoreTriggerOffset(int paramInt) {
    this.mLoadMoreTriggerOffset = paramInt;
  }
  
  public void setLoadingMore(boolean paramBoolean) {
    if (isLoadMoreEnabled()) {
      if (this.mFooterView == null)
        return; 
      this.mAutoLoading = paramBoolean;
      if (paramBoolean) {
        if (STATUS.isStatusDefault(this.mStatus)) {
          setStatus(1);
          scrollDefaultToLoadingMore();
          return;
        } 
      } else if (STATUS.isLoadingMore(this.mStatus)) {
        this.mLoadMoreCallback.onRefreshComplete();
        postDelayed(new Runnable() {
              public void run() {
                SwipeToLoadLayout.this.scrollLoadingMoreToDefault();
              }
            },  this.mLoadMoreCompleteDelayDuration);
      } 
    } 
  }
  
  public void setOnLoadMoreListener(OnLoadMoreListener paramOnLoadMoreListener) {
    this.mLoadMoreListener = paramOnLoadMoreListener;
  }
  
  public void setOnRefreshListener(OnRefreshListener paramOnRefreshListener) {
    this.mRefreshListener = paramOnRefreshListener;
  }
  
  public void setRefreshCompleteDelayDuration(int paramInt) {
    this.mRefreshCompleteDelayDuration = paramInt;
  }
  
  public void setRefreshCompleteToDefaultScrollingDuration(int paramInt) {
    this.mRefreshCompleteToDefaultScrollingDuration = paramInt;
  }
  
  public void setRefreshEnabled(boolean paramBoolean) {
    this.mRefreshEnabled = paramBoolean;
  }
  
  public void setRefreshFinalDragOffset(int paramInt) {
    this.mRefreshFinalDragOffset = paramInt;
  }
  
  public void setRefreshHeaderView(View paramView) {
    if (paramView instanceof SwipeRefreshTrigger) {
      View view = this.mHeaderView;
      if (view != null && view != paramView)
        removeView(view); 
      if (this.mHeaderView != paramView) {
        this.mHeaderView = paramView;
        addView(paramView);
        return;
      } 
    } else {
      AppBrandLogger.e("SwipeToLoadLayout", new Object[] { "Refresh header view must be an implement of SwipeRefreshTrigger" });
    } 
  }
  
  public void setRefreshTriggerOffset(int paramInt) {
    this.mRefreshTriggerOffset = paramInt;
  }
  
  public void setRefreshing(boolean paramBoolean) {
    if (isRefreshEnabled()) {
      if (this.mHeaderView == null)
        return; 
      this.mAutoLoading = paramBoolean;
      if (paramBoolean) {
        if (STATUS.isStatusDefault(this.mStatus)) {
          setStatus(-1);
          scrollDefaultToRefreshing();
          return;
        } 
      } else if (STATUS.isRefreshing(this.mStatus)) {
        this.mRefreshCallback.onRefreshComplete();
        postDelayed(new Runnable() {
              public void run() {
                SwipeToLoadLayout.this.scrollRefreshingToDefault();
              }
            },  this.mRefreshCompleteDelayDuration);
      } 
    } 
  }
  
  public void setReleaseToLoadingMoreScrollingDuration(int paramInt) {
    this.mReleaseToLoadMoreToLoadingMoreScrollingDuration = paramInt;
  }
  
  public void setReleaseToRefreshingScrollingDuration(int paramInt) {
    this.mReleaseToRefreshToRefreshingScrollingDuration = paramInt;
  }
  
  public void setSwipeStyle(int paramInt) {
    this.mStyle = paramInt;
    requestLayout();
  }
  
  public void setSwipingToLoadMoreToDefaultScrollingDuration(int paramInt) {
    this.mSwipingToLoadMoreToDefaultScrollingDuration = paramInt;
  }
  
  public void setSwipingToRefreshToDefaultScrollingDuration(int paramInt) {
    this.mSwipingToRefreshToDefaultScrollingDuration = paramInt;
  }
  
  public void updateOffset(int paramInt) {
    this.mTargetOffset = paramInt;
  }
  
  class AutoScroller implements Runnable {
    private boolean mAbort;
    
    private boolean mRunning;
    
    private Scroller mScroller = new Scroller(SwipeToLoadLayout.this.getContext());
    
    private int mmLastY;
    
    private void finish() {
      this.mmLastY = 0;
      this.mRunning = false;
      SwipeToLoadLayout.this.removeCallbacks(this);
      if (!this.mAbort)
        SwipeToLoadLayout.this.autoScrollFinished(); 
    }
    
    public void abortIfRunning() {
      if (this.mRunning) {
        if (!this.mScroller.isFinished()) {
          this.mAbort = true;
          this.mScroller.forceFinished(true);
        } 
        finish();
        this.mAbort = false;
      } 
    }
    
    public void autoScroll(int param1Int1, int param1Int2) {
      SwipeToLoadLayout.this.removeCallbacks(this);
      this.mmLastY = 0;
      if (!this.mScroller.isFinished())
        this.mScroller.forceFinished(true); 
      this.mScroller.startScroll(0, 0, 0, param1Int1, param1Int2);
      SwipeToLoadLayout.this.post(this);
      this.mRunning = true;
    }
    
    public void run() {
      boolean bool;
      if (!this.mScroller.computeScrollOffset() || this.mScroller.isFinished()) {
        bool = true;
      } else {
        bool = false;
      } 
      int i = this.mScroller.getCurrY();
      int j = this.mmLastY;
      if (bool) {
        finish();
        return;
      } 
      this.mmLastY = i;
      SwipeToLoadLayout.this.autoScroll((i - j));
      SwipeToLoadLayout.this.post(this);
    }
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
  
  abstract class LoadMoreCallback implements SwipeLoadMoreTrigger, SwipeTrigger {}
  
  abstract class RefreshCallback implements SwipeRefreshTrigger, SwipeTrigger {}
  
  static final class STATUS {
    public static String getStatus(int param1Int) {
      switch (param1Int) {
        default:
          return "status_illegal!";
        case 4:
          return "status_load_more_returning";
        case 3:
          return "status_loading_more";
        case 2:
          return "status_release_to_load_more";
        case 1:
          return "status_swiping_to_load_more";
        case 0:
          return "status_default";
        case -1:
          return "status_swiping_to_refresh";
        case -2:
          return "status_release_to_refresh";
        case -3:
          return "status_refreshing";
        case -4:
          break;
      } 
      return "status_refresh_returning";
    }
    
    public static boolean isLoadMoreStatus(int param1Int) {
      return (param1Int > 0);
    }
    
    public static boolean isLoadingMore(int param1Int) {
      return (param1Int == 3);
    }
    
    public static boolean isRefreshStatus(int param1Int) {
      return (param1Int < 0);
    }
    
    public static boolean isRefreshing(int param1Int) {
      return (param1Int == -3);
    }
    
    public static boolean isReleaseToLoadMore(int param1Int) {
      return (param1Int == 2);
    }
    
    public static boolean isReleaseToRefresh(int param1Int) {
      return (param1Int == -2);
    }
    
    public static boolean isStatusDefault(int param1Int) {
      return (param1Int == 0);
    }
    
    public static boolean isSwipingToLoadMore(int param1Int) {
      return (param1Int == 1);
    }
    
    public static boolean isSwipingToRefresh(int param1Int) {
      return (param1Int == -1);
    }
    
    public static void printStatus(int param1Int) {
      AppBrandLogger.i("SwipeToLoadLayout", new Object[] { "printStatus:", getStatus(param1Int) });
    }
  }
  
  public static final class STYLE {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\refresh\SwipeToLoadLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */