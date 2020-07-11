package com.tt.miniapp.shortcut.handler;

import android.content.Context;
import android.util.Log;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutResult;
import com.tt.miniapp.shortcut.adapter.CustomShortcutManagerCompat;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapphost.AppBrandLogger;

public class ValidateHandler extends AbsHandler {
  ValidateHandler(ShortcutRequest paramShortcutRequest) {
    super(paramShortcutRequest);
  }
  
  protected ShortcutResult handleRequest() {
    if (!CustomShortcutManagerCompat.isRequestPinShortcutSupported((Context)this.mAct)) {
      AppBrandLogger.d("ValidateHandler", new Object[] { "device not support shortcut" });
      ShortcutEventReporter.reportResult("no", "device_unsupported");
      return new ShortcutResult(ShortcutResult.Result.FAIL, "device_unsupported");
    } 
    Observable.create(new Function<ShortcutResult>() {
          public ShortcutResult fun() {
            CustomShortcutManagerCompat.ShortcutStatus shortcutStatus = CustomShortcutManagerCompat.getShortcutState((Context)ValidateHandler.this.mAct, entity);
            if (shortcutStatus.exist && !shortcutStatus.needUpdate) {
              AppBrandLogger.i("ValidateHandler", new Object[] { "update shortcut exist" });
              ToastManager.showShortToast((Context)ValidateHandler.this.mAct, ValidateHandler.this.mAct.getString(2097741851));
              return new ShortcutResult(ShortcutResult.Result.SUCCESS, "shortcut is exist and shortcut info same");
            } 
            if (!shortcutStatus.exist)
              return ValidateHandler.this.getNextHandler().handleRequest(); 
            ValidateHandler.this.mRequest.updateShortcut = true;
            return ValidateHandler.this.getNextHandler().handleRequest();
          }
        }).schudleOn(ThreadPools.defaults()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<ShortcutResult>() {
          public void onError(Throwable param1Throwable) {
            ValidateHandler.this.mRequest.callback(new ShortcutResult(ShortcutResult.Result.FAIL, Log.getStackTraceString(param1Throwable)));
          }
          
          public void onSuccess(ShortcutResult param1ShortcutResult) {
            ValidateHandler.this.mRequest.callback(param1ShortcutResult);
          }
        });
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\ValidateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */