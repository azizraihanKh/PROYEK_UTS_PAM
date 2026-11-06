package lat.pam.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgProduct: ImageView =
            itemView.findViewById(R.id.imgCartProduct)

        val productName: TextView =
            itemView.findViewById(R.id.CartProductName)

        val productPrice: TextView =
            itemView.findViewById(R.id.CartProductPrice)

        val quantity: TextView =
            itemView.findViewById(R.id.CartQuantity)

        val btnMinus: MaterialButton =
            itemView.findViewById(R.id.btnMinus)

        val btnPlus: MaterialButton =
            itemView.findViewById(R.id.btnPlus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {

        val item = cartItems[position]
        val product = item.product

        holder.imgProduct.setImageResource(product.gambar)
        holder.productName.text = product.nama

        val formatHarga = NumberFormat.getNumberInstance(
            Locale("id", "ID")
        )

        holder.productPrice.text =
            "Rp${formatHarga.format(product.harga)}"

        holder.quantity.text = item.quantity.toString()

        // Tombol tambah
        holder.btnPlus.setOnClickListener {
            CartManager.updateQuantity(
                product,
                item.quantity + 1
            )
            refreshCart()
        }

        // Tombol kurang
        holder.btnMinus.setOnClickListener {
            CartManager.updateQuantity(
                product,
                item.quantity - 1
            )
            refreshCart()
        }
    }

    private fun refreshCart() {
        cartItems = CartManager.getItems()
        notifyDataSetChanged()
        onCartUpdated()
    }

    fun updateData(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
