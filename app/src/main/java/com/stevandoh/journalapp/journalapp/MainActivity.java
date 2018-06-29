package com.stevandoh.journalapp.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stevandoh.journalapp.journalapp.Adapters.EntryAdapter;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;
import com.stevandoh.journalapp.journalapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private MainViewModel mViewModel;
    private List<EntryEntity> entriesData = new ArrayList<>();
    private EntryAdapter mEntryAdapter;

    @OnClick(R.id.fab)
    void setClickHandler() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Journal App");
        setViewModel();
        setRecyclerView();
    }

    private void setViewModel() {
        final Observer<List<EntryEntity>> entriesObserver =
                new Observer<List<EntryEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<EntryEntity> entriesEntities) {
                        entriesData.clear();
                        entriesData.addAll(entriesEntities);

                        if (mEntryAdapter == null) {
                            mEntryAdapter = new EntryAdapter(
                                    MainActivity.this, entriesData);
                            mRvList.setAdapter(mEntryAdapter);
                        } else {
                            mEntryAdapter.notifyDataSetChanged();
                        }

                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mViewModel.mEntries.observe(this, entriesObserver);
    }

    private void setRecyclerView() {
        mRvList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRvList.getContext(), linearLayoutManager.getOrientation());
        mRvList.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllNotes();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }


}
