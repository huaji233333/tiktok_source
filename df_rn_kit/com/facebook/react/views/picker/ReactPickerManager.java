package com.facebook.react.views.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.picker.events.PickerItemSelectEvent;

public abstract class ReactPickerManager extends SimpleViewManager<ReactPicker> {
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, ReactPicker paramReactPicker) {
    paramReactPicker.setOnSelectListener(new PickerEventEmitter(paramReactPicker, ((UIManagerModule)paramThemedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher()));
  }
  
  protected void onAfterUpdateTransaction(ReactPicker paramReactPicker) {
    super.onAfterUpdateTransaction((View)paramReactPicker);
    paramReactPicker.updateStagedSelection();
  }
  
  @ReactProp(customType = "Color", name = "color")
  public void setColor(ReactPicker paramReactPicker, Integer paramInteger) {
    paramReactPicker.setPrimaryColor(paramInteger);
    ReactPickerAdapter reactPickerAdapter = (ReactPickerAdapter)paramReactPicker.getAdapter();
    if (reactPickerAdapter != null)
      reactPickerAdapter.setPrimaryTextColor(paramInteger); 
  }
  
  @ReactProp(defaultBoolean = true, name = "enabled")
  public void setEnabled(ReactPicker paramReactPicker, boolean paramBoolean) {
    paramReactPicker.setEnabled(paramBoolean);
  }
  
  @ReactProp(name = "items")
  public void setItems(ReactPicker paramReactPicker, ReadableArray paramReadableArray) {
    if (paramReadableArray != null) {
      ReadableMap[] arrayOfReadableMap = new ReadableMap[paramReadableArray.size()];
      for (int i = 0; i < paramReadableArray.size(); i++)
        arrayOfReadableMap[i] = paramReadableArray.getMap(i); 
      ReactPickerAdapter reactPickerAdapter = new ReactPickerAdapter(paramReactPicker.getContext(), arrayOfReadableMap);
      reactPickerAdapter.setPrimaryTextColor(paramReactPicker.getPrimaryColor());
      paramReactPicker.setAdapter((SpinnerAdapter)reactPickerAdapter);
      return;
    } 
    paramReactPicker.setAdapter(null);
  }
  
  @ReactProp(name = "prompt")
  public void setPrompt(ReactPicker paramReactPicker, String paramString) {
    paramReactPicker.setPrompt(paramString);
  }
  
  @ReactProp(name = "selected")
  public void setSelected(ReactPicker paramReactPicker, int paramInt) {
    paramReactPicker.setStagedSelection(paramInt);
  }
  
  static class PickerEventEmitter implements ReactPicker.OnSelectListener {
    private final EventDispatcher mEventDispatcher;
    
    private final ReactPicker mReactPicker;
    
    public PickerEventEmitter(ReactPicker param1ReactPicker, EventDispatcher param1EventDispatcher) {
      this.mReactPicker = param1ReactPicker;
      this.mEventDispatcher = param1EventDispatcher;
    }
    
    public void onItemSelected(int param1Int) {
      this.mEventDispatcher.dispatchEvent((Event)new PickerItemSelectEvent(this.mReactPicker.getId(), param1Int));
    }
  }
  
  static class ReactPickerAdapter extends ArrayAdapter<ReadableMap> {
    private final LayoutInflater mInflater;
    
    private Integer mPrimaryTextColor;
    
    public ReactPickerAdapter(Context param1Context, ReadableMap[] param1ArrayOfReadableMap) {
      super(param1Context, 0, (Object[])param1ArrayOfReadableMap);
      this.mInflater = (LayoutInflater)a.b(param1Context.getSystemService("layout_inflater"));
    }
    
    private View getView(int param1Int, View param1View, ViewGroup param1ViewGroup, boolean param1Boolean) {
      ReadableMap readableMap = (ReadableMap)getItem(param1Int);
      View view = param1View;
      if (param1View == null) {
        if (param1Boolean) {
          param1Int = 17367049;
        } else {
          param1Int = 17367048;
        } 
        view = this.mInflater.inflate(param1Int, param1ViewGroup, false);
      } 
      TextView textView = (TextView)view;
      textView.setText(readableMap.getString("label"));
      if (!param1Boolean) {
        Integer integer = this.mPrimaryTextColor;
        if (integer != null) {
          textView.setTextColor(integer.intValue());
          return view;
        } 
      } 
      if (readableMap.hasKey("color") && !readableMap.isNull("color"))
        textView.setTextColor(readableMap.getInt("color")); 
      return view;
    }
    
    public View getDropDownView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      return getView(param1Int, param1View, param1ViewGroup, true);
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      return getView(param1Int, param1View, param1ViewGroup, false);
    }
    
    public void setPrimaryTextColor(Integer param1Integer) {
      this.mPrimaryTextColor = param1Integer;
      notifyDataSetChanged();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\picker\ReactPickerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */