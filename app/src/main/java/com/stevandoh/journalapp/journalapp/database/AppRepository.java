package com.stevandoh.journalapp.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.stevandoh.journalapp.journalapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;
    public LiveData<List<EntryEntity>> mEntries;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mEntries = getAllEntries();
    }

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.entryDao().insertAll(SampleData.getEntries());
            }
        });
    }

    private LiveData<List<EntryEntity>> getAllEntries() {
        return mDb.entryDao().getAll();
    }

    public void deleteAllEntries() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.entryDao().deleteAll();
            }
        });
    }

    public EntryEntity getEntryById(int entryId) {
        return mDb.entryDao().getEntryById(entryId);
    }

    public void insertEntry(final EntryEntity entry) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.entryDao().insertEntry(entry);
            }
        });
    }

    public void deleteEntry(final EntryEntity entry) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.entryDao().deleteEntry(entry);
            }
        });
    }
}
