package com.amakrsmks.sanatangranth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amakrsmks.sanatangranth.entity.Verse
import com.amakrsmks.sanatangranth.repository.VerseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerseViewModel @Inject constructor(
    private val repository: VerseRepository
) : ViewModel() {

    private val _verses = MutableStateFlow<List<Verse>>(emptyList())
    val verses = _verses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var searchJob: Job? = null

    fun loadBook(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _verses.value = repository.getVersesByBook(name)
            _isLoading.value = false
        }
    }

    /**
     * Performs a high-speed search using the FTS5 table created in the script.
     * Uses "debouncing" (delay) to prevent unnecessary DB queries while typing.
     */
    fun search(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            loadBook("श्रीमद भागवत गीता")
            return
        }

        searchJob = viewModelScope.launch {
            delay(300)
            _isLoading.value = true
            _verses.value = repository.search(query)
            _isLoading.value = false
        }
    }
}