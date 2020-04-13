
package com.kunall17.trellassignment.folder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllReviews {

    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

}
