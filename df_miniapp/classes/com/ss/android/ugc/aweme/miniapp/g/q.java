package com.ss.android.ugc.aweme.miniapp.g;

import com.ss.android.ugc.aweme.miniapp.d.a;
import com.ss.android.ugc.aweme.miniapp.d.b;
import com.ss.android.ugc.aweme.miniapp.h.b;
import com.ss.android.ugc.aweme.miniapp.i.a;
import com.ss.android.ugc.aweme.miniapp.m.a;
import com.ss.android.ugc.aweme.miniapp.n.a;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.option.e.c;
import java.util.ArrayList;
import java.util.List;

public class q extends c {
  public List<NativeModule> createNativeModules(AppbrandContext paramAppbrandContext) {
    ArrayList<b> arrayList = new ArrayList();
    arrayList.add(new b(paramAppbrandContext));
    arrayList.add(new a(paramAppbrandContext));
    arrayList.add(new a(paramAppbrandContext));
    arrayList.add(new a(paramAppbrandContext));
    arrayList.add(new b(paramAppbrandContext));
    arrayList.add(new a(paramAppbrandContext));
    return (List)arrayList;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */