package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.address.AddressInfo;
import com.tt.miniapp.address.LoadAddressTask;
import com.tt.miniapp.offlinezip.OfflineZipManager;
import com.tt.miniapp.offlinezip.OnOfflineZipCheckUpdateResultListener;
import java.util.ArrayList;
import java.util.List;

public class RegionPicker extends MultiPicker implements LoadAddressTask.LoadCallBack {
  public AddressInfo mCityName = new AddressInfo("", "", "");
  
  public AddressInfo mDistrictName = new AddressInfo("", "", "");
  
  public AddressInfo mHeads;
  
  private int mLastColumn = -2;
  
  private int mLastIndex = -2;
  
  public AddressInfo mRegionName = new AddressInfo("", "", "");
  
  public String[] mSelectedStrings;
  
  public RegionPicker(Activity paramActivity) {
    super(paramActivity, (List<List<T>>)null);
    ArrayList<T> arrayList = new ArrayList();
    this.items.add(arrayList);
    arrayList = new ArrayList<T>();
    this.items.add(arrayList);
    arrayList = new ArrayList<T>();
    this.items.add(arrayList);
    setItems(this.items);
  }
  
  private void loadItem(final int province, final int city, final int district) {
    final LoadAddressTask task = new LoadAddressTask((Context)this.activity, new LoadAddressTask.LoadCallBack() {
          public void onLoaded(String param1String, List<AddressInfo> param1List, int param1Int) {
            // Byte code:
            //   0: aload_0
            //   1: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   4: aload_2
            //   5: invokevirtual addHeadIfExist : (Ljava/util/List;)Ljava/util/List;
            //   8: astore_1
            //   9: aload_0
            //   10: getfield val$province : I
            //   13: istore #5
            //   15: iconst_0
            //   16: istore #4
            //   18: iload #5
            //   20: istore_3
            //   21: iload #4
            //   23: aload_1
            //   24: invokeinterface size : ()I
            //   29: if_icmpge -> 124
            //   32: iload #5
            //   34: istore_3
            //   35: aload_0
            //   36: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   39: invokevirtual hasSelectedProvince : ()Z
            //   42: ifeq -> 124
            //   45: aload_1
            //   46: iload #4
            //   48: invokeinterface get : (I)Ljava/lang/Object;
            //   53: checkcast com/tt/miniapp/address/AddressInfo
            //   56: getfield name : Ljava/lang/String;
            //   59: aload_0
            //   60: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   63: getfield mSelectedStrings : [Ljava/lang/String;
            //   66: iconst_0
            //   67: aaload
            //   68: invokevirtual equals : (Ljava/lang/Object;)Z
            //   71: ifeq -> 98
            //   74: aload_0
            //   75: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   78: aload_1
            //   79: iload #4
            //   81: invokeinterface get : (I)Ljava/lang/Object;
            //   86: checkcast com/tt/miniapp/address/AddressInfo
            //   89: putfield mRegionName : Lcom/tt/miniapp/address/AddressInfo;
            //   92: iload #4
            //   94: istore_3
            //   95: goto -> 124
            //   98: aload_0
            //   99: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   102: aload_1
            //   103: iconst_0
            //   104: invokeinterface get : (I)Ljava/lang/Object;
            //   109: checkcast com/tt/miniapp/address/AddressInfo
            //   112: putfield mRegionName : Lcom/tt/miniapp/address/AddressInfo;
            //   115: iload #4
            //   117: iconst_1
            //   118: iadd
            //   119: istore #4
            //   121: goto -> 18
            //   124: iload_3
            //   125: istore #4
            //   127: aload_0
            //   128: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   131: getfield mHeads : Lcom/tt/miniapp/address/AddressInfo;
            //   134: ifnull -> 196
            //   137: aload_0
            //   138: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   141: getfield mHeads : Lcom/tt/miniapp/address/AddressInfo;
            //   144: getfield name : Ljava/lang/String;
            //   147: aload_0
            //   148: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   151: getfield mSelectedStrings : [Ljava/lang/String;
            //   154: iconst_0
            //   155: aaload
            //   156: invokevirtual equals : (Ljava/lang/Object;)Z
            //   159: ifne -> 177
            //   162: iload_3
            //   163: istore #4
            //   165: aload_0
            //   166: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   169: getfield mSelectedStrings : [Ljava/lang/String;
            //   172: iconst_0
            //   173: aaload
            //   174: ifnonnull -> 196
            //   177: aload_0
            //   178: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   181: astore #6
            //   183: aload #6
            //   185: aload #6
            //   187: getfield mHeads : Lcom/tt/miniapp/address/AddressInfo;
            //   190: putfield mRegionName : Lcom/tt/miniapp/address/AddressInfo;
            //   193: iconst_0
            //   194: istore #4
            //   196: aload_0
            //   197: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   200: iconst_0
            //   201: aload_1
            //   202: iload #4
            //   204: invokevirtual updateMultiPickerView : (ILjava/util/List;I)V
            //   207: aload_0
            //   208: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   211: invokevirtual hasSelectedCity : ()Z
            //   214: ifeq -> 335
            //   217: new com/tt/miniapp/address/LoadAddressTask
            //   220: dup
            //   221: aload_0
            //   222: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   225: getfield activity : Landroid/app/Activity;
            //   228: new com/tt/miniapp/component/nativeview/picker/wheel/RegionPicker$1$1
            //   231: dup
            //   232: aload_0
            //   233: invokespecial <init> : (Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker$1;)V
            //   236: invokespecial <init> : (Landroid/content/Context;Lcom/tt/miniapp/address/LoadAddressTask$LoadCallBack;)V
            //   239: astore_1
            //   240: iload #4
            //   242: istore_3
            //   243: aload_0
            //   244: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   247: getfield mHeads : Lcom/tt/miniapp/address/AddressInfo;
            //   250: ifnull -> 310
            //   253: iload #4
            //   255: iconst_1
            //   256: isub
            //   257: istore #4
            //   259: iload #4
            //   261: istore_3
            //   262: iload #4
            //   264: ifge -> 310
            //   267: new java/util/ArrayList
            //   270: dup
            //   271: invokespecial <init> : ()V
            //   274: astore_1
            //   275: aload_1
            //   276: aload_0
            //   277: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   280: getfield mHeads : Lcom/tt/miniapp/address/AddressInfo;
            //   283: invokeinterface add : (Ljava/lang/Object;)Z
            //   288: pop
            //   289: aload_0
            //   290: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   293: iconst_1
            //   294: aload_1
            //   295: iconst_0
            //   296: invokevirtual updateMultiPickerView : (ILjava/util/List;I)V
            //   299: aload_0
            //   300: getfield this$0 : Lcom/tt/miniapp/component/nativeview/picker/wheel/RegionPicker;
            //   303: iconst_2
            //   304: aload_1
            //   305: iconst_0
            //   306: invokevirtual updateMultiPickerView : (ILjava/util/List;I)V
            //   309: return
            //   310: aload_1
            //   311: iconst_1
            //   312: anewarray java/lang/String
            //   315: dup
            //   316: iconst_0
            //   317: aload_2
            //   318: iload_3
            //   319: invokeinterface get : (I)Ljava/lang/Object;
            //   324: checkcast com/tt/miniapp/address/AddressInfo
            //   327: invokevirtual getAddrHashCode : ()Ljava/lang/String;
            //   330: aastore
            //   331: invokevirtual execute : ([Ljava/lang/Object;)Landroid/os/AsyncTask;
            //   334: pop
            //   335: return
          }
        });
    if (TextUtils.isEmpty(OfflineZipManager.INSTANCE.getSpecifiedOfflineModuleVersion((Context)this.activity, "address"))) {
      OfflineZipManager.INSTANCE.checkUpdateOfflineZip((Context)this.activity, new OnOfflineZipCheckUpdateResultListener() {
            public void onComplete(boolean param1Boolean) {
              if (param1Boolean)
                task.execute((Object[])new String[] { "province" }, ); 
            }
          },  new String[] { "address" });
      return;
    } 
    loadAddressTask.execute((Object[])new String[] { "province" });
  }
  
  public List<AddressInfo> addHeadIfExist(List<AddressInfo> paramList) {
    if (this.mHeads != null) {
      ArrayList<AddressInfo> arrayList = new ArrayList();
      arrayList.add(this.mHeads);
      if (paramList != null)
        arrayList.addAll(paramList); 
      return arrayList;
    } 
    return paramList;
  }
  
  public AddressInfo[] getSelectedRegionArray() {
    return new AddressInfo[] { this.mRegionName, this.mCityName, this.mDistrictName };
  }
  
  public boolean hasSelectedCity() {
    String[] arrayOfString = this.mSelectedStrings;
    return (arrayOfString != null && arrayOfString.length >= 2);
  }
  
  public boolean hasSelectedDistrict() {
    String[] arrayOfString = this.mSelectedStrings;
    return (arrayOfString != null && arrayOfString.length >= 3);
  }
  
  public boolean hasSelectedProvince() {
    String[] arrayOfString = this.mSelectedStrings;
    return (arrayOfString != null && arrayOfString.length > 0);
  }
  
  public void loadItems() {
    loadItem(0, 0, 0);
  }
  
  public void onLoaded(String paramString, List<AddressInfo> paramList, int paramInt) {
    updateMultiPickerView(paramInt, (List)paramList, 0);
  }
  
