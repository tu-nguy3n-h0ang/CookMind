package com.multiverse.cookmind.domain.geminimodel

import com.multiverse.cookmind.domain.model.Recipe

/**
 * Đại diện cho một gợi ý từ Gemini API.
 * Có thể là một công thức hoàn chỉnh, một mẹo, một sự thay thế, v.v.
 */
data class RecipeSuggestion(
    val type: SuggestionType,
    val title: String,
    val description: String,
    val suggestedFullRecipe: Recipe? = null,
    val originalRecipeId: Int? = null,
    val specificQuickSuggestions: List<String>? = null,
    val additionalContext: Map<String, String>? = null
)
