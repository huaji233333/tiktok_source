package com.tt.miniapp.titlemenu;

import com.tt.miniapp.event.Event;
import com.tt.miniapp.titlemenu.item.IMenuItem;
import java.util.Iterator;
import java.util.List;

public class MenuHelper {
  public static IMenuItem getMenuItemById(List<IMenuItem> paramList, String paramString) {
    if (paramList != null) {
      if (paramString == null)
        return null; 
      for (IMenuItem iMenuItem : paramList) {
        if (iMenuItem.getId() != null && paramString.contentEquals(iMenuItem.getId()))
          return iMenuItem; 
      } 
    } 
    return null;
  }
  
  public static void mpMenuClickEvent(String paramString) {
    Event.builder(paramString).flush();
  }
  
  public static void removeMenuItem(List<IMenuItem> paramList, String paramString) {
    if (paramList != null) {
      if (paramString == null)
        return; 
      Iterator<IMenuItem> iterator = paramList.iterator();
      while (iterator.hasNext()) {
        IMenuItem iMenuItem = iterator.next();
        if (iMenuItem.getId() != null && paramString.contentEquals(iMenuItem.getId())) {
          iterator.remove();
          break;
        } 
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\MenuHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */