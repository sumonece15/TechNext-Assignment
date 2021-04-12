package com.technextassignment.data.loader;

import android.content.Context;
import android.os.AsyncTask;

import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.Category;
import com.technextassignment.data.helper.AppDb;
import com.technextassignment.data.helper.DaoHelper;
import com.technextassignment.data.helper.DbLoaderInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CategoryLoader extends AsyncTask<Object, Void, Object> {

    private DbLoaderInterface dbLoaderInterface;
    private WeakReference<Context> weakContext;

    public CategoryLoader(Context context) {
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
            ArrayList<Category> models = (ArrayList<Category>) object[1];
            AppDb.getAppDb(context).getCategoryDao().insertAll(models);
        } else if (command == DaoHelper.FETCH_ALL) {
            return AppDb.getAppDb(context).getCategoryDao().getAll();
        }
        else if (command == DaoHelper.FETCH_BY_BLOG) {
            int id = (int) object[1];
            return AppDb.getAppDb(context).getCategoryDao().getAllByBlogId(id);
        }
        else if (command == DaoHelper.DELETE) {
            Category category = (Category) object[1];
            AppDb.getAppDb(context).getCategoryDao().delete(category);
        } else if (command == DaoHelper.SEARCH) {
            String name = (String) object[1];
            return AppDb.getAppDb(context).getCategoryDao().findByName(name);
        } else if (command == DaoHelper.EDIT) {
            Category category = (Category) object[1];
            AppDb.getAppDb(context).getCategoryDao().update(category);
        }
        else if (command == DaoHelper.DELETE_ALL) {
            AppDb.getAppDb(context).getCategoryDao().clearAll();
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