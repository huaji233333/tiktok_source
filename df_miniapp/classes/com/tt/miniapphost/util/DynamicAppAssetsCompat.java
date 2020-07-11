package com.tt.miniapphost.util;

import android.content.Context;
import android.content.res.AssetManager;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;

public class DynamicAppAssetsCompat {
  static DynamicsAssetCompat sDynamicActivityAssetsCompat = new DynamicsAssetCompat();
  
  static DynamicsAssetCompat sDynamicAppAssetsCompat = new DynamicsAssetCompat();
  
  public static void ensureDynamicFeatureAssets(Context paramContext) {
    if (paramContext == null)
      return; 
    ensureDynamicFeatureAssets(paramContext, paramContext.getAssets());
  }
  
  public static void ensureDynamicFeatureAssets(Context paramContext, AssetManager paramAssetManager) {
    if (!AppbrandUtil.isAppBundleEnable())
      return; 
    if (paramContext == null)
      return; 
    if (paramAssetManager == null)
      return; 
    if (paramContext instanceof android.app.Activity) {
      sDynamicActivityAssetsCompat.ensureAssets(paramContext, paramAssetManager);
      return;
    } 
    sDynamicAppAssetsCompat.ensureAssets(paramContext, paramAssetManager);
  }
  
  static class DynamicsAssetCompat {
    private LinkedHashSet<Integer> injectedAssetHashcodeSet = new LinkedHashSet<Integer>();
    
    private volatile WeakReference<AssetManager> lastInjectedAssetsRef;
    
    public void ensureAssets(Context param1Context, AssetManager param1AssetManager) {
      // Byte code:
      //   0: aload_0
      //   1: getfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   4: ifnull -> 19
      //   7: aload_0
      //   8: getfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   11: invokevirtual get : ()Ljava/lang/Object;
      //   14: aload_2
      //   15: if_acmpne -> 19
      //   18: return
      //   19: aload_0
      //   20: monitorenter
      //   21: aload_0
      //   22: getfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   25: ifnull -> 42
      //   28: aload_0
      //   29: getfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   32: invokevirtual get : ()Ljava/lang/Object;
      //   35: aload_2
      //   36: if_acmpne -> 42
      //   39: aload_0
      //   40: monitorexit
      //   41: return
      //   42: aload_0
      //   43: new java/lang/ref/WeakReference
      //   46: dup
      //   47: aload_2
      //   48: invokespecial <init> : (Ljava/lang/Object;)V
      //   51: putfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   54: aload_2
      //   55: invokestatic identityHashCode : (Ljava/lang/Object;)I
      //   58: istore_3
      //   59: aload_0
      //   60: getfield injectedAssetHashcodeSet : Ljava/util/LinkedHashSet;
      //   63: iload_3
      //   64: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   67: invokevirtual contains : (Ljava/lang/Object;)Z
      //   70: istore #4
      //   72: iload #4
      //   74: ifne -> 114
      //   77: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
      //   80: aload_1
      //   81: invokevirtual doAppBundleSplitInstallAction : (Landroid/content/Context;)Z
      //   84: pop
      //   85: goto -> 102
      //   88: astore_1
      //   89: ldc 'DynamicAppAssetsCompat'
      //   91: iconst_1
      //   92: anewarray java/lang/Object
      //   95: dup
      //   96: iconst_0
      //   97: aload_1
      //   98: aastore
      //   99: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   102: aload_0
      //   103: getfield injectedAssetHashcodeSet : Ljava/util/LinkedHashSet;
      //   106: iload_3
      //   107: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   110: invokevirtual add : (Ljava/lang/Object;)Z
      //   113: pop
      //   114: aload_0
      //   115: monitorexit
      //   116: return
      //   117: astore_1
      //   118: aload_0
      //   119: monitorexit
      //   120: aload_1
      //   121: athrow
      // Exception table:
      //   from	to	target	type
      //   21	41	117	finally
      //   42	72	117	finally
      //   77	85	88	finally
      //   89	102	117	finally
      //   102	114	117	finally
      //   114	116	117	finally
      //   118	120	117	finally
    }
    
    public void invalidCaches() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aconst_null
      //   4: putfield lastInjectedAssetsRef : Ljava/lang/ref/WeakReference;
      //   7: aload_0
      //   8: getfield injectedAssetHashcodeSet : Ljava/util/LinkedHashSet;
      //   11: invokevirtual clear : ()V
      //   14: aload_0
      //   15: monitorexit
      //   16: return
      //   17: astore_1
      //   18: aload_0
      //   19: monitorexit
      //   20: aload_1
      //   21: athrow
      // Exception table:
      //   from	to	target	type
      //   2	16	17	finally
      //   18	20	17	finally
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\DynamicAppAssetsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */