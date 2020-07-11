package com.bytedance.ies.bullet.kit.rn.internal;

import com.bytedance.ies.bullet.b.e.a.h;
import com.bytedance.ies.bullet.b.g.a.b;
import com.bytedance.ies.bullet.kit.rn.c;
import com.bytedance.ies.bullet.kit.rn.core.b;
import com.bytedance.ies.bullet.kit.rn.core.e;
import com.bytedance.ies.bullet.kit.rn.f;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.NativeModuleWrapper;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.SimpleViewManagerWrapper;
import com.bytedance.ies.bullet.kit.rn.j;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewManager;
import d.a.m;
import d.f.b.l;
import d.u;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class a implements ReactPackage {
  public final j a;
  
  public final List<c> b;
  
  public final b c;
  
  private final d.f.a.a<h> d;
  
  public a(j paramj, d.f.a.a<? extends h> parama, List<? extends c> paramList, b paramb) {
    this.a = paramj;
    this.d = (d.f.a.a)parama;
    this.b = (List)paramList;
    this.c = paramb;
  }
  
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    l.b(paramReactApplicationContext, "reactContext");
    List<NativeModule> list = m.c((Object[])new NativeModule[] { (NativeModule)new RnBridgeModule(paramReactApplicationContext, this.d) });
    b b1 = this.c.a();
    b1.a(ReactApplicationContext.class, paramReactApplicationContext);
    List<c> list1 = this.b;
    ArrayList arrayList = new ArrayList();
    Iterator<c> iterator = list1.iterator();
    while (iterator.hasNext()) {
      List list2 = ((c)iterator.next()).a((f)this.a, b1);
      ArrayList<NativeModule> arrayList1 = new ArrayList(m.a(list2, 10));
      Iterator<b> iterator1 = list2.iterator();
      while (iterator1.hasNext())
        arrayList1.add(NativeModuleWrapper.a.a(iterator1.next())); 
      m.a(arrayList, arrayList1);
    } 
    list.addAll(arrayList);
    return list;
  }
  
  public final List<ViewManager<?, ?>> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    l.b(paramReactApplicationContext, "reactContext");
    ArrayList<ViewManager<?, ?>> arrayList1 = new ArrayList();
    b b1 = this.c.a();
    b1.a(ReactApplicationContext.class, paramReactApplicationContext);
    List<c> list = this.b;
    ArrayList arrayList = new ArrayList();
    Iterator<c> iterator = list.iterator();
    while (iterator.hasNext()) {
      List list1 = ((c)iterator.next()).b((f)this.a, b1);
      ArrayList<SimpleViewManager> arrayList2 = new ArrayList(m.a(list1, 10));
      for (e e : list1) {
        if (e != null) {
          arrayList2.add(SimpleViewManagerWrapper.a.a(e));
          continue;
        } 
        throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.core.BulletSimpleViewManager<android.view.View>");
      } 
      m.a(arrayList, arrayList2);
    } 
    arrayList1.addAll(arrayList);
    return arrayList1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */