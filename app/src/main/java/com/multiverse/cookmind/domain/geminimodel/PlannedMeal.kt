package com.multiverse.cookmind.domain.geminimodel

/**
 * Một bữa ăn cụ thể trong kế hoạch bữa ăn.
 */
data class PlannedMeal(
    val mealTypeName: String, // "Breakfast", "Lunch", "Dinner" (có thể là MealType.name)
    val recipeId: Int,        // ID để tra cứu Recipe gốc
    val recipeName: String,   // Tên công thức để hiển thị nhanh
    val recipeImage: String? = null // Ảnh công thức
)
