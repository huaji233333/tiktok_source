package com.ss.android.ugc.aweme.miniapp.g;

import com.ss.android.ugc.aweme.miniapp.t;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.item.IMenuItem;
import com.tt.option.o.a;
import java.util.ArrayList;
import java.util.List;

public class o extends a {
  private static String[] a = new String[] { "favorite_mini_app", "generate_shortcut", "share", "back_home", "feedback_and_helper", "restart_mini_app", "about", "settings" };
  
  public List<Object> createTitleMenuItems() {
    ArrayList<t> arrayList = new ArrayList();
    arrayList.add(new t());
    return (List)arrayList;
  }
  
  public List<IMenuItem> replacesMenuItems(List<IMenuItem> paramList) {
    ArrayList<IMenuItem> arrayList = new ArrayList();
    String[] arrayOfString = a;
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      IMenuItem iMenuItem = MenuHelper.getMenuItemById(paramList, arrayOfString[i]);
      if (iMenuItem != null) {
        arrayList.add(iMenuItem);
        paramList.remove(iMenuItem);
      } 
    } 
    arrayList.addAll(paramList);
    return arrayList;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */