package com.example.rebuy.Adapters
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rebuy.Model.data.Product
import com.example.rebuy.R

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
        var year: Int = Calendar.getInstance().get(Calendar.YEAR);

        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productYear: TextView = itemView.findViewById(R.id.product_year)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
//        private val productImage: ImageView = itemView.findViewById(R.id.upload_image)


        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = "₹ " + product.price.toString()
            productYear.text = year.toString() + " | "+ product.location
//            productImage.setImageResource(product.image_url)


        }
    }
}

