package com.facebook.react.bridge;

import com.a;
import com.facebook.m.a;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaModuleWrapper {
  private final ArrayList<MethodDescriptor> mDescs;
  
  private final JSInstance mJSInstance;
  
  private final ArrayList<NativeModule.NativeMethod> mMethods;
  
  private final Class<? extends NativeModule> mModuleClass;
  
  private final ModuleHolder mModuleHolder;
  
  public JavaModuleWrapper(JSInstance paramJSInstance, Class<? extends NativeModule> paramClass, ModuleHolder paramModuleHolder) {
    this.mJSInstance = paramJSInstance;
    this.mModuleHolder = paramModuleHolder;
    this.mModuleClass = paramClass;
    this.mMethods = new ArrayList<NativeModule.NativeMethod>();
    this.mDescs = new ArrayList<MethodDescriptor>();
  }
  
  private void findMethods() {
    Class<? super NativeModule> clazz1;
    StringBuilder stringBuilder;
    a.a(0L, "findMethods");
    HashSet hashSet = new HashSet();
    Class<? extends NativeModule> clazz = this.mModuleClass;
    Class<? super NativeModule> clazz2 = clazz.getSuperclass();
    if (ReactModuleWithSpec.class.isAssignableFrom(clazz2))
      clazz1 = clazz2; 
    for (Method method : clazz1.getDeclaredMethods()) {
      ReactMethod reactMethod = method.<ReactMethod>getAnnotation(ReactMethod.class);
      if (reactMethod != null) {
        String str = method.getName();
        if (!hashSet.contains(str)) {
          MethodDescriptor methodDescriptor = new MethodDescriptor();
          JavaMethodWrapper javaMethodWrapper = new JavaMethodWrapper(this, method, reactMethod.isBlockingSynchronousMethod());
          methodDescriptor.name = str;
          methodDescriptor.type = javaMethodWrapper.getType();
          if (methodDescriptor.type == "sync") {
            methodDescriptor.signature = javaMethodWrapper.getSignature();
            methodDescriptor.method = method;
          } 
          this.mMethods.add(javaMethodWrapper);
          this.mDescs.add(methodDescriptor);
        } else {
          stringBuilder = new StringBuilder("Java Module ");
          stringBuilder.append(getName());
          stringBuilder.append(" method name already registered: ");
          stringBuilder.append(str);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } 
    } 
    if (IDynamicJavaMethodsFactory.class.isAssignableFrom((Class<?>)stringBuilder))
      try {
        Method method = stringBuilder.getDeclaredMethod("getDynamicMethods", new Class[0]);
        Field field = stringBuilder.getDeclaredField("DYNAMIC_METHODS");
        if (field != null) {
          Object object = field.get(null);
          if (object instanceof List)
            for (String str : object) {
              MethodDescriptor methodDescriptor = new MethodDescriptor();
              DynamicMethodWrapper dynamicMethodWrapper = new DynamicMethodWrapper(this, str);
              methodDescriptor.type = dynamicMethodWrapper.getType();
              methodDescriptor.name = str;
              methodDescriptor.method = method;
              this.mMethods.add(dynamicMethodWrapper);
              this.mDescs.add(methodDescriptor);
            }  
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    a.a(0L);
  }
  
  public NativeMap getConstants() {
    if (!this.mModuleHolder.getHasConstants())
      return null; 
    null = getName();
    ReactMarker.logMarker(ReactMarkerConstants.GET_CONSTANTS_START, null);
    BaseJavaModule baseJavaModule = getModule();
    a.a(0L, "module.getConstants");
    Map<String, Object> map = baseJavaModule.getConstants();
    a.a(0L);
    a.a(0L, "create WritableNativeMap");
    ReactMarker.logMarker(ReactMarkerConstants.CONVERT_CONSTANTS_START, null);
    try {
      return Arguments.makeNativeMap(map);
    } finally {
      ReactMarker.logMarker(ReactMarkerConstants.CONVERT_CONSTANTS_END);
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.GET_CONSTANTS_END);
    } 
  }
  
  public JSInstance getJSInstance() {
    return this.mJSInstance;
  }
  
  public List<MethodDescriptor> getMethodDescriptors() {
    if (this.mDescs.isEmpty())
      findMethods(); 
    return this.mDescs;
  }
  
  public BaseJavaModule getModule() {
    return (BaseJavaModule)this.mModuleHolder.getModule();
  }
  
  public String getName() {
    return this.mModuleHolder.getName();
  }
  
  public void invoke(int paramInt, ReadableNativeArray paramReadableNativeArray) {
    a.a("start invoke %d", new Object[] { Integer.valueOf(paramInt) });
    Iterator<MethodDescriptor> iterator = this.mDescs.iterator();
    String str;
    for (str = "methods:"; iterator.hasNext(); str = stringBuilder1.toString()) {
      MethodDescriptor methodDescriptor = iterator.next();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(methodDescriptor.method);
      str = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(":");
      str = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(methodDescriptor.name);
      str = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(";");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(this.mModuleClass.getName());
    ArrayList<NativeModule.NativeMethod> arrayList = this.mMethods;
    if (arrayList != null) {
      if (paramInt >= arrayList.size())
        return; 
      try {
        ((NativeModule.NativeMethod)this.mMethods.get(paramInt)).invoke(this.mJSInstance, paramReadableNativeArray);
        return;
      } catch (Exception exception) {
        throw exception;
      } 
    } 
  }
  
  public class MethodDescriptor {
    Method method;
    
    String name;
    
    String signature;
    
    String type;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaModuleWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */