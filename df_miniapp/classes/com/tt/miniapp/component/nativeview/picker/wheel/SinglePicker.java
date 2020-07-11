package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapphost.util.UIUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinglePicker<T> extends WheelPicker {
  private List<String> itemStrings = new ArrayList<String>();
  
  private int itemWidth = -99;
  
  public List<T> items = new ArrayList<T>();
  
  private String label = "";
  
  private OnItemPickListener<T> onItemPickListener;
  
  public OnWheelListener<T> onWheelListener;
  
  public int selectedItemIndex = 0;
  
  private WheelView wheelView;
  
  public SinglePicker(Activity paramActivity, List<T> paramList) {
    super(paramActivity);
    setItems(paramList);
  }
  
  public SinglePicker(Activity paramActivity, T[] paramArrayOfT) {
    this(paramActivity, Arrays.asList(paramArrayOfT));
  }
  
  private String formatToString(T paramT) {
    return (paramT instanceof Float || paramT instanceof Double) ? (new DecimalFormat("0.00")).format(paramT) : paramT.toString();
  }
  
  public void addItem(T paramT) {
    this.items.add(paramT);
    this.itemStrings.add(formatToString(paramT));
  }
  
  public int getSelectedIndex() {
    return this.selectedItemIndex;
  }
  
  public T getSelectedItem() {
    return this.items.get(this.selectedItemIndex);
  }
  
  public WheelView getWheelView() {
    return this.wheelView;
  }
  
  public View makeCenterView() {
    if (this.items.size() != 0) {
      LinearLayout linearLayout = new LinearLayout((Context)this.activity);
      linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
      linearLayout.setOrientation(0);
      linearLayout.setGravity(17);
      this.wheelView = createWheelView();
      linearLayout.addView(this.wheelView);
      if (TextUtils.isEmpty(this.label)) {
        this.wheelView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(this.screenWidthPixels, -2));
      } else {
        this.wheelView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        TextView textView = createLabelView();
        textView.setText(this.label);
        linearLayout.addView((View)textView);
      } 
      this.wheelView.setItems(this.itemStrings, this.selectedItemIndex);
      this.wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              SinglePicker singlePicker = SinglePicker.this;
              singlePicker.selectedItemIndex = param1Int;
              if (singlePicker.onWheelListener != null)
                SinglePicker.this.onWheelListener.onWheeled(SinglePicker.this.selectedItemIndex, SinglePicker.this.items.get(param1Int)); 
            }
          });
      if (this.itemWidth != -99) {
        ViewGroup.LayoutParams layoutParams = this.wheelView.getLayoutParams();
        layoutParams.width = (int)UIUtils.dip2Px((Context)this.activity, this.itemWidth);
        this.wheelView.setLayoutParams(layoutParams);
      } 
      return (View)linearLayout;
    } 
    throw new IllegalArgumentException("Items can't be empty");
  }
  
  public void onSubmit() {
    OnItemPickListener<T> onItemPickListener = this.onItemPickListener;
    if (onItemPickListener != null)
      onItemPickListener.onItemPicked(this.wheelView.getSelectedIndex(), getSelectedItem()); 
  }
  
  public void removeItem(T paramT) {
    this.items.remove(paramT);
    this.itemStrings.remove(formatToString(paramT));
  }
  
  public void setItemWidth(int paramInt) {
    WheelView wheelView = this.wheelView;
    if (wheelView != null) {
      ViewGroup.LayoutParams layoutParams = wheelView.getLayoutParams();
      layoutParams.width = (int)UIUtils.dip2Px((Context)this.activity, paramInt);
      this.wheelView.setLayoutParams(layoutParams);
      return;
    } 
    this.itemWidth = paramInt;
  }
  
  public void setItems(List<T> paramList) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 90
    //   4: aload_1
    //   5: invokeinterface size : ()I
    //   10: ifne -> 14
    //   13: return
    //   14: aload_0
    //   15: aload_1
    //   16: putfield items : Ljava/util/List;
    //   19: aload_0
    //   20: getfield itemStrings : Ljava/util/List;
    //   23: invokeinterface clear : ()V
    //   28: aload_1
    //   29: invokeinterface iterator : ()Ljava/util/Iterator;
    //   34: astore_1
    //   35: aload_1
    //   36: invokeinterface hasNext : ()Z
    //   41: ifeq -> 69
    //   44: aload_1
    //   45: invokeinterface next : ()Ljava/lang/Object;
    //   50: astore_2
    //   51: aload_0
    //   52: getfield itemStrings : Ljava/util/List;
    //   55: aload_0
    //   56: aload_2
    //   57: invokespecial formatToString : (Ljava/lang/Object;)Ljava/lang/String;
    //   60: invokeinterface add : (Ljava/lang/Object;)Z
    //   65: pop
    //   66: goto -> 35
    //   69: aload_0
    //   70: getfield wheelView : Lcom/tt/miniapp/component/nativeview/picker/wheel/WheelView;
    //   73: astore_1
    //   74: aload_1
    //   75: ifnull -> 90
    //   78: aload_1
    //   79: aload_0
    //   80: getfield itemStrings : Ljava/util/List;
    //   83: aload_0
    //   84: getfield selectedItemIndex : I
    //   87: invokevirtual setItems : (Ljava/util/List;I)V
    //   90: return
  }
  
  public void setItems(T[] paramArrayOfT) {
    setItems(Arrays.asList(paramArrayOfT));
  }
  
  public void setLabel(String paramString) {
    this.label = paramString;
  }
  
  public void setOnItemPickListener(OnItemPickListener<T> paramOnItemPickListener) {
    this.onItemPickListener = paramOnItemPickListener;
  }
  
  public void setOnWheelListener(OnWheelListener<T> paramOnWheelListener) {
    this.onWheelListener = paramOnWheelListener;
  }
  
  public void setSelectedIndex(int paramInt) {
    if (paramInt >= 0 && paramInt < this.items.size())
      this.selectedItemIndex = paramInt; 
  }
  
  public void setSelectedItem(T paramT) {
    setSelectedIndex(this.itemStrings.indexOf(formatToString(paramT)));
  }
  
  public static interface OnItemPickListener<T> {
    void onItemPicked(int param1Int, T param1T);
  }
  
  public static interface OnWheelListener<T> {
    void onWheeled(int param1Int, T param1T);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\SinglePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */