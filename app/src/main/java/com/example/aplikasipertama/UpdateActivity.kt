package com.example.aplikasipertama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipertama.model.Student
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateActivity : AppCompatActivity() {

    private val updateViewModel: UpdateViewModel by viewModel()

    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        student = intent.getParcelableExtra("STUDENT")

        val nameEd: EditText = findViewById(R.id.ed_update_name)
        (nameEd as TextView).text = student?.name.orEmpty()
        val majorEd: EditText = findViewById(R.id.ed_update_major)
        (majorEd as TextView).text = student?.major.orEmpty()
        val updateBtn: Button = findViewById(R.id.btn_update)

        updateBtn.setOnClickListener {
            val updatedStudent = Student(
                id = student?.id,
                name = nameEd.text.ifEmpty { "" }.toString(),
                major = majorEd.text.ifEmpty { "" }.toString()
            )
            updateViewModel.update(updatedStudent)
        }

        updateViewModel.studentUpdated.observe(this) {
            if (it) {
                finish()
            }
        }
    }
}