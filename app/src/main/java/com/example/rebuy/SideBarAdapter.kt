import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rebuy.Account
import com.example.rebuy.Home
import com.example.rebuy.Listing
import com.example.rebuy.Orders
import com.example.rebuy.R
import com.example.rebuy.SideBar

class SideBarAdapter(private val sideBarList: List<SideBar>, private val context: Context) :
    RecyclerView.Adapter<SideBarAdapter.SideBarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideBarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.side_bar_card, parent, false)
        return SideBarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SideBarViewHolder, position: Int) {
        val sideBarCard = sideBarList[position]
        holder.bind(sideBarCard)
        holder.itemView.setOnClickListener {
            val selectedSideBar = sideBarList[position]
            navigateToActivity(selectedSideBar.title)
        }
    }

    private fun navigateToActivity(title: String) {
        val intent = when (title) {
            "My Account" -> Intent(context, Account::class.java)
            "My Orders" -> Intent(context, Orders::class.java)
            "My Listings" -> Intent(context, Listing::class.java)
            "Liked Items" -> Intent(context, Home::class.java)
            else -> null
        }
        intent?.let {
            context.startActivity(it)
        }
    }

    override fun getItemCount(): Int {
        return sideBarList.size
    }

    class SideBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.side_bar_card_title)
        private val subtitle: TextView = itemView.findViewById(R.id.side_bar_card_subtitle)
        private val image: ImageView = itemView.findViewById(R.id.side_bar_icon)

        fun bind(sidebar: SideBar) {
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
