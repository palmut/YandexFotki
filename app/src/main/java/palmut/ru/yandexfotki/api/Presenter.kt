package palmut.ru.yandexfotki.api

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class YandexFotkiPresenter(private val repo: YandexFotkiApi, var view: View?) {

    fun loadUser(name: String) {
        repo.getUser(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<User> {
                    override fun onSuccess(user: User) {
                        view?.also {
                            it.showProgress(false)
                            it.showUser(user)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        view?.showProgress(true)
                    }

                    override fun onError(e: Throwable) {
                        view?.also {
                            it.showProgress(false)
                            it.showError(e)
                        }
                    }

                })
    }

    interface View {
        fun showProgress(state: Boolean)
        fun showUser(user: User)
        fun showError(e: Throwable)
    }
}
