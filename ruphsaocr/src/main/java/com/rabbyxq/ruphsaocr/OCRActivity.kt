package com.rabbyxq.ruphsaocr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rabbyxq.rupshaocr.ocrbase.ui.MainFragment

class OCRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ocr_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}