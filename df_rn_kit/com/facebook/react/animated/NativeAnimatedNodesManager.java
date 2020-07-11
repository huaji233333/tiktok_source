package com.facebook.react.animated;

import android.util.SparseArray;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

class NativeAnimatedNodesManager implements EventDispatcherListener {
  private final SparseArray<AnimationDriver> mActiveAnimations = new SparseArray();
  
  private int mAnimatedGraphBFSColor = 0;
  
  private final SparseArray<AnimatedNode> mAnimatedNodes = new SparseArray();
  
  private final UIManagerModule.CustomEventNamesResolver mCustomEventNamesResolver;
  
  private final Map<String, List<EventAnimationDriver>> mEventDrivers = new HashMap<String, List<EventAnimationDriver>>();
  
  private final List<AnimatedNode> mRunUpdateNodeList = new LinkedList<AnimatedNode>();
  
  private final UIImplementation mUIImplementation;
  
  private final SparseArray<AnimatedNode> mUpdatedNodes = new SparseArray();
  
  public NativeAnimatedNodesManager(UIManagerModule paramUIManagerModule) {
    this.mUIImplementation = paramUIManagerModule.getUIImplementation();
    paramUIManagerModule.getEventDispatcher().addListener(this);
    this.mCustomEventNamesResolver = paramUIManagerModule.getDirectEventNamesResolver();
  }
  
  private void stopAnimationsForNode(AnimatedNode paramAnimatedNode) {
    for (int i = 0; i < this.mActiveAnimations.size(); i = j + 1) {
      AnimationDriver animationDriver = (AnimationDriver)this.mActiveAnimations.valueAt(i);
      int j = i;
      if (paramAnimatedNode.equals(animationDriver.mAnimatedValue)) {
        if (animationDriver.mEndCallback != null) {
          WritableMap writableMap = Arguments.createMap();
          writableMap.putBoolean("finished", false);
          animationDriver.mEndCallback.invoke(new Object[] { writableMap });
        } 
        this.mActiveAnimations.removeAt(i);
        j = i - 1;
      } 
    } 
  }
  
  private void updateNodes(List<AnimatedNode> paramList) {
    int k;
    int i = ++this.mAnimatedGraphBFSColor;
    if (i == 0)
      this.mAnimatedGraphBFSColor = i + 1; 
    ArrayDeque<AnimatedNode> arrayDeque = new ArrayDeque();
    Iterator<AnimatedNode> iterator2 = paramList.iterator();
    int j = 0;
    while (true) {
      i = j;
      if (iterator2.hasNext()) {
        AnimatedNode animatedNode = iterator2.next();
        i = animatedNode.mBFSColor;
        k = this.mAnimatedGraphBFSColor;
        if (i != k) {
          animatedNode.mBFSColor = k;
          j++;
          arrayDeque.add(animatedNode);
        } 
        continue;
      } 
      break;
    } 
    while (!arrayDeque.isEmpty()) {
      AnimatedNode animatedNode = arrayDeque.poll();
      if (animatedNode.mChildren != null) {
        j = 0;
        while (j < animatedNode.mChildren.size()) {
          AnimatedNode animatedNode1 = animatedNode.mChildren.get(j);
          animatedNode1.mActiveIncomingNodes++;
          int m = animatedNode1.mBFSColor;
          int n = this.mAnimatedGraphBFSColor;
          k = i;
          if (m != n) {
            animatedNode1.mBFSColor = n;
            k = i + 1;
            arrayDeque.add(animatedNode1);
          } 
          j++;
          i = k;
        } 
      } 
    } 
    j = ++this.mAnimatedGraphBFSColor;
    if (j == 0)
      this.mAnimatedGraphBFSColor = j + 1; 
    Iterator<AnimatedNode> iterator1 = paramList.iterator();
    j = 0;
    while (true) {
      k = j;
      if (iterator1.hasNext()) {
        AnimatedNode animatedNode = iterator1.next();
        if (animatedNode.mActiveIncomingNodes == 0) {
          k = animatedNode.mBFSColor;
          int m = this.mAnimatedGraphBFSColor;
          if (k != m) {
            animatedNode.mBFSColor = m;
            j++;
            arrayDeque.add(animatedNode);
          } 
        } 
        continue;
      } 
      break;
    } 
    while (!arrayDeque.isEmpty()) {
      AnimatedNode animatedNode = arrayDeque.poll();
      animatedNode.update();
      if (animatedNode instanceof PropsAnimatedNode)
        try {
          ((PropsAnimatedNode)animatedNode).updateView();
        } catch (IllegalViewOperationException illegalViewOperationException) {
          a.c("ReactNative", "Native animation workaround, frame lost as result of race condition", (Throwable)illegalViewOperationException);
        }  
      if (animatedNode instanceof ValueAnimatedNode)
        ((ValueAnimatedNode)animatedNode).onValueUpdate(); 
      if (animatedNode.mChildren != null) {
        j = k;
        k = 0;
        while (k < animatedNode.mChildren.size()) {
          AnimatedNode animatedNode1 = animatedNode.mChildren.get(k);
          animatedNode1.mActiveIncomingNodes--;
          int m = j;
          if (animatedNode1.mBFSColor != this.mAnimatedGraphBFSColor) {
            m = j;
            if (animatedNode1.mActiveIncomingNodes == 0) {
              animatedNode1.mBFSColor = this.mAnimatedGraphBFSColor;
              m = j + 1;
              arrayDeque.add(animatedNode1);
            } 
          } 
          k++;
          j = m;
        } 
        k = j;
      } 
    } 
    if (i == k)
      return; 
    StringBuilder stringBuilder = new StringBuilder("Looks like animated nodes graph has cycles, there are ");
    stringBuilder.append(i);
    stringBuilder.append(" but toposort visited only ");
    stringBuilder.append(k);
    IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
    throw illegalStateException;
  }
  
