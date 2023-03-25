package com.abduldev.youtubeclone.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abduldev.youtubeclone.data.api.models.Item
import com.abduldev.youtubeclone.databinding.ItemVideoBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class PopularVideosAdapter :
    RecyclerView.Adapter<PopularVideosAdapter.PopularVideoViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)


    inner class PopularVideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PopularVideoViewHolder {

        val itemPopularVideoBinding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularVideoViewHolder(itemPopularVideoBinding)

    }


    override fun getItemCount(): Int = differ.currentList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PopularVideoViewHolder, position: Int) {
        val video = differ.currentList[position]

        val thumbnail = video.snippet.thumbnails.medium.url
        val channelLogo = video.snippet.thumbnails.medium.url
        val videoName = video.snippet.title
        val channelTitle = video.snippet.channelTitle
        val videoViews = viewsCount(video.statistics.viewCount.toInt())
        val publishedAt = convert(video.snippet.publishedAt)

        holder.binding.apply {
            videoThumbnail.load(thumbnail)
            channelPicture.load(channelLogo)
            videoTitle.text = videoName
            channelName.text = channelTitle
            views.text = videoViews
            publishedTime.text = publishedAt
        }
    }

    private fun viewsCount(views: Int): String {
        return when {
            views >= 1000000000 -> {
                val formattedViews = views / 1000000
                "${formattedViews}B Views"
            }
            views >= 1000000 -> {
                val formattedViews = views / 10000
                "${formattedViews}M Views"
            }

            views >= 1000 -> {
                val formattedViews = views / 1000
                "${formattedViews}K Views"
            }
            else -> {
                "$views Views"
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convert(publishedDate: String): String {
        val formatPattern = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val publishedAt = LocalDateTime.parse(publishedDate, formatPattern)

        val currentDate = LocalDateTime.now().withNano(0)
        val differenceInSeconds = ChronoUnit.SECONDS.between(
            publishedAt,
            currentDate
        )

        val differenceInDays = ChronoUnit.DAYS.between(
            publishedAt,
            currentDate
        )
        val differenceInMonths = ChronoUnit.MONTHS.between(
            publishedAt,
            currentDate
        )
        return findDifference(differenceInSeconds, differenceInDays, differenceInMonths)
    }

    private fun findDifference(
        differenceInSeconds: Long,
        differenceInDays: Long, differenceInMonths: Long
    ): String {
        val hours = differenceInSeconds / 3600
        when (differenceInDays) {
            in 21..31 -> {
                return "3 weeks Ago"
            }
            in 14..20 -> {
                return "2 weeks ago"
            }
            in 2..13 -> {
                return "$differenceInDays days ago"
            }
            in 0..1 -> {
                return "$hours hours ago"
            }
        }

        if (differenceInMonths in 0..1) {
            return "$differenceInMonths month ago"
        }
        return "$differenceInMonths months ago"
    }
}