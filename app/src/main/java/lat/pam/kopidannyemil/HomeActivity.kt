package lat.pam.kopidannyemil

import android.os.Bundle
import android.content.res.ColorStateList
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.homeContainer, HomeFragment())
                .commit()
        }

        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setItemActiveIndicatorEnabled(true)

        bottomNavigation.setItemActiveIndicatorColor(
            ColorStateList.valueOf(getColor(R.color.brown))
        )

        bottomNavigation.setOnItemSelectedListener { item ->

            if (item.itemId == R.id.navOrder &&
                CartManager.getItems().isEmpty()
            ) {
                android.widget.Toast.makeText(
                    this,
                    "Pilih item terlebih dahulu",
                    android.widget.Toast.LENGTH_SHORT
                ).show()

                false

            } else {

                val fragment = when (item.itemId) {
                    R.id.navHome -> HomeFragment()
                    R.id.navOrder -> OrderFragment()
                    R.id.navProfile -> ProfileFragment()
                    else -> null
                }

                if (fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeContainer, fragment)
                        .commit()

                    true
                } else {
                    false
                }
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.homeContainer)

            val selectedMenu = when (currentFragment) {
                is OrderFragment -> R.id.navOrder
                is ProfileFragment -> R.id.navProfile
                else -> R.id.navHome
            }

            bottomNavigation.menu.findItem(selectedMenu).isChecked = true
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    val currentFragment =
                        supportFragmentManager.findFragmentById(R.id.homeContainer)

                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()

                    } else if (currentFragment !is HomeFragment) {
                        bottomNavigation.selectedItemId = R.id.navHome

                    } else {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.rootHome)
        ) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                view.paddingLeft,
                systemBars.top,
                view.paddingRight,
                systemBars.bottom
            )

            insets
        }
    }
}
