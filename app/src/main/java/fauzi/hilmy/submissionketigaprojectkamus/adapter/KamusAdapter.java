package fauzi.hilmy.submissionketigaprojectkamus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fauzi.hilmy.submissionketigaprojectkamus.R;
import fauzi.hilmy.submissionketigaprojectkamus.activity.DetailActivity;
import fauzi.hilmy.submissionketigaprojectkamus.model.KamusModel;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.MyViewHolder> {

    private ArrayList<KamusModel> models = new ArrayList<>();

    public KamusAdapter(){

    }

    @NonNull
    @Override
    public KamusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        return new MyViewHolder(view);
    }

    public void addItem(ArrayList<KamusModel> mData) {
        this.models = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final KamusAdapter.MyViewHolder holder, final int position) {
        holder.txtKata.setText(models.get(position).getKata());
        holder.txtArti.setText(models.get(position).getArti());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM_WORD, models.get(position).getKata());
                intent.putExtra(DetailActivity.ITEM_MEANING, models.get(position).getArti());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKata, txtArti;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtKata = itemView.findViewById(R.id.kata);
            txtArti = itemView.findViewById(R.id.arti);
        }
    }

}
