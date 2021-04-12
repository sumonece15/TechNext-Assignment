package com.technextassignment.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.Category;
import com.technextassignment.data.helper.DbLoaderInterface;
import com.technextassignment.data.loader.BlogsLoader;
import com.technextassignment.data.loader.CategoryLoader;

import java.util.List;

import static com.technextassignment.data.helper.DaoHelper.FETCH_BY_BLOG;
import static com.technextassignment.data.helper.DaoHelper.SEARCH;

public class ViewBlogViewModel extends AndroidViewModel {

    public MutableLiveData<Blog> blogMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> categories = new MutableLiveData<>();
    public ViewBlogViewModel(@NonNull Application application, int id) {
        super(application);
        getBlogMutableLiveData(id);
    }

    public void getBlogMutableLiveData(int id){
        BlogsLoader loader = new BlogsLoader(getApplication());
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                Blog blog = (Blog) object;
                blogMutableLiveData.setValue(blog);
                Log.d("BlogData",new Gson().toJson(blogMutableLiveData.getValue()));
                getCategories();
            }
        });
        loader.execute(SEARCH,id);
    }

    public void getCategories(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i < blogMutableLiveData.getValue().getCategoryList().size() ; i++){
            stringBuilder.append(blogMutableLiveData.getValue().getCategoryList().get(i).getTitle());
            if(i != blogMutableLiveData.getValue().getCategoryList().size() -1 ){
                stringBuilder.append(", ");
            }
        }
        categories.setValue(stringBuilder.toString());
    }

}
