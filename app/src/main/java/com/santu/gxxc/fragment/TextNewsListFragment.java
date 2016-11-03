package com.santu.gxxc.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.santu.gxxc.R;
import com.santu.gxxc.activity.TextNewsDetailActivity;
import com.santu.gxxc.adapter.TextNewsListAdapter;
import com.santu.gxxc.http.JsoupManager;
import com.santu.gxxc.model.TextNewsModel;
import com.santu.gxxc.widget.XSwipeRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字新闻列表fragment
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "TextNewsListFragment";

    private ListView mListView;
    private BaseAdapter mAdapter;
    private XSwipeRefreshLayout swipeRefreshLayout;

    private List<TextNewsModel> mList = new ArrayList<>();

    private int currentPage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_news_list, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        refresh(0);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.onRefresh();
            }
        });
    }

    private void initViews(View view){
        mListView = (ListView) view.findViewById(R.id.list);
        mAdapter = new TextNewsListAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        swipeRefreshLayout = (XSwipeRefreshLayout) view.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnLoadListener(onLoadListener);
    }

    final XSwipeRefreshLayout.OnLoadListener onLoadListener = new XSwipeRefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            refresh(currentPage);
        }

        @Override
        public void onRefresh() {
            currentPage = 1;
            refresh(1);
        }
    };

    private void refresh(int page) {
        new AsyncTask<Integer, Integer, List<TextNewsModel>>() {
            @Override
            protected List<TextNewsModel> doInBackground(Integer... params) {
                return JsoupManager.getInstance().getDailyNews(params[0]);
            }

            @Override
            protected void onPostExecute(List<TextNewsModel> textNewsModels) {
                super.onPostExecute(textNewsModels);
                resetSwipeStatus();
                mList.addAll(textNewsModels);
                mAdapter.notifyDataSetChanged();
                currentPage++;
            }
        }.execute(page);
    }

    private void resetSwipeStatus(){
        if(swipeRefreshLayout.status == XSwipeRefreshLayout.Status.LOAD) {
            swipeRefreshLayout.setLoading(false);
        }else if(swipeRefreshLayout.status == XSwipeRefreshLayout.Status.REFRESH){
            swipeRefreshLayout.setRefreshing(false);
            mList.clear();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), TextNewsDetailActivity.class);
        intent.putExtra("url", mList.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }
}
