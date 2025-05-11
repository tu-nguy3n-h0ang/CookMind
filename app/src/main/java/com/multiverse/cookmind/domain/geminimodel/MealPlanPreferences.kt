package com.multiverse.cookmind.domain.geminimodel

import com.multiverse.cookmind.domain.model.Cuisine
import com.multiverse.cookmind.domain.model.Ingredient
import com.multiverse.cookmind.domain.model.MealType

/**
 * Sở thích của người dùng để tạo kế hoạch bữa ăn.
 */
data class MealPlanPreferences(
    val durationDays: Int = 7,
    val dietaryRestrictions: List<String> = emptyList(), // Ví dụ: "vegetarian", "gluten-free"
    val cuisinePreferences: List<Cuisine> = emptyList(),
    val calorieTargetPerDay: Int? = null,
    val preferredMealTypes: List<MealType> = emptyList(), // Ví dụ: chỉ muốn "Dinner" và "Lunch"
    val excludedIngredients: List<Ingredient> = emptyList() // Nguyên liệu không muốn có
)
