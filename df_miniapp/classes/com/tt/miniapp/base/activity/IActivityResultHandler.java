package com.tt.miniapp.base.activity;

import android.content.Intent;

public interface IActivityResultHandler {
  boolean autoClearAfterActivityResult();
  
  boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\activity\IActivityResultHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */