package com.project.roombasic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface MemoDAO {

    //PrimaryKey가 일치할시 덮어씌운다.
    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    // *(별) -> all이라는 뜻
    @Query("SELECT * FROM memo")
    fun getAll() : MutableList<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)

}