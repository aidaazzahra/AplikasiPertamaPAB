package com.example.aplikasipertama

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipertama.data.StudentRepository
import com.example.aplikasipertama.model.Student
import com.example.aplikasipertama.utils.Resource
import kotlinx.coroutines.launch

class UpdateViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _studentUpdated = MutableLiveData(false)
    val studentUpdated: LiveData<Boolean>
        get() = _studentUpdated

    fun update(student: Student) {
        viewModelScope.launch {
            repository.update(student).collect {
                when (it) {
                    is Resource.Error -> {}
                    Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.d("UpdateViewModel: update", "Success")
                        _studentUpdated.value = true
                    }
                }
            }
        }
    }
}