package com.example.diplommap2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegLog = findViewById<Button>(R.id.btnRegLog)
        btnRegLog.setOnClickListener {
            val Intent = Intent(this, Registration::class.java)
            startActivity(Intent)
        }
        val btnMain = findViewById<Button>(R.id.btnMain)
        btnMain.setOnClickListener {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
        auth = FirebaseAuth.getInstance()

        val etMail = findViewById<EditText>(R.id.etMailLog)
        val etPass = findViewById<EditText>(R.id.etPassLog)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etMail.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (email.isEmpty()) {
                etMail.error = "Введите электронную почту"
                etMail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPass.error = "Введите пароль"
                etPass.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Переход на главное активити после успешного входа
                    val intent = Intent(this, MainActivity::class.java) // Замените MainActivity на ваше главное активити
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Ошибка входа: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}