package com.tt.option.ad;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import java.util.ArrayList;

public final class d {
  public static a a(String paramString, c paramc) {
    ArrayList<AdModel> arrayList;
    if (!HostDependManager.getInst().isSupportAd(paramc))
      return new a(false, 1006, "The scene does not support advertising"); 
    String str = paramc.getStrType();
    if (TextUtils.isEmpty(paramString))
      return new a(false, 1001, "参数错误,adUnitId = null"); 
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null) {
      arrayList = appInfoEntity.adlist;
    } else {
      arrayList = new ArrayList();
    } 
    if (arrayList == null)
      return new a(false, 1003, "meta接口广告数据下发失败,内部错误:adlist = null"); 
    int j = arrayList.size();
    if (j == 0)
      return new a(false, 1003, "meta接口广告数据下发失败,内部错误:adlist.size() = 0"); 
    for (int i = 0;; i++) {
      if (i < j) {
        try {
          AdModel adModel = arrayList.get(i);
          if (adModel == null) {
            AppBrandLogger.e("AdUtils", new Object[] { "ad == null" });
          } else if (TextUtils.equals(paramString, adModel.a) && TextUtils.equals(str, adModel.b)) {
            if (adModel.c == 0)
              return new a(false, 1008, "广告单元已关闭"); 
            if (adModel.c == 1)
              return new a(true, 0, ""); 
          } 
        } catch (Exception exception) {
          AppBrandLogger.stacktrace(6, "AdUtils", exception.getStackTrace());
          return new a(false, 1003, "内部错误,解析失败");
        } 
      } else {
        return new a(false, 1002, "广告单元无效");
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */