package com.techvd.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.techvd.newsapp.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var page= 1
    private var list = mutableListOf<Data>()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.searchButton.setOnClickListener{
            list = mutableListOf<Data>()
            Search()
        }

        binding.loadButton.setOnClickListener{
           page = page + 1
            Search()


        }
    }

    private fun Search(){
        val word = binding.mainEdit.text
        val ApiKey = "61f9fd07-5abf-4d2e-9362-6030a4b87c7e"
        val pageSize = 10
        val url = "https://content.guardianapis.com/search?page=$page&page-size=$pageSize&q=$word&api-key=$ApiKey"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET,url,
            { response ->
                try{
                extractResponseFromDefinition(response)}
                catch(exception:Exception){
                    exception.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()
            })

        queue.add(stringRequest)
    }


    private fun extractResponseFromDefinition(response : String){
        val jsonObject = JSONObject(response)
        val jsonResponse =  jsonObject.getJSONObject("response")
        val jsonResult = jsonResponse.getJSONArray("results")


        for (i in 0..9){
            val item = jsonResult.getJSONObject(0)
            val title = item.getString("webTitle")
            val url = item.getString("webUrl")
            val data = Data(title, url)
            list.add(data)
        }

        val adapter = NewsAdapter(list)

        binding.listView.adapter = adapter
    }
}