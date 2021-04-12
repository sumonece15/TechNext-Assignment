package com.technextassignment.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.technextassignment.data.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category ORDER BY id DESC")
    List<Category> getAll();

    @Query("SELECT t1.* FROM Category t1 join BlogCategoryMapping t2 on t2.cat_id = t1.id where t2.blog_id =:blogId ORDER BY id DESC")
    List<Category> getAllByBlogId(int blogId);

    @Query("SELECT * FROM Category WHERE title LIKE :name ORDER BY id DESC")
    List<Category> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Category> categories);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM Category")
    void clearAll();
}
