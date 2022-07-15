package com.project.roombasic

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() , OnDeleteListener {

    lateinit var db : MemoDatabase
    var memoList : List<MemoEntity> = listOf<MemoEntity>()
    private lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val AddButton : Button = findViewById(R.id.add_button)
        val EditText : TextView = findViewById(R.id.edit_text)
        recyclerview = findViewById(R.id.recyclerview)

        db = MemoDatabase.getInstance(this)!!

        AddButton.setOnClickListener {
            val memo = MemoEntity(null, EditText.text.toString())
            EditText.setText("")
            insertMemo(memo)
        }
        getAllMemos()
    }

    fun insertMemo(memo : MemoEntity) {

        val insertTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        insertTask.execute()
    }

    fun getAllMemos() {
        val getTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }
        getTask.execute()
    }

    fun deleteMemo(memo : MemoEntity) {
        val deleteTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        deleteTask.execute()
    }

    fun setRecyclerView(memoList : List<MemoEntity>) {
        recyclerview.adapter = MyAdapter(this, memoList, this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}