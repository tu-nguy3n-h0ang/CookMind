package com.multiverse.cookmind.domain.usecase.gemini

import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.model.ShoppingList
import com.multiverse.cookmind.domain.repository.GeminiRepository
import com.multiverse.cookmind.util.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateShoppingListUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    suspend operator fun invoke(recipes: List<Recipe>): Outcome<ShoppingList> {
        if (recipes.isEmpty()) {
            return Outcome.Error("Cần chọn ít nhất một công thức để tạo danh sách mua sắm.")
        }
        if (recipes.any { it.id <= 0 && it.name.isBlank() }) { // Kiểm tra cơ bản tính hợp lệ của Recipe
            return Outcome.Error("Một hoặc nhiều công thức được chọn không hợp lệ.")
        }
        if (recipes.size > 10) { // Ví dụ: Giới hạn số lượng công thức cho một lần tạo shopping list
            return Outcome.Error("Chỉ có thể tạo danh sách mua sắm cho tối đa 10 công thức mỗi lần.")
        }

        val result = geminiRepository.generateShoppingList(recipes).firstOrNull()

        return when (result) {
            is Outcome.Success -> {
                if (result.data != null) {
                    if (result.data.items.isNotEmpty()) {
                        Outcome.Success(result.data)
                    } else {
                        Outcome.Error("Gemini đã tạo danh sách mua sắm nhưng không có mục nào.")
                    }
                } else {
                    Outcome.Error("Gemini đã xử lý thành công nhưng không có dữ liệu danh sách mua sắm.")
                }
            }
            is Outcome.Error -> Outcome.Error("Lỗi khi tạo danh sách mua sắm: ${result.message}")
            is Outcome.Loading -> Outcome.Error("Đang tạo danh sách mua sắm (Loading), vui lòng thử lại.")
            null -> Outcome.Error("Không nhận được phản hồi khi tạo danh sách mua sắm.")
        }
    }
}