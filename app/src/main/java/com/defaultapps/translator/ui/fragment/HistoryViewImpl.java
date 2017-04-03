package com.defaultapps.translator.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.defaultapps.translator.R;
import com.defaultapps.translator.data.model.realm.RealmTranslate;
import com.defaultapps.translator.di.scope.PerActivity;
import com.defaultapps.translator.ui.activity.MainActivity;
import com.defaultapps.translator.ui.adapter.HistoryAdapter;
import com.defaultapps.translator.ui.base.BaseActivity;
import com.defaultapps.translator.ui.presenter.HistoryViewPresenterImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HistoryViewImpl extends Fragment implements HistoryView {

    @BindView(R.id.historyRecycler)
    RecyclerView historyRecycler;

    @Inject
    HistoryViewPresenterImpl historyViewPresenter;

    @PerActivity
    @Inject
    HistoryAdapter historyAdapter;

    private MainActivity activity;
    private Unbinder unbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            activity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getActivityComponent().inject(this);
        historyViewPresenter.onAttach(this);
        historyViewPresenter.requestHistoryItems();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("History", "OnResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("HistoryView", "DESTROYED");
        unbinder.unbind();
        historyViewPresenter.onDetach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @OnClick(R.id.testButton)
    void onClick() {
        Log.d("HistoryView", "onClick");
        historyViewPresenter.requestHistoryItems();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void receiveResult(List<RealmTranslate> realmTranslateList) {
        initRecyclerView(realmTranslateList);
    }

    private void initRecyclerView(List<RealmTranslate> realmTranslateList) {
        historyRecycler.setAdapter(historyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyAdapter.setData(realmTranslateList);
    }
}
