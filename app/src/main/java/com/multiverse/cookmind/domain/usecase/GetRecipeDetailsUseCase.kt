package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(id: Int): Flow<Outcome<Recipe>> {
        if (id <= 0) { // Ví dụ validation đơn giản
            return flowOf(Outcome.Error(null, "Invalid recipe ID."))
        }
        return recipeRepository.getRecipeDetails(id)
    }
}