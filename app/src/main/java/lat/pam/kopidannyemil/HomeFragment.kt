package lat.pam.kopidannyemil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val greeting = view.findViewById<TextView>(R.id.greeting)

        val sharedPref = requireActivity().getSharedPreferences(
            "UserData",
            android.content.Context.MODE_PRIVATE
        )

        val fullname = sharedPref.getString("fullname", "Pengguna")
        greeting.text = "Hello, $fullname!"

        val menu = view.findViewById<RecyclerView>(R.id.menu)
        val emptyProduct = view.findViewById<TextView>(R.id.emptyProduct)
        val btnCart = view.findViewById<View>(R.id.btnCart)
        val cartDot = view.findViewById<View>(R.id.cartDot)

        fun updateCartDot() {
            cartDot.visibility = if (CartManager.getTotalQuantity() > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        menu.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = ProductAdapter(ProductData.products) { product ->
            CartManager.addProduct(product)
            updateCartDot()

            Toast.makeText(
                requireContext(),
                "${product.nama} ditambahkan ke keranjang",
                Toast.LENGTH_SHORT
            ).show()
        }
        menu.adapter = adapter

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val allProducts = ProductData.products
        val btnSemua = view.findViewById<MaterialButton>(R.id.btnSemua)
        val btnCoffee = view.findViewById<MaterialButton>(R.id.btnCoffee)
        val btnNonCoffee = view.findViewById<MaterialButton>(R.id.btnNonCoffee)
        val btnPastry = view.findViewById<MaterialButton>(R.id.btnPastry)
        val btnSnack = view.findViewById<MaterialButton>(R.id.btnSnack)
        val btnDessert = view.findViewById<MaterialButton>(R.id.btnDessert)

        var selectedCategory = "Semua"

        fun filterProducts() {
            val keyword = etSearch.text.toString().trim()

            val filteredProducts = allProducts.filter { product ->
                val cocokKategori = selectedCategory == "Semua" ||
                        product.kategori == selectedCategory

                val cocokPencarian = product.nama.contains(
                    keyword,
                    ignoreCase = true
                )

                cocokKategori && cocokPencarian
            }

            adapter.updateData(filteredProducts)

            emptyProduct.visibility = if (filteredProducts.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        fun selectCategory(category: String, selectedButton: MaterialButton) {
            selectedCategory = category

            val buttons = listOf(
                btnSemua, btnCoffee, btnNonCoffee,
                btnPastry, btnSnack, btnDessert
            )

            buttons.forEach { button ->
                button.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                        requireContext().getColor(R.color.white)
                    )
                )
                button.setTextColor(requireContext().getColor(R.color.brown))
            }

            selectedButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                    requireContext().getColor(R.color.brown)
                )
            )
            selectedButton.setTextColor(requireContext().getColor(R.color.white))

            filterProducts()
        }

        btnSemua.setOnClickListener {
            selectCategory("Semua", btnSemua)
        }

        btnCoffee.setOnClickListener {
            selectCategory("Coffee", btnCoffee)
        }

        btnNonCoffee.setOnClickListener {
            selectCategory("Non Coffee", btnNonCoffee)
        }

        btnPastry.setOnClickListener {
            selectCategory("Pastry", btnPastry)
        }

        btnSnack.setOnClickListener {
            selectCategory("Snack", btnSnack)
        }

        btnDessert.setOnClickListener {
            selectCategory("Dessert", btnDessert)
        }

        btnCart.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.homeContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        etSearch.doAfterTextChanged {
            filterProducts()
        }

    }

    override fun onResume() {
        super.onResume()

        view?.findViewById<View>(R.id.cartDot)?.visibility =
            if (CartManager.getTotalQuantity() > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}