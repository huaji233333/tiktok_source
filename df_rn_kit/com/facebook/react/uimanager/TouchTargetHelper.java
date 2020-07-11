package com.facebook.react.uimanager;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.touch.ReactHitSlopView;

public class TouchTargetHelper {
  private static final float[] mEventCoords = new float[2];
  
  private static final Matrix mInverseMatrix;
  
  private static final float[] mMatrixTransformCoords;
  
  private static final PointF mTempPoint = new PointF();
  
  static {
    mMatrixTransformCoords = new float[2];
    mInverseMatrix = new Matrix();
  }
  
  private static View findClosestReactAncestor(View paramView) {
    while (paramView != null && paramView.getId() <= 0)
      paramView = (View)paramView.getParent(); 
    return paramView;
  }
  
  public static int findTargetTagAndCoordinatesForTouch(float paramFloat1, float paramFloat2, ViewGroup paramViewGroup, float[] paramArrayOffloat, int[] paramArrayOfint) {
    UiThreadUtil.assertOnUiThread();
    int j = paramViewGroup.getId();
    paramArrayOffloat[0] = paramFloat1;
    paramArrayOffloat[1] = paramFloat2;
    View view = findTouchTargetView(paramArrayOffloat, paramViewGroup);
    int i = j;
    if (view != null) {
      view = findClosestReactAncestor(view);
      i = j;
      if (view != null) {
        if (paramArrayOfint != null)
          paramArrayOfint[0] = view.getId(); 
        i = getTouchTargetForView(view, paramArrayOffloat[0], paramArrayOffloat[1]);
      } 
    } 
    return i;
  }
  
  public static int findTargetTagForTouch(float paramFloat1, float paramFloat2, ViewGroup paramViewGroup) {
    return findTargetTagAndCoordinatesForTouch(paramFloat1, paramFloat2, paramViewGroup, mEventCoords, null);
  }
  
  public static int findTargetTagForTouch(float paramFloat1, float paramFloat2, ViewGroup paramViewGroup, int[] paramArrayOfint) {
    return findTargetTagAndCoordinatesForTouch(paramFloat1, paramFloat2, paramViewGroup, mEventCoords, paramArrayOfint);
  }
  
  private static View findTouchTargetView(float[] paramArrayOffloat, ViewGroup paramViewGroup) {
    ReactZIndexedViewGroup reactZIndexedViewGroup;
    int i = paramViewGroup.getChildCount();
    if (paramViewGroup instanceof ReactZIndexedViewGroup) {
      reactZIndexedViewGroup = (ReactZIndexedViewGroup)paramViewGroup;
    } else {
      reactZIndexedViewGroup = null;
    } 
    while (--i >= 0) {
      int j;
      if (reactZIndexedViewGroup != null) {
        j = reactZIndexedViewGroup.getZIndexMappedChildIndex(i);
      } else {
        j = i;
      } 
      View view = paramViewGroup.getChildAt(j);
      PointF pointF = mTempPoint;
      if (isTransformedTouchPointInView(paramArrayOffloat[0], paramArrayOffloat[1], paramViewGroup, view, pointF)) {
        float f1 = paramArrayOffloat[0];
        float f2 = paramArrayOffloat[1];
        paramArrayOffloat[0] = pointF.x;
        paramArrayOffloat[1] = pointF.y;
        view = findTouchTargetViewWithPointerEvents(paramArrayOffloat, view);
        if (view != null)
          return view; 
        paramArrayOffloat[0] = f1;
        paramArrayOffloat[1] = f2;
      } 
      i--;
    } 
    return (View)paramViewGroup;
  }
  
  private static View findTouchTargetViewWithPointerEvents(float[] paramArrayOffloat, View paramView) {
    View view;
    PointerEvents pointerEvents2;
    if (paramView instanceof ReactPointerEventsView) {
      pointerEvents2 = ((ReactPointerEventsView)paramView).getPointerEvents();
    } else {
      pointerEvents2 = PointerEvents.AUTO;
    } 
    PointerEvents pointerEvents1 = pointerEvents2;
    if (!paramView.isEnabled())
      if (pointerEvents2 == PointerEvents.AUTO) {
        pointerEvents1 = PointerEvents.BOX_NONE;
      } else {
        pointerEvents1 = pointerEvents2;
        if (pointerEvents2 == PointerEvents.BOX_ONLY)
          pointerEvents1 = PointerEvents.NONE; 
      }  
    if (pointerEvents1 == PointerEvents.NONE)
      return null; 
    if (pointerEvents1 == PointerEvents.BOX_ONLY)
      return paramView; 
    if (pointerEvents1 == PointerEvents.BOX_NONE) {
      if (paramView instanceof ViewGroup) {
        view = findTouchTargetView(paramArrayOffloat, (ViewGroup)paramView);
        if (view != paramView)
          return view; 
        if (paramView instanceof ReactCompoundView && ((ReactCompoundView)paramView).reactTagForTouch(paramArrayOffloat[0], paramArrayOffloat[1]) != paramView.getId())
          return paramView; 
      } 
      return null;
    } 
    if (view == PointerEvents.AUTO)
      return (paramView instanceof ReactCompoundViewGroup && ((ReactCompoundViewGroup)paramView).interceptsTouchEvent(paramArrayOffloat[0], paramArrayOffloat[1])) ? paramView : ((paramView instanceof ViewGroup) ? findTouchTargetView(paramArrayOffloat, (ViewGroup)paramView) : paramView); 
    StringBuilder stringBuilder = new StringBuilder("Unknown pointer event type: ");
    stringBuilder.append(view.toString());
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  private static int getTouchTargetForView(View paramView, float paramFloat1, float paramFloat2) {
    return (paramView instanceof ReactCompoundView) ? ((ReactCompoundView)paramView).reactTagForTouch(paramFloat1, paramFloat2) : paramView.getId();
  }
  
  private static boolean isTransformedTouchPointInView(float paramFloat1, float paramFloat2, ViewGroup paramViewGroup, View paramView, PointF paramPointF) {
    float f1 = paramFloat1 + paramViewGroup.getScrollX() - paramView.getLeft();
    float f2 = paramFloat2 + paramViewGroup.getScrollY() - paramView.getTop();
    Matrix matrix = paramView.getMatrix();
    paramFloat2 = f1;
    paramFloat1 = f2;
    if (!matrix.isIdentity()) {
      float[] arrayOfFloat = mMatrixTransformCoords;
      arrayOfFloat[0] = f1;
      arrayOfFloat[1] = f2;
      Matrix matrix1 = mInverseMatrix;
      matrix.invert(matrix1);
      matrix1.mapPoints(arrayOfFloat);
      paramFloat2 = arrayOfFloat[0];
      paramFloat1 = arrayOfFloat[1];
    } 
    if (paramView instanceof ReactHitSlopView) {
      ReactHitSlopView reactHitSlopView = (ReactHitSlopView)paramView;
      if (reactHitSlopView.getHitSlopRect() != null) {
        Rect rect = reactHitSlopView.getHitSlopRect();
        if (paramFloat2 >= -rect.left && paramFloat2 < (paramView.getRight() - paramView.getLeft() + rect.right) && paramFloat1 >= -rect.top && paramFloat1 < (paramView.getBottom() - paramView.getTop() + rect.bottom)) {
          paramPointF.set(paramFloat2, paramFloat1);
          return true;
        } 
        return false;
      } 
    } 
    if (paramFloat2 >= 0.0F && paramFloat2 < (paramView.getRight() - paramView.getLeft()) && paramFloat1 >= 0.0F && paramFloat1 < (paramView.getBottom() - paramView.getTop())) {
      paramPointF.set(paramFloat2, paramFloat1);
      return true;
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\TouchTargetHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */