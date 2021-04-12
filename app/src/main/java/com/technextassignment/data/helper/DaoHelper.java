package com.technextassignment.data.helper;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.technextassignment.data.dao.BlogDao;
import com.technextassignment.data.dao.CategoryDao;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.BlogCategoryMapping;
import com.technextassignment.data.entity.Category;

@Database(entities = {Blog.class, Category.class, BlogCategoryMapping.class}, version = 1,exportSchema = false)
public abstract class DaoHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "blogs.db";
    // commands
    public static final int INSERT_ALL = 1, FETCH_ALL = 2, DELETE = 3,
            SEARCH = 4, EDIT = 5, DELETE_ALL = 6, FETCH_BY_BLOG = 7;

    public abstract BlogDao getBlogDao();
    public abstract CategoryDao getCategoryDao();



}