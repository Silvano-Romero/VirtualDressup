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

const val THEME_PREFERENCE_KEY = "selected_theme"

class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logout: Button
    private lateinit var mTvEmail: TextView
    private lateinit var darkModeSwitch: Switch
    private lateinit var themeSpinner: Spinner
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
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
        val textColorResId = when (selectedTheme) {
            "Default" -> R.color.default_text_color
            "Blue" -> R.color.blue_text_color
            "Green" -> R.color.green_text_color
            "Purple" -> R.color.purple_text_color
            else -> R.color.default_text_color
        }
        val textColor = ContextCompat.getColor(context, textColorResId)
        mTvEmail.setTextColor(textColor)
        // Update other UI elements as needed
    }
}