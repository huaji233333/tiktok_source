package com.facebook.react.uimanager;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.facebook.i.a.a;
import com.facebook.react.animation.Animation;
import com.facebook.react.animation.AnimationListener;
import com.facebook.react.animation.AnimationRegistry;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationController;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationListener;
import java.lang.ref.WeakReference;

public class NativeViewHierarchyManager {
  private static final String TAG = NativeViewHierarchyManager.class.getSimpleName();
  
  public final AnimationRegistry mAnimationRegistry;
  
  private WeakReference<CatalystInstance> mCatalyInstance;
  
  private long mFirstScreenPaintedFinishTime = 0L;
  
  private final JSResponderHandler mJSResponderHandler = new JSResponderHandler();
  
  private boolean mLayoutAnimationEnabled;
  
  private final LayoutAnimationController mLayoutAnimator = new LayoutAnimationController();
  
  private final SparseBooleanArray mRootTags;
  
  private final RootViewManager mRootViewManager;
  
  private int mScreenHigh = 0;
  
  private final SparseArray<ViewManager> mTagsToViewManagers;
  
  private final SparseArray<View> mTagsToViews;
  
  private final ViewManagerRegistry mViewManagers;
  
  public NativeViewHierarchyManager(ViewManagerRegistry paramViewManagerRegistry) {
    this(paramViewManagerRegistry, new RootViewManager());
  }
  
  public NativeViewHierarchyManager(ViewManagerRegistry paramViewManagerRegistry, RootViewManager paramRootViewManager) {
    this.mAnimationRegistry = new AnimationRegistry();
    this.mViewManagers = paramViewManagerRegistry;
    this.mTagsToViews = new SparseArray();
    this.mTagsToViewManagers = new SparseArray();
    this.mRootTags = new SparseBooleanArray();
    this.mRootViewManager = paramRootViewManager;
    this.mFirstScreenPaintedFinishTime = 0L;
  }
  
  public NativeViewHierarchyManager(ViewManagerRegistry paramViewManagerRegistry, RootViewManager paramRootViewManager, WeakReference<CatalystInstance> paramWeakReference) {
    this.mCatalyInstance = paramWeakReference;
    this.mAnimationRegistry = new AnimationRegistry();
    this.mViewManagers = paramViewManagerRegistry;
    this.mTagsToViews = new SparseArray();
    this.mTagsToViewManagers = new SparseArray();
    this.mRootTags = new SparseBooleanArray();
    this.mRootViewManager = paramRootViewManager;
    this.mFirstScreenPaintedFinishTime = 0L;
  }
  
  public NativeViewHierarchyManager(ViewManagerRegistry paramViewManagerRegistry, WeakReference<CatalystInstance> paramWeakReference) {
    this(paramViewManagerRegistry, new RootViewManager(), paramWeakReference);
  }
  
  private boolean arrayContains(int[] paramArrayOfint, int paramInt) {
    if (paramArrayOfint == null)
      return false; 
    int j = paramArrayOfint.length;
    for (int i = 0; i < j; i++) {
      if (paramArrayOfint[i] == paramInt)
        return true; 
    } 
    return false;
  }
  
  private static String constructManageChildrenErrorMessage(ViewGroup paramViewGroup, ViewGroupManager<ViewGroup> paramViewGroupManager, int[] paramArrayOfint1, ViewAtIndex[] paramArrayOfViewAtIndex, int[] paramArrayOfint2) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramViewGroup != null) {
      StringBuilder stringBuilder1 = new StringBuilder("View tag:");
      stringBuilder1.append(paramViewGroup.getId());
      stringBuilder1.append("\n");
      stringBuilder.append(stringBuilder1.toString());
      stringBuilder1 = new StringBuilder("  children(");
      stringBuilder1.append(paramViewGroupManager.getChildCount(paramViewGroup));
      stringBuilder1.append("): [\n");
      stringBuilder.append(stringBuilder1.toString());
      int i;
      for (i = 0; i < paramViewGroupManager.getChildCount((T)paramViewGroup); i += 16) {
        int j = 0;
        while (true) {
          int k = i + j;
          if (k < paramViewGroupManager.getChildCount((T)paramViewGroup) && j < 16) {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramViewGroupManager.getChildAt(paramViewGroup, k).getId());
            stringBuilder1.append(",");
            stringBuilder.append(stringBuilder1.toString());
            j++;
            continue;
          } 
          break;
        } 
        stringBuilder.append("\n");
      } 
      stringBuilder.append(" ],\n");
    } 
    if (paramArrayOfint1 != null) {
      StringBuilder stringBuilder1 = new StringBuilder("  indicesToRemove(");
      stringBuilder1.append(paramArrayOfint1.length);
      stringBuilder1.append("): [\n");
      stringBuilder.append(stringBuilder1.toString());
      int i;
      for (i = 0; i < paramArrayOfint1.length; i += 16) {
        int j = 0;
        while (true) {
          int k = i + j;
          if (k < paramArrayOfint1.length && j < 16) {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramArrayOfint1[k]);
            stringBuilder1.append(",");
            stringBuilder.append(stringBuilder1.toString());
            j++;
            continue;
          } 
          break;
        } 
        stringBuilder.append("\n");
      } 
      stringBuilder.append(" ],\n");
    } 
    if (paramArrayOfViewAtIndex != null) {
      StringBuilder stringBuilder1 = new StringBuilder("  viewsToAdd(");
      stringBuilder1.append(paramArrayOfViewAtIndex.length);
      stringBuilder1.append("): [\n");
      stringBuilder.append(stringBuilder1.toString());
      int i;
      for (i = 0; i < paramArrayOfViewAtIndex.length; i += 16) {
        int j = 0;
        while (true) {
          int k = i + j;
          if (k < paramArrayOfViewAtIndex.length && j < 16) {
            stringBuilder1 = new StringBuilder("[");
            stringBuilder1.append((paramArrayOfViewAtIndex[k]).mIndex);
            stringBuilder1.append(",");
            stringBuilder1.append((paramArrayOfViewAtIndex[k]).mTag);
            stringBuilder1.append("],");
            stringBuilder.append(stringBuilder1.toString());
            j++;
            continue;
          } 
          break;
        } 
        stringBuilder.append("\n");
      } 
      stringBuilder.append(" ],\n");
    } 
    if (paramArrayOfint2 != null) {
      StringBuilder stringBuilder1 = new StringBuilder("  tagsToDelete(");
      stringBuilder1.append(paramArrayOfint2.length);
      stringBuilder1.append("): [\n");
      stringBuilder.append(stringBuilder1.toString());
      int i;
      for (i = 0; i < paramArrayOfint2.length; i += 16) {
        int j = 0;
        while (true) {
          int k = i + j;
          if (k < paramArrayOfint2.length && j < 16) {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramArrayOfint2[k]);
            stringBuilder1.append(",");
            stringBuilder.append(stringBuilder1.toString());
            j++;
            continue;
          } 
          break;
        } 
        stringBuilder.append("\n");
      } 
      stringBuilder.append(" ]\n");
    } 
    return stringBuilder.toString();
  }
  
  private static String constructSetChildrenErrorMessage(ViewGroup paramViewGroup, ViewGroupManager paramViewGroupManager, ReadableArray paramReadableArray) {
    ViewAtIndex[] arrayOfViewAtIndex = new ViewAtIndex[paramReadableArray.size()];
    for (int i = 0; i < paramReadableArray.size(); i++)
      arrayOfViewAtIndex[i] = new ViewAtIndex(paramReadableArray.getInt(i), i); 
    return constructManageChildrenErrorMessage(paramViewGroup, paramViewGroupManager, null, arrayOfViewAtIndex, null);
  }
  
  private ThemedReactContext getReactContextForView(int paramInt) {
    View view = (View)this.mTagsToViews.get(paramInt);
    if (view != null)
      return (ThemedReactContext)view.getContext(); 
    StringBuilder stringBuilder = new StringBuilder("Could not find view with tag ");
    stringBuilder.append(paramInt);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  private void updateLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mLayoutAnimationEnabled && this.mLayoutAnimator.shouldAnimateLayout(paramView)) {
      this.mLayoutAnimator.applyLayoutUpdate(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    } 
    paramInt4 += paramInt2;
    paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4);
    WeakReference<CatalystInstance> weakReference = this.mCatalyInstance;
    if (weakReference != null)
      ((CatalystInstance)weakReference.get()).updateLayout(); 
    if (paramInt4 >= this.mScreenHigh && this.mFirstScreenPaintedFinishTime == 0L) {
      this.mFirstScreenPaintedFinishTime = System.currentTimeMillis();
      weakReference = this.mCatalyInstance;
      if (weakReference != null)
        ((CatalystInstance)weakReference.get()).setFirstScreenPaintFinished(this.mFirstScreenPaintedFinishTime); 
    } 
  }
  
  public void addRootView(int paramInt, SizeMonitoringFrameLayout paramSizeMonitoringFrameLayout, ThemedReactContext paramThemedReactContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iload_1
    //   4: aload_2
    //   5: aload_3
    //   6: invokevirtual addRootViewGroup : (ILandroid/view/ViewGroup;Lcom/facebook/react/uimanager/ThemedReactContext;)V
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: astore_2
    //   13: aload_0
    //   14: monitorexit
    //   15: aload_2
    //   16: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	12	finally
  }
  
  protected final void addRootViewGroup(int paramInt, ViewGroup paramViewGroup, ThemedReactContext paramThemedReactContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: invokevirtual getId : ()I
    //   6: iconst_m1
    //   7: if_icmpne -> 48
    //   10: aload_0
    //   11: getfield mTagsToViews : Landroid/util/SparseArray;
    //   14: iload_1
    //   15: aload_2
    //   16: invokevirtual put : (ILjava/lang/Object;)V
    //   19: aload_0
    //   20: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   23: iload_1
    //   24: aload_0
    //   25: getfield mRootViewManager : Lcom/facebook/react/uimanager/RootViewManager;
    //   28: invokevirtual put : (ILjava/lang/Object;)V
    //   31: aload_0
    //   32: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   35: iload_1
    //   36: iconst_1
    //   37: invokevirtual put : (IZ)V
    //   40: aload_2
    //   41: iload_1
    //   42: invokevirtual setId : (I)V
    //   45: aload_0
    //   46: monitorexit
    //   47: return
    //   48: new com/facebook/react/uimanager/IllegalViewOperationException
    //   51: dup
    //   52: ldc_w 'Trying to add a root view with an explicit id already set. React Native uses the id field to track react tags and will overwrite this field. If that is fine, explicitly overwrite the id field to View.NO_ID before calling addRootView.'
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: athrow
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   2	45	59	finally
    //   48	59	59	finally
  }
  
  public void clearJSResponder() {
    this.mJSResponderHandler.clearJSResponder();
  }
  
  void clearLayoutAnimation() {
    this.mLayoutAnimator.reset();
  }
  
  void configureLayoutAnimation(ReadableMap paramReadableMap) {
    this.mLayoutAnimator.initializeFromConfig(paramReadableMap);
  }
  
  public void createView(ThemedReactContext paramThemedReactContext, int paramInt, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mScreenHigh : I
    //   9: ifne -> 26
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   17: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   20: getfield heightPixels : I
    //   23: putfield mScreenHigh : I
    //   26: aload_0
    //   27: getfield mViewManagers : Lcom/facebook/react/uimanager/ViewManagerRegistry;
    //   30: aload_3
    //   31: invokevirtual get : (Ljava/lang/String;)Lcom/facebook/react/uimanager/ViewManager;
    //   34: astore_3
    //   35: aload_3
    //   36: aload_1
    //   37: aload_0
    //   38: getfield mJSResponderHandler : Lcom/facebook/react/touch/JSResponderHandler;
    //   41: invokevirtual createView : (Lcom/facebook/react/uimanager/ThemedReactContext;Lcom/facebook/react/touch/JSResponderHandler;)Landroid/view/View;
    //   44: astore_1
    //   45: aload_0
    //   46: getfield mTagsToViews : Landroid/util/SparseArray;
    //   49: iload_2
    //   50: aload_1
    //   51: invokevirtual put : (ILjava/lang/Object;)V
    //   54: aload_0
    //   55: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   58: iload_2
    //   59: aload_3
    //   60: invokevirtual put : (ILjava/lang/Object;)V
    //   63: aload_1
    //   64: iload_2
    //   65: invokevirtual setId : (I)V
    //   68: aload #4
    //   70: ifnull -> 80
    //   73: aload_3
    //   74: aload_1
    //   75: aload #4
    //   77: invokevirtual updateProperties : (Landroid/view/View;Lcom/facebook/react/uimanager/ReactStylesDiffMap;)V
    //   80: lconst_0
    //   81: invokestatic a : (J)V
    //   84: aload_0
    //   85: monitorexit
    //   86: return
    //   87: astore_1
    //   88: lconst_0
    //   89: invokestatic a : (J)V
    //   92: aload_1
    //   93: athrow
    //   94: astore_1
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_1
    //   98: athrow
    //   99: astore #5
    //   101: goto -> 26
    // Exception table:
    //   from	to	target	type
    //   2	5	94	finally
    //   5	26	99	finally
    //   26	68	87	finally
    //   73	80	87	finally
    //   80	84	94	finally
    //   88	94	94	finally
  }
  
  public void dispatchCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore #4
    //   18: aload #4
    //   20: ifnull -> 38
    //   23: aload_0
    //   24: iload_1
    //   25: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   28: aload #4
    //   30: iload_2
    //   31: aload_3
    //   32: invokevirtual receiveCommand : (Landroid/view/View;ILcom/facebook/react/bridge/ReadableArray;)V
    //   35: aload_0
    //   36: monitorexit
    //   37: return
    //   38: new java/lang/StringBuilder
    //   41: dup
    //   42: ldc_w 'Trying to send command to a non-existing view with tag '
    //   45: invokespecial <init> : (Ljava/lang/String;)V
    //   48: astore_3
    //   49: aload_3
    //   50: iload_1
    //   51: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: new com/facebook/react/uimanager/IllegalViewOperationException
    //   58: dup
    //   59: aload_3
    //   60: invokevirtual toString : ()Ljava/lang/String;
    //   63: invokespecial <init> : (Ljava/lang/String;)V
    //   66: athrow
    //   67: astore_3
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_3
    //   71: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	67	finally
    //   23	35	67	finally
    //   38	67	67	finally
  }
  
  public void dropView(View paramView) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   9: aload_1
    //   10: invokevirtual getId : ()I
    //   13: invokevirtual get : (I)Z
    //   16: ifne -> 31
    //   19: aload_0
    //   20: aload_1
    //   21: invokevirtual getId : ()I
    //   24: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   27: aload_1
    //   28: invokevirtual onDropViewInstance : (Landroid/view/View;)V
    //   31: aload_0
    //   32: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   35: aload_1
    //   36: invokevirtual getId : ()I
    //   39: invokevirtual get : (I)Ljava/lang/Object;
    //   42: checkcast com/facebook/react/uimanager/ViewManager
    //   45: astore #4
    //   47: aload_1
    //   48: instanceof android/view/ViewGroup
    //   51: ifeq -> 126
    //   54: aload #4
    //   56: instanceof com/facebook/react/uimanager/ViewGroupManager
    //   59: ifeq -> 126
    //   62: aload_1
    //   63: checkcast android/view/ViewGroup
    //   66: astore_3
    //   67: aload #4
    //   69: checkcast com/facebook/react/uimanager/ViewGroupManager
    //   72: astore #4
    //   74: aload #4
    //   76: aload_3
    //   77: invokevirtual getChildCount : (Landroid/view/ViewGroup;)I
    //   80: iconst_1
    //   81: isub
    //   82: istore_2
    //   83: iload_2
    //   84: iflt -> 120
    //   87: aload #4
    //   89: aload_3
    //   90: iload_2
    //   91: invokevirtual getChildAt : (Landroid/view/ViewGroup;I)Landroid/view/View;
    //   94: astore #5
    //   96: aload_0
    //   97: getfield mTagsToViews : Landroid/util/SparseArray;
    //   100: aload #5
    //   102: invokevirtual getId : ()I
    //   105: invokevirtual get : (I)Ljava/lang/Object;
    //   108: ifnull -> 162
    //   111: aload_0
    //   112: aload #5
    //   114: invokevirtual dropView : (Landroid/view/View;)V
    //   117: goto -> 162
    //   120: aload #4
    //   122: aload_3
    //   123: invokevirtual removeAllViews : (Landroid/view/ViewGroup;)V
    //   126: aload_0
    //   127: getfield mTagsToViews : Landroid/util/SparseArray;
    //   130: aload_1
    //   131: invokevirtual getId : ()I
    //   134: invokevirtual remove : (I)V
    //   137: aload_0
    //   138: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   141: aload_1
    //   142: invokevirtual getId : ()I
    //   145: invokevirtual remove : (I)V
    //   148: aload_0
    //   149: monitorexit
    //   150: return
    //   151: astore_1
    //   152: aload_0
    //   153: monitorexit
    //   154: goto -> 159
    //   157: aload_1
    //   158: athrow
    //   159: goto -> 157
    //   162: iload_2
    //   163: iconst_1
    //   164: isub
    //   165: istore_2
    //   166: goto -> 83
    // Exception table:
    //   from	to	target	type
    //   2	31	151	finally
    //   31	83	151	finally
    //   87	117	151	finally
    //   120	126	151	finally
    //   126	148	151	finally
  }
  
  public int findTargetTagForTouch(int paramInt, float paramFloat1, float paramFloat2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore #4
    //   18: aload #4
    //   20: ifnull -> 38
    //   23: fload_2
    //   24: fload_3
    //   25: aload #4
    //   27: checkcast android/view/ViewGroup
    //   30: invokestatic findTargetTagForTouch : (FFLandroid/view/ViewGroup;)I
    //   33: istore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: iload_1
    //   37: ireturn
    //   38: new java/lang/StringBuilder
    //   41: dup
    //   42: ldc 'Could not find view with tag '
    //   44: invokespecial <init> : (Ljava/lang/String;)V
    //   47: astore #4
    //   49: aload #4
    //   51: iload_1
    //   52: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: new com/facebook/react/bridge/JSApplicationIllegalArgumentException
    //   59: dup
    //   60: aload #4
    //   62: invokevirtual toString : ()Ljava/lang/String;
    //   65: invokespecial <init> : (Ljava/lang/String;)V
    //   68: athrow
    //   69: astore #4
    //   71: aload_0
    //   72: monitorexit
    //   73: aload #4
    //   75: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	69	finally
    //   23	34	69	finally
    //   38	69	69	finally
  }
  
  public AnimationRegistry getAnimationRegistry() {
    return this.mAnimationRegistry;
  }
  
  public void manageChildren(int paramInt, int[] paramArrayOfint1, ViewAtIndex[] paramArrayOfViewAtIndex, int[] paramArrayOfint2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/ViewGroup
    //   16: astore #8
    //   18: aload_0
    //   19: iload_1
    //   20: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   23: checkcast com/facebook/react/uimanager/ViewGroupManager
    //   26: astore #9
    //   28: aload #8
    //   30: ifnull -> 644
    //   33: aload #9
    //   35: aload #8
    //   37: invokevirtual getChildCount : (Landroid/view/ViewGroup;)I
    //   40: istore #5
    //   42: aload_2
    //   43: ifnull -> 726
    //   46: aload_2
    //   47: arraylength
    //   48: iconst_1
    //   49: isub
    //   50: istore #6
    //   52: iload #6
    //   54: iflt -> 726
    //   57: aload_2
    //   58: iload #6
    //   60: iaload
    //   61: istore #7
    //   63: iload #7
    //   65: iflt -> 305
    //   68: iload #7
    //   70: aload #9
    //   72: aload #8
    //   74: invokevirtual getChildCount : (Landroid/view/ViewGroup;)I
    //   77: if_icmpge -> 230
    //   80: iload #7
    //   82: iload #5
    //   84: if_icmpge -> 155
    //   87: aload #9
    //   89: aload #8
    //   91: iload #7
    //   93: invokevirtual getChildAt : (Landroid/view/ViewGroup;I)Landroid/view/View;
    //   96: astore #10
    //   98: aload_0
    //   99: getfield mLayoutAnimationEnabled : Z
    //   102: ifeq -> 143
    //   105: aload_0
    //   106: getfield mLayoutAnimator : Lcom/facebook/react/uimanager/layoutanimation/LayoutAnimationController;
    //   109: aload #10
    //   111: invokevirtual shouldAnimateLayout : (Landroid/view/View;)Z
    //   114: ifeq -> 143
    //   117: aload_0
    //   118: aload #4
    //   120: aload #10
    //   122: invokevirtual getId : ()I
    //   125: invokespecial arrayContains : ([II)Z
    //   128: ifeq -> 143
    //   131: aload #9
    //   133: aload #8
    //   135: iload #7
    //   137: invokevirtual markView : (Landroid/view/ViewGroup;I)V
    //   140: goto -> 713
    //   143: aload #9
    //   145: aload #8
    //   147: aload #10
    //   149: invokevirtual removeView : (Landroid/view/ViewGroup;Landroid/view/View;)V
    //   152: goto -> 713
    //   155: new java/lang/StringBuilder
    //   158: dup
    //   159: ldc_w 'Trying to remove an out of order view index:'
    //   162: invokespecial <init> : (Ljava/lang/String;)V
    //   165: astore #10
    //   167: aload #10
    //   169: iload #7
    //   171: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload #10
    //   177: ldc_w ' view tag: '
    //   180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: aload #10
    //   186: iload_1
    //   187: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload #10
    //   193: ldc_w '\\n detail: '
    //   196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload #10
    //   202: aload #8
    //   204: aload #9
    //   206: aload_2
    //   207: aload_3
    //   208: aload #4
    //   210: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   213: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: new com/facebook/react/uimanager/IllegalViewOperationException
    //   220: dup
    //   221: aload #10
    //   223: invokevirtual toString : ()Ljava/lang/String;
    //   226: invokespecial <init> : (Ljava/lang/String;)V
    //   229: athrow
    //   230: new java/lang/StringBuilder
    //   233: dup
    //   234: ldc_w 'Trying to remove a view index above child count '
    //   237: invokespecial <init> : (Ljava/lang/String;)V
    //   240: astore #10
    //   242: aload #10
    //   244: iload #7
    //   246: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   249: pop
    //   250: aload #10
    //   252: ldc_w ' view tag: '
    //   255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: pop
    //   259: aload #10
    //   261: iload_1
    //   262: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload #10
    //   268: ldc_w '\\n detail: '
    //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: pop
    //   275: aload #10
    //   277: aload #8
    //   279: aload #9
    //   281: aload_2
    //   282: aload_3
    //   283: aload #4
    //   285: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   288: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   291: pop
    //   292: new com/facebook/react/uimanager/IllegalViewOperationException
    //   295: dup
    //   296: aload #10
    //   298: invokevirtual toString : ()Ljava/lang/String;
    //   301: invokespecial <init> : (Ljava/lang/String;)V
    //   304: athrow
    //   305: new java/lang/StringBuilder
    //   308: dup
    //   309: ldc_w 'Trying to remove a negative view index:'
    //   312: invokespecial <init> : (Ljava/lang/String;)V
    //   315: astore #10
    //   317: aload #10
    //   319: iload #7
    //   321: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   324: pop
    //   325: aload #10
    //   327: ldc_w ' view tag: '
    //   330: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   333: pop
    //   334: aload #10
    //   336: iload_1
    //   337: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   340: pop
    //   341: aload #10
    //   343: ldc_w '\\n detail: '
    //   346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   349: pop
    //   350: aload #10
    //   352: aload #8
    //   354: aload #9
    //   356: aload_2
    //   357: aload_3
    //   358: aload #4
    //   360: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   363: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: pop
    //   367: new com/facebook/react/uimanager/IllegalViewOperationException
    //   370: dup
    //   371: aload #10
    //   373: invokevirtual toString : ()Ljava/lang/String;
    //   376: invokespecial <init> : (Ljava/lang/String;)V
    //   379: athrow
    //   380: iload_1
    //   381: aload_3
    //   382: arraylength
    //   383: if_icmpge -> 738
    //   386: aload_3
    //   387: iload_1
    //   388: aaload
    //   389: astore #10
    //   391: aload_0
    //   392: getfield mTagsToViews : Landroid/util/SparseArray;
    //   395: aload #10
    //   397: getfield mTag : I
    //   400: invokevirtual get : (I)Ljava/lang/Object;
    //   403: checkcast android/view/View
    //   406: astore #11
    //   408: aload #11
    //   410: ifnull -> 434
    //   413: aload #9
    //   415: aload #8
    //   417: aload #11
    //   419: aload #10
    //   421: getfield mIndex : I
    //   424: invokevirtual addView : (Landroid/view/ViewGroup;Landroid/view/View;I)V
    //   427: iload_1
    //   428: iconst_1
    //   429: iadd
    //   430: istore_1
    //   431: goto -> 380
    //   434: new java/lang/StringBuilder
    //   437: dup
    //   438: ldc_w 'Trying to add unknown view tag: '
    //   441: invokespecial <init> : (Ljava/lang/String;)V
    //   444: astore #11
    //   446: aload #11
    //   448: aload #10
    //   450: getfield mTag : I
    //   453: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   456: pop
    //   457: aload #11
    //   459: ldc_w '\\n detail: '
    //   462: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: pop
    //   466: aload #11
    //   468: aload #8
    //   470: aload #9
    //   472: aload_2
    //   473: aload_3
    //   474: aload #4
    //   476: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   482: pop
    //   483: new com/facebook/react/uimanager/IllegalViewOperationException
    //   486: dup
    //   487: aload #11
    //   489: invokevirtual toString : ()Ljava/lang/String;
    //   492: invokespecial <init> : (Ljava/lang/String;)V
    //   495: athrow
    //   496: iload_1
    //   497: aload #4
    //   499: arraylength
    //   500: if_icmpge -> 641
    //   503: aload #4
    //   505: iload_1
    //   506: iaload
    //   507: istore #5
    //   509: aload_0
    //   510: getfield mTagsToViews : Landroid/util/SparseArray;
    //   513: iload #5
    //   515: invokevirtual get : (I)Ljava/lang/Object;
    //   518: checkcast android/view/View
    //   521: astore #10
    //   523: aload #10
    //   525: ifnull -> 582
    //   528: aload_0
    //   529: getfield mLayoutAnimationEnabled : Z
    //   532: ifeq -> 573
    //   535: aload_0
    //   536: getfield mLayoutAnimator : Lcom/facebook/react/uimanager/layoutanimation/LayoutAnimationController;
    //   539: aload #10
    //   541: invokevirtual shouldAnimateLayout : (Landroid/view/View;)Z
    //   544: ifeq -> 573
    //   547: aload_0
    //   548: getfield mLayoutAnimator : Lcom/facebook/react/uimanager/layoutanimation/LayoutAnimationController;
    //   551: aload #10
    //   553: new com/facebook/react/uimanager/NativeViewHierarchyManager$1
    //   556: dup
    //   557: aload_0
    //   558: aload #9
    //   560: aload #8
    //   562: aload #10
    //   564: invokespecial <init> : (Lcom/facebook/react/uimanager/NativeViewHierarchyManager;Lcom/facebook/react/uimanager/ViewGroupManager;Landroid/view/ViewGroup;Landroid/view/View;)V
    //   567: invokevirtual deleteView : (Landroid/view/View;Lcom/facebook/react/uimanager/layoutanimation/LayoutAnimationListener;)V
    //   570: goto -> 749
    //   573: aload_0
    //   574: aload #10
    //   576: invokevirtual dropView : (Landroid/view/View;)V
    //   579: goto -> 749
    //   582: new java/lang/StringBuilder
    //   585: dup
    //   586: ldc_w 'Trying to destroy unknown view tag: '
    //   589: invokespecial <init> : (Ljava/lang/String;)V
    //   592: astore #10
    //   594: aload #10
    //   596: iload #5
    //   598: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   601: pop
    //   602: aload #10
    //   604: ldc_w '\\n detail: '
    //   607: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: pop
    //   611: aload #10
    //   613: aload #8
    //   615: aload #9
    //   617: aload_2
    //   618: aload_3
    //   619: aload #4
    //   621: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   624: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   627: pop
    //   628: new com/facebook/react/uimanager/IllegalViewOperationException
    //   631: dup
    //   632: aload #10
    //   634: invokevirtual toString : ()Ljava/lang/String;
    //   637: invokespecial <init> : (Ljava/lang/String;)V
    //   640: athrow
    //   641: aload_0
    //   642: monitorexit
    //   643: return
    //   644: new java/lang/StringBuilder
    //   647: dup
    //   648: ldc_w 'Trying to manageChildren view with tag '
    //   651: invokespecial <init> : (Ljava/lang/String;)V
    //   654: astore #10
    //   656: aload #10
    //   658: iload_1
    //   659: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   662: pop
    //   663: aload #10
    //   665: ldc_w ' which doesn't exist\\n detail: '
    //   668: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   671: pop
    //   672: aload #10
    //   674: aload #8
    //   676: aload #9
    //   678: aload_2
    //   679: aload_3
    //   680: aload #4
    //   682: invokestatic constructManageChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;[I[Lcom/facebook/react/uimanager/ViewAtIndex;[I)Ljava/lang/String;
    //   685: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   688: pop
    //   689: new com/facebook/react/uimanager/IllegalViewOperationException
    //   692: dup
    //   693: aload #10
    //   695: invokevirtual toString : ()Ljava/lang/String;
    //   698: invokespecial <init> : (Ljava/lang/String;)V
    //   701: athrow
    //   702: astore_2
    //   703: aload_0
    //   704: monitorexit
    //   705: goto -> 710
    //   708: aload_2
    //   709: athrow
    //   710: goto -> 708
    //   713: iload #6
    //   715: iconst_1
    //   716: isub
    //   717: istore #6
    //   719: iload #7
    //   721: istore #5
    //   723: goto -> 52
    //   726: iconst_0
    //   727: istore #5
    //   729: aload_3
    //   730: ifnull -> 738
    //   733: iconst_0
    //   734: istore_1
    //   735: goto -> 380
    //   738: aload #4
    //   740: ifnull -> 641
    //   743: iload #5
    //   745: istore_1
    //   746: goto -> 496
    //   749: iload_1
    //   750: iconst_1
    //   751: iadd
    //   752: istore_1
    //   753: goto -> 496
    // Exception table:
    //   from	to	target	type
    //   2	28	702	finally
    //   33	42	702	finally
    //   46	52	702	finally
    //   68	80	702	finally
    //   87	140	702	finally
    //   143	152	702	finally
    //   155	230	702	finally
    //   230	305	702	finally
    //   305	380	702	finally
    //   380	386	702	finally
    //   391	408	702	finally
    //   413	427	702	finally
    //   434	496	702	finally
    //   496	503	702	finally
    //   509	523	702	finally
    //   528	570	702	finally
    //   573	579	702	finally
    //   582	641	702	finally
    //   644	702	702	finally
  }
  
  public void measure(int paramInt, int[] paramArrayOfint) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore #4
    //   18: aload #4
    //   20: ifnull -> 130
    //   23: aload #4
    //   25: invokestatic getRootView : (Landroid/view/View;)Lcom/facebook/react/uimanager/RootView;
    //   28: checkcast android/view/View
    //   31: astore #5
    //   33: aload #5
    //   35: ifnull -> 93
    //   38: aload #5
    //   40: aload_2
    //   41: invokevirtual getLocationInWindow : ([I)V
    //   44: aload_2
    //   45: iconst_0
    //   46: iaload
    //   47: istore_1
    //   48: aload_2
    //   49: iconst_1
    //   50: iaload
    //   51: istore_3
    //   52: aload #4
    //   54: aload_2
    //   55: invokevirtual getLocationInWindow : ([I)V
    //   58: aload_2
    //   59: iconst_0
    //   60: aload_2
    //   61: iconst_0
    //   62: iaload
    //   63: iload_1
    //   64: isub
    //   65: iastore
    //   66: aload_2
    //   67: iconst_1
    //   68: aload_2
    //   69: iconst_1
    //   70: iaload
    //   71: iload_3
    //   72: isub
    //   73: iastore
    //   74: aload_2
    //   75: iconst_2
    //   76: aload #4
    //   78: invokevirtual getWidth : ()I
    //   81: iastore
    //   82: aload_2
    //   83: iconst_3
    //   84: aload #4
    //   86: invokevirtual getHeight : ()I
    //   89: iastore
    //   90: aload_0
    //   91: monitorexit
    //   92: return
    //   93: new java/lang/StringBuilder
    //   96: dup
    //   97: ldc_w 'Native view '
    //   100: invokespecial <init> : (Ljava/lang/String;)V
    //   103: astore_2
    //   104: aload_2
    //   105: iload_1
    //   106: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   109: pop
    //   110: aload_2
    //   111: ldc_w ' is no longer on screen'
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: pop
    //   118: new com/facebook/react/uimanager/NoSuchNativeViewException
    //   121: dup
    //   122: aload_2
    //   123: invokevirtual toString : ()Ljava/lang/String;
    //   126: invokespecial <init> : (Ljava/lang/String;)V
    //   129: athrow
    //   130: new java/lang/StringBuilder
    //   133: dup
    //   134: ldc_w 'No native view for '
    //   137: invokespecial <init> : (Ljava/lang/String;)V
    //   140: astore_2
    //   141: aload_2
    //   142: iload_1
    //   143: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload_2
    //   148: ldc_w ' currently exists'
    //   151: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: new com/facebook/react/uimanager/NoSuchNativeViewException
    //   158: dup
    //   159: aload_2
    //   160: invokevirtual toString : ()Ljava/lang/String;
    //   163: invokespecial <init> : (Ljava/lang/String;)V
    //   166: athrow
    //   167: astore_2
    //   168: aload_0
    //   169: monitorexit
    //   170: aload_2
    //   171: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	167	finally
    //   23	33	167	finally
    //   38	44	167	finally
    //   52	58	167	finally
    //   74	90	167	finally
    //   93	130	167	finally
    //   130	167	167	finally
  }
  
  public void measureInWindow(int paramInt, int[] paramArrayOfint) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull -> 87
    //   21: aload_3
    //   22: aload_2
    //   23: invokevirtual getLocationOnScreen : ([I)V
    //   26: aload_3
    //   27: invokevirtual getContext : ()Landroid/content/Context;
    //   30: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   33: astore #4
    //   35: aload #4
    //   37: ldc_w 'status_bar_height'
    //   40: ldc_w 'dimen'
    //   43: ldc_w 'android'
    //   46: invokevirtual getIdentifier : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    //   49: istore_1
    //   50: iload_1
    //   51: ifle -> 70
    //   54: aload #4
    //   56: iload_1
    //   57: invokevirtual getDimension : (I)F
    //   60: f2i
    //   61: istore_1
    //   62: aload_2
    //   63: iconst_1
    //   64: aload_2
    //   65: iconst_1
    //   66: iaload
    //   67: iload_1
    //   68: isub
    //   69: iastore
    //   70: aload_2
    //   71: iconst_2
    //   72: aload_3
    //   73: invokevirtual getWidth : ()I
    //   76: iastore
    //   77: aload_2
    //   78: iconst_3
    //   79: aload_3
    //   80: invokevirtual getHeight : ()I
    //   83: iastore
    //   84: aload_0
    //   85: monitorexit
    //   86: return
    //   87: new java/lang/StringBuilder
    //   90: dup
    //   91: ldc_w 'No native view for '
    //   94: invokespecial <init> : (Ljava/lang/String;)V
    //   97: astore_2
    //   98: aload_2
    //   99: iload_1
    //   100: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload_2
    //   105: ldc_w ' currently exists'
    //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: new com/facebook/react/uimanager/NoSuchNativeViewException
    //   115: dup
    //   116: aload_2
    //   117: invokevirtual toString : ()Ljava/lang/String;
    //   120: invokespecial <init> : (Ljava/lang/String;)V
    //   123: athrow
    //   124: astore_2
    //   125: aload_0
    //   126: monitorexit
    //   127: aload_2
    //   128: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	124	finally
    //   21	50	124	finally
    //   54	62	124	finally
    //   70	84	124	finally
    //   87	124	124	finally
  }
  
  public void removeRootView(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Z
    //   13: ifne -> 48
    //   16: new java/lang/StringBuilder
    //   19: dup
    //   20: ldc_w 'View with tag '
    //   23: invokespecial <init> : (Ljava/lang/String;)V
    //   26: astore_2
    //   27: aload_2
    //   28: iload_1
    //   29: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   32: pop
    //   33: aload_2
    //   34: ldc_w ' is not registered as a root view'
    //   37: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload_2
    //   42: invokevirtual toString : ()Ljava/lang/String;
    //   45: invokestatic assertUnreachable : (Ljava/lang/String;)V
    //   48: aload_0
    //   49: aload_0
    //   50: getfield mTagsToViews : Landroid/util/SparseArray;
    //   53: iload_1
    //   54: invokevirtual get : (I)Ljava/lang/Object;
    //   57: checkcast android/view/View
    //   60: invokevirtual dropView : (Landroid/view/View;)V
    //   63: aload_0
    //   64: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   67: iload_1
    //   68: invokevirtual delete : (I)V
    //   71: aload_0
    //   72: monitorexit
    //   73: return
    //   74: astore_2
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_2
    //   78: athrow
    // Exception table:
    //   from	to	target	type
    //   2	48	74	finally
    //   48	71	74	finally
  }
  
  public final View resolveView(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mTagsToViews : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast android/view/View
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_2
    //   21: areturn
    //   22: new java/lang/StringBuilder
    //   25: dup
    //   26: ldc_w 'Trying to resolve view with tag '
    //   29: invokespecial <init> : (Ljava/lang/String;)V
    //   32: astore_2
    //   33: aload_2
    //   34: iload_1
    //   35: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_2
    //   40: ldc_w ' which doesn't exist'
    //   43: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: new com/facebook/react/uimanager/IllegalViewOperationException
    //   50: dup
    //   51: aload_2
    //   52: invokevirtual toString : ()Ljava/lang/String;
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: athrow
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	59	finally
    //   22	59	59	finally
  }
  
  public final ViewManager resolveViewManager(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/facebook/react/uimanager/ViewManager
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_2
    //   21: areturn
    //   22: new java/lang/StringBuilder
    //   25: dup
    //   26: ldc_w 'ViewManager for tag '
    //   29: invokespecial <init> : (Ljava/lang/String;)V
    //   32: astore_2
    //   33: aload_2
    //   34: iload_1
    //   35: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_2
    //   40: ldc_w ' could not be found'
    //   43: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: new com/facebook/react/uimanager/IllegalViewOperationException
    //   50: dup
    //   51: aload_2
    //   52: invokevirtual toString : ()Ljava/lang/String;
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: athrow
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	59	finally
    //   22	59	59	finally
  }
  
  public void sendAccessibilityEvent(int paramInt1, int paramInt2) {
    View view = (View)this.mTagsToViews.get(paramInt1);
    if (view != null) {
      AccessibilityHelper.sendAccessibilityEvent(view, paramInt2);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Could not find view with tag ");
    stringBuilder.append(paramInt1);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void setCatalyInstance(WeakReference<CatalystInstance> paramWeakReference) {
    this.mCatalyInstance = paramWeakReference;
  }
  
  public void setChildren(int paramInt, ReadableArray paramReadableArray) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/ViewGroup
    //   16: astore_3
    //   17: aload_0
    //   18: iload_1
    //   19: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   22: checkcast com/facebook/react/uimanager/ViewGroupManager
    //   25: astore #4
    //   27: iconst_0
    //   28: istore_1
    //   29: iload_1
    //   30: aload_2
    //   31: invokeinterface size : ()I
    //   36: if_icmpge -> 139
    //   39: aload_0
    //   40: getfield mTagsToViews : Landroid/util/SparseArray;
    //   43: aload_2
    //   44: iload_1
    //   45: invokeinterface getInt : (I)I
    //   50: invokevirtual get : (I)Ljava/lang/Object;
    //   53: checkcast android/view/View
    //   56: astore #5
    //   58: aload #5
    //   60: ifnull -> 79
    //   63: aload #4
    //   65: aload_3
    //   66: aload #5
    //   68: iload_1
    //   69: invokevirtual addView : (Landroid/view/ViewGroup;Landroid/view/View;I)V
    //   72: iload_1
    //   73: iconst_1
    //   74: iadd
    //   75: istore_1
    //   76: goto -> 29
    //   79: new java/lang/StringBuilder
    //   82: dup
    //   83: ldc_w 'Trying to add unknown view tag: '
    //   86: invokespecial <init> : (Ljava/lang/String;)V
    //   89: astore #5
    //   91: aload #5
    //   93: aload_2
    //   94: iload_1
    //   95: invokeinterface getInt : (I)I
    //   100: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload #5
    //   106: ldc_w '\\n detail: '
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload #5
    //   115: aload_3
    //   116: aload #4
    //   118: aload_2
    //   119: invokestatic constructSetChildrenErrorMessage : (Landroid/view/ViewGroup;Lcom/facebook/react/uimanager/ViewGroupManager;Lcom/facebook/react/bridge/ReadableArray;)Ljava/lang/String;
    //   122: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: pop
    //   126: new com/facebook/react/uimanager/IllegalViewOperationException
    //   129: dup
    //   130: aload #5
    //   132: invokevirtual toString : ()Ljava/lang/String;
    //   135: invokespecial <init> : (Ljava/lang/String;)V
    //   138: athrow
    //   139: aload_0
    //   140: monitorexit
    //   141: return
    //   142: astore_2
    //   143: aload_0
    //   144: monitorexit
    //   145: goto -> 150
    //   148: aload_2
    //   149: athrow
    //   150: goto -> 148
    // Exception table:
    //   from	to	target	type
    //   2	27	142	finally
    //   29	58	142	finally
    //   63	72	142	finally
    //   79	139	142	finally
  }
  
  public void setJSResponder(int paramInt1, int paramInt2, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_3
    //   3: ifne -> 18
    //   6: aload_0
    //   7: getfield mJSResponderHandler : Lcom/facebook/react/touch/JSResponderHandler;
    //   10: iload_2
    //   11: aconst_null
    //   12: invokevirtual setJSResponder : (ILandroid/view/ViewParent;)V
    //   15: aload_0
    //   16: monitorexit
    //   17: return
    //   18: aload_0
    //   19: getfield mTagsToViews : Landroid/util/SparseArray;
    //   22: iload_1
    //   23: invokevirtual get : (I)Ljava/lang/Object;
    //   26: checkcast android/view/View
    //   29: astore #4
    //   31: iload_2
    //   32: iload_1
    //   33: if_icmpeq -> 60
    //   36: aload #4
    //   38: instanceof android/view/ViewParent
    //   41: ifeq -> 60
    //   44: aload_0
    //   45: getfield mJSResponderHandler : Lcom/facebook/react/touch/JSResponderHandler;
    //   48: iload_2
    //   49: aload #4
    //   51: checkcast android/view/ViewParent
    //   54: invokevirtual setJSResponder : (ILandroid/view/ViewParent;)V
    //   57: aload_0
    //   58: monitorexit
    //   59: return
    //   60: aload_0
    //   61: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   64: iload_1
    //   65: invokevirtual get : (I)Z
    //   68: ifeq -> 107
    //   71: new java/lang/StringBuilder
    //   74: dup
    //   75: ldc_w 'Cannot block native responder on '
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: astore #5
    //   83: aload #5
    //   85: iload_1
    //   86: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   89: pop
    //   90: aload #5
    //   92: ldc_w ' that is a root view'
    //   95: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload #5
    //   101: invokevirtual toString : ()Ljava/lang/String;
    //   104: invokestatic assertUnreachable : (Ljava/lang/String;)V
    //   107: aload_0
    //   108: getfield mJSResponderHandler : Lcom/facebook/react/touch/JSResponderHandler;
    //   111: iload_2
    //   112: aload #4
    //   114: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   117: invokevirtual setJSResponder : (ILandroid/view/ViewParent;)V
    //   120: aload_0
    //   121: monitorexit
    //   122: return
    //   123: astore #4
    //   125: aload_0
    //   126: monitorexit
    //   127: aload #4
    //   129: athrow
    // Exception table:
    //   from	to	target	type
    //   6	15	123	finally
    //   18	31	123	finally
    //   36	57	123	finally
    //   60	107	123	finally
    //   107	120	123	finally
  }
  
  public void setLayoutAnimationEnabled(boolean paramBoolean) {
    this.mLayoutAnimationEnabled = paramBoolean;
  }
  
  public void showPopupMenu(int paramInt, ReadableArray paramReadableArray, Callback paramCallback1, Callback paramCallback2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore #5
    //   18: aload #5
    //   20: ifnonnull -> 61
    //   23: new java/lang/StringBuilder
    //   26: dup
    //   27: ldc_w 'Can't display popup. Could not find view with tag '
    //   30: invokespecial <init> : (Ljava/lang/String;)V
    //   33: astore_2
    //   34: aload_2
    //   35: iload_1
    //   36: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload #4
    //   42: iconst_1
    //   43: anewarray java/lang/Object
    //   46: dup
    //   47: iconst_0
    //   48: aload_2
    //   49: invokevirtual toString : ()Ljava/lang/String;
    //   52: aastore
    //   53: invokeinterface invoke : ([Ljava/lang/Object;)V
    //   58: aload_0
    //   59: monitorexit
    //   60: return
    //   61: new android/widget/PopupMenu
    //   64: dup
    //   65: aload_0
    //   66: iload_1
    //   67: invokespecial getReactContextForView : (I)Lcom/facebook/react/uimanager/ThemedReactContext;
    //   70: aload #5
    //   72: invokespecial <init> : (Landroid/content/Context;Landroid/view/View;)V
    //   75: astore #4
    //   77: aload #4
    //   79: invokevirtual getMenu : ()Landroid/view/Menu;
    //   82: astore #5
    //   84: iconst_0
    //   85: istore_1
    //   86: iload_1
    //   87: aload_2
    //   88: invokeinterface size : ()I
    //   93: if_icmpge -> 121
    //   96: aload #5
    //   98: iconst_0
    //   99: iconst_0
    //   100: iload_1
    //   101: aload_2
    //   102: iload_1
    //   103: invokeinterface getString : (I)Ljava/lang/String;
    //   108: invokeinterface add : (IIILjava/lang/CharSequence;)Landroid/view/MenuItem;
    //   113: pop
    //   114: iload_1
    //   115: iconst_1
    //   116: iadd
    //   117: istore_1
    //   118: goto -> 86
    //   121: new com/facebook/react/uimanager/NativeViewHierarchyManager$PopupMenuCallbackHandler
    //   124: dup
    //   125: aload_3
    //   126: aconst_null
    //   127: invokespecial <init> : (Lcom/facebook/react/bridge/Callback;Lcom/facebook/react/uimanager/NativeViewHierarchyManager$1;)V
    //   130: astore_2
    //   131: aload #4
    //   133: aload_2
    //   134: invokevirtual setOnMenuItemClickListener : (Landroid/widget/PopupMenu$OnMenuItemClickListener;)V
    //   137: aload #4
    //   139: aload_2
    //   140: invokevirtual setOnDismissListener : (Landroid/widget/PopupMenu$OnDismissListener;)V
    //   143: aload #4
    //   145: invokevirtual show : ()V
    //   148: aload_0
    //   149: monitorexit
    //   150: return
    //   151: astore_2
    //   152: aload_0
    //   153: monitorexit
    //   154: goto -> 159
    //   157: aload_2
    //   158: athrow
    //   159: goto -> 157
    // Exception table:
    //   from	to	target	type
    //   2	18	151	finally
    //   23	58	151	finally
    //   61	84	151	finally
    //   86	114	151	finally
    //   121	148	151	finally
  }
  
  void startAnimationForNativeView(int paramInt, Animation paramAnimation, Callback paramCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mTagsToViews : Landroid/util/SparseArray;
    //   9: iload_1
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast android/view/View
    //   16: astore #5
    //   18: aload_2
    //   19: invokevirtual getAnimationID : ()I
    //   22: istore #4
    //   24: aload #5
    //   26: ifnull -> 53
    //   29: aload_2
    //   30: new com/facebook/react/uimanager/NativeViewHierarchyManager$2
    //   33: dup
    //   34: aload_0
    //   35: iload #4
    //   37: aload_3
    //   38: invokespecial <init> : (Lcom/facebook/react/uimanager/NativeViewHierarchyManager;ILcom/facebook/react/bridge/Callback;)V
    //   41: invokevirtual setAnimationListener : (Lcom/facebook/react/animation/AnimationListener;)V
    //   44: aload_2
    //   45: aload #5
    //   47: invokevirtual start : (Landroid/view/View;)V
    //   50: aload_0
    //   51: monitorexit
    //   52: return
    //   53: new java/lang/StringBuilder
    //   56: dup
    //   57: ldc_w 'View with tag '
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: astore_2
    //   64: aload_2
    //   65: iload_1
    //   66: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: aload_2
    //   71: ldc_w ' not found'
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: new com/facebook/react/uimanager/IllegalViewOperationException
    //   81: dup
    //   82: aload_2
    //   83: invokevirtual toString : ()Ljava/lang/String;
    //   86: invokespecial <init> : (Ljava/lang/String;)V
    //   89: athrow
    //   90: astore_2
    //   91: aload_0
    //   92: monitorexit
    //   93: aload_2
    //   94: athrow
    // Exception table:
    //   from	to	target	type
    //   2	24	90	finally
    //   29	50	90	finally
    //   53	90	90	finally
  }
  
  public void updateLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: iload_2
    //   7: invokevirtual resolveView : (I)Landroid/view/View;
    //   10: astore #7
    //   12: aload #7
    //   14: iload #5
    //   16: ldc_w 1073741824
    //   19: invokestatic makeMeasureSpec : (II)I
    //   22: iload #6
    //   24: ldc_w 1073741824
    //   27: invokestatic makeMeasureSpec : (II)I
    //   30: invokevirtual measure : (II)V
    //   33: aload #7
    //   35: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   38: astore #8
    //   40: aload #8
    //   42: instanceof com/facebook/react/uimanager/RootView
    //   45: ifeq -> 55
    //   48: aload #8
    //   50: invokeinterface requestLayout : ()V
    //   55: aload_0
    //   56: getfield mRootTags : Landroid/util/SparseBooleanArray;
    //   59: iload_1
    //   60: invokevirtual get : (I)Z
    //   63: ifne -> 164
    //   66: aload_0
    //   67: getfield mTagsToViewManagers : Landroid/util/SparseArray;
    //   70: iload_1
    //   71: invokevirtual get : (I)Ljava/lang/Object;
    //   74: checkcast com/facebook/react/uimanager/ViewManager
    //   77: astore #8
    //   79: aload #8
    //   81: instanceof com/facebook/react/uimanager/ViewGroupManager
    //   84: ifeq -> 123
    //   87: aload #8
    //   89: checkcast com/facebook/react/uimanager/ViewGroupManager
    //   92: astore #8
    //   94: aload #8
    //   96: ifnull -> 177
    //   99: aload #8
    //   101: invokevirtual needsCustomLayoutForChildren : ()Z
    //   104: ifne -> 177
    //   107: aload_0
    //   108: aload #7
    //   110: iload_3
    //   111: iload #4
    //   113: iload #5
    //   115: iload #6
    //   117: invokespecial updateLayout : (Landroid/view/View;IIII)V
    //   120: goto -> 177
    //   123: new java/lang/StringBuilder
    //   126: dup
    //   127: ldc_w 'Trying to use view with tag '
    //   130: invokespecial <init> : (Ljava/lang/String;)V
    //   133: astore #7
    //   135: aload #7
    //   137: iload_2
    //   138: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload #7
    //   144: ldc_w ' as a parent, but its Manager doesn't extends ViewGroupManager'
    //   147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: new com/facebook/react/uimanager/IllegalViewOperationException
    //   154: dup
    //   155: aload #7
    //   157: invokevirtual toString : ()Ljava/lang/String;
    //   160: invokespecial <init> : (Ljava/lang/String;)V
    //   163: athrow
    //   164: aload_0
    //   165: aload #7
    //   167: iload_3
    //   168: iload #4
    //   170: iload #5
    //   172: iload #6
    //   174: invokespecial updateLayout : (Landroid/view/View;IIII)V
    //   177: lconst_0
    //   178: invokestatic a : (J)V
    //   181: aload_0
    //   182: monitorexit
    //   183: return
    //   184: astore #7
    //   186: lconst_0
    //   187: invokestatic a : (J)V
    //   190: aload #7
    //   192: athrow
    //   193: astore #7
    //   195: aload_0
    //   196: monitorexit
    //   197: aload #7
    //   199: athrow
    // Exception table:
    //   from	to	target	type
    //   2	5	193	finally
    //   5	55	184	finally
    //   55	94	184	finally
    //   99	120	184	finally
    //   123	164	184	finally
    //   164	177	184	finally
    //   177	181	193	finally
    //   186	193	193	finally
  }
  
  public void updateProperties(int paramInt, ReactStylesDiffMap paramReactStylesDiffMap) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: iload_1
    //   7: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   10: aload_0
    //   11: iload_1
    //   12: invokevirtual resolveView : (I)Landroid/view/View;
    //   15: aload_2
    //   16: invokevirtual updateProperties : (Landroid/view/View;Lcom/facebook/react/uimanager/ReactStylesDiffMap;)V
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: aload_0
    //   23: monitorexit
    //   24: return
    //   25: astore_2
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_2
    //   29: athrow
    //   30: astore_2
    //   31: goto -> 22
    // Exception table:
    //   from	to	target	type
    //   2	5	25	finally
    //   5	19	30	com/facebook/react/uimanager/IllegalViewOperationException
    //   5	19	25	finally
  }
  
  public void updateViewExtraData(int paramInt, Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: iload_1
    //   7: invokevirtual resolveViewManager : (I)Lcom/facebook/react/uimanager/ViewManager;
    //   10: aload_0
    //   11: iload_1
    //   12: invokevirtual resolveView : (I)Landroid/view/View;
    //   15: aload_2
    //   16: invokevirtual updateExtraData : (Landroid/view/View;Ljava/lang/Object;)V
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: aload_0
    //   23: monitorexit
    //   24: return
    //   25: astore_2
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_2
    //   29: athrow
    //   30: astore_2
    //   31: goto -> 22
    // Exception table:
    //   from	to	target	type
    //   2	5	25	finally
    //   5	19	30	com/facebook/react/uimanager/IllegalViewOperationException
    //   5	19	25	finally
  }
  
  static class PopupMenuCallbackHandler implements PopupMenu.OnDismissListener, PopupMenu.OnMenuItemClickListener {
    boolean mConsumed;
    
    final Callback mSuccess;
    
    private PopupMenuCallbackHandler(Callback param1Callback) {
      this.mSuccess = param1Callback;
    }
    
    public void onDismiss(PopupMenu param1PopupMenu) {
      if (!this.mConsumed) {
        this.mSuccess.invoke(new Object[] { "dismissed" });
        this.mConsumed = true;
      } 
    }
    
    public boolean onMenuItemClick(MenuItem param1MenuItem) {
      boolean bool1 = this.mConsumed;
      boolean bool = false;
      if (!bool1) {
        Callback callback = this.mSuccess;
        int i = param1MenuItem.getOrder();
        bool = true;
        callback.invoke(new Object[] { "itemSelected", Integer.valueOf(i) });
        this.mConsumed = true;
      } 
      return bool;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\NativeViewHierarchyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */