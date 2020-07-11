package com.facebook.react.flat;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.NoSuchNativeViewException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.TouchTargetHelper;
import com.facebook.react.uimanager.UIViewOperationQueue;
import java.util.ArrayList;

final class FlatUIViewOperationQueue extends UIViewOperationQueue {
  public static final int[] MEASURE_BUFFER = new int[4];
  
  public final FlatNativeViewHierarchyManager mNativeViewHierarchyManager;
  
  private final ProcessLayoutRequests mProcessLayoutRequests = new ProcessLayoutRequests();
  
  public FlatUIViewOperationQueue(ReactApplicationContext paramReactApplicationContext, FlatNativeViewHierarchyManager paramFlatNativeViewHierarchyManager, int paramInt) {
    super(paramReactApplicationContext, paramFlatNativeViewHierarchyManager, paramInt);
    this.mNativeViewHierarchyManager = paramFlatNativeViewHierarchyManager;
  }
  
  public final UpdateViewBounds createUpdateViewBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    return new UpdateViewBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public final ViewManagerCommand createViewManagerCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    return new ViewManagerCommand(paramInt1, paramInt2, paramReadableArray);
  }
  
  public final DetachAllChildrenFromViews enqueueDetachAllChildrenFromViews() {
    DetachAllChildrenFromViews detachAllChildrenFromViews = new DetachAllChildrenFromViews();
    enqueueUIOperation(detachAllChildrenFromViews);
    return detachAllChildrenFromViews;
  }
  
  public final void enqueueDropViews(ArrayList<Integer> paramArrayList1, ArrayList<Integer> paramArrayList2) {
    enqueueUIOperation(new DropViews(paramArrayList1, paramArrayList2));
  }
  
  public final void enqueueFindTargetForTouch(int paramInt, float paramFloat1, float paramFloat2, Callback paramCallback) {
    enqueueUIOperation(new FindTargetForTouchOperation(paramInt, paramFloat1, paramFloat2, paramCallback));
  }
  
  final void enqueueFlatUIOperation(UIViewOperationQueue.UIOperation paramUIOperation) {
    enqueueUIOperation(paramUIOperation);
  }
  
  public final void enqueueMeasureVirtualView(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean, Callback paramCallback) {
    enqueueUIOperation(new MeasureVirtualView(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean, paramCallback));
  }
  
  public final void enqueueProcessLayoutRequests() {
    enqueueUIOperation(this.mProcessLayoutRequests);
  }
  
  public final void enqueueSetPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    enqueueUIOperation(new SetPadding(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5));
  }
  
  public final void enqueueUpdateClippingMountState(int paramInt, DrawCommand[] paramArrayOfDrawCommand, SparseIntArray paramSparseIntArray, float[] paramArrayOffloat1, float[] paramArrayOffloat2, AttachDetachListener[] paramArrayOfAttachDetachListener, NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat3, float[] paramArrayOffloat4, boolean paramBoolean) {
    enqueueUIOperation(new UpdateClippingMountState(paramInt, paramArrayOfDrawCommand, paramSparseIntArray, paramArrayOffloat1, paramArrayOffloat2, paramArrayOfAttachDetachListener, paramArrayOfNodeRegion, paramArrayOffloat3, paramArrayOffloat4, paramBoolean));
  }
  
  public final void enqueueUpdateMountState(int paramInt, DrawCommand[] paramArrayOfDrawCommand, AttachDetachListener[] paramArrayOfAttachDetachListener, NodeRegion[] paramArrayOfNodeRegion) {
    enqueueUIOperation(new UpdateMountState(paramInt, paramArrayOfDrawCommand, paramArrayOfAttachDetachListener, paramArrayOfNodeRegion));
  }
  
  public final void enqueueUpdateViewGroup(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    enqueueUIOperation(new UpdateViewGroup(paramInt, paramArrayOfint1, paramArrayOfint2));
  }
  
  public final class DetachAllChildrenFromViews implements UIViewOperationQueue.UIOperation {
    private int[] mViewsToDetachAllChildrenFrom;
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.detachAllChildrenFromViews(this.mViewsToDetachAllChildrenFrom);
    }
    
    public final void setViewsToDetachAllChildrenFrom(int[] param1ArrayOfint) {
      this.mViewsToDetachAllChildrenFrom = param1ArrayOfint;
    }
  }
  
  final class DropViews implements UIViewOperationQueue.UIOperation {
    private final SparseIntArray mViewsToDrop;
    
    private DropViews(ArrayList<Integer> param1ArrayList1, ArrayList<Integer> param1ArrayList2) {
      SparseIntArray sparseIntArray = new SparseIntArray();
      int j = param1ArrayList1.size();
      int i;
      for (i = 0; i < j; i++)
        sparseIntArray.put(((Integer)param1ArrayList1.get(i)).intValue(), ((Integer)param1ArrayList2.get(i)).intValue()); 
      this.mViewsToDrop = sparseIntArray;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.dropViews(this.mViewsToDrop);
    }
  }
  
  final class FindTargetForTouchOperation implements UIViewOperationQueue.UIOperation {
    private final int[] NATIVE_VIEW_BUFFER = new int[1];
    
    private final Callback mCallback;
    
    private final int mReactTag;
    
    private final float mTargetX;
    
    private final float mTargetY;
    
    private FindTargetForTouchOperation(int param1Int, float param1Float1, float param1Float2, Callback param1Callback) {
      this.mReactTag = param1Int;
      this.mTargetX = param1Float1;
      this.mTargetY = param1Float2;
      this.mCallback = param1Callback;
    }
    
    public final void execute() {
      try {
        FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
        float f2 = FlatUIViewOperationQueue.MEASURE_BUFFER[0];
        float f1 = FlatUIViewOperationQueue.MEASURE_BUFFER[1];
        View view = FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.getView(this.mReactTag);
        int i = TouchTargetHelper.findTargetTagForTouch(this.mTargetX, this.mTargetY, (ViewGroup)view, this.NATIVE_VIEW_BUFFER);
        try {
          boolean bool;
          FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.NATIVE_VIEW_BUFFER[0], FlatUIViewOperationQueue.MEASURE_BUFFER);
          NodeRegion nodeRegion2 = NodeRegion.EMPTY;
          if (this.NATIVE_VIEW_BUFFER[0] == i) {
            bool = true;
          } else {
            bool = false;
          } 
          NodeRegion nodeRegion1 = nodeRegion2;
          if (!bool) {
            View view1 = FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.getView(this.NATIVE_VIEW_BUFFER[0]);
            nodeRegion1 = nodeRegion2;
            if (view1 instanceof FlatViewGroup)
              nodeRegion1 = ((FlatViewGroup)view1).getNodeRegionForTag(this.mReactTag); 
          } 
          if (nodeRegion1 != NodeRegion.EMPTY)
            i = nodeRegion1.mTag; 
          f2 = PixelUtil.toDIPFromPixel(nodeRegion1.getLeft() + FlatUIViewOperationQueue.MEASURE_BUFFER[0] - f2);
          float f3 = PixelUtil.toDIPFromPixel(nodeRegion1.getTop() + FlatUIViewOperationQueue.MEASURE_BUFFER[1] - f1);
          if (bool) {
            f1 = FlatUIViewOperationQueue.MEASURE_BUFFER[2];
          } else {
            f1 = nodeRegion1.getRight() - nodeRegion1.getLeft();
          } 
          float f4 = PixelUtil.toDIPFromPixel(f1);
          if (bool) {
            f1 = FlatUIViewOperationQueue.MEASURE_BUFFER[3];
          } else {
            f1 = nodeRegion1.getBottom() - nodeRegion1.getTop();
          } 
          f1 = PixelUtil.toDIPFromPixel(f1);
          this.mCallback.invoke(new Object[] { Integer.valueOf(i), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f1) });
          return;
        } catch (IllegalViewOperationException illegalViewOperationException) {
          this.mCallback.invoke(new Object[0]);
        } 
        return;
      } catch (IllegalViewOperationException illegalViewOperationException) {
        this.mCallback.invoke(new Object[0]);
        return;
      } 
    }
  }
  
  final class MeasureVirtualView implements UIViewOperationQueue.UIOperation {
    private final Callback mCallback;
    
    private final int mReactTag;
    
    private final boolean mRelativeToWindow;
    
    private final float mScaledHeight;
    
    private final float mScaledWidth;
    
    private final float mScaledX;
    
    private final float mScaledY;
    
    private MeasureVirtualView(int param1Int, float param1Float1, float param1Float2, float param1Float3, float param1Float4, boolean param1Boolean, Callback param1Callback) {
      this.mReactTag = param1Int;
      this.mScaledX = param1Float1;
      this.mScaledY = param1Float2;
      this.mScaledWidth = param1Float3;
      this.mScaledHeight = param1Float4;
      this.mCallback = param1Callback;
      this.mRelativeToWindow = param1Boolean;
    }
    
    public final void execute() {
      try {
        if (this.mRelativeToWindow) {
          FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measureInWindow(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
        } else {
          FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, FlatUIViewOperationQueue.MEASURE_BUFFER);
        } 
        float f2 = FlatUIViewOperationQueue.MEASURE_BUFFER[0];
        float f4 = FlatUIViewOperationQueue.MEASURE_BUFFER[1];
        float f3 = FlatUIViewOperationQueue.MEASURE_BUFFER[2];
        float f1 = FlatUIViewOperationQueue.MEASURE_BUFFER[3];
        f2 = PixelUtil.toDIPFromPixel(this.mScaledX * f3 + f2);
        f4 = PixelUtil.toDIPFromPixel(this.mScaledY * f1 + f4);
        f3 = PixelUtil.toDIPFromPixel(this.mScaledWidth * f3);
        f1 = PixelUtil.toDIPFromPixel(this.mScaledHeight * f1);
        if (this.mRelativeToWindow) {
          this.mCallback.invoke(new Object[] { Float.valueOf(f2), Float.valueOf(f4), Float.valueOf(f3), Float.valueOf(f1) });
          return;
        } 
        this.mCallback.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Float.valueOf(f3), Float.valueOf(f1), Float.valueOf(f2), Float.valueOf(f4) });
        return;
      } catch (NoSuchNativeViewException noSuchNativeViewException) {
        this.mCallback.invoke(new Object[0]);
        return;
      } 
    }
  }
  
  final class ProcessLayoutRequests implements UIViewOperationQueue.UIOperation {
    private ProcessLayoutRequests() {}
    
    public final void execute() {
      FlatViewGroup.processLayoutRequests();
    }
  }
  
  final class SetPadding implements UIViewOperationQueue.UIOperation {
    private final int mPaddingBottom;
    
    private final int mPaddingLeft;
    
    private final int mPaddingRight;
    
    private final int mPaddingTop;
    
    private final int mReactTag;
    
    private SetPadding(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.mReactTag = param1Int1;
      this.mPaddingLeft = param1Int2;
      this.mPaddingTop = param1Int3;
      this.mPaddingRight = param1Int4;
      this.mPaddingBottom = param1Int5;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.setPadding(this.mReactTag, this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
    }
  }
  
  final class UpdateClippingMountState implements UIViewOperationQueue.UIOperation {
    private final AttachDetachListener[] mAttachDetachListeners;
    
    private final float[] mCommandMaxBot;
    
    private final float[] mCommandMinTop;
    
    private final DrawCommand[] mDrawCommands;
    
    private final SparseIntArray mDrawViewIndexMap;
    
    private final NodeRegion[] mNodeRegions;
    
    private final int mReactTag;
    
    private final float[] mRegionMaxBot;
    
    private final float[] mRegionMinTop;
    
    private final boolean mWillMountViews;
    
    private UpdateClippingMountState(int param1Int, DrawCommand[] param1ArrayOfDrawCommand, SparseIntArray param1SparseIntArray, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, AttachDetachListener[] param1ArrayOfAttachDetachListener, NodeRegion[] param1ArrayOfNodeRegion, float[] param1ArrayOffloat3, float[] param1ArrayOffloat4, boolean param1Boolean) {
      this.mReactTag = param1Int;
      this.mDrawCommands = param1ArrayOfDrawCommand;
      this.mDrawViewIndexMap = param1SparseIntArray;
      this.mCommandMaxBot = param1ArrayOffloat1;
      this.mCommandMinTop = param1ArrayOffloat2;
      this.mAttachDetachListeners = param1ArrayOfAttachDetachListener;
      this.mNodeRegions = param1ArrayOfNodeRegion;
      this.mRegionMaxBot = param1ArrayOffloat3;
      this.mRegionMinTop = param1ArrayOffloat4;
      this.mWillMountViews = param1Boolean;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateClippingMountState(this.mReactTag, this.mDrawCommands, this.mDrawViewIndexMap, this.mCommandMaxBot, this.mCommandMinTop, this.mAttachDetachListeners, this.mNodeRegions, this.mRegionMaxBot, this.mRegionMinTop, this.mWillMountViews);
    }
  }
  
  final class UpdateMountState implements UIViewOperationQueue.UIOperation {
    private final AttachDetachListener[] mAttachDetachListeners;
    
    private final DrawCommand[] mDrawCommands;
    
    private final NodeRegion[] mNodeRegions;
    
    private final int mReactTag;
    
    private UpdateMountState(int param1Int, DrawCommand[] param1ArrayOfDrawCommand, AttachDetachListener[] param1ArrayOfAttachDetachListener, NodeRegion[] param1ArrayOfNodeRegion) {
      this.mReactTag = param1Int;
      this.mDrawCommands = param1ArrayOfDrawCommand;
      this.mAttachDetachListeners = param1ArrayOfAttachDetachListener;
      this.mNodeRegions = param1ArrayOfNodeRegion;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateMountState(this.mReactTag, this.mDrawCommands, this.mAttachDetachListeners, this.mNodeRegions);
    }
  }
  
  public final class UpdateViewBounds implements UIViewOperationQueue.UIOperation {
    private final int mBottom;
    
    private final int mLeft;
    
    private final int mReactTag;
    
    private final int mRight;
    
    private final int mTop;
    
    private UpdateViewBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.mReactTag = param1Int1;
      this.mLeft = param1Int2;
      this.mTop = param1Int3;
      this.mRight = param1Int4;
      this.mBottom = param1Int5;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewBounds(this.mReactTag, this.mLeft, this.mTop, this.mRight, this.mBottom);
    }
  }
  
  final class UpdateViewGroup implements UIViewOperationQueue.UIOperation {
    private final int mReactTag;
    
    private final int[] mViewsToAdd;
    
    private final int[] mViewsToDetach;
    
    private UpdateViewGroup(int param1Int, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
      this.mReactTag = param1Int;
      this.mViewsToAdd = param1ArrayOfint1;
      this.mViewsToDetach = param1ArrayOfint2;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewGroup(this.mReactTag, this.mViewsToAdd, this.mViewsToDetach);
    }
  }
  
  public final class ViewManagerCommand implements UIViewOperationQueue.UIOperation {
    private final ReadableArray mArgs;
    
    private final int mCommand;
    
    private final int mReactTag;
    
    public ViewManagerCommand(int param1Int1, int param1Int2, ReadableArray param1ReadableArray) {
      this.mReactTag = param1Int1;
      this.mCommand = param1Int2;
      this.mArgs = param1ReadableArray;
    }
    
    public final void execute() {
      FlatUIViewOperationQueue.this.mNativeViewHierarchyManager.dispatchCommand(this.mReactTag, this.mCommand, this.mArgs);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatUIViewOperationQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */