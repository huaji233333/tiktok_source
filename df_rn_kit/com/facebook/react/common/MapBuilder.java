package com.facebook.react.common;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
  public static <K, V> Builder<K, V> builder() {
    return new Builder<K, V>();
  }
  
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
  }
  
  public static <K, V> Map<K, V> of() {
    return newHashMap();
  }
  
  public static <K, V> Map<K, V> of(K paramK, V paramV) {
    Map<?, ?> map = of();
    map.put(paramK, paramV);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    map.put(paramK3, paramV3);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3, K paramK4, V paramV4) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    map.put(paramK3, paramV3);
    map.put(paramK4, paramV4);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3, K paramK4, V paramV4, K paramK5, V paramV5) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    map.put(paramK3, paramV3);
    map.put(paramK4, paramV4);
    map.put(paramK5, paramV5);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3, K paramK4, V paramV4, K paramK5, V paramV5, K paramK6, V paramV6) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    map.put(paramK3, paramV3);
    map.put(paramK4, paramV4);
    map.put(paramK5, paramV5);
    map.put(paramK6, paramV6);
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3, K paramK4, V paramV4, K paramK5, V paramV5, K paramK6, V paramV6, K paramK7, V paramV7) {
    Map<?, ?> map = of();
    map.put(paramK1, paramV1);
    map.put(paramK2, paramV2);
    map.put(paramK3, paramV3);
    map.put(paramK4, paramV4);
    map.put(paramK5, paramV5);
    map.put(paramK6, paramV6);
    map.put(paramK7, paramV7);
    return (Map)map;
  }
  
  public static final class Builder<K, V> {
    private Map mMap = MapBuilder.newHashMap();
    
    private boolean mUnderConstruction = true;
    
    private Builder() {}
    
    public final Map<K, V> build() {
      if (this.mUnderConstruction) {
        this.mUnderConstruction = false;
        return this.mMap;
      } 
      throw new IllegalStateException("Underlying map has already been built");
    }
    
    public final Builder<K, V> put(K param1K, V param1V) {
      if (this.mUnderConstruction) {
        this.mMap.put(param1K, param1V);
        return this;
      } 
      throw new IllegalStateException("Underlying map has already been built");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\MapBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */