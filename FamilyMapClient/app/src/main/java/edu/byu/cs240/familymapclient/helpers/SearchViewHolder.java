package edu.byu.cs240.familymapclient.helpers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private static final int PERSON_VIEW_TYPE = 0;
    private static final int EVENT_VIEW_TYPE = 1;

    private final int viewType;

    SearchViewHolder(View view, int viewType) {
        super(view);
        this.viewType = viewType;

        view.setOnClickListener(v -> {

        });

        if (viewType == PERSON_VIEW_TYPE) {     // Person

        } else {    // Event

        }
    }
}
