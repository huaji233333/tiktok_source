package okhttp3;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.internal.c;
import okhttp3.internal.g.f;

public final class v implements m {
  private final CookieHandler b;
  
  public v(CookieHandler paramCookieHandler) {
    this.b = paramCookieHandler;
  }
  
  private static List<l> a(t paramt, String paramString) {
    ArrayList<l> arrayList = new ArrayList();
    int j = paramString.length();
    for (int i = 0; i < j; i = k + 1) {
      int k = c.a(paramString, i, j, ";,");
      int n = c.a(paramString, i, k, '=');
      String str = c.c(paramString, i, n);
      if (!str.startsWith("$")) {
        String str1;
        if (n < k) {
          str1 = c.c(paramString, n + 1, k);
        } else {
          str1 = "";
        } 
        String str2 = str1;
        if (str1.startsWith("\"")) {
          str2 = str1;
          if (str1.endsWith("\""))
            str2 = str1.substring(1, str1.length() - 1); 
        } 
        arrayList.add((new l.a()).a(str).b(str2).c(paramt.d).a());
      } 
    } 
    return arrayList;
  }
  
  public final List<l> loadForRequest(t paramt) {
    Map<?, ?> map = Collections.emptyMap();
    try {
      ArrayList<l> arrayList;
      Map<String, List<String>> map1 = this.b.get(paramt.b(), (Map)map);
      map = null;
      label23: for (Map.Entry<String, List<String>> entry : map1.entrySet()) {
        String str = (String)entry.getKey();
        if (("Cookie".equalsIgnoreCase(str) || "Cookie2".equalsIgnoreCase(str)) && !((List)entry.getValue()).isEmpty()) {
          Iterator<String> iterator = ((List)entry.getValue()).iterator();
          Map<?, ?> map2 = map;
          while (true) {
            map = map2;
            if (iterator.hasNext()) {
              String str1 = iterator.next();
              map = map2;
              if (map2 == null)
                arrayList = new ArrayList(); 
              arrayList.addAll(a(paramt, str1));
              ArrayList<l> arrayList1 = arrayList;
              continue;
            } 
            continue label23;
          } 
        } 
      } 
      return (arrayList != null) ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
    } catch (IOException iOException) {
      f f = f.c();
      StringBuilder stringBuilder = new StringBuilder("Loading cookies failed for ");
      stringBuilder.append(paramt.d("/..."));
      f.a(5, stringBuilder.toString(), iOException);
      return Collections.emptyList();
    } 
  }
  
  public final void saveFromResponse(t paramt, List<l> paramList) {
    if (this.b != null) {
      ArrayList<String> arrayList = new ArrayList();
      Iterator<l> iterator = paramList.iterator();
      while (iterator.hasNext())
        arrayList.add(((l)iterator.next()).a(true)); 
      Map<String, ArrayList<String>> map = Collections.singletonMap("Set-Cookie", arrayList);
      try {
        this.b.put(paramt.b(), (Map)map);
        return;
      } catch (IOException iOException) {
        f f = f.c();
        StringBuilder stringBuilder = new StringBuilder("Saving cookies failed for ");
        stringBuilder.append(paramt.d("/..."));
        f.a(5, stringBuilder.toString(), iOException);
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\okhttp3\v.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */