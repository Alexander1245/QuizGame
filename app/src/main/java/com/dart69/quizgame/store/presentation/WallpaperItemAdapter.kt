package com.dart69.quizgame.store.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dart69.quizgame.R
import com.dart69.quizgame.databinding.WallpaperItemBinding
import com.dart69.quizgame.store.presentation.models.WallpaperItem

class WallpaperItemDiffCallback : DiffUtil.ItemCallback<WallpaperItem>() {
    override fun areItemsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean =
        oldItem == newItem
}

class WallpaperItemAdapter(
    callback: DiffUtil.ItemCallback<WallpaperItem> = WallpaperItemDiffCallback(),
    private val callbacks: Callbacks,
) : ListAdapter<WallpaperItem, WallpaperItemViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperItemViewHolder =
        WallpaperItemViewHolder(
            binding = WallpaperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            callbacks = callbacks,
        )

    override fun onBindViewHolder(holder: WallpaperItemViewHolder, position: Int) =
        holder.bind(currentList[position])

    interface Callbacks {
        fun onSubmitPurchase(wallpaper: WallpaperItem)

        fun onSubmitSetAsWallpaper(wallpaper: WallpaperItem)
    }
}

class WallpaperItemViewHolder(
    private val binding: WallpaperItemBinding,
    private val callbacks: WallpaperItemAdapter.Callbacks,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(wallpaper: WallpaperItem) {
        binding.imageViewWallpaper.setImageURI(wallpaper.uri.toUri())
        binding.imageIsBought.isVisible = wallpaper.isBought
        binding.buttonBuy.isEnabled = wallpaper.isAvailable
        binding.buttonBuy.text = wallpaper.price.toString()
        binding.buttonBuy.setOnClickListener {
            it.context.showAlertDialog(
                title = R.string.are_you_sure,
                message = R.string.points_will_be_spent,
                positiveButton = R.string.buy,
                onPositiveClick = { callbacks.onSubmitPurchase(wallpaper) },
            )
        }
        binding.imageViewWallpaper.setOnClickListener {
            if (wallpaper.isBought) {
                it.context.showAlertDialog(
                    title = R.string.are_you_sure,
                    message = R.string.are_you_want_to_set_wallpaper,
                    positiveButton = R.string.yes,
                    onPositiveClick = { callbacks.onSubmitSetAsWallpaper(wallpaper) }
                )
            }
        }
    }
}

fun Context.showAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveButton: Int,
    @StringRes negativeButton: Int = R.string.no_thanks,
    onPositiveClick: () -> Unit,
): AlertDialog = AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(message)
    .setPositiveButton(positiveButton) { _, _ -> onPositiveClick() }
    .setNegativeButton(negativeButton) { dialog, _ -> dialog.cancel() }
    .show()