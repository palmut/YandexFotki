package palmut.ru.yandexfotki

import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import palmut.ru.yandexfotki.api.YandexFotkiRepo

class ApiTest {
    @Test
    fun testUser() {

        val user = YandexFotkiRepo()
                .getUser("alekna")
                .subscribeOn(Schedulers.single())
                .blockingGet()

        assertEquals("user title does not match", EXPECTED_TITLE, user.title)
    }

    @Test
    fun testCollection() {
        val collection = YandexFotkiRepo()
                .getCollection("alekna", "photos")
                .subscribeOn(Schedulers.single())
                .blockingGet()

        assertEquals("collection title does not match", EXPECTED_TITLE, collection.title)
    }

    companion object {
        private const val EXPECTED_TITLE ="alekna на Яндекс.Фотках"
    }
}
