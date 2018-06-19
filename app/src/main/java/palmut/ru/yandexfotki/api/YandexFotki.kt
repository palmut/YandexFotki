package palmut.ru.yandexfotki.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface YandexFotkiApi {

    @GET("{user}/")
    @Headers("Accept: application/json")
    fun getUser(@Path("user") user: String): Single<User>
}

class YandexFotkiRepo : YandexFotkiApi {
    val api = Retrofit.Builder()
            .baseUrl("http://api-fotki.yandex.ru/api/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(YandexFotkiApi::class.java)

    override fun getUser(user: String) = api.getUser(user)
}