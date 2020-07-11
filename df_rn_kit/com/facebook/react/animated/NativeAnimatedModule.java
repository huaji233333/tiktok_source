package com.facebook.react.animated;

import com.facebook.i.a.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModuleListener;
import java.util.ArrayList;
import java.util.Iterator;

@ReactModule(name = "NativeAnimatedModule")
public class NativeAnimatedModule extends ReactContextBaseJavaModule implements LifecycleEventListener, UIManagerModuleListener {
  public final GuardedFrameCallback mAnimatedFrameCallback;
  
  private NativeAnimatedNodesManager mNodesManager;
  
  private ArrayList<UIThreadOperation> mOperations = new ArrayList<UIThreadOperation>();
  
  private ArrayList<UIThreadOperation> mPreOperations = new ArrayList<UIThreadOperation>();
  
  public final ReactChoreographer mReactChoreographer = ReactChoreographer.getInstance();
  
  public NativeAnimatedModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.mAnimatedFrameCallback = new GuardedFrameCallback((ReactContext)paramReactApplicationContext) {
        public void doFrameGuarded(long param1Long) {
          NativeAnimatedNodesManager nativeAnimatedNodesManager = NativeAnimatedModule.this.getNodesManager();
          if (nativeAnimatedNodesManager.hasActiveAnimations())
            nativeAnimatedNodesManager.runUpdates(param1Long); 
          ((ReactChoreographer)a.b(NativeAnimatedModule.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, (ChoreographerCompat.FrameCallback)NativeAnimatedModule.this.mAnimatedFrameCallback);
        }
      };
  }
  
  private void clearFrameCallback() {
    ((ReactChoreographer)a.b(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, (ChoreographerCompat.FrameCallback)this.mAnimatedFrameCallback);
  }
  
  private void enqueueFrameCallback() {
    ((ReactChoreographer)a.b(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, (ChoreographerCompat.FrameCallback)this.mAnimatedFrameCallback);
  }
  
  @ReactMethod
  public void addAnimatedEventToView(final int viewTag, final String eventName, final ReadableMap eventMapping) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.addAnimatedEventToView(viewTag, eventName, eventMapping);
          }
        });
  }
  
  @ReactMethod
  public void connectAnimatedNodeToView(final int animatedNodeTag, final int viewTag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.connectAnimatedNodeToView(animatedNodeTag, viewTag);
          }
        });
  }
  
  @ReactMethod
  public void connectAnimatedNodes(final int parentNodeTag, final int childNodeTag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.connectAnimatedNodes(parentNodeTag, childNodeTag);
          }
        });
  }
  
  @ReactMethod
  public void createAnimatedNode(final int tag, final ReadableMap config) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.createAnimatedNode(tag, config);
          }
        });
  }
  
  @ReactMethod
  public void disconnectAnimatedNodeFromView(final int animatedNodeTag, final int viewTag) {
    this.mPreOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.restoreDefaultValues(animatedNodeTag, viewTag);
          }
        });
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.disconnectAnimatedNodeFromView(animatedNodeTag, viewTag);
          }
        });
  }
  
  @ReactMethod
  public void disconnectAnimatedNodes(final int parentNodeTag, final int childNodeTag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.disconnectAnimatedNodes(parentNodeTag, childNodeTag);
          }
        });
  }
  
  @ReactMethod
  public void dropAnimatedNode(final int tag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.dropAnimatedNode(tag);
          }
        });
  }
  
  @ReactMethod
  public void extractAnimatedNodeOffset(final int tag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.extractAnimatedNodeOffset(tag);
          }
        });
  }
  
  @ReactMethod
  public void flattenAnimatedNodeOffset(final int tag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.flattenAnimatedNodeOffset(tag);
          }
        });
  }
  
  public String getName() {
    return "NativeAnimatedModule";
  }
  
  public NativeAnimatedNodesManager getNodesManager() {
    if (this.mNodesManager == null)
      this.mNodesManager = new NativeAnimatedNodesManager((UIManagerModule)getReactApplicationContext().getNativeModule(UIManagerModule.class)); 
    return this.mNodesManager;
  }
  
  public void initialize() {
    ReactApplicationContext reactApplicationContext = getReactApplicationContext();
    UIManagerModule uIManagerModule = (UIManagerModule)reactApplicationContext.getNativeModule(UIManagerModule.class);
    reactApplicationContext.addLifecycleEventListener(this);
    uIManagerModule.addUIManagerListener(this);
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    clearFrameCallback();
  }
  
  public void onHostResume() {
    enqueueFrameCallback();
  }
  
  @ReactMethod
  public void removeAnimatedEventFromView(final int viewTag, final String eventName, final int animatedValueTag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.removeAnimatedEventFromView(viewTag, eventName, animatedValueTag);
          }
        });
  }
  
  @ReactMethod
  public void setAnimatedNodeOffset(final int tag, final double value) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.setAnimatedNodeOffset(tag, value);
          }
        });
  }
  
  @ReactMethod
  public void setAnimatedNodeValue(final int tag, final double value) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.setAnimatedNodeValue(tag, value);
          }
        });
  }
  
  public void setNodesManager(NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    this.mNodesManager = paramNativeAnimatedNodesManager;
  }
  
  @ReactMethod
  public void startAnimatingNode(final int animationId, final int animatedNodeTag, final ReadableMap animationConfig, final Callback endCallback) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.startAnimatingNode(animationId, animatedNodeTag, animationConfig, endCallback);
          }
        });
  }
  
  @ReactMethod
  public void startListeningToAnimatedNodeValue(final int tag) {
    final AnimatedNodeValueListener listener = new AnimatedNodeValueListener() {
        public void onValueUpdate(double param1Double) {
          WritableMap writableMap = Arguments.createMap();
          writableMap.putInt("tag", tag);
          writableMap.putDouble("value", param1Double);
          ((DeviceEventManagerModule.RCTDeviceEventEmitter)NativeAnimatedModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("onAnimatedValueUpdate", writableMap);
        }
      };
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.startListeningToAnimatedNodeValue(tag, listener);
          }
        });
  }
  
  @ReactMethod
  public void stopAnimation(final int animationId) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.stopAnimation(animationId);
          }
        });
  }
  
  @ReactMethod
  public void stopListeningToAnimatedNodeValue(final int tag) {
    this.mOperations.add(new UIThreadOperation() {
          public void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager) {
            param1NativeAnimatedNodesManager.stopListeningToAnimatedNodeValue(tag);
          }
        });
  }
  
  public void willDispatchViewUpdates(UIManagerModule paramUIManagerModule) {
    if (this.mOperations.isEmpty() && this.mPreOperations.isEmpty())
      return; 
    final ArrayList<UIThreadOperation> preOperations = this.mPreOperations;
    final ArrayList<UIThreadOperation> operations = this.mOperations;
    this.mPreOperations = new ArrayList<UIThreadOperation>();
    this.mOperations = new ArrayList<UIThreadOperation>();
    paramUIManagerModule.prependUIBlock(new UIBlock() {
          public void execute(NativeViewHierarchyManager param1NativeViewHierarchyManager) {
            NativeAnimatedNodesManager nativeAnimatedNodesManager = NativeAnimatedModule.this.getNodesManager();
            Iterator<NativeAnimatedModule.UIThreadOperation> iterator = preOperations.iterator();
            while (iterator.hasNext())
              ((NativeAnimatedModule.UIThreadOperation)iterator.next()).execute(nativeAnimatedNodesManager); 
          }
        });
    paramUIManagerModule.addUIBlock(new UIBlock() {
          public void execute(NativeViewHierarchyManager param1NativeViewHierarchyManager) {
            NativeAnimatedNodesManager nativeAnimatedNodesManager = NativeAnimatedModule.this.getNodesManager();
            Iterator<NativeAnimatedModule.UIThreadOperation> iterator = operations.iterator();
            while (iterator.hasNext())
              ((NativeAnimatedModule.UIThreadOperation)iterator.next()).execute(nativeAnimatedNodesManager); 
          }
        });
  }
  
  static interface UIThreadOperation {
    void execute(NativeAnimatedNodesManager param1NativeAnimatedNodesManager);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\NativeAnimatedModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */