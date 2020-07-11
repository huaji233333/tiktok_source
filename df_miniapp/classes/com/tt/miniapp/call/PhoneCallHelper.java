package com.tt.miniapp.call;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCallHelper {
  public static void markPhoneCall(Context paramContext, String paramString) {
    if (!paramString.startsWith("tel:")) {
      StringBuilder stringBuilder = new StringBuilder("tel:");
      stringBuilder.append(paramString);
      paramString = stringBuilder.toString();
    } 
    Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(paramString));
    intent.setFlags(268435456);
    paramContext.startActivity(intent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\call\PhoneCallHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */