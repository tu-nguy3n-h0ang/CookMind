package com.multiverse.cookmind.domain.model

/**
 * Một mục trong danh sách mua sắm.
 */
data class ShoppingListItem(
    val ingredientName: String,
    val quantityAndUnit: String, // Ví dụ: "200g", "1 quả", "2 muỗng canh" (Gemini có thể giúp tổng hợp)
    val category: String? = null, // Ví dụ: "Rau củ", "Gia vị", "Thịt cá" (Gemini có thể phân loại)
    val fromRecipeNames: List<String> = emptyList(), // Ghi chú xem nguyên liệu này từ công thức nào
    var isChecked: Boolean = false // Trạng thái đã mua hay chưa
)
