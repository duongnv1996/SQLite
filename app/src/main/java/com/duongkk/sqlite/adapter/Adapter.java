package com.duongkk.sqlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duongkk.sqlite.R;
import com.duongkk.sqlite.models.Student;

import java.util.List;

/**
 * Created by DuongKK on 10/6/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Student> list;
    Context context;
    CallBack callBack;

    public Adapter(List<Student> list, Context context, CallBack callBack) {
        this.list = list;
        this.context = context;
        this.callBack = callBack;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(holder!=null){
            Student student = list.get(position);
            holder.textView.setText(student.getText());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onCallBack(position);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll);
        }
    }

    public interface  CallBack{
        void onCallBack(int position);
    }
}
