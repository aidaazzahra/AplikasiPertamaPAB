package com.example.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipertama.model.Student

class DetailActivity : AppCompatActivity() {
    private var student: Student? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
//      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//          val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//          insets
//        }

//        val name = intent.getStringExtra("NAME")
//        val major = intent.getStringExtra("MAJOR")

        student = intent.getParcelableExtra("STUDENT")

        Log.d("DetailActivity", "name ${student?.name}")
        Log.d("DetailActivity", "major ${student?.major}")

        val tvName = findViewById<TextView>(R.id.tv_detail_name)
        val tvMajor = findViewById<TextView>(R.id.tv_detail_major)

        tvName.text = student?.name
        tvMajor.text = student?.major
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_update) {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("STUDENT", student)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}