package com.rabbyxq.rupshaocr.ocrbase.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rabbyxq.ruphsaocr.databinding.OcrLayoutBinding
import com.rabbyxq.ruphsaocr.ocrbase.Assets
import com.rabbyxq.ruphsaocr.ocrbase.Config

class MainFragment : Fragment() {
    private var binding: OcrLayoutBinding? = null

    private var viewModel: MainViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(
            MainViewModel::class.java
        )

        Assets.extractAssets(requireContext())

        if (!viewModel!!.isInitialized) {
            val dataPath = Assets.getTessDataPath(requireContext())
            viewModel!!.initTesseract(dataPath, Config.TESS_LANG, Config.TESS_ENGINE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OcrLayoutBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.image?.setImageBitmap(Assets.getImageBitmap(requireContext()))
        binding?.start?.setOnClickListener { v ->
            val imageFile = Assets.getImageFile(requireContext())
            viewModel!!.recognizeImage(imageFile)
        }
        binding?.stop?.setOnClickListener { v ->
            viewModel!!.stop()
        }
        binding?.text?.setMovementMethod(ScrollingMovementMethod())

        viewModel!!.processing.observe(viewLifecycleOwner) { processing: Boolean? ->
            binding?.start?.setEnabled(!processing!!)
            if (processing != null) {
                binding?.stop?.setEnabled(processing)
            }
        }
        viewModel!!.progress.observe(viewLifecycleOwner) { progress: String? ->
            binding?.status?.setText(progress)
        }
        viewModel!!.result.observe(viewLifecycleOwner) { result: String? ->
            binding?.text?.setText(result)
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}