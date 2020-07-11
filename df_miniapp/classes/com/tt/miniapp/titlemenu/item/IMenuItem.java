package com.tt.miniapp.titlemenu.item;

import com.tt.miniapp.titlemenu.view.MenuItemView;

public interface IMenuItem {
  ItemCategory getCategory();
  
  String getId();
  
  MenuItemView getView();
  
  void onMenuDismiss();
  
  void onMenuShow();
  
  public enum ItemCategory {
    SHARE;
    
    static {
    
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\IMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */