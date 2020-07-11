package com.tt.miniapp.event.remedy;

import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public abstract class AbsEventDelegate {
  private InnerEventHandler mEventHandler;
  
  protected List<EventEntity> mList;
  
  public AbsEventDelegate(InnerEventHandler paramInnerEventHandler) {
    this.mEventHandler = paramInnerEventHandler;
    this.mList = new ArrayList<EventEntity>();
  }
  
  protected void add(EventEntity paramEventEntity) {
    if (this.mList.contains(paramEventEntity))
      return; 
    this.mList.add(paramEventEntity);
    InnerEventHandler innerEventHandler = this.mEventHandler;
    if (innerEventHandler != null)
      innerEventHandler.saveEntity(paramEventEntity); 
  }
  
  protected void addOrUpdate(EventEntity paramEventEntity) {
    if (this.mList.contains(paramEventEntity)) {
      update(paramEventEntity);
      return;
    } 
    add(paramEventEntity);
  }
  
  public void clear() {
    InnerEventHandler innerEventHandler = this.mEventHandler;
    if (innerEventHandler != null)
      innerEventHandler.clearEntity(); 
    this.mList.clear();
  }
  
  public List<EventEntity> getFinalLogs() {
    return this.mList.isEmpty() ? Collections.emptyList() : this.mList;
  }
  
  public abstract boolean handle(EventEntity paramEventEntity);
  
  public EventEntity keepKeyVal(EventEntity paramEventEntity) {
    if (paramEventEntity.eventData.has("__inner_handled"))
      paramEventEntity.eventData.remove("__inner_handled"); 
    return paramEventEntity;
  }
  
  protected void onAlive(String paramString1, String paramString2) {}
  
  protected void putKeyVal(JSONObject paramJSONObject, String paramString, Object paramObject) {
    try {
      paramJSONObject.put(paramString, paramObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("AbsEventDelegate", "49411_build json put exp", exception);
      return;
    } 
  }
  
  protected boolean remove(EventEntity paramEventEntity) {
    InnerEventHandler innerEventHandler = this.mEventHandler;
    if (innerEventHandler != null)
      innerEventHandler.delEntity(paramEventEntity); 
    return this.mList.remove(paramEventEntity);
  }
  
  protected void update(EventEntity paramEventEntity) {
    if (!this.mList.contains(paramEventEntity))
      return; 
    List<EventEntity> list = this.mList;
    list.set(list.indexOf(paramEventEntity), paramEventEntity);
    InnerEventHandler innerEventHandler = this.mEventHandler;
    if (innerEventHandler != null)
      innerEventHandler.saveEntity(paramEventEntity); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\AbsEventDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */