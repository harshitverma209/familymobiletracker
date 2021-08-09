package com.hyg.awesome.familymobiletracker.feature.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyg.awesome.familymobiletracker.feature.Models.GroupModel;
import com.hyg.awesome.familymobiletracker.feature.Models.MemberModel;
import com.hyg.awesome.familymobiletracker.feature.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MemberAdapter extends ArrayAdapter<MemberModel>{
    public MemberAdapter(@NonNull Context context, MemberModel memberModel[]) {
        super(context, R.layout.mem_item,memberModel);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MemberModel memberModel=getItem(position);
        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.mem_item, parent,false);
        TextView memberName=convertView.findViewById(R.id.gname);
        memberName.setText(memberModel.getMemberName());
        TextView lUpdate=convertView.findViewById(R.id.nom);
        lUpdate.setText("Last Update:"+memberModel.getlUpdate());
        return convertView;
    }
}
