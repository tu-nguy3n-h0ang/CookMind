package com.multiverse.cookmind.domain.repository

import com.multiverse.cookmind.domain.geminimodel.MealPlan
import com.multiverse.cookmind.domain.geminimodel.MealPlanPreferences
import com.multiverse.cookmind.domain.geminimodel.RecipeSuggestion
import com.multiverse.cookmind.domain.model.Ingredient
import com.multiverse.cookmind.domain.model.Instruction
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.model.ShoppingList
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    fun generateRecipeVariation(
        originalRecipe: Recipe,
        userPrompt: String
    ): Flow<Outcome<RecipeSuggestion>>

    fun suggestIngredientSubstitution(
        originalRecipe: Recipe,
        ingredientToReplace: Ingredient,
        userDietaryNeeds: String? = null
    ): Flow<Outcome<RecipeSuggestion>>

    fun explainCookingTechnique(
        recipeContext: Recipe,
        instruction: Instruction
    ): Flow<Outcome<RecipeSuggestion>>

    fun generateMealPlan(
        preferences: MealPlanPreferences,
        existingRecipes: List<Recipe>? = null
    ): Flow<Outcome<MealPlan>>

    fun advancedRecipeSearch(
        naturalLanguageQuery: String,
        availableRecipes: List<Recipe>
    ): Flow<Outcome<List<Recipe>>>

    fun generateRecipeSummary(
        recipe: Recipe,
        stylePrompt: String? = null
    ): Flow<Outcome<RecipeSuggestion>>

    fun suggestDrinkPairing(
        recipe: Recipe
    ): Flow<Outcome<RecipeSuggestion>>

    // --- CHỨC NĂNG MỚI ---
    /**
     * Phân tích và đề xuất điều chỉnh dinh dưỡng cho một công thức.
     * @param recipe Công thức cần phân tích.
     * @param userGoal Mục tiêu dinh dưỡng của người dùng (ví dụ: "ít calo hơn", "giàu protein").
     * @return RecipeSuggestion chứa phân tích và/hoặc công thức đã điều chỉnh.
     */
    fun analyzeAndSuggestNutritionalAdjustments(
        recipe: Recipe,
        userGoal: String
    ): Flow<Outcome<RecipeSuggestion>>

    /**
     * Tạo ý tưởng công thức mới từ danh sách nguyên liệu người dùng cung cấp.
     * @param ingredients Danh sách nguyên liệu hiện có.
     * @param userPreferences Sở thích bổ sung (ví dụ: "món chay", "cay", "đơn giản").
     * @return RecipeSuggestion chứa tên, nguyên liệu, hướng dẫn cho công thức được đề xuất.
     */
    fun generateRecipeFromIngredients(
        ingredients: List<Ingredient>,
        userPreferences: String? = null
    ): Flow<Outcome<RecipeSuggestion>>

    /**
     * Tạo ra các mẹo nấu ăn hữu ích cho một công thức cụ thể.
     * @param recipe Công thức cần tạo mẹo.
     * @return RecipeSuggestion chứa danh sách các mẹo, có thể trong trường description hoặc specificQuickSuggestions.
     */
    fun generateRecipeTips(
        recipe: Recipe
    ): Flow<Outcome<RecipeSuggestion>>

    /**
     * Tạo danh sách mua sắm tổng hợp từ một hoặc nhiều công thức.
     * @param recipes Danh sách các công thức cần đưa vào danh sách mua sắm.
     * @return ShoppingList đã được tổng hợp và có thể đã phân loại.
     */
    fun generateShoppingList(
        recipes: List<Recipe>
    ): Flow<Outcome<ShoppingList>>

}