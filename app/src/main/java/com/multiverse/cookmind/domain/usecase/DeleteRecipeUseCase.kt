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
    suspend operator fun invoke(id: Int): Outcome<Unit> {
        if (id <= 0) {
            return Outcome.Error("ID công thức không hợp lệ để xóa.")
        }
        return recipeRepository.deleteRecipe(id)
    }
}