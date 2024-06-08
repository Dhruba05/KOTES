package com.example.kotes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotes.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val url: String = "https://api.quotable.io/random"
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        getQuote()
        binding.nextBtn.setOnClickListener {
            getQuote()
        }

    }

    fun getQuote() {
        setInProgress(true)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                setInProgress(false)

            var responseObj=JSONObject(response)

                binding.quoteTv.text=responseObj.getString("content")
//                binding.authorTv.text=responseObj.getString( "author")
                binding.authorTv.text = responseObj.getString("author")?.let { "- $it" }


            }, { error ->
                {
                    Toast.makeText(this@MainActivity, "${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            })


        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    private  fun setInProgress(intProgress : Boolean){
        if (intProgress){
            binding.progressBar.visibility= View.VISIBLE
            binding.nextBtn.visibility = View.GONE
        }
        else{
            binding.progressBar.visibility= View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        }
    }
}
