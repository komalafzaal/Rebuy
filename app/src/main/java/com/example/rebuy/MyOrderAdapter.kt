package com.example.rebuy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyOrderAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_orders_card, parent, false)
        return MyOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.my_order_item_title)
        private val productDate: TextView = itemView.findViewById(R.id.my_order_item_date)
        private val productPrice: TextView = itemView.findViewById(R.id.my_order_item_price)

        // Add other views as needed

        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = product.price.toString()

            // Load product image using Glide/Picasso or other image loading libraries
            // Glide.with(itemView.context).load(product.imageUrl).into(productImage)
            // Example usage of loading image from URL (uncomment above lines and replace imageUrl with your actual image URL)
        }
    }
}

