package com.multiverse.cookmind.domain.model

/**
 * Danh sách mua sắm tổng hợp.
 */
data class ShoppingList(
    val id: String, // Có thể là UUID tự tạo
    val title: String, // Ví dụ: "Danh sách mua sắm cho Kế hoạch bữa ăn tuần 1"
    val items: List<ShoppingListItem>,
    val createdAt: Long = System.currentTimeMillis(),
    val notes: String? = null // Ghi chú chung cho việc đi chợ
)
