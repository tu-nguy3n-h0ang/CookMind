package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Tag
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAvailableTagsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<Outcome<List<Tag>>> {
        return recipeRepository.getAllAvailableTags()
    }
}