package io.github.alamo18.updater.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.alamo18.updater.R;
import io.github.alamo18.updater.model.json.Repo;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Repo> repolist;

    public DataAdapter(ArrayList<Repo> android) {
        this.repolist = android;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText(repolist.get(i).getName());
        viewHolder.tv_json_location.setText(repolist.get(i).getJsonLocation());
        viewHolder.tv_dev.setText(repolist.get(i).getDev());
    }

    @Override
    public int getItemCount() {
        return repolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_json_location,tv_dev;
        public ViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.name);
            tv_json_location = view.findViewById(R.id.json_location);
            tv_dev = view.findViewById(R.id.dev);

        }
    }

}