package com.project.roombasic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    //id가 PrimaryKey 1,2,3 순서대로
    var id : Long?,
    var memo : String = ""
)
