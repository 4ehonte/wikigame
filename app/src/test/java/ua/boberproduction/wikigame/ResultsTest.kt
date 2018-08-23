package ua.boberproduction.wikigame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.artfulbits.fletch.util.TestPreferenceProvider
import com.artfulbits.fletch.util.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.ui.results.ResultsViewModel

class ResultsTest {
    lateinit var viewModel: ResultsViewModel
    private val repository = mock<Repository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        viewModel = ResultsViewModel(repository, TestSchedulerProvider(), TestPreferenceProvider())
    }

    @Test
    fun `upon creation, save results to database`() {
        val result = mock<Result>()
        viewModel.onCreate(result)

        verify(repository).saveResult(result)
    }

}