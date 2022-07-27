package wahyu.ristanto.takehometest.data.ui.popular

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import wahyu.ristanto.takehometest.data.remote.BeritaRepository

class PopularViewModel @ViewModelInject constructor(
    private val repository: BeritaRepository,
    @Assisted state: SavedStateHandle
) :
    ViewModel() {
    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }
    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)
    private val mvJob = Job()
    private val coroutineScope = CoroutineScope(mvJob+Dispatchers.IO)
    val articles = currentQuery.switchMap { query ->
        repository.searchBerita(query)
    }

    fun searchBerita(query: String) {
        currentQuery.value = query
    }
}