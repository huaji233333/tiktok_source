package com.tt.miniapp.permission;

import android.os.Looper;
import java.util.HashSet;
import java.util.Set;

public abstract class PermissionsResultAction {
  private Looper mLooper = Looper.getMainLooper();
  
  private final Set<String> mPermissions = new HashSet<String>(1);
  
  public PermissionsResultAction() {}
  
  public PermissionsResultAction(Looper paramLooper) {
    this.mLooper = paramLooper;
  }
  
  public abstract void onDenied(String paramString);
  
  public abstract void onGranted();
  
  protected final boolean onResult(String paramString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_2
    //   3: ifne -> 19
    //   6: aload_0
    //   7: aload_1
    //   8: getstatic com/tt/miniapp/permission/Permissions.GRANTED : Lcom/tt/miniapp/permission/Permissions;
    //   11: invokevirtual onResult : (Ljava/lang/String;Lcom/tt/miniapp/permission/Permissions;)Z
    //   14: istore_3
    //   15: aload_0
    //   16: monitorexit
    //   17: iload_3
    //   18: ireturn
    //   19: aload_0
    //   20: aload_1
    //   21: getstatic com/tt/miniapp/permission/Permissions.DENIED : Lcom/tt/miniapp/permission/Permissions;
    //   24: invokevirtual onResult : (Ljava/lang/String;Lcom/tt/miniapp/permission/Permissions;)Z
    //   27: istore_3
    //   28: aload_0
    //   29: monitorexit
    //   30: iload_3
    //   31: ireturn
    //   32: astore_1
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_1
    //   36: athrow
    // Exception table:
    //   from	to	target	type
    //   6	15	32	finally
    //   19	28	32	finally
  }
  
  protected final boolean onResult(String paramString, Permissions paramPermissions) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPermissions : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface remove : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_2
    //   14: getstatic com/tt/miniapp/permission/Permissions.GRANTED : Lcom/tt/miniapp/permission/Permissions;
    //   17: if_acmpne -> 60
    //   20: aload_0
    //   21: getfield mPermissions : Ljava/util/Set;
    //   24: invokeinterface isEmpty : ()Z
    //   29: ifeq -> 178
    //   32: new android/os/Handler
    //   35: dup
    //   36: aload_0
    //   37: getfield mLooper : Landroid/os/Looper;
    //   40: invokespecial <init> : (Landroid/os/Looper;)V
    //   43: new com/tt/miniapp/permission/PermissionsResultAction$1
    //   46: dup
    //   47: aload_0
    //   48: aload_1
    //   49: invokespecial <init> : (Lcom/tt/miniapp/permission/PermissionsResultAction;Ljava/lang/String;)V
    //   52: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   55: pop
    //   56: aload_0
    //   57: monitorexit
    //   58: iconst_1
    //   59: ireturn
    //   60: aload_2
    //   61: getstatic com/tt/miniapp/permission/Permissions.DENIED : Lcom/tt/miniapp/permission/Permissions;
    //   64: if_acmpne -> 95
    //   67: new android/os/Handler
    //   70: dup
    //   71: aload_0
    //   72: getfield mLooper : Landroid/os/Looper;
    //   75: invokespecial <init> : (Landroid/os/Looper;)V
    //   78: new com/tt/miniapp/permission/PermissionsResultAction$2
    //   81: dup
    //   82: aload_0
    //   83: aload_1
    //   84: invokespecial <init> : (Lcom/tt/miniapp/permission/PermissionsResultAction;Ljava/lang/String;)V
    //   87: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   90: pop
    //   91: aload_0
    //   92: monitorexit
    //   93: iconst_1
    //   94: ireturn
    //   95: aload_2
    //   96: getstatic com/tt/miniapp/permission/Permissions.NOT_FOUND : Lcom/tt/miniapp/permission/Permissions;
    //   99: if_acmpne -> 178
    //   102: aload_0
    //   103: aload_1
    //   104: invokevirtual shouldIgnorePermissionNotFound : (Ljava/lang/String;)Z
    //   107: ifeq -> 150
    //   110: aload_0
    //   111: getfield mPermissions : Ljava/util/Set;
    //   114: invokeinterface isEmpty : ()Z
    //   119: ifeq -> 178
    //   122: new android/os/Handler
    //   125: dup
    //   126: aload_0
    //   127: getfield mLooper : Landroid/os/Looper;
    //   130: invokespecial <init> : (Landroid/os/Looper;)V
    //   133: new com/tt/miniapp/permission/PermissionsResultAction$3
    //   136: dup
    //   137: aload_0
    //   138: aload_1
    //   139: invokespecial <init> : (Lcom/tt/miniapp/permission/PermissionsResultAction;Ljava/lang/String;)V
    //   142: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   145: pop
    //   146: aload_0
    //   147: monitorexit
    //   148: iconst_1
    //   149: ireturn
    //   150: new android/os/Handler
    //   153: dup
    //   154: aload_0
    //   155: getfield mLooper : Landroid/os/Looper;
    //   158: invokespecial <init> : (Landroid/os/Looper;)V
    //   161: new com/tt/miniapp/permission/PermissionsResultAction$4
    //   164: dup
    //   165: aload_0
    //   166: aload_1
    //   167: invokespecial <init> : (Lcom/tt/miniapp/permission/PermissionsResultAction;Ljava/lang/String;)V
    //   170: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   173: pop
    //   174: aload_0
    //   175: monitorexit
    //   176: iconst_1
    //   177: ireturn
    //   178: aload_0
    //   179: monitorexit
    //   180: iconst_0
    //   181: ireturn
    //   182: astore_1
    //   183: aload_0
    //   184: monitorexit
    //   185: aload_1
    //   186: athrow
    // Exception table:
    //   from	to	target	type
    //   2	56	182	finally
    //   60	91	182	finally
    //   95	146	182	finally
    //   150	174	182	finally
  }
  
  protected final void registerPermissions(Set<String> paramSet) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPermissions : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public boolean shouldIgnorePermissionNotFound(String paramString) {
    /* monitor enter ThisExpression{ObjectType{com/tt/miniapp/permission/PermissionsResultAction}} */
    /* monitor exit ThisExpression{ObjectType{com/tt/miniapp/permission/PermissionsResultAction}} */
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\PermissionsResultAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */