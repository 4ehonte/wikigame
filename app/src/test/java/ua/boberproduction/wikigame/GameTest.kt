package ua.boberproduction.wikigame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.artfulbits.fletch.util.TestPreferenceProvider
import com.artfulbits.fletch.util.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.ui.game.GameViewModel

class GameTest {
    lateinit var viewModel: GameViewModel
    private val repository = mock<Repository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        //Mockito.`when`(repository.getPhrases(any())).thenReturn(Single.just("First blabla" to "Target blablabla"))

        viewModel = GameViewModel(repository, TestSchedulerProvider(), TestPreferenceProvider())
    }

    @Test
    fun onCreateLoadUrl() {
        val observer = mock<Observer<String>>()
        viewModel.html.observeForever(observer)

        viewModel.onCreate("Blabla" to "Lalala")
        argumentCaptor<String>().apply {
            verify(observer).onChanged(capture())

            assert(firstValue.isNotEmpty())
        }
    }
}