package com.project.roombasic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context : Context,
                var list : List<MemoEntity>,
                var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount() = list.size

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        val imageView: ImageView
        init {
            // Define click listener for the ViewHolder's View.
            textView = itemView.findViewById(R.id.todo_text)
            imageView = itemView.findViewById(R.id.delete_image_view)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.item_todo, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val memo = list[position]

        holder.textView.text = memo.memo
        holder.imageView.setOnClickListener {
            onDeleteListener.onDeleteListener(memo)
        }
    }
}