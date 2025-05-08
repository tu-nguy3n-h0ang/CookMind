package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(recipe: Recipe): Flow<Outcome<Recipe>> {
        // Có thể thêm validation cho recipe ở đây trước khi gọi repository
        // Ví dụ: if (recipe.name.isBlank()) return flowOf(Resource.Error("Recipe name cannot be blank"))
        return recipeRepository.addRecipe(recipe)
    }
}