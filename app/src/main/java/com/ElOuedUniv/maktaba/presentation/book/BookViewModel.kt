package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent: SharedFlow<BookUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getBooksUseCase()
                .catch {
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            errorMessage = it.message ?: "Failed to load books"
                        )
                    }
                    _uiEvent.emit(BookUiEvent.ShowSnackbar(it.message ?: "Failed to load books"))
                }
                .collect { bookList ->
                    _uiState.update { current ->
                        current.copy(
                            books = bookList,
                            isLoading = false,
                            totalPages = bookList.sumOf(Book::nbPages)
                        )
                    }
                }
        }
    }

    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> refreshBooks()
            BookUiAction.OnAddBookClick -> {
                _uiState.update { it.copy(isAddingBook = true) }
            }
            BookUiAction.OnDismissAddBook -> {
                _uiState.update { it.copy(isAddingBook = false) }
            }
            is BookUiAction.OnAddBookConfirm -> {
                addBookUseCase(
                    Book(
                        isbn = action.isbn,
                        title = action.title,
                        nbPages = action.nbPages,
                        author = action.author
                    )
                )
                _uiState.update { it.copy(isAddingBook = false) }
                viewModelScope.launch {
                    _uiEvent.emit(BookUiEvent.ShowSnackbar("Book added successfully"))
                }
            }
            is BookUiAction.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }
}
