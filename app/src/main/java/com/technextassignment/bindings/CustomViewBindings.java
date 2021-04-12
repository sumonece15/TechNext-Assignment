package com.technextassignment.bindings;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technextassignment.R;
import com.technextassignment.adapters.CustomSpinnerAdapter;
import com.technextassignment.data.entity.Category;

import java.util.List;

public class CustomViewBindings {
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @BindingAdapter(value = "categoryList")
    public static void bindAutoCompleteTextView(AutoCompleteTextView textView, List<Category> categoryList){
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(textView.getContext(), R.layout.spinner_item, categoryList);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getItemAtPosition(i);
                textView.setText(category.getTitle());
            }
        });


    }

    @BindingAdapter(value = {"categoryList","selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(Spinner pAppCompatSpinner, List<Category> categoryList, Category selectedValue, final InverseBindingListener newTextAttrChanged) {

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(pAppCompatSpinner.getContext(), R.layout.spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pAppCompatSpinner.setAdapter(adapter);

        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
                Log.d("TestSelected",categoryList.get(position).getTitle());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newTextAttrChanged.onChange();
            }
        });


        for(int i=0; i<pAppCompatSpinner.getAdapter().getCount() ; i++){
            if(pAppCompatSpinner.getItemAtPosition(i) == selectedValue.getTitle()){
                pAppCompatSpinner.setSelection(i,true);
            }

            Log.d("TestSelected",pAppCompatSpinner.getItemAtPosition(i).toString());
        }

    }
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Category captureSelectedValue(Spinner pAppCompatSpinner) {

        Toast.makeText(pAppCompatSpinner.getContext(), ((Category)pAppCompatSpinner.getSelectedItem()).getTitle(),
                Toast.LENGTH_SHORT).show();

        return (Category) pAppCompatSpinner.getSelectedItem();
    }
}
