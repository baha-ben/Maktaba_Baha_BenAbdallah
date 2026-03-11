package com.ElOuedUniv.maktaba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Category
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categoryList = getCategoriesUseCase()
                _categories.value = categoryList
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshCategories() {
        loadCategories()
    }

    // ============ ADDED: Bonus 2 - Search Category by ID ============
    /**
     * Find a category by its ID
     * @param id The ID of the category to find
     * @return The category if found, null otherwise
     */
    fun getCategoryById(id: String): Category? {
        return _categories.value.find { it.id == id }
    }
    // ============ END ADDED ============
}