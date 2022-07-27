package wahyu.ristanto.takehometest.data.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import wahyu.ristanto.takehometest.R
import wahyu.ristanto.takehometest.data.model.ArticleItem
import wahyu.ristanto.takehometest.databinding.ItemArticleBinding

class PopularAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ArticleItem, PopularAdapter.PopularViewBinding>(COMPARATOR) {

    interface OnItemClickListener {
        fun onItemClick(article: ArticleItem)
    }

    inner class PopularViewBinding(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(resultsItem: ArticleItem) {
            with(binding) {
                Glide.with(itemView)
                    .load("${resultsItem.urlToImage}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageUrl)
                tvTitle.text = resultsItem.title
                tvDesc.text = resultsItem.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewBinding {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewBinding(binding)
    }

    override fun onBindViewHolder(holder: PopularViewBinding, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean =
                oldItem == newItem

        }
    }

}