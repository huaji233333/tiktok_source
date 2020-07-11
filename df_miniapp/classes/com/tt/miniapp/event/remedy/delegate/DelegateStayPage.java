package com.tt.miniapp.event.remedy.delegate;

import com.tt.miniapp.event.remedy.AbsEventDelegate;
import com.tt.miniapp.event.remedy.EventEntity;
import com.tt.miniapp.event.remedy.InnerEventHandler;

public class DelegateStayPage extends AbsEventDelegate {
  public DelegateStayPage(InnerEventHandler paramInnerEventHandler) {
    super(paramInnerEventHandler);
  }
  
  public boolean handle(EventEntity paramEventEntity) {
    if (!"stay_page".equals(paramEventEntity.eventName))
      return false; 
    if (paramEventEntity.innerHandled) {
      addOrUpdate(keepKeyVal(paramEventEntity));
      return true;
    } 
    return !remove(paramEventEntity);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\delegate\DelegateStayPage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */