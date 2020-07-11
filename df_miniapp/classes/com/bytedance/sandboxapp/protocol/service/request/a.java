package com.bytedance.sandboxapp.protocol.service.request;

import com.bytedance.sandboxapp.b.b;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.bytedance.sandboxapp.protocol.service.request.entity.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.b;

public interface a extends b {
  void addDownloadRequest(a.d paramd, a.a parama);
  
  void addHttpRequest(HttpRequest.RequestTask paramRequestTask, HttpRequest.a parama);
  
  void addUploadRequest(b.d paramd, b.a parama);
  
  void operateDownloadRequest(int paramInt, String paramString);
  
  void operateHttpRequest(int paramInt, String paramString);
  
  void operateUploadRequest(int paramInt, String paramString);
  
  HttpRequest.RequestResult syncHttpRequest(HttpRequest.RequestTask paramRequestTask);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\request\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */