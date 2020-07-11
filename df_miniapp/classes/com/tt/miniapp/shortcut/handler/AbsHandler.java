package com.tt.miniapp.shortcut.handler;

import android.app.Activity;
import com.tt.miniapp.shortcut.ShortcutResult;

public abstract class AbsHandler {
  Activity mAct;
  
  AbsHandler mNextHandler;
  
  ShortcutRequest mRequest;
  
  AbsHandler(ShortcutRequest paramShortcutRequest) {
    this.mRequest = paramShortcutRequest;
    this.mAct = paramShortcutRequest.activity;
  }
  
  AbsHandler getNextHandler() {
    return this.mNextHandler;
  }
  
  protected abstract ShortcutResult handleRequest();
  
  void setNextHandler(AbsHandler paramAbsHandler) {
    this.mNextHandler = paramAbsHandler;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\AbsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */