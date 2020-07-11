package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.content.Context;
import com.ss.android.ugc.aweme.dfbase.c.f;

public class BaseActivity extends Activity {
  protected void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
    f.a(paramContext);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\BaseActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */