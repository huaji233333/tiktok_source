package com.tt.miniapp.event.remedy.delegate;

import com.tt.miniapp.event.remedy.AbsEventDelegate;
import com.tt.miniapp.event.remedy.EventEntity;
import com.tt.miniapp.event.remedy.InnerEventHandler;

public class DelegateLoadDetail extends AbsEventDelegate {
  public DelegateLoadDetail(InnerEventHandler paramInnerEventHandler) {
    super(paramInnerEventHandler);
  }
  
  public boolean handle(EventEntity paramEventEntity) {
    if (!"load_detail".equals(paramEventEntity.eventName))
      return false; 
    if (paramEventEntity.innerHandled) {
      addOrUpdate(keepKeyVal(paramEventEntity));
      return true;
    } 
    boolean bool = remove(paramEventEntity);
    return (paramEventEntity.eventData.optInt("load_type", 1) == 0 && !bool);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\delegate\DelegateLoadDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */