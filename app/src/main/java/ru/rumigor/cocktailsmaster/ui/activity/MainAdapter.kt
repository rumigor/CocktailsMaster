package ru.rumigor.cocktailsmaster.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rumigor.cocktailsmaster.R
import ru.rumigor.cocktailsmaster.model.Drink

interface OnItemClickListener {
    fun onItemClick(drink: Drink)
}

class MainAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {
    var position = 0

    var drinks: List<Drink> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(drinks[position])
        holder.itemView.setOnLongClickListener {
            this.position = holder.adapterPosition
            false
        }
    }


    override fun getItemCount(): Int = drinks.size

    override fun onViewRecycled(holder: NoteViewHolder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder)
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ui: ItemNoteBinding = ItemNoteBinding.bind(itemView)

        fun bind(drink: Drink) {
            with(drink) {
                ui.title.text = this.title
                ui.body.text = this.note
                ui.title.setTextColor(this.textColor.getColorInt(itemView.context))
                ui.body.setTextColor(this.textColor.getColorInt(itemView.context))
                ui.container.setCardBackgroundColor(this.color.getColorInt(itemView.context))
                itemView.setOnClickListener { onItemClickListener.onItemClick(this) }
            }
        }

    }

}