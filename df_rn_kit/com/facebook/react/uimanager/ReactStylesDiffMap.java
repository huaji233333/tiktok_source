package com.facebook.react.uimanager;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

public class ReactStylesDiffMap {
  final ReadableMap mBackingMap;
  
  public ReactStylesDiffMap(ReadableMap paramReadableMap) {
    this.mBackingMap = paramReadableMap;
  }
  
  public ReadableArray getArray(String paramString) {
    return this.mBackingMap.getArray(paramString);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    return this.mBackingMap.isNull(paramString) ? paramBoolean : this.mBackingMap.getBoolean(paramString);
  }
  
  public double getDouble(String paramString, double paramDouble) {
    return this.mBackingMap.isNull(paramString) ? paramDouble : this.mBackingMap.getDouble(paramString);
  }
  
  public Dynamic getDynamic(String paramString) {
    return this.mBackingMap.getDynamic(paramString);
  }
  
  public float getFloat(String paramString, float paramFloat) {
    return this.mBackingMap.isNull(paramString) ? paramFloat : (float)this.mBackingMap.getDouble(paramString);
  }
  
  public int getInt(String paramString, int paramInt) {
    return this.mBackingMap.isNull(paramString) ? paramInt : this.mBackingMap.getInt(paramString);
  }
  
  public ReadableMap getMap(String paramString) {
    return this.mBackingMap.getMap(paramString);
  }
  
  public String getString(String paramString) {
    return this.mBackingMap.getString(paramString);
  }
  
  public boolean hasKey(String paramString) {
    return this.mBackingMap.hasKey(paramString);
  }
  
  public boolean isNull(String paramString) {
    return this.mBackingMap.isNull(paramString);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{ ");
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append(": ");
    stringBuilder.append(this.mBackingMap.toString());
    stringBuilder.append(" }");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactStylesDiffMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */