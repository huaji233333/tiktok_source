package com.facebook.react.flat;

import com.facebook.i.a.a;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ReactShadowNode;

final class MoveProxy {
  private ReactShadowNode[] mChildren = (ReactShadowNode[])new com.facebook.react.uimanager.ReactShadowNodeImpl[4];
  
  private int[] mMapping = new int[8];
  
  private ReadableArray mMoveTo;
  
  private int mSize;
  
  private static int k(int paramInt) {
    return paramInt * 2;
  }
  
  private int moveFromToIndex(int paramInt) {
    return this.mMapping[k(paramInt)];
  }
  
  private int moveFromToValue(int paramInt) {
    return this.mMapping[v(paramInt)];
  }
  
  private static int moveToToIndex(int paramInt) {
    return paramInt;
  }
  
  private int moveToToValue(int paramInt) {
    return ((ReadableArray)a.a(this.mMoveTo)).getInt(paramInt);
  }
  
  private void setKeyValue(int paramInt1, int paramInt2, int paramInt3) {
    this.mMapping[k(paramInt1)] = paramInt2;
    this.mMapping[v(paramInt1)] = paramInt3;
  }
  
  private void setSize(int paramInt) {
    for (int i = paramInt; i < this.mSize; i++)
      this.mChildren[i] = null; 
    this.mSize = paramInt;
  }
  
  private static int v(int paramInt) {
    return paramInt * 2 + 1;
  }
  
  public final ReactShadowNode getChildMoveTo(int paramInt) {
    return this.mChildren[moveToToIndex(paramInt)];
  }
  
  public final int getMoveFrom(int paramInt) {
    return moveFromToValue(paramInt);
  }
  
  public final int getMoveTo(int paramInt) {
    return moveToToValue(paramInt);
  }
  
  public final void setChildMoveFrom(int paramInt, ReactShadowNode paramReactShadowNode) {
    this.mChildren[moveFromToIndex(paramInt)] = paramReactShadowNode;
  }
  
  public final void setup(ReadableArray paramReadableArray1, ReadableArray paramReadableArray2) {
    this.mMoveTo = paramReadableArray2;
    if (paramReadableArray1 == null) {
      setSize(0);
      return;
    } 
    int j = paramReadableArray1.size();
    int i = j + j;
    if (this.mMapping.length < i) {
      this.mMapping = new int[i];
      this.mChildren = (ReactShadowNode[])new FlatShadowNode[j];
    } 
    setSize(j);
    setKeyValue(0, 0, paramReadableArray1.getInt(0));
    for (i = 1; i < j; i++) {
      int m = paramReadableArray1.getInt(i);
      int k;
      for (k = i - 1; k >= 0 && moveFromToValue(k) >= m; k--)
        setKeyValue(k + 1, moveFromToIndex(k), moveFromToValue(k)); 
      setKeyValue(k + 1, i, m);
    } 
  }
  
  public final int size() {
    return this.mSize;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\MoveProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */