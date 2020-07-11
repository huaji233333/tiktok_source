package com.tt.miniapp.suffixmeta;

import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;

class SuffixMetaResponse {
  String errorMsg;
  
  String originData;
  
  SuffixMetaEntity suffixMetaEntity;
  
  SuffixMetaResponse(String paramString, SuffixMetaEntity paramSuffixMetaEntity) {
    this.originData = paramString;
    this.suffixMetaEntity = paramSuffixMetaEntity;
  }
  
  SuffixMetaResponse(String paramString1, SuffixMetaEntity paramSuffixMetaEntity, String paramString2) {
    this.originData = paramString1;
    this.suffixMetaEntity = paramSuffixMetaEntity;
    this.errorMsg = paramString2;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\suffixmeta\SuffixMetaResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */