package com.facebook.react.flat;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import java.util.ArrayList;

final class FlatNativeViewHierarchyManager extends NativeViewHierarchyManager implements ViewResolver {
  FlatNativeViewHierarchyManager(ViewManagerRegistry paramViewManagerRegistry) {
    super(paramViewManagerRegistry, new FlatRootViewManager());
  }
  
  public final void addRootView(int paramInt, SizeMonitoringFrameLayout paramSizeMonitoringFrameLayout, ThemedReactContext paramThemedReactContext) {
    FlatViewGroup flatViewGroup = new FlatViewGroup((Context)paramThemedReactContext);
    paramSizeMonitoringFrameLayout.addView((View)flatViewGroup);
    paramSizeMonitoringFrameLayout.setId(paramInt);
    addRootViewGroup(paramInt, flatViewGroup, paramThemedReactContext);
  }
  
  final void detachAllChildrenFromViews(int[] paramArrayOfint) {
    int j = paramArrayOfint.length;
    for (int i = 0; i < j; i++) {
      int k = paramArrayOfint[i];
      View view = resolveView(k);
      if (view instanceof FlatViewGroup) {
        ((FlatViewGroup)view).detachAllViewsFromParent();
      } else {
        ViewGroup viewGroup = (ViewGroup)view;
        ((ViewGroupManager)resolveViewManager(k)).removeAllViews(viewGroup);
      } 
    } 
  }
  
  public final void dropView(View paramView) {
    super.dropView(paramView);
    if (paramView instanceof FlatViewGroup) {
      FlatViewGroup flatViewGroup = (FlatViewGroup)paramView;
      if (flatViewGroup.getRemoveClippedSubviews()) {
        SparseArray<View> sparseArray = flatViewGroup.getDetachedViews();
        int i = 0;
        int j = sparseArray.size();
        while (true) {
          if (i < j) {
            View view = (View)sparseArray.valueAt(i);
            try {
              dropView(view);
            } catch (Exception exception) {}
            flatViewGroup.removeDetachedView(view);
            i++;
            continue;
          } 
          return;
        } 
      } 
    } 
  }
  
  final void dropViews(SparseIntArray paramSparseIntArray) {
    int j = paramSparseIntArray.size();
    int i = 0;
    while (true) {
      if (i < j) {
        int k = paramSparseIntArray.keyAt(i);
        View view2 = null;
        View view1 = null;
        if (k > 0) {
          view1 = view2;
          try {
            view2 = resolveView(k);
            view1 = view2;
            dropView(view2);
            view1 = view2;
          } catch (Exception exception) {}
        } else {
          removeRootView(-k);
        } 
        k = paramSparseIntArray.valueAt(i);
        if (k > 0 && view1 != null && view1.getParent() == null) {
          view2 = resolveView(k);
          if (view2 instanceof FlatViewGroup)
            ((FlatViewGroup)view2).onViewDropped(view1); 
        } 
        i++;
        continue;
      } 
      return;
    } 
  }
  
  public final View getView(int paramInt) {
    return resolveView(paramInt);
  }
  
  final void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    resolveView(paramInt1).setPadding(paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  final void updateClippingMountState(int paramInt, DrawCommand[] paramArrayOfDrawCommand, SparseIntArray paramSparseIntArray, float[] paramArrayOffloat1, float[] paramArrayOffloat2, AttachDetachListener[] paramArrayOfAttachDetachListener, NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat3, float[] paramArrayOffloat4, boolean paramBoolean) {
    FlatViewGroup flatViewGroup = (FlatViewGroup)resolveView(paramInt);
    if (paramArrayOfDrawCommand != null)
      flatViewGroup.mountClippingDrawCommands(paramArrayOfDrawCommand, paramSparseIntArray, paramArrayOffloat1, paramArrayOffloat2, paramBoolean); 
    if (paramArrayOfAttachDetachListener != null)
      flatViewGroup.mountAttachDetachListeners(paramArrayOfAttachDetachListener); 
    if (paramArrayOfNodeRegion != null)
      flatViewGroup.mountClippingNodeRegions(paramArrayOfNodeRegion, paramArrayOffloat3, paramArrayOffloat4); 
  }
  
  final void updateMountState(int paramInt, DrawCommand[] paramArrayOfDrawCommand, AttachDetachListener[] paramArrayOfAttachDetachListener, NodeRegion[] paramArrayOfNodeRegion) {
    FlatViewGroup flatViewGroup = (FlatViewGroup)resolveView(paramInt);
    if (paramArrayOfDrawCommand != null)
      flatViewGroup.mountDrawCommands(paramArrayOfDrawCommand); 
    if (paramArrayOfAttachDetachListener != null)
      flatViewGroup.mountAttachDetachListeners(paramArrayOfAttachDetachListener); 
    if (paramArrayOfNodeRegion != null)
      flatViewGroup.mountNodeRegions(paramArrayOfNodeRegion); 
  }
  
  final void updateViewBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    View view = resolveView(paramInt1);
    paramInt1 = paramInt4 - paramInt2;
    int i = paramInt5 - paramInt3;
    if (view.getWidth() != paramInt1 || view.getHeight() != i) {
      view.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
      view.layout(paramInt2, paramInt3, paramInt4, paramInt5);
      return;
    } 
    view.offsetLeftAndRight(paramInt2 - view.getLeft());
    view.offsetTopAndBottom(paramInt3 - view.getTop());
  }
  
  final void updateViewGroup(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    View view = resolveView(paramInt);
    if (view instanceof FlatViewGroup) {
      ((FlatViewGroup)view).mountViews(this, paramArrayOfint1, paramArrayOfint2);
      return;
    } 
    ViewGroup viewGroup = (ViewGroup)view;
    ViewGroupManager viewGroupManager = (ViewGroupManager)resolveViewManager(paramInt);
    ArrayList<View> arrayList = new ArrayList(paramArrayOfint1.length);
    int i = paramArrayOfint1.length;
    for (paramInt = 0; paramInt < i; paramInt++)
      arrayList.add(resolveView(Math.abs(paramArrayOfint1[paramInt]))); 
    viewGroupManager.addViews(viewGroup, arrayList);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatNativeViewHierarchyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */