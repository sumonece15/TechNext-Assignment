package com.technextassignment.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.technextassignment.api.RetrofitClient;
import com.technextassignment.data.dao.BlogDao;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.helper.AppDb;
import com.technextassignment.data.helper.DaoHelper;
import com.technextassignment.data.helper.DbLoaderInterface;
import com.technextassignment.data.loader.BlogsLoader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technextassignment.data.helper.DaoHelper.DELETE;
import static com.technextassignment.data.helper.DaoHelper.DELETE_ALL;
import static com.technextassignment.data.helper.DaoHelper.EDIT;
import static com.technextassignment.data.helper.DaoHelper.FETCH_ALL;
import static com.technextassignment.data.helper.DaoHelper.INSERT_ALL;

public class BlogRepository {

    public static final String TAG = "BlogRepository";
    private Context mContext;
    public BlogRepository(Application application) { //application is subclass of context
        this.mContext = application;
    }


    //methods for database operations :-
    public void insert(List<Blog> blog){
        BlogsLoader loader = new BlogsLoader(mContext);
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                Log.d(TAG,"inserted");
            }
        });
        loader.execute(INSERT_ALL,blog);
    }

    public void update(Blog blog){
        BlogsLoader loader = new BlogsLoader(mContext);
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                Log.d(TAG,"updated");
            }
        });
        loader.execute(EDIT,blog);
    }


    public void delete(Blog blog){
        BlogsLoader loader = new BlogsLoader(mContext);
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                Log.d(TAG,"deleted");
            }
        });
        loader.execute(DELETE,blog);
    }

    public void deleteAllBlogs(){
        BlogsLoader loader = new BlogsLoader(mContext);
        loader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                Log.d(TAG,"all deleted");
            }
        });
        loader.execute(DELETE_ALL);
    }
}
