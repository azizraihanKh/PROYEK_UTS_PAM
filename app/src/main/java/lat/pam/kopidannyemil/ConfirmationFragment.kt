
package lat.pam.kopidannyemil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class ConfirmationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_confirmation,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvConfirmationName =
            view.findViewById<TextView>(R.id.tvConfirmationName)

        val btnConfirmationOk =
            view.findViewById<MaterialButton>(R.id.btnConfirmationOk)

        val preferences = requireContext()
            .getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)

        val fullName = preferences.getString("fullname", "Pengguna")

        tvConfirmationName.text = "Halo, $fullName,"

        val bottomNavigation = requireActivity()
            .findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.visibility = View.GONE

        btnConfirmationOk.setOnClickListener {
            CartManager.clearCart()

            parentFragmentManager.popBackStackImmediate(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            parentFragmentManager.beginTransaction()
                .replace(R.id.homeContainer, HomeFragment())
                .commitNow()

            bottomNavigation.menu.findItem(R.id.navHome).isChecked = true

            bottomNavigation.visibility = View.VISIBLE
        }
    }
}
