package com.ElOuedUniv.maktaba.presentation.book.add

data class AddBookUiState(
    val title: String = "",
    val author: String = "",
    val isbn: String = "",
    val nbPages: String = "",
    val isFormValid: Boolean = false,
    val titleErrorMessage: String? = null,
    val isbnErrorMessage: String? = null,
    val pagesErrorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
