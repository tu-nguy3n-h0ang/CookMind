package com.multiverse.cookmind.domain.model

data class Ingredient(
    val id: Long? = null, // Id từ database nếu ta lưu trữ phần tử duy nhất.
    val name: String
)

