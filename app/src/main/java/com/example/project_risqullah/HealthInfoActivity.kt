package com.example.project_risqullah

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.webkit.WebViewClient
import com.example.project_risqullah.databinding.ActivityHealthInfoBinding

class HealthInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthInfoBinding

    private val healthInfoUrl =
        "https://ayosehat.kemkes.go.id/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityHealthInfoBinding.inflate(
            layoutInflater
        )

        setContentView(binding.root)

        // Mengatur system bar dan inset
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Mengaktifkan Toolbar
        setupToolbar()

        // Mengaktifkan WebView
        setupWebView()
    }

    // =====================================
    // PENGATURAN TOOLBAR
    // =====================================

    private fun setupToolbar() {

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {

            title = "Informasi Kesehatan"

            subtitle = "Ayo Sehat • Kemenkes RI"

            setDisplayHomeAsUpEnabled(true)

            setDisplayShowHomeEnabled(true)

            setHomeAsUpIndicator(
                R.drawable.ic_arrow_back
            )
        }

        binding.toolbar.navigationIcon?.setTint(
            getColor(R.color.white)
        )
    }

    // =====================================
    // PENGATURAN WEBVIEW
    // =====================================

    private fun setupWebView() {

        // Membuka link di dalam aplikasi
        binding.webView.webViewClient = WebViewClient()

        // Mengaktifkan JavaScript dan penyimpanan web
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.settings.domStorageEnabled = true

        // Memuat website Ayo Sehat
        binding.webView.loadUrl(healthInfoUrl)

        // Mengatur Toolbar saat WebView di-scroll
        binding.webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            if (scrollY > oldScrollY) {

                // Scroll ke bawah: sembunyikan Toolbar
                binding.appBar.setExpanded(
                    false,
                    true
                )

            } else if (scrollY < oldScrollY) {

                // Scroll ke atas: tampilkan Toolbar
                binding.appBar.setExpanded(
                    true,
                    true
                )
            }
        }
    }

    // =====================================
    // MENAMPILKAN OPTION MENU
    // =====================================

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(
            R.menu.health_info_menu,
            menu
        )

        return true
    }

    // =====================================
    // AKSI OPTION MENU DAN TOMBOL BACK
    // =====================================

    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {

        return when (item.itemId) {

            // Tombol kembali di Toolbar
            android.R.id.home -> {

                onBackPressedDispatcher.onBackPressed()

                true
            }

            // Muat ulang WebView
            R.id.action_refresh -> {

                binding.webView.reload()

                true
            }

            // Buka melalui browser eksternal
            R.id.action_open_browser -> {

                try {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(healthInfoUrl)
                    )

                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {

                    Toast.makeText(
                        this,
                        "Browser tidak tersedia.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Membersihkan WebView ketika Activity ditutup
    override fun onDestroy() {

        binding.webView.stopLoading()

        binding.webView.destroy()

        super.onDestroy()
    }
}
