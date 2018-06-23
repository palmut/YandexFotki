package palmut.ru.yandexfotki

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_collection.swipeRefresh
import kotlinx.android.synthetic.main.activity_collection.toolbar
import kotlinx.android.synthetic.main.activity_collection.recyclerView
import kotlinx.android.synthetic.main.activity_collection.collapsingToolbar
import palmut.ru.yandexfotki.api.Collection
import palmut.ru.yandexfotki.api.YandexFotkiCollectionPresenter
import palmut.ru.yandexfotki.api.YandexFotkiPresenter
import palmut.ru.yandexfotki.api.YandexFotkiRepo

class CollectionActivity : AppCompatActivity(), YandexFotkiPresenter.View<Collection> {

    private val presenter = YandexFotkiCollectionPresenter(YandexFotkiRepo(), this)
    private val user get() = intent.getStringExtra(EXTRA_USER)
    private val name get() = intent.getStringExtra(EXTRA_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        recyclerView.apply {
            addItemDecoration(ScaleDecoration())
            adapter = ImageAdapter(resources.displayMetrics.widthPixels)
        }

        swipeRefresh.setOnRefreshListener(::refresh)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    override fun showProgress(state: Boolean) {
        swipeRefresh.isRefreshing = state
    }

    override fun showResult(result: Collection) {
        collapsingToolbar.title = result.title
        (recyclerView.adapter as ImageAdapter).submitList(result.entries.toList())
    }

    override fun showError(e: Throwable) {
        Snackbar.make(recyclerView, "Error: ${e.message}", Snackbar.LENGTH_LONG).show()
    }

    private fun refresh() {
        presenter.loadCollection(user, name)
    }

    companion object {
        private const val EXTRA_USER = "extra_user"
        private const val EXTRA_NAME = "extra_name"

        fun startIntent(context: Context, user: String, name: String): Intent {
            return Intent(context, CollectionActivity::class.java)
                    .putExtra(EXTRA_USER, user)
                    .putExtra(EXTRA_NAME, name)
        }
    }
}
