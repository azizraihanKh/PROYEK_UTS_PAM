package lat.pam.kopidannyemil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    private lateinit var preferences: android.content.SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireContext().getSharedPreferences(
            "UserData",
            android.content.Context.MODE_PRIVATE
        )

        tampilkanDataProfil(view)

        view.findViewById<MaterialButton>(R.id.btnEditProfile)
            .setOnClickListener {
                tampilkanDialogEdit()
            }
    }

    override fun onResume() {
        super.onResume()

        if (::preferences.isInitialized) {
            view?.let { tampilkanDataProfil(it) }
        }
    }

    private fun tampilkanDataProfil(view: View) {
        val fullname = preferences.getString("fullname", "Pengguna") ?: "Pengguna"
        val username = preferences.getString("username", "") ?: ""

        view.findViewById<TextView>(R.id.tvProfileInitial).text =
            fullname.firstOrNull()?.uppercase() ?: "P"

        view.findViewById<TextView>(R.id.tvProfileName).text = fullname
        view.findViewById<TextView>(R.id.tvProfileUsername).text = "@$username"
    }

    private fun tampilkanDialogEdit() {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_edit_profile,
            null
        )

        val etFullname = dialogView.findViewById<EditText>(R.id.etEditFullname)
        val etUsername = dialogView.findViewById<EditText>(R.id.etEditUsername)

        etFullname.setText(preferences.getString("fullname", ""))
        etUsername.setText(preferences.getString("username", ""))

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Profil")
            .setView(dialogView)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan", null)
            .create()

        dialog.window?.setBackgroundDrawableResource(
            R.drawable.bg_dialog_edit
        )

        dialog.setOnShowListener {
            dialog.findViewById<TextView>(
                androidx.appcompat.R.id.alertTitle
            )?.setTextColor(
                requireContext().getColor(R.color.brown)
            )

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(requireContext().getColor(R.color.brown))

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(requireContext().getColor(R.color.brown))

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                val fullname = etFullname.text.toString().trim()
                val username = etUsername.text.toString().trim()

                if (fullname.isEmpty()) {
                    etFullname.error = "Nama lengkap harus diisi"
                } else if (username.isEmpty()) {
                    etUsername.error = "Username harus diisi"
                } else {
                    preferences.edit()
                        .putString("fullname", fullname)
                        .putString("username", username)
                        .apply()

                    view?.let { tampilkanDataProfil(it) }

                    Toast.makeText(
                        requireContext(),
                        "Profil berhasil diperbarui",
                        Toast.LENGTH_SHORT
                    ).show()

                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }
}
