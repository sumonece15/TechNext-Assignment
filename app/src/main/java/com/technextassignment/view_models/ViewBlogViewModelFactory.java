package com.technextassignment.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewBlogViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private int mParam;


    public ViewBlogViewModelFactory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ViewBlogViewModel(mApplication, mParam);
    }
}
