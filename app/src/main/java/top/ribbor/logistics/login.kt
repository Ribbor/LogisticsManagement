package top.ribbor.logistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class login : AppCompatActivity() {
    lateinit var launch01: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // 获取布局元素
        val account = findViewById<EditText>(R.id.account)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val exit = findViewById<Button>(R.id.exit)
        val whoknow2 = findViewById<TextView>(R.id.whoknow2)

        launch01 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val data = it.data
            val resultCode = it.resultCode
            whoknow2.text = getString(R.string.no_one_konws)
            if (resultCode == RESULT_OK) {
                val who = data?.extras?.get("who")
                if(who!=""){
                    whoknow2.text = getString(R.string.some_one_konws,who)
                }
            }
        }
        login.setOnClickListener {
            val intent = Intent(this, login_result::class.java)
            intent.putExtra("account", account.text.toString())
            intent.putExtra("password", password.text.toString())
            launch01.launch(intent)
        }
    }
}