package com.tt.miniapp.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardManagerUtil {
  public static String get(Context paramContext) {
    ClipData clipData = ((ClipboardManager)paramContext.getSystemService("clipboard")).getPrimaryClip();
    if (clipData != null && clipData.getItemCount() > 0) {
      CharSequence charSequence = clipData.getItemAt(0).getText();
      if (charSequence != null)
        return charSequence.toString(); 
    } 
    return "";
  }
  
  public static void set(String paramString, Context paramContext) {
    ((ClipboardManager)paramContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(paramString, paramString));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ClipboardManagerUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */