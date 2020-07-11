package com.swmansion.gesturehandler.react;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.swmansion.gesturehandler.i;
import com.swmansion.gesturehandler.j;
import com.swmansion.gesturehandler.k;
import com.swmansion.gesturehandler.m;
import com.swmansion.gesturehandler.n;
import com.swmansion.gesturehandler.o;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ReactModule(name = "RNGestureHandlerModule")
public class RNGestureHandlerModule extends ReactContextBaseJavaModule {
  public List<Integer> mEnqueuedRootViewInit = new ArrayList<Integer>();
  
  private i mEventListener = new i(this) {
      public final void a(com.swmansion.gesturehandler.b param1b, int param1Int1, int param1Int2) {
        this.a.onStateChange(param1b, param1Int1, param1Int2);
      }
      
      public final void a(com.swmansion.gesturehandler.b param1b, MotionEvent param1MotionEvent) {
        this.a.onTouchEvent(param1b, param1MotionEvent);
      }
    };
  
  private b[] mHandlerFactories = new b[] { new d(), new h(), new c(), new e(), new f(), new g(), new a() };
  
  private d mInteractionManager = new d();
  
  private final f mRegistry = new f();
  
  private List<g> mRoots = new ArrayList<g>();
  
  public RNGestureHandlerModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private b findFactoryForHandler(com.swmansion.gesturehandler.b paramb) {
    int j = 0;
    while (true) {
      b[] arrayOfB = this.mHandlerFactories;
      if (j < arrayOfB.length) {
        b b1 = arrayOfB[j];
        if (b1.a().equals(paramb.getClass()))
          return b1; 
        j++;
        continue;
      } 
      return null;
    } 
  }
  
  private g findRootHelperForViewAncestor(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   4: ldc com/facebook/react/uimanager/UIManagerModule
    //   6: invokevirtual getNativeModule : (Ljava/lang/Class;)Lcom/facebook/react/bridge/NativeModule;
    //   9: checkcast com/facebook/react/uimanager/UIManagerModule
    //   12: iload_1
    //   13: invokevirtual resolveRootTagFromReactTag : (I)I
    //   16: istore_2
    //   17: iload_2
    //   18: ifgt -> 23
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield mRoots : Ljava/util/List;
    //   27: astore_3
    //   28: aload_3
    //   29: monitorenter
    //   30: iconst_0
    //   31: istore_1
    //   32: iload_1
    //   33: aload_0
    //   34: getfield mRoots : Ljava/util/List;
    //   37: invokeinterface size : ()I
    //   42: if_icmpge -> 77
    //   45: aload_0
    //   46: getfield mRoots : Ljava/util/List;
    //   49: iload_1
    //   50: invokeinterface get : (I)Ljava/lang/Object;
    //   55: checkcast com/swmansion/gesturehandler/react/g
    //   58: astore #4
    //   60: aload #4
    //   62: getfield a : Lcom/facebook/react/ReactRootView;
    //   65: invokevirtual getRootViewTag : ()I
    //   68: iload_2
    //   69: if_icmpne -> 94
    //   72: aload_3
    //   73: monitorexit
    //   74: aload #4
    //   76: areturn
    //   77: aload_3
    //   78: monitorexit
    //   79: aconst_null
    //   80: areturn
    //   81: astore #4
    //   83: aload_3
    //   84: monitorexit
    //   85: goto -> 91
    //   88: aload #4
    //   90: athrow
    //   91: goto -> 88
    //   94: iload_1
    //   95: iconst_1
    //   96: iadd
    //   97: istore_1
    //   98: goto -> 32
    // Exception table:
    //   from	to	target	type
    //   32	74	81	finally
    //   77	79	81	finally
    //   83	85	81	finally
  }
  
  public static void handleHitSlopProperty(com.swmansion.gesturehandler.b paramb, ReadableMap paramReadableMap) {
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    if (paramReadableMap.getType("hitSlop") == ReadableType.Number) {
      f1 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("hitSlop"));
      paramb.a(f1, f1, f1, f1, Float.NaN, Float.NaN);
      return;
    } 
    paramReadableMap = paramReadableMap.getMap("hitSlop");
    if (paramReadableMap.hasKey("horizontal")) {
      f3 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("horizontal"));
      f1 = f3;
    } else {
      f3 = Float.NaN;
      f1 = Float.NaN;
    } 
    if (paramReadableMap.hasKey("vertical")) {
      f2 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("vertical"));
      f4 = f2;
    } else {
      f2 = Float.NaN;
      f4 = Float.NaN;
    } 
    if (paramReadableMap.hasKey("left"))
      f3 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("left")); 
    if (paramReadableMap.hasKey("top"))
      f4 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("top")); 
    if (paramReadableMap.hasKey("right"))
      f1 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("right")); 
    if (paramReadableMap.hasKey("bottom"))
      f2 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("bottom")); 
    if (paramReadableMap.hasKey("width")) {
      f5 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("width"));
    } else {
      f5 = Float.NaN;
    } 
    if (paramReadableMap.hasKey("height")) {
      f6 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("height"));
    } else {
      f6 = Float.NaN;
    } 
    paramb.a(f3, f4, f1, f2, f5, f6);
  }
  
  private void tryInitializeHandlerForReactRootView(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   4: ldc com/facebook/react/uimanager/UIManagerModule
    //   6: invokevirtual getNativeModule : (Ljava/lang/Class;)Lcom/facebook/react/bridge/NativeModule;
    //   9: checkcast com/facebook/react/uimanager/UIManagerModule
    //   12: astore_3
    //   13: aload_3
    //   14: iload_1
    //   15: invokevirtual resolveRootTagFromReactTag : (I)I
    //   18: istore_2
    //   19: iload_2
    //   20: ifle -> 149
    //   23: aload_0
    //   24: getfield mRoots : Ljava/util/List;
    //   27: astore #4
    //   29: aload #4
    //   31: monitorenter
    //   32: iconst_0
    //   33: istore_1
    //   34: iload_1
    //   35: aload_0
    //   36: getfield mRoots : Ljava/util/List;
    //   39: invokeinterface size : ()I
    //   44: if_icmpge -> 74
    //   47: aload_0
    //   48: getfield mRoots : Ljava/util/List;
    //   51: iload_1
    //   52: invokeinterface get : (I)Ljava/lang/Object;
    //   57: checkcast com/swmansion/gesturehandler/react/g
    //   60: getfield a : Lcom/facebook/react/ReactRootView;
    //   63: invokevirtual getRootViewTag : ()I
    //   66: iload_2
    //   67: if_icmpne -> 185
    //   70: aload #4
    //   72: monitorexit
    //   73: return
    //   74: aload #4
    //   76: monitorexit
    //   77: aload_0
    //   78: getfield mEnqueuedRootViewInit : Ljava/util/List;
    //   81: astore #4
    //   83: aload #4
    //   85: monitorenter
    //   86: aload_0
    //   87: getfield mEnqueuedRootViewInit : Ljava/util/List;
    //   90: iload_2
    //   91: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   94: invokeinterface contains : (Ljava/lang/Object;)Z
    //   99: ifeq -> 106
    //   102: aload #4
    //   104: monitorexit
    //   105: return
    //   106: aload_0
    //   107: getfield mEnqueuedRootViewInit : Ljava/util/List;
    //   110: iload_2
    //   111: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   114: invokeinterface add : (Ljava/lang/Object;)Z
    //   119: pop
    //   120: aload #4
    //   122: monitorexit
    //   123: aload_3
    //   124: new com/swmansion/gesturehandler/react/RNGestureHandlerModule$2
    //   127: dup
    //   128: aload_0
    //   129: iload_2
    //   130: invokespecial <init> : (Lcom/swmansion/gesturehandler/react/RNGestureHandlerModule;I)V
    //   133: invokevirtual addUIBlock : (Lcom/facebook/react/uimanager/UIBlock;)V
    //   136: return
    //   137: astore_3
    //   138: aload #4
    //   140: monitorexit
    //   141: aload_3
    //   142: athrow
    //   143: astore_3
    //   144: aload #4
    //   146: monitorexit
    //   147: aload_3
    //   148: athrow
    //   149: new java/lang/StringBuilder
    //   152: dup
    //   153: ldc 'Could find root view for a given ancestor with tag '
    //   155: invokespecial <init> : (Ljava/lang/String;)V
    //   158: astore_3
    //   159: aload_3
    //   160: iload_1
    //   161: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: new com/facebook/react/bridge/JSApplicationIllegalArgumentException
    //   168: dup
    //   169: aload_3
    //   170: invokevirtual toString : ()Ljava/lang/String;
    //   173: invokespecial <init> : (Ljava/lang/String;)V
    //   176: astore_3
    //   177: goto -> 182
    //   180: aload_3
    //   181: athrow
    //   182: goto -> 180
    //   185: iload_1
    //   186: iconst_1
    //   187: iadd
    //   188: istore_1
    //   189: goto -> 34
    // Exception table:
    //   from	to	target	type
    //   34	73	143	finally
    //   74	77	143	finally
    //   86	105	137	finally
    //   106	123	137	finally
    //   138	141	137	finally
    //   144	147	143	finally
  }
  
  @ReactMethod
  public void attachGestureHandler(int paramInt1, int paramInt2) {
    tryInitializeHandlerForReactRootView(paramInt2);
    if (this.mRegistry.a(paramInt1, paramInt2))
      return; 
    StringBuilder stringBuilder = new StringBuilder("Handler with tag ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactMethod
  public void createGestureHandler(String paramString, int paramInt, ReadableMap paramReadableMap) {
    int j = 0;
    while (true) {
      b[] arrayOfB = this.mHandlerFactories;
      if (j < arrayOfB.length) {
        b<String> b1 = arrayOfB[j];
        if (b1.b().equals(paramString)) {
          paramString = b1.a((Context)getReactApplicationContext());
          ((com.swmansion.gesturehandler.b)paramString).e = paramInt;
          ((com.swmansion.gesturehandler.b)paramString).p = this.mEventListener;
          this.mRegistry.a((com.swmansion.gesturehandler.b)paramString);
          this.mInteractionManager.a((com.swmansion.gesturehandler.b)paramString, paramReadableMap);
          b1.a(paramString, paramReadableMap);
          return;
        } 
        j++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder("Invalid handler name ");
      stringBuilder.append(paramString);
      JSApplicationIllegalArgumentException jSApplicationIllegalArgumentException = new JSApplicationIllegalArgumentException(stringBuilder.toString());
      throw jSApplicationIllegalArgumentException;
    } 
  }
  
  @ReactMethod
  public void dropGestureHandler(int paramInt) {
    this.mInteractionManager.a(paramInt);
    this.mRegistry.b(paramInt);
  }
  
  public Map getConstants() {
    Integer integer1 = Integer.valueOf(2);
    Integer integer2 = Integer.valueOf(4);
    Integer integer3 = Integer.valueOf(1);
    return MapBuilder.of("State", MapBuilder.of("UNDETERMINED", Integer.valueOf(0), "BEGAN", integer1, "ACTIVE", integer2, "CANCELLED", Integer.valueOf(3), "FAILED", integer3, "END", Integer.valueOf(5)), "Direction", MapBuilder.of("RIGHT", integer3, "LEFT", integer1, "UP", integer2, "DOWN", Integer.valueOf(8)));
  }
  
  public String getName() {
    return "RNGestureHandlerModule";
  }
  
  public f getRegistry() {
    return this.mRegistry;
  }
  
  @ReactMethod
  public void handleClearJSResponder() {}
  
  @ReactMethod
  public void handleSetJSResponder(int paramInt, boolean paramBoolean) {
    if (this.mRegistry != null) {
      g g = findRootHelperForViewAncestor(paramInt);
      if (g != null && paramBoolean)
        UiThreadUtil.runOnUiThread((Runnable)new Object(g)); 
    } 
  }
  
  public void onCatalystInstanceDestroy() {
    this.mRegistry.a();
    d d1 = this.mInteractionManager;
    d1.a.clear();
    d1.b.clear();
    synchronized (this.mRoots) {
      while (!this.mRoots.isEmpty()) {
        a a;
        int j = this.mRoots.size();
        g g = this.mRoots.get(0);
        ReactRootView reactRootView = g.a;
        if (reactRootView instanceof a) {
          a = (a)reactRootView;
          if (a.b != null) {
            a.b.a();
            a.b = null;
          } 
        } else {
          a.a();
        } 
        if (this.mRoots.size() < j)
          continue; 
        throw new IllegalStateException("Expected root helper to get unregistered while tearing down");
      } 
      super.onCatalystInstanceDestroy();
      return;
    } 
  }
  
  public void onStateChange(com.swmansion.gesturehandler.b paramb, int paramInt1, int paramInt2) {
    if (paramb.e < 0)
      return; 
    b b1 = findFactoryForHandler(paramb);
    EventDispatcher eventDispatcher = ((UIManagerModule)getReactApplicationContext().getNativeModule(UIManagerModule.class)).getEventDispatcher();
    i i2 = (i)i.a.acquire();
    i i1 = i2;
    if (i2 == null)
      i1 = new i(); 
    i1.a(paramb, paramInt1, paramInt2, b1);
    eventDispatcher.dispatchEvent(i1);
  }
  
  public void onTouchEvent(com.swmansion.gesturehandler.b paramb, MotionEvent paramMotionEvent) {
    if (paramb.e < 0)
      return; 
    if (paramb.g == 4) {
      b b1 = findFactoryForHandler(paramb);
      ((UIManagerModule)getReactApplicationContext().getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(b.a(paramb, b1));
    } 
  }
  
  public void registerRootHelper(g paramg) {
    synchronized (this.mRoots) {
      if (!this.mRoots.contains(paramg)) {
        this.mRoots.add(paramg);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("Root helper");
      stringBuilder.append(paramg);
      stringBuilder.append(" already registered");
      throw new IllegalStateException(stringBuilder.toString());
    } 
  }
  
  public void unregisterRootHelper(g paramg) {
    synchronized (this.mRoots) {
      this.mRoots.remove(paramg);
      return;
    } 
  }
  
  @ReactMethod
  public void updateGestureHandler(int paramInt, ReadableMap paramReadableMap) {
    com.swmansion.gesturehandler.b b1 = this.mRegistry.a(paramInt);
    if (b1 != null) {
      b<com.swmansion.gesturehandler.b> b2 = findFactoryForHandler(b1);
      if (b2 != null) {
        this.mInteractionManager.a(paramInt);
        this.mInteractionManager.a(b1, paramReadableMap);
        b2.a(b1, paramReadableMap);
      } 
    } 
  }
  
  static final class a extends b<com.swmansion.gesturehandler.a> {
    private a() {}
    
    public final Class<com.swmansion.gesturehandler.a> a() {
      return com.swmansion.gesturehandler.a.class;
    }
    
    public final String b() {
      return "FlingGestureHandler";
    }
  }
  
  static abstract class b<T extends com.swmansion.gesturehandler.b> implements c<T> {
    private b() {}
    
    public abstract T a(Context param1Context);
    
    public abstract Class<T> a();
    
    public void a(T param1T, ReadableMap param1ReadableMap) {
      if (param1ReadableMap.hasKey("shouldCancelWhenOutside"))
        param1T.a(param1ReadableMap.getBoolean("shouldCancelWhenOutside")); 
      if (param1ReadableMap.hasKey("enabled"))
        param1T.b(param1ReadableMap.getBoolean("enabled")); 
      if (param1ReadableMap.hasKey("hitSlop"))
        RNGestureHandlerModule.handleHitSlopProperty((com.swmansion.gesturehandler.b)param1T, param1ReadableMap); 
    }
    
    public void a(T param1T, WritableMap param1WritableMap) {
      param1WritableMap.putDouble("numberOfPointers", ((com.swmansion.gesturehandler.b)param1T).n);
    }
    
    public abstract String b();
  }
  
  static final class c extends b<com.swmansion.gesturehandler.g> {
    private c() {}
    
    public final Class<com.swmansion.gesturehandler.g> a() {
      return com.swmansion.gesturehandler.g.class;
    }
    
    public final String b() {
      return "LongPressGestureHandler";
    }
  }
  
  static final class d extends b<com.swmansion.gesturehandler.h> {
    private d() {}
    
    public final Class<com.swmansion.gesturehandler.h> a() {
      return com.swmansion.gesturehandler.h.class;
    }
    
    public final String b() {
      return "NativeViewGestureHandler";
    }
  }
  
  static final class e extends b<j> {
    private e() {}
    
    public final Class<j> a() {
      return j.class;
    }
    
    public final String b() {
      return "PanGestureHandler";
    }
  }
  
  static final class f extends b<k> {
    private f() {}
    
    public final Class<k> a() {
      return k.class;
    }
    
    public final String b() {
      return "PinchGestureHandler";
    }
  }
  
  static final class g extends b<n> {
    private g() {}
    
    public final Class<n> a() {
      return n.class;
    }
    
    public final String b() {
      return "RotationGestureHandler";
    }
  }
  
  static final class h extends b<o> {
    private h() {}
    
    public final Class<o> a() {
      return o.class;
    }
    
    public final String b() {
      return "TapGestureHandler";
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\RNGestureHandlerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */