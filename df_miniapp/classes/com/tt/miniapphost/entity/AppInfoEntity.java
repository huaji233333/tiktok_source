package com.tt.miniapphost.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.f.a;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.ad.AdModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInfoEntity implements Parcelable {
  public static final Parcelable.Creator<AppInfoEntity> CREATOR = new Parcelable.Creator<AppInfoEntity>() {
      public final AppInfoEntity createFromParcel(Parcel param1Parcel) {
        return new AppInfoEntity(param1Parcel);
      }
      
      public final AppInfoEntity[] newArray(int param1Int) {
        return new AppInfoEntity[param1Int];
      }
    };
  
  public String adSiteVersionFromMeta;
  
  public String adSiteVersionFromSchema;
  
  public ArrayList<AdModel> adlist;
  
  public String appId;
  
  public String appName;
  
  public List<String> appUrls;
  
  public int authPass;
  
  public String bdpLaunchQuery;
  
  public String bdpLog;
  
  public String bizLocation;
  
  public volatile a<String, List<String>> domainMap;
  
  public String domains;
  
  public String encryptextra;
  
  public String extra;
  
  public int getFromType;
  
  public String gtoken;
  
  public String icon;
  
  public int innertype;
  
  public boolean isAutoTest;
  
  public boolean isDevelop;
  
  public boolean isForceCheckDomains;
  
  public boolean isLandScape;
  
  public boolean isNotRecordRecentUseApps;
  
  public int isOpenLocation;
  
  public String launchFrom;
  
  public String launchType;
  
  public long leastVersionCode;
  
  public List<String> libra_path;
  
  public String loadingBg;
  
  public String location;
  
  public String mExtJson;
  
  public String md5;
  
  public String meta;
  
  public String minJssdk;
  
  public int mode;
  
  public int needUpdateSettings;
  
  public String oriStartPage;
  
  public String page;
  
  public String pkgCompressType;
  
  public String privacyPolicyUrl;
  
  public String query;
  
  public String refererInfo;
  
  public String roomid;
  
  public String scene;
  
  public String schemaVersion;
  
  public String session;
  
  public int shareLevel;
  
  public String shareTicket;
  
  public String snapShotCompileVersion;
  
  public String sourceMd5;
  
  public String startPage;
  
  public int state;
  
  public String subScene;
  
  public int switchBitmap;
  
  public String techType;
  
  public String timelineServerUrl;
  
  public String token;
  
  public int toolbarStyle;
  
  public String ttBlackCode;
  
  public String ttId;
  
  public String ttSafeCode;
  
  public int type;
  
  public String version;
  
  public long versionCode;
  
  public int versionState;
  
  public String versionType;
  
  public AppInfoEntity() {
    this.versionType = "current";
    this.isForceCheckDomains = true;
  }
  
  protected AppInfoEntity(Parcel paramParcel) {
    boolean bool1;
    this.versionType = "current";
    boolean bool2 = true;
    this.isForceCheckDomains = true;
    this.appId = paramParcel.readString();
    this.version = paramParcel.readString();
    this.versionType = paramParcel.readString();
    this.versionCode = paramParcel.readLong();
    this.token = paramParcel.readString();
    this.appUrls = paramParcel.createStringArrayList();
    this.icon = paramParcel.readString();
    this.appName = paramParcel.readString();
    this.isOpenLocation = paramParcel.readInt();
    this.startPage = paramParcel.readString();
    this.oriStartPage = paramParcel.readString();
    this.query = paramParcel.readString();
    this.ttId = paramParcel.readString();
    this.ttSafeCode = paramParcel.readString();
    this.ttBlackCode = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.isDevelop = bool1;
    this.md5 = paramParcel.readString();
    this.type = paramParcel.readInt();
    this.techType = paramParcel.readString();
    this.mode = paramParcel.readInt();
    this.launchType = paramParcel.readString();
    this.domains = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.isForceCheckDomains = bool1;
    this.launchFrom = paramParcel.readString();
    this.shareTicket = paramParcel.readString();
    this.scene = paramParcel.readString();
    this.subScene = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.isLandScape = bool1;
    this.state = paramParcel.readInt();
    this.versionState = paramParcel.readInt();
    this.bdpLog = paramParcel.readString();
    this.location = paramParcel.readString();
    this.bizLocation = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    this.isNotRecordRecentUseApps = bool1;
    if (paramParcel.readByte() != 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    this.isAutoTest = bool1;
    this.page = paramParcel.readString();
    this.schemaVersion = paramParcel.readString();
    this.meta = paramParcel.readString();
    this.sourceMd5 = paramParcel.readString();
    this.snapShotCompileVersion = paramParcel.readString();
    this.extra = paramParcel.readString();
    this.bdpLaunchQuery = paramParcel.readString();
    this.encryptextra = paramParcel.readString();
    this.minJssdk = paramParcel.readString();
    this.shareLevel = paramParcel.readInt();
    this.adlist = paramParcel.createTypedArrayList(AdModel.CREATOR);
    this.session = paramParcel.readString();
    this.roomid = paramParcel.readString();
    this.gtoken = paramParcel.readString();
    this.timelineServerUrl = paramParcel.readString();
    this.innertype = paramParcel.readInt();
    this.authPass = paramParcel.readInt();
    this.getFromType = paramParcel.readInt();
    this.loadingBg = paramParcel.readString();
    this.refererInfo = paramParcel.readString();
    this.switchBitmap = paramParcel.readInt();
    this.needUpdateSettings = paramParcel.readInt();
    this.mExtJson = paramParcel.readString();
    this.privacyPolicyUrl = paramParcel.readString();
    this.pkgCompressType = paramParcel.readString();
    this.libra_path = paramParcel.createStringArrayList();
    this.toolbarStyle = paramParcel.readInt();
    this.adSiteVersionFromSchema = paramParcel.readString();
    this.adSiteVersionFromMeta = paramParcel.readString();
    this.leastVersionCode = paramParcel.readLong();
  }
  
  private boolean isSameVersionType(String paramString) {
    return TextUtils.equals(paramString, this.versionType) ? true : ((TextUtils.isEmpty(this.versionType) || TextUtils.equals(this.versionType, "current")) ? (!TextUtils.isEmpty(paramString) ? (TextUtils.equals(paramString, "current")) : true) : false);
  }
  
  public int compareVersion(String paramString) {
    if (paramString == null && this.version != null)
      return -1; 
    if (paramString != null && this.version == null)
      return 1; 
    int j = 0;
    if (paramString == null)
      return 0; 
    String str = this.version;
    String[] arrayOfString1 = paramString.split("\\.");
    String[] arrayOfString2 = str.split("\\.");
    int k = Math.min(arrayOfString1.length, arrayOfString2.length);
    int i = 0;
    while (j < k) {
      int m = arrayOfString1[j].length() - arrayOfString2[j].length();
      i = m;
      if (m == 0) {
        m = arrayOfString1[j].compareTo(arrayOfString2[j]);
        i = m;
        if (m == 0) {
          j++;
          i = m;
        } 
      } 
    } 
    return (i != 0) ? i : (arrayOfString1.length - arrayOfString2.length);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (!(paramObject instanceof AppInfoEntity))
      return false; 
    paramObject = paramObject;
    return (((AppInfoEntity)paramObject).appId.equals(this.appId) && ((AppInfoEntity)paramObject).version.equals(this.version));
  }
  
  public JSONObject getAdParams() {
    String str = this.oriStartPage;
    boolean bool = TextUtils.isEmpty(str);
    JSONObject jSONObject2 = null;
    if (!bool) {
      str = Uri.parse(str).getQueryParameter("ad_params");
    } else {
      str = null;
    } 
    JSONObject jSONObject1 = jSONObject2;
    try {
      if (!TextUtils.isEmpty(str))
        jSONObject1 = new JSONObject(str); 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("AppInfoEntity", new Object[] { jSONException });
      jSONObject1 = jSONObject2;
    } 
    return (jSONObject1 != null) ? jSONObject1 : new JSONObject();
  }
  
  public String getDefaultUrl() {
    List<String> list = this.appUrls;
    return (list != null && !list.isEmpty()) ? this.appUrls.get(0) : null;
  }
  
  public JSONObject getExtConfigInfoJson() {
    try {
      String str = this.mExtJson;
      if (!TextUtils.isEmpty(str))
        return new JSONObject((new JSONObject(str)).optString("ext")); 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("AppInfoEntity", new Object[] { "generateExtConfigData", jSONException });
    } 
    return null;
  }
  
  public int hashCode() {
    byte b;
    String str = this.appId;
    int i = 0;
    if (str != null) {
      b = str.hashCode();
    } else {
      b = 0;
    } 
    str = this.version;
    if (str != null)
      i = str.hashCode(); 
    return b + i;
  }
  
  public boolean isAdSite() {
    return ((this.switchBitmap & 0x10) != 0);
  }
  
  public boolean isAppValid() {
    return (this.state == 1 && this.versionState == 0);
  }
  
  public boolean isAudit() {
    String str = this.versionType;
    return (str != null && "audit".equals(str));
  }
  
  public boolean isBox() {
    return ((this.switchBitmap & 0x2) != 0);
  }
  
  public boolean isCanLaunchApp() {
    return ((this.switchBitmap & 0x8) != 0);
  }
  
  public boolean isGame() {
    int i = this.type;
    return (i == 2 || i == 7);
  }
  
  public boolean isGameCenter() {
    return ((this.switchBitmap & 0x20) != 0);
  }
  
  public boolean isLocalTest() {
    String str = this.versionType;
    return (str != null && !"current".equals(str));
  }
  
  public boolean isPreviewVersion() {
    String str = this.versionType;
    return (str != null && "preview".equals(str));
  }
  
  public boolean isSpecial() {
    return ((this.switchBitmap & 0x1) != 0);
  }
  
  public boolean isWebApp() {
    return (this.type == 6);
  }
  
  public boolean isWhite() {
    return ((this.switchBitmap & 0x4) != 0);
  }
  
  public void parseDomain() {
    a<String, List<String>> a1 = new a();
    AppBrandLogger.d("AppInfoEntity", new Object[] { "parseDomain" });
    if (TextUtils.isEmpty(this.domains)) {
      this.domainMap = a1;
      return;
    } 
    AppBrandLogger.d("AppInfoEntity", new Object[] { "domains = ", this.domains });
    try {
      JSONObject jSONObject = new JSONObject(this.domains);
      Iterator<String> iterator = jSONObject.keys();
      while (iterator.hasNext()) {
        String str = iterator.next();
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        if (jSONArray != null) {
          ArrayList<String> arrayList = new ArrayList();
          for (int i = 0; i < jSONArray.length(); i++) {
            AppBrandLogger.d("AppInfoEntity", new Object[] { "getHost() = ", jSONArray.optString(i) });
            arrayList.add(jSONArray.optString(i));
          } 
          a1.put(str, arrayList);
        } 
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "AppInfoEntity", jSONException.getStackTrace());
    } 
    AppBrandLogger.d("AppInfoEntity", new Object[] { "domainMap.size = ", Integer.valueOf(a1.size()) });
    this.domainMap = a1;
  }
  
  public void setType(int paramInt) {
    int i = this.type;
    if (1 != i && 2 != i && 6 != i)
      this.type = paramInt; 
  }
  
  public boolean shouldHotLaunch(String paramString) {
    if (!isSameVersionType(paramString))
      return false; 
    if (TextUtils.isEmpty(this.versionType))
      return true; 
    paramString = this.versionType;
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -1109880953) {
      if (i != 93166555) {
        if (i == 1126940025 && paramString.equals("current"))
          b = 0; 
      } else if (paramString.equals("audit")) {
        b = 2;
      } 
    } else if (paramString.equals("latest")) {
      b = 1;
    } 
    return !(b != 0 && b != 1 && b != 2);
  }
  
  public String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder("AppInfoEntity{\n\nappId='");
    stringBuilder.append(this.appId);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n version='");
    stringBuilder.append(this.version);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n versionType=");
    stringBuilder.append(this.versionType);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n versionCode");
    stringBuilder.append(this.versionCode);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n appUrl='");
    List<String> list = this.appUrls;
    if (list != null) {
      str = Arrays.toString(list.toArray());
    } else {
      str = "";
    } 
    stringBuilder.append(str);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n icon='");
    stringBuilder.append(this.icon);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n appName='");
    stringBuilder.append(this.appName);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n isOpenLocation='");
    stringBuilder.append(this.isOpenLocation);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n startPage='");
    stringBuilder.append(this.startPage);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n ttId='");
    stringBuilder.append(this.ttId);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n ttSafeCode='");
    stringBuilder.append(this.ttSafeCode);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n ttBlackCode='");
    stringBuilder.append(this.ttBlackCode);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n isLocalTest=");
    stringBuilder.append(isLocalTest());
    stringBuilder.append(",\n\n isDevelop=");
    stringBuilder.append(this.isDevelop);
    stringBuilder.append(",\n\n md5='");
    stringBuilder.append(this.md5);
    stringBuilder.append('\'');
    stringBuilder.append(",\n\n type=");
    stringBuilder.append(this.type);
    stringBuilder.append(",\n\n techType=");
    stringBuilder.append(this.techType);
    stringBuilder.append(",\n\n mode=");
    stringBuilder.append(this.mode);
    stringBuilder.append(",\n\n launchType=");
    stringBuilder.append(this.launchType);
    stringBuilder.append(",\n\n isAutoTest=");
    stringBuilder.append(this.isAutoTest);
    stringBuilder.append(",\n\n extra=");
    stringBuilder.append(this.extra);
    stringBuilder.append(",\n\n encryptextra=");
    stringBuilder.append(this.encryptextra);
    stringBuilder.append(",\n\n mExtJson=");
    stringBuilder.append(this.mExtJson);
    stringBuilder.append(",\n\n privacyPolicyUrl=");
    stringBuilder.append(this.privacyPolicyUrl);
    stringBuilder.append(",\n\n compressType=");
    stringBuilder.append(this.pkgCompressType);
    stringBuilder.append(",\n\n bdpLaunchQuery=");
    stringBuilder.append(this.bdpLaunchQuery);
    stringBuilder.append(",\n\n toolbarStyle=");
    stringBuilder.append(this.toolbarStyle);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.appId);
    paramParcel.writeString(this.version);
    paramParcel.writeString(this.versionType);
    paramParcel.writeLong(this.versionCode);
    paramParcel.writeString(this.token);
    paramParcel.writeStringList(this.appUrls);
    paramParcel.writeString(this.icon);
    paramParcel.writeString(this.appName);
    paramParcel.writeInt(this.isOpenLocation);
    paramParcel.writeString(this.startPage);
    paramParcel.writeString(this.oriStartPage);
    paramParcel.writeString(this.query);
    paramParcel.writeString(this.ttId);
    paramParcel.writeString(this.ttSafeCode);
    paramParcel.writeString(this.ttBlackCode);
    paramParcel.writeByte((byte)this.isDevelop);
    paramParcel.writeString(this.md5);
    paramParcel.writeInt(this.type);
    paramParcel.writeString(this.techType);
    paramParcel.writeInt(this.mode);
    paramParcel.writeString(this.launchType);
    paramParcel.writeString(this.domains);
    paramParcel.writeByte((byte)this.isForceCheckDomains);
    paramParcel.writeString(this.launchFrom);
    paramParcel.writeString(this.shareTicket);
    paramParcel.writeString(this.scene);
    paramParcel.writeString(this.subScene);
    paramParcel.writeByte((byte)this.isLandScape);
    paramParcel.writeInt(this.state);
    paramParcel.writeInt(this.versionState);
    paramParcel.writeString(this.bdpLog);
    paramParcel.writeString(this.location);
    paramParcel.writeString(this.bizLocation);
    paramParcel.writeByte((byte)this.isNotRecordRecentUseApps);
    paramParcel.writeByte((byte)this.isAutoTest);
    paramParcel.writeString(this.page);
    paramParcel.writeString(this.schemaVersion);
    paramParcel.writeString(this.meta);
    paramParcel.writeString(this.sourceMd5);
    paramParcel.writeString(this.snapShotCompileVersion);
    paramParcel.writeString(this.extra);
    paramParcel.writeString(this.bdpLaunchQuery);
    paramParcel.writeString(this.encryptextra);
    paramParcel.writeString(this.minJssdk);
    paramParcel.writeInt(this.shareLevel);
    paramParcel.writeTypedList(this.adlist);
    paramParcel.writeString(this.session);
    paramParcel.writeString(this.roomid);
    paramParcel.writeString(this.gtoken);
    paramParcel.writeString(this.timelineServerUrl);
    paramParcel.writeInt(this.innertype);
    paramParcel.writeInt(this.authPass);
    paramParcel.writeInt(this.getFromType);
    paramParcel.writeString(this.loadingBg);
    paramParcel.writeString(this.refererInfo);
    paramParcel.writeInt(this.switchBitmap);
    paramParcel.writeInt(this.needUpdateSettings);
    paramParcel.writeString(this.mExtJson);
    paramParcel.writeString(this.privacyPolicyUrl);
    paramParcel.writeString(this.pkgCompressType);
    paramParcel.writeStringList(this.libra_path);
    paramParcel.writeInt(this.toolbarStyle);
    paramParcel.writeString(this.adSiteVersionFromSchema);
    paramParcel.writeString(this.adSiteVersionFromMeta);
    paramParcel.writeLong(this.leastVersionCode);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\AppInfoEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */