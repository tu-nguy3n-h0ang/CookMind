package com.multiverse.cookmind.domain.geminimodel

enum class SuggestionType {
    VARIATION,              // Biến thể công thức
    INGREDIENT_SUBSTITUTION,// Thay thế nguyên liệu
    COOKING_TECHNIQUE_TIP,  // Mẹo/Giải thích kỹ thuật nấu ăn
    GENERAL_RECIPE_TIPS,    // Hoặc thêm một cái mới như thế này
    PAIRING_SUGGESTION,     // Gợi ý kết hợp món ăn/đồ uống
    RECIPE_SUMMARY,         // Tóm tắt công thức
    NUTRITIONAL_ADJUSTMENT,  // Điều chỉnh dinh dưỡng
    RECIPE_GENERATION // Khi Gemini tạo công thức mới hoàn toàn
}