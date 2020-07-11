package com.swmansion.gesturehandler.react;

import android.util.SparseArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.swmansion.gesturehandler.b;
import com.swmansion.gesturehandler.c;

public final class d implements c {
  SparseArray<int[]> a = new SparseArray();
  
  SparseArray<int[]> b = new SparseArray();
  
  private static int[] a(ReadableMap paramReadableMap, String paramString) {
    ReadableArray readableArray = paramReadableMap.getArray(paramString);
    int[] arrayOfInt = new int[readableArray.size()];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = readableArray.getInt(i); 
    return arrayOfInt;
  }
  
  public final void a(int paramInt) {
    this.a.remove(paramInt);
    this.b.remove(paramInt);
  }
  
  public final void a(b paramb, ReadableMap paramReadableMap) {
    paramb.q = this;
    if (paramReadableMap.hasKey("waitFor")) {
      int[] arrayOfInt = a(paramReadableMap, "waitFor");
      this.a.put(paramb.e, arrayOfInt);
    } 
    if (paramReadableMap.hasKey("simultaneousHandlers")) {
      int[] arrayOfInt = a(paramReadableMap, "simultaneousHandlers");
      this.b.put(paramb.e, arrayOfInt);
    } 
  }
  
  public final boolean a(b paramb1, b paramb2) {
    int[] arrayOfInt = (int[])this.a.get(paramb1.e);
    if (arrayOfInt != null)
      for (int i = 0; i < arrayOfInt.length; i++) {
        if (arrayOfInt[i] == paramb2.e)
          return true; 
      }  
    return false;
  }
  
  public final boolean b(b paramb1, b paramb2) {
    int[] arrayOfInt = (int[])this.b.get(paramb1.e);
    if (arrayOfInt != null)
      for (int i = 0; i < arrayOfInt.length; i++) {
        if (arrayOfInt[i] == paramb2.e)
          return true; 
      }  
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */