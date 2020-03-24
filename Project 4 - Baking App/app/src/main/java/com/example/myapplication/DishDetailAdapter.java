package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DishDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Step> mStepsList;
    private String mAllIngredinets;
    private Context context;
    private List<Ingredient> mingredientsList;
    private StepClickListener stepClickListener;

    public DishDetailAdapter(Context context, List<Step> mStepsList, List<Ingredient> mingredientList, StepClickListener stepClickListener) {
        this.mStepsList = mStepsList;
        this.context = context;
        this.mingredientsList = mingredientList;
        this.stepClickListener = stepClickListener;
        getAllIngredients(mingredientList);
    }

    //getting all the info needed from ingredients list and adding it into one String
    public void getAllIngredients(List<Ingredient> allIngredients) {
        int count = 1;
        StringBuilder IngredientsBuilder = new StringBuilder();
        for (Ingredient ingredient : allIngredients) {
            IngredientsBuilder.append("Item " + count + ": " + ingredient.getIngredient());
            IngredientsBuilder.append("\n");
            IngredientsBuilder.append("Quantity: " + ingredient.getQuantity() + " ");
            IngredientsBuilder.append("");
            IngredientsBuilder.append("");
            IngredientsBuilder.append(ingredient.getMeasure() + "\n");
            IngredientsBuilder.append("\n");
            count++;
        }
        mAllIngredinets = IngredientsBuilder.toString();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        //returning a specific viewholder depending on the viewtype
        if (viewType == R.layout.ingredient_list_layout) {
            return new IngredientsViewHolder(view);
        } else if (viewType == R.layout.steps_list_layout) {
            return new StepsViewHolder(view);
        } else {
            return null;
        }
    }

    //overriding getViewType to provide layout depending on position of view
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return R.layout.ingredient_list_layout;
            default:
                return R.layout.steps_list_layout;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).ingredientsNameTextview.setText(mAllIngredinets);
        } else if (holder instanceof StepsViewHolder) {
            Step currentStep = mStepsList.get(position);
            ((StepsViewHolder) holder).shortDescriptionTextview.setText(currentStep.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientsNameTextview;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientsNameTextview = itemView.findViewById(R.id.ingredientName_TV);
        }

        void bindIngredients(String ingredients) {
            ingredientsNameTextview.setText(ingredients);
        }
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        private ImageView VideoImageView;
        private TextView shortDescriptionTextview;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            shortDescriptionTextview = itemView.findViewById(R.id.step_short_description_TV);
            VideoImageView = itemView.findViewById(R.id.Video_IV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepClickListener.stepOnClick(getAdapterPosition());
                }
            });
        }
    }

    public List<Step> getmStepsList() {
        return mStepsList;
    }

    public interface StepClickListener {
        public void stepOnClick(int ItemClicked);
    }
}
