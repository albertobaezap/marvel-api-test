package com.example.marvelbrowser

import androidx.recyclerview.widget.DiffUtil
import java.security.MessageDigest

fun String.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray())

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

/**
 * Generic [DiffUtil] class to be used with the models across the application that has an identifier.
 */
class GenericDiff<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemDiff: (T) -> Any
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemDiff(oldList[oldItemPosition]) == itemDiff(newList[newItemPosition])

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}