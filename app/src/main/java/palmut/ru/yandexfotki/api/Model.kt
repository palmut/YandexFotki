package palmut.ru.yandexfotki.api

data class CollectionListItem(val href: String, val accept: String, val title: String)

data class User(val title: String, val collections: Map<String, CollectionListItem>)
