package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title, isSuccess = false) }
                validateInputs()
            }
            is AddBookUiAction.OnAuthorChange -> {
                _uiState.update { it.copy(author = action.author, isSuccess = false) }
            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update {
                    it.copy(
                        isbn = action.isbn.filter(Char::isDigit).take(13),
                        isSuccess = false
                    )
                }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update {
                    it.copy(
                        nbPages = action.pages.filter(Char::isDigit),
                        isSuccess = false
                    )
                }
                validateInputs()
            }
            is AddBookUiAction.OnImageSelected -> {
                _uiState.update { it.copy(imageUri = action.uri) }
            }
            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }
            AddBookUiAction.OnNavigationHandled -> {
                _uiState.update { it.copy(isSuccess = false) }
            }
        }
    }

    private fun validateInputs() {
        val currentState = _uiState.value
        val titleError = if (currentState.title.trim().isEmpty()) "Title cannot be empty." else null
        val isbnError = when {
            currentState.isbn.isEmpty() -> "ISBN must be exactly 13 digits."
            currentState.isbn.length != 13 -> "ISBN must be exactly 13 digits."
            else -> null
        }
        val pagesValue = currentState.nbPages.toIntOrNull()
        val pagesError = when {
            currentState.nbPages.isEmpty() -> "Pages must be a positive number."
            pagesValue == null || pagesValue <= 0 -> "Pages must be a positive number."
            else -> null
        }

        _uiState.update {
            it.copy(
                titleErrorMessage = titleError,
                isbnErrorMessage = isbnError,
                pagesErrorMessage = pagesError,
                isFormValid = titleError == null && isbnError == null && pagesError == null
            )
        }
    }

    private fun addBook() {
        validateInputs()
        val currentState = _uiState.value
        if (!currentState.isFormValid) return

        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            author = currentState.author,
            imageUrl = currentState.imageUri
        )
        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }
}