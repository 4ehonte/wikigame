package ua.boberproduction.wikigame.mvvm.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.util.getReadableTime

class HistoryAdapter(private val results: List<Result>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Result) {
            with(itemView) {
                start_phrase_tv.text = item.startPhrase
                target_phrase_tv.text = item.targetPhrase
                clicks_tv.text = item.clicks.toString()
                time_tv.text = getReadableTime(item.seconds)
            }
        }
    }
}