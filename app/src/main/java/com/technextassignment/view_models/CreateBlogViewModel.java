package com.technextassignment.view_models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.technextassignment.BR;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.Category;
import com.technextassignment.data.helper.DbLoaderInterface;
import com.technextassignment.data.loader.CategoryLoader;
import com.technextassignment.repository.BlogRepository;

import java.util.ArrayList;
import java.util.List;

import static com.technextassignment.data.helper.DaoHelper.FETCH_ALL;

public class CreateBlogViewModel extends BaseObservable {
    private Blog blog;
    private String successMessage = "Blog added successful";
    private String errorMessage = "Title or Details not valid";
    private BlogRepository repository;
    private Context mContext;
    public List<Category> categories =  new ArrayList<>();
    public MutableLiveData<String> message = new MutableLiveData<>();

    public CreateBlogViewModel(Application mContext) {
        this.mContext = mContext;
        blog = new Blog("","","");
        repository = new BlogRepository(mContext);
        getAllCategories();
    }

    @Bindable
    private String toastMessage = null;
    public String getToastMessage() {
        return toastMessage;
    }
    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }
    public void setTitle(String title) {
        blog.setTitle(title);
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getTitle() {
        return blog.getTitle();
    }
    @Bindable
    public String getDescription() {
        return blog.getDescription();
    }
    public void setDescription(String description) {
        blog.setDescription(description);
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public String getCoverImage() {
        return blog.getCover_photo();
    }
    public void setCoverImage(String coverImage) {
        blog.setCover_photo(coverImage);
        notifyPropertyChanged(BR.coverImage);
    }


    public MutableLiveData<String> getMessage(){
        return message;
    }

    @Bindable
    public String getSelected() {
        return blog.getCategories() == null ? "" : blog.getCategories().get(0);
    }

    public void setSelected(String category){
        List<String> list = new ArrayList<>();
        list.add(category);
        blog.setCategories(list);
        notifyPropertyChanged(BR.selected);
    }

    public void onCreateClicked() {
        if (isInputDataValid()) {
            List<Blog> blogList = new ArrayList<>();
            blogList.add(blog);
            repository.insert(blogList);
            setToastMessage(successMessage);
            message.setValue("success");
        }
        else {
            setToastMessage(errorMessage);
        }


    }
    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getTitle()) &&
                !TextUtils.isEmpty(getDescription()) &&
                !TextUtils.isEmpty(getCoverImage());
    }


    public void getAllCategories(){

        CategoryLoader categoryLoader = new CategoryLoader(mContext);
        categoryLoader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                List<Category> catList = (List<Category>) object;

                categories.addAll(catList);
                Log.d("CategoryList", new Gson().toJson(categories));
            }
        });

        categoryLoader.execute(FETCH_ALL);
    }


}
