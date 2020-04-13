
package com.kunall17.trellassignment.folder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    public static final DiffUtil.ItemCallback<Restaurant> REPO_COMPARATOR = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant marsplayTrack, @NonNull Restaurant t1) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant marsplayTrack, @NonNull Restaurant t1) {
            return false;
        }
    };
    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }

}
