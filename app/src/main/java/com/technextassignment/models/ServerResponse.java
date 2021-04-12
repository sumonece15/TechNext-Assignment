package com.technextassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.technextassignment.data.entity.Blog;

import java.util.List;

public class ServerResponse {
    @SerializedName("blogs")
    @Expose
    private List<Blog> blogs = null;

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

}
