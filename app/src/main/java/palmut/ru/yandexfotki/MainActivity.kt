package palmut.ru.yandexfotki

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import palmut.ru.yandexfotki.api.CollectionListItem
import palmut.ru.yandexfotki.api.User
import palmut.ru.yandexfotki.api.YandexFotkiPresenter
import palmut.ru.yandexfotki.api.YandexFotkiRepo

class MainActivity : AppCompatActivity(), YandexFotkiPresenter.View {

    private val presenter = YandexFotkiPresenter(YandexFotkiRepo(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        swipeRefresh.setOnRefreshListener(::refresh)
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        presenter.loadUser("alekna")
    }

    override fun onStop() {
        presenter.view = null
        super.onStop()
    }

    override fun showProgress(state: Boolean) {
        swipeRefresh.isRefreshing = state
        if (state) {
            collectionList.removeAllViews()
        }
    }

    override fun showUser(user: User) {
        user.collections.forEach { (_, collection) ->
            val button = Button(this).apply {
                text = collection.title
                setOnClickListener { showCollection(collection) }
            }
            collectionList.addView(button, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        }
    }

    private fun showCollection(collection: CollectionListItem) {
        Snackbar.make(collectionList, "Show collection: ${collection.href}", Snackbar.LENGTH_LONG).show()
    }

    override fun showError(e: Throwable) {
        Snackbar.make(collectionList, "Error: ${e.message}", Snackbar.LENGTH_LONG).show()
    }

}
