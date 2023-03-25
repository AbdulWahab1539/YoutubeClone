package com.abduldev.youtubeclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abduldev.youtubeclone.data.api.models.YoutubeResponse
import com.abduldev.youtubeclone.repo.FetchPopularVideoRepo
import com.abduldev.youtubeclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class PopularVideoViewModel @Inject constructor(
    private val repo: FetchPopularVideoRepo
) : ViewModel() {

    private val _popularVideos:
            MutableLiveData<Resource<YoutubeResponse>> = MutableLiveData()

    var popularVideos: LiveData<Resource<YoutubeResponse>> = _popularVideos

    private fun fetchPopularVideos() = viewModelScope.launch {
        _popularVideos.postValue(Resource.Loading())
        val response = repo.getPopularVideos()
        _popularVideos.postValue(handleYoutubeResponse(response))
    }

    private fun handleYoutubeResponse(response: Response<YoutubeResponse>):
            Resource<YoutubeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { youtubeResponse ->
                return Resource.Success(youtubeResponse)
            }
        }
        return Resource.Error(data = null, response.message())
    }
}