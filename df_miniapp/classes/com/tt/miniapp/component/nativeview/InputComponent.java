package com.tt.miniapp.component.nativeview;

public interface InputComponent {
  boolean getConfirm();
  
  int getCursor();
  
  int getInputHeight();
  
  String getType();
  
  String getValue();
  
  boolean hasFocus();
  
  boolean isAdjustPosition();
  
  boolean isAutoBlur();
  
  boolean isFixed();
  
  void setValue(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\InputComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */