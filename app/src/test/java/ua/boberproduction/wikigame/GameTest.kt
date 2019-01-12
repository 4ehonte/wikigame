package ua.boberproduction.wikigame

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ua.boberproduction.wikigame.util.TestPreferenceRepository
import ua.boberproduction.wikigame.util.TestSchedulerProvider
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
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.annotation.Config
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.Resource
import ua.boberproduction.wikigame.mvvm.game.GameViewModel
import ua.boberproduction.wikigame.util.TestResourceRepository

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, manifest = Config.NONE)
class GameTest {
    lateinit var viewModel: GameViewModel
    private val repository = mock<DataRepository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Mockito.`when`(repository.getArticleHtml(any(), any())).thenReturn(Single.just(Resource.Success("<html> <body> <h1>My First Heading</h1> <p>My first paragraph.</p> </body> </html>")))
        val application = RuntimeEnvironment.application
        viewModel = GameViewModel(repository, TestSchedulerProvider(), TestResourceRepository(), TestPreferenceRepository(), application)
    }

    @Test
    fun `after creation, the first article starts to load`() {
        val observer = mock<Observer<String>>()
        viewModel.loadUrl.observeForever(observer)

        viewModel.onCreate("Blabla" to "Lalala")
        argumentCaptor<String>().apply {
            verify(observer).onChanged(capture())
            assert(firstValue.contains("Blabla"))
        }
    }

    @Test
    fun `after the first article is loaded, timer starts`() {
        viewModel = GameViewModel(repository, TestSchedulerProvider(), TestResourceRepository(), TestPreferenceRepository(), application)
        assert(viewModel.timer == null || viewModel.timer!!.time == 0L)

        viewModel.pageLoaded("wikiurl", "wiki title")
        Thread.sleep(1100)
        assert(viewModel.timer != null && viewModel.timer!!.time > 0L)
    }

    @Test
    fun `if timer if already running, don't change it after page loaded`() {
        viewModel.initTimer()
        val timer1 = viewModel.timer
        val time1 = viewModel.timer?.time

        viewModel.pageLoaded("wikiurl", "wiki title")
        val timer2 = viewModel.timer
        val time2 = viewModel.timer?.time

        assert(timer1 == timer2)
        assert(time1!! >= time2!!)
    }

    @Test
    fun `if user clicks on the target link, the game is finished`() {
        val viewModelSpy = Mockito.spy(viewModel)
        val targetArticle = "target shmarget"
        viewModelSpy.onCreate("Blabla" to targetArticle)

        viewModelSpy.onLinkClicked("https://en.wikipedia.org/wiki/target shmarget")
        verify(viewModelSpy).finishGame()
    }


}