package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateRecipeVariationUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(originalRecipe: Recipe, userPrompt: String): Outcome<RecipeSuggestion> {
        if (userPrompt.isBlank()) {
            return Outcome.Error( "Yêu cầu tạo biến thể không được để trống.")
        }
        if (userPrompt.length > 500) { // Ví dụ: Giới hạn độ dài của prompt
            return Outcome.Error( "Yêu cầu tạo biến thể quá dài (tối đa 500 ký tự).")
        }

        // originalRecipe được coi là hợp lệ nếu nó được lấy từ hệ thống
        // và đã qua các bước validation trước đó.

        val result: Outcome<RecipeSuggestion>? = geminiRepository.generateRecipeVariation(originalRecipe, userPrompt).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error( "Gemini đã xử lý thành công nhưng không có dữ liệu biến thể trả về.")
                }
            }
            is Outcome.Error -> Outcome.Error( "Lỗi khi tạo biến thể công thức: ${result.message}")
            is Outcome.Loading -> Outcome.Error( "Đang tạo biến thể công thức (Loading), vui lòng thử lại.") // firstOrNull có thể không nên gặp Loading nếu Flow giải quyết nhanh
            null -> Outcome.Error( "Không nhận được phản hồi khi tạo biến thể công thức.")
        }
    }
}