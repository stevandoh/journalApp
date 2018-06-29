package com.stevandoh.journalapp.journalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stevandoh.journalapp.journalapp.EditActivity;
import com.stevandoh.journalapp.journalapp.R;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.stevandoh.journalapp.journalapp.utilities.Constants.NOTE_ID_KEY;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private final List<EntryEntity> mEntries;
    private final Context mContext;

    public EntryAdapter(Context mContext, List<EntryEntity> mEntries) {
        this.mContext = mContext;
        this.mEntries = mEntries;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.entry_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder holder, int position) {
        final EntryEntity entry = mEntries.get(position);
        holder.mTextView.setText(entry.getContent());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra(NOTE_ID_KEY, entry.getId());
                mContext.startActivity(intent);
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(entry.getDate());
        holder.mTvDate.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_entry)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
