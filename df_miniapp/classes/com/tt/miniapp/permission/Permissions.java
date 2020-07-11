package com.tt.miniapp.permission;

enum Permissions {
  DENIED, GRANTED, NOT_FOUND;
  
  static {
    DENIED = new Permissions("DENIED", 1);
    NOT_FOUND = new Permissions("NOT_FOUND", 2);
    $VALUES = new Permissions[] { GRANTED, DENIED, NOT_FOUND };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\Permissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */