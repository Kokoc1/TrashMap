package com.example.diplommap2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val etMail = findViewById<EditText>(R.id.etMail)
        val etPass = findViewById<EditText>(R.id.etPass)
        val btnReg = findViewById<Button>(R.id.btnReg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        btnReg.setOnClickListener {
            val email = etMail.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (email.isEmpty()) {
                etMail.error = "Введите электронную почту"
                etMail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                etPass.error = "Пароль должен иметь не менее 7 символов"
                etPass.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)
        }
    }
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Регистрация прошла успешно",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Переход на LoginActivity
                        val intent = Intent(this, Login::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                } else {
                    Toast.makeText(
                        baseContext, "Ошибка регистрации ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}