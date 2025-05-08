package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UpdateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(id: Int, recipe: Recipe): Flow<Outcome<Recipe>> {
        // Add validation
        if (id <= 0) {
            return flowOf(Outcome.Error(null, "Recipe ID must be positive."))
        }
        // if (recipe.name.isBlank()) { ... }
        return recipeRepository.updateRecipe(id, recipe)
    }
}