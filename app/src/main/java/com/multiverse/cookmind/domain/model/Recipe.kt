package com.multiverse.cookmind.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val difficulty: String,
    val cuisine: String,
    val caloriesPerServing: Int?,
    val tag: List<Tag>,
    val userId: Int,
    val image: String,
    val rating: Double,
    val reviewCount: Int,
    val mealTypes: List<MealType>
)