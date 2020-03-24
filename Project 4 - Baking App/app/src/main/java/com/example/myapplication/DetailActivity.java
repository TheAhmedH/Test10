package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DishDetailAdapter.StepClickListener {
    List<Ingredient> mIngredientList = new ArrayList<>();
    List<Step> mStepList = new ArrayList<>();
    private RecyclerView mDetailView_RecyclerView;
    RecipeDish mRecipeDish;
    private DishDetailAdapter mdishDetailAdapter;
    private Boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Initializing 2 pane layout boolean
        mTwoPane = findViewById(R.id.media_fragment_container) != null;
        mRecipeDish = (RecipeDish) getIntent().getSerializableExtra("Dish");
        mIngredientList = mRecipeDish.getIngredients();
        //Setting the title to the Dish Clicked
        setTitle(mRecipeDish.getName());
        //Initializing Steps
        mStepList = mRecipeDish.getSteps();
        //RV Setup
        mDetailView_RecyclerView = findViewById(R.id.detailView_RecyclerView);
        mDetailView_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailView_RecyclerView.setHasFixedSize(true);
        mdishDetailAdapter = new DishDetailAdapter(this, mStepList, mIngredientList, this);
        mDetailView_RecyclerView.setAdapter(mdishDetailAdapter);

        if (mTwoPane == true) {
            FillPlayerFragmentContainer();
        }
    }

    //Filling the FragmentContainet on TwpPane
    private void FillPlayerFragmentContainer() {
        PlayerDetailFragment mPlayerDetailActivityFragment = new PlayerDetailFragment();
        //Getting the current step from the intent
        Step mStep = mRecipeDish.getSteps().get(0);
        //setting a bundle to pass the step to the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("step", mStep);
        mPlayerDetailActivityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.media_fragment_container, mPlayerDetailActivityFragment).commit();
    }

    //Filling the Fragmentcontainer for Steps ItemClicked
    private void FillPlayerFragmentContainerOnClick(int ItemClicked) {
        PlayerDetailFragment mPlayerDetailActivityFragment = new PlayerDetailFragment();
        //Getting the current step from the intent
        Step mStep = mRecipeDish.getSteps().get(ItemClicked);
        //setting a bundle to pass the step to the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("step", mStep);
        mPlayerDetailActivityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.media_fragment_container, mPlayerDetailActivityFragment).commit();
    }

    @Override
    public void stepOnClick(int ItemClicked) {
        //TODO Handle the click in twoPane scenario
        Step currentStep = mdishDetailAdapter.getmStepsList().get(ItemClicked);
        //Check if step has a url and handling the click with a toast
        if (currentStep.getVideoURL().isEmpty()) {
            Toast.makeText(this, "No video for this Step", Toast.LENGTH_SHORT).show();
            //TODO Fix the tablet layout handling
        } else if (mTwoPane) {
            FillPlayerFragmentContainerOnClick(ItemClicked);
        } else {
            Intent intent = new Intent(DetailActivity.this, PlayerDetailActivity.class);
            intent.putExtra("step", currentStep);
            startActivity(intent);
        }
    }
}
