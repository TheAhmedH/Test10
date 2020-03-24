package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class PlayerDetailActivity extends AppCompatActivity {

    private Fragment mPlayerDetailFragment;
    private  Step mStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view_container);
        mPlayerDetailFragment = new PlayerDetailFragment();

        setTitle("Step Player");

        //Getting the current step from the intent
        mStep = (Step) getIntent().getExtras().getSerializable("step");

        //setting a bundle to pass the step to the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("step", mStep);

        mPlayerDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.media_fragment_container, mPlayerDetailFragment).commit();
    }

}

