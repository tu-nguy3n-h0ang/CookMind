package com.multiverse.cookmind.domain.geminimodel

/**
 * Kế hoạch bữa ăn được tạo ra.
 */
data class MealPlan(
    val id: String? = null, // ID có thể được tạo ra nếu lưu trữ
    val name: String, // Ví dụ: "Kế hoạch ăn kiêng tuần 1"
    val description: String? = null,
    val days: List<MealPlanDay>,
    val totalEstimatedCalories: Int? = null
)
