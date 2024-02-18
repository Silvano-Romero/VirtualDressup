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
    private var avatarImageView: ImageView? = null
    private var avatarBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avatar_creation)
        avatarImageView = findViewById(R.id.avatar_image)
    }

    fun takePhoto(view: View?) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    fun chooseFromGallery(view: View?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY)
    }

    fun verifyAvatar(view: View?) {
        if (avatarBitmap != null) {
            // Implement verification logic here
            Toast.makeText(this, "Avatar verified!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please select an image first.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val extras = data!!.extras
                if (extras != null) {
                    avatarBitmap = extras["data"] as Bitmap?
                    avatarImageView!!.setImageBitmap(avatarBitmap)
                }
            } else if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto(findViewById(android.R.id.content))
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_FROM_GALLERY = 2
    }
}