  protected void onOneWheelSelected(WheelView paramWheelView, int paramInt) {
    super.onOneWheelSelected(paramWheelView, paramInt);
    int i = ((Integer)paramWheelView.getTag()).intValue();
    if (paramInt == this.mLastIndex && i == this.mLastColumn)
      return; 
    this.mLastColumn = i;
    this.mLastIndex = paramInt;
    if (paramInt == 0 && this.mHeads != null) {
      ArrayList<AddressInfo> arrayList = new ArrayList();
      arrayList.add(this.mHeads);
      if (i == 0) {
        AddressInfo addressInfo = this.mHeads;
        this.mRegionName = addressInfo;
        this.mCityName = addressInfo;
        this.mDistrictName = addressInfo;
        updateMultiPickerView(1, (List)arrayList, 0);
        updateMultiPickerView(2, (List)arrayList, 0);
      } 
      if (i == 1) {
        AddressInfo addressInfo = this.mHeads;
        this.mCityName = addressInfo;
        this.mDistrictName = addressInfo;
        updateMultiPickerView(2, (List)arrayList, 0);
      } 
      if (i == 2)
        this.mDistrictName = this.mHeads; 
      return;
    } 
    null = new LoadAddressTask((Context)this.activity, this);
    synchronized (this.items) {
      List<AddressInfo> list1 = (List)this.items.get(i);
      if (list1.isEmpty() || list1.size() < paramInt)
        return; 
      AddressInfo addressInfo = list1.get(paramInt);
      List<AddressInfo> list2 = addHeadIfExist(null.loadDivisionFromCache(addressInfo.getAddrHashCode()));
      if (i != 0) {
        if (i != 1) {
          if (i == 2)
            this.mDistrictName = addressInfo; 
        } else {
          ((WheelView)this.mWheelViews.get(2)).setSelectedIndex(0);
          this.mDistrictName = list2.get(0);
          this.mCityName = addressInfo;
          updateMultiPickerView(null.getLastDepth(), (List)list2, 0);
        } 
      } else {
        this.mRegionName = addressInfo;
        ((WheelView)this.mWheelViews.get(1)).setSelectedIndex(0);
        this.mCityName = list2.get(0);
        paramInt = null.getLastDepth();
        updateMultiPickerView(paramInt, (List)list2, 0);
        List<AddressInfo> list = addHeadIfExist(null.loadDivisionFromCache(this.mCityName.getAddrHashCode()));
        this.mDistrictName = list.get(0);
        updateMultiPickerView(paramInt + 1, (List)list, 0);
      } 
      return;
    } 
  }
  
  public void setSelectedRegionItem(String[] paramArrayOfString) {
    this.mSelectedStrings = new String[3];
    int i = 0;
    while (i < paramArrayOfString.length) {
      String[] arrayOfString = this.mSelectedStrings;
      if (i < arrayOfString.length) {
        arrayOfString[i] = paramArrayOfString[i];
        i++;
      } 
    } 
  }
  
  public void setWheelHead(String paramString) {
    if (paramString != null) {
      this.mHeads = new AddressInfo(paramString, "", "");
      this.mCityName = new AddressInfo(paramString, "", "");
      this.mDistrictName = new AddressInfo(paramString, "", "");
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\RegionPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */