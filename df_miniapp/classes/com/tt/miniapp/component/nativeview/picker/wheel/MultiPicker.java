package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.tt.miniapp.component.nativeview.picker.wheel.entity.MultiPickerManager;
import com.tt.miniapphost.util.UIUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiPicker<T> extends WheelPicker {
  private List<List<String>> itemStrings = new ArrayList<List<String>>();
  
  private int itemWidth = -99;
  
  protected List<List<T>> items = new ArrayList<List<T>>();
  
  private String label = "";
  
  private onConfirmListener mOnComfimListener;
  
  protected OnWheelListener mOnWheelListener;
  
  protected List<WheelView> mWheelViews;
  
  private int[] selectedItemIndex;
  
  public MultiPicker(Activity paramActivity, List<List<T>> paramList) {
    super(paramActivity);
    setItems(paramList);
    MultiPickerManager.getInst().setMultiPicker(this);
  }
  
  private String formatToString(T paramT, int paramInt) {
    return this.textSizeAutoFit ? paramT.toString() : ((paramT instanceof Float || paramT instanceof Double) ? (new DecimalFormat("0.00")).format(paramT) : paramT.toString());
  }
  
  public View makeCenterView() {
    if (this.items.size() != 0) {
      LinearLayout linearLayout = new LinearLayout((Context)this.activity);
      linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
      linearLayout.setOrientation(0);
      linearLayout.setGravity(17);
      int j = this.items.size();
      this.mWheelViews = new ArrayList<WheelView>();
      int k = (int)(UIUtils.getScreenWidth(getContext()) / 1.0F / j);
      for (int i = 0; i < j; i++) {
        boolean bool;
        final WheelView wheelView = createWheelView();
        this.mWheelViews.add(wheelView);
        linearLayout.addView(wheelView);
        if (TextUtils.isEmpty(this.label))
          wheelView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(k, -2)); 
        int[] arrayOfInt = this.selectedItemIndex;
        if (arrayOfInt != null) {
          bool = arrayOfInt[i];
        } else {
          bool = false;
        } 
        wheelView.setItems(this.itemStrings.get(i), bool);
        wheelView.setTag(Integer.valueOf(i));
        wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
              public void onSelected(int param1Int) {
                MultiPicker.this.onOneWheelSelected(wheelView, param1Int);
              }
            });
      } 
      return (View)linearLayout;
    } 
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Items can't be empty");
    throw illegalArgumentException;
  }
  
  protected void onOneWheelSelected(WheelView paramWheelView, int paramInt) {
    int i = ((Integer)paramWheelView.getTag()).intValue();
    if (this.mOnWheelListener != null) {
      List list = this.items.get(i);
      if (list != null && list.size() != 0) {
        if (list.size() <= paramInt)
          return; 
        this.mOnWheelListener.onWheeled(i, paramInt, ((List)this.items.get(i)).get(paramInt));
      } 
    } 
  }
  
  public void onSubmit() {
    if (this.mOnComfimListener != null) {
      List<WheelView> list = this.mWheelViews;
      if (list != null) {
        int j = list.size();
        int[] arrayOfInt = new int[j];
        for (int i = 0; i < j; i++)
          arrayOfInt[i] = ((WheelView)this.mWheelViews.get(i)).getSelectedIndex(); 
        this.mOnComfimListener.onConfirm(arrayOfInt);
      } 
    } 
  }
  
  public void setItems(List<List<T>> paramList) {
    if (paramList != null) {
      if (paramList.size() == 0)
        return; 
      this.items = paramList;
      this.itemStrings.clear();
      for (List<T> list : paramList) {
        ArrayList<String> arrayList = new ArrayList();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext())
          arrayList.add(formatToString(iterator.next(), paramList.size())); 
        this.itemStrings.add(arrayList);
      } 
    } 
  }
  
  public void setLabel(String paramString) {
    this.label = paramString;
  }
  
  public void setOnComfimListener(onConfirmListener paramonConfirmListener) {
    this.mOnComfimListener = paramonConfirmListener;
  }
  
  public void setOnWheelListener(OnWheelListener paramOnWheelListener) {
    this.mOnWheelListener = paramOnWheelListener;
  }
  
  public void setSelectedItem(int[] paramArrayOfint) {
    this.selectedItemIndex = paramArrayOfint;
  }
  
  public void updateMultiPickerView(int paramInt1, List<T> paramList, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mWheelViews : Ljava/util/List;
    //   4: astore #4
    //   6: aload #4
    //   8: ifnull -> 186
    //   11: aload #4
    //   13: invokeinterface size : ()I
    //   18: iload_1
    //   19: iconst_1
    //   20: iadd
    //   21: if_icmplt -> 186
    //   24: aload_0
    //   25: getfield mWheelViews : Ljava/util/List;
    //   28: iload_1
    //   29: invokeinterface get : (I)Ljava/lang/Object;
    //   34: checkcast com/tt/miniapp/component/nativeview/picker/wheel/WheelView
    //   37: astore #4
    //   39: new java/util/ArrayList
    //   42: dup
    //   43: invokespecial <init> : ()V
    //   46: astore #5
    //   48: aload_2
    //   49: invokeinterface iterator : ()Ljava/util/Iterator;
    //   54: astore #6
    //   56: aload #6
    //   58: invokeinterface hasNext : ()Z
    //   63: ifeq -> 113
    //   66: aload #6
    //   68: invokeinterface next : ()Ljava/lang/Object;
    //   73: astore #7
    //   75: aload #7
    //   77: instanceof java/lang/String
    //   80: ifeq -> 56
    //   83: aload_0
    //   84: getfield items : Ljava/util/List;
    //   87: ifnull -> 56
    //   90: aload_0
    //   91: getfield textSizeAutoFit : Z
    //   94: ifne -> 56
    //   97: aload #5
    //   99: aload #7
    //   101: invokevirtual toString : ()Ljava/lang/String;
    //   104: invokeinterface add : (Ljava/lang/Object;)Z
    //   109: pop
    //   110: goto -> 56
    //   113: aload_0
    //   114: getfield items : Ljava/util/List;
    //   117: iload_1
    //   118: invokeinterface get : (I)Ljava/lang/Object;
    //   123: checkcast java/util/List
    //   126: astore #6
    //   128: aload #6
    //   130: invokeinterface clear : ()V
    //   135: aload #5
    //   137: invokeinterface isEmpty : ()Z
    //   142: ifne -> 165
    //   145: aload #6
    //   147: aload #5
    //   149: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   154: pop
    //   155: aload #4
    //   157: aload #5
    //   159: invokevirtual setItems : (Ljava/util/List;)V
    //   162: goto -> 180
    //   165: aload #6
    //   167: aload_2
    //   168: invokeinterface addAll : (Ljava/util/Collection;)Z
    //   173: pop
    //   174: aload #4
    //   176: aload_2
    //   177: invokevirtual setItems : (Ljava/util/List;)V
    //   180: aload #4
    //   182: iload_3
    //   183: invokevirtual setSelectedIndex : (I)V
    //   186: return
  }
  
  public static interface OnWheelListener<T> {
    void onWheeled(int param1Int1, int param1Int2, T param1T);
  }
  
  public static interface onConfirmListener {
    void onConfirm(int[] param1ArrayOfint);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\MultiPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */