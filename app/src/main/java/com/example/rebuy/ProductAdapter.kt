package com.example.rebuy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_arrival_card_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productYear: TextView = itemView.findViewById(R.id.product_year)
        private val productCompany: TextView = itemView.findViewById(R.id.product_company)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)

        // Add other views as needed

        fun bind(product: Product) {
            productName.text = product.name
            productYear.text = product.date
            productCompany.text = product.company
            productPrice.text = product.price.toString()

        }
    }
}

