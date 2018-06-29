package com.stevandoh.journalapp.journalapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.stevandoh.journalapp.journalapp.database.AppDatabase;
import com.stevandoh.journalapp.journalapp.database.EntryDao;
import com.stevandoh.journalapp.journalapp.database.EntryEntity;
import com.stevandoh.journalapp.journalapp.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private EntryDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.entryDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveEntries() {
        mDao.insertAll(SampleData.getEntries());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveEntries: count=" + count);
        assertEquals(SampleData.getEntries().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getEntries());
        EntryEntity original = SampleData.getEntries().get(0);
        EntryEntity fromDb = mDao.getEntryById(1);
        assertEquals(original.getContent(), fromDb.getContent());
        assertEquals(1, fromDb.getId());
    }
}
