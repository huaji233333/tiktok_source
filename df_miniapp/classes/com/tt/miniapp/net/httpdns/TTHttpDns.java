package com.tt.miniapp.net.httpdns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import okhttp3.o;

public class TTHttpDns implements o {
  public static TTHttpDns getInstance() {
    return Holder.mInstance;
  }
  
  public List<InetAddress> lookup(String paramString) throws UnknownHostException {
    if (paramString != null) {
      try {
        List<InetAddress> list1 = NetDnsResolver.getInst().getAddrByNameFromCache(paramString);
        if (list1 != null && list1.size() > 0)
          return list1; 
        list1 = o.b.lookup(paramString);
        if (list1 != null && list1.size() > 0) {
          List<InetAddress> list2 = NetDnsResolver.getInst().getAddrByNameFromCache(paramString);
          if (list2 != null) {
            int i = list2.size();
            if (i > 0)
              return list2; 
          } 
          return list1;
        } 
      } catch (Exception exception) {}
      List<InetAddress> list = NetDnsResolver.getInst().getAddrByName(paramString);
      if (list != null && list.size() > 0)
        return list; 
      StringBuilder stringBuilder = new StringBuilder("resolve dns failed by http dns, domain=");
      stringBuilder.append(paramString);
      throw new UnknownHostException(stringBuilder.toString());
    } 
    throw new UnknownHostException("hostname == null");
  }
  
  static class Holder {
    static final TTHttpDns mInstance = new TTHttpDns();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\httpdns\TTHttpDns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */