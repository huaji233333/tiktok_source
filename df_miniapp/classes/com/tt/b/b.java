package com.tt.b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.w.f;
import com.tt.option.w.g;
import com.tt.option.w.h;
import java.util.HashMap;
import java.util.List;

public interface b {
  InitParamsEntity createInitParams();
  
  boolean handleActivityLoginResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  boolean handleActivityShareResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  void loadImage(Context paramContext, c paramc);
  
  boolean openLoginActivity(Activity paramActivity, HashMap<String, Object> paramHashMap);
  
  boolean share(Activity paramActivity, h paramh, g paramg);
  
  void showShareDialog(Activity paramActivity, f paramf);
  
  boolean startImagePreviewActivity(Activity paramActivity, String paramString, List<String> paramList, int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */