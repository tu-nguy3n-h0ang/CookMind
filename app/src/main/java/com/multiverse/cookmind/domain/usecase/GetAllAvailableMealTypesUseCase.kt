package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.MealType
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAvailableMealTypesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<Outcome<List<MealType>>> {
        return recipeRepository.getAllAvailableMealTypes()
    }
}