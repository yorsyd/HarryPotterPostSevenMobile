package com.asisten.lp_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asisten.lp_7.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.title = "Harry Potter Books"

        // setup recyclerviewz
        binding.rvBooks.layoutManager = LinearLayoutManager(this)

        fetchBooks()
    }

    private fun fetchBooks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://potterapi-fedeperin.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(
                call: Call<List<Book>>,
                response: Response<List<Book>>
            ) {
                if (response.isSuccessful) {
                    val books = response.body() ?: emptyList()

                    adapter = BookAdapter(books) { book ->
                        // click event
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra("title", book.title)
                            putExtra("originalTitle", book.originalTitle)
                            putExtra("releaseDate", book.releaseDate)
                            putExtra("description", book.description)
                            putExtra("pages", book.pages)
                            putExtra("cover", book.cover)
                        }
                        startActivity(intent)
                    }

                    binding.rvBooks.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

