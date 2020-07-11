package com.tt.miniapp.audio;

import android.text.TextUtils;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AudioStateModule {
  public int audioId;
  
  public boolean autoplay;
  
  public String callProcessName;
  
  public long duration;
  
  public String encryptToken;
  
  public boolean isBgAudio;
  
  public boolean loop;
  
  public String miniAppId;
  
  public boolean obeyMuteSwitch;
  
  public boolean paused;
  
  public String src;
  
  public int startTime;
  
  public float volume;
  
  public static AudioStateModule parse(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    AudioStateModule audioStateModule = new AudioStateModule();
    audioStateModule.audioId = jSONObject.optInt("audioId");
    audioStateModule.src = jSONObject.optString("src");
    audioStateModule.encryptToken = jSONObject.optString("encrypt_token");
    AppBrandLogger.d("tma_AudioStateModule", new Object[] { "before ", audioStateModule.src });
    audioStateModule.src = StreamLoader.waitExtractFinishIfNeeded(FileManager.inst().getRealFilePath(audioStateModule.src));
    AppBrandLogger.d("tma_AudioStateModule", new Object[] { "after ", audioStateModule.src });
    audioStateModule.startTime = jSONObject.optInt("startTime");
    audioStateModule.paused = jSONObject.optBoolean("paused");
    audioStateModule.duration = jSONObject.optLong("duration");
    audioStateModule.obeyMuteSwitch = jSONObject.optBoolean("obeyMuteSwitch");
    audioStateModule.autoplay = jSONObject.optBoolean("autoplay");
    audioStateModule.loop = jSONObject.optBoolean("loop");
    audioStateModule.volume = (float)jSONObject.optDouble("volume");
    return audioStateModule;
  }
  
  public static AudioStateModule parseFromJSONStr(String paramString) {
    if (!TextUtils.isEmpty(paramString))
      return null; 
    JSONParser jSONParser = new JSONParser();
    try {
      JSONObject jSONObject = (JSONObject)jSONParser.parse(paramString);
      AudioStateModule audioStateModule = new AudioStateModule();
      audioStateModule.src = jSONObject.optString("src");
      audioStateModule.encryptToken = jSONObject.optString("encryptToken");
      audioStateModule.startTime = jSONObject.optInt("startTime");
      audioStateModule.paused = jSONObject.optBoolean("paused");
      audioStateModule.duration = jSONObject.optLong("duration");
      audioStateModule.obeyMuteSwitch = jSONObject.optBoolean("obeyMuteSwitch");
      audioStateModule.autoplay = jSONObject.optBoolean("autoplay");
      audioStateModule.loop = jSONObject.optBoolean("loop");
      audioStateModule.audioId = jSONObject.optInt("audioId");
      audioStateModule.volume = jSONObject.optInt("volume");
      return audioStateModule;
    } catch (ParseException parseException) {
      AppBrandLogger.e("tma_AudioStateModule", new Object[] { "", parseException });
      return null;
    } 
  }
  
  public String toJSONStr() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("src", this.src);
      jSONObject.put("encryptToken", this.encryptToken);
      jSONObject.put("startTime", this.startTime);
      jSONObject.put("paused", this.paused);
      jSONObject.put("duration", this.duration);
      jSONObject.put("obeyMuteSwitch", this.obeyMuteSwitch);
      jSONObject.put("autoplay", this.autoplay);
      jSONObject.put("loop", this.loop);
      jSONObject.put("audioId", this.audioId);
      jSONObject.put("volume", this.volume);
      return jSONObject.toString();
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_AudioStateModule", new Object[] { "", jSONException });
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\audio\AudioStateModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */