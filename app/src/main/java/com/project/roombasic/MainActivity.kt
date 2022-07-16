package com.project.roombasic

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() , OnDeleteListener {

    lateinit var db : MemoDatabase
    var memoList : MutableList<MemoEntity> = mutableListOf()

    private lateinit var recyclerview : RecyclerView
    lateinit var myadapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val AddButton : Button = findViewById(R.id.add_button)
        val EditText : TextView = findViewById(R.id.edit_text)
        recyclerview = findViewById(R.id.recyclerview)

        db = MemoDatabase.getInstance(this)!!

        AddButton.setOnClickListener {
            val memo = MemoEntity(null, EditText.text.toString())
            EditText.text = ""
            insertMemo(memo)
            myadapter.list.add(memo)
            myadapter.notifyDataSetChanged()
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
            }
        }
        deleteTask.execute()
    }

    fun setRecyclerView(memoList : MutableList<MemoEntity>) {
        myadapter = MyAdapter(this, memoList, this)
        recyclerview.adapter = myadapter
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
        myadapter.list.remove(memo)
        myadapter.notifyDataSetChanged()
    }
}