package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Use case này có thể đơn giản là gọi getRecipes với query,
// hoặc phức tạp hơn nếu cần xử lý logic tìm kiếm đặc biệt trước khi gọi repo.
class SearchRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(query: String): Flow<Outcome<List<Recipe>>> {
        if (query.isBlank()) {
            return recipeRepository.getRecipes() // Trả về tất cả nếu query rỗng
        }
        return recipeRepository.getRecipes(query = query)
    }
}