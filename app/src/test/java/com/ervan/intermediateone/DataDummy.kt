package com.ervan.intermediateone

import com.ervan.intermediateone.data.responses.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100 ) {
            val story = ListStoryItem(
                photoUrl = "https://example.com/photo$i.jpg",
                createdAt = "2023-04-01T00:00:00Z",
                name = "Name $i",
                description = "Description $i",
                lon = 106.816666,
                id = i.toString(),
                lat = -6.200000
            )
            items.add(story)
        }
        return items
    }
}