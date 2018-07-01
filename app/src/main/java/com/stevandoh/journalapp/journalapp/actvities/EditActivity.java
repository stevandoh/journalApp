package com.stevandoh.journalapp.journalapp.actvities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stevandoh.journalapp.journalapp.R;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;
import com.stevandoh.journalapp.journalapp.viewmodel.EditViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.stevandoh.journalapp.journalapp.utilities.Constants.EDITING_KEY;
import static com.stevandoh.journalapp.journalapp.utilities.Constants.ENTRY_ID_KEY;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.txt_entry)
    EditText mTxtEntry;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private EditViewModel mViewModel;
    private boolean mNewNote, mEditing;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (content != null && !content.isEmpty()) {
//                    shareContent(content);
                } else {
                    new MaterialDialog.Builder(EditActivity.this)
                            .title("Oops")
                            .content("No content yet. Kindly add your thoughts before sharing")
                            .positiveText(R.string.btn_ok)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(EditViewModel.class);

        mViewModel.mLiveEntry.observe(this, new Observer<EntryEntity>() {
            @Override
            public void onChanged(@Nullable EntryEntity entryEntity) {
                if (entryEntity != null && !mEditing) {
                    mTxtEntry.setText(entryEntity.getContent());
                    content = entryEntity.getContent();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_entry));
            mNewNote = true;
        } else {
            setTitle(getString(R.string.edit_entry));
            int noteId = extras.getInt(ENTRY_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            new MaterialDialog.Builder(EditActivity.this)
                    .title(R.string.title_delete_entry)
                    .content(R.string.cnt_delete_entry)
                    .positiveText(R.string.btn_ok)
                    .negativeText(R.string.btn_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mViewModel.deleteEntry();
                            finish();
                        }
                    })
                    .show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveEntry(mTxtEntry.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }




//    private fun createShareIntent() {
//        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
//        sharingIntent.type = "text/plain"
//        val shareBody = "Enjoying ".plus(caption).plus(" on Ziptv \nDownload here https://www.myziptv.com/download/playstore/ ")
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Join Ziptv ")
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
//        startActivity(Intent.createChooser(sharingIntent, "Share via"))
//
//    }
}



