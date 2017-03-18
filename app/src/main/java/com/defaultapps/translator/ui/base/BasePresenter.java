package com.defaultapps.translator.ui.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public BasePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        this.view = null;
    }

    protected V getView() {
        return this.view;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}