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

import java.util.ArrayList;
import java.util.List;

import cn.gov.gxxc.R;
import cn.gov.gxxc.TextNewsDetailActivity;
import cn.gov.gxxc.adapter.TextNewsListAdapter;
import cn.gov.gxxc.http.JsoupManager;
import cn.gov.gxxc.model.TextNewsModel;

/**
 * 文字新闻列表fragment
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private BaseAdapter mAdapter;

    private List<TextNewsModel> mList = new ArrayList<>();

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
        refresh();
    }

    private void initViews(View view){
        mListView = (ListView) view.findViewById(R.id.list);
        mAdapter = new TextNewsListAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    private void refresh() {
        new AsyncTask<Integer, Integer, List<TextNewsModel>>() {
            @Override
            protected List<TextNewsModel> doInBackground(Integer... params) {
                return JsoupManager.getInstance().getDailyNews(params[0]);
            }

            @Override
            protected void onPostExecute(List<TextNewsModel> textNewsModels) {
                super.onPostExecute(textNewsModels);
                mList.addAll(textNewsModels);
                mAdapter.notifyDataSetChanged();
            }
        }.execute(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), TextNewsDetailActivity.class);
        intent.putExtra("url", mList.get(position).getUrl());
        startActivity(intent);
    }
}
