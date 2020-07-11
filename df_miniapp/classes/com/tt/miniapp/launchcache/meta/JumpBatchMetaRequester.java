package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import com.tt.miniapp.launchcache.RequestType;
import d.f.b.l;
import java.util.List;

public final class JumpBatchMetaRequester extends BaseBatchMetaRequester {
  public JumpBatchMetaRequester(Context paramContext) {
    super(paramContext, RequestType.silence);
  }
  
  public final void onSaveMetaList(List<? extends RequestResultInfo> paramList) {
    l.b(paramList, "requestInfoList");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\JumpBatchMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */