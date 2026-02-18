package com.ElOuedUniv.maktaba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing book-related UI state
 * This follows the MVVM pattern where ViewModel acts as a bridge between
 * the UI and the business logic (Use Cases)
 */
class BookViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    // Private mutable state for internal use
    private val _books = MutableStateFlow<List<Book>>(emptyList())

    // Public immutable state for UI observation
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // ============ ADDED: Total pages state ============
    private val _totalPages = MutableStateFlow(0)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()
    // ============ END ADDED ============

    init {
        // Load books when ViewModel is created
        loadBooks()
    }

    /**
     * Load all books from the use case
     */
    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val bookList = getBooksUseCase()
                _books.value = bookList
                // ============ ADDED: Calculate total pages when books are loaded ============
                calculateTotalPages()
                // ============ END ADDED ============
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Refresh the books list
     * Can be called from UI to reload data
     */
    fun refreshBooks() {
        loadBooks()
    }

    /**
     * Update search query
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Get filtered books based on search query
     */
    fun getFilteredBooks(): StateFlow<List<Book>> {
        return _books
    }

    // ============ ADDED: Calculate total pages function ============
    /**
     * Calculate the total number of pages of all books combined
     */
    private fun calculateTotalPages() {
        _totalPages.value = _books.value.sumOf { it.nbPages }
    }
    // ============ END ADDED ============
}