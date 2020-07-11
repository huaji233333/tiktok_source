package com.facebook.react.uimanager.layoutanimation;

enum LayoutAnimationType {
  CREATE("create"),
  DELETE("create"),
  UPDATE("update");
  
  private final String mName;
  
  static {
    DELETE = new LayoutAnimationType("DELETE", 2, "delete");
    $VALUES = new LayoutAnimationType[] { CREATE, UPDATE, DELETE };
  }
  
  LayoutAnimationType(String paramString1) {
    this.mName = paramString1;
  }
  
  public final String toString() {
    return this.mName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\LayoutAnimationType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */