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
        initialSetup()
        clickEvent()


    }

    private fun clickEvent() {
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    // You can set the image to an ImageView or upload it
                    bindingMainActivity.imageViewSingle.setImageURI(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        val pickMedia1 =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    val mimeType = contentResolver.getType(uri)

                    Log.d("PhotoPicker", "Selected URI: $uri")
                    Log.d("PhotoPicker", "MIME Type: $mimeType")

                    if (mimeType?.startsWith("video") == true) {
                        bindingMainActivity.imageViewWithVideoSingle.visibility = View.GONE
                        bindingMainActivity.videoViewSingle.apply {
                            setVideoURI(uri)
                            visibility = View.VISIBLE
                            start()
                        }
                    } else if (mimeType?.startsWith("image") == true) {
                        bindingMainActivity.videoViewSingle.visibility = View.GONE
                        bindingMainActivity.imageViewWithVideoSingle.apply {
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

        bindingMainActivity.buttonForSingle.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        bindingMainActivity.buttonForSingleVideoImage.setOnClickListener {
            pickMedia1.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        }

    }

    private fun initialSetup() {
        // Inside your Activity or Fragment


    }
}