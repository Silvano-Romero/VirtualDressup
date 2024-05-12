import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.virtualdressup2.R
import com.example.virtualdressup2.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Switch
import com.example.virtualdressup2.ProfileSelectionActivity

const val THEME_PREFERENCE_KEY = "selected_theme"

class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logout: Button
    private lateinit var mTvEmail: TextView
    private lateinit var darkModeSwitch: Switch
    private lateinit var themeSpinner: Spinner
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var optInNotificationSwitch: Switch
    private lateinit var backToProfileButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        optInNotificationSwitch = view.findViewById(R.id.optInNotificationSwitch)

        firebaseAuth = FirebaseAuth.getInstance()

        mTvEmail = view.findViewById(R.id.emailEt)
        logout = view.findViewById(R.id.signOut)
        themeSpinner = view.findViewById(R.id.themeSpinner)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.theme_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            themeSpinner.adapter = adapter
        }

        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            val email = it.email
            mTvEmail.text = email
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        }

        darkModeSwitch = view.findViewById(R.id.darkModeSwitch)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        darkModeSwitch.isChecked = sharedPreferences.getBoolean("darkModeEnabled", false)

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            sharedPreferences.edit().putBoolean("darkModeEnabled", isChecked).apply()
        }

        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTheme = parent?.getItemAtPosition(position).toString()
                saveSelectedTheme(selectedTheme)
                applyTheme(selectedTheme)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        backToProfileButton = view.findViewById(R.id.backToProfileButton)

        // Add click listener to the button
        backToProfileButton.setOnClickListener {
            // Navigate back to ProfileSelectionActivity
            val intent = Intent(requireContext(), ProfileSelectionActivity::class.java)
            startActivity(intent)
        }

        val selectedTheme = sharedPreferences.getString(THEME_PREFERENCE_KEY, "Default") ?: "Default"
        val themePosition = when (selectedTheme) {
            "Blue" -> 1
            "Green" -> 2
            "Purple" -> 3
            else -> 0 // Default
        }
        themeSpinner.setSelection(themePosition)
        applyTheme(selectedTheme)

        return view
    }

    private fun saveSelectedTheme(selectedTheme: String) {
        sharedPreferences.edit().putString(THEME_PREFERENCE_KEY, selectedTheme).apply()
    }

    private fun applyTheme(selectedTheme: String) {
        val context = requireContext()
        val themeResId = when (selectedTheme) {
            "Default" -> R.style.AppThemeDefault
            "Blue" -> R.style.AppThemeBlue
            "Green" -> R.style.AppThemeGreen
            "Purple" -> R.style.AppThemePurple
            else -> R.style.AppThemeDefault
        }
        context.setTheme(themeResId)
        updateUIWithTheme(selectedTheme)
    }

    private fun updateUIWithTheme(selectedTheme: String) {
        val context = requireContext()
        val primaryColorResId = when (selectedTheme) {
            "Default" -> R.color.default_primary
            "Blue" -> R.color.blue_primary
            "Green" -> R.color.green_primary
            "Purple" -> R.color.purple_primary
            else -> R.color.default_primary
        }
        val primaryColor = ContextCompat.getColor(context, primaryColorResId)

        val secondaryColorResId = when (selectedTheme) {
            "Default" -> R.color.default_secondary
            "Blue" -> R.color.blue_secondary
            "Green" -> R.color.green_secondary
            "Purple" -> R.color.purple_secondary
            else -> R.color.default_secondary
        }
        val secondaryColor = ContextCompat.getColor(context, secondaryColorResId)

        val accentColorResId = when (selectedTheme) {
            "Default" -> R.color.default_accent
            "Blue" -> R.color.blue_accent
            "Green" -> R.color.green_accent
            "Purple" -> R.color.purple_accent
            else -> R.color.default_accent
        }
        val accentColor = ContextCompat.getColor(context, accentColorResId)

        val textColorResId = when (selectedTheme) {
            "Default" -> R.color.default_text_color
            "Blue" -> R.color.blue_text_color
            "Green" -> R.color.green_text_color
            "Purple" -> R.color.purple_text_color
            else -> R.color.default_text_color
        }
        val textColor = ContextCompat.getColor(context, textColorResId)

        val buttonColorResId = when (selectedTheme) {
            "Default" -> R.color.default_button_color
            "Blue" -> R.color.blue_button_color
            "Green" -> R.color.green_button_color
            "Purple" -> R.color.purple_button_color
            else -> R.color.default_button_color
        }
        val buttonColor = ContextCompat.getColor(context, buttonColorResId)

        val appBackgroundColorResId = when (selectedTheme) {
            "Default" -> R.color.default_app_background_color
            "Blue" -> R.color.blue_app_background_color
            "Green" -> R.color.green_app_background_color
            "Purple" -> R.color.purple_app_background_color
            else -> R.color.default_app_background_color
        }
        val appBackgroundColor = ContextCompat.getColor(context, appBackgroundColorResId)

        // Update UI elements with the theme colors
        mTvEmail.setTextColor(textColor)
        logout.setBackgroundColor(buttonColor)
        view?.setBackgroundColor(appBackgroundColor)
        // Update other UI elements' colors as needed
        // For example:
        // logout.setBackgroundColor(buttonColor)
        // view.setBackgroundColor(appBackgroundColor)
    }
}