package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.model.Category
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository

// TODO: Implement this use case
class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {
    // ============ MODIFIED: Bonus 3 - Sort Categories Alphabetically ============
    /**
     * Get all categories sorted alphabetically by name
     * @return List of categories sorted by name
     */
    operator fun invoke(): List<Category> {
        return categoryRepository.getAllCategories().sortedBy { it.name }
    }
    // ============ END MODIFIED ============
}