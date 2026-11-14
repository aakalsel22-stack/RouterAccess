package com.example.routeraccess

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.LinkProperties
import android.net.RouteInfo
import android.net.Uri
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var gatewayText: TextView
    private lateinit var addressInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildScreen()
        detectGateway()
    }

    private fun buildScreen() {
        val outer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 42, 28, 28)
            setBackgroundColor(0xFFF4F7FB.toInt())
        }

        val title = TextView(this).apply {
            text = "Router Access"
            textSize = 28f
            setTextColor(0xFF12345A.toInt())
            typeface = Typeface.DEFAULT_BOLD
        }
        outer.addView(title)

        val subtitle = TextView(this).apply {
            text = "Temukan gateway Wi-Fi dan buka halaman admin router."
            textSize = 15f
            setTextColor(0xFF455A64.toInt())
            setPadding(0, 10, 0, 28)
        }
        outer.addView(subtitle)

        gatewayText = TextView(this).apply {
            text = "Gateway: sedang diperiksa..."
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(0xFF263238.toInt())
            setPadding(0, 12, 0, 16)
        }
        outer.addView(gatewayText)

        addressInput = EditText(this).apply {
            hint = "IP router, contoh 192.168.1.1"
            textSize = 16f
            singleLine = true
            setTextColor(0xFF102030.toInt())
            setPadding(16, 12, 16, 12)
            setBackgroundColor(0xFFFFFFFF.toInt())
        }
        outer.addView(addressInput, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))

        val openButton = Button(this).apply {
            text = "Buka Halaman Admin"
            setOnClickListener { openRouterPage() }
        }
        outer.addView(openButton)

        val refreshButton = Button(this).apply {
            text = "Deteksi Ulang Gateway"
            setOnClickListener { detectGateway() }
        }
        outer.addView(refreshButton)

        val note = TextView(this).apply {
            text = "Catatan: sambungkan HP ke Wi-Fi router terlebih dahulu. Gunakan akun admin yang sah. Aplikasi ini tidak menebak atau menyimpan password."
            textSize = 13f
            setTextColor(0xFF607D8B.toInt())
            setPadding(0, 28, 0, 0)
        }
        outer.addView(note)

        setContentView(outer)
    }

    private fun detectGateway() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        if (network == null) {
            gatewayText.text = "Gateway: tidak ada jaringan aktif"
            return
        }

        val caps = cm.getNetworkCapabilities(network)
        if (caps == null || !caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            gatewayText.text = "Gateway: sambungkan HP ke Wi-Fi"
            return
        }

        val links: LinkProperties? = cm.getLinkProperties(network)
        val gateway = links?.routes
            ?.firstOrNull { it.isDefaultRoute && it.gateway != null }
            ?.gateway?.hostAddress

        if (!gateway.isNullOrBlank()) {
            gatewayText.text = "Gateway terdeteksi: $gateway"
            addressInput.setText("http://$gateway")
        } else {
            gatewayText.text = "Gateway tidak ditemukan. Masukkan IP router manual."
        }
    }

    private fun openRouterPage() {
        var address = addressInput.text.toString().trim()
        if (address.isEmpty()) {
            Toast.makeText(this, "Masukkan alamat IP router terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }
        if (!address.startsWith("http://") && !address.startsWith("https://")) {
            address = "http://$address"
        }
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Alamat router tidak dapat dibuka", Toast.LENGTH_LONG).show()
        }
    }
}
