package com.tt.miniapp.launchschedule;

import java.util.ArrayList;

class ILaunchStatus {
  static int STATUS_FIRST_PAGE_RENDER_SUCCESS;
  
  static int STATUS_INIT;
  
  static int STATUS_LOAD_ENTRY_PAGE_SUCCESS;
  
  static int STATUS_LOAD_MAIN_JS_SUCCESS;
  
  static int STATUS_LOAD_PAGE_FRAME_JS_SUCCESS;
  
  static int STATUS_LOAD_TMA_CORE_SUCCESS;
  
  static int STATUS_LOAD_TMG_CORE_SUCCESS;
  
  static int STATUS_LOAD_TMPLATE_HTML_SUCCESS;
  
  static int STATUS_META_SUCCESS;
  
  static int STATUS_PKG_INSTALL_SUCCESS;
  
  private static ArrayList<Status> sStatusList = new ArrayList<Status>();
  
  static {
    STATUS_INIT = addStatus(0, "st_init");
    STATUS_LOAD_TMA_CORE_SUCCESS = addStatus(8, "st_load_tma_core");
    STATUS_LOAD_TMG_CORE_SUCCESS = addStatus(8, "st_load_tmg_core");
    STATUS_LOAD_TMPLATE_HTML_SUCCESS = addStatus(10, "st_load_template_html");
    STATUS_META_SUCCESS = addStatus(9, "st_meta");
    STATUS_PKG_INSTALL_SUCCESS = addStatus(13, "st_pkg_install");
    STATUS_LOAD_MAIN_JS_SUCCESS = addStatus(27, "st_load_main_js");
    STATUS_LOAD_PAGE_FRAME_JS_SUCCESS = addStatus(28, "st_load_page_frame_js");
    STATUS_LOAD_ENTRY_PAGE_SUCCESS = addStatus(3, "st_dom_ready");
    STATUS_FIRST_PAGE_RENDER_SUCCESS = addStatus(2, "st_first_page_render");
  }
  
  private static int addStatus(int paramInt, String paramString) {
    int i = sStatusList.size();
    sStatusList.add(new Status(i, paramInt, paramString));
    return i;
  }
  
  static Status[] statusArray() {
    return sStatusList.<Status>toArray(new Status[0]);
  }
  
  static class Status {
    final int mIdx;
    
    final String mName;
    
    final int mScore;
    
    protected Status(int param1Int1, int param1Int2, String param1String) {
      this.mIdx = param1Int1;
      this.mScore = param1Int2;
      this.mName = param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\ILaunchStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */