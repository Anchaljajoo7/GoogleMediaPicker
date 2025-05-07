package com.github.googlemediapicker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.googlemediapicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainActivity: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
        clickEvent()


    }

    private fun clickEvent() {
        val pickMediaImages =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    bindingMainActivity.imageViewSingle.setImageURI(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        val pickMediaBoth =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    val mimeType = contentResolver.getType(uri)

                    Log.d("PhotoPicker", "Selected URI: $uri")
                    Log.d("PhotoPicker", "MIME Type: $mimeType")

                    if (mimeType?.startsWith("video") == true) {
                        bindingMainActivity.imageViewBoth.visibility = View.GONE
                        bindingMainActivity.videoViewBoth.apply {
                            setVideoURI(uri)
                            visibility = View.VISIBLE
                            start()
                        }
                    } else if (mimeType?.startsWith("image") == true) {
                        bindingMainActivity.videoViewBoth.visibility = View.GONE
                        bindingMainActivity.imageViewBoth.apply {
                            setImageURI(uri)
                            visibility = View.VISIBLE
                        }
                    } else {
                        Log.d("PhotoPicker", "Unsupported media type.")
                    }

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        val pickMediaVideo =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

                Log.d("Anchal", "clickEvent: "+uri)
                if (uri != null) {
                    bindingMainActivity.videoView.apply {
                        setVideoURI(uri)
                        visibility = View.VISIBLE
                        start()
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        bindingMainActivity.buttonSingleImage.setOnClickListener {
            pickMediaImages.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingMainActivity.buttonSingleVideoImage.setOnClickListener {
            pickMediaBoth.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        }

        bindingMainActivity.buttonSingleVideo.setOnClickListener {
            Log.d("PhotoPicker", "Launching video picker")
            pickMediaVideo.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }

    }


}