package ua.boberproduction.wikigame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ua.boberproduction.wikigame.util.TestPreferenceRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import ua.boberproduction.wikigame.mvvm.pregame.PregameViewModel
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.util.TestResourceRepository
import ua.boberproduction.wikigame.util.TestSchedulerProvider


class PregameTest {
    lateinit var viewModel: PregameViewModel
    private val repository = mock<DataRepository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Mockito.`when`(repository.getPhrases(any())).thenReturn(Single.just(listOf("Barcelona", "Spain", "Catalonia")))

        viewModel = PregameViewModel(repository, TestSchedulerProvider(), TestPreferenceRepository(), TestResourceRepository())
    }

    @Test
    fun `after creation get phrases`() {
        val observer = mock<Observer<Pair<String, String>>>()
        viewModel.phrases.observeForever(observer)

        viewModel.onCreate()

        argumentCaptor<Pair<String, String>>().apply {
            verify(observer).onChanged(capture())

            assert(firstValue.first.isNotEmpty() && firstValue.second.isNotEmpty())
        }
    }

    @Test
    fun `get two random phrases from phrases list`() {
        val phrasesList = listOf("one", "two", "three", "four", "five")

        val randomPair = viewModel.getRandomPhrases(phrasesList)
        val startPhrase = randomPair.first
        val targetPhrase = randomPair.second

        assert(startPhrase.isNotEmpty() && targetPhrase.isNotEmpty())
        assert(startPhrase != targetPhrase)
        assert(phrasesList.contains(startPhrase))
    }
}