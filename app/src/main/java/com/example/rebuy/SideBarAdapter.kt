package com.example.rebuy
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class SideBarAdapter(private val sideBarList: List<SideBar>) :
    RecyclerView.Adapter<SideBarAdapter.SideBarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideBarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.side_bar_card, parent, false)
        return SideBarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SideBarViewHolder, position: Int) {
        val sideBarCard = sideBarList[position]
        holder.bind(sideBarCard)
    }

    override fun getItemCount(): Int {
        return sideBarList.size
    }

    class SideBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.side_bar_card_title)
        private val subtitle: TextView = itemView.findViewById(R.id.side_bar_card_subtitle)
        private val image: ImageView = itemView.findViewById(R.id.side_bar_icon)

        fun bind(sidebar: SideBar) {
//            icon_url.text = sidebar.image
            title.text = sidebar.title
            subtitle.text = sidebar.subtitle


            when (sidebar.title) {
                "My Account" -> image.setImageResource(R.drawable.my_account_icon)
                "My Orders" -> image.setImageResource(R.drawable.my_order_icon)
                "My Listings" -> image.setImageResource(R.drawable.my_listing_icon)
                "Liked Items" -> image.setImageResource(R.drawable.liked_items_icon)
            }



        }
    }
}

