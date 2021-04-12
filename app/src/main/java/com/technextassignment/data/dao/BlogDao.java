package com.technextassignment.data.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.google.gson.Gson;
import com.technextassignment.data.entity.Blog;
import com.technextassignment.data.entity.BlogCategoryMapping;
import com.technextassignment.data.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class BlogDao {

    @Query("SELECT * FROM Blog ORDER BY id DESC")
    public abstract List<Blog> getAll();

    @Query("SELECT * FROM Blog WHERE id LIKE :id ORDER BY id DESC")
    public abstract Blog findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAll(ArrayList<Blog> blogs);

    @Update
    public abstract void update(Blog blog);

    @Delete
    public abstract void delete(Blog blog);

    @Query("DELETE FROM Blog")
    public abstract void clearAll();

    @Transaction
    public void insertBlogWithCategory(List<Blog> blogs){
        for (Blog blog : blogs){
            long blogId = insertBlog(blog);
            for(String catName:blog.getCategories()){
                List<Category> category = findByName(catName);
                if( !category.isEmpty()){
                    List<BlogCategoryMapping> mappings = isExist(category.get(0).getId(),(int)blogId);
                    if(mappings.isEmpty())
                        insertBlogCategory(new BlogCategoryMapping(blogId,category.get(0).getId()));
                }
                else {
                    long catId = insertCategory(new Category(catName));
                    insertBlogCategory(new BlogCategoryMapping(blogId, catId));
                }
            }
        }
    };

    @Transaction
    public Blog getBlogsById(int id){
        Blog blog = findById(Long.valueOf(id));
        List<Category> categories = getAllCategoryByBlogId(id);
        blog.setCategoryList(categories);

        return blog;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long insertCategory(Category categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long insertBlog(Blog blogs);

    @Insert
    abstract long insertBlogCategory(BlogCategoryMapping mapping);

    @Query("SELECT * FROM Category WHERE title =:name ORDER BY id DESC")
    abstract List<Category> findByName(String name);

    @Query("SELECT t1.* FROM Category t1 join BlogCategoryMapping t2 on t2.cat_id = t1.id where t2.blog_id =:blogId ORDER BY id DESC")
    abstract List<Category> getAllCategoryByBlogId(int blogId);

    @Query("SELECT * FROM BlogCategoryMapping WHERE cat_id=:catId and blog_id=:blogId")
    abstract List<BlogCategoryMapping> isExist(int catId, int blogId);

}
