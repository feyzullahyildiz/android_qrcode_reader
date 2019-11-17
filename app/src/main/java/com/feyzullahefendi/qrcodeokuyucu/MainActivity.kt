package com.feyzullahefendi.qrcodeokuyucu

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    companion object {
        const val TAG = "TAGTAG"
    }

    lateinit var resonseTextView: TextView
    lateinit var mScannerView: ZXingScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mScannerView = ZXingScannerView(this)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        val contentFrame = findViewById<ViewGroup>(R.id.content_frame)
        resonseTextView = findViewById<TextView>(R.id.response_text_view)
        contentFrame.addView(mScannerView)
        var restartButton = findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            resonseTextView.text = ""
            mScannerView.resumeCameraPreview(this)
        }
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera()
    }

    override fun onStop() {
        super.onStop()
        mScannerView.stopCamera()
    }


    override fun handleResult(rawResult: Result?) {
        val text = rawResult!!.text
        Log.v(TAG, text)
        resonseTextView.text = text
        Log.v(TAG, rawResult.barcodeFormat.toString())

//        mScannerView.resumeCameraPreview(this)
    }
}
