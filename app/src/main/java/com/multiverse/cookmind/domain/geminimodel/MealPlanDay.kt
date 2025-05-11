package com.multiverse.cookmind.domain.geminimodel

/**
 * Đại diện cho các bữa ăn trong một ngày của kế hoạch.
 */
data class MealPlanDay(
    val dayIdentifier: String, // Ví dụ: "Day 1", "Monday" hoặc một ngày cụ thể "2025-05-10"
    val meals: List<PlannedMeal>,
    val dailyCalorieEstimate: Int? = null, // Ước tính calo cho ngày
    val notes: String? = null // Ghi chú thêm cho ngày
)
