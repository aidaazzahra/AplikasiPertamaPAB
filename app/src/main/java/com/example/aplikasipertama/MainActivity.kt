package com.example.aplikasipertama

import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth


        val actionBar = supportActionBar
        actionBar!!.title = "Main"

        val textView = findViewById<TextView>(R.id.tv_hello)
        val button = findViewById<Button>(R.id.btn_tombol)
        val imageView = findViewById<ImageView>(R.id.iv_avatar)
        val btnNextPage = findViewById<Button>(R.id.btn_next_page)
        val edEmail = findViewById<EditText>(R.id.ed_email)
        val edPassword = findViewById<EditText>(R.id.ed_password)

//        imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_account_circle_black))

        Glide
            .with(this@MainActivity)
            .load("https://upload.wikimedia.org/wikipedia/commons/3/37/Lambang_UNJ_dan_moto.png")
            .centerCrop()
            .into(imageView)

        button.setOnClickListener {
            val email = edEmail.text.toString()
            if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email tidak cocok dan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val password = edPassword.text.toString()
            if (password.isEmpty() && password.length < 8) {
                Toast.makeText(this, "Password tidak boleh kurang dari 8 dan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) {
//                    val user = auth.currentUser
//                    val intent = Intent(this, ProfileActivity::class.java)
//                    intent.putExtra("EMAIL", user?.email)
//                    startActivity(intent)
//                }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    val user = auth.currentUser
                    Log.d("signin", user?.email.toString())
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("EMAIL", user?.email.toString())
                    startActivity(intent)
                }
        }

        btnNextPage.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
//            finish()
        }
    }
}