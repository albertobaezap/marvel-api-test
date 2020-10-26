package com.example.marvelbrowser.model

import java.util.*

typealias Url = String

enum class UrlType(name: String) {
    DETAIL("detail"),
    WIKI("wiki"),
    COMICLINK("comiclink")
}

data class CharacterDataWrapper(
    val code: Int?,
    val limit: Int?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHtml: String?,
    val data: CharacterDataContainer,
    val etag: String
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val result: List<Character>
)

//Data class for every Marvel character
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val resourceUri: Url,
    val urls: List<UrlObject>,
    val thumbnail: Image,
    val comics: List<ItemList.ComicItemList>,
    val stories: List<ItemList.StoryItemList>,
    val events: List<ItemList.EventItemList>,
    val series: List<ItemList.SeriesItemList>
)

data class UrlObject(
    val type: UrlType,
    val url: Url
)

data class Image(
    val path: String,
    val extension: String
)

enum class ItemType {
    COMIC, EVENT, STORY, SERIES
}

//Too much class duplication but will leave it like this for the sake of extension.
sealed class ItemList(open val itemType: ItemType) {
    abstract val available: Int
    abstract val returned: Int
    abstract val collectionUri: Url
    abstract val items: List<BaseItemSummary>

    data class ComicItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionUri: Url,
        override val items: List<BaseItemSummary>
    ): ItemList(ItemType.COMIC)

    data class EventItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionUri: Url,
        override val items: List<BaseItemSummary>
    ): ItemList(ItemType.EVENT)

    data class SeriesItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionUri: Url,
        override val items: List<BaseItemSummary>
    ): ItemList(ItemType.SERIES)

    data class StoryItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionUri: Url,
        override val items: List<StoryItemSummary>
    ): ItemList(ItemType.STORY)

    interface BaseItemSummary {
        val resourceUri: Url
        val name: String
    }

    data class StoryItemSummary(
        override val resourceUri: Url,
        override val name: String,
        val type: String
    ): BaseItemSummary
}