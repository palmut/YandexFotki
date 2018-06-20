package palmut.ru.yandexfotki.api

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class YandexFotkiUserPresenter(private val repo: YandexFotkiApi, view: View<User>)
    : YandexFotkiPresenter<User>(view) {

    fun loadUser(name: String) = executeRequest { repo.getUser(name) }
}

class YandexFotkiCollectionPresenter(private val repo: YandexFotkiApi, view: View<Collection>)
    : YandexFotkiPresenter<Collection>(view) {

    fun loadCollection(user: String, name: String) = executeRequest { repo.getCollection(user, name) }
}

open class YandexFotkiPresenter<T>(var view: View<T>?) {

    protected fun executeRequest(repoRequest: () -> Single<T>) {
        repoRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<T> {
                    override fun onSuccess(result: T) {
                        view?.run {
                            showProgress(false)
                            showResult(result)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        view?.showProgress(true)
                    }

                    override fun onError(e: Throwable) {
                        view?.run {
                            showProgress(false)
                            showError(e)
                        }
                    }

                })
    }

    interface View<T> {
        fun showProgress(state: Boolean)
        fun showResult(result: T)
        fun showError(e: Throwable)
    }
}
