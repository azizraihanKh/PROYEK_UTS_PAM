package lat.pam.kopidannyemil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class OrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvOrderItems = view.findViewById<RecyclerView>(R.id.rvOrderItems)
        val orderTotalItems = view.findViewById<TextView>(R.id.orderTotalItems)
        val orderTotalPrice = view.findViewById<TextView>(R.id.orderTotalPrice)

        val etFullName = view.findViewById<EditText>(R.id.etFullName)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val etLandmark = view.findViewById<EditText>(R.id.etLandmark)

        val btnOrderSend = view.findViewById<MaterialButton>(R.id.btnOrderSend)

        rvOrderItems.layoutManager = LinearLayoutManager(requireContext())
        rvOrderItems.adapter = OrderAdapter(CartManager.getItems().toList())

        val formatHarga = NumberFormat.getNumberInstance(Locale("id", "ID"))

        orderTotalItems.text = "${CartManager.getTotalQuantity()} item"
        orderTotalPrice.text =
            "Rp${formatHarga.format(CartManager.getTotalPrice())}"

        val preferences = requireContext()
            .getSharedPreferences("UserData", android.content.Context.MODE_PRIVATE)

        etFullName.setText(preferences.getString("fullname", ""))

        btnOrderSend.setOnClickListener {

            val fullName = etFullName.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Keranjang masih kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (fullName.isEmpty()) {
                etFullName.error = "Nama harus diisi"
            } else if (address.isEmpty()) {
                etAddress.error = "Alamat harus diisi"
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.homeContainer, ConfirmationFragment())
                    .commit()
            }
        }
    }
}
