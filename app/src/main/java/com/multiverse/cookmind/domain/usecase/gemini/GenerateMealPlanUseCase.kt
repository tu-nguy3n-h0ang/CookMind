package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.geminimodel.MealPlan
import com.multiverse.cookmind.domain.geminimodel.MealPlanPreferences
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GenerateMealPlanUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val recipeRepository: RecipeRepository // Inject RecipeRepository để lấy công thức nếu cần
) {
    suspend operator fun invoke(preferences: MealPlanPreferences): Flow<Outcome<MealPlan>> {
        if (preferences.durationDays <= 0) {
            return flowOf(Outcome.Error("Meal plan duration must be positive."))
        }

        // Lấy danh sách công thức hiện có để Gemini tham khảo (tùy chọn)
        // Điều này có thể cải thiện chất lượng kế hoạch nếu Gemini được yêu cầu sử dụng công thức từ app
        val availableRecipesResource = recipeRepository.getRecipes().firstOrNull()
        val availableRecipes = if (availableRecipesResource is Outcome.Success<*>) {
            availableRecipesResource.data
        } else {
            // Xử lý trường hợp không lấy được công thức, có thể trả về lỗi hoặc để Gemini tự tạo
            // For now, let's proceed without them if fetching fails, Gemini might still work
            emptyList()
        }

        return geminiRepository.generateMealPlan(preferences, availableRecipes)
    }
}