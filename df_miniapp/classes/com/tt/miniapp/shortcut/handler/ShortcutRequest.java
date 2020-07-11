package com.tt.miniapp.shortcut.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutResult;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapp.shortcut.adapter.CustomShortcutManagerCompat;
import com.tt.miniapp.thread.ThreadPools;
import java.util.UUID;

public class ShortcutRequest {
  Activity activity;
  
  public ShortcutEntity entity;
  
  public String id;
  
  boolean updateShortcut;
  
  public ShortcutRequest(Activity paramActivity, ShortcutEntity paramShortcutEntity) {
    this.activity = paramActivity;
    this.entity = paramShortcutEntity;
    this.id = UUID.randomUUID().toString();
  }
  
  private void doubleCheck() {
    if (Build.VERSION.SDK_INT < 25) {
      ShortcutEventReporter.reportResult("yes", "add shortcut success");
      return;
    } 
    Observable.create(new Function<Boolean>() {
          public Boolean fun() {
            return Boolean.valueOf(CustomShortcutManagerCompat.isShortcutExist((Context)ShortcutRequest.this.activity, ShortcutRequest.this.entity.getAppId()));
          }
        }).schudleOn(ThreadPools.defaults()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<Boolean>() {
          public void onError(Throwable param1Throwable) {
            ShortcutEventReporter.reportResult("no", Log.getStackTraceString(param1Throwable));
          }
          
          public void onSuccess(Boolean param1Boolean) {
            if (param1Boolean.booleanValue()) {
              ShortcutEventReporter.reportResult("yes", "add shortcut success");
              ((ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class)).setShortcutState(true);
              return;
            } 
            ShortcutEventReporter.reportResult("no", "permission_denied");
          }
        });
  }
  
  public void callback(ShortcutResult paramShortcutResult) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_1
    //   10: invokevirtual getResult : ()Lcom/tt/miniapp/shortcut/ShortcutResult$Result;
    //   13: astore_2
    //   14: aload_2
    //   15: getstatic com/tt/miniapp/shortcut/ShortcutResult$Result.FAIL : Lcom/tt/miniapp/shortcut/ShortcutResult$Result;
    //   18: if_acmpne -> 33
    //   21: ldc 'no'
    //   23: aload_1
    //   24: invokevirtual getErrorMsg : ()Ljava/lang/String;
    //   27: invokestatic reportResult : (Ljava/lang/String;Ljava/lang/String;)V
    //   30: goto -> 89
    //   33: aload_2
    //   34: getstatic com/tt/miniapp/shortcut/ShortcutResult$Result.SUCCESS : Lcom/tt/miniapp/shortcut/ShortcutResult$Result;
    //   37: if_acmpne -> 78
    //   40: aload_1
    //   41: invokevirtual getErrorMsg : ()Ljava/lang/String;
    //   44: astore_1
    //   45: aload_1
    //   46: ldc 'shortcut is exist and shortcut info same'
    //   48: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   51: ifeq -> 69
    //   54: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   57: ldc com/tt/miniapp/shortcut/ShortcutService
    //   59: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   62: checkcast com/tt/miniapp/shortcut/ShortcutService
    //   65: iconst_1
    //   66: invokevirtual setShortcutState : (Z)V
    //   69: ldc 'yes'
    //   71: aload_1
    //   72: invokestatic reportResult : (Ljava/lang/String;Ljava/lang/String;)V
    //   75: goto -> 89
    //   78: aload_2
    //   79: getstatic com/tt/miniapp/shortcut/ShortcutResult$Result.NEED_CHECK : Lcom/tt/miniapp/shortcut/ShortcutResult$Result;
    //   82: if_acmpne -> 89
    //   85: aload_0
    //   86: invokespecial doubleCheck : ()V
    //   89: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   92: ldc com/tt/miniapp/shortcut/ShortcutService
    //   94: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   97: checkcast com/tt/miniapp/shortcut/ShortcutService
    //   100: astore_1
    //   101: aload_1
    //   102: invokevirtual getPendingList : ()Ljava/util/List;
    //   105: astore_2
    //   106: aload_2
    //   107: ifnull -> 145
    //   110: aload_2
    //   111: aload_0
    //   112: invokeinterface remove : (Ljava/lang/Object;)Z
    //   117: pop
    //   118: aload_2
    //   119: invokeinterface size : ()I
    //   124: ifne -> 145
    //   127: ldc 'ShortcutRequest'
    //   129: iconst_1
    //   130: anewarray java/lang/Object
    //   133: dup
    //   134: iconst_0
    //   135: ldc 'pendingList is empty unregister broadcast'
    //   137: aastore
    //   138: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   141: aload_1
    //   142: invokevirtual unregisterIntentCallback : ()V
    //   145: aload_0
    //   146: monitorexit
    //   147: return
    //   148: astore_1
    //   149: aload_0
    //   150: monitorexit
    //   151: aload_1
    //   152: athrow
    // Exception table:
    //   from	to	target	type
    //   9	30	148	finally
    //   33	69	148	finally
    //   69	75	148	finally
    //   78	89	148	finally
    //   89	106	148	finally
    //   110	145	148	finally
  }
  
  public void request() {
    PermissionHandler permissionHandler = new PermissionHandler(this);
    ValidateHandler validateHandler = new ValidateHandler(this);
    AddHandler addHandler = new AddHandler(this);
    DialogHandler dialogHandler = new DialogHandler(this);
    permissionHandler.setNextHandler(validateHandler);
    validateHandler.setNextHandler(addHandler);
    addHandler.setNextHandler(dialogHandler);
    callback(permissionHandler.handleRequest());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\ShortcutRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */