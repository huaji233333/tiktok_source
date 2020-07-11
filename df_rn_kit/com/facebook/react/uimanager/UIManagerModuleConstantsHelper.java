package com.facebook.react.uimanager;

import com.facebook.m.a;
import com.facebook.react.common.MapBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UIManagerModuleConstantsHelper {
  static Map<String, Object> createConstants(UIManagerModule.ViewManagerResolver paramViewManagerResolver) {
    Map<String, Object> map = UIManagerModuleConstants.getConstants();
    map.put("ViewManagerNames", paramViewManagerResolver.getViewManagerNames());
    return map;
  }
  
  static Map<String, Object> createConstants(List<ViewManager> paramList, Map<String, Object> paramMap1, Map<String, Object> paramMap2) {
    Map<String, Object> map = UIManagerModuleConstants.getConstants();
    Map<? extends String, ?> map1 = UIManagerModuleConstants.getBubblingEventTypeConstants();
    Map<? extends String, ?> map2 = UIManagerModuleConstants.getDirectEventTypeConstants();
    if (paramMap1 != null)
      paramMap1.putAll(map1); 
    if (paramMap2 != null)
      paramMap2.putAll(map2); 
    for (ViewManager viewManager : paramList) {
      String str = viewManager.getName();
      try {
        Map<String, Object> map3 = createConstantsForViewManager(viewManager, null, null, paramMap1, paramMap2);
        if (!map3.isEmpty())
          map.put(str, map3); 
      } finally {
        a.a(0L);
      } 
    } 
    map.put("genericBubblingEventTypes", map1);
    map.put("genericDirectEventTypes", map2);
    return map;
  }
  
  static Map<String, Object> createConstantsForViewManager(ViewManager paramViewManager, Map<String, Object> paramMap1, Map<String, Object> paramMap2, Map paramMap3, Map paramMap4) {
    HashMap<String, Map<String, Object>> hashMap = MapBuilder.newHashMap();
    Map<String, Object> map1 = paramViewManager.getExportedCustomBubblingEventTypeConstants();
    if (map1 != null) {
      recursiveMerge(paramMap3, map1);
      recursiveMerge(map1, paramMap1);
      hashMap.put("bubblingEventTypes", map1);
    } else if (paramMap1 != null) {
      hashMap.put("bubblingEventTypes", paramMap1);
    } 
    paramMap1 = paramViewManager.getExportedCustomDirectEventTypeConstants();
    if (paramMap1 != null) {
      recursiveMerge(paramMap4, paramMap1);
      recursiveMerge(paramMap1, paramMap2);
      hashMap.put("directEventTypes", paramMap1);
    } else if (paramMap2 != null) {
      hashMap.put("directEventTypes", paramMap2);
    } 
    paramMap1 = paramViewManager.getExportedViewConstants();
    if (paramMap1 != null)
      hashMap.put("Constants", paramMap1); 
    paramMap1 = (Map)paramViewManager.getCommandsMap();
    if (paramMap1 != null)
      hashMap.put("Commands", paramMap1); 
    Map<String, String> map = paramViewManager.getNativeProps();
    if (!map.isEmpty())
      hashMap.put("NativeProps", map); 
    return (Map)hashMap;
  }
  
  static Map<String, Object> getDefaultExportableEventTypes() {
    return MapBuilder.of("bubblingEventTypes", UIManagerModuleConstants.getBubblingEventTypeConstants(), "directEventTypes", UIManagerModuleConstants.getDirectEventTypeConstants());
  }
  
  private static void recursiveMerge(Map paramMap1, Map paramMap2) {
    if (paramMap1 != null && paramMap2 != null) {
      if (paramMap2.isEmpty())
        return; 
      for (Object object : paramMap2.keySet()) {
        Object object1 = paramMap2.get(object);
        Object object2 = paramMap1.get(object);
        if (object2 != null && object1 instanceof Map && object2 instanceof Map) {
          recursiveMerge((Map)object2, (Map)object1);
          continue;
        } 
        paramMap1.put(object, object1);
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\UIManagerModuleConstantsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */