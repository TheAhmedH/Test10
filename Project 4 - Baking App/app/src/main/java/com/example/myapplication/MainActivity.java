package com.example.myapplication;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeDishAdapter.OnRecipeDishListener {
    public List<RecipeDish> mRecipeDishes;
    public RecipeDish mRecipeDishSingle;
    private RecipeDishAdapter recipeDishAdapter;
    private static final String TAG = "MainActivity";
    private RecyclerView recipeDish_recycleview;
    public List<Step> mStepsList;
    public List<Ingredient> mIngredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeDish_recycleview = findViewById(R.id.dish_RV);

        //Setting the title
        setTitle("Select A Dish");

        RecipeInterface recipeInterface = RecipeClient.getClient().create(RecipeInterface.class);
        Call<List<RecipeDish>> call = recipeInterface.getRecipeDish();

        call.enqueue(new Callback<List<RecipeDish>>() {
            @Override
            public void onResponse(Call<List<RecipeDish>> call, Response<List<RecipeDish>> response) {
                mRecipeDishes = response.body();
                recipeDishAdapter.setRecipeDishList(mRecipeDishes);
            }
            @Override
            public void onFailure(Call<List<RecipeDish>> call, Throwable t) {
            }
        });
        recipeDish_recycleview.setLayoutManager(new LinearLayoutManager(this));
        mRecipeDishes = new ArrayList<>();
        recipeDishAdapter = new RecipeDishAdapter(this, mRecipeDishes, this);
        recipeDish_recycleview.setAdapter(recipeDishAdapter);
    }

    @Override
    public void onRecipeDishClick(int itemclicked) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        RecipeDish recipeDish = recipeDishAdapter.getmRecipeDishList().get(itemclicked);
        intent.putExtra("Dish", recipeDish);
        startActivity(intent);
    }
}
