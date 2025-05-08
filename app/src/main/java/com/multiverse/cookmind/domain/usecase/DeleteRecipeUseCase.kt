package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(id: Int): Flow<Outcome<Recipe>> {
        if (id <= 0) {
            return flowOf(Outcome.Error(null, "Recipe ID must be positive."))
        }
        return recipeRepository.deleteRecipe(id)
    }
}