package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetRecipesByMealTypeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(mealTypeName: String): Flow<Outcome<List<Recipe>>> {
        if (mealTypeName.isBlank()) {
            return flowOf(Outcome.Error(null, "Meal type name cannot be blank."))
        }
        return recipeRepository.getRecipesByMealType(mealTypeName)
    }
}