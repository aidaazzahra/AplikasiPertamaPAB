package com.example.aplikasipertama

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasipertama.model.Student

class ListAdapter : PagingDataAdapter<Student, ListAdapter.ViewHolder>(DIFF_CALLBACK) {
//
//   val students = mutableListOf<Student>()
//
//    fun setListStudents(students: List<Student>) {
//        val diffCallback = StudentDiffCallback(this.students, students)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        this.students.clear()
//        this.students.addAll(students)
//        diffResult.dispatchUpdatesTo(this)
//    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var student: Student
        private lateinit var tvName: TextView
        private lateinit var tvMajor: TextView

        fun bind(student: Student) {
            this.student = student
            tvName = itemView.findViewById(R.id.tv_item_name)
            tvMajor = itemView.findViewById(R.id.tv_item_major)

            student.apply {
                tvName.text = name
                tvMajor.text = major
            }
        }

        fun getStudent() = student
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("STUDENT", item)
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}

//class StudentDiffCallback(private val oldStudents: List<Student>, private val newStudents: List<Student>) : DiffUtil.Callback() {
//    override fun getOldListSize() = oldStudents.size
//
//    override fun getNewListSize() = newStudents.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldStudents[oldItemPosition].name == newStudents[newItemPosition].name
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldStudent = oldStudents[oldItemPosition]
//        val newStudent = newStudents[newItemPosition]
//        return oldStudent.name == newStudent.name && oldStudent.major == newStudent.major
//    }
//}