  public void addAnimatedEventToView(int paramInt, String paramString, ReadableMap paramReadableMap) {
    int i = paramReadableMap.getInt("animatedValueTag");
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(i);
    if (animatedNode != null) {
      if (animatedNode instanceof ValueAnimatedNode) {
        ReadableArray readableArray = paramReadableMap.getArray("nativeEventPath");
        ArrayList<String> arrayList1 = new ArrayList(readableArray.size());
        for (i = 0; i < readableArray.size(); i++)
          arrayList1.add(readableArray.getString(i)); 
        EventAnimationDriver eventAnimationDriver = new EventAnimationDriver(arrayList1, (ValueAnimatedNode)animatedNode);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(paramInt);
        stringBuilder2.append(paramString);
        paramString = stringBuilder2.toString();
        if (this.mEventDrivers.containsKey(paramString)) {
          ((List<EventAnimationDriver>)this.mEventDrivers.get(paramString)).add(eventAnimationDriver);
          return;
        } 
        ArrayList<EventAnimationDriver> arrayList = new ArrayList(1);
        arrayList.add(eventAnimationDriver);
        this.mEventDrivers.put(paramString, arrayList);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node connected to event should beof type ");
      stringBuilder1.append(ValueAnimatedNode.class.getName());
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(i);
    stringBuilder.append(" does not exists");
    JSApplicationIllegalArgumentException jSApplicationIllegalArgumentException = new JSApplicationIllegalArgumentException(stringBuilder.toString());
    throw jSApplicationIllegalArgumentException;
  }
  
  public void connectAnimatedNodeToView(int paramInt1, int paramInt2) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt1);
    if (animatedNode != null) {
      if (animatedNode instanceof PropsAnimatedNode) {
        ((PropsAnimatedNode)animatedNode).connectToView(paramInt2);
        this.mUpdatedNodes.put(paramInt1, animatedNode);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node connected to view should beof type ");
      stringBuilder1.append(PropsAnimatedNode.class.getName());
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void connectAnimatedNodes(int paramInt1, int paramInt2) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt1);
    if (animatedNode != null) {
      AnimatedNode animatedNode1 = (AnimatedNode)this.mAnimatedNodes.get(paramInt2);
      if (animatedNode1 != null) {
        animatedNode.addChild(animatedNode1);
        this.mUpdatedNodes.put(paramInt2, animatedNode1);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node with tag ");
      stringBuilder1.append(paramInt2);
      stringBuilder1.append(" does not exists");
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void createAnimatedNode(int paramInt, ReadableMap paramReadableMap) {
    if (this.mAnimatedNodes.get(paramInt) == null) {
      StyleAnimatedNode styleAnimatedNode;
      StringBuilder stringBuilder1;
      String str = paramReadableMap.getString("type");
      if ("style".equals(str)) {
        styleAnimatedNode = new StyleAnimatedNode(paramReadableMap, this);
      } else {
        ValueAnimatedNode valueAnimatedNode;
        if ("value".equals(str)) {
          valueAnimatedNode = new ValueAnimatedNode((ReadableMap)styleAnimatedNode);
        } else {
          PropsAnimatedNode propsAnimatedNode;
          if ("props".equals(str)) {
            propsAnimatedNode = new PropsAnimatedNode((ReadableMap)valueAnimatedNode, this, this.mUIImplementation);
          } else {
            InterpolationAnimatedNode interpolationAnimatedNode;
            if ("interpolation".equals(str)) {
              interpolationAnimatedNode = new InterpolationAnimatedNode((ReadableMap)propsAnimatedNode);
            } else {
              AdditionAnimatedNode additionAnimatedNode;
              if ("addition".equals(str)) {
                additionAnimatedNode = new AdditionAnimatedNode((ReadableMap)interpolationAnimatedNode, this);
              } else {
                DivisionAnimatedNode divisionAnimatedNode;
                if ("division".equals(str)) {
                  divisionAnimatedNode = new DivisionAnimatedNode((ReadableMap)additionAnimatedNode, this);
                } else {
                  MultiplicationAnimatedNode multiplicationAnimatedNode;
                  if ("multiplication".equals(str)) {
                    multiplicationAnimatedNode = new MultiplicationAnimatedNode((ReadableMap)divisionAnimatedNode, this);
                  } else {
                    ModulusAnimatedNode modulusAnimatedNode;
                    if ("modulus".equals(str)) {
                      modulusAnimatedNode = new ModulusAnimatedNode((ReadableMap)multiplicationAnimatedNode, this);
                    } else {
                      DiffClampAnimatedNode diffClampAnimatedNode;
                      if ("diffclamp".equals(str)) {
                        diffClampAnimatedNode = new DiffClampAnimatedNode((ReadableMap)modulusAnimatedNode, this);
                      } else {
                        TransformAnimatedNode transformAnimatedNode;
                        if ("transform".equals(str)) {
                          transformAnimatedNode = new TransformAnimatedNode((ReadableMap)diffClampAnimatedNode, this);
                        } else if ("tracking".equals(str)) {
                          TrackingAnimatedNode trackingAnimatedNode = new TrackingAnimatedNode((ReadableMap)transformAnimatedNode, this);
                        } else {
                          stringBuilder1 = new StringBuilder("Unsupported node type: ");
                          stringBuilder1.append(str);
                          throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
                        } 
                      } 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      ((AnimatedNode)stringBuilder1).mTag = paramInt;
      this.mAnimatedNodes.put(paramInt, stringBuilder1);
      this.mUpdatedNodes.put(paramInt, stringBuilder1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" already exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void disconnectAnimatedNodeFromView(int paramInt1, int paramInt2) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt1);
    if (animatedNode != null) {
      if (animatedNode instanceof PropsAnimatedNode) {
        ((PropsAnimatedNode)animatedNode).disconnectFromView(paramInt2);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node connected to view should beof type ");
      stringBuilder1.append(PropsAnimatedNode.class.getName());
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void disconnectAnimatedNodes(int paramInt1, int paramInt2) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt1);
    if (animatedNode != null) {
      AnimatedNode animatedNode1 = (AnimatedNode)this.mAnimatedNodes.get(paramInt2);
      if (animatedNode1 != null) {
        animatedNode.removeChild(animatedNode1);
        this.mUpdatedNodes.put(paramInt2, animatedNode1);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node with tag ");
      stringBuilder1.append(paramInt2);
      stringBuilder1.append(" does not exists");
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void dropAnimatedNode(int paramInt) {
    this.mAnimatedNodes.remove(paramInt);
    this.mUpdatedNodes.remove(paramInt);
  }
  
  public void extractAnimatedNodeOffset(int paramInt) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      ((ValueAnimatedNode)animatedNode).extractOffset();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void flattenAnimatedNodeOffset(int paramInt) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      ((ValueAnimatedNode)animatedNode).flattenOffset();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  AnimatedNode getNodeById(int paramInt) {
    return (AnimatedNode)this.mAnimatedNodes.get(paramInt);
  }
  
  public void handleEvent(Event paramEvent) {
    if (!this.mEventDrivers.isEmpty()) {
      String str = this.mCustomEventNamesResolver.resolveCustomEventName(paramEvent.getEventName());
      Map<String, List<EventAnimationDriver>> map = this.mEventDrivers;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramEvent.getViewTag());
      stringBuilder.append(str);
      List list = map.get(stringBuilder.toString());
      if (list != null) {
        for (EventAnimationDriver eventAnimationDriver : list) {
          stopAnimationsForNode(eventAnimationDriver.mValueNode);
          paramEvent.dispatch(eventAnimationDriver);
          this.mRunUpdateNodeList.add(eventAnimationDriver.mValueNode);
        } 
        updateNodes(this.mRunUpdateNodeList);
        this.mRunUpdateNodeList.clear();
      } 
    } 
  }
  
  public boolean hasActiveAnimations() {
    return (this.mActiveAnimations.size() > 0 || this.mUpdatedNodes.size() > 0);
  }
  
  public void onEventDispatch(final Event event) {
    if (UiThreadUtil.isOnUiThread()) {
      handleEvent(event);
      return;
    } 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            NativeAnimatedNodesManager.this.handleEvent(event);
          }
        });
  }
  
  public void removeAnimatedEventFromView(int paramInt1, String paramString, int paramInt2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt1);
    stringBuilder.append(paramString);
    String str = stringBuilder.toString();
    if (this.mEventDrivers.containsKey(str)) {
      Map<String, List<EventAnimationDriver>> map;
      List list = this.mEventDrivers.get(str);
      if (list.size() == 1) {
        map = this.mEventDrivers;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(paramInt1);
        stringBuilder1.append(paramString);
        map.remove(stringBuilder1.toString());
        return;
      } 
      ListIterator<String> listIterator = map.listIterator();
      while (listIterator.hasNext()) {
        if (((EventAnimationDriver)listIterator.next()).mValueNode.mTag == paramInt2) {
          listIterator.remove();
          break;
        } 
      } 
    } 
  }
  
  public void restoreDefaultValues(int paramInt1, int paramInt2) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt1);
    if (animatedNode == null)
      return; 
    if (animatedNode instanceof PropsAnimatedNode) {
      ((PropsAnimatedNode)animatedNode).restoreDefaultValues();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node connected to view should beof type ");
    stringBuilder.append(PropsAnimatedNode.class.getName());
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void runUpdates(long paramLong) {
    UiThreadUtil.assertOnUiThread();
    int i;
    for (i = 0; i < this.mUpdatedNodes.size(); i++) {
      AnimatedNode animatedNode = (AnimatedNode)this.mUpdatedNodes.valueAt(i);
      this.mRunUpdateNodeList.add(animatedNode);
    } 
    this.mUpdatedNodes.clear();
    i = 0;
    boolean bool = false;
    while (i < this.mActiveAnimations.size()) {
      AnimationDriver animationDriver = (AnimationDriver)this.mActiveAnimations.valueAt(i);
      animationDriver.runAnimationStep(paramLong);
      ValueAnimatedNode valueAnimatedNode = animationDriver.mAnimatedValue;
      this.mRunUpdateNodeList.add(valueAnimatedNode);
      if (animationDriver.mHasFinished)
        bool = true; 
      i++;
    } 
    updateNodes(this.mRunUpdateNodeList);
    this.mRunUpdateNodeList.clear();
    if (bool)
      for (i = this.mActiveAnimations.size() - 1; i >= 0; i--) {
        AnimationDriver animationDriver = (AnimationDriver)this.mActiveAnimations.valueAt(i);
        if (animationDriver.mHasFinished) {
          if (animationDriver.mEndCallback != null) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putBoolean("finished", true);
            animationDriver.mEndCallback.invoke(new Object[] { writableMap });
          } 
          this.mActiveAnimations.removeAt(i);
        } 
      }  
  }
  
  public void setAnimatedNodeOffset(int paramInt, double paramDouble) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      ((ValueAnimatedNode)animatedNode).mOffset = paramDouble;
      this.mUpdatedNodes.put(paramInt, animatedNode);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void setAnimatedNodeValue(int paramInt, double paramDouble) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      stopAnimationsForNode(animatedNode);
      ((ValueAnimatedNode)animatedNode).mValue = paramDouble;
      this.mUpdatedNodes.put(paramInt, animatedNode);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void startAnimatingNode(int paramInt1, int paramInt2, ReadableMap paramReadableMap, Callback paramCallback) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt2);
    if (animatedNode != null) {
      if (animatedNode instanceof ValueAnimatedNode) {
        FrameBasedAnimationDriver frameBasedAnimationDriver;
        StringBuilder stringBuilder2;
        AnimationDriver animationDriver = (AnimationDriver)this.mActiveAnimations.get(paramInt1);
        if (animationDriver != null) {
          animationDriver.resetConfig(paramReadableMap);
          return;
        } 
        String str = paramReadableMap.getString("type");
        if ("frames".equals(str)) {
          frameBasedAnimationDriver = new FrameBasedAnimationDriver(paramReadableMap);
        } else {
          SpringAnimation springAnimation;
          if ("spring".equals(str)) {
            springAnimation = new SpringAnimation((ReadableMap)frameBasedAnimationDriver);
          } else if ("decay".equals(str)) {
            DecayAnimation decayAnimation = new DecayAnimation((ReadableMap)springAnimation);
          } else {
            stringBuilder2 = new StringBuilder("Unsupported animation type: ");
            stringBuilder2.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder2.toString());
          } 
        } 
        ((AnimationDriver)stringBuilder2).mId = paramInt1;
        ((AnimationDriver)stringBuilder2).mEndCallback = paramCallback;
        ((AnimationDriver)stringBuilder2).mAnimatedValue = (ValueAnimatedNode)animatedNode;
        this.mActiveAnimations.put(paramInt1, stringBuilder2);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("Animated node should be of type ");
      stringBuilder1.append(ValueAnimatedNode.class.getName());
      throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt2);
    stringBuilder.append(" does not exists");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void startListeningToAnimatedNodeValue(int paramInt, AnimatedNodeValueListener paramAnimatedNodeValueListener) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      ((ValueAnimatedNode)animatedNode).setValueListener(paramAnimatedNodeValueListener);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void stopAnimation(int paramInt) {
    for (int i = 0; i < this.mActiveAnimations.size(); i++) {
      AnimationDriver animationDriver = (AnimationDriver)this.mActiveAnimations.valueAt(i);
      if (animationDriver.mId == paramInt) {
        if (animationDriver.mEndCallback != null) {
          WritableMap writableMap = Arguments.createMap();
          writableMap.putBoolean("finished", false);
          animationDriver.mEndCallback.invoke(new Object[] { writableMap });
        } 
        this.mActiveAnimations.removeAt(i);
        return;
      } 
    } 
  }
  
  public void stopListeningToAnimatedNodeValue(int paramInt) {
    AnimatedNode animatedNode = (AnimatedNode)this.mAnimatedNodes.get(paramInt);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      ((ValueAnimatedNode)animatedNode).setValueListener((AnimatedNodeValueListener)null);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" does not exists or is not a 'value' node");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\NativeAnimatedNodesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */