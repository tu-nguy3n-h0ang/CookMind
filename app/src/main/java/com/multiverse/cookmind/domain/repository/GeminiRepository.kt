package com.multiverse.cookmind.domain.repository

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    fun generateRecipeTips(recipe: Recipe): Flow<Outcome<String>>
    fun suggestDrinkPairing(recipe: Recipe): Flow<Outcome<String>>
}