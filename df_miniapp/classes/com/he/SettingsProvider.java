package com.he;

import android.content.Context;

public interface SettingsProvider {
  int getSetting(Context paramContext, Enum<?> paramEnum, int paramInt);
  
  String getSetting(Context paramContext, Enum<?> paramEnum, String paramString);
  
  boolean getSetting(Context paramContext, Enum<?> paramEnum, boolean paramBoolean);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\SettingsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */