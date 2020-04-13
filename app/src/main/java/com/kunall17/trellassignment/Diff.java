package com.kunall17.trellassignment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.kunall17.trellassignment.folder.Restaurant;

import java.util.List;

public class Diff extends DiffUtil.Callback {

    List<Restaurant> oldPersons;
    List<Restaurant> newPersons;

    public Diff(List<Restaurant> newPersons, List<Restaurant> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getRestaurant().getId() == newPersons.get(newItemPosition).getRestaurant().getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}