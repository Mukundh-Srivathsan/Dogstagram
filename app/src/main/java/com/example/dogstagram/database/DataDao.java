package com.example.dogstagram.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM data")
    List<Data> getAllData();

    @Query("SELECT imageURL From data")
    List<String> getImgURLs();

    @Insert
    void insertData(Data... data);

    @Delete
    void deleteData(Data data);
}
