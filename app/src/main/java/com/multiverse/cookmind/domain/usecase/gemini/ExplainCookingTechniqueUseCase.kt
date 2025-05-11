package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Instruction
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ExplainCookingTechniqueUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(
        recipeContext: Recipe, // Cung cấp ngữ cảnh của công thức
        instruction: Instruction // Bước hướng dẫn cụ thể cần giải thích
    ): Outcome<RecipeSuggestion> {
        if (instruction.description.isBlank()) {
            return Outcome.Error("Nội dung hướng dẫn cần giải thích không được để trống.")
        }
        if (instruction.description.length > 1000) { // Giới hạn độ dài của một bước hướng dẫn
            return Outcome.Error("Nội dung hướng dẫn cần giải thích quá dài.")
        }

        val result = geminiRepository.explainCookingTechnique(recipeContext, instruction).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có giải thích kỹ thuật nấu ăn.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi giải thích kỹ thuật nấu ăn: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang giải thích kỹ thuật nấu ăn (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi giải thích kỹ thuật nấu ăn.")
        }
    }
}