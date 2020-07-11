package com.ss.android.ugc.aweme.miniapp.g;

import android.text.TextUtils;
import com.tt.option.v.a;
import java.util.HashMap;
import java.util.Map;

public class a extends a {
  private Map<String, String> a;
  
  public String getScene(String paramString) {
    if (this.a == null) {
      this.a = new HashMap<String, String>();
      this.a.put("in_mp", "021009");
      this.a.put("back_mp", "021010");
      this.a.put("desktop", "021020");
    } 
    String str = this.a.get(paramString);
    paramString = str;
    if (TextUtils.isEmpty(str))
      paramString = "0"; 
    return paramString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */