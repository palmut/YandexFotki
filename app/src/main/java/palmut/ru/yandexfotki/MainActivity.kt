package palmut.ru.yandexfotki

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import palmut.ru.yandexfotki.api.*

class MainActivity : AppCompatActivity(), YandexFotkiPresenter.View<User> {

    private val presenter = YandexFotkiUserPresenter(YandexFotkiRepo(), this)
    private val user = "alekna"

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
        presenter.loadUser(user)
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

    override fun showResult(result: User) {
        result.collections.forEach { (_, collection) ->
            val button = Button(this).apply {
                text = collection.title
                setOnClickListener { showCollection(collection) }
            }
            collectionList.addView(button, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        }
    }

    private fun showCollection(collection: CollectionListItem) {
        val name = Uri.parse(collection.href).lastPathSegment
        startActivity(CollectionActivity.startIntent(this, user, name))
    }

    override fun showError(e: Throwable) {
        Snackbar.make(collectionList, "Error: ${e.message}", Snackbar.LENGTH_LONG).show()
    }
}
