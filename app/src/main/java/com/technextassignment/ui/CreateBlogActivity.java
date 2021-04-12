package com.technextassignment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.technextassignment.R;
import com.technextassignment.databinding.ActivityCreateBlogBinding;
import com.technextassignment.view_models.CreateBlogViewModel;

public class CreateBlogActivity extends AppCompatActivity {

    private CreateBlogViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityCreateBlogBinding activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_create_blog);
        viewModel = new CreateBlogViewModel(getApplication());
        activityMainBinding.setViewModel(viewModel);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.executePendingBindings();


        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}