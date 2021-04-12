package com.technextassignment.data.loader;

import android.content.Context;
import android.os.AsyncTask;

import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.helper.AppDb;
import com.technextassignment.data.helper.DaoHelper;
import com.technextassignment.data.helper.DbLoaderInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BlogsLoader extends AsyncTask<Object, Void, Object> {

    private DbLoaderInterface dbLoaderInterface;
    private WeakReference<Context> weakContext;

    public BlogsLoader(Context context) {
        weakContext = new WeakReference<Context>(context);
    }

    public void setDbLoaderInterface(DbLoaderInterface dbLoaderInterface) {
        this.dbLoaderInterface = dbLoaderInterface;
    }

    @Override
    protected Object doInBackground(Object... object) {
        Context context = weakContext.get();
        int command = (int) object[0];

        if (command == DaoHelper.INSERT_ALL) {
            ArrayList<Blog> models = (ArrayList<Blog>) object[1];
            AppDb.getAppDb(context).getBlogDao().insertBlogWithCategory(models);
        } else if (command == DaoHelper.FETCH_ALL) {
            return AppDb.getAppDb(context).getBlogDao().getAll();
        }
        else if (command == DaoHelper.DELETE) {
            Blog blog = (Blog) object[1];
            AppDb.getAppDb(context).getBlogDao().delete(blog);
        } else if (command == DaoHelper.SEARCH) {
            int id = (int) object[1];
            return AppDb.getAppDb(context).getBlogDao().getBlogsById(id);
        } else if (command == DaoHelper.EDIT) {
            Blog blog = (Blog) object[1];
            AppDb.getAppDb(context).getBlogDao().update(blog);
        }
        else if (command == DaoHelper.DELETE_ALL) {
            AppDb.getAppDb(context).getBlogDao().clearAll();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(dbLoaderInterface != null) {
            dbLoaderInterface.onFinished(o);
        }
    }
}