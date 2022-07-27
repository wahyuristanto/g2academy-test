package wahyu.ristanto.takehometest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BeritaResponse(
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("articles")
    val articles: List<ArticleItem>
) : Parcelable

@Parcelize
data class ArticleItem(
    @SerializedName("author")
    val author: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlToImage: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String? = "",
    @SerializedName("content")
    val content: String? = "",
) : Parcelable