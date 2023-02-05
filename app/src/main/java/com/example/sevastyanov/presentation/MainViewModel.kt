package com.example.sevastyanov.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevastyanov.data.RepositoryImpl
import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmInList
import com.example.sevastyanov.domain.usecases.*
import com.example.sevastyanov.presentation.MainActivity.Companion.SCREEN_MODE_TOP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val getTopListUseCase = GetTopListUseCase(RepositoryImpl)
    private val addFilmToFavouritesUseCase = AddFilmToFavouritesUseCase(RepositoryImpl)
    private val deleteFilmFromFavouritesUseCase = DeleteFilmFromFavouritesUseCase(RepositoryImpl)
    private val getFavouritesListUseCase = GetFavouritesListUseCase(RepositoryImpl)
    private val getFilmDetailsUseCase = GetFilmDetailsUseCase(RepositoryImpl)

    val topListLD = MutableLiveData<List<FilmInList>?>()
    val filmDetailsLD = MutableLiveData<FilmDetails?>()
    val isAddedSuccessfully = MutableLiveData<FilmDetails?>()

    fun getTopList() {
        viewModelScope.launch(Dispatchers.IO) {
            topListLD.postValue(getTopListUseCase.getTopList())
        }
    }

    fun addOrDelete(filmInList: FilmInList, screenMode: Int) {
        viewModelScope.launch(Dispatchers.IO){

            if (filmInList.isFavourite) {
                deleteFilmFromFavouritesUseCase.deleteFilmFromFavourites(filmInList)
                if (screenMode == SCREEN_MODE_TOP) getTopList() else getFavouritesList()
            } else {
                isAddedSuccessfully.postValue(
                    addFilmToFavouritesUseCase.addFilmToFavourites(filmInList)
                )
                if (screenMode == SCREEN_MODE_TOP) getTopList() else getFavouritesList()
            }
        }
    }

    fun getFavouritesList() {
        viewModelScope.launch(Dispatchers.IO){
            topListLD.postValue(getFavouritesListUseCase.getFavouritesList())
        }
    }

    fun getFilmDetails(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            filmDetailsLD.postValue(getFilmDetailsUseCase.getFilmDetails(filmId))
        }
    }


}