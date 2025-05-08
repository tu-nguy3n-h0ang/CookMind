package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(query: String): Flow<Outcome<List<Recipe>>> {
        if (query.isBlank()) {
            // Có thể trả về danh sách rỗng hoặc lỗi tùy theo logic mong muốn
            return recipeRepository.getRecipes() // Hoặc trả về lỗi/danh sách rỗng
        }
        return recipeRepository.searchRecipes(query)
    }
}