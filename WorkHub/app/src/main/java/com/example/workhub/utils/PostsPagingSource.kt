package com.example.workhub.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User

data class PostUserPage(
    val post: Post,
    val user: User?,
    val page: Page?
)

class PostsPagingSource(
    private val workHubApi: WorkHubApi,
    private val email: String,
    private val timestamp: Long
): PagingSource<Int, PostUserPage>() {
    override fun getRefreshKey(state: PagingState<Int, PostUserPage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostUserPage> {
        return try {
            val page = params.key ?: 1
            val posts = workHubApi.getPosts(
                email = email,
                timestamp = timestamp,
                page = page
            )

            var postUserPageList: List<PostUserPage> = emptyList()

            for(post in posts) {
                var user: User? = null
                var postPage: Page? = null

                if (post.creator_type == 0) {
                    user = workHubApi.getUserByEmail(post.creator)
                } else {
                    postPage = workHubApi.getPageByName(post.creator)
                }

                val postUserPage = PostUserPage(post = post, user = user, page = postPage)
                postUserPageList = postUserPageList.plus(postUserPage)
            }

            LoadResult.Page(
                data = postUserPageList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (posts.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}