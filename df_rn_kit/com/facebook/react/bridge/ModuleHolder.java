package com.facebook.react.bridge;

import com.facebook.react.module.model.ReactModuleInfo;
import java.util.concurrent.atomic.AtomicInteger;
import javax.a.a;

public class ModuleHolder {
  private static final AtomicInteger sInstanceKeyCounter = new AtomicInteger(1);
  
  private final boolean mCanOverrideExistingModule;
  
  private final boolean mHasConstants;
  
  private boolean mInitializable;
  
  private final int mInstanceKey = sInstanceKeyCounter.getAndIncrement();
  
  private boolean mIsCreating;
  
  private boolean mIsInitializing;
  
  private NativeModule mModule;
  
  private final String mName;
  
  private a<? extends NativeModule> mProvider;
  
  public ModuleHolder(NativeModule paramNativeModule) {
    this.mName = paramNativeModule.getName();
    this.mCanOverrideExistingModule = paramNativeModule.canOverrideExistingModule();
    this.mHasConstants = true;
    this.mModule = paramNativeModule;
    String str = this.mName;
  }
  
  public ModuleHolder(ReactModuleInfo paramReactModuleInfo, a<? extends NativeModule> parama) {
    this.mName = paramReactModuleInfo.name();
    this.mCanOverrideExistingModule = paramReactModuleInfo.canOverrideExistingModule();
    this.mHasConstants = paramReactModuleInfo.hasConstants();
    this.mProvider = parama;
    if (paramReactModuleInfo.needsEagerInit())
      this.mModule = create(); 
  }
  
  private NativeModule create() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   4: astore_3
    //   5: iconst_1
    //   6: istore_1
    //   7: aload_3
    //   8: ifnonnull -> 16
    //   11: iconst_1
    //   12: istore_2
    //   13: goto -> 18
    //   16: iconst_0
    //   17: istore_2
    //   18: iload_2
    //   19: ldc 'Creating an already created module.'
    //   21: invokestatic assertCondition : (ZLjava/lang/String;)V
    //   24: getstatic com/facebook/react/bridge/ReactMarkerConstants.CREATE_MODULE_START : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   27: aload_0
    //   28: getfield mName : Ljava/lang/String;
    //   31: aload_0
    //   32: getfield mInstanceKey : I
    //   35: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;Ljava/lang/String;I)V
    //   38: aload_0
    //   39: getfield mName : Ljava/lang/String;
    //   42: astore_3
    //   43: aload_0
    //   44: getfield mProvider : Ljavax/a/a;
    //   47: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   50: checkcast javax/a/a
    //   53: invokeinterface get : ()Ljava/lang/Object;
    //   58: checkcast com/facebook/react/bridge/NativeModule
    //   61: astore_3
    //   62: aload_0
    //   63: aconst_null
    //   64: putfield mProvider : Ljavax/a/a;
    //   67: aload_0
    //   68: monitorenter
    //   69: aload_0
    //   70: aload_3
    //   71: putfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   74: aload_0
    //   75: getfield mInitializable : Z
    //   78: ifeq -> 132
    //   81: aload_0
    //   82: getfield mIsInitializing : Z
    //   85: ifne -> 132
    //   88: goto -> 91
    //   91: aload_0
    //   92: monitorexit
    //   93: iload_1
    //   94: ifeq -> 102
    //   97: aload_0
    //   98: aload_3
    //   99: invokespecial doInitialize : (Lcom/facebook/react/bridge/NativeModule;)V
    //   102: getstatic com/facebook/react/bridge/ReactMarkerConstants.CREATE_MODULE_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   105: aload_0
    //   106: getfield mInstanceKey : I
    //   109: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;I)V
    //   112: aload_3
    //   113: areturn
    //   114: astore_3
    //   115: aload_0
    //   116: monitorexit
    //   117: aload_3
    //   118: athrow
    //   119: astore_3
    //   120: getstatic com/facebook/react/bridge/ReactMarkerConstants.CREATE_MODULE_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   123: aload_0
    //   124: getfield mInstanceKey : I
    //   127: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;I)V
    //   130: aload_3
    //   131: athrow
    //   132: iconst_0
    //   133: istore_1
    //   134: goto -> 91
    // Exception table:
    //   from	to	target	type
    //   43	69	119	finally
    //   69	88	114	finally
    //   91	93	114	finally
    //   97	102	119	finally
    //   115	117	114	finally
    //   117	119	119	finally
  }
  
  private void doInitialize(NativeModule paramNativeModule) {
    // Byte code:
    //   0: getstatic com/facebook/react/bridge/ReactMarkerConstants.INITIALIZE_MODULE_START : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   3: aload_0
    //   4: getfield mName : Ljava/lang/String;
    //   7: aload_0
    //   8: getfield mInstanceKey : I
    //   11: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;Ljava/lang/String;I)V
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield mInitializable : Z
    //   20: istore_3
    //   21: iconst_1
    //   22: istore_2
    //   23: iload_3
    //   24: ifeq -> 100
    //   27: aload_0
    //   28: getfield mIsInitializing : Z
    //   31: ifne -> 100
    //   34: aload_0
    //   35: iconst_1
    //   36: putfield mIsInitializing : Z
    //   39: goto -> 42
    //   42: aload_0
    //   43: monitorexit
    //   44: iload_2
    //   45: ifeq -> 71
    //   48: aload_1
    //   49: invokeinterface initialize : ()V
    //   54: aload_0
    //   55: monitorenter
    //   56: aload_0
    //   57: iconst_0
    //   58: putfield mIsInitializing : Z
    //   61: aload_0
    //   62: monitorexit
    //   63: goto -> 71
    //   66: astore_1
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: athrow
    //   71: getstatic com/facebook/react/bridge/ReactMarkerConstants.INITIALIZE_MODULE_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   74: aload_0
    //   75: getfield mInstanceKey : I
    //   78: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;I)V
    //   81: return
    //   82: astore_1
    //   83: aload_0
    //   84: monitorexit
    //   85: aload_1
    //   86: athrow
    //   87: astore_1
    //   88: getstatic com/facebook/react/bridge/ReactMarkerConstants.INITIALIZE_MODULE_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   91: aload_0
    //   92: getfield mInstanceKey : I
    //   95: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;I)V
    //   98: aload_1
    //   99: athrow
    //   100: iconst_0
    //   101: istore_2
    //   102: goto -> 42
    // Exception table:
    //   from	to	target	type
    //   14	16	87	finally
    //   16	21	82	finally
    //   27	39	82	finally
    //   42	44	82	finally
    //   48	56	87	finally
    //   56	63	66	finally
    //   67	69	66	finally
    //   69	71	87	finally
    //   83	85	82	finally
    //   85	87	87	finally
  }
  
  public void destroy() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   6: ifnull -> 18
    //   9: aload_0
    //   10: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   13: invokeinterface onCatalystInstanceDestroy : ()V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	21	finally
  }
  
  public boolean getCanOverrideExistingModule() {
    return this.mCanOverrideExistingModule;
  }
  
  public boolean getHasConstants() {
    return this.mHasConstants;
  }
  
  public NativeModule getModule() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   6: ifnull -> 18
    //   9: aload_0
    //   10: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   13: astore_3
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_3
    //   17: areturn
    //   18: aload_0
    //   19: getfield mIsCreating : Z
    //   22: istore_2
    //   23: iconst_1
    //   24: istore_1
    //   25: iload_2
    //   26: ifne -> 128
    //   29: aload_0
    //   30: iconst_1
    //   31: putfield mIsCreating : Z
    //   34: goto -> 37
    //   37: aload_0
    //   38: monitorexit
    //   39: iload_1
    //   40: ifeq -> 68
    //   43: aload_0
    //   44: invokespecial create : ()Lcom/facebook/react/bridge/NativeModule;
    //   47: astore_3
    //   48: aload_0
    //   49: monitorenter
    //   50: aload_0
    //   51: iconst_0
    //   52: putfield mIsCreating : Z
    //   55: aload_0
    //   56: invokevirtual notifyAll : ()V
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_3
    //   62: areturn
    //   63: astore_3
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_3
    //   67: athrow
    //   68: aload_0
    //   69: monitorenter
    //   70: aload_0
    //   71: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   74: ifnonnull -> 93
    //   77: aload_0
    //   78: getfield mIsCreating : Z
    //   81: istore_2
    //   82: iload_2
    //   83: ifeq -> 93
    //   86: aload_0
    //   87: invokevirtual wait : ()V
    //   90: goto -> 70
    //   93: aload_0
    //   94: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   97: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   100: checkcast com/facebook/react/bridge/NativeModule
    //   103: astore_3
    //   104: aload_0
    //   105: monitorexit
    //   106: aload_3
    //   107: areturn
    //   108: astore_3
    //   109: aload_0
    //   110: monitorexit
    //   111: aload_3
    //   112: athrow
    //   113: astore_3
    //   114: aload_0
    //   115: monitorexit
    //   116: goto -> 121
    //   119: aload_3
    //   120: athrow
    //   121: goto -> 119
    //   124: astore_3
    //   125: goto -> 70
    //   128: iconst_0
    //   129: istore_1
    //   130: goto -> 37
    // Exception table:
    //   from	to	target	type
    //   2	16	113	finally
    //   18	23	113	finally
    //   29	34	113	finally
    //   37	39	113	finally
    //   50	61	63	finally
    //   64	66	63	finally
    //   70	82	108	finally
    //   86	90	124	java/lang/InterruptedException
    //   86	90	108	finally
    //   93	106	108	finally
    //   109	111	108	finally
    //   114	116	113	finally
  }
  
  public String getName() {
    return this.mName;
  }
  
  boolean hasInstance() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull -> 17
    //   11: iconst_1
    //   12: istore_1
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_1
    //   16: ireturn
    //   17: iconst_0
    //   18: istore_1
    //   19: goto -> 13
    //   22: astore_2
    //   23: aload_0
    //   24: monitorexit
    //   25: goto -> 30
    //   28: aload_2
    //   29: athrow
    //   30: goto -> 28
    // Exception table:
    //   from	to	target	type
    //   2	7	22	finally
  }
  
  void markInitializable() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_1
    //   3: istore_1
    //   4: aload_0
    //   5: iconst_1
    //   6: putfield mInitializable : Z
    //   9: aload_0
    //   10: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   13: astore_3
    //   14: iconst_0
    //   15: istore_2
    //   16: aload_3
    //   17: ifnull -> 58
    //   20: aload_0
    //   21: getfield mIsInitializing : Z
    //   24: ifne -> 29
    //   27: iconst_1
    //   28: istore_2
    //   29: iload_2
    //   30: invokestatic a : (Z)V
    //   33: aload_0
    //   34: getfield mModule : Lcom/facebook/react/bridge/NativeModule;
    //   37: astore_3
    //   38: goto -> 41
    //   41: aload_0
    //   42: monitorexit
    //   43: iload_1
    //   44: ifeq -> 52
    //   47: aload_0
    //   48: aload_3
    //   49: invokespecial doInitialize : (Lcom/facebook/react/bridge/NativeModule;)V
    //   52: return
    //   53: astore_3
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_3
    //   57: athrow
    //   58: aconst_null
    //   59: astore_3
    //   60: iconst_0
    //   61: istore_1
    //   62: goto -> 41
    // Exception table:
    //   from	to	target	type
    //   4	14	53	finally
    //   20	27	53	finally
    //   29	38	53	finally
    //   41	43	53	finally
    //   54	56	53	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ModuleHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */