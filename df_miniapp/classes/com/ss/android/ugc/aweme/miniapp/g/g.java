package com.ss.android.ugc.aweme.miniapp.g;

import android.app.Application;
import android.content.Context;
import com.ss.android.ugc.aweme.miniapp.f.a;
import com.ss.android.ugc.aweme.miniapp.f.a.b;
import com.ss.android.ugc.aweme.miniapp.f.a.c;
import com.ss.android.ugc.aweme.miniapp.f.a.d;
import com.ss.android.ugc.aweme.miniapp.f.a.e;
import com.ss.android.ugc.aweme.miniapp.f.b;
import com.ss.android.ugc.aweme.miniapp.f.c;
import com.ss.android.ugc.aweme.miniapp.f.d;
import com.ss.android.ugc.aweme.miniapp.f.e;
import com.ss.android.ugc.aweme.miniapp.f.f;
import com.ss.android.ugc.aweme.miniapp.f.h;
import com.ss.android.ugc.aweme.miniapp.f.i;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import com.tt.option.j.a;
import java.util.ArrayList;
import java.util.List;

public class g extends a {
  public List<IAsyncHostDataHandler> createAsyncHostDataHandlerList() {
    ArrayList<e> arrayList = new ArrayList();
    Application application = AppbrandContext.getInst().getApplicationContext();
    arrayList.add(new e());
    arrayList.add(new b((Context)application));
    arrayList.add(new i());
    arrayList.add(new ae());
    return (List)arrayList;
  }
  
  public List<ISyncHostDataHandler> createSyncHostDataHandlerList() {
    ArrayList<f> arrayList = new ArrayList();
    AppbrandContext.getInst().getApplicationContext();
    arrayList.add(new f());
    arrayList.add(new d());
    arrayList.add(new a());
    arrayList.add(new c());
    arrayList.add(new e());
    arrayList.add(new h());
    arrayList.add(new d());
    arrayList.add(new c());
    arrayList.add(new b());
    return (List)arrayList;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */