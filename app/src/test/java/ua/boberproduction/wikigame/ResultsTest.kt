package ua.boberproduction.wikigame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.artfulbits.fletch.util.TestPreferenceRepository
import ua.boberproduction.wikigame.util.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.mvvm.postgame.ResultsViewModel

class ResultsTest {
    lateinit var viewModel: ResultsViewModel
    private val repository = mock<DataRepository>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        viewModel = ResultsViewModel(repository, TestSchedulerProvider(), TestPreferenceRepository())
    }

    @Test
    fun `upon creation, save results to database`() {
        val result = mock<Result>()
        viewModel.onCreate(result)

        verify(repository).saveResult(result)
    }

}