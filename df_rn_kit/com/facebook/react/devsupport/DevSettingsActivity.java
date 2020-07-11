package com.facebook.react.devsupport;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class DevSettingsActivity extends PreferenceActivity {
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setTitle(getApplication().getResources().getString(1980104728));
    addPreferencesFromResource(1980301312);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevSettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */