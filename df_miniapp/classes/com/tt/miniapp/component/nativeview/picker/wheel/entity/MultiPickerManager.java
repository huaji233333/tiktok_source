package com.tt.miniapp.component.nativeview.picker.wheel.entity;

import com.tt.miniapp.component.nativeview.picker.wheel.MultiPicker;
import java.lang.ref.WeakReference;

public class MultiPickerManager {
  private WeakReference<MultiPicker> multiPicker;
  
  private MultiPickerManager() {}
  
  public static MultiPickerManager getInst() {
    return Holder.instance;
  }
  
  public MultiPicker getMultiPicker() {
    return this.multiPicker.get();
  }
  
  public void setMultiPicker(MultiPicker paramMultiPicker) {
    this.multiPicker = new WeakReference<MultiPicker>(paramMultiPicker);
  }
  
  static class Holder {
    static MultiPickerManager instance = new MultiPickerManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\entity\MultiPickerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */