package palmut.ru.yandexfotki.api

data class CollectionListItem(val href: String, val accept: String, val title: String)

data class User(val title: String, val collections: Map<String, CollectionListItem>)

data class Image(val width: Int, val href: String, val height: Int)

data class Entry(val edited: String, val updated: String, val xxx: Boolean, val img: Map<String, Image>)

class Collection(val updated: String, val author: String, val title: String, val entries: Array<Entry>)
