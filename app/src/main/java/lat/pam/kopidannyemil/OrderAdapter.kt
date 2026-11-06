package lat.pam.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(
    private val orderItems: List<CartItem>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView =
            itemView.findViewById(R.id.imgOrderProduct)

        val productName: TextView =
            itemView.findViewById(R.id.orderProductName)

        val quantityPrice: TextView =
            itemView.findViewById(R.id.orderProductQuantity)

        val subtotal: TextView =
            itemView.findViewById(R.id.orderProductSubtotal)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)

        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        val item = orderItems[position]
        val product = item.product

        val formatHarga = NumberFormat.getNumberInstance(Locale("id", "ID"))

        holder.imgProduct.setImageResource(product.gambar)
        holder.productName.text = product.nama

        holder.quantityPrice.text =
            "${item.quantity} × Rp${formatHarga.format(product.harga)}"

        holder.subtotal.text =
            "Rp${formatHarga.format(product.harga * item.quantity)}"
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }
}
