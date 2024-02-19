package com.example.virtualdressup2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class AvatarCreationActivity : AppCompatActivity() {
    // ImageView to display the avatar
    private var avatarImageView: ImageView? = null
    // Bitmap to hold the avatar image
    private var avatarBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avatar_creation)
        // Initialize avatarImageView with the view from the layout
        avatarImageView = findViewById(R.id.avatar_image)
    }

    // Function to initiate taking a photo
    fun takePhoto(view: View?) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission to access the camera
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            // If permission is granted, start the camera intent
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Function to choose an image from the gallery
    fun chooseFromGallery(view: View?) {
        // Create intent to pick an image from the gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY)
    }

    // Function to verify the selected avatar
    fun verifyAvatar(view: View?) {
        if (avatarBitmap != null) {
            // Implement verification logic here
            Toast.makeText(this, "Avatar verified!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please select an image first.", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle result of taking a photo or choosing from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // If image is captured from camera, set it to avatarImageView
                val extras = data!!.extras
                if (extras != null) {
                    avatarBitmap = extras["data"] as Bitmap?
                    avatarImageView!!.setImageBitmap(avatarBitmap)
                }
            } else if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {
                // If image is chosen from gallery, set it to avatarImageView
                val selectedImage = data!!.data
                try {
                    avatarBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    avatarImageView!!.setImageBitmap(avatarBitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If camera permission is granted, initiate taking photo again
                takePhoto(findViewById(android.R.id.content))
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1 // Request code for capturing image from camera
        private const val REQUEST_IMAGE_FROM_GALLERY = 2 // Request code for choosing image from gallery
    }
}