package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateRecipeSummaryUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(recipe: Recipe, stylePrompt: String? = null): Outcome<RecipeSuggestion> {
        if (stylePrompt != null && stylePrompt.length > 200) {
            return Outcome.Error("Yêu cầu về phong cách tóm tắt quá dài (tối đa 200 ký tự).")
        }

        val result = geminiRepository.generateRecipeSummary(recipe, stylePrompt).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu tóm tắt.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi tạo tóm tắt công thức: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang tạo tóm tắt công thức (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi tạo tóm tắt công thức.")
        }
    }
}