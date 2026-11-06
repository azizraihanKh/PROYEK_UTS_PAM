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
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat

class ProductAdapter(
    private var productList: List<Product>,
    private val onAddClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgProduct: ImageView =
            itemView.findViewById(R.id.imgProduct)

        val productName: TextView =
            itemView.findViewById(R.id.productName)

        val price: TextView =
            itemView.findViewById(R.id.price)

        val btnTambah: MaterialButton =
            itemView.findViewById(R.id.btnTambah)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = productList[position]

        holder.imgProduct.setImageResource(product.gambar)
        holder.productName.text = product.nama

        val formatHarga = NumberFormat.getNumberInstance(
            Locale("id", "ID")
        )

        holder.price.text = "Rp${formatHarga.format(product.harga)}"

        // Mengatur tampilan tombol
        updateButtonState(holder, product)

        holder.btnTambah.setOnClickListener {
            if (!CartManager.isInCart(product)) {
                onAddClick(product)
                updateButtonState(holder, product)
            }
        }
    }

    private fun updateButtonState(
        holder: ProductViewHolder,
        product: Product
    ) {
        val context = holder.itemView.context
        val isAdded = CartManager.isInCart(product)

        val brown = ContextCompat.getColor(context, R.color.brown)
        val white = ContextCompat.getColor(context, R.color.white)

        if (isAdded) {
            holder.btnTambah.text = "Ditambahkan"
            holder.btnTambah.backgroundTintList =
                ColorStateList.valueOf(white)
            holder.btnTambah.strokeColor =
                ColorStateList.valueOf(brown)
            holder.btnTambah.strokeWidth = 2
            holder.btnTambah.setTextColor(brown)
        } else {
            holder.btnTambah.text = "+ Tambah"
            holder.btnTambah.backgroundTintList =
                ColorStateList.valueOf(brown)
            holder.btnTambah.strokeWidth = 0
            holder.btnTambah.setTextColor(white)
        }
    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}