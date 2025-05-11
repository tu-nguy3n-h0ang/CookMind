package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Ingredient
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateRecipeFromIngredientsUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(
        ingredients: List<Ingredient>,
        userPreferences: String? = null
    ): Outcome<RecipeSuggestion> {
        if (ingredients.isEmpty()) {
            return Outcome.Error("Danh sách nguyên liệu không được để trống.")
        }
        if (ingredients.any { it.name.isBlank() }) {
            return Outcome.Error("Tên của mỗi nguyên liệu không được để trống.")
        }
        if (ingredients.size < 2 && userPreferences.isNullOrBlank()) {
            return Outcome.Error("Vui lòng cung cấp ít nhất 2 nguyên liệu hoặc mô tả sở thích rõ ràng hơn.")
        }
        if (userPreferences != null && userPreferences.length > 300) {
            return Outcome.Error("Mô tả sở thích quá dài (tối đa 300 ký tự).")
        }

        val result = geminiRepository.generateRecipeFromIngredients(ingredients, userPreferences).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    // Kiểm tra thêm xem RecipeSuggestion có chứa suggestedFullRecipe không nếu đó là mong đợi chính
                    if (result.data.suggestedFullRecipe != null) {
                        Outcome.Success(result.data)
                    } else {
                        // Có thể Gemini chỉ trả về một mô tả/ý tưởng chung mà không phải công thức đầy đủ
                        // Xử lý tùy theo logic mong muốn, ở đây coi như cần công thức đầy đủ
                        Outcome.Error("Gemini không tạo được công thức đầy đủ từ các nguyên liệu được cung cấp.")
                        // Hoặc nếu chấp nhận RecipeSuggestion không có full recipe: Outcome.Success(result.data)
                    }
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu công thức được tạo.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi tạo công thức từ nguyên liệu: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang tạo công thức từ nguyên liệu (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi tạo công thức từ nguyên liệu.")
        }
    }
}