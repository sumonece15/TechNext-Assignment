package com.technextassignment.view_models;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.technextassignment.R;
import com.technextassignment.adapters.BlogAdapter;
import com.technextassignment.api.RetrofitClient;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.Category;
import com.technextassignment.data.helper.DbLoaderInterface;
import com.technextassignment.data.loader.BlogsLoader;
import com.technextassignment.data.loader.CategoryLoader;
import com.technextassignment.models.ServerResponse;
import com.technextassignment.repository.BlogRepository;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technextassignment.data.helper.DaoHelper.FETCH_ALL;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<Blog> selected;
    private BlogAdapter adapter;
    private BlogRepository repository;
    private MutableLiveData<List<Blog>> dataList = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryDataList = new MutableLiveData<>();
    public ObservableInt loading;
    public ObservableInt showEmpty;
    public Context mContext;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
        repository = new BlogRepository(application);
        selected = new MutableLiveData<>();
        adapter = new BlogAdapter(R.layout.item_blog, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void setBlogsInAdapter(List<Blog> blogs) {
        this.adapter.setBlogss(blogs);
        this.adapter.notifyDataSetChanged();
    }

    public BlogAdapter getAdapter() {
        return adapter;
    }
    public MutableLiveData<Blog> getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        Blog db = getBlogAt(index);
        selected.setValue(db);
    }

    public Blog getBlogAt(Integer index) {
        if (dataList.getValue() != null &&
                index != null &&
                dataList.getValue().size() > index) {
            return dataList.getValue().get(index);
        }
        return null;
    }

    public void fetchDataFromServer(){
        RetrofitClient.getClient().getBlogData().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                insert(response.body().getBlogs());
                getAllBlogsFromDb();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }




    // Database function START
    public void insert(List<Blog> blogs) {
        repository.insert(blogs);
    }
    public void update(Blog blog) {
        repository.update(blog);
    }
    public void delete(Blog blog) {
        repository.delete(blog);
    }
    public void deleteAllBlogs() {
        repository.deleteAllBlogs();
    }
    public MutableLiveData<List<Blog>> getAllBlogs() {
       return dataList;
    }

    public void getAllBlogsFromDb() {
        BlogsLoader loader = new BlogsLoader(mContext);
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                List<Blog> blogs = (List<Blog>) object;
                dataList.setValue(blogs);

                Log.d("BlogData",new Gson().toJson(blogs));
            }
        });
        loader.execute(FETCH_ALL);
    }
    // Database Function END
}
