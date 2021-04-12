package com.technextassignment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.technextassignment.R;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.databinding.ActivityMainBinding;
import com.technextassignment.databinding.ActivityViewBlogBinding;
import com.technextassignment.view_models.MainActivityViewModel;
import com.technextassignment.view_models.ViewBlogViewModel;
import com.technextassignment.view_models.ViewBlogViewModelFactory;

import static com.technextassignment.data.AppConstants.BLOG_ID;

public class ViewBlogActivity extends AppCompatActivity {

    private ViewBlogViewModel viewModel;
    private int blogId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);
        blogId = getIntent().getIntExtra(BLOG_ID,0);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityViewBlogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_view_blog);
        viewModel = new ViewModelProvider(this,new ViewBlogViewModelFactory(this.getApplication(),blogId)).get(ViewBlogViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}