package com.tt.miniapp.chooser.data;

import com.tt.miniapp.entity.Folder;
import java.util.ArrayList;

public class LoaderM {
  public String getParent(String paramString) {
    String[] arrayOfString = paramString.split("/");
    return arrayOfString[arrayOfString.length - 2];
  }
  
  public int hasDir(ArrayList<Folder> paramArrayList, String paramString) {
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (((Folder)paramArrayList.get(i)).name.equals(paramString))
        return i; 
    } 
    return -1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\data\LoaderM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */