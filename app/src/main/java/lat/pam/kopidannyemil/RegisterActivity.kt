package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val etFullname = findViewById<EditText>(R.id.fullname)
        val etUsername = findViewById<EditText>(R.id.username)
        val etPassword = findViewById<EditText>(R.id.password)
        val btnregister = findViewById<MaterialButton>(R.id.btnregister)
        val txtLogin = findViewById<TextView>(R.id.txtlogin)

        btnregister.setOnClickListener {
            val fullname = etFullname.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            if(fullname.isEmpty() || username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)

                sharedPref.edit().apply {
                    putString("fullname", fullname)
                    putString("username", username)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }
    }
}