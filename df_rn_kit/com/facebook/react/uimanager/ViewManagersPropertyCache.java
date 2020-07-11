package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class ViewManagersPropertyCache {
  private static final Map<Class, Map<String, PropSetter>> CLASS_PROPS_CACHE = (Map)new HashMap<Class<?>, Map<String, PropSetter>>();
  
  private static final Map<String, PropSetter> EMPTY_PROPS_MAP = new HashMap<String, PropSetter>();
  
  public static void clear() {
    CLASS_PROPS_CACHE.clear();
    EMPTY_PROPS_MAP.clear();
  }
  
  private static PropSetter createPropSetter(ReactProp paramReactProp, Method paramMethod, Class<?> paramClass) {
    if (paramClass == Dynamic.class)
      return new DynamicPropSetter(paramReactProp, paramMethod); 
    if (paramClass == boolean.class)
      return new BooleanPropSetter(paramReactProp, paramMethod, paramReactProp.defaultBoolean()); 
    if (paramClass == int.class)
      return new IntPropSetter(paramReactProp, paramMethod, paramReactProp.defaultInt()); 
    if (paramClass == float.class)
      return new FloatPropSetter(paramReactProp, paramMethod, paramReactProp.defaultFloat()); 
    if (paramClass == double.class)
      return new DoublePropSetter(paramReactProp, paramMethod, paramReactProp.defaultDouble()); 
    if (paramClass == String.class)
      return new StringPropSetter(paramReactProp, paramMethod); 
    if (paramClass == Boolean.class)
      return new BoxedBooleanPropSetter(paramReactProp, paramMethod); 
    if (paramClass == Integer.class)
      return new BoxedIntPropSetter(paramReactProp, paramMethod); 
    if (paramClass == ReadableArray.class)
      return new ArrayPropSetter(paramReactProp, paramMethod); 
    if (paramClass == ReadableMap.class)
      return new MapPropSetter(paramReactProp, paramMethod); 
    StringBuilder stringBuilder = new StringBuilder("Unrecognized type: ");
    stringBuilder.append(paramClass);
    stringBuilder.append(" for method: ");
    stringBuilder.append(paramMethod.getDeclaringClass().getName());
    stringBuilder.append("#");
    stringBuilder.append(paramMethod.getName());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private static void createPropSetters(ReactPropGroup paramReactPropGroup, Method paramMethod, Class<?> paramClass, Map<String, PropSetter> paramMap) {
    String[] arrayOfString = paramReactPropGroup.names();
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    int i = 0;
    if (paramClass == Dynamic.class) {
      while (i < arrayOfString.length) {
        paramMap.put(arrayOfString[i], new DynamicPropSetter(paramReactPropGroup, paramMethod, i));
        i++;
      } 
      return;
    } 
    if (paramClass == int.class) {
      for (i = bool1; i < arrayOfString.length; i++)
        paramMap.put(arrayOfString[i], new IntPropSetter(paramReactPropGroup, paramMethod, i, paramReactPropGroup.defaultInt())); 
      return;
    } 
    if (paramClass == float.class) {
      for (i = bool2; i < arrayOfString.length; i++)
        paramMap.put(arrayOfString[i], new FloatPropSetter(paramReactPropGroup, paramMethod, i, paramReactPropGroup.defaultFloat())); 
      return;
    } 
    if (paramClass == double.class) {
      for (i = bool3; i < arrayOfString.length; i++)
        paramMap.put(arrayOfString[i], new DoublePropSetter(paramReactPropGroup, paramMethod, i, paramReactPropGroup.defaultDouble())); 
      return;
    } 
    if (paramClass == Integer.class) {
      for (i = bool4; i < arrayOfString.length; i++)
        paramMap.put(arrayOfString[i], new BoxedIntPropSetter(paramReactPropGroup, paramMethod, i)); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Unrecognized type: ");
    stringBuilder.append(paramClass);
    stringBuilder.append(" for method: ");
    stringBuilder.append(paramMethod.getDeclaringClass().getName());
    stringBuilder.append("#");
    stringBuilder.append(paramMethod.getName());
    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
    throw runtimeException;
  }
  
  private static void extractPropSettersFromShadowNodeClassDefinition(Class<? extends ReactShadowNode> paramClass, Map<String, PropSetter> paramMap) {
    for (Method method : paramClass.getDeclaredMethods()) {
      StringBuilder stringBuilder;
      ReactProp reactProp = method.<ReactProp>getAnnotation(ReactProp.class);
      if (reactProp != null) {
        Class[] arrayOfClass = method.getParameterTypes();
        if (arrayOfClass.length == 1) {
          paramMap.put(reactProp.name(), createPropSetter(reactProp, method, arrayOfClass[0]));
        } else {
          stringBuilder = new StringBuilder("Wrong number of args for prop setter: ");
          stringBuilder.append(paramClass.getName());
          stringBuilder.append("#");
          stringBuilder.append(method.getName());
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
      ReactPropGroup reactPropGroup = method.<ReactPropGroup>getAnnotation(ReactPropGroup.class);
      if (reactPropGroup != null) {
        Class[] arrayOfClass = method.getParameterTypes();
        if (arrayOfClass.length == 2) {
          if (arrayOfClass[0] == int.class) {
            createPropSetters(reactPropGroup, method, arrayOfClass[1], (Map<String, PropSetter>)stringBuilder);
          } else {
            stringBuilder = new StringBuilder("Second argument should be property index: ");
            stringBuilder.append(paramClass.getName());
            stringBuilder.append("#");
            stringBuilder.append(method.getName());
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          stringBuilder = new StringBuilder("Wrong number of args for group prop setter: ");
          stringBuilder.append(paramClass.getName());
          stringBuilder.append("#");
          stringBuilder.append(method.getName());
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
    } 
  }
  
  private static void extractPropSettersFromViewManagerClassDefinition(Class<? extends ViewManager> paramClass, Map<String, PropSetter> paramMap) {
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    for (int i = 0; i < arrayOfMethod.length; i++) {
      StringBuilder stringBuilder;
      Method method = arrayOfMethod[i];
      ReactProp reactProp = method.<ReactProp>getAnnotation(ReactProp.class);
      if (reactProp != null) {
        Class[] arrayOfClass = method.getParameterTypes();
        if (arrayOfClass.length == 2) {
          if (View.class.isAssignableFrom(arrayOfClass[0])) {
            paramMap.put(reactProp.name(), createPropSetter(reactProp, method, arrayOfClass[1]));
          } else {
            stringBuilder = new StringBuilder("First param should be a view subclass to be updated: ");
            stringBuilder.append(paramClass.getName());
            stringBuilder.append("#");
            stringBuilder.append(method.getName());
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          stringBuilder = new StringBuilder("Wrong number of args for prop setter: ");
          stringBuilder.append(paramClass.getName());
          stringBuilder.append("#");
          stringBuilder.append(method.getName());
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
      ReactPropGroup reactPropGroup = method.<ReactPropGroup>getAnnotation(ReactPropGroup.class);
      if (reactPropGroup != null) {
        Class[] arrayOfClass = method.getParameterTypes();
        if (arrayOfClass.length == 3) {
          if (View.class.isAssignableFrom(arrayOfClass[0])) {
            if (arrayOfClass[1] == int.class) {
              createPropSetters(reactPropGroup, method, arrayOfClass[2], (Map<String, PropSetter>)stringBuilder);
            } else {
              stringBuilder = new StringBuilder("Second argument should be property index: ");
              stringBuilder.append(paramClass.getName());
              stringBuilder.append("#");
              stringBuilder.append(method.getName());
              throw new RuntimeException(stringBuilder.toString());
            } 
          } else {
            stringBuilder = new StringBuilder("First param should be a view subclass to be updated: ");
            stringBuilder.append(paramClass.getName());
            stringBuilder.append("#");
            stringBuilder.append(method.getName());
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          stringBuilder = new StringBuilder("Wrong number of args for group prop setter: ");
          stringBuilder.append(paramClass.getName());
          stringBuilder.append("#");
          stringBuilder.append(method.getName());
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
    } 
  }
  
  static Map<String, PropSetter> getNativePropSettersForShadowNodeClass(Class<? extends ReactShadowNode> paramClass) {
    Class[] arrayOfClass = paramClass.getInterfaces();
    int j = arrayOfClass.length;
    for (int i = 0; i < j; i++) {
      if (arrayOfClass[i] == ReactShadowNode.class)
        return EMPTY_PROPS_MAP; 
    } 
    Map<String, PropSetter> map = CLASS_PROPS_CACHE.get(paramClass);
    if (map != null)
      return map; 
    map = new HashMap<String, PropSetter>(getNativePropSettersForShadowNodeClass((Class)paramClass.getSuperclass()));
    extractPropSettersFromShadowNodeClassDefinition(paramClass, map);
    CLASS_PROPS_CACHE.put(paramClass, map);
    return map;
  }
  
  static Map<String, PropSetter> getNativePropSettersForViewManagerClass(Class<? extends ViewManager> paramClass) {
    if (paramClass == ViewManager.class)
      return EMPTY_PROPS_MAP; 
    Map<String, PropSetter> map = CLASS_PROPS_CACHE.get(paramClass);
    if (map != null)
      return map; 
    map = new HashMap<String, PropSetter>(getNativePropSettersForViewManagerClass((Class)paramClass.getSuperclass()));
    extractPropSettersFromViewManagerClassDefinition(paramClass, map);
    CLASS_PROPS_CACHE.put(paramClass, map);
    return map;
  }
  
  static Map<String, String> getNativePropsForView(Class<? extends ViewManager> paramClass, Class<? extends ReactShadowNode> paramClass1) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (PropSetter propSetter : getNativePropSettersForViewManagerClass(paramClass).values())
      hashMap.put(propSetter.getPropName(), propSetter.getPropType()); 
    for (PropSetter propSetter : getNativePropSettersForShadowNodeClass(paramClass1).values())
      hashMap.put(propSetter.getPropName(), propSetter.getPropType()); 
    return (Map)hashMap;
  }
  
  static class ArrayPropSetter extends PropSetter {
    public ArrayPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "Array", param1Method);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return param1ReactStylesDiffMap.getArray(this.mPropName);
    }
  }
  
  static class BooleanPropSetter extends PropSetter {
    private final boolean mDefaultValue;
    
    public BooleanPropSetter(ReactProp param1ReactProp, Method param1Method, boolean param1Boolean) {
      super(param1ReactProp, "boolean", param1Method);
      this.mDefaultValue = param1Boolean;
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return param1ReactStylesDiffMap.getBoolean(this.mPropName, this.mDefaultValue) ? Boolean.TRUE : Boolean.FALSE;
    }
  }
  
  static class BoxedBooleanPropSetter extends PropSetter {
    public BoxedBooleanPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "boolean", param1Method);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return !param1ReactStylesDiffMap.isNull(this.mPropName) ? (param1ReactStylesDiffMap.getBoolean(this.mPropName, false) ? Boolean.TRUE : Boolean.FALSE) : null;
    }
  }
  
  static class BoxedIntPropSetter extends PropSetter {
    public BoxedIntPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "number", param1Method);
    }
    
    public BoxedIntPropSetter(ReactPropGroup param1ReactPropGroup, Method param1Method, int param1Int) {
      super(param1ReactPropGroup, "number", param1Method, param1Int);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return !param1ReactStylesDiffMap.isNull(this.mPropName) ? Integer.valueOf(param1ReactStylesDiffMap.getInt(this.mPropName, 0)) : null;
    }
  }
  
  static class DoublePropSetter extends PropSetter {
    private final double mDefaultValue;
    
    public DoublePropSetter(ReactProp param1ReactProp, Method param1Method, double param1Double) {
      super(param1ReactProp, "number", param1Method);
      this.mDefaultValue = param1Double;
    }
    
    public DoublePropSetter(ReactPropGroup param1ReactPropGroup, Method param1Method, int param1Int, double param1Double) {
      super(param1ReactPropGroup, "number", param1Method, param1Int);
      this.mDefaultValue = param1Double;
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return Double.valueOf(param1ReactStylesDiffMap.getDouble(this.mPropName, this.mDefaultValue));
    }
  }
  
  static class DynamicPropSetter extends PropSetter {
    public DynamicPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "mixed", param1Method);
    }
    
    public DynamicPropSetter(ReactPropGroup param1ReactPropGroup, Method param1Method, int param1Int) {
      super(param1ReactPropGroup, "mixed", param1Method, param1Int);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return param1ReactStylesDiffMap.getDynamic(this.mPropName);
    }
  }
  
  static class FloatPropSetter extends PropSetter {
    private final float mDefaultValue;
    
    public FloatPropSetter(ReactProp param1ReactProp, Method param1Method, float param1Float) {
      super(param1ReactProp, "number", param1Method);
      this.mDefaultValue = param1Float;
    }
    
    public FloatPropSetter(ReactPropGroup param1ReactPropGroup, Method param1Method, int param1Int, float param1Float) {
      super(param1ReactPropGroup, "number", param1Method, param1Int);
      this.mDefaultValue = param1Float;
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return Float.valueOf(param1ReactStylesDiffMap.getFloat(this.mPropName, this.mDefaultValue));
    }
  }
  
  static class IntPropSetter extends PropSetter {
    private final int mDefaultValue;
    
    public IntPropSetter(ReactProp param1ReactProp, Method param1Method, int param1Int) {
      super(param1ReactProp, "number", param1Method);
      this.mDefaultValue = param1Int;
    }
    
    public IntPropSetter(ReactPropGroup param1ReactPropGroup, Method param1Method, int param1Int1, int param1Int2) {
      super(param1ReactPropGroup, "number", param1Method, param1Int1);
      this.mDefaultValue = param1Int2;
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return Integer.valueOf(param1ReactStylesDiffMap.getInt(this.mPropName, this.mDefaultValue));
    }
  }
  
  static class MapPropSetter extends PropSetter {
    public MapPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "Map", param1Method);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return param1ReactStylesDiffMap.getMap(this.mPropName);
    }
  }
  
  static abstract class PropSetter {
    private static final Object[] SHADOW_ARGS = new Object[1];
    
    private static final Object[] SHADOW_GROUP_ARGS = new Object[2];
    
    private static final Object[] VIEW_MGR_ARGS = new Object[2];
    
    private static final Object[] VIEW_MGR_GROUP_ARGS = new Object[3];
    
    protected final Integer mIndex;
    
    protected final String mPropName;
    
    protected final String mPropType;
    
    protected final Method mSetter;
    
    static {
    
    }
    
    private PropSetter(ReactProp param1ReactProp, String param1String, Method param1Method) {
      this.mPropName = param1ReactProp.name();
      if (!"__default_type__".equals(param1ReactProp.customType()))
        param1String = param1ReactProp.customType(); 
      this.mPropType = param1String;
      this.mSetter = param1Method;
      this.mIndex = null;
    }
    
    private PropSetter(ReactPropGroup param1ReactPropGroup, String param1String, Method param1Method, int param1Int) {
      this.mPropName = param1ReactPropGroup.names()[param1Int];
      if (!"__default_type__".equals(param1ReactPropGroup.customType()))
        param1String = param1ReactPropGroup.customType(); 
      this.mPropType = param1String;
      this.mSetter = param1Method;
      this.mIndex = Integer.valueOf(param1Int);
    }
    
    protected abstract Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap);
    
    public String getPropName() {
      return this.mPropName;
    }
    
    public String getPropType() {
      return this.mPropType;
    }
    
    public void updateShadowNodeProp(ReactShadowNode param1ReactShadowNode, ReactStylesDiffMap param1ReactStylesDiffMap) {
      try {
        return;
      } finally {
        param1ReactStylesDiffMap = null;
        StringBuilder stringBuilder = new StringBuilder("Error while updating prop ");
        stringBuilder.append(this.mPropName);
        a.b(ViewManager.class, stringBuilder.toString(), (Throwable)param1ReactStylesDiffMap);
        stringBuilder = new StringBuilder("Error while updating property '");
        stringBuilder.append(this.mPropName);
        stringBuilder.append("' in shadow node of type: ");
        stringBuilder.append(param1ReactShadowNode.getViewClass());
      } 
    }
    
    public void updateViewProp(ViewManager param1ViewManager, View param1View, ReactStylesDiffMap param1ReactStylesDiffMap) {
      try {
        return;
      } finally {
        param1View = null;
        StringBuilder stringBuilder = new StringBuilder("Error while updating prop ");
        stringBuilder.append(this.mPropName);
        a.b(ViewManager.class, stringBuilder.toString(), (Throwable)param1View);
        stringBuilder = new StringBuilder("Error while updating property '");
        stringBuilder.append(this.mPropName);
        stringBuilder.append("' of a view managed by: ");
        stringBuilder.append(param1ViewManager.getName());
      } 
    }
  }
  
  static class StringPropSetter extends PropSetter {
    public StringPropSetter(ReactProp param1ReactProp, Method param1Method) {
      super(param1ReactProp, "String", param1Method);
    }
    
    protected Object extractProperty(ReactStylesDiffMap param1ReactStylesDiffMap) {
      return param1ReactStylesDiffMap.getString(this.mPropName);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewManagersPropertyCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */