package com.multiverse.cookmind.domain.repository

import com.multiverse.cookmind.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import com.multiverse.cookmind.util.Outcome

interface RecipeRepository {

    fun getRecipes(): Flow<Outcome<List<Recipe>>>

    fun getRecipeById(id: Int): Flow<Outcome<Recipe>>

    fun searchRecipes(query: String): Flow<Outcome<List<Recipe>>>

    fun getRecipesByTag(tag: String): Flow<Outcome<List<Recipe>>> // tag vẫn là String để tìm kiếm

    fun getRecipesByMealType(mealTypeName: String): Flow<Outcome<List<Recipe>>> // mealTypeName vẫn là String

    fun addRecipe(recipe: Recipe): Flow<Outcome<Recipe>>

    fun updateRecipe(id: Int, recipe: Recipe): Flow<Outcome<Recipe>>

    fun deleteRecipe(id: Int): Flow<Outcome<Recipe>>


}