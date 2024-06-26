// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.Outfit
import com.myapp.revery.GarmentToDelete
import com.myapp.revery.GarmentToModify
import com.myapp.revery.GarmentToUpload
import com.myapp.revery.ModelToUpload
import com.myapp.firebase.revery.AvatarDAO
import com.myapp.firebase.users.ProfileDAO

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.myapp.revery.ReveryAIClient
import com.myapp.revery.ReveryAIConstants
//fun getGarments(){
//
//        // Fetch models for female and male genders
//        val female = reveryClient.getModels(ReveryAIConstants.FEMALE)
//        val gender = reveryClient.getModels(ReveryAIConstants.MALE)
//
//        //println(AvatarDAO().getSpecificAvatar("Avatar"))
//        // Delete a specific garment and print the ID of the deleted garment
//        val deletedGarmentId = reveryClient.garmentToDelete(
//            GarmentToDelete(
//                garment_id = "7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94"
//            )
//        )
//        println("GARMENT_ID" + deletedGarmentId)
//        reveryClient.getFilteredGarments()
//        // Fetch details of a specific garment using its ID
//        reveryClient.getSpecificGarment("7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94")
//
//        // Upload a new garment and print its ID
//        val garmentID = reveryClient.uploadGarment(
//            GarmentToUpload(
//                category = ReveryAIConstants.TOPS,
//                gender = ReveryAIConstants.MALE,
//                garment_img_url = "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg",
//            )
//        )
//        println("GARMENT_ID_UPLOADED " + garmentID)
//        reveryClient.getSpecificGarment(garmentID)

//        // Creates map of Garments to pass for Avatar TryOnResponse
//        val garments = mapOf(
//            "tops" to "7047a79ff16b3393b5b2ff4d35ac8b8e_z52g6siOWP6A",
//            "bottoms" to "7047a79ff16b3393b5b2ff4d35ac8b8e_VbcHFMmOfzey"
//        )
//         // Makes API requestTryOn with provided parameters
//        val tryOnResponse = reveryClient.requestTryOn(garments, "d79b5e0a1b2fd3817da7c3a26005b4b0", null, "white", false)
//
//        // Provides available list of male and female Avatars and success response
//        println("male111: " + gender)
//        println("female: " + female)
//        println("Try-On Response: $tryOnResponse")
//        println("TEST... " + tryOnResponse)
//

//        reveryClient.getModels(ReveryAIConstants.FEMALE)
//        reveryClient.getModels(ReveryAIConstants.MALE)

//        val uploadModelID = reveryClient.uploadModel(
//            ModelToUpload(
//                gender = ReveryAIConstants.FEMALE,
//                model_img_url = "https://humanaigc-outfitanyone.hf.space/--replicas/ppht9/file=/tmp/gradio/28dbd2deba1e160bfadffbc3675ba4dcac20ca58/Eva_0.png",
//                standardized = false
//            )
//        )
//        println("MODEL_ID_UPLOADED" + uploadModelID)
//        reveryClient.getModels(ReveryAIConstants.FEMALE)

        //println(AvatarDAO().getSpecificAvatar("Avatar"))
//        val deletedGarmentId = reveryClient.garmentToDelete(
//            GarmentToDelete(
//                garment_id = "7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94"
//            )
//        )
//        println("GARMENT_ID" + deletedGarmentId)
//        reveryClient.getFilteredGarments()
//        reveryClient.getSpecificGarment("7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94")
//        val garmentID = reveryClient.uploadGarment(
//            GarmentToUpload(
//                category = ReveryAIConstants.TOPS,
//                gender = ReveryAIConstants.MALE,
//                garment_img_url = "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg",
//            )
//        )
//        println("GARMENT_ID_UPLOADED " + garmentID)
//        reveryClient.getSpecificGarment(garmentID)
//
//        val garments = mapOf(
//            "tops" to "7047a79ff16b3393b5b2ff4d35ac8b8e_z52g6siOWP6A",
//            "bottoms" to "7047a79ff16b3393b5b2ff4d35ac8b8e_VbcHFMmOfzey"
//        )
//        val tryOnResponse = reveryClient.requestTryOn(garments, "d79b5e0a1b2fd3817da7c3a26005b4b0", null, "white", false)
//
//        println("male111: " + gender)
//        println("female: " + female)
//        println("Try-On Response: $tryOnResponse")
//        println("TEST... " + tryOnResponse)
//
//        reveryClient.getModels(ReveryAIConstants.FEMALE)
//        reveryClient.getModels(ReveryAIConstants.MALE)
//        ProfileDAO().writeProfileToProfilesCollection(123, mapOf(
//            "firstName" to "Silvano",
//        ))
//        var ids = AvatarDAO().getAllProfileIDs()
//        for(id in ids){
//            println(id)
//            println(AvatarDAO().getAvatarsFromProfile(id))
//            AvatarDAO().addAvatarToProfile(id, Avatar(
//                "NewAvatar",
//                "MODEL123",
//                listOf(Outfit("OutfitNew123", "TOPGARMENT", "BOTTOMGARMENT"))
//            ))
//        }
//        AvatarDAO().addOutfitToAvatar("Gabe", "Avatar", Outfit(
//            "OUTFIT01",
//            "TOPGARMENT01",
//            "BottomGarment01",
//        ))
        //println(AvatarDAO().addAvatarToProfile(123))
//        val deletedGarmentId = reveryClient.garmentToDelete(
//            GarmentToDelete(
//                garment_id = "7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94"
//            )
//        )
//        println("GARMENT_ID" + deletedGarmentId)
//        reveryClient.getFilteredGarments()
//        reveryClient.getSpecificGarment("7047a79ff16b3393b5b2ff4d35ac8b8e_l8Ix91bkol94")
//        val garmentID = reveryClient.uploadGarment(
//            GarmentToUpload(
//                category = ReveryAIConstants.TOPS,
//                gender = ReveryAIConstants.MALE,
//                garment_img_url = "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg",
//            )
//        )
//        println("GARMENT_ID_UPLOADED" + garmentID)
//        reveryClient.getSpecificGarment(garmentID)
// 78b5256d8f61705a2b07cd6092302ef1953ba621
//    }
//}

// Declaration of the SignInActivity class which extends AppCompatActivity
class SignInActivity : AppCompatActivity() {

    // Declaring variables for view binding and Firebase Authentication
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Overriding the onCreate method to initialize the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get filtered garments at once SignIn page is rendered. This can be removed only here
        // for test purposes and demo.
        //getGarments()

        // Inflating the layout using view binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Setting click listener for the sign-up button
        binding.signUpButton.setOnClickListener {
            // Creating an Intent to navigate to SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Setting click listener for the login button
        binding.loginButton.setOnClickListener {
            // Retrieving email and password from input fields
            //val email = binding.usernameInput.text.toString()
            //val pass = binding.passwordInput.text.toString()
            val email = "sync@gmail.com"
            val pass = "password"

            // Checking if email and password are not empty
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Attempting to sign in with the provided credentials
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            // Sign-in successful, navigate to MainActivity
                            Toast.makeText(this, "Successful Sign In!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ProfileSelectionActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Sign-in failed, display error message
                            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                // Displaying a toast message for empty fields
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Overriding the onStart method
    // This method checks if the user is already signed in and navigates to MainActivity if so
//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, AvatarCreationActivity::class.java)
//            startActivity(intent)
//        }
//    }
}
