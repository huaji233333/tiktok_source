package com.tt.miniapphost.process.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class CrossProcessDataEntity implements Parcelable {
  public static final Parcelable.Creator<CrossProcessDataEntity> CREATOR = new Parcelable.Creator<CrossProcessDataEntity>() {
      public final CrossProcessDataEntity createFromParcel(Parcel param1Parcel) {
        return new CrossProcessDataEntity(param1Parcel);
      }
      
      public final CrossProcessDataEntity[] newArray(int param1Int) {
        return new CrossProcessDataEntity[param1Int];
      }
    };
  
  public Bundle mData;
  
  private CrossProcessDataEntity(Bundle paramBundle) {
    this.mData = paramBundle;
  }
  
  protected CrossProcessDataEntity(Parcel paramParcel) {
    this.mData = paramParcel.readBundle(getClass().getClassLoader());
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean getBoolean(String paramString) {
    return getBoolean(paramString, false);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    return this.mData.getBoolean(paramString, paramBoolean);
  }
  
  public CrossProcessDataEntity getCrossProcessDataEntity(String paramString) {
    return new CrossProcessDataEntity(this.mData.getBundle(paramString));
  }
  
  public double getDouble(String paramString) {
    return getDouble(paramString, 0.0D);
  }
  
  public double getDouble(String paramString, double paramDouble) {
    return this.mData.getDouble(paramString, paramDouble);
  }
  
  public int getInt(String paramString) {
    return getInt(paramString, 0);
  }
  
  public int getInt(String paramString, int paramInt) {
    return this.mData.getInt(paramString, paramInt);
  }
  
  public JSONObject getJSONObject(String paramString) {
    try {
      paramString = getString(paramString);
      if (paramString != null)
        return new JSONObject(paramString); 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("CrossProcessDataEntity", new Object[] { jSONException });
    } 
    return null;
  }
  
  public long getLong(String paramString) {
    return getLong(paramString, 0L);
  }
  
  public long getLong(String paramString, long paramLong) {
    return this.mData.getLong(paramString, paramLong);
  }
  
  public <T extends Parcelable> T getParcelable(String paramString) {
    return (T)this.mData.getParcelable(paramString);
  }
  
  public String getString(String paramString) {
    return getString(paramString, null);
  }
  
  public String getString(String paramString1, String paramString2) {
    return this.mData.getString(paramString1, paramString2);
  }
  
  public List<String> getStringList(String paramString) {
    return this.mData.getStringArrayList(paramString);
  }
  
  public List<String> getStringList(String paramString, List<String> paramList) {
    ArrayList<String> arrayList = this.mData.getStringArrayList(paramString);
    return (arrayList != null) ? arrayList : paramList;
  }
  
  public boolean has(String paramString) {
    return this.mData.containsKey(paramString);
  }
  
  public String toString() {
    return this.mData.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeBundle(this.mData);
  }
  
  public static class Builder {
    Bundle mData = new Bundle();
    
    public static Builder create() {
      return new Builder();
    }
    
    public CrossProcessDataEntity build() {
      return new CrossProcessDataEntity(this.mData);
    }
    
    public Builder put(String param1String, Object param1Object) {
      if (param1Object == null) {
        this.mData.remove(param1String);
        return this;
      } 
      if (param1Object instanceof String) {
        this.mData.putString(param1String, (String)param1Object);
        return this;
      } 
      if (param1Object instanceof Boolean) {
        this.mData.putBoolean(param1String, ((Boolean)param1Object).booleanValue());
        return this;
      } 
      if (param1Object instanceof Integer) {
        this.mData.putInt(param1String, ((Integer)param1Object).intValue());
        return this;
      } 
      if (param1Object instanceof Double) {
        this.mData.putDouble(param1String, ((Double)param1Object).doubleValue());
        return this;
      } 
      if (param1Object instanceof Long) {
        this.mData.putLong(param1String, ((Long)param1Object).longValue());
        return this;
      } 
      if (param1Object instanceof JSONObject) {
        this.mData.putString(param1String, param1Object.toString());
        return this;
      } 
      throw new IllegalArgumentException();
    }
    
    @Deprecated
    public Builder putCrossProcessDataEntity(String param1String, CrossProcessDataEntity param1CrossProcessDataEntity) {
      if (param1CrossProcessDataEntity != null)
        this.mData.putBundle(param1String, param1CrossProcessDataEntity.mData); 
      return this;
    }
    
    public Builder putParcelable(String param1String, Parcelable param1Parcelable) {
      this.mData.putParcelable(param1String, param1Parcelable);
      return this;
    }
    
    public Builder putStringList(String param1String, List<String> param1List) {
      if (param1List != null)
        this.mData.putStringArrayList(param1String, new ArrayList<String>(param1List)); 
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\data\CrossProcessDataEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */