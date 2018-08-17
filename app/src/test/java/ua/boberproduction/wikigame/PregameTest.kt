package ua.boberproduction.wikigame

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
import org.mockito.Mockito
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.ui.pregame.PregameViewModel


class PregameTest {
    lateinit var viewModel: PregameViewModel
    private val repository = mock<Repository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Mockito.`when`(repository.getPhrases(any())).thenReturn(Single.just("First blabla" to "Target blablabla"))

        viewModel = PregameViewModel(repository, TestSchedulerProvider(), TestPreferenceProvider())
    }

    @Test
    fun onCreateGetPhrases() {
        val observer = mock<Observer<Pair<String, String>>>()
        viewModel.phrases.observeForever(observer)

        viewModel.onCreate()
        argumentCaptor<Pair<String, String>>().apply {
            verify(observer).onChanged(capture())

            assert(firstValue.first.isNotEmpty() && firstValue.second.isNotEmpty())
        }
    }

    @Test
    fun activateStartButtonAfterPhrasesLoad() {

    }

}