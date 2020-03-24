package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class RecipeDishAdapter extends RecyclerView.Adapter<RecipeDishAdapter.RecipeDishHolder> {
    private List<RecipeDish> mRecipeDishList;
    private Context mContext;
    private OnRecipeDishListener mOnRecipeDishListener;
    private List<String> DishPhotoURL = new ArrayList<String>(4);


    public RecipeDishAdapter(Context mContext, List<RecipeDish> mRecipeDishList, OnRecipeDishListener onRecipeDishListener) {
        this.mRecipeDishList = mRecipeDishList;
        this.mContext = mContext;
        this.mOnRecipeDishListener = onRecipeDishListener;

        DishPhotoURL.add("https://www.handletheheat.com/wp-content/uploads/2017/05/Nutella-Caramel-Tart-Square.jpg");
        DishPhotoURL.add("https://res.cloudinary.com/hksqkdlah/image/upload/ar_1:1,c_fill,dpr_2.0,f_auto,fl_lossy.progressive.strip_profile,g_faces:auto,q_auto:low,w_344/42571-sfs-fudgy-brownies-11-1");
        DishPhotoURL.add("https://www.wilton.com/dw/image/v2/AAWA_PRD/on/demandware.static/-/Sites-wilton-project-master/default/dwcf523b99/images/project/WLRECIP-7/YeBaCaFe33819.jpg?sw=800&sh=800");
        DishPhotoURL.add("https://www.thecheesecakefactory.com/assets/images/Menu-Import/CCF_OriginalCheesecake.jpg");
    }


    public void BindDishPics(String URLPath, ImageView imageView) {
        Picasso
                .get().
                load(URLPath).
                into(imageView);
    }

    @NonNull
    @Override
    public RecipeDishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_dish_list, parent, false);
        return new RecipeDishHolder(view, mOnRecipeDishListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDishHolder holder, int position) {
        RecipeDish currentDish = mRecipeDishList.get(position);
        String dishName = currentDish.getName();
        String imageURL = currentDish.getImage();
        holder.recipeDish_TV.setText(dishName);

        BindDishPics(DishPhotoURL.get(position), holder.recipeDish_IV);
    }

    class RecipeDishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeDish_IV;
        TextView recipeDish_TV;
        OnRecipeDishListener onRecipeDishListener;

        public RecipeDishHolder(@NonNull View itemView, OnRecipeDishListener onRecipeDishListener) {
            super(itemView);
            this.onRecipeDishListener = onRecipeDishListener;
            recipeDish_TV = itemView.findViewById(R.id.dish_name_textview);
            recipeDish_IV = itemView.findViewById(R.id.dish_Image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         onRecipeDishListener.onRecipeDishClick(getAdapterPosition());
        }
    }

    //method to set RecipeDishList
    public void setRecipeDishList(List<RecipeDish> mRecipeDishList) {
        this.mRecipeDishList = mRecipeDishList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecipeDishList.size();
    }

    //Creating an interface
    public interface OnRecipeDishListener {
        void onRecipeDishClick(int position);
    }

    public List<RecipeDish> getmRecipeDishList() {
        return mRecipeDishList;
    }
}
