package ew.mycoursework;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    String[] results;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView myCard;
        TextView test;
        TextView result;

        ViewHolder(View itemView) {
            super(itemView);
            myCard = itemView.findViewById(R.id.card);
            test = myCard.findViewById(R.id.testNameInResults);
            result = myCard.findViewById(R.id.resultOfTheTest);
        }
    }

    ResultsAdapter(String[] results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {
        if (results.length != 0) {
            String[] oneCard = results[position].split(":");
            holder.test.setText(oneCard[0]);
            double d = Double.valueOf(oneCard[1]);
            d*=10000;
            d/=(double)100;
            holder.result.setText(d+"%");
        }
    }

    @Override
    public int getItemCount() {
        return results.length;
    }
}
