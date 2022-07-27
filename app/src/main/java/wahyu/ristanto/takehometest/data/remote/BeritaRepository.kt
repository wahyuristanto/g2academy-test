package wahyu.ristanto.takehometest.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeritaRepository @Inject constructor(private val api: BaseApi) {
    fun searchBerita(query: String) = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PopularPagingSource(api, query)
        }
    ).liveData
}