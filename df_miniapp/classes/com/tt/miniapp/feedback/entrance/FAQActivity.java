package com.tt.miniapp.feedback.entrance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.k;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapp.feedback.report.ReportHelper;
import com.tt.miniapp.feedback.report.ReportNetHelper;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.UIUtils;
import com.tt.miniapphost.view.BaseActivity;
import com.tt.option.q.i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FAQActivity extends BaseActivity implements BaseFAQFragment.FAQPresenter {
  private AppInfoEntity mAppInfoEntity;
  
  public FeedbackParam mFeedbackParam;
  
  private k mFragmentManager;
  
  private IActivityResultHandler mHandler = null;
  
  public List<FAQItemVO> mItemList;
  
  public JSONArray mListJsonArray;
  
  public final Object mLock = new Object();
  
  public volatile String mOpenId;
  
  public AtomicBoolean mOpenIdPreloading;
  
  public long mSelectItemId = -1L;
  
  public static Intent genIntent(Context paramContext, FeedbackParam paramFeedbackParam, AppInfoEntity paramAppInfoEntity) {
    return genIntent(paramContext, paramFeedbackParam, paramAppInfoEntity, -1L);
  }
  
  public static Intent genIntent(Context paramContext, FeedbackParam paramFeedbackParam, AppInfoEntity paramAppInfoEntity, long paramLong) {
    Intent intent = new Intent(paramContext, FAQActivity.class);
    intent.putExtra("key_request_param", (Parcelable)paramFeedbackParam);
    intent.putExtra("key_appinfo_entity", (Parcelable)paramAppInfoEntity);
    intent.putExtra("key_selected_item_id", paramLong);
    return intent;
  }
  
  public static List<FAQItemVO> getFAQListFromJSONArray(JSONArray paramJSONArray) throws JSONException {
    ArrayList<FAQItemVO> arrayList = new ArrayList();
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayList.add(FAQItemVO.from(paramJSONArray.getJSONObject(i))); 
    return arrayList;
  }
  
  private void initIntent() {
    if (getIntent() == null)
      return; 
    this.mFeedbackParam = (FeedbackParam)getIntent().getParcelableExtra("key_request_param");
    this.mAppInfoEntity = (AppInfoEntity)getIntent().getParcelableExtra("key_appinfo_entity");
    this.mSelectItemId = getIntent().getLongExtra("key_selected_item_id", this.mSelectItemId);
  }
  
  private void preloadOpenId() {
    if (this.mFeedbackParam == null)
      return; 
    this.mOpenIdPreloading = new AtomicBoolean(true);
    Observable.create(new Action() {
          public void act() {
            FAQActivity fAQActivity = FAQActivity.this;
            fAQActivity.mOpenId = UserInfoManager.requestOpenId(fAQActivity.mFeedbackParam.getHostAid(), FAQActivity.this.mFeedbackParam.getAid());
          }
        }).schudleOn(ThreadPools.longIO()).subscribe(new Subscriber() {
          public void onError(Throwable param1Throwable) {
            if (FAQActivity.this.mOpenIdPreloading != null)
              FAQActivity.this.mOpenIdPreloading.set(false); 
            synchronized (FAQActivity.this.mLock) {
              FAQActivity.this.mLock.notify();
              return;
            } 
          }
          
          public void onSuccess() {
            if (FAQActivity.this.mOpenIdPreloading != null)
              FAQActivity.this.mOpenIdPreloading.set(false); 
            synchronized (FAQActivity.this.mLock) {
              FAQActivity.this.mLock.notify();
              return;
            } 
          }
          
          public void onSuccess(Object param1Object) {
            if (FAQActivity.this.mOpenIdPreloading != null)
              FAQActivity.this.mOpenIdPreloading.set(false); 
            synchronized (FAQActivity.this.mLock) {
              FAQActivity.this.mLock.notify();
              return;
            } 
          }
        });
  }
  
  public void addListFragment2Root(JSONArray paramJSONArray) {
    FAQListFragment fAQListFragment;
    if (paramJSONArray == null) {
      fAQListFragment = FAQListFragment.newInstance(false, null);
    } else {
      fAQListFragment = FAQListFragment.newInstance((JSONArray)fAQListFragment, false, null);
    } 
    this.mFragmentManager.a().a(2097545295, fAQListFragment).a(fAQListFragment.getClass().getSimpleName()).c();
  }
  
  public void addTargetFragment2Root(FAQItemVO paramFAQItemVO) {
    FAQListFragment fAQListFragment;
    JSONArray jSONArray = paramFAQItemVO.getChildren();
    if (jSONArray == null || jSONArray.length() <= 0) {
      FAQCommitFragment fAQCommitFragment;
      if (TextUtils.isEmpty(paramFAQItemVO.getValue()) || paramFAQItemVO.getValue().equals("null")) {
        fAQCommitFragment = FAQCommitFragment.newInstance(paramFAQItemVO);
      } else {
        FAQDetailFragment fAQDetailFragment = FAQDetailFragment.newInstance((FAQItemVO)fAQCommitFragment);
      } 
    } else {
      fAQListFragment = FAQListFragment.newInstance(jSONArray, false, null);
    } 
    this.mFragmentManager.a().a(2097545295, fAQListFragment).a(fAQListFragment.getClass().getSimpleName()).c();
  }
  
  public void finish() {
    super.finish();
    overridePendingTransition(2131034235, UIUtils.getSlideOutAnimation());
  }
  
  public k getActivitySupportFragmentManager() {
    return this.mFragmentManager;
  }
  
  public AppInfoEntity getAppInfoEntity() {
    return this.mAppInfoEntity;
  }
  
  public FeedbackParam getFeedbackParam() {
    return this.mFeedbackParam;
  }
  
  public String getOpenIdSync() {
    if (TextUtils.isEmpty(this.mOpenId)) {
      AtomicBoolean atomicBoolean = this.mOpenIdPreloading;
      if (atomicBoolean != null && atomicBoolean.get())
        synchronized (this.mLock) {
          boolean bool = TextUtils.isEmpty(this.mOpenId);
          if (bool)
            try {
              this.mLock.wait(1500L);
              return this.mOpenId;
            } catch (InterruptedException interruptedException) {
              AppBrandLogger.e("tma_FAQActivity", new Object[] { "", interruptedException });
              return CharacterUtils.empty();
            }  
        }  
    } 
    return this.mOpenId;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    IActivityResultHandler iActivityResultHandler = this.mHandler;
    if (iActivityResultHandler != null)
      iActivityResultHandler.handleActivityResult(paramInt1, paramInt2, paramIntent); 
  }
  
  public void onAddFragment(Fragment paramFragment1, Fragment paramFragment2) {
    this.mFragmentManager.a().a(2097545295, paramFragment2).b(paramFragment1).a(UIUtils.getSlideInAnimation(), 2131034242).a(paramFragment2.getClass().getSimpleName()).c();
  }
  
  public void onBackPressed() {
    if (getSupportFragmentManager().e() == 1) {
      finish();
      return;
    } 
    super.onBackPressed();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676292);
    ReportHelper.init();
    initIntent();
    this.mFragmentManager = getSupportFragmentManager();
    if (-1L == this.mSelectItemId) {
      addListFragment2Root((JSONArray)null);
    } else {
      requestItemList(new BaseFAQFragment.OnRequestDataCallback() {
            public void callbackItemList(List<FAQItemVO> param1List) {
              FAQItemVO fAQItemVO;
              FAQActivity fAQActivity;
              List list = null;
              if (param1List == null || param1List.isEmpty()) {
                FAQActivity.this.addListFragment2Root((JSONArray)null);
                return;
              } 
              Iterator<FAQItemVO> iterator = param1List.iterator();
              while (true) {
                param1List = list;
                if (iterator.hasNext()) {
                  fAQItemVO = iterator.next();
                  if (FAQActivity.this.mSelectItemId == fAQItemVO.getId())
                    break; 
                  continue;
                } 
                break;
              } 
              if (fAQItemVO == null) {
                fAQActivity = FAQActivity.this;
                fAQActivity.addListFragment2Root(fAQActivity.mListJsonArray);
                return;
              } 
              FAQActivity.this.addTargetFragment2Root((FAQItemVO)fAQActivity);
            }
          });
    } 
    preloadOpenId();
  }
  
  public void onPause() {
    super.onPause();
    if (ReportHelper.isReportOpen() && isFinishing())
      ReportNetHelper.clearOptionCache(); 
  }
  
  public void requestItemList(final BaseFAQFragment.OnRequestDataCallback callback) {
    List<FAQItemVO> list = this.mItemList;
    if (list != null && !list.isEmpty()) {
      callback.callbackItemList(this.mItemList);
      return;
    } 
    HostDependManager.getInst().showToast((Context)this, null, getString(2097741915), 10000L, "loading");
    Observable.create(new Function<String>() {
          public String fun() {
            StringBuilder stringBuilder = new StringBuilder();
            FeedbackParam feedbackParam = new FeedbackParam();
            feedbackParam.setFeedbackAppkey(FAQActivity.this.mFeedbackParam.getFeedbackAppkey());
            feedbackParam.setFeedbackAid(FAQActivity.this.mFeedbackParam.getFeedbackAid());
            feedbackParam.setFeedbackAppName(FAQActivity.this.mFeedbackParam.getFeedbackAppName());
            feedbackParam.setIid(FAQActivity.this.mFeedbackParam.getIid());
            feedbackParam.setChannel(FAQActivity.this.mFeedbackParam.getChannel());
            feedbackParam.setDeviceId(FAQActivity.this.mFeedbackParam.getDeviceId());
            stringBuilder.append(AppbrandConstant.SnssdkAPI.getInst().getFeedbackQuestionList());
            stringBuilder.append(feedbackParam.toParamString(FAQActivity.this.mFeedbackParam.getFeedbackAppkey(), FAQActivity.this.mFeedbackParam.getFeedbackAid(), FAQActivity.this.mFeedbackParam.getFeedbackAppName()));
            Locale locale = LocaleManager.getInst().getCurrentLocale();
            if (locale != null) {
              String str1 = locale.getLanguage();
              stringBuilder.append("&lang=");
              stringBuilder.append(str1);
            } 
            String str = stringBuilder.toString();
            return NetManager.getInst().request(new i(str, "GET", false)).a();
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            ThreadUtil.runOnUIThread(new Runnable() {
                  public void run() {
                    HostDependManager.getInst().hideToast();
                  }
                });
            AppBrandLogger.e("tma_FAQActivity", new Object[] { "requestData", param1Throwable });
          }
          
          public void onSuccess(String param1String) {
            try {
              JSONObject jSONObject = new JSONObject(param1String);
              FAQActivity.this.mListJsonArray = jSONObject.optJSONArray("list");
              ReportHelper.tryInsertReportItem(FAQActivity.this.mListJsonArray);
              FAQActivity.this.mItemList = FAQActivity.getFAQListFromJSONArray(FAQActivity.this.mListJsonArray);
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      HostDependManager.getInst().hideToast();
                      callback.callbackItemList(FAQActivity.this.mItemList);
                    }
                  });
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_FAQActivity", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
  
  public void setActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {
    this.mHandler = paramIActivityResultHandler;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\FAQActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */