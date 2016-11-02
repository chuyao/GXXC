package cn.gov.gxxc.fragment;


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

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.gov.gxxc.R;
import cn.gov.gxxc.activity.TextNewsDetailActivity;
import cn.gov.gxxc.activity.VideoNewsDetailActivity;
import cn.gov.gxxc.adapter.VideoNewsListAdapter;
import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.TextNewsModel;
import cn.gov.gxxc.model.VideoNewsModel;
import cn.gov.gxxc.widget.XSwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoNewsListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "VideoNewsListFragment";

    private ListView listView;
    private BaseAdapter adapter;
    private List<VideoNewsModel> list = new ArrayList<>();

    private XSwipeRefreshLayout swipeRefreshLayout;

    private int currentPage = 1;

    public VideoNewsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_news_list, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh(1);
    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new VideoNewsListAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
        new AsyncTask<Integer, Integer, List<VideoNewsModel>>() {
            @Override
            protected List<VideoNewsModel> doInBackground(Integer... params) {
                return JsoupManager.getInstance().getVideoNews(params[0]);
            }

            @Override
            protected void onPostExecute(List<VideoNewsModel> videoNewsModels) {
                super.onPostExecute(videoNewsModels);
                resetSwipeStatus();
                list.addAll(videoNewsModels);
                adapter.notifyDataSetChanged();
                currentPage++;
            }
        }.execute(page);
    }

    private void resetSwipeStatus(){
        if(swipeRefreshLayout.status == XSwipeRefreshLayout.Status.LOAD) {
            swipeRefreshLayout.setLoading(false);
        }else if(swipeRefreshLayout.status == XSwipeRefreshLayout.Status.REFRESH){
            swipeRefreshLayout.setRefreshing(false);
            list.clear();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), VideoNewsDetailActivity.class);
        intent.putExtra("url", list.get(position).getUrl());
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
