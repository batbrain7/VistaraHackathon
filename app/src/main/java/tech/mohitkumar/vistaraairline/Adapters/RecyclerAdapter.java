package tech.mohitkumar.vistaraairline.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tech.mohitkumar.vistaraairline.R;

/**
 * Created by mohitkumar on 08/10/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<String> arrayList = new ArrayList<String>();
    Context context;

    public RecyclerAdapter(ArrayList<String> arrayList,Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,arrayList,context);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String s = arrayList.get(position);
        holder.content.setText(s);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ArrayList<String> arrayList;
        Context context;
        TextView content;
        public RecyclerViewHolder(View itemView,ArrayList<String> arrayList,Context context) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            content = (TextView) itemView.findViewById(R.id.recycler_text);
        }
    }
}
