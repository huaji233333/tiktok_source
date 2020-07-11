package com.tt.miniapp.maplocate;

import android.content.Context;
import com.tt.option.a;
import com.tt.option.m.a;

public class AbstractHostOptionMapLocateDepend extends a<HostOptionMapLocateDepend> implements HostOptionMapLocateDepend {
  public ILocator createLocateInstance(Context paramContext) {
    return inject() ? ((HostOptionMapLocateDepend)this.defaultOptionDepend).createLocateInstance(paramContext) : null;
  }
  
  public a createMapInstance() {
    return inject() ? ((HostOptionMapLocateDepend)this.defaultOptionDepend).createMapInstance() : null;
  }
  
  protected HostOptionMapLocateDepend init() {
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\maplocate\AbstractHostOptionMapLocateDepend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */