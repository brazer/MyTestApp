package com.salanevich.testapp.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.salanevich.testapp.databinding.ActivityDetailsBinding

private const val KEY_URL_EXTRA = "url"

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, url: String) = Intent(context, DetailsActivity::class.java).apply {
            putExtra(KEY_URL_EXTRA, url)
        }
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.webViewClient = WebClient()
        binding.webView.loadUrl(getUrl())
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun getUrl(): String = intent.getStringExtra(KEY_URL_EXTRA)!!

    private inner class WebClient: WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressbar.isVisible = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressbar.isVisible = false
        }
    }

}