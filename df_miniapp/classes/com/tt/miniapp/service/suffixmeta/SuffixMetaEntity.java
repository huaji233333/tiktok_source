package com.tt.miniapp.service.suffixmeta;

import android.os.Parcel;
import android.os.Parcelable;
import com.tt.miniapphost.AppBrandLogger;
import java.util.List;

public class SuffixMetaEntity implements Parcelable {
  public static final Parcelable.Creator<SuffixMetaEntity> CREATOR = new Parcelable.Creator<SuffixMetaEntity>() {
      public final SuffixMetaEntity createFromParcel(Parcel param1Parcel) {
        return new SuffixMetaEntity(param1Parcel);
      }
      
      public final SuffixMetaEntity[] newArray(int param1Int) {
        return new SuffixMetaEntity[param1Int];
      }
    };
  
  public String awemeSecUserId;
  
  public String awemeUserId;
  
  public boolean diskCache;
  
  public int isNew;
  
  public int mLivePlayNativeOrH5 = Integer.MIN_VALUE;
  
  public int mNativeOrH5 = Integer.MIN_VALUE;
  
  public List<String> shareChannelBlackList;
  
  public String shieldPage;
  
  public SuffixMetaEntity() {}
  
  protected SuffixMetaEntity(Parcel paramParcel) {
    this.shieldPage = paramParcel.readString();
    this.shareChannelBlackList = paramParcel.createStringArrayList();
    this.mNativeOrH5 = paramParcel.readInt();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean isUseWebLivePlayer(boolean paramBoolean) {
    if (this.mNativeOrH5 == Integer.MIN_VALUE) {
      AppBrandLogger.i("SuffixMetaEntity", new Object[] { "isUseWebLivePlayer 未获取 nativeOrH5 值，使用配置的默认值" });
      return paramBoolean;
    } 
    AppBrandLogger.i("SuffixMetaEntity", new Object[] { "isUseWebLivePlayer 后置 meta 数据是否使用本地缓存", Boolean.valueOf(this.diskCache) });
    return (this.mLivePlayNativeOrH5 == 1);
  }
  
  public boolean isUseWebVideo(boolean paramBoolean) {
    if (this.mNativeOrH5 == Integer.MIN_VALUE) {
      AppBrandLogger.i("SuffixMetaEntity", new Object[] { "isUseWebVideo 未获取 nativeOrH5 值，使用配置的默认值" });
      return paramBoolean;
    } 
    AppBrandLogger.i("SuffixMetaEntity", new Object[] { "isUseWebVideo 后置 meta 数据是否使用本地缓存", Boolean.valueOf(this.diskCache) });
    return (this.mNativeOrH5 == 1);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.shieldPage);
    paramParcel.writeStringList(this.shareChannelBlackList);
    paramParcel.writeInt(this.mNativeOrH5);
  }
  
  public enum PROPERTY {
    shieldPage("shield_page");
    
    private String name;
    
    static {
    
    }
    
    PROPERTY(String param1String1) {
      this.name = param1String1;
    }
    
    public final String getName() {
      return this.name;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\suffixmeta\SuffixMetaEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */