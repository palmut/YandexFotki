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

        assertEquals("","alekna на Яндекс.Фотках", user.title)
    }
}
