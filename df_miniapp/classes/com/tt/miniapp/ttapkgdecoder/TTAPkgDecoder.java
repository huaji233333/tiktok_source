package com.tt.miniapp.ttapkgdecoder;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.streamloader.LoadTask;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.ttapkgdecoder.downloader.DefaultStreamFetcher;
import com.tt.miniapp.ttapkgdecoder.downloader.IStreamFetcher;
import com.tt.miniapp.ttapkgdecoder.reader.TTAPkgReader;
import com.tt.miniapp.ttapkgdecoder.source.ISource;
import com.tt.miniapp.ttapkgdecoder.source.MappedByteBufferDiskSource;
import com.tt.miniapp.ttapkgdecoder.source.OkHttpSource;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapp.ttapkgdecoder.utils.OkioTools;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.LaunchThreadPool;
import java.io.File;
import java.util.concurrent.ExecutorService;

public class TTAPkgDecoder implements OkioTools.OnProgressChangeListener {
  public DecoderCallback mCallback;
  
  private ExecutorService mDecodeThreadPool;
  
  public IStreamFetcher mDownloader;
  
  private String mErrorMsg;
  
  private File mFile;
  
  private TTAPkgReader mReader;
  
  public TTAPkgInfo mTTAPkgInfo;
  
  public String mUrl;
  
  private int mVersion;
  
  private TTAPkgDecoder(File paramFile) {
    this.mFile = paramFile;
    setup();
  }
  
  private TTAPkgDecoder(String paramString) {
    this.mUrl = paramString;
    setup();
  }
  
  public static DiskLoadBuilder create(File paramFile) {
    return new DiskLoadBuilder(new TTAPkgDecoder(paramFile));
  }
  
  public static NetLoadBuilder create(String paramString) {
    return new NetLoadBuilder(new TTAPkgDecoder(paramString));
  }
  
  private int decode(ISource paramISource) {
    try {
      byte b;
      paramISource.setOnProgressChangeListener((OkioTools.OnProgressChangeListener)this);
      this.mReader = new TTAPkgReader(paramISource);
      MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class);
      LoadTask loadTask = StreamLoader.getLoadTask();
      if (loadTask != null) {
        Boolean bool = loadTask.isUseLocalPkg();
      } else {
        loadTask = null;
      } 
      MpTimeLineReporter.ExtraBuilder extraBuilder2 = new MpTimeLineReporter.ExtraBuilder();
      if (loadTask != null) {
        if (loadTask.booleanValue()) {
          b = 2;
        } else {
          b = 0;
        } 
      } else {
        b = -1;
      } 
      MpTimeLineReporter.ExtraBuilder extraBuilder1 = extraBuilder2.kv("pkg_type", Integer.valueOf(b));
      if ((AppbrandApplicationImpl.getInst().getAppInfo()).getFromType == 1) {
        b = 1;
      } else {
        b = 0;
      } 
      mpTimeLineReporter.addPoint("parse_ttpkg_header_begin", extraBuilder1.kv("meta_type", Integer.valueOf(b)).build());
      if (this.mReader.checkMagicString()) {
        this.mVersion = this.mReader.readVersion();
        TimeLogger.getInstance().logTimeDuration(new String[] { "tma_TTAPkgDecoder_start_readTTPkgInfo" });
        this.mTTAPkgInfo = this.mReader.readTTPkgInfo();
        TimeLogger.getInstance().logTimeDuration(new String[] { "tma_TTAPkgDecoder_stop_readTTPkgInfo" });
        mpTimeLineReporter.addPoint("parse_ttpkg_header_end");
        if (this.mCallback != null)
          this.mCallback.onLoadHeader(this.mVersion, this.mTTAPkgInfo); 
        if (!(paramISource instanceof com.tt.miniapp.ttapkgdecoder.source.DiskSource))
          while (true) {
            Pair pair = this.mReader.readNextFile(this.mCallback);
            if (pair != null) {
              if (this.mCallback != null)
                this.mCallback.onDecodeFile((TTAPkgFile)pair.first, (byte[])pair.second); 
              continue;
            } 
            break;
          }  
        ThreadUtil.runOnWorkThread(new Action() {
              public void act() {
                if (TTAPkgDecoder.this.mDownloader != null)
                  TTAPkgDecoder.this.mDownloader.onReadFinished(); 
                if (TTAPkgDecoder.this.mCallback != null)
                  TTAPkgDecoder.this.mCallback.onDecodeFinish(TTAPkgDecoder.this.mTTAPkgInfo); 
              }
            },  (Scheduler)LaunchThreadPool.getInst());
        TTAPkgReader tTAPkgReader1 = this.mReader;
        if (tTAPkgReader1 != null && !tTAPkgReader1.isReleased())
          this.mReader.release(); 
        return 0;
      } 
      this.mErrorMsg = "magic string \"TPKG\" check fail!";
      throw new DecodeException(-3);
    } catch (DecodeException decodeException) {
      AppBrandLogger.e("tma_TTAPkgDecoder", new Object[] { "decode ttpkg fail ", decodeException });
      if (TextUtils.isEmpty(this.mErrorMsg))
        this.mErrorMsg = Log.getStackTraceString((Throwable)decodeException); 
      int i = decodeException.getErrorCode();
      TTAPkgReader tTAPkgReader1 = this.mReader;
      if (tTAPkgReader1 != null && !tTAPkgReader1.isReleased())
        this.mReader.release(); 
      return i;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_TTAPkgDecoder", new Object[] { "decode ttpkg fail ", exception });
      if (TextUtils.isEmpty(this.mErrorMsg))
        this.mErrorMsg = Log.getStackTraceString(exception); 
      TTAPkgReader tTAPkgReader1 = this.mReader;
      if (tTAPkgReader1 != null && !tTAPkgReader1.isReleased())
        this.mReader.release(); 
      return -4;
    } finally {}
    TTAPkgReader tTAPkgReader = this.mReader;
    if (tTAPkgReader != null && !tTAPkgReader.isReleased())
      this.mReader.release(); 
    throw paramISource;
  }
  
  private void notifyProgressChange(long paramLong) {
    if (this.mCallback != null) {
      TTAPkgReader tTAPkgReader = this.mReader;
      if (tTAPkgReader != null && paramLong > 0L && tTAPkgReader.getByteSize() > 0L)
        this.mCallback.onDecodeProgress((int)((float)paramLong / (float)this.mReader.getByteSize() * 100.0F)); 
    } 
  }
  
  private void setup() {
    this.mDecodeThreadPool = _lancet.com_ss_android_ugc_aweme_lancet_ThreadPoolLancet_newSingleThreadExecutor();
  }
  
  public void loadWithFile(ISource paramISource) {
    int i = decode(paramISource);
    if (i != 0) {
      DecoderCallback decoderCallback = this.mCallback;
      if (decoderCallback != null)
        decoderCallback.onDecodeFail(i, this.mErrorMsg); 
    } 
  }
  
  public void loadWithUrl(String paramString, IStreamFetcher paramIStreamFetcher) {
    byte b;
    if (paramString != null && !paramString.isEmpty()) {
      b = decode((ISource)new OkHttpSource(paramString, paramIStreamFetcher));
    } else {
      b = -1;
    } 
    if (b != 0) {
      DecoderCallback decoderCallback = this.mCallback;
      if (decoderCallback != null)
        decoderCallback.onDecodeFail(b, this.mErrorMsg); 
    } 
  }
  
  public void onProgressChange(long paramLong) {
    notifyProgressChange(paramLong);
  }
  
  public void realDiskStreamLoad(DecoderCallback paramDecoderCallback) {
    final MappedByteBufferDiskSource finalSource;
    if (paramDecoderCallback != null)
      this.mCallback = paramDecoderCallback; 
    DecoderCallback decoderCallback = null;
    File file = this.mFile;
    paramDecoderCallback = decoderCallback;
    if (file != null) {
      paramDecoderCallback = decoderCallback;
      if (file.exists())
        mappedByteBufferDiskSource = new MappedByteBufferDiskSource(this.mFile); 
    } 
    this.mDecodeThreadPool.execute(new Runnable() {
          public void run() {
            ISource iSource = finalSource;
            if (iSource != null) {
              TTAPkgDecoder.this.loadWithFile(iSource);
            } else if (TTAPkgDecoder.this.mCallback != null) {
              TTAPkgDecoder.this.mCallback.onDecodeFail(-1, "invalid file!");
            } 
            TTAPkgDecoder.this.release();
          }
        });
  }
  
  public void realNetStreamLoad(DecoderCallback paramDecoderCallback, IStreamFetcher paramIStreamFetcher) {
    if (paramDecoderCallback != null)
      this.mCallback = paramDecoderCallback; 
    if (paramIStreamFetcher == null) {
      this.mDownloader = (IStreamFetcher)new DefaultStreamFetcher();
    } else {
      this.mDownloader = paramIStreamFetcher;
    } 
    this.mDecodeThreadPool.execute(new Runnable() {
          public void run() {
            if (TTAPkgDecoder.this.mUrl != null && !TTAPkgDecoder.this.mUrl.isEmpty()) {
              TTAPkgDecoder tTAPkgDecoder = TTAPkgDecoder.this;
              tTAPkgDecoder.loadWithUrl(tTAPkgDecoder.mUrl, TTAPkgDecoder.this.mDownloader);
            } else if (TTAPkgDecoder.this.mCallback != null) {
              TTAPkgDecoder.this.mCallback.onDecodeFail(-5, "invalid url!");
            } 
            TTAPkgDecoder.this.release();
          }
        });
  }
  
  public void release() {
    if (!this.mDecodeThreadPool.isShutdown())
      this.mDecodeThreadPool.shutdown(); 
  }
  
  public static class DiskLoadBuilder {
    TTAPkgDecoder mTTAPkgDecoder;
    
    public DiskLoadBuilder(TTAPkgDecoder param1TTAPkgDecoder) {
      this.mTTAPkgDecoder = param1TTAPkgDecoder;
    }
    
    public TTAPkgDecoder asyncLoad(DecoderCallback param1DecoderCallback) {
      this.mTTAPkgDecoder.realDiskStreamLoad(param1DecoderCallback);
      return this.mTTAPkgDecoder;
    }
  }
  
  public static class NetLoadBuilder {
    private IStreamFetcher mIStreamFetcher;
    
    TTAPkgDecoder mTTAPkgDecoder;
    
    public NetLoadBuilder(TTAPkgDecoder param1TTAPkgDecoder) {
      this.mTTAPkgDecoder = param1TTAPkgDecoder;
    }
    
    public TTAPkgDecoder asyncLoad(DecoderCallback param1DecoderCallback) {
      this.mTTAPkgDecoder.realNetStreamLoad(param1DecoderCallback, this.mIStreamFetcher);
      return this.mTTAPkgDecoder;
    }
    
    public NetLoadBuilder setNetDownloader(IStreamFetcher param1IStreamFetcher) {
      this.mIStreamFetcher = param1IStreamFetcher;
      return this;
    }
  }
  
  class TTAPkgDecoder {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\TTAPkgDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */