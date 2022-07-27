package wahyu.ristanto.takehometest.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import wahyu.ristanto.takehometest.data.model.ArticleItem
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class PopularPagingSource(
    private val api: BaseApi,
    private val query: String? = null
) : PagingSource<Int, ArticleItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            Log.e("_log:Query", query.toString())
            val response = if (query != null) api.searchBerita(
                query,
                position
            ) else api.searchBerita("test", position)
            val article = response.articles
            LoadResult.Page(
                data = article,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (article.isEmpty()) null else position + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}