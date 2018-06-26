package com.stevandoh.journalapp.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(EntryEntity entryEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EntryEntity> entries);

    @Delete
    void deleteEntry(EntryEntity entryEntity);

    @Query("SELECT * FROM entries WHERE id = :id")
    EntryEntity getEntryById(int id);

    @Query("SELECT * FROM entries ORDER BY date DESC")
    LiveData<List<EntryEntity>> getAll();

    @Query("DELETE FROM entries")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM entries")
    int getCount();


}
