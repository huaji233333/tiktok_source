package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.common.e.a;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Map;

public class ViewManagerPropertyUpdater {
  private static final Map<Class<?>, ShadowNodeSetter<?>> SHADOW_NODE_SETTER_MAP;
  
  private static final Map<Class<?>, ViewManagerSetter<?, ?>> VIEW_MANAGER_SETTER_MAP = new HashMap<Class<?>, ViewManagerSetter<?, ?>>();
  
  static {
    SHADOW_NODE_SETTER_MAP = new HashMap<Class<?>, ShadowNodeSetter<?>>();
  }
  
  public static void clear() {
    ViewManagersPropertyCache.clear();
    VIEW_MANAGER_SETTER_MAP.clear();
    SHADOW_NODE_SETTER_MAP.clear();
  }
  
  private static <T> T findGeneratedSetter(Class<?> paramClass) {
    StringBuilder stringBuilder;
    String str = paramClass.getName();
    try {
      null = new StringBuilder();
      null.append(str);
      null.append("$$PropsSetter");
      return (T)Class.forName(null.toString()).newInstance();
    } catch (ClassNotFoundException classNotFoundException) {
      stringBuilder = new StringBuilder("Could not find generated setter for ");
      stringBuilder.append(paramClass);
      a.b("ViewManagerPropertyUpdater", stringBuilder.toString());
      return null;
    } catch (InstantiationException instantiationException) {
      StringBuilder stringBuilder1 = new StringBuilder("Unable to instantiate methods getter for ");
      stringBuilder1.append((String)stringBuilder);
      throw new RuntimeException(stringBuilder1.toString(), instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder1 = new StringBuilder("Unable to instantiate methods getter for ");
      stringBuilder1.append((String)stringBuilder);
      throw new RuntimeException(stringBuilder1.toString(), illegalAccessException);
    } 
  }
  
  private static <T extends ViewManager, V extends View> ViewManagerSetter<T, V> findManagerSetter(Class<? extends ViewManager> paramClass) {
    ViewManagerSetter<ViewManager, View> viewManagerSetter2 = (ViewManagerSetter)VIEW_MANAGER_SETTER_MAP.get(paramClass);
    ViewManagerSetter<ViewManager, View> viewManagerSetter1 = viewManagerSetter2;
    if (viewManagerSetter2 == null) {
      viewManagerSetter2 = findGeneratedSetter(paramClass);
      viewManagerSetter1 = viewManagerSetter2;
      if (viewManagerSetter2 == null)
        viewManagerSetter1 = new FallbackViewManagerSetter<ViewManager, View>(paramClass); 
      VIEW_MANAGER_SETTER_MAP.put(paramClass, viewManagerSetter1);
    } 
    return (ViewManagerSetter)viewManagerSetter1;
  }
  
  private static <T extends ReactShadowNode> ShadowNodeSetter<T> findNodeSetter(Class<? extends ReactShadowNode> paramClass) {
    ShadowNodeSetter<ReactShadowNode> shadowNodeSetter2 = (ShadowNodeSetter)SHADOW_NODE_SETTER_MAP.get(paramClass);
    ShadowNodeSetter<ReactShadowNode> shadowNodeSetter1 = shadowNodeSetter2;
    if (shadowNodeSetter2 == null) {
      shadowNodeSetter2 = findGeneratedSetter(paramClass);
      shadowNodeSetter1 = shadowNodeSetter2;
      if (shadowNodeSetter2 == null)
        shadowNodeSetter1 = new FallbackShadowNodeSetter<ReactShadowNode>(paramClass); 
      SHADOW_NODE_SETTER_MAP.put(paramClass, shadowNodeSetter1);
    } 
    return (ShadowNodeSetter)shadowNodeSetter1;
  }
  
  public static Map<String, String> getNativeProps(Class<? extends ViewManager> paramClass, Class<? extends ReactShadowNode> paramClass1) {
    // Byte code:
    //   0: ldc com/facebook/react/uimanager/ViewManagerPropertyUpdater
    //   2: monitorenter
    //   3: new java/util/HashMap
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: astore_2
    //   11: aload_0
    //   12: invokestatic findManagerSetter : (Ljava/lang/Class;)Lcom/facebook/react/uimanager/ViewManagerPropertyUpdater$ViewManagerSetter;
    //   15: aload_2
    //   16: invokeinterface getProperties : (Ljava/util/Map;)V
    //   21: aload_1
    //   22: invokestatic findNodeSetter : (Ljava/lang/Class;)Lcom/facebook/react/uimanager/ViewManagerPropertyUpdater$ShadowNodeSetter;
    //   25: aload_2
    //   26: invokeinterface getProperties : (Ljava/util/Map;)V
    //   31: ldc com/facebook/react/uimanager/ViewManagerPropertyUpdater
    //   33: monitorexit
    //   34: aload_2
    //   35: areturn
    //   36: astore_0
    //   37: ldc com/facebook/react/uimanager/ViewManagerPropertyUpdater
    //   39: monitorexit
    //   40: aload_0
    //   41: athrow
    // Exception table:
    //   from	to	target	type
    //   3	31	36	finally
  }
  
  public static <T extends ReactShadowNode> void updateProps(T paramT, ReactStylesDiffMap paramReactStylesDiffMap) {
    ShadowNodeSetter<ReactShadowNode> shadowNodeSetter = findNodeSetter((Class)paramT.getClass());
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReactStylesDiffMap.mBackingMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey())
      shadowNodeSetter.setProperty((ReactShadowNode)paramT, readableMapKeySetIterator.nextKey(), paramReactStylesDiffMap); 
  }
  
  public static <T extends ViewManager, V extends View> void updateProps(T paramT, V paramV, ReactStylesDiffMap paramReactStylesDiffMap) {
    ViewManagerSetter<ViewManager, View> viewManagerSetter = findManagerSetter((Class)paramT.getClass());
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReactStylesDiffMap.mBackingMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey())
      viewManagerSetter.setProperty((ViewManager)paramT, (View)paramV, readableMapKeySetIterator.nextKey(), paramReactStylesDiffMap); 
  }
  
  static class FallbackShadowNodeSetter<T extends ReactShadowNode> implements ShadowNodeSetter<T> {
    private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;
    
    private FallbackShadowNodeSetter(Class<? extends ReactShadowNode> param1Class) {
      this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForShadowNodeClass(param1Class);
    }
    
    public void getProperties(Map<String, String> param1Map) {
      for (ViewManagersPropertyCache.PropSetter propSetter : this.mPropSetters.values())
        param1Map.put(propSetter.getPropName(), propSetter.getPropType()); 
    }
    
    public void setProperty(ReactShadowNode param1ReactShadowNode, String param1String, ReactStylesDiffMap param1ReactStylesDiffMap) {
      ViewManagersPropertyCache.PropSetter propSetter = this.mPropSetters.get(param1String);
      if (propSetter != null)
        propSetter.updateShadowNodeProp(param1ReactShadowNode, param1ReactStylesDiffMap); 
    }
  }
  
  static class FallbackViewManagerSetter<T extends ViewManager, V extends View> implements ViewManagerSetter<T, V> {
    private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;
    
    private FallbackViewManagerSetter(Class<? extends ViewManager> param1Class) {
      this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForViewManagerClass(param1Class);
    }
    
    public void getProperties(Map<String, String> param1Map) {
      for (ViewManagersPropertyCache.PropSetter propSetter : this.mPropSetters.values())
        param1Map.put(propSetter.getPropName(), propSetter.getPropType()); 
    }
    
    public void setProperty(T param1T, V param1V, String param1String, ReactStylesDiffMap param1ReactStylesDiffMap) {
      ViewManagersPropertyCache.PropSetter propSetter = this.mPropSetters.get(param1String);
      if (propSetter != null)
        propSetter.updateViewProp((ViewManager)param1T, (View)param1V, param1ReactStylesDiffMap); 
    }
  }
  
  public static interface Settable {
    void getProperties(Map<String, String> param1Map);
  }
  
  public static interface ShadowNodeSetter<T extends ReactShadowNode> extends Settable {
    void setProperty(T param1T, String param1String, ReactStylesDiffMap param1ReactStylesDiffMap);
  }
  
  public static interface ViewManagerSetter<T extends ViewManager, V extends View> extends Settable {
    void setProperty(T param1T, V param1V, String param1String, ReactStylesDiffMap param1ReactStylesDiffMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewManagerPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */