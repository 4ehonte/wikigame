package ua.boberproduction.wikigame

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.artfulbits.fletch.util.TestPreferenceProvider
import com.artfulbits.fletch.util.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.repository.Resource
import ua.boberproduction.wikigame.ui.game.GameViewModel

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, manifest = Config.NONE)
class GameTest {
    lateinit var viewModel: GameViewModel
    private val repository = mock<Repository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Mockito.`when`(repository.getArticleHtml(any(), any())).thenReturn(Single.just(Resource.Success("<html> <body> <h1>My First Heading</h1> <p>My first paragraph.</p> </body> </html>")))
        val application = RuntimeEnvironment.application
        viewModel = GameViewModel(repository, TestSchedulerProvider(), TestPreferenceProvider(), application)
    }

    @Test
    fun onCreateLoadsArticle() {
        val observer = mock<Observer<String>>()
        viewModel.url.observeForever(observer)

        viewModel.onCreate("Blabla" to "Lalala")
        argumentCaptor<String>().apply {
            verify(observer).onChanged(capture())

            assert(firstValue.contains("Blabla"))
        }
    }

    @Test
    fun clickingOnLinkUpdatesTitle() {
        val observer = mock<Observer<String>>()
        viewModel.title.observeForever(observer)

        val linkUrl = "https://en.m.wikipedia.org/wiki/Goose"
        viewModel.onLinkClicked(linkUrl)

        argumentCaptor<String>().apply {
            verify(observer).onChanged(capture())

            assert(firstValue == "Goose")
        }
    }

    @Test
    fun pageLoadedStartsTimer() {

    }
}