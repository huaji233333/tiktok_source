package com.tt.miniapp.launchcache.pkg;

import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.l;
import java.io.File;
import java.util.Map;

public final class PkgRequestContext {
  private final AppInfoEntity appInfo;
  
  private String downloadUrl;
  
  private String errCode;
  
  private String errMsg;
  
  private Map<String, String> extraInfo;
  
  private long httpContentLength;
  
  private int httpStatusCode;
  
  private boolean isNetDownload;
  
  private final StreamDownloadInstallListener listener;
  
  private int monitorStatus;
  
  private File pkgFile;
  
  private long useTime;
  
  public PkgRequestContext(AppInfoEntity paramAppInfoEntity, StreamDownloadInstallListener paramStreamDownloadInstallListener) {
    this.appInfo = paramAppInfoEntity;
    this.listener = paramStreamDownloadInstallListener;
    this.errCode = "";
    this.errMsg = "";
  }
  
  public final AppInfoEntity getAppInfo() {
    return this.appInfo;
  }
  
  public final String getDownloadUrl() {
    return this.downloadUrl;
  }
  
  public final String getErrCode() {
    return this.errCode;
  }
  
  public final String getErrMsg() {
    return this.errMsg;
  }
  
  public final Map<String, String> getExtraInfo() {
    return this.extraInfo;
  }
  
  public final long getHttpContentLength() {
    return this.httpContentLength;
  }
  
  public final int getHttpStatusCode() {
    return this.httpStatusCode;
  }
  
  public final StreamDownloadInstallListener getListener() {
    return this.listener;
  }
  
  public final int getMonitorStatus() {
    return this.monitorStatus;
  }
  
  public final File getPkgFile() {
    return this.pkgFile;
  }
  
  public final long getUseTime() {
    return this.useTime;
  }
  
  public final boolean isNetDownload() {
    return this.isNetDownload;
  }
  
  public final void setDownloadUrl(String paramString) {
    this.downloadUrl = paramString;
  }
  
  public final void setErrCode(String paramString) {
    l.b(paramString, "<set-?>");
    this.errCode = paramString;
  }
  
  public final void setErrMsg(String paramString) {
    l.b(paramString, "<set-?>");
    this.errMsg = paramString;
  }
  
  public final void setExtraInfo(Map<String, String> paramMap) {
    this.extraInfo = paramMap;
  }
  
  public final void setHttpContentLength(long paramLong) {
    this.httpContentLength = paramLong;
  }
  
  public final void setHttpStatusCode(int paramInt) {
    this.httpStatusCode = paramInt;
  }
  
  public final void setMonitorStatus(int paramInt) {
    this.monitorStatus = paramInt;
  }
  
  public final void setNetDownload(boolean paramBoolean) {
    this.isNetDownload = paramBoolean;
  }
  
  public final void setPkgFile(File paramFile) {
    this.pkgFile = paramFile;
  }
  
  public final void setUseTime(long paramLong) {
    this.useTime = paramLong;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\PkgRequestContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */