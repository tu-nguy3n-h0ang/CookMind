package com.multiverse.cookmind.domain.usecase

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.repository.RecipeRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe): Outcome<Recipe> {
        if (recipe.name.isBlank()) {
            return Outcome.Error("Tên công thức không được để trống.")
        }
        if (recipe.ingredients.isEmpty()) {
            return Outcome.Error( "Danh sách nguyên liệu không được để trống.")
        }
        if (recipe.ingredients.any { it.name.isBlank() }) {
            return Outcome.Error("Tên của mỗi nguyên liệu không được để trống.")
        }
        if (recipe.instructions.isEmpty()) {
            return Outcome.Error( "Danh sách hướng dẫn không được để trống.")
        }
        if (recipe.instructions.any { it.description.isBlank() }) {
            return Outcome.Error( "Nội dung của mỗi bước hướng dẫn không được để trống.")
        }
        if (recipe.prepTimeMinutes < 0) {
            return Outcome.Error( "Thời gian chuẩn bị không hợp lệ.")
        }
        if (recipe.cookTimeMinutes < 0) {
            return Outcome.Error( "Thời gian nấu không hợp lệ.")
        }
        if (recipe.servings <= 0) {
            return Outcome.Error( "Số lượng khẩu phần phải lớn hơn 0.")
        }
        recipe.caloriesPerServing?.let {
            if (it < 0) {
                return Outcome.Error( "Lượng calo mỗi khẩu phần không hợp lệ.")
            }
        }
        return recipeRepository.addRecipe(recipe)
    }
}