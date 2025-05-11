package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AnalyzeAndSuggestNutritionalAdjustmentsUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(recipe: Recipe, userGoal: String): Outcome<RecipeSuggestion> {
        if (userGoal.isBlank()) {
            return Outcome.Error("Mục tiêu dinh dưỡng không được để trống.")
        }
        if (userGoal.length < 5) {
            return Outcome.Error("Mô tả mục tiêu dinh dưỡng quá ngắn.")
        }
        if (userGoal.length > 300) {
            return Outcome.Error("Mô tả mục tiêu dinh dưỡng quá dài (tối đa 300 ký tự).")
        }

        val result = geminiRepository.analyzeAndSuggestNutritionalAdjustments(recipe, userGoal).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu phân tích/điều chỉnh dinh dưỡng.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi phân tích/điều chỉnh dinh dưỡng: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang phân tích/điều chỉnh dinh dưỡng (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi phân tích/điều chỉnh dinh dưỡng.")
        }
    }
}