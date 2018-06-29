package com.stevandoh.journalapp.journalapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.stevandoh.journalapp.journalapp.database.AppRepository;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;
import com.stevandoh.journalapp.journalapp.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public LiveData< List<EntryEntity>> mEntries ;
    private AppRepository mAppRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
        mEntries = mAppRepository.mEntries;
    }

    public void addSampleData() {
        mAppRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mAppRepository.deleteAllEntries();
    }
}

