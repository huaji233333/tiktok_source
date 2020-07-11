package com.tt.miniapp.feedback.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.b;
import android.support.v4.app.k;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.storage.async.Action;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.feedback.FeedbackImgUploadHelper;
import com.tt.miniapp.feedback.entrance.BaseFAQFragment;
import com.tt.miniapp.feedback.entrance.image.ImageClickListener;
import com.tt.miniapp.feedback.entrance.image.ImageLoaderInterface;
import com.tt.miniapp.feedback.entrance.image.ImageLoaderUtil;
import com.tt.miniapp.feedback.entrance.image.ImageModel;
import com.tt.miniapp.feedback.entrance.image.ImageUploadView;
import com.tt.miniapp.feedback.entrance.vo.ImageInfoVO;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.n.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportFragment extends BaseFAQFragment implements k, IActivityResultHandler, ImageClickListener, ReportOptionAdapter.OnOptionClickListener {
  private View mBtnContainer;
  
  public TextView mBtnSubmit;
  
  public View mDescAnchorBView;
  
  public View mDescAnchorTView;
  
  public EditText mEtOriginalContent;
  
  public EditText mEtReportDesc;
  
  public int mFetchOptionCnt = 0;
  
  public ImageUploadView mImageUploadView;
  
  public KeyboardHeightProvider mKeyboardHeightProvider;
  
  public UserInfoManager.HostClientLoginListener mLoginListener;
  
  public volatile boolean mLoginTriggered;
  
  public ReportOptionAdapter mOptionAdapter;
  
  public JSONArray mOptionArray;
  
  public View mOriginAnchorBView;
  
  public View mOriginAnchorTView;
  
  private View mOriginalContainer;
  
  public View mScrollContainer;
  
  public Boolean mSubmitState;
  
  public boolean mTipViewClick = false;
  
  public final List<ImageInfoVO> mUploadImageList = Collections.synchronizedList(new ArrayList<ImageInfoVO>(5));
  
  private Pattern mUrlPattern;
  
  private void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    HostDependManager.getInst().chooseImage(paramActivity, paramInt, paramBoolean1, paramBoolean2, new b.b() {
          public void onCancel() {}
          
          public void onFail(String param1String) {}
          
          public void onSuccess(List<MediaEntity> param1List) {
            if (param1List != null && !param1List.isEmpty())
              for (MediaEntity mediaEntity : param1List)
                ReportFragment.this.uploadImage(mediaEntity);  
          }
        },  new b.a() {
          public void setActivityResultHandler(IActivityResultHandler param1IActivityResultHandler) {
            ReportFragment.this.mPresenter.setActivityResultHandler(param1IActivityResultHandler);
          }
        });
  }
  
  private void initDescTextCount(final EditText editText, final TextView textCount) {
    editText.addTextChangedListener(new ScrollableEditText.AbsTextWatcher() {
          public void afterTextChanged(Editable param1Editable) {
            TextView textView = textCount;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(editText.getText().toString().length());
            stringBuilder.append("/200");
            textView.setText(stringBuilder.toString());
          }
        });
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(editText.getText().toString().length());
    stringBuilder.append("/200");
    textCount.setText(stringBuilder.toString());
  }
  
  private void initOriginalLink(EditText paramEditText) {
    paramEditText.addTextChangedListener(new ScrollableEditText.AbsTextWatcher() {
          public void afterTextChanged(Editable param1Editable) {
            boolean bool;
            TextView textView = ReportFragment.this.mBtnSubmit;
            if (ReportFragment.this.mEtOriginalContent != null && !TextUtils.isEmpty(ReportFragment.this.mEtOriginalContent.getText().toString())) {
              bool = true;
            } else {
              bool = false;
            } 
            textView.setEnabled(bool);
          }
        });
  }
  
  private void initSubmitBtn(TextView paramTextView) {
    StateListDrawable stateListDrawable = new StateListDrawable();
    GradientDrawable gradientDrawable = (GradientDrawable)b.a((Context)this.mActivity, 2097479694);
    if (gradientDrawable != null)
      gradientDrawable.setColor(b.c((Context)this.mActivity, 2097348623)); 
    stateListDrawable.addState(new int[] { -16842919, 16842910 }, (Drawable)gradientDrawable);
    gradientDrawable = (GradientDrawable)b.a((Context)this.mActivity, 2097479694);
    if (gradientDrawable != null)
      gradientDrawable.setColor(b.c((Context)this.mActivity, 2097348624)); 
    stateListDrawable.addState(new int[] { -16842910 }, (Drawable)gradientDrawable);
    gradientDrawable = (GradientDrawable)b.a((Context)this.mActivity, 2097479694);
    if (gradientDrawable != null)
      gradientDrawable.setColor(b.c((Context)this.mActivity, 2097348622)); 
    stateListDrawable.addState(new int[] { 16842919 }, (Drawable)gradientDrawable);
    paramTextView.setBackground((Drawable)stateListDrawable);
    paramTextView.setEnabled(false);
    paramTextView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ReportFragment.this.submitReport(param1View);
          }
        });
  }
  
  private boolean invalidInput(ReportOptionAdapter.ItemVO paramItemVO, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mOptionAdapter : Lcom/tt/miniapp/feedback/report/ReportOptionAdapter;
    //   4: ifnull -> 170
    //   7: aload_0
    //   8: getfield mEtReportDesc : Landroid/widget/EditText;
    //   11: ifnonnull -> 16
    //   14: iconst_1
    //   15: ireturn
    //   16: aload_1
    //   17: getfield type : I
    //   20: invokestatic getPlagiarizeType : ()I
    //   23: if_icmpne -> 78
    //   26: aload_0
    //   27: getfield mEtOriginalContent : Landroid/widget/EditText;
    //   30: ifnull -> 78
    //   33: aload_2
    //   34: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   37: ifne -> 54
    //   40: aload_0
    //   41: getfield mUrlPattern : Ljava/util/regex/Pattern;
    //   44: aload_2
    //   45: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   48: invokevirtual matches : ()Z
    //   51: ifne -> 78
    //   54: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   57: aload_0
    //   58: getfield mActivity : Landroid/app/Activity;
    //   61: aconst_null
    //   62: aload_0
    //   63: ldc_w 2097741998
    //   66: invokevirtual getString : (I)Ljava/lang/String;
    //   69: ldc2_w 2000
    //   72: aconst_null
    //   73: invokevirtual showToast : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V
    //   76: iconst_1
    //   77: ireturn
    //   78: aload_0
    //   79: getfield mImageUploadView : Lcom/tt/miniapp/feedback/entrance/image/ImageUploadView;
    //   82: astore_1
    //   83: aload_1
    //   84: ifnonnull -> 92
    //   87: aconst_null
    //   88: astore_1
    //   89: goto -> 97
    //   92: aload_1
    //   93: invokevirtual getImageList : ()Ljava/util/ArrayList;
    //   96: astore_1
    //   97: aload_1
    //   98: ifnull -> 168
    //   101: aload_1
    //   102: invokeinterface iterator : ()Ljava/util/Iterator;
    //   107: astore_1
    //   108: aload_1
    //   109: invokeinterface hasNext : ()Z
    //   114: ifeq -> 138
    //   117: iconst_1
    //   118: aload_1
    //   119: invokeinterface next : ()Ljava/lang/Object;
    //   124: checkcast com/tt/miniapp/feedback/entrance/image/ImageModel
    //   127: invokevirtual getStatus : ()I
    //   130: if_icmpne -> 108
    //   133: iconst_0
    //   134: istore_3
    //   135: goto -> 140
    //   138: iconst_1
    //   139: istore_3
    //   140: iload_3
    //   141: ifne -> 168
    //   144: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   147: aload_0
    //   148: getfield mActivity : Landroid/app/Activity;
    //   151: aconst_null
    //   152: aload_0
    //   153: ldc_w 2097741997
    //   156: invokevirtual getString : (I)Ljava/lang/String;
    //   159: ldc2_w 2000
    //   162: aconst_null
    //   163: invokevirtual showToast : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V
    //   166: iconst_1
    //   167: ireturn
    //   168: iconst_0
    //   169: ireturn
    //   170: iconst_1
    //   171: ireturn
  }
  
  public static ReportFragment newInstance() {
    return new ReportFragment();
  }
  
  private void toggleOriginal(boolean paramBoolean) {
    byte b;
    if (this.mOriginalContainer == null) {
      ViewStub viewStub = (ViewStub)this.mRoot.findViewById(2097545458);
      if (viewStub != null) {
        this.mOriginalContainer = viewStub.inflate();
        this.mOriginAnchorTView = this.mOriginalContainer.findViewById(2097545379);
        this.mOriginAnchorBView = this.mOriginalContainer.findViewById(2097545378);
        this.mOriginalContainer.findViewById(2097545423).setOnClickListener(new View.OnClickListener() {
              public void onClick(View param1View) {
                if (ReportFragment.this.mTipViewClick)
                  return; 
                ReportFragment reportFragment = ReportFragment.this;
                reportFragment.mTipViewClick = true;
                InputMethodUtil.hideSoftKeyboard(reportFragment.mActivity);
                ThreadUtil.runOnUIThread(new Runnable() {
                      public void run() {
                        ReportFragment.this.mTipViewClick = false;
                        ReportTipDialog.newInst(UIUtils.txt(2097742007), ReportHelper.getPlagiarizeImg(), 100).show((FragmentActivity)ReportFragment.this.mActivity);
                      }
                    }100L);
              }
            });
        this.mEtOriginalContent = (EditText)this.mOriginalContainer.findViewById(2097545272);
        initOriginalLink(this.mEtOriginalContent);
      } 
    } 
    View view = this.mOriginalContainer;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    view.setVisibility(b);
  }
  
  public void addImageClickListener() {
    if (b.a((Context)this.mActivity, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
      requestPermissions(new String[] { "android.permission.READ_EXTERNAL_STORAGE" }, 32663);
      return;
    } 
    chooseImage(this.mActivity, 5 - this.mImageUploadView.getImageList().size(), true, true);
  }
  
  public boolean autoClearAfterActivityResult() {
    return true;
  }
  
  public void bindData(ReportOptionAdapter paramReportOptionAdapter, JSONArray paramJSONArray) {
    paramReportOptionAdapter.updateData(paramJSONArray);
    this.mScrollContainer.setVisibility(0);
    this.mBtnContainer.setVisibility(0);
  }
  
  public void delImageClickListener(int paramInt) {
    if (paramInt >= 0 && paramInt < this.mUploadImageList.size()) {
      this.mImageUploadView.delImage(paramInt);
      this.mUploadImageList.remove(paramInt);
    } 
  }
  
  public int getLayoutId() {
    return 2097676311;
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (invalidState())
      return false; 
    if (this.mLoginTriggered) {
      UserInfoManager.HostClientLoginListener hostClientLoginListener = this.mLoginListener;
      if (hostClientLoginListener != null) {
        this.mLoginTriggered = false;
        return UserInfoManager.handleHostClientLoginResult(paramInt1, paramInt2, paramIntent, hostClientLoginListener);
      } 
    } 
    return false;
  }
  
  public void initFragment() {
    super.initFragment();
    this.mUrlPattern = Pattern.compile("^((https|http)://)?(((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!$&'()*+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(/((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!$&'()*+,;=]|:|@)+(/(([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!$&'()*+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!$&'()*+,;=]|:|@)|[\\uE000-\\uF8FF]|/|\\?)*)?(#((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!$&'()*+,;=]|:|@)|/|\\?)*)?$");
    requestReportOption();
  }
  
  public void initTitleBar() {
    super.initTitleBar();
    ((TextView)this.mRoot.findViewById(2097545358)).setText(getText(2097742008));
    UIUtils.setViewVisibility(this.mRoot.findViewById(2097545300), 4);
    this.mRoot.findViewById(2097545355).setVisibility(4);
  }
  
  public void initView() {
    this.mScrollContainer = this.mRoot.findViewById(2097545388);
    this.mBtnContainer = this.mRoot.findViewById(2097545324);
    this.mBtnSubmit = (TextView)this.mRoot.findViewById(2097545425);
    initSubmitBtn(this.mBtnSubmit);
    RecyclerView recyclerView = (RecyclerView)this.mRoot.findViewById(2097545323);
    recyclerView.setLayoutManager((RecyclerView.i)new GridLayoutManager((Context)this.mActivity, 2));
    this.mEtReportDesc = (EditText)this.mRoot.findViewById(2097545273);
    TextView textView = (TextView)this.mRoot.findViewById(2097545274);
    initDescTextCount(this.mEtReportDesc, textView);
    this.mDescAnchorTView = this.mRoot.findViewById(2097545377);
    this.mDescAnchorBView = this.mRoot.findViewById(2097545376);
    this.mImageUploadView = (ImageUploadView)this.mRoot.findViewById(2097545297);
    this.mImageUploadView.setImageClickListener(this).setImageLoaderInterface((ImageLoaderInterface)new ImageLoaderUtil()).setShowDel(true).setOneLineShowNum(3).setMaxNum(5);
    int j = UIUtils.getScreenWidth((Context)this.mActivity);
    int i = (int)UIUtils.dip2Px((Context)this.mActivity, 15.0F);
    j = (int)(j - UIUtils.dip2Px((Context)this.mActivity, 3.0F) * 2.0F - (i * 2)) / 3;
    UIUtils.updateLayoutMargin((View)this.mImageUploadView, i, -3, i, -3);
    this.mImageUploadView.setmPicSize(j);
    this.mKeyboardHeightProvider = new KeyboardHeightProvider(this.mActivity);
    this.mRoot.post(new Runnable() {
          public void run() {
            ReportFragment.this.mKeyboardHeightProvider.start();
          }
        });
    ReportOptionAdapter reportOptionAdapter = new ReportOptionAdapter(this);
    this.mOptionAdapter = reportOptionAdapter;
    recyclerView.setAdapter(reportOptionAdapter);
    JSONArray jSONArray = this.mOptionArray;
    if (jSONArray != null)
      bindData(this.mOptionAdapter, jSONArray); 
  }
  
  public boolean invalidState() {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null && !fragmentActivity.isFinishing()) {
      if (fragmentActivity.isDestroyed())
        return true; 
      k k1 = getFragmentManager();
      if (k1 != null && !k1.h())
        return k1.g(); 
    } 
    return true;
  }
  
  public void onClick(ReportOptionAdapter.ItemVO paramItemVO) {
    boolean bool1;
    int i = ReportHelper.getInfringementType();
    int j = paramItemVO.type;
    boolean bool2 = true;
    if (i == j) {
      if (this.mTipViewClick)
        return; 
      this.mTipViewClick = true;
      InputMethodUtil.hideSoftKeyboard(this.mActivity);
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              ReportFragment.this.mTipViewClick = false;
              ReportTipDialog.newInst(UIUtils.txt(2097742006), ReportHelper.getInfringementImg(), 101).show((FragmentActivity)ReportFragment.this.mActivity);
            }
          }100L);
      return;
    } 
    if (ReportHelper.getPlagiarizeType() == paramItemVO.type && paramItemVO.selected) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    toggleOriginal(bool1);
    if (ReportHelper.getPlagiarizeType() == paramItemVO.type) {
      TextView textView = this.mBtnSubmit;
      EditText editText = this.mEtOriginalContent;
      if (editText != null && !TextUtils.isEmpty(editText.getText().toString())) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
      textView.setEnabled(bool1);
      return;
    } 
    this.mBtnSubmit.setEnabled(true);
  }
  
  public void onDestroyView() {
    super.onDestroyView();
    KeyboardHeightProvider keyboardHeightProvider = this.mKeyboardHeightProvider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.close(); 
    this.mPresenter.setActivityResultHandler(null);
  }
  
  public void onKeyboardHeightChanged(int paramInt1, int paramInt2) {
    if (this.mScrollContainer == null)
      return; 
    if (UIUtils.isKeyboardActive(paramInt1))
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              // Byte code:
              //   0: iconst_2
              //   1: newarray int
              //   3: astore #6
              //   5: aload_0
              //   6: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   9: getfield mTitleBar : Landroid/view/View;
              //   12: aload #6
              //   14: invokevirtual getLocationOnScreen : ([I)V
              //   17: aload #6
              //   19: iconst_1
              //   20: iaload
              //   21: aload_0
              //   22: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   25: getfield mTitleBar : Landroid/view/View;
              //   28: invokevirtual getHeight : ()I
              //   31: iadd
              //   32: istore #4
              //   34: aload_0
              //   35: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   38: getfield mBtnSubmit : Landroid/widget/TextView;
              //   41: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
              //   44: checkcast android/view/ViewGroup$MarginLayoutParams
              //   47: astore #7
              //   49: aload_0
              //   50: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   53: getfield mBtnSubmit : Landroid/widget/TextView;
              //   56: aload #6
              //   58: invokevirtual getLocationOnScreen : ([I)V
              //   61: aload #6
              //   63: iconst_1
              //   64: iaload
              //   65: aload #7
              //   67: getfield topMargin : I
              //   70: isub
              //   71: istore #5
              //   73: new java/lang/StringBuilder
              //   76: dup
              //   77: ldc 'v:['
              //   79: invokespecial <init> : (Ljava/lang/String;)V
              //   82: astore #7
              //   84: aload #7
              //   86: iload #4
              //   88: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   91: pop
              //   92: aload #7
              //   94: ldc '-'
              //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   99: pop
              //   100: aload #7
              //   102: iload #5
              //   104: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   107: pop
              //   108: aload #7
              //   110: ldc ']:'
              //   112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   115: pop
              //   116: aload #7
              //   118: iload #5
              //   120: iload #4
              //   122: isub
              //   123: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   126: pop
              //   127: ldc 'ReportFragment'
              //   129: iconst_1
              //   130: anewarray java/lang/Object
              //   133: dup
              //   134: iconst_0
              //   135: aload #7
              //   137: invokevirtual toString : ()Ljava/lang/String;
              //   140: aastore
              //   141: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
              //   144: aload_0
              //   145: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   148: getfield mEtOriginalContent : Landroid/widget/EditText;
              //   151: ifnull -> 234
              //   154: aload_0
              //   155: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   158: getfield mEtOriginalContent : Landroid/widget/EditText;
              //   161: invokevirtual isFocused : ()Z
              //   164: ifeq -> 234
              //   167: aload_0
              //   168: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   171: getfield mOriginAnchorTView : Landroid/view/View;
              //   174: aload #6
              //   176: invokevirtual getLocationOnScreen : ([I)V
              //   179: aload #6
              //   181: iconst_1
              //   182: iaload
              //   183: i2f
              //   184: aload_0
              //   185: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   188: getfield mActivity : Landroid/app/Activity;
              //   191: ldc 8.0
              //   193: invokestatic dip2Px : (Landroid/content/Context;F)F
              //   196: fsub
              //   197: f2i
              //   198: istore_1
              //   199: aload_0
              //   200: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   203: getfield mOriginAnchorBView : Landroid/view/View;
              //   206: aload #6
              //   208: invokevirtual getLocationOnScreen : ([I)V
              //   211: aload #6
              //   213: iconst_1
              //   214: iaload
              //   215: istore_2
              //   216: aload_0
              //   217: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   220: getfield mOriginAnchorBView : Landroid/view/View;
              //   223: invokevirtual getHeight : ()I
              //   226: istore_3
              //   227: iload_2
              //   228: iload_3
              //   229: iadd
              //   230: istore_2
              //   231: goto -> 324
              //   234: aload_0
              //   235: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   238: getfield mEtReportDesc : Landroid/widget/EditText;
              //   241: ifnull -> 320
              //   244: aload_0
              //   245: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   248: getfield mEtReportDesc : Landroid/widget/EditText;
              //   251: invokevirtual isFocused : ()Z
              //   254: ifeq -> 320
              //   257: aload_0
              //   258: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   261: getfield mDescAnchorTView : Landroid/view/View;
              //   264: aload #6
              //   266: invokevirtual getLocationOnScreen : ([I)V
              //   269: aload #6
              //   271: iconst_1
              //   272: iaload
              //   273: i2f
              //   274: aload_0
              //   275: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   278: getfield mActivity : Landroid/app/Activity;
              //   281: ldc 8.0
              //   283: invokestatic dip2Px : (Landroid/content/Context;F)F
              //   286: fsub
              //   287: f2i
              //   288: istore_1
              //   289: aload_0
              //   290: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   293: getfield mDescAnchorBView : Landroid/view/View;
              //   296: aload #6
              //   298: invokevirtual getLocationOnScreen : ([I)V
              //   301: aload #6
              //   303: iconst_1
              //   304: iaload
              //   305: istore_2
              //   306: aload_0
              //   307: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   310: getfield mDescAnchorBView : Landroid/view/View;
              //   313: invokevirtual getHeight : ()I
              //   316: istore_3
              //   317: goto -> 227
              //   320: iconst_0
              //   321: istore_2
              //   322: iconst_m1
              //   323: istore_1
              //   324: iload_1
              //   325: iconst_m1
              //   326: if_icmpeq -> 444
              //   329: iload_2
              //   330: iconst_m1
              //   331: if_icmpeq -> 444
              //   334: new java/lang/StringBuilder
              //   337: dup
              //   338: ldc 't:('
              //   340: invokespecial <init> : (Ljava/lang/String;)V
              //   343: astore #6
              //   345: aload #6
              //   347: iload_1
              //   348: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   351: pop
              //   352: aload #6
              //   354: ldc '-'
              //   356: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   359: pop
              //   360: aload #6
              //   362: iload_2
              //   363: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   366: pop
              //   367: aload #6
              //   369: ldc '):'
              //   371: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   374: pop
              //   375: aload #6
              //   377: iload_2
              //   378: iload_1
              //   379: isub
              //   380: invokevirtual append : (I)Ljava/lang/StringBuilder;
              //   383: pop
              //   384: ldc 'ReportFragment'
              //   386: iconst_1
              //   387: anewarray java/lang/Object
              //   390: dup
              //   391: iconst_0
              //   392: aload #6
              //   394: invokevirtual toString : ()Ljava/lang/String;
              //   397: aastore
              //   398: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
              //   401: iload_1
              //   402: iload #4
              //   404: if_icmpge -> 423
              //   407: aload_0
              //   408: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   411: getfield mScrollContainer : Landroid/view/View;
              //   414: iconst_0
              //   415: iload_1
              //   416: iload #4
              //   418: isub
              //   419: invokevirtual scrollBy : (II)V
              //   422: return
              //   423: iload_2
              //   424: iload #5
              //   426: if_icmple -> 444
              //   429: aload_0
              //   430: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
              //   433: getfield mScrollContainer : Landroid/view/View;
              //   436: iconst_0
              //   437: iload_2
              //   438: iload #5
              //   440: isub
              //   441: invokevirtual scrollBy : (II)V
              //   444: return
            }
          },  16L); 
  }
  
  public void onPause() {
    super.onPause();
    KeyboardHeightProvider keyboardHeightProvider = this.mKeyboardHeightProvider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.setKeyboardHeightObserver(null); 
    if (isRemoving())
      InputMethodUtil.hideSoftKeyboard(this.mActivity); 
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    if (paramInt == 32663) {
      if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
        chooseImage(this.mActivity, 5 - this.mImageUploadView.getImageList().size(), true, true);
        return;
      } 
      PermissionsManager.getInstance().notifyPermissionsChange(this.mActivity, paramArrayOfString, paramArrayOfint);
    } 
  }
  
  public void onResume() {
    super.onResume();
    KeyboardHeightProvider keyboardHeightProvider = this.mKeyboardHeightProvider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.setKeyboardHeightObserver(this); 
    Boolean bool = this.mSubmitState;
    if (bool != null) {
      boolean bool1 = bool.booleanValue();
      this.mSubmitState = null;
      HostDependManager.getInst().hideToast();
      TextView textView = this.mBtnSubmit;
      if (textView != null)
        textView.setEnabled(false); 
      if (bool1 && this.mActivity != null)
        this.mActivity.onBackPressed(); 
    } 
  }
  
  public void onUploadImageFinish(MediaEntity paramMediaEntity, final boolean isSuccess) {
    final ArrayList<ImageModel> imageArr = new ArrayList();
    arrayList.add(new ImageModel(paramMediaEntity.id, paramMediaEntity.path, 2));
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            ReportFragment.this.mImageUploadView.loadImageDone(imageArr, isSuccess);
          }
        });
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    super.onViewCreated(paramView, paramBundle);
    paramView.post(new Runnable() {
          public void run() {
            ReportFragment.this.preloadImage(ReportHelper.getInfringementImg());
            ReportFragment.this.preloadImage(ReportHelper.getPlagiarizeImg());
          }
        });
  }
  
  public void preloadImage(final String url) {
    if (TextUtils.isEmpty(url))
      return; 
    final ImageView imageView = new ImageView((Context)this.mActivity);
    HostDependManager.getInst().loadImage(imageView.getContext(), (new c(url)).a(new a() {
            public void onFail(Exception param1Exception) {
              StringBuilder stringBuilder = new StringBuilder("preload: ");
              stringBuilder.append(url);
              stringBuilder.append(" failed.");
              stringBuilder.append(imageView.hashCode());
              AppBrandLogger.w("ReportFragment", new Object[] { stringBuilder.toString() });
            }
            
            public void onSuccess() {
              StringBuilder stringBuilder = new StringBuilder("preload: ");
              stringBuilder.append(url);
              stringBuilder.append(" succeed.");
              stringBuilder.append(imageView.hashCode());
              AppBrandLogger.i("ReportFragment", new Object[] { stringBuilder.toString() });
            }
          }).a((View)imageView));
  }
  
  public void realSubmitReport(final View btnView, final ReportOptionAdapter.ItemVO selectItem, final String desc, final String originalUrl) {
    final ArrayList urlList = new ArrayList();
    Iterator<ImageInfoVO> iterator = this.mUploadImageList.iterator();
    while (iterator.hasNext()) {
      List list = ((ImageInfoVO)iterator.next()).getUrlList();
      if (list != null && list.size() > 0)
        arrayList.add(list.get(0)); 
    } 
    btnView.setEnabled(false);
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            String str = ReportFragment.this.mPresenter.getOpenIdSync();
            ReportNetHelper.submitReport(ReportFragment.this.mFeedbackParam, str, selectItem, originalUrl, desc, urlList, new ReportNetHelper.ReportCallback() {
                  public void onResponse(JSONObject param2JSONObject) {
                    boolean bool;
                    if (param2JSONObject != null && param2JSONObject.optInt("err_code", -1) == 0) {
                      bool = true;
                    } else {
                      bool = false;
                    } 
                    if (ReportFragment.this.invalidState()) {
                      ReportFragment.this.mSubmitState = Boolean.valueOf(bool);
                      return;
                    } 
                    if (bool) {
                      HostDependManager.getInst().showToast((Context)ReportFragment.this.mActivity, null, ReportFragment.this.getString(2097742005), 2000L, "success");
                      ThreadUtil.runOnUIThread(new Runnable() {
                            public void run() {
                              if (ReportFragment.this.invalidState()) {
                                ReportFragment.this.mSubmitState = Boolean.valueOf(true);
                                return;
                              } 
                              ReportFragment.this.mSubmitState = null;
                              ReportFragment.this.mActivity.onBackPressed();
                            }
                          },  2000L);
                      return;
                    } 
                    HostDependManager.getInst().showToast((Context)ReportFragment.this.mActivity, null, ReportFragment.this.getString(2097742004), 0L, null);
                    ReportFragment.this.mSubmitState = null;
                    btnView.setEnabled(true);
                  }
                });
          }
        }ThreadPools.longIO());
  }
  
  public void requestLogin(final Runnable onSucceed) {
    this.mLoginListener = new UserInfoManager.HostClientLoginListener() {
        public void onLoginFail() {}
        
        public void onLoginSuccess() {
          boolean bool;
          UserInfoManager.UserInfo userInfo = UserInfoManager.getHostClientUserInfo();
          if (userInfo.isLogin && !TextUtils.isEmpty(userInfo.userId)) {
            bool = true;
          } else {
            bool = false;
          } 
          if (bool) {
            onSucceed.run();
            return;
          } 
          AppBrandLogger.e("ReportFragment", new Object[] { "requestLogin: loginSucceed bug no userId" });
        }
        
        public void onLoginUnSupport() {
          AppBrandLogger.e("ReportFragment", new Object[] { "onLoginUnSupport:" });
        }
        
        public void onLoginWhenBackground() {}
        
        public void onTriggerHostClientLogin(String param1String) {
          ReportFragment.this.mLoginTriggered = true;
        }
      };
    if (this.mPresenter != null)
      this.mPresenter.setActivityResultHandler(this); 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            if (ReportFragment.this.invalidState())
              return; 
            UserInfoManager.requestLoginHostClient(ReportFragment.this.mActivity, ReportFragment.this.mLoginListener, null, false, null);
          }
        }ThreadPools.defaults());
  }
  
  public void requestReportOption() {
    JSONObject jSONObject = ReportNetHelper.getReportOptionCache();
    if (jSONObject != null && jSONObject.optInt("err_code", -1) == 0) {
      jSONObject = jSONObject.optJSONObject("data");
      if (jSONObject != null) {
        JSONArray jSONArray = jSONObject.optJSONArray("report_text_array");
        this.mOptionArray = jSONArray;
        if (jSONArray != null) {
          ReportOptionAdapter reportOptionAdapter = this.mOptionAdapter;
          if (reportOptionAdapter != null)
            bindData(reportOptionAdapter, this.mOptionArray); 
          return;
        } 
      } 
    } 
    HostDependManager.getInst().showToast((Context)this.mActivity, null, getString(2097741915), 20000L, "loading");
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            ReportNetHelper.requestReportOption(ReportFragment.this.mFeedbackParam, new ReportNetHelper.ReportCallback() {
                  public void onResponse(JSONObject param2JSONObject) {
                    // Byte code:
                    //   0: aload_1
                    //   1: ifnull -> 49
                    //   4: aload_1
                    //   5: ldc 'err_code'
                    //   7: iconst_m1
                    //   8: invokevirtual optInt : (Ljava/lang/String;I)I
                    //   11: ifne -> 49
                    //   14: aload_1
                    //   15: ldc 'data'
                    //   17: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
                    //   20: astore_2
                    //   21: aload_2
                    //   22: ifnull -> 49
                    //   25: aload_0
                    //   26: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   29: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   32: astore_1
                    //   33: aload_2
                    //   34: ldc 'report_text_array'
                    //   36: invokevirtual optJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
                    //   39: astore_2
                    //   40: aload_1
                    //   41: aload_2
                    //   42: putfield mOptionArray : Lorg/json/JSONArray;
                    //   45: aload_2
                    //   46: ifnonnull -> 118
                    //   49: aload_0
                    //   50: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   53: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   56: getfield mActivity : Landroid/app/Activity;
                    //   59: invokestatic isNetworkAvailable : (Landroid/content/Context;)Z
                    //   62: ifne -> 112
                    //   65: aload_0
                    //   66: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   69: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   72: getfield mFetchOptionCnt : I
                    //   75: ifgt -> 112
                    //   78: invokestatic clearOptionCache : ()V
                    //   81: aload_0
                    //   82: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   85: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   88: astore_1
                    //   89: aload_1
                    //   90: aload_1
                    //   91: getfield mFetchOptionCnt : I
                    //   94: iconst_1
                    //   95: iadd
                    //   96: putfield mFetchOptionCnt : I
                    //   99: aload_0
                    //   100: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   103: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   106: invokevirtual requestReportOption : ()V
                    //   109: goto -> 118
                    //   112: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
                    //   115: invokevirtual hideToast : ()V
                    //   118: aload_0
                    //   119: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   122: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   125: getfield mOptionAdapter : Lcom/tt/miniapp/feedback/report/ReportOptionAdapter;
                    //   128: ifnull -> 161
                    //   131: aload_0
                    //   132: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   135: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   138: aload_0
                    //   139: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   142: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   145: getfield mOptionAdapter : Lcom/tt/miniapp/feedback/report/ReportOptionAdapter;
                    //   148: aload_0
                    //   149: getfield this$1 : Lcom/tt/miniapp/feedback/report/ReportFragment$10;
                    //   152: getfield this$0 : Lcom/tt/miniapp/feedback/report/ReportFragment;
                    //   155: getfield mOptionArray : Lorg/json/JSONArray;
                    //   158: invokevirtual bindData : (Lcom/tt/miniapp/feedback/report/ReportOptionAdapter;Lorg/json/JSONArray;)V
                    //   161: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
                    //   164: invokevirtual hideToast : ()V
                    //   167: return
                  }
                });
          }
        }ThreadPools.longIO());
  }
  
  public void showImageClickListener(ArrayList<ImageModel> paramArrayList, int paramInt) {}
  
  public void submitReport(final View btnView) {
    ReportOptionAdapter reportOptionAdapter = this.mOptionAdapter;
    if (reportOptionAdapter != null && this.mEtReportDesc != null) {
      if (this.mEtOriginalContent == null)
        return; 
      final ReportOptionAdapter.ItemVO selectItem = reportOptionAdapter.getSelectItem();
      final String desc = this.mEtReportDesc.getText().toString();
      final String originalUrl = this.mEtOriginalContent.getText().toString().trim();
      if (invalidInput(itemVO, str2))
        return; 
      if (itemVO.type == ReportHelper.getPlagiarizeType()) {
        UserInfoManager.fetchHostClientUserInfo(new UserInfoManager.UserInfoFetcher() {
              public void onFetched(UserInfoManager.UserInfo param1UserInfo) {
                boolean bool;
                if (ReportFragment.this.invalidState())
                  return; 
                if (param1UserInfo.isLogin && !TextUtils.isEmpty(param1UserInfo.userId)) {
                  bool = true;
                } else {
                  bool = false;
                } 
                if (bool) {
                  ReportFragment.this.realSubmitReport(btnView, selectItem, desc, originalUrl);
                  return;
                } 
                ReportFragment.this.requestLogin(new Runnable() {
                      public void run() {
                        ReportFragment.this.realSubmitReport(btnView, selectItem, desc, originalUrl);
                      }
                    });
              }
            });
        return;
      } 
      realSubmitReport(btnView, itemVO, str1, str2);
    } 
  }
  
  public void uploadImage(final MediaEntity entity) {
    this.mImageUploadView.startLoadImage(new ImageModel(entity.id, entity.path, 1));
    FeedbackImgUploadHelper.uploadImage(this.mFeedbackParam, entity, new FeedbackImgUploadHelper.ImgUploadCallback() {
          public void onError(Throwable param1Throwable) {
            ReportFragment.this.onUploadImageFinish(entity, false);
          }
          
          public void onSuccess(JSONObject param1JSONObject) {
            try {
              if (!param1JSONObject.optString("message").equals("success")) {
                ReportFragment.this.onUploadImageFinish(entity, false);
                return;
              } 
              param1JSONObject = new JSONObject(param1JSONObject.optString("data"));
              ReportFragment.this.mUploadImageList.add(ImageInfoVO.from(param1JSONObject));
              ReportFragment.this.onUploadImageFinish(entity, true);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("ReportFragment", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ReportFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */