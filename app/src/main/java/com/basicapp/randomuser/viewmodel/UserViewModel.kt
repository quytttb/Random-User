package com.basicapp.randomuser.viewmodel

import androidx.lifecycle.*
import com.basicapp.randomuser.model.User
import com.basicapp.randomuser.repository.UserRepository
import kotlinx.coroutines.*


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList


    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getUsersViewModel() {
/*
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = userRepository.getUsers(10)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        _userList.postValue(response.body())
                        _loading.value = false
                    }
                    else -> {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
*/

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = userRepository.getUsers(10)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        _userList.postValue(response.body())
                        _loading.value = false
                    }
                    else -> {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
    }


    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    /**
     * Factory class để khởi tạo [ViewModel] instance.
     */
    @Suppress("UNCHECKED_CAST")
    class NoteViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                UserViewModel(this.userRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}






