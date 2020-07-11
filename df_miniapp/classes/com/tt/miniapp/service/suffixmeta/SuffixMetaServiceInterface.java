package com.tt.miniapp.service.suffixmeta;

import com.bytedance.sandboxapp.b.b;

public interface SuffixMetaServiceInterface extends b {
  SuffixMetaEntity get();
  
  SuffixMetaEntity get(boolean paramBoolean);
  
  SuffixMetaEntity getOrNull(boolean paramBoolean);
  
  void getRemoteImmediately(SuffixMetaListener paramSuffixMetaListener);
  
  void removeLocalCache(SuffixMetaEntity.PROPERTY paramPROPERTY, boolean paramBoolean);
  
  void requestSuffixMeta();
  
  void subscribe(SuffixMetaListener paramSuffixMetaListener);
  
  void unsubscribe(SuffixMetaListener paramSuffixMetaListener);
  
  public static interface SuffixMetaListener {
    void onError(String param1String);
    
    void onSuccess(SuffixMetaEntity param1SuffixMetaEntity);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\suffixmeta\SuffixMetaServiceInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */