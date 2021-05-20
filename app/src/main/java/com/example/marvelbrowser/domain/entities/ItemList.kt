package com.example.marvelbrowser.domain.entities

enum class ItemType(name: String) {
    COMIC("COMICS"), EVENT("EVENTS"), STORY("STORIES"), SERIES("SERIES")
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
