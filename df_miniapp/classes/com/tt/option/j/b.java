package com.tt.option.j;

import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import java.util.List;

public interface b {
  List<IAsyncHostDataHandler> createAsyncHostDataHandlerList();
  
  List<ISyncHostDataHandler> createSyncHostDataHandlerList();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\j\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */