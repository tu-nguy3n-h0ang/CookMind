package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.MealType
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetRecipesByMealTypeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(mealType: MealType): Flow<Outcome<List<Recipe>>> {
        return recipeRepository.getRecipes(mealTypes = listOf(mealType.name))
    }
}