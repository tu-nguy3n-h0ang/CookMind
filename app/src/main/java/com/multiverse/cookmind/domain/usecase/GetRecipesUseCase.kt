package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(
        query: String? = null,
        tags: List<String>? = null,
        mealTypes: List<String>? = null
    ): Flow<Outcome<List<Recipe>>> {
        return recipeRepository.getRecipes(query, tags, mealTypes)
    }
}