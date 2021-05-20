package com.example.marvelbrowser.domain.entities

//Actual data class for every Marvel character
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
