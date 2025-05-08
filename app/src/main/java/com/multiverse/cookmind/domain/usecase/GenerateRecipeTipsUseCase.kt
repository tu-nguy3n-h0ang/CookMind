package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerateRecipeTipsUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    operator fun invoke(recipe: Recipe): Flow<Outcome<String>> {
        // Có thể thêm logic kiểm tra recipe trước khi gửi tới Gemini
        return geminiRepository.generateRecipeTips(recipe)
    }
}