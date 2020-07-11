package com.ss.android.ugc.rhea.a;

import java.util.ArrayList;
import java.util.List;

public final class a {
  public static List<b> a = new ArrayList<b>();
  
  public static final class a<T extends b> implements Runnable {
    private List<T> a = new ArrayList<T>();
    
    private a(List<T> param1List) {
      this.a.addAll(param1List);
    }
    
    public final void run() {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = this.a.size() - 1; i >= 0; i--) {
        stringBuilder.append("\n");
        b b = (b)this.a.get(i);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(b.d);
        stringBuilder1.append(",");
        stringBuilder1.append(b.a);
        stringBuilder1.append(",");
        stringBuilder1.append(b.g);
        stringBuilder1.append(",");
        stringBuilder1.append(b.h);
        stringBuilder1.append(",");
        stringBuilder1.append(b.e);
        stringBuilder1.append(",");
        stringBuilder1.append(b.f);
        stringBuilder.append(stringBuilder1.toString());
      } 
      com.ss.android.ugc.rhea.e.a.c.a(stringBuilder.toString(), com.ss.android.ugc.rhea.a.b);
      this.a.clear();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */