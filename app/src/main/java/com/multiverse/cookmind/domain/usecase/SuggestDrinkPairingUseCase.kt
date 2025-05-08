package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuggestDrinkPairingUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    operator fun invoke(recipe: Recipe): Flow<Outcome<String>> {
        return geminiRepository.suggestDrinkPairing(recipe)
    }
}