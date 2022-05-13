package ru.rumigor.cocktailsmaster.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rumigor.cocktailsmaster.R
import ru.rumigor.cocktailsmaster.databinding.ItemDrinkBinding
import ru.rumigor.cocktailsmaster.model.Drink
import ru.rumigor.cocktailsmaster.setStartDrawableCircleImageFromUri

interface OnItemClickListener {
    fun onItemClick(drink: Drink)
}

class MainAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.DrinkViewHolder>() {
    var position = 0

    var drinks: List<Drink> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(drinks[position])
        holder.itemView.setOnLongClickListener {
            this.position = holder.adapterPosition
            false
        }
    }


    override fun getItemCount(): Int = drinks.size

    override fun onViewRecycled(holder: DrinkViewHolder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder)
    }


    inner class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ui: ItemDrinkBinding = ItemDrinkBinding.bind(itemView)

        fun bind(drink: Drink) {
            with(drink) {
                ui.drinkName.text = this.name
                ui.category.text = this.category
                ui.description.text = this.description
                this.imageUrl.let {url ->
                    ui.drinkName.setStartDrawableCircleImageFromUri(url)
                }
                itemView.setOnClickListener { onItemClickListener.onItemClick(this) }
            }
        }

    }
}