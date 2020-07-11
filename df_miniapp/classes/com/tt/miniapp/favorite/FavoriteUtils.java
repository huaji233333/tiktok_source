package com.tt.miniapp.favorite;

import android.text.TextUtils;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.titlemenu.item.FavoriteMiniAppMenuItem;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class FavoriteUtils {
  public static d checkCommonLimit() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    return (appInfoEntity == null) ? d.a("common env error") : (TextUtils.isEmpty(appInfoEntity.appId) ? d.a("get appId error") : (appInfoEntity.isBox() ? d.a("box app not support") : (!FavoriteMiniAppMenuItem.isDisplayFavoriteEnterHostLevel() ? d.a("favorites function offline") : (!FavoriteMiniAppMenuItem.isDisplayFavoriteEnterPlatformLevel() ? d.a("favorites function offline") : (isCollected() ? d.a("had added to favorites list") : d.a())))));
  }
  
  public static String endEllipsize(String paramString, int paramInt) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    if (paramInt < 0)
      return ""; 
    if (paramString.length() <= paramInt)
      return paramString; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString.substring(0, paramInt - 1));
    stringBuilder.append("…");
    return stringBuilder.toString();
  }
  
  public static boolean isCollected() {
    LinkedHashSet linkedHashSet = InnerHostProcessBridge.getFavoriteSet();
    String str = (AppbrandApplication.getInst().getAppInfo()).appId;
    if (linkedHashSet != null) {
      Iterator<String> iterator = linkedHashSet.iterator();
      while (iterator.hasNext()) {
        if (str.contentEquals(iterator.next()))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean isFeedScene() {
    FavoriteConfig favoriteConfig = FavoriteConfig.get();
    String str = (AppbrandApplicationImpl.getInst().getAppInfo()).scene;
    Iterator<String> iterator = favoriteConfig.feedSceneList.iterator();
    while (iterator.hasNext()) {
      if (TextUtils.equals(iterator.next(), str))
        return true; 
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */