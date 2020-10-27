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
    val attributionHTML: String?,
    val data: CharacterDataContainer,
    val etag: String
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)

//Data class for every Marvel character
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: Url,
    val urls: List<UrlObject>,
    val thumbnail: Image,
    val comics: ItemList.ComicItemList,
    val stories: ItemList.StoryItemList,
    val events: ItemList.EventItemList,
    val series: ItemList.SeriesItemList
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
    abstract val collectionURI: Url
    abstract val items: List<BaseItemSummary>

    data class ComicItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionURI: Url,
        override val items: List<ItemSummary>
    ) : ItemList(ItemType.COMIC)

    data class EventItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionURI: Url,
        override val items: List<ItemSummary>
    ) : ItemList(ItemType.EVENT)

    data class SeriesItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionURI: Url,
        override val items: List<ItemSummary>
    ) : ItemList(ItemType.SERIES)

    data class StoryItemList(
        override val available: Int,
        override val returned: Int,
        override val collectionURI: Url,
        override val items: List<StoryItemSummary>
    ) : ItemList(ItemType.STORY)

    interface BaseItemSummary {
        val resourceURI: Url
        val name: String
    }

    data class ItemSummary(
        override val resourceURI: Url,
        override val name: String
    ) : BaseItemSummary

    data class StoryItemSummary(
        override val resourceURI: Url,
        override val name: String,
        val type: String
    ) : BaseItemSummary
}