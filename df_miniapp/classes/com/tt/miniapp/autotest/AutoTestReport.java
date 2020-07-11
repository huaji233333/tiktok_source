package com.tt.miniapp.autotest;

import android.content.Intent;
import android.os.Environment;
import com.storage.async.Action;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.IOUtils;
import d.f.a.b;
import d.f.b.g;
import d.f.b.l;
import d.n;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class AutoTestReport implements Action {
  public static final Companion Companion = new Companion(null);
  
  private final List<n<String, b<Map<String, ? extends List<AutoTestEvent>>, Object>>> mCaculatorList;
  
  private final List<AutoTestEvent> mEventList;
  
  private final List<AutoTestLoopEvent> mLooperInfos;
  
  public AutoTestReport(List<AutoTestEvent> paramList, List<? extends n<String, ? extends b<? super Map<String, ? extends List<AutoTestEvent>>, ? extends Object>>> paramList1, List<AutoTestLoopEvent> paramList2) {
    this.mEventList = paramList;
    this.mCaculatorList = (List)paramList1;
    this.mLooperInfos = paramList2;
  }
  
  private final JSONObject generateCalculatorJson() {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    for (AutoTestEvent autoTestEvent : this.mEventList) {
      List<AutoTestEvent> list2 = (List)linkedHashMap.get(autoTestEvent.getId());
      List<AutoTestEvent> list1 = list2;
      if (list2 == null)
        list1 = new LinkedList(); 
      list1.add(autoTestEvent);
      linkedHashMap.put(autoTestEvent.getId(), list1);
    } 
    JSONObject jSONObject = new JSONObject();
    for (n<String, b<Map<String, ? extends List<AutoTestEvent>>, Object>> n : this.mCaculatorList)
      jSONObject.put((String)n.getFirst(), ((b)n.getSecond()).invoke(linkedHashMap)); 
    return jSONObject;
  }
  
  private final JSONArray generateEventJson() {
    JSONArray jSONArray = new JSONArray();
    Iterator<AutoTestEvent> iterator = this.mEventList.iterator();
    while (iterator.hasNext())
      jSONArray.put(((AutoTestEvent)iterator.next()).toJson()); 
    return jSONArray;
  }
  
  private final JSONArray generateLoopJson() {
    JSONArray jSONArray = new JSONArray();
    for (AutoTestLoopEvent autoTestLoopEvent : this.mLooperInfos) {
      AppBrandLogger.d("AutoTestReport", new Object[] { autoTestLoopEvent });
      jSONArray.put(autoTestLoopEvent.toJson());
    } 
    return jSONArray;
  }
  
  public final void act() {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("calculator", generateCalculatorJson());
    jSONObject.put("event", generateEventJson());
    jSONObject.put("loop", generateLoopJson());
    AppBrandLogger.d("AutoTestReport", new Object[] { jSONObject.toString() });
    Intent intent = new Intent("com.tt.miniapp.autotest");
    StringBuilder stringBuilder = new StringBuilder();
    File file = Environment.getExternalStorageDirectory();
    l.a(file, "Environment.getExternalStorageDirectory()");
    stringBuilder.append(file.getAbsolutePath());
    stringBuilder.append("/autotest.json");
    String str = stringBuilder.toString();
    IOUtils.writeStringToFile(str, jSONObject.toString(), "utf-8");
    intent.putExtra("path", str);
    ThreadUtil.runOnUIThread(new AutoTestReport$act$1(intent), 2000L);
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class AutoTestReport$act$1 implements Runnable {
    AutoTestReport$act$1(Intent param1Intent) {}
    
    public final void run() {
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      appbrandContext.getApplicationContext().sendBroadcast(this.$intent);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\autotest\AutoTestReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */