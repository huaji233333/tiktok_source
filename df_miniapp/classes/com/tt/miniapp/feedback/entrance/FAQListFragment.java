package com.tt.miniapp.feedback.entrance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.feedback.entrance.vo.FAQItemVO;
import com.tt.miniapp.feedback.report.ReportFragment;
import com.tt.miniapp.feedback.report.ReportHelper;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class FAQListFragment extends BaseFAQFragment {
  public boolean mIsSelectScene;
  
  public List<FAQItemVO> mItemList;
  
  public OnItemSelectedListener mItemSelectedListener;
  
  private FAQListAdapter mListAdapter;
  
  private RecyclerView mListRv;
  
  private static List<FAQItemVO> getFAQListFromJSONArray(JSONArray paramJSONArray) throws JSONException {
    ArrayList<FAQItemVO> arrayList = new ArrayList();
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayList.add(FAQItemVO.from(paramJSONArray.getJSONObject(i))); 
    return arrayList;
  }
  
  private void initRecyclerView() {
    this.mListRv = (RecyclerView)this.mRoot.findViewById(2097545276);
    this.mListRv.setLayoutManager((RecyclerView.i)new LinearLayoutManager((Context)this.mActivity));
    this.mListAdapter = new FAQListAdapter((Context)this.mActivity);
    this.mListAdapter.setOnItemClickListener(new FAQListAdapter.OnItemClickListener() {
          public void onItemClick(View param1View, int param1Int) {
            FAQDetailFragment fAQDetailFragment;
            FAQListFragment fAQListFragment;
            Event.builder("mp_feedback_item_click", FAQListFragment.this.mAppInfoEntity).flush();
            FAQItemVO fAQItemVO = FAQListFragment.this.mItemList.get(param1Int);
            JSONArray jSONArray = fAQItemVO.getChildren();
            if (FAQListFragment.this.mPresenter != null && ReportHelper.isReportOpen() && fAQItemVO.getId() == ReportHelper.getReportItemId()) {
              FAQListFragment.this.mPresenter.onAddFragment(FAQListFragment.this, (Fragment)ReportFragment.newInstance());
              return;
            } 
            if (jSONArray == null || jSONArray.length() <= 0) {
              if (FAQListFragment.this.mIsSelectScene) {
                if (FAQListFragment.this.mPresenter != null)
                  FAQListFragment.this.mPresenter.getActivitySupportFragmentManager().a(FAQCommitFragment.class.getSimpleName(), 0); 
                jSONArray = null;
              } else if (TextUtils.isEmpty(fAQItemVO.getValue()) || fAQItemVO.getValue().equals("null")) {
                FAQCommitFragment fAQCommitFragment = FAQCommitFragment.newInstance(fAQItemVO);
              } else {
                fAQDetailFragment = FAQDetailFragment.newInstance(fAQItemVO);
              } 
              if (FAQListFragment.this.mItemSelectedListener != null)
                FAQListFragment.this.mItemSelectedListener.onItemSelected(fAQItemVO); 
            } else {
              fAQListFragment = FAQListFragment.newInstance((JSONArray)fAQDetailFragment, FAQListFragment.this.mIsSelectScene, FAQListFragment.this.mItemSelectedListener);
            } 
            if (fAQListFragment != null && FAQListFragment.this.mPresenter != null)
              FAQListFragment.this.mPresenter.onAddFragment(FAQListFragment.this, fAQListFragment); 
          }
        });
    this.mListRv.setAdapter(this.mListAdapter);
  }
  
  public static FAQListFragment newInstance(JSONArray paramJSONArray, boolean paramBoolean, OnItemSelectedListener paramOnItemSelectedListener) {
    FAQListFragment fAQListFragment = new FAQListFragment();
    fAQListFragment.mItemSelectedListener = paramOnItemSelectedListener;
    Bundle bundle = new Bundle();
    bundle.putBoolean("key_is_select_scene", paramBoolean);
    try {
      List<FAQItemVO> list = getFAQListFromJSONArray(paramJSONArray);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_FAQListFragment", new Object[] { jSONException });
      jSONException = null;
    } 
    bundle.putParcelableArrayList("key_faq_list", (ArrayList)jSONException);
    fAQListFragment.setArguments(bundle);
    return fAQListFragment;
  }
  
  public static FAQListFragment newInstance(boolean paramBoolean, OnItemSelectedListener paramOnItemSelectedListener) {
    FAQListFragment fAQListFragment = new FAQListFragment();
    fAQListFragment.mItemSelectedListener = paramOnItemSelectedListener;
    Bundle bundle = new Bundle();
    bundle.putBoolean("key_is_select_scene", paramBoolean);
    fAQListFragment.setArguments(bundle);
    return fAQListFragment;
  }
  
  public void bindData(List<FAQItemVO> paramList) {
    this.mListAdapter.setData(paramList);
    this.mListAdapter.notifyDataSetChanged();
  }
  
  protected int getLayoutId() {
    return 2097676310;
  }
  
  protected void initFragment() {
    super.initFragment();
    Bundle bundle = getArguments();
    if (bundle != null) {
      this.mItemList = bundle.getParcelableArrayList("key_faq_list");
      this.mIsSelectScene = bundle.getBoolean("key_is_select_scene", false);
    } 
  }
  
  protected void initTitleBar() {
    super.initTitleBar();
    ((TextView)this.mRoot.findViewById(2097545358)).setText(getText(ReportHelper.getTitleResId()));
  }
  
  protected void initView() {
    super.initView();
    initRecyclerView();
    List<FAQItemVO> list = this.mItemList;
    if ((list == null || list.isEmpty()) && this.mPresenter != null) {
      this.mPresenter.requestItemList(new BaseFAQFragment.OnRequestDataCallback() {
            public void callbackItemList(List<FAQItemVO> param1List) {
              FAQListFragment fAQListFragment = FAQListFragment.this;
              fAQListFragment.mItemList = param1List;
              fAQListFragment.bindData(fAQListFragment.mItemList);
            }
          });
      return;
    } 
    bindData(this.mItemList);
  }
  
  public void setOnItemSelectedListener(OnItemSelectedListener paramOnItemSelectedListener) {
    this.mItemSelectedListener = paramOnItemSelectedListener;
  }
  
  public static interface OnItemSelectedListener {
    void onItemSelected(FAQItemVO param1FAQItemVO);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\entrance\FAQListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */