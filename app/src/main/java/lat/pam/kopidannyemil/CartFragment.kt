package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartFragment : Fragment() {

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_cart,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val rvCart = view.findViewById<RecyclerView>(R.id.rvCart)
        val emptyCart = view.findViewById<TextView>(R.id.emptyCart)
        val totalItems = view.findViewById<TextView>(R.id.TotalItems)
        val totalPrice = view.findViewById<TextView>(R.id.TotalPrice)
        val btnBack = view.findViewById<MaterialButton>(R.id.btnBack)
        val btnOrder = view.findViewById<MaterialButton>(R.id.btnOrder)

        rvCart.layoutManager = LinearLayoutManager(requireContext())

        adapter = CartAdapter(CartManager.getItems()) {
            updateCartSummary(
                emptyCart,
                totalItems,
                totalPrice,
                rvCart
            )
        }

        rvCart.adapter = adapter

        updateCartSummary(
            emptyCart,
            totalItems,
            totalPrice,
            rvCart
        )

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnOrder.setOnClickListener {

            if (CartManager.getItems().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Pilih item terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.homeContainer, OrderFragment())
                    .addToBackStack(null)
                    .commit()

                val bottomNavigation = requireActivity()
                    .findViewById<BottomNavigationView>(R.id.bottomNavigation)

                bottomNavigation.menu.findItem(R.id.navOrder).isChecked = true
            }
        }
    }

    private fun updateCartSummary(
        emptyCart: TextView,
        totalItems: TextView,
        totalPrice: TextView,
        rvCart: RecyclerView
    ) {
        val items = CartManager.getItems()

        adapter.updateData(items)

        val isEmpty = items.isEmpty()

        emptyCart.visibility = if (isEmpty) View.VISIBLE else View.GONE
        rvCart.visibility = if (isEmpty) View.GONE else View.VISIBLE

        totalItems.text = "${CartManager.getTotalQuantity()} item"

        val formatHarga = NumberFormat.getNumberInstance(
            Locale("id", "ID")
        )

        totalPrice.text = "Rp${formatHarga.format(CartManager.getTotalPrice())}"
    }
}
