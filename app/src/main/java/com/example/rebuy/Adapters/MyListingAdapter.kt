package com.example.rebuy.Adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rebuy.Model.data.Product
import com.example.rebuy.R

class MyListingAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<MyListingAdapter.MyListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_listing_card, parent, false)
        return MyListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyListingViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.my_listing_item_title)
        private val productDate: TextView = itemView.findViewById(R.id.my_listing_item_date)
        private val productPrice: TextView = itemView.findViewById(R.id.my_listing_item_price)
        private val productViews: TextView = itemView.findViewById(R.id.my_listing_item_views)


        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = product.price.toString()

        }
    }
}

