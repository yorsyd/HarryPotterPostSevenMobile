package com.asisten.lp_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asisten.lp_7.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val title = intent.getStringExtra("title")
        val originalTitle = intent.getStringExtra("originalTitle")
        val releaseDate = intent.getStringExtra("releaseDate")
        val description = intent.getStringExtra("description")
        val pages = intent.getIntExtra("pages", 0)
        val cover = intent.getStringExtra("cover")

        binding.txtDetailTitle.text = title
        binding.txtDetailOriginalTitle.text = originalTitle
        binding.txtDetailRelease.text = releaseDate
        binding.txtDetailDesc.text = description
        binding.txtDetailPage.text = "Pages: $pages"

        Glide.with(this)
            .load(cover)
            .into(binding.imgDetailCover)
    }
}
