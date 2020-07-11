package com.facebook.react.flat;

interface AttachDetachListener {
  public static final AttachDetachListener[] EMPTY_ARRAY = new AttachDetachListener[0];
  
  void onAttached(FlatViewGroup.InvalidateCallback paramInvalidateCallback);
  
  void onDetached();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\AttachDetachListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */