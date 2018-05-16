package ew.mycoursework;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {
    private String[] dataSource;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(40);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent().setClass(view.getContext(), TestActivity.class);
                    intent.putExtra(MainActivity.TEST, ((TextView)view).getText());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    TestsAdapter(String[] dataArgs) {
        dataSource = dataArgs;
    }

    @NonNull
    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }


}
