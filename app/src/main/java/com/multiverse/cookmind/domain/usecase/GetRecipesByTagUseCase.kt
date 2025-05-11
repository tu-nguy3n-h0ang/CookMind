package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.model.Tag
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetRecipesByTagUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(tag: Tag): Flow<Outcome<List<Recipe>>> {
        return recipeRepository.getRecipes(tags = listOf(tag.name))
    }
}