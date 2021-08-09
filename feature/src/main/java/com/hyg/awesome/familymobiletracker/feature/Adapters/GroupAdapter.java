package com.hyg.awesome.familymobiletracker.feature.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyg.awesome.familymobiletracker.feature.Models.GroupModel;
import com.hyg.awesome.familymobiletracker.feature.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GroupAdapter extends ArrayAdapter<GroupModel>{
    public GroupAdapter(@NonNull Context context, GroupModel groupModel[]) {
        super(context, R.layout.group_item,groupModel);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GroupModel groupModel=getItem(position);
        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.group_item, parent,false);
        TextView gname=convertView.findViewById(R.id.gname);
        gname.setText(groupModel.getGroupName());
        TextView Gnom=convertView.findViewById(R.id.nom);
        Gnom.setText("Members:"+groupModel.getGnom());
        return convertView;
    }
}
