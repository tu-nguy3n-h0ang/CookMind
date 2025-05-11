package com.multiverse.cookmind.domain.repository

import com.multiverse.cookmind.domain.model.MealType
import com.multiverse.cookmind.domain.model.Recipe
import com.multiverse.cookmind.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import com.multiverse.cookmind.util.Outcome

interface RecipeRepository {

    /**
     * Lấy danh sách công thức, có thể lọc theo query, tags, mealTypes.
     * @param query Từ khóa tìm kiếm cho tên công thức.
     * @param tags Danh sách tên tags để lọc.
     * @param mealTypes Danh sách tên meal types để lọc.
     */
    fun getRecipes(
        query: String? = null,
        tags: List<String>? = null,
        mealTypes: List<String>? = null
    ): Flow<Outcome<List<Recipe>>>

    fun getRecipeDetails(id: Int): Flow<Outcome<Recipe>>

    /**
     * Lấy tất cả các tags duy nhất có sẵn.
     * Hữu ích cho việc hiển thị bộ lọc tags.
     */
    fun getAllAvailableTags(): Flow<Outcome<List<Tag>>>

    /**
     * Lấy tất cả các meal types duy nhất có sẵn.
     * Hữu ích cho việc hiển thị bộ lọc meal types.
     */
    fun getAllAvailableMealTypes(): Flow<Outcome<List<MealType>>>

    // Các hàm mô phỏng cho DummyJSON (không thực sự thay đổi dữ liệu trên server)
    suspend fun addRecipe(recipe: Recipe): Outcome<Recipe>
    suspend fun updateRecipe(recipeId: Int, updatedRecipe: Recipe): Outcome<Recipe>
    suspend fun deleteRecipe(id: Int): Outcome<Unit>


}