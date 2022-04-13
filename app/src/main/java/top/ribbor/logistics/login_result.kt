package top.ribbor.logistics

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class login_result : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    lateinit var time: TextView
    var hour = 0
    var minute = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_result)
        val account = findViewById<EditText>(R.id.account)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val exit = findViewById<Button>(R.id.exit)
        val browser = findViewById<Button>(R.id.browser)
        val who = findViewById<EditText>(R.id.who)
        time = findViewById(R.id.time)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)


        val account_text = intent.getStringExtra("account")
        val password_text = intent.getStringExtra("password")
        account.setText(getString(R.string.your_account_msg, account_text))
        password.setText(getString(R.string.your_passwd_msg, password_text))
        val current_time = LocalDateTime.now()
        val time_formatter_hour = DateTimeFormatter.ofPattern("HH")
        hour = current_time.format(time_formatter_hour).toInt()
        val time_formatter_minute = DateTimeFormatter.ofPattern("mm")
        minute = current_time.format(time_formatter_minute).toInt()
        time.text =
            getString(R.string.time_now, String.format("%02d", hour), String.format("%02d", minute))
        browser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
        }
        exit.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("who", who.text.toString()))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    inner class TimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (minute == 59) {
                if (hour == 23) {
                    hour = 0
                } else {
                    hour++
                }
                minute = 0
            } else {
                minute++
            }
            time.text = getString(
                R.string.time_now,
                String.format("%02d", hour),
                String.format("%02d", minute)
            )
            // other way
            /*
            val current_time = LocalDateTime.now()
            val time_formatter_hour = DateTimeFormatter.ofPattern("HH")
            hour = current_time.format(time_formatter_hour).toInt()
            val time_formatter_minute = DateTimeFormatter.ofPattern("mm")
            minute = current_time.format(time_formatter_minute).toInt()
            time.text =
                getString(R.string.time_now, String.format("%02d", hour), String.format("%02d", minute))
            */
            // For test
            Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
        }
    }
}