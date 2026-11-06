package lat.pam.kopidannyemil

class Product (
    val nama: String,
    val harga: Int,
    val gambar: Int,
    val kategori: String
)

object ProductData {
    val products = listOf(
        Product(
            "Kopi Susu Gula Aren",
            18000,
            R.drawable.kopi_aren,
            "Coffee"
        ),
        Product(
            "Americano",
            15000,
            R.drawable.americano,
            "Coffee"
        ),
        Product(
            "Matcha Latte",
            18000,
            R.drawable.matcha_latte,
            "Non Coffee"
        ),
        Product(
            "Chocolate Milk",
            16000,
            R.drawable.chocolate_milk,
            "Non Coffee"
        ),
        Product(
            "Croissant Butter",
            15000,
            R.drawable.croisssant_butter,
            "Pastry"
        ),
        Product(
            "Cinnamon Roll",
            17000,
            R.drawable.cinnamon_rolls,
            "Pastry"
        ),
        Product(
            "Kentang Goreng",
            12000,
            R.drawable.kentang_goreng,
            "Snack"
        ),
        Product(
            "Tahu Walik",
            15000,
            R.drawable.tahu_walik,
            "Snack"
        ),
        Product(
            "Brownies Coklat",
            14000,
            R.drawable.bwornies_coklat,
            "Dessert"
        ),
        Product(
            "Puding Cokelat",
            10000,
            R.drawable.puding_coklat,
            "Dessert"
        )
    )
}