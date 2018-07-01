package com.stevandoh.journalapp.journalapp.actvities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.stevandoh.journalapp.journalapp.Adapters.EntryAdapter;
import com.stevandoh.journalapp.journalapp.R;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;
import com.stevandoh.journalapp.journalapp.utilities.SharedPrefManager;
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
    SharedPrefManager mSharedPrefManager;


    private MainViewModel mViewModel;
    private List<EntryEntity> entriesData = new ArrayList<>();
    private EntryAdapter mEntryAdapter;
    private Drawer mDrawer;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @OnClick(R.id.fab_add)
    void setClickHandler() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSharedPrefManager = new SharedPrefManager(getApplicationContext());

        setSupportActionBar(toolbar);
        toolbar.setTitle("Journal App");
        mAuth = FirebaseAuth.getInstance();
        setViewModel();
        setRecyclerView();

        setDrawer(toolbar);


    }

    private void setDrawer(Toolbar toolbar) {

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.wave)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(mSharedPrefManager.getName())
                                .withEmail(mSharedPrefManager.getUserEmail())

                )
                .build();


        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.title_logout);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 0) {
                                signOut();
                            }
                        }

                        return false;
                    }
                })
                .build();
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
            new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.title_delete_entry)
                    .content(R.string.cnt_delete__all)
                    .positiveText(R.string.btn_ok)
                    .negativeText(R.string.btn_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            deleteAllNotes();
                            finish();
                        }
                    })
                    .show();


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


    private void signOut() {

        new MaterialDialog.Builder(MainActivity.this)
                .title(R.string.title_signout)
                .content(R.string.cnt_signout)
                .negativeText(R.string.btn_cancel)
                .positiveText(R.string.btn_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mSharedPrefManager.clear();
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

                        mAuth.signOut();


                        mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this,
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                                        finish();
                                    }
                                });

                        revokeAccess();



                    }
                }).show();
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


}
