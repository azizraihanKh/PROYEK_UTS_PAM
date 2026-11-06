
package lat.pam.kopidannyemil

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

object CartManager {

    private val cartItems = mutableListOf<CartItem>()

    fun isInCart(product: Product): Boolean {
        return cartItems.any {
            it.product.nama == product.nama
        }
    }

    fun addProduct(product: Product) {
        val existingItem = cartItems.find {
            it.product.nama == product.nama
        }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun getItems(): List<CartItem> {
        return cartItems
    }

    fun updateQuantity(product: Product, quantity: Int) {
        val item = cartItems.find {
            it.product.nama == product.nama
        }

        if (item != null) {
            if (quantity <= 0) {
                cartItems.remove(item)
            } else {
                item.quantity = quantity
            }
        }
    }

    fun removeProduct(product: Product) {
        cartItems.removeAll {
            it.product.nama == product.nama
        }
    }

    fun getTotalQuantity(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun getTotalPrice(): Int {
        return cartItems.sumOf {
            it.product.harga * it.quantity
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
