package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Difficulty
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UpdateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Int, updatedRecipe: Recipe): Outcome<Recipe> {
        if (recipeId <= 0) {
            return Outcome.Error("ID công thức không hợp lệ để cập nhật.")
        }
        if (updatedRecipe.name.isBlank()) {
            return Outcome.Error( "Tên công thức không được để trống khi cập nhật.")
        }
        // ... (thêm các validation khác cho updatedRecipe như trong AddRecipeUseCase nếu cần) ...
        if (updatedRecipe.ingredients.isEmpty()) {
            return Outcome.Error( "Danh sách nguyên liệu không được để trống khi cập nhật.")
        }
        if (updatedRecipe.ingredients.any { it.name.isBlank() }) {
            return Outcome.Error("Tên của mỗi nguyên liệu không được để trống khi cập nhật.")
        }
        if (updatedRecipe.instructions.isEmpty()) {
            return Outcome.Error( "Danh sách hướng dẫn không được để trống khi cập nhật.")
        }
        if (updatedRecipe.instructions.any { it.description.isBlank() }) {
            return Outcome.Error( "Nội dung của mỗi bước hướng dẫn không được để trống khi cập nhật.")
        }
        if (updatedRecipe.prepTimeMinutes <= 0) {
            return Outcome.Error( "Thời gian chế biến không hợp lệ khi cập nhật.")
        }
        if (updatedRecipe.cookTimeMinutes <= 0) {
            return Outcome.Error( "Thời gian nấu ăn không hợp lệ khi cập nhật.")
        }
        if (updatedRecipe.servings <= 0) {
            return Outcome.Error( "Số lượng người ăn không hợp lệ khi cập nhật.")
        }
        if (updatedRecipe.difficulty !in Difficulty.entries.toTypedArray()) {
            return Outcome.Error( "Độ khó không hợp lệ khi cập nhật.")
        }
        if (updatedRecipe.cuisine.isBlank()) {
            return Outcome.Error( "Ẩm thực không được để trống khi cập nhật.")
        }
        if (updatedRecipe.mealTypes.isEmpty()) {
            return Outcome.Error( "Danh sách meal types không được để trống khi cập nhật.")
        }
        if (updatedRecipe.mealTypes.any { it.name.isBlank() }) {
            return Outcome.Error( "Tên của mỗi meal type không được để trống khi cập nhật.")
        }
        // TODO: Update các trường khác nếu cần
        return recipeRepository.updateRecipe(recipeId, updatedRecipe)
    }
}