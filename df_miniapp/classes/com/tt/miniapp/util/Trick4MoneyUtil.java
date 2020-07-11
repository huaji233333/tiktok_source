package com.tt.miniapp.util;

import android.text.TextUtils;
import com.tt.miniapphost.entity.AppInfoEntity;

public class Trick4MoneyUtil {
  public static boolean ignoreWebViewScheme(String paramString, AppInfoEntity paramAppInfoEntity) {
    return (paramAppInfoEntity == null) ? false : ((TextUtils.equals("tt8535f3881a1ffba5", paramAppInfoEntity.appId) && (TextUtils.equals(paramString, "tbopen") || TextUtils.equals(paramString, "tmall"))));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\Trick4MoneyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */