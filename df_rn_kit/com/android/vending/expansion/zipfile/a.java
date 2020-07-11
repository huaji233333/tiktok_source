package com.android.vending.expansion.zipfile;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public final class a {
  public static b a(Context paramContext, int paramInt1, int paramInt2) throws IOException {
    String str = paramContext.getPackageName();
    Vector<String> vector = new Vector();
    if (Environment.getExternalStorageState().equals("mounted")) {
      File file = Environment.getExternalStorageDirectory();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(file.toString());
      stringBuilder.append("/Android/obb/");
      stringBuilder.append(str);
      file = new File(stringBuilder.toString());
      if (file.exists()) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(file);
        stringBuilder.append(File.separator);
        stringBuilder.append("main.");
        stringBuilder.append(paramInt1);
        stringBuilder.append(".");
        stringBuilder.append(str);
        stringBuilder.append(".obb");
        String str1 = stringBuilder.toString();
        if (paramInt1 > 0 && (new File(str1)).isFile())
          vector.add(str1); 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(file);
        stringBuilder1.append(File.separator);
        stringBuilder1.append("patch.");
        stringBuilder1.append(paramInt2);
        stringBuilder1.append(".");
        stringBuilder1.append(str);
        stringBuilder1.append(".obb");
        str = stringBuilder1.toString();
        if (paramInt2 > 0 && (new File(str)).isFile())
          vector.add(str); 
      } 
    } 
    String[] arrayOfString = new String[vector.size()];
    vector.toArray(arrayOfString);
    return a(arrayOfString);
  }
  
  private static b a(String[] paramArrayOfString) throws IOException {
    int j = paramArrayOfString.length;
    b b = null;
    for (int i = 0; i < j; i++) {
      String str = paramArrayOfString[i];
      if (b == null) {
        b = new b(str);
      } else {
        b.b(str);
      } 
    } 
    return b;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\android\vending\expansion\zipfile\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */