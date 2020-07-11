package com.facebook.react.flat;

import android.content.Context;
import com.facebook.drawee.e.q;
import com.facebook.react.bridge.ReadableArray;

interface DrawImage extends AttachDetachListener {
  int getBorderColor();
  
  float getBorderRadius();
  
  float getBorderWidth();
  
  q.b getScaleType();
  
  boolean hasImageRequest();
  
  void setBorderColor(int paramInt);
  
  void setBorderRadius(float paramFloat);
  
  void setBorderWidth(float paramFloat);
  
  void setFadeDuration(int paramInt);
  
  void setProgressiveRenderingEnabled(boolean paramBoolean);
  
  void setReactTag(int paramInt);
  
  void setScaleType(q.b paramb);
  
  void setSource(Context paramContext, ReadableArray paramReadableArray);
  
  void setTintColor(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */