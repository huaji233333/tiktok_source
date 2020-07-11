package com.tt.option.n;

import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

public interface c {
  void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  void openFileChooser(ValueCallback<Uri[]> paramValueCallback, WebChromeClient.FileChooserParams paramFileChooserParams);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\n\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */