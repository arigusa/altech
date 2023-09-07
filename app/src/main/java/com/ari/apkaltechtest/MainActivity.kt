package com.ari.apkaltechtest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ari.apkaltechtest.retrofit.ConfigApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object {
        const val PREFS_KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = findViewById<RecyclerView>(R.id.rv_api)

        ConfigApi.getService().getApi().enqueue(object : Callback<ResponseApi> {
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.isSuccessful) {
                    val responseApi = response.body()
                    val dataApi = responseApi?.results
                    val apiAdapter = ApiAdapter(dataApi)
                    data.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        setHasFixedSize(true)
                        apiAdapter.notifyDataSetChanged()
                        adapter = apiAdapter
                    }
                }
            }
        })

        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)
        Log.d("MainActivity", "isLoggedIn: $isLoggedIn")
        if (!isLoggedIn) {
            // Jika pengguna TIDAK login, tampilkan tombol back di toolbar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false) // Baca status login dari SharedPreferences

        if (isLoggedIn) {
            menuInflater.inflate(R.menu.toolbar_menu, menu) // Tampilkan menu jika sudah login
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tbLogout -> {
                // Handle logout
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            R.id.tbNotification -> {
                // Handle notification
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                // Handle toolbar back button
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
