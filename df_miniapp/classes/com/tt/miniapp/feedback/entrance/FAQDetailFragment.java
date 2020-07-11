package com.tt.miniapp.feedback.entrance;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import java.util.List;
import org.json.JSONArray;

public class FAQDetailFragment extends BaseFAQFragment implements View.OnClickListener {
  private TextView mAnswerTextView;
  
  public View mFeedbackDetailBody;
  
  public View mFeedbackDetailFooter;
  
  private TextView mFeedbackTextView;
  
  private FAQItemVO mItem;
  
  private FAQListAdapter mRelatedQuestionAdapter;
  
  private RecyclerView mRelatedQuestionRecyclerView;
  
  private TextView mTitleTextView;
  
  private ImageView mUsefulImage;
  
  private TextView mUsefulTextView;
  
  private View mUsefulView;
  
  private ImageView mUselessImage;
  
  private TextView mUselessTextView;
  
  private View mUselessView;
  
  private void highlightUsefulLayout(View paramView, ImageView paramImageView, TextView paramTextView) {
    paramImageView.setBackground(getResources().getDrawable(2097479718));
    paramTextView.setTextColor(getResources().getColor(2097348623));
    paramView.setEnabled(false);
  }
  
  public static FAQDetailFragment newInstance(FAQItemVO paramFAQItemVO) {
    FAQDetailFragment fAQDetailFragment = new FAQDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("key_faq_item", (Parcelable)paramFAQItemVO);
    fAQDetailFragment.setArguments(bundle);
    return fAQDetailFragment;
  }
  
  private void unhighlightUsefulLayout(View paramView, ImageView paramImageView, TextView paramTextView) {
    paramImageView.setBackground(getResources().getDrawable(2097479719));
    paramTextView.setTextColor(getResources().getColor(2097348667));
    paramView.setEnabled(true);
  }
  
  protected int getLayoutId() {
    return 2097676309;
  }
  
  protected void initFragment() {
    super.initFragment();
    Bundle bundle = getArguments();
    if (bundle != null)
      this.mItem = (FAQItemVO)bundle.getParcelable("key_faq_item"); 
  }
  
  protected void initTitleBar() {
    super.initTitleBar();
    ((TextView)this.mRoot.findViewById(2097545358)).setText(getText(2097741907));
  }
  
  protected void initView() {
    super.initView();
    this.mTitleTextView = (TextView)this.mRoot.findViewById(2097545290);
    this.mAnswerTextView = (TextView)this.mRoot.findViewById(2097545281);
    this.mRelatedQuestionRecyclerView = (RecyclerView)this.mRoot.findViewById(2097545288);
    this.mFeedbackTextView = (TextView)this.mRoot.findViewById(2097545283);
    this.mFeedbackDetailBody = this.mRoot.findViewById(2097545282);
    this.mFeedbackDetailFooter = this.mRoot.findViewById(2097545284);
    this.mUsefulView = this.mRoot.findViewById(2097545285);
    this.mUsefulImage = (ImageView)this.mRoot.findViewById(2097545291);
    this.mUsefulTextView = (TextView)this.mRoot.findViewById(2097545292);
    this.mUselessView = this.mRoot.findViewById(2097545286);
    this.mUselessImage = (ImageView)this.mRoot.findViewById(2097545293);
    this.mUselessTextView = (TextView)this.mRoot.findViewById(2097545294);
    this.mUsefulView.setOnClickListener(this);
    this.mUselessView.setOnClickListener(this);
    this.mFeedbackTextView.setOnClickListener(this);
    final List<FAQItemVO> relativeQuestionlist = this.mItem.getRelatedLabels();
    if (list == null || list.isEmpty()) {
      UIUtils.setViewVisibility(this.mRoot.findViewById(2097545287), 8);
    } else {
      this.mRelatedQuestionRecyclerView.setLayoutManager((RecyclerView.i)new LinearLayoutManager((Context)this.mActivity));
      this.mRelatedQuestionAdapter = new FAQListAdapter((Context)this.mActivity);
      this.mRelatedQuestionAdapter.setNameTextColor(2097348651);
      this.mRelatedQuestionAdapter.setData(list);
      this.mRelatedQuestionAdapter.setOnItemClickListener(new FAQListAdapter.OnItemClickListener() {
            public void onItemClick(View param1View, int param1Int) {
              FAQListFragment fAQListFragment;
              Event.builder("mp_feedback_item_click", FAQDetailFragment.this.mAppInfoEntity).flush();
              FAQItemVO fAQItemVO = relativeQuestionlist.get(param1Int);
              JSONArray jSONArray = fAQItemVO.getChildren();
              if (jSONArray == null || jSONArray.length() <= 0) {
                FAQCommitFragment fAQCommitFragment;
                if (TextUtils.isEmpty(fAQItemVO.getValue()) || fAQItemVO.getValue().equals("null")) {
                  fAQCommitFragment = FAQCommitFragment.newInstance(fAQItemVO);
                } else {
                  FAQDetailFragment fAQDetailFragment = FAQDetailFragment.newInstance((FAQItemVO)fAQCommitFragment);
                } 
              } else {
                fAQListFragment = FAQListFragment.newInstance(jSONArray, false, null);
              } 
              if (fAQListFragment != null)
                FAQDetailFragment.this.mPresenter.onAddFragment(FAQDetailFragment.this, fAQListFragment); 
            }
          });
      this.mRelatedQuestionRecyclerView.setAdapter(this.mRelatedQuestionAdapter);
    } 
    this.mTitleTextView.setText(this.mItem.getName());
    this.mAnswerTextView.setText(this.mItem.getValue());
    this.mTitleBar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
          public void onLayoutChange(final View v, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
            FAQDetailFragment.this.mFeedbackDetailFooter.post(new Runnable() {
                  public void run() {
                    int i = FAQDetailFragment.this.mActivity.findViewById(16908290).getHeight();
                    int k = v.getHeight();
                    int n = FAQDetailFragment.this.mFeedbackDetailBody.getHeight();
                    int i1 = FAQDetailFragment.this.mFeedbackDetailFooter.getHeight();
                    int m = (int)UIUtils.dip2Px((Context)FAQDetailFragment.this.mActivity, 20.0F);
                    int j = (int)UIUtils.dip2Px((Context)FAQDetailFragment.this.mActivity, 80.0F);
                    k = i - k - n - i1 - m;
                    i = k;
                    if (k < j)
                      i = j; 
                    UIUtils.updateLayoutMargin(FAQDetailFragment.this.mFeedbackDetailFooter, 0, i, 0, m);
                    UIUtils.setViewVisibility(FAQDetailFragment.this.mFeedbackDetailFooter, 0);
                  }
                });
          }
        });
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i == 2097545285) {
      highlightUsefulLayout(this.mUsefulView, this.mUsefulImage, this.mUsefulTextView);
      unhighlightUsefulLayout(this.mUselessView, this.mUselessImage, this.mUselessTextView);
      HostDependManager.getInst().showToast((Context)this.mActivity, null, getResources().getString(2097741906), 0L, null);
      return;
    } 
    if (i == 2097545286) {
      highlightUsefulLayout(this.mUselessView, this.mUselessImage, this.mUselessTextView);
      unhighlightUsefulLayout(this.mUsefulView, this.mUsefulImage, this.mUsefulTextView);
      FAQCommitFragment fAQCommitFragment = FAQCommitFragment.newInstance(this.mItem);
      this.mPresenter.onAddFragment(this, fAQCommitFragment);
      return;
    } 
    if (i == 2097545283) {
      FAQCommitFragment fAQCommitFragment = FAQCommitFragment.newInstance(this.mItem);
      this.mPresenter.onAddFragment(this, fAQCommitFragment);
    } 
  }
  
  public void onHiddenChanged(boolean paramBoolean) {
    super.onHiddenChanged(paramBoolean);
    if (!paramBoolean) {
      unhighlightUsefulLayout(this.mUselessView, this.mUselessImage, this.mUselessTextView);
      unhighlightUsefulLayout(this.mUsefulView, this.mUsefulImage, this.mUsefulTextView);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\FAQDetailFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */