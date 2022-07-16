package com.project.roombasic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = arrayOf(MemoEntity::class), version = 1)
@Database(entities = [MemoEntity::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDAO() : MemoDAO

    companion object {
        var INSTANCE : MemoDatabase? = null
        //fallbackToDestructiveMigration() 모든 data를 drop하고 새로운 버전으로 data 이동
        fun getInstance(context : Context) : MemoDatabase? {
            if(INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}