package com.stevandoh.journalapp.journalapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.stevandoh.journalapp.journalapp.database.AppRepository;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditViewModel extends AndroidViewModel {

    public MutableLiveData<EntryEntity> mLiveEntry =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int entryId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                EntryEntity entry = mRepository.getEntryById(entryId);
                mLiveEntry.postValue(entry);
            }
        });
    }

    public void saveEntry(String entryText) {
        EntryEntity entry = mLiveEntry.getValue();

        if (entry == null) {
            if (TextUtils.isEmpty(entryText.trim())) {
                return;
            }
            entry = new EntryEntity(new Date(), entryText.trim());
        } else {
            entry.setContent(entryText.trim());
        }
        mRepository.insertEntry(entry);
    }

    public void deleteEntry() {
        mRepository.deleteEntry(mLiveEntry.getValue());
    }
}
