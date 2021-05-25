package com.example.timezones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class timezonesAdapter extends
        RecyclerView.Adapter<timezonesAdapter.ListItemHolder> {

    private List<timezone> mtimezoneList;
    private MainActivity mMainActivity;

    public timezonesAdapter(MainActivity mainActivity, List<timezone> timezoneList) {
        mMainActivity = mainActivity;
        mtimezoneList = timezoneList;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timezone_fragment_layout, parent, false);

        return new ListItemHolder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        timezone note = mtimezoneList.get(position);
        holder.mTitle.setText(note.getTitle());

    }

    @Override
    public int getItemCount() {
        return mtimezoneList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public ListItemHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.timeZoneTitle);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mMainActivity.showTimezoneTime(getAdapterPosition());
        }
    }

}

