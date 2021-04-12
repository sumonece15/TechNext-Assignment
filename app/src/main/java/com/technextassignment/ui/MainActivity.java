package com.technextassignment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.technextassignment.R;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.databinding.ActivityMainBinding;
import com.technextassignment.view_models.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.technextassignment.data.AppConstants.BLOG_ID;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityBinding;
    private MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBindings();
    }

    private void setupBindings() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        activityBinding.setViewModel(viewModel);
        activityBinding.executePendingBindings();

        setupListClick();
    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchDataFromServer();
        viewModel.getAllBlogsFromDb();
        viewModel.getAllBlogs().observe(this, new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogList) {
                viewModel.loading.set(View.GONE);
                if (blogList.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setBlogsInAdapter(blogList);
                }
            }
        });
    }

    private void setupListClick() {
        viewModel.getSelected().observe(this, new Observer<Blog>() {
            @Override
            public void onChanged(Blog blog) {
                if (blog != null) {
                    //Toast.makeText(MainActivity.this, "You selected a " +blog.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ViewBlogActivity.class);
                    intent.putExtra(BLOG_ID,blog.getId());
                    startActivity(intent);
                }
            }
        });
    }

    public void createBlog(View view){
        Intent intent = new Intent(MainActivity.this, CreateBlogActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListUpdate();
    }
}