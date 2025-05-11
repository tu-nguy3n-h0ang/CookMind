package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Ingredient
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SuggestIngredientSubstitutionUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(
        originalRecipe: Recipe,
        ingredientToReplace: Ingredient,
        userDietaryNeeds: String? = null
    ): Outcome<RecipeSuggestion> {
        if (ingredientToReplace.name.isBlank()) {
            return Outcome.Error("Tên nguyên liệu cần thay thế không được để trống.")
        }

        val existsInRecipe = originalRecipe.ingredients.any { it.name.equals(ingredientToReplace.name, ignoreCase = true) }
        if (!existsInRecipe) {
            return Outcome.Error("Nguyên liệu '${ingredientToReplace.name}' không tìm thấy trong công thức gốc.")
        }

        if (userDietaryNeeds != null && userDietaryNeeds.length > 300) {
            return Outcome.Error("Mô tả nhu cầu ăn kiêng quá dài (tối đa 300 ký tự).")
        }

        val result = geminiRepository.suggestIngredientSubstitution(originalRecipe, ingredientToReplace, userDietaryNeeds).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    Outcome.Success(result.data)
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu gợi ý thay thế.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi gợi ý thay thế nguyên liệu: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang gợi ý thay thế nguyên liệu (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi gợi ý thay thế nguyên liệu.")
        }
    }
}