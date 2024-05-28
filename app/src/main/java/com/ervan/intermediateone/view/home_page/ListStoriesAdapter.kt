    package com.ervan.intermediateone.view.home_page

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.paging.PagingDataAdapter
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.RecyclerView
    import coil.load
    import com.ervan.intermediateone.data.responses.ListStoryItem
    import com.ervan.intermediateone.databinding.ItemListStoryBinding
    import com.ervan.intermediateone.view.detail.DetailActivity
    import java.text.ParseException
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale

    class ListStoriesAdapter(private val listener: (ListStoryItem) -> Unit) :
        PagingDataAdapter<ListStoryItem, ListStoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {

        class MyViewHolder(private var binding: ItemListStoryBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data: ListStoryItem) {
                binding.ivItem.load(data.photoUrl)
                binding.tvItemName.text = data.name
                binding.tvItemDesc.text = data.description
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder {
            val binding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = getItem(position)
            if (item != null) {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    listener(item)
                }
            }
        }

        companion object {
             val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
                override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                    return oldItem == newItem
                }
            }

            fun dateToString(dateString: String?): String {
                val inputDateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val date: Date?
                var outputDate = ""

                try {
                    date = inputDateFormat.parse(dateString)
                    outputDate = outputDateFormat.format(date!!)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                return outputDate
            }
        }
    }
