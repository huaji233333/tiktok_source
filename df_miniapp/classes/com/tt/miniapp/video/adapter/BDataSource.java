package com.tt.miniapp.video.adapter;

import com.ss.ttvideoengine.DataSource;
import java.util.Map;

public interface BDataSource extends DataSource {
  String apiForFetcher(Map paramMap);
  
  String apiForFetcher(Map<String, String> paramMap, int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\adapter\BDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */