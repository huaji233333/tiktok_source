package com.tt.miniapp.feedback.entrance;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.b;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.a;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.feedback.FeedbackImgUploadHelper;
import com.tt.miniapp.feedback.entrance.image.ImageClickListener;
import com.tt.miniapp.feedback.entrance.image.ImageLoaderInterface;
import com.tt.miniapp.feedback.entrance.image.ImageLoaderUtil;
import com.tt.miniapp.feedback.entrance.image.ImageModel;
import com.tt.miniapp.feedback.entrance.image.ImageUploadView;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapp.feedback.entrance.vo.ImageInfoVO;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.apm.AppbrandApmService;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.n.b;
import com.tt.option.q.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FAQCommitFragment extends BaseFAQFragment implements ImageClickListener {
  private TextWatcher contactTextWatcher = new TextWatcher() {
      public void afterTextChanged(Editable param1Editable) {
        if (param1Editable.length() > 0) {
          UIUtils.setViewVisibility(FAQCommitFragment.this.mContactClearBtn, 0);
          if (FAQCommitFragment.this.hasChinese(param1Editable.toString())) {
            FAQCommitFragment.this.mContactCorrect = false;
          } else {
            FAQCommitFragment.this.mContactCorrect = true;
          } 
        } else {
          UIUtils.setViewVisibility(FAQCommitFragment.this.mContactClearBtn, 8);
          FAQCommitFragment.this.mContactCorrect = true;
        } 
        FAQCommitFragment.this.refreshState();
      }
      
      public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
      
      public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
    };
  
  private TextWatcher feedbackTextWatcher = new TextWatcher() {
      public void afterTextChanged(Editable param1Editable) {
        if (param1Editable.length() > 0 && param1Editable.length() <= 300) {
          FAQCommitFragment.this.mFeedbackContentCorrect = true;
        } else {
          FAQCommitFragment.this.mFeedbackContentCorrect = false;
        } 
        if (param1Editable.length() >= 300) {
          FAQCommitFragment.this.mFeedbackContentLengthTextView.setText(a.a(FAQCommitFragment.this.mNumberCount, new Object[] { Integer.valueOf(param1Editable.length()) }));
          UIUtils.setViewVisibility((View)FAQCommitFragment.this.mFeedbackContentLengthTextView, 0);
        } else {
          UIUtils.setViewVisibility((View)FAQCommitFragment.this.mFeedbackContentLengthTextView, 4);
        } 
        FAQCommitFragment.this.refreshState();
      }
      
      public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
      
      public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
    };
  
  private int mAvailableColor;
  
  public View mContactClearBtn;
  
  public boolean mContactCorrect = true;
  
  public EditText mContactEditText;
  
  private View mContactErrorLayout;
  
  public boolean mFeedbackContentCorrect = false;
  
  private EditText mFeedbackContentEditText;
  
  public TextView mFeedbackContentLengthTextView;
  
  public List<ImageInfoVO> mImageResultList = Collections.synchronizedList(new ArrayList<ImageInfoVO>(3));
  
  public FAQItemVO mItem;
  
  public String mNumberCount;
  
  private View mSceneSelectLayout;
  
  public TextView mSceneTextView;
  
  private TextView mSendTextView;
  
  private int mUnavailableColor;
  
  public ImageUploadView mUploadView;
  
  private void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    HostDependManager.getInst().chooseImage(paramActivity, paramInt, paramBoolean1, paramBoolean2, new b.b() {
          public void onCancel() {}
          
          public void onFail(String param1String) {}
          
          public void onSuccess(List<MediaEntity> param1List) {
            if (param1List != null && !param1List.isEmpty())
              for (MediaEntity mediaEntity : param1List)
                FAQCommitFragment.this.uploadImage(mediaEntity);  
          }
        },  new b.a() {
          public void setActivityResultHandler(IActivityResultHandler param1IActivityResultHandler) {
            FAQCommitFragment.this.mPresenter.setActivityResultHandler(param1IActivityResultHandler);
          }
        });
  }
  
  private void enableSend() {
    this.mSendTextView.setTextColor(this.mAvailableColor);
    this.mSendTextView.setEnabled(true);
  }
  
  public static FAQCommitFragment newInstance(FAQItemVO paramFAQItemVO) {
    FAQCommitFragment fAQCommitFragment = new FAQCommitFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("key_faq_item", (Parcelable)paramFAQItemVO);
    fAQCommitFragment.setArguments(bundle);
    return fAQCommitFragment;
  }
  
  private void requestAlbum(Activity paramActivity, int paramInt) {
    if (b.a((Context)paramActivity, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
      requestPermissions(new String[] { "android.permission.READ_EXTERNAL_STORAGE" }, 66);
      return;
    } 
    chooseImage(paramActivity, paramInt, true, true);
  }
  
  private void unableSend() {
    this.mSendTextView.setTextColor(this.mUnavailableColor);
    this.mSendTextView.setEnabled(false);
  }
  
  public void addImageClickListener() {
    requestAlbum(this.mActivity, 3 - this.mUploadView.getImageList().size());
  }
  
  public void delImageClickListener(int paramInt) {
    if (paramInt < this.mImageResultList.size()) {
      this.mUploadView.delImage(paramInt);
      this.mImageResultList.remove(paramInt);
    } 
  }
  
  public String getContact() {
    return this.mContactEditText.getText().toString();
  }
  
  public String getContent() {
    return this.mFeedbackContentEditText.getText().toString();
  }
  
  public String getDeviceType() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(DevicesUtil.getBrand());
    stringBuilder.append("+");
    stringBuilder.append(DevicesUtil.getModel());
    return stringBuilder.toString();
  }
  
  public String getHostVersion(String paramString) {
    return CharacterUtils.splitCharByPoints(paramString);
  }
  
  protected int getLayoutId() {
    return 2097676308;
  }
  
  public String getSystemVersion() {
    return DevicesUtil.getSystem();
  }
  
  public boolean hasChinese(String paramString) {
    for (int i = 0; i < paramString.length(); i++) {
      char c = paramString.charAt(i);
      if (c >= '一' && c <= '龥')
        return true; 
    } 
    return false;
  }
  
  public void hideSoftInput(View paramView) {
    ((InputMethodManager)this.mActivity.getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
  }
  
  protected void initFragment() {
    super.initFragment();
    Bundle bundle = getArguments();
    if (bundle != null)
      this.mItem = (FAQItemVO)bundle.getParcelable("key_faq_item"); 
  }
  
  protected void initTitleBar() {
    super.initTitleBar();
    ((TextView)this.mRoot.findViewById(2097545358)).setText(getText(2097741900));
    this.mSendTextView = (TextView)this.mRoot.findViewById(2097545300);
    UIUtils.setViewVisibility((View)this.mSendTextView, 0);
    this.mSendTextView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Event.builder("mp_feedback_upload", FAQCommitFragment.this.mAppInfoEntity).flush();
            FAQCommitFragment.this.submit();
          }
        });
  }
  
  protected void initView() {
    String str;
    super.initView();
    this.mUploadView = (ImageUploadView)this.mRoot.findViewById(2097545297);
    this.mUploadView.setImageClickListener(this).setImageLoaderInterface((ImageLoaderInterface)new ImageLoaderUtil()).setShowDel(true).setOneLineShowNum(3).setMaxNum(3);
    int j = UIUtils.getScreenWidth((Context)this.mActivity);
    int i = (int)UIUtils.dip2Px((Context)this.mActivity, 15.0F);
    j = (int)(j - UIUtils.dip2Px((Context)this.mActivity, 3.0F) * 2.0F - (i * 2)) / 3;
    UIUtils.updateLayoutMargin((View)this.mUploadView, i, -3, i, -3);
    this.mUploadView.setmPicSize(j);
    this.mFeedbackContentEditText = (EditText)this.mRoot.findViewById(2097545296);
    InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(300);
    this.mFeedbackContentEditText.setFilters(new InputFilter[] { (InputFilter)lengthFilter });
    this.mFeedbackContentEditText.addTextChangedListener(this.feedbackTextWatcher);
    this.mFeedbackContentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          public void onFocusChange(View param1View, boolean param1Boolean) {
            if (!param1Boolean)
              FAQCommitFragment.this.hideSoftInput(param1View); 
          }
        });
    this.mContactEditText = (EditText)this.mRoot.findViewById(2097545271);
    this.mContactEditText.addTextChangedListener(this.contactTextWatcher);
    this.mContactEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          public void onFocusChange(View param1View, boolean param1Boolean) {
            if (!param1Boolean)
              FAQCommitFragment.this.hideSoftInput(param1View); 
          }
        });
    this.mSceneTextView = (TextView)this.mRoot.findViewById(2097545298);
    TextView textView = this.mSceneTextView;
    FAQItemVO fAQItemVO = this.mItem;
    if (fAQItemVO != null) {
      str = fAQItemVO.getName();
    } else {
      str = "";
    } 
    textView.setText(str);
    this.mContactClearBtn = this.mRoot.findViewById(2097545278);
    this.mContactClearBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FAQCommitFragment.this.mContactEditText.setText("");
          }
        });
    this.mContactErrorLayout = this.mRoot.findViewById(2097545279);
    this.mFeedbackContentLengthTextView = (TextView)this.mRoot.findViewById(2097545301);
    this.mSceneSelectLayout = this.mRoot.findViewById(2097545299);
    this.mSceneSelectLayout.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            FAQListFragment fAQListFragment = FAQListFragment.newInstance(true, new FAQListFragment.OnItemSelectedListener() {
                  public void onItemSelected(FAQItemVO param2FAQItemVO) {
                    String str;
                    FAQCommitFragment.this.mItem = param2FAQItemVO;
                    TextView textView = FAQCommitFragment.this.mSceneTextView;
                    if (FAQCommitFragment.this.mItem != null) {
                      str = FAQCommitFragment.this.mItem.getName();
                    } else {
                      str = "";
                    } 
                    textView.setText(str);
                  }
                });
            FAQCommitFragment.this.mPresenter.onAddFragment(FAQCommitFragment.this, fAQListFragment);
          }
        });
    this.mNumberCount = getString(2097741916);
    this.mAvailableColor = getResources().getColor(2097348635);
    this.mUnavailableColor = getResources().getColor(2097348636);
  }
  
  public void loadDownImage(MediaEntity paramMediaEntity, final boolean isSuccess) {
    final ArrayList<ImageModel> imageArr = new ArrayList();
    arrayList.add(new ImageModel(paramMediaEntity.id, paramMediaEntity.path, 2));
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            FAQCommitFragment.this.mUploadView.loadImageDone(imageArr, isSuccess);
          }
        });
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    if (paramInt == 66) {
      if (paramArrayOfint != null && paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
        chooseImage(this.mActivity, 3 - this.mUploadView.getImageList().size(), true, true);
        return;
      } 
      PermissionsManager.getInstance().notifyPermissionsChange(this.mActivity, paramArrayOfString, paramArrayOfint);
    } 
  }
  
  public void refreshState() {
    boolean bool;
    if (this.mContactCorrect && this.mFeedbackContentCorrect) {
      enableSend();
    } else {
      unableSend();
    } 
    View view = this.mContactErrorLayout;
    if (this.mContactCorrect) {
      bool = true;
    } else {
      bool = false;
    } 
    UIUtils.setViewVisibility(view, bool);
  }
  
  public void showFailToast(CharSequence paramCharSequence, long paramLong) {
    ToastManager.Toast toast = new ToastManager.Toast(this.mActivity);
    View view = View.inflate((Context)this.mActivity, 2097676340, null);
    TextView textView = (TextView)view.findViewById(2097545396);
    textView.setMaxLines(2);
    textView.setEllipsize(TextUtils.TruncateAt.END);
    textView.setMinWidth((int)UIUtils.dip2Px((Context)this.mActivity, 108.0F));
    textView.setMaxWidth((int)UIUtils.dip2Px((Context)this.mActivity, 168.0F));
    textView.setText(paramCharSequence);
    ImageView imageView = (ImageView)view.findViewById(2097545309);
    imageView.setVisibility(0);
    imageView.setImageDrawable(getResources().getDrawable(2097479803));
    textView.setMaxLines(1);
    toast.setView(view);
    toast.setDuration(paramLong);
    toast.setGravity(17);
    toast.show();
  }
  
  public void showImageClickListener(ArrayList<ImageModel> paramArrayList, int paramInt) {}
  
  public void submit() {
    HostDependManager.getInst().showToast((Context)this.mActivity, null, getString(2097741917), 10000L, "loading");
    Observable.create(new Function<String>() {
          public String fun() {
            FAQCommitFragment.this.uploadALog();
            StringBuilder stringBuilder = new StringBuilder();
            FeedbackParam feedbackParam = new FeedbackParam();
            feedbackParam.setHostAppKey(FAQCommitFragment.this.mFeedbackParam.getHostAppKey());
            feedbackParam.setHostAid(FAQCommitFragment.this.mFeedbackParam.getHostAid());
            feedbackParam.setHostAppName(FAQCommitFragment.this.mFeedbackParam.getHostAppName());
            feedbackParam.setIid(FAQCommitFragment.this.mFeedbackParam.getIid());
            feedbackParam.setChannel(FAQCommitFragment.this.mFeedbackParam.getChannel());
            feedbackParam.setDeviceId(FAQCommitFragment.this.mFeedbackParam.getDeviceId());
            stringBuilder.append(AppbrandConstant.SnssdkAPI.getInst().getFeedbackSubmit());
            String str2 = FAQCommitFragment.this.mFeedbackParam.getHostAppKey();
            String str3 = FAQCommitFragment.this.mFeedbackParam.getHostAid();
            String str4 = FAQCommitFragment.this.mFeedbackParam.getHostAppName();
            FAQCommitFragment fAQCommitFragment = FAQCommitFragment.this;
            stringBuilder.append(feedbackParam.toParamString(str2, str3, str4, fAQCommitFragment.getHostVersion(fAQCommitFragment.mFeedbackParam.getHostAppVersion()), FAQCommitFragment.this.getSystemVersion(), FAQCommitFragment.this.getDeviceType(), FAQCommitFragment.this.mFeedbackParam.getHostAppUpdateVersion()));
            i i = new i(stringBuilder.toString(), "POST", false);
            String str1 = HostProcessBridge.getLoginCookie();
            if (!TextUtils.isEmpty(str1))
              i.a("Cookie", str1); 
            JSONObject jSONObject1 = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            try {
              jSONObject1.put("mp_id", FAQCommitFragment.this.mFeedbackParam.getAid());
              jSONObject1.put("mp_name", FAQCommitFragment.this.mFeedbackParam.getAppName());
              jSONObject1.put("mp_type", FAQCommitFragment.this.mFeedbackParam.getType());
              jSONObject1.put("mp_path", FAQCommitFragment.this.mFeedbackParam.getPath());
              jSONObject1.put("mp_query", FAQCommitFragment.this.mFeedbackParam.getQuery());
              jSONObject1.put("feedback_title", FAQCommitFragment.this.mItem.getName());
              jSONObject2.put("mp_id", FAQCommitFragment.this.mFeedbackParam.getAid());
              jSONObject2.put("mp_name", FAQCommitFragment.this.mFeedbackParam.getAppName());
              jSONObject2.put("mp_type", FAQCommitFragment.this.mFeedbackParam.getType());
              if (FAQCommitFragment.this.mFeedbackParam.getVersionType() == null) {
                str1 = "current";
              } else {
                str1 = FAQCommitFragment.this.mFeedbackParam.getVersionType();
              } 
              jSONObject2.put("mp_version_type", str1);
              jSONObject2.put("feedback_title", FAQCommitFragment.this.mItem.getName());
              str1 = FAQCommitFragment.this.mPresenter.getOpenIdSync();
              if (!TextUtils.isEmpty(str1))
                jSONObject2.put("openId", str1); 
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_FAQCommitFragment", new Object[] { jSONException });
            } 
            i.a("extra_params", jSONObject1);
            i.a("extra_persistent_params", jSONObject2);
            i.a("content", FAQCommitFragment.this.getContent());
            if (FAQCommitFragment.this.mItem != null) {
              Long long_ = Long.valueOf(FAQCommitFragment.this.mItem.getId());
            } else {
              str1 = "";
            } 
            i.a("qr_id", str1);
            if (!TextUtils.isEmpty(FAQCommitFragment.this.getContact()))
              i.a("contact", FAQCommitFragment.this.getContact()); 
            if (FAQCommitFragment.this.mImageResultList != null && !FAQCommitFragment.this.mImageResultList.isEmpty())
              if (FAQCommitFragment.this.mImageResultList.size() == 1 && ((ImageInfoVO)FAQCommitFragment.this.mImageResultList.get(0)).getUrlList() != null && !((ImageInfoVO)FAQCommitFragment.this.mImageResultList.get(0)).getUrlList().isEmpty()) {
                i.a("multi_image", Integer.valueOf(0));
                i.a("image_uri", ((ImageInfoVO)FAQCommitFragment.this.mImageResultList.get(0)).getUrlList().get(0));
                i.a("image_width", Integer.valueOf(((ImageInfoVO)FAQCommitFragment.this.mImageResultList.get(0)).getHeight()));
                i.a("image_height", Integer.valueOf(((ImageInfoVO)FAQCommitFragment.this.mImageResultList.get(0)).getWidth()));
              } else {
                JSONArray jSONArray = new JSONArray();
                for (int j = 0; j < FAQCommitFragment.this.mImageResultList.size(); j++) {
                  try {
                    jSONArray.put(ImageInfoVO.toJsonObject(FAQCommitFragment.this.mImageResultList.get(j)));
                  } catch (JSONException jSONException) {
                    AppBrandLogger.e("tma_FAQCommitFragment", new Object[] { jSONException });
                  } 
                } 
                if (jSONArray.length() > 0) {
                  i.a("multi_image", Integer.valueOf(1));
                  i.a("image_list", jSONArray);
                } 
              }  
            try {
              return HostDependManager.getInst().doPostUrlEncoded(i).a();
            } catch (Exception exception) {
              AppBrandLogger.e("tma_FAQCommitFragment", new Object[] { exception });
              return "";
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            HostDependManager.getInst().hideToast();
            if (FAQCommitFragment.this.isAdded()) {
              FAQCommitFragment fAQCommitFragment = FAQCommitFragment.this;
              fAQCommitFragment.showFailToast(fAQCommitFragment.getResources().getString(2097741919), 0L);
            } 
            AppBrandLogger.e("tma_FAQCommitFragment", new Object[] { "submit", param1Throwable });
          }
          
          public void onSuccess(String param1String) {
            HostDependManager.getInst().hideToast();
            try {
              boolean bool = TextUtils.equals((new JSONObject(param1String)).optString("message"), "success");
              if (bool) {
                Event.builder("mp_feedback_result", FAQCommitFragment.this.mAppInfoEntity).kv("result_type", "success").flush();
                if (FAQCommitFragment.this.isAdded())
                  HostDependManager.getInst().showToast((Context)FAQCommitFragment.this.mActivity, null, FAQCommitFragment.this.getResources().getString(2097741920), 0L, "success"); 
                ThreadUtil.runOnUIThread(new Runnable() {
                      public void run() {
                        FAQCommitFragment.this.mActivity.onBackPressed();
                      }
                    });
                return;
              } 
              Event.builder("mp_feedback_result", FAQCommitFragment.this.mAppInfoEntity).kv("result_type", "fail").flush();
              if (FAQCommitFragment.this.isAdded())
                FAQCommitFragment.this.showFailToast(FAQCommitFragment.this.getResources().getString(2097741919), 0L); 
              return;
            } catch (JSONException jSONException) {
              return;
            } 
          }
        });
  }
  
  public void uploadALog() {
    String str = this.mSceneTextView.getText().toString();
    long l2 = System.currentTimeMillis() / 1000L;
    Application application = AppbrandContext.getInst().getApplicationContext();
    SharedPreferences sharedPreferences = KVUtil.getSharedPreferences((Context)application, "alog_upload_time");
    long l1 = sharedPreferences.getLong(ProcessUtil.getCurProcessName((Context)application), 0L);
    if (l2 - l1 >= 86400L)
      l1 = l2 - 86400L; 
    AppbrandApmService.getInstance().tryUploadALog(l1, l2, str);
    HostProcessBridge.uploadFeedbackAlog(str, null);
    sharedPreferences.edit().putLong(ProcessUtil.getCurProcessName((Context)application), System.currentTimeMillis() / 1000L).apply();
  }
  
  public void uploadImage(final MediaEntity entity) {
    this.mUploadView.startLoadImage(new ImageModel(entity.id, entity.path, 1));
    FeedbackImgUploadHelper.uploadImage(this.mFeedbackParam, entity, new FeedbackImgUploadHelper.ImgUploadCallback() {
          public void onError(Throwable param1Throwable) {
            FAQCommitFragment.this.loadDownImage(entity, false);
          }
          
          public void onSuccess(JSONObject param1JSONObject) {
            try {
              if (!param1JSONObject.optString("message").equals("success")) {
                FAQCommitFragment.this.loadDownImage(entity, false);
                return;
              } 
              param1JSONObject = new JSONObject(param1JSONObject.optString("data"));
              FAQCommitFragment.this.mImageResultList.add(ImageInfoVO.from(param1JSONObject));
              FAQCommitFragment.this.loadDownImage(entity, true);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_FAQCommitFragment", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\FAQCommitFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */