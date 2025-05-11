package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateRecipeTipsUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    /**
     * Tạo và trả về các mẹo nấu ăn cho một công thức cụ thể.
     * @param recipe Đối tượng Recipe để tạo mẹo.
     * @return Outcome<RecipeSuggestion> chứa các mẹo.
     */
    suspend operator fun invoke(recipe: Recipe): Outcome<RecipeSuggestion> {
        // Validation cơ bản cho đối tượng Recipe đầu vào
        // Giả sử recipe được lấy từ nguồn đáng tin cậy (ví dụ: database hoặc API đã được validate)
        // nên không cần validate quá sâu các trường của nó ở đây, trừ khi có yêu cầu cụ thể.
        // Tuy nhiên, một số kiểm tra cơ bản vẫn có thể hữu ích.
        if (recipe.name.isBlank()) {
            return Outcome.Error("Tên công thức không được để trống để tạo mẹo.")
        }
        if (recipe.ingredients.isEmpty() || recipe.instructions.isEmpty()) {
            return Outcome.Error("Công thức cần có nguyên liệu và hướng dẫn để tạo mẹo hữu ích.")
        }

        // Gọi hàm repository
        val result = geminiRepository.generateRecipeTips(recipe).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    // Đảm bảo type của suggestion là phù hợp nếu bạn muốn kiểm tra
                    // Ví dụ: if (result.data.type == SuggestionType.COOKING_TIP || result.data.type == SuggestionType.GENERAL_RECIPE_TIPS)
                    if (result.data.description.isNotBlank() || !result.data.specificQuickSuggestions.isNullOrEmpty()) {
                        Outcome.Success(result.data)
                    } else {
                        Outcome.Error("Gemini đã xử lý thành công nhưng không có mẹo nào được tạo.")
                    }
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu mẹo trả về.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi tạo mẹo cho công thức: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang tạo mẹo cho công thức (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi tạo mẹo cho công thức.")
        }
    }
}