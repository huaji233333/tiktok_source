package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import java.util.ArrayList;
import java.util.List;

class TransformAnimatedNode extends AnimatedNode {
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  private final List<TransformConfig> mTransformConfigs;
  
  TransformAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    ReadableArray readableArray = paramReadableMap.getArray("transforms");
    this.mTransformConfigs = new ArrayList<TransformConfig>(readableArray.size());
    for (int i = 0; i < readableArray.size(); i++) {
      ReadableMap readableMap = readableArray.getMap(i);
      String str = readableMap.getString("property");
      if (readableMap.getString("type").equals("animated")) {
        AnimatedTransformConfig animatedTransformConfig = new AnimatedTransformConfig();
        animatedTransformConfig.mProperty = str;
        animatedTransformConfig.mNodeTag = readableMap.getInt("nodeTag");
        this.mTransformConfigs.add(animatedTransformConfig);
      } else {
        StaticTransformConfig staticTransformConfig = new StaticTransformConfig();
        staticTransformConfig.mProperty = str;
        staticTransformConfig.mValue = readableMap.getDoubleFromDeg("value");
        this.mTransformConfigs.add(staticTransformConfig);
      } 
    } 
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
  }
  
  public void collectViewUpdates(JavaOnlyMap paramJavaOnlyMap) {
    StringBuilder stringBuilder;
    ArrayList<JavaOnlyMap> arrayList = new ArrayList(this.mTransformConfigs.size());
    for (TransformConfig transformConfig : this.mTransformConfigs) {
      double d;
      if (transformConfig instanceof AnimatedTransformConfig) {
        int i = ((AnimatedTransformConfig)transformConfig).mNodeTag;
        AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(i);
        if (animatedNode != null) {
          if (animatedNode instanceof ValueAnimatedNode) {
            d = ((ValueAnimatedNode)animatedNode).getValue();
          } else {
            stringBuilder = new StringBuilder("Unsupported type of node used as a transform child node ");
            stringBuilder.append(animatedNode.getClass());
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
        } else {
          throw new IllegalArgumentException("Mapped style node does not exists");
        } 
      } else {
        d = ((StaticTransformConfig)transformConfig).mValue;
      } 
      arrayList.add(JavaOnlyMap.of(new Object[] { transformConfig.mProperty, Double.valueOf(d) }));
    } 
    stringBuilder.putArray("transform", (WritableArray)JavaOnlyArray.from(arrayList));
  }
  
  class AnimatedTransformConfig extends TransformConfig {
    public int mNodeTag;
    
    private AnimatedTransformConfig() {}
  }
  
  class StaticTransformConfig extends TransformConfig {
    public double mValue;
    
    private StaticTransformConfig() {}
  }
  
  class TransformConfig {
    public String mProperty;
    
    private TransformConfig() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\TransformAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */