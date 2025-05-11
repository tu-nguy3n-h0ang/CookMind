package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AdvancedSearchWithGeminiUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(naturalLanguageQuery: String): Outcome<List<Recipe>> {
        if (naturalLanguageQuery.isBlank()) {
            return Outcome.Error("Truy vấn tìm kiếm nâng cao không được để trống.")
        }
        if (naturalLanguageQuery.length < 5) {
            return Outcome.Error("Truy vấn tìm kiếm quá ngắn, vui lòng mô tả chi tiết hơn.")
        }
        if (naturalLanguageQuery.length > 500) {
            return Outcome.Error("Truy vấn tìm kiếm quá dài (tối đa 500 ký tự).")
        }

        val recipesOutcome = recipeRepository.getRecipes().firstOrNull() // Lấy tất cả làm ngữ cảnh

        val availableRecipes: List<Recipe>
        when (recipesOutcome) {
            is Outcome.Success -> {
                availableRecipes = recipesOutcome.data ?: emptyList()
                if (availableRecipes.isEmpty()) {
                    // Không có công thức nào trong hệ thống để tìm kiếm
                    return Outcome.Success(emptyList())
                }
            }
            is Outcome.Error -> return Outcome.Error("Không thể lấy danh sách công thức để tìm kiếm: ${recipesOutcome.message}")
            is Outcome.Loading -> return Outcome.Error("Đang tải danh sách công thức (Loading), vui lòng thử lại.")
            null -> return Outcome.Error("Không nhận được phản hồi danh sách công thức.")
        }

        val searchResult = geminiRepository.advancedRecipeSearch(naturalLanguageQuery, availableRecipes).firstOrNull()

        return when (searchResult) {
            is Outcome.Success -> {
                // Dữ liệu trả về là List<Recipe>, có thể là null nếu Gemini không tìm thấy gì
                // hoặc có thể là danh sách rỗng.
                Outcome.Success(searchResult.data ?: emptyList())
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi tìm kiếm nâng cao với Gemini: ${searchResult.message}")
            is Outcome.Loading -> Outcome.Error("Đang thực hiện tìm kiếm nâng cao (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi từ tìm kiếm nâng cao của Gemini.")
        }
    }
}