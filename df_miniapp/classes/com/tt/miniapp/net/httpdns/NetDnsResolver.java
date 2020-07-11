package com.tt.miniapp.net.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.service.ServiceProvider;
import com.tt.miniapp.service.netconfig.AppbrandNetConfigService;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NetDnsResolver {
  public final Object mPreResolveLock = new Object();
  
  private List<InetAddress> getAddrByNameFromDomainServerSync(String paramString) {
    ArrayList arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder("https://dig.bdurl.net/q?host=");
    stringBuilder.append(paramString);
    paramString = stringBuilder.toString();
    NetDnsResult netDnsResult = NetDnsResult.parseDnsResult(NetManager.getInst().request(paramString).a());
    List<InetAddress> list = arrayList;
    if (netDnsResult != null)
      list = netDnsResult.addrList; 
    return list;
  }
  
  public static NetDnsResolver getInst() {
    return Holder.instance;
  }
  
  private List<InetAddress> parseNumericAddressList(String paramString, List<String> paramList) {
    ArrayList<InetAddress> arrayList = new ArrayList();
    if (paramList != null && paramList.size() > 0 && !TextUtils.isEmpty(paramString))
      for (int i = 0; i < paramList.size(); i++) {
        InetAddress inetAddress = NetAddressUtil.parseAddress(paramList.get(i));
        if (inetAddress != null)
          arrayList.add(inetAddress); 
      }  
    return arrayList;
  }
  
  public List<InetAddress> getAddrByName(String paramString) {
    List<InetAddress> list = getAddrByNameFromCache(paramString);
    if (list != null && list.size() != 0) {
      StringBuilder stringBuilder = new StringBuilder("hit cache, domain=");
      stringBuilder.append(paramString);
      AppBrandLogger.i("tma_NetDnsResolver", new Object[] { stringBuilder.toString() });
      return list;
    } 
    list = getAddrByNameFromDomainServerSync(paramString);
    if (list != null && list.size() > 0)
      NetAddressCache.getIns().put(paramString, list); 
    return list;
  }
  
  public List<InetAddress> getAddrByNameFromCache(String paramString) {
    return NetAddressCache.getIns().get(paramString);
  }
  
  public void preResolveInetAddressFromHttpDns(final AppInfoEntity appInfo) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            if (((AppbrandNetConfigService)ServiceProvider.getInstance().getService(AppbrandNetConfigService.class)).getTTRequestType((Context)AppbrandContext.getInst().getApplicationContext(), appInfo.appId).equals("httpdns"))
              synchronized (NetDnsResolver.this.mPreResolveLock) {
                if (appInfo != null && appInfo.domainMap != null) {
                  List<String> list = (List)appInfo.domainMap.get("request");
                  NetDnsResolver.this.preResolveInetAddressFromHttpDns(list);
                } 
                return;
              }  
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public void preResolveInetAddressFromHttpDns(List<String> paramList) {
    if (paramList != null) {
      int i;
      if (paramList.size() == 0)
        return; 
      Iterator<String> iterator = paramList.iterator();
      while (iterator.hasNext()) {
        String str = iterator.next();
        if (NetAddressCache.getIns().isExist(str))
          iterator.remove(); 
      } 
      int k = paramList.size();
      if (k == 0)
        return; 
      if (k % 6 != 0) {
        i = k / 6 + 1;
      } else {
        i = k / 6;
      } 
      for (int j = 0; j < i; j++) {
        StringBuilder stringBuilder = new StringBuilder("https://dig.bdurl.net/q?host=");
        int m = j * 6;
        int n = m + 6;
        while (m < n && m < k) {
          String str = paramList.get(m);
          if (!TextUtils.isEmpty(str))
            stringBuilder.append(str); 
          if (m != k - 1 && m != n - 1)
            stringBuilder.append(","); 
          m++;
        } 
        NetDnsResult.MultiNetDnsResult multiNetDnsResult = NetDnsResult.parseDnsResultList(NetManager.getInst().request(stringBuilder.toString()).a());
        if (multiNetDnsResult != null) {
          List<NetDnsResult> list = multiNetDnsResult.dnsResultList;
          if (list != null && list.size() > 0)
            for (m = 0; m < list.size(); m++) {
              NetDnsResult netDnsResult = list.get(m);
              List<InetAddress> list1 = netDnsResult.addrList;
              if (list1 != null && list1.size() > 0)
                NetAddressCache.getIns().put(netDnsResult.host, list1); 
            }  
        } 
      } 
    } 
  }
  
  static class Holder {
    public static NetDnsResolver instance = new NetDnsResolver();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\httpdns\NetDnsResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */