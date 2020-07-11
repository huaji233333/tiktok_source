package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import java.util.ArrayList;
import java.util.Arrays;

abstract class ClippingDrawCommandManager extends DrawCommandManager {
  private static final String TAG = ClippingDrawCommandManager.class.getSimpleName();
  
  private final SparseArray<View> mClippedSubviews = new SparseArray();
  
  protected final Rect mClippingRect = new Rect();
  
  private final ArrayList<ReactClippingViewGroup> mClippingViewGroups = new ArrayList<ReactClippingViewGroup>();
  
  protected float[] mCommandMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
  
  protected float[] mCommandMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
  
  private DrawCommand[] mDrawCommands = DrawCommand.EMPTY_ARRAY;
  
  private SparseIntArray mDrawViewIndexMap = StateBuilder.EMPTY_SPARSE_INT;
  
  private final FlatViewGroup mFlatViewGroup;
  
  private NodeRegion[] mNodeRegions = NodeRegion.EMPTY_ARRAY;
  
  protected float[] mRegionMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
  
  protected float[] mRegionMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
  
  private int mStart;
  
  private int mStop;
  
  private final ArrayList<View> mViewsToKeep = new ArrayList<View>();
  
  private final SparseArray<View> mViewsToRemove = new SparseArray();
  
  ClippingDrawCommandManager(FlatViewGroup paramFlatViewGroup, DrawCommand[] paramArrayOfDrawCommand) {
    this.mFlatViewGroup = paramFlatViewGroup;
    initialSetup(paramArrayOfDrawCommand);
  }
  
  private static boolean animating(View paramView) {
    Animation animation = paramView.getAnimation();
    return (animation != null && !animation.hasEnded());
  }
  
  private void clip(int paramInt, View paramView) {
    this.mClippedSubviews.put(paramInt, paramView);
  }
  
  private void initialSetup(DrawCommand[] paramArrayOfDrawCommand) {
    mountDrawCommands(paramArrayOfDrawCommand, this.mDrawViewIndexMap, this.mCommandMaxBottom, this.mCommandMinTop, true);
    updateClippingRect();
  }
  
  private boolean isClipped(int paramInt) {
    return (this.mClippedSubviews.get(paramInt) != null);
  }
  
  private boolean isNotClipped(int paramInt) {
    return (this.mClippedSubviews.get(paramInt) == null);
  }
  
  private void unclip(int paramInt) {
    this.mClippedSubviews.remove(paramInt);
  }
  
  private void updateClippingRecursively() {
    int j = this.mClippingViewGroups.size();
    for (int i = 0; i < j; i++) {
      ReactClippingViewGroup reactClippingViewGroup = this.mClippingViewGroups.get(i);
      if (isNotClipped(((View)reactClippingViewGroup).getId()))
        reactClippingViewGroup.updateClippingRect(); 
    } 
  }
  
  private void updateClippingToCurrentRect() {
    int k;
    int j = this.mFlatViewGroup.getChildCount();
    int m = 0;
    int i;
    for (i = 0; i < j; i++) {
      View view = this.mFlatViewGroup.getChildAt(i);
      if (withinBounds(this.mDrawViewIndexMap.get(view.getId())) || animating(view)) {
        this.mViewsToKeep.add(view);
      } else {
        this.mViewsToRemove.append(i, view);
        clip(view.getId(), view);
      } 
    } 
    j = this.mViewsToRemove.size();
    if (j > 2) {
      k = 1;
    } else {
      k = 0;
    } 
    i = j;
    if (k) {
      this.mFlatViewGroup.detachAllViewsFromParent();
      for (i = 0; i < j; i++)
        this.mFlatViewGroup.removeDetachedView((View)this.mViewsToRemove.valueAt(i)); 
    } else {
      while (true) {
        j = i - 1;
        if (i > 0) {
          this.mFlatViewGroup.removeViewsInLayout(this.mViewsToRemove.keyAt(j), 1);
          i = j;
          continue;
        } 
        break;
      } 
    } 
    this.mViewsToRemove.clear();
    j = this.mStart;
    int n = this.mViewsToKeep.size();
    i = 0;
    while (m < n) {
      View view = this.mViewsToKeep.get(m);
      int i3 = this.mDrawViewIndexMap.get(view.getId());
      int i1 = j;
      int i2 = i;
      if (j <= i3) {
        while (j != i3) {
          DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
          i1 = i;
          if (arrayOfDrawCommand[j] instanceof DrawView) {
            DrawView drawView = (DrawView)arrayOfDrawCommand[j];
            this.mFlatViewGroup.addViewInLayout((View)a.a(this.mClippedSubviews.get(drawView.reactTag)), i);
            unclip(drawView.reactTag);
            i1 = i + 1;
          } 
          j++;
          i = i1;
        } 
        i1 = j + 1;
        i2 = i;
      } 
      if (k)
        this.mFlatViewGroup.attachViewToParent(view, i2); 
      i = i2 + 1;
      m++;
      j = i1;
    } 
    this.mViewsToKeep.clear();
    while (j < this.mStop) {
      DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
      k = i;
      if (arrayOfDrawCommand[j] instanceof DrawView) {
        DrawView drawView = (DrawView)arrayOfDrawCommand[j];
        this.mFlatViewGroup.addViewInLayout((View)a.a(this.mClippedSubviews.get(drawView.reactTag)), i);
        unclip(drawView.reactTag);
        k = i + 1;
      } 
      j++;
      i = k;
    } 
  }
  
  private boolean withinBounds(int paramInt) {
    return (this.mStart <= paramInt && paramInt < this.mStop);
  }
  
  public NodeRegion anyNodeRegionWithinBounds(float paramFloat1, float paramFloat2) {
    int i = regionStopIndex(paramFloat1, paramFloat2);
    while (true) {
      int j = i - 1;
      if (i > 0) {
        NodeRegion nodeRegion = this.mNodeRegions[j];
        if (!regionAboveTouch(j, paramFloat1, paramFloat2)) {
          if (nodeRegion.withinBounds(paramFloat1, paramFloat2))
            return nodeRegion; 
          i = j;
          continue;
        } 
      } 
      break;
    } 
    return null;
  }
  
  abstract int commandStartIndex();
  
  abstract int commandStopIndex(int paramInt);
  
  void debugDraw(Canvas paramCanvas) {
    for (DrawCommand drawCommand : this.mDrawCommands) {
      if (!(drawCommand instanceof DrawView) || isNotClipped(((DrawView)drawCommand).reactTag))
        drawCommand.debugDraw(this.mFlatViewGroup, paramCanvas); 
    } 
  }
  
  public void draw(Canvas paramCanvas) {
    int j;
    int i = this.mStart;
    int m = this.mFlatViewGroup.getChildCount();
    int k = 0;
    while (true) {
      j = i;
      if (k < m) {
        int n = this.mDrawViewIndexMap.get(this.mFlatViewGroup.getChildAt(k).getId());
        if (this.mStop < n) {
          while (true) {
            j = i;
            if (i < this.mStop) {
              this.mDrawCommands[i].draw(this.mFlatViewGroup, paramCanvas);
              i++;
              continue;
            } 
            break;
          } 
        } else {
          j = i;
          if (i <= n) {
            while (i < n) {
              this.mDrawCommands[i].draw(this.mFlatViewGroup, paramCanvas);
              i++;
            } 
            j = i + 1;
          } 
        } 
        this.mDrawCommands[n].draw(this.mFlatViewGroup, paramCanvas);
        k++;
        i = j;
        continue;
      } 
      break;
    } 
    while (j < this.mStop) {
      String str;
      DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
      i = j + 1;
      DrawCommand drawCommand = arrayOfDrawCommand[j];
      if (drawCommand instanceof DrawView) {
        str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Unexpected DrawView command at index ");
        stringBuilder.append(i - 1);
        stringBuilder.append(" with mStop=");
        stringBuilder.append(this.mStop);
        stringBuilder.append(". ");
        stringBuilder.append(Arrays.toString((Object[])this.mDrawCommands));
        a.b(str, stringBuilder.toString());
      } else {
        str.draw(this.mFlatViewGroup, paramCanvas);
      } 
      j = i;
    } 
  }
  
  public void getClippingRect(Rect paramRect) {
    paramRect.set(this.mClippingRect);
  }
  
  public SparseArray<View> getDetachedViews() {
    return this.mClippedSubviews;
  }
  
  public void mountDrawCommands(DrawCommand[] paramArrayOfDrawCommand, SparseIntArray paramSparseIntArray, float[] paramArrayOffloat1, float[] paramArrayOffloat2, boolean paramBoolean) {
    this.mDrawCommands = paramArrayOfDrawCommand;
    this.mCommandMaxBottom = paramArrayOffloat1;
    this.mCommandMinTop = paramArrayOffloat2;
    this.mDrawViewIndexMap = paramSparseIntArray;
    if (this.mClippingRect.bottom != this.mClippingRect.top) {
      this.mStart = commandStartIndex();
      this.mStop = commandStopIndex(this.mStart);
      if (!paramBoolean)
        updateClippingToCurrentRect(); 
    } 
  }
  
  public void mountNodeRegions(NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    this.mNodeRegions = paramArrayOfNodeRegion;
    this.mRegionMaxBottom = paramArrayOffloat1;
    this.mRegionMinTop = paramArrayOffloat2;
  }
  
  public void mountViews(ViewResolver paramViewResolver, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    this.mClippingViewGroups.clear();
    int k = paramArrayOfint1.length;
    boolean bool = false;
    int i;
    for (i = 0; i < k; i++) {
      boolean bool1;
      int n = paramArrayOfint1[i];
      if (n > 0) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      int m = n;
      if (!bool1)
        m = -n; 
      m = this.mDrawViewIndexMap.get(m);
      DrawView drawView = (DrawView)this.mDrawCommands[m];
      View view = paramViewResolver.getView(drawView.reactTag);
      ensureViewHasNoParent(view);
      if (view instanceof ReactClippingViewGroup) {
        ReactClippingViewGroup reactClippingViewGroup = (ReactClippingViewGroup)view;
        if (reactClippingViewGroup.getRemoveClippedSubviews())
          this.mClippingViewGroups.add(reactClippingViewGroup); 
      } 
      if (bool1) {
        drawView.mWasMounted = true;
        if (animating(view) || withinBounds(m)) {
          this.mFlatViewGroup.addViewInLayout(view);
        } else {
          clip(drawView.reactTag, view);
        } 
      } else if (drawView.mWasMounted) {
        if (isNotClipped(drawView.reactTag))
          this.mFlatViewGroup.attachViewToParent(view); 
      } else {
        drawView.mWasMounted = true;
        if (animating(view) || withinBounds(m)) {
          if (isClipped(drawView.reactTag)) {
            this.mFlatViewGroup.addViewInLayout(view);
            unclip(drawView.reactTag);
          } else {
            this.mFlatViewGroup.attachViewToParent(view);
          } 
        } else if (isNotClipped(drawView.reactTag)) {
          this.mFlatViewGroup.removeDetachedView(view);
          clip(drawView.reactTag, view);
        } 
      } 
    } 
    int j = paramArrayOfint2.length;
    i = bool;
    while (i < j) {
      int m = paramArrayOfint2[i];
      View view = paramViewResolver.getView(m);
      if (view.getParent() == null) {
        this.mFlatViewGroup.removeDetachedView(view);
        unclip(m);
        i++;
        continue;
      } 
      throw new RuntimeException("Trying to remove view not owned by FlatViewGroup");
    } 
  }
  
  void onClippedViewDropped(View paramView) {
    unclip(paramView.getId());
    this.mFlatViewGroup.removeDetachedView(paramView);
  }
  
  abstract boolean regionAboveTouch(int paramInt, float paramFloat1, float paramFloat2);
  
  abstract int regionStopIndex(float paramFloat1, float paramFloat2);
  
  public boolean updateClippingRect() {
    ReactClippingViewGroupHelper.calculateClippingRect((View)this.mFlatViewGroup, this.mClippingRect);
    if (this.mFlatViewGroup.getParent() != null) {
      if (this.mClippingRect.top == this.mClippingRect.bottom)
        return false; 
      int i = commandStartIndex();
      int j = commandStopIndex(i);
      if (this.mStart <= i && j <= this.mStop) {
        updateClippingRecursively();
        return false;
      } 
      this.mStart = i;
      this.mStop = j;
      updateClippingToCurrentRect();
      updateClippingRecursively();
      return true;
    } 
    return false;
  }
  
  public NodeRegion virtualNodeRegionWithinBounds(float paramFloat1, float paramFloat2) {
    int i = regionStopIndex(paramFloat1, paramFloat2);
    while (true) {
      int j = i - 1;
      if (i > 0) {
        NodeRegion nodeRegion = this.mNodeRegions[j];
        if (nodeRegion.mIsVirtual)
          if (!regionAboveTouch(j, paramFloat1, paramFloat2)) {
            if (nodeRegion.withinBounds(paramFloat1, paramFloat2))
              return nodeRegion; 
          } else {
            break;
          }  
        i = j;
        continue;
      } 
      break;
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\ClippingDrawCommandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */