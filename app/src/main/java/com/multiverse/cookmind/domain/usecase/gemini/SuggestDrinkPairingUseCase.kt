package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SuggestDrinkPairingUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(recipe: Recipe): Outcome<RecipeSuggestion> {
        // recipe được coi là hợp lệ
        val result = geminiRepository.suggestDrinkPairing(recipe).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu gợi ý kết hợp món.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi gợi ý kết hợp món ăn: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang gợi ý kết hợp món ăn (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi gợi ý kết hợp món ăn.")
        }
    }
}