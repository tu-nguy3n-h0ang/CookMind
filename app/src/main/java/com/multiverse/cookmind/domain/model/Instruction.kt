package com.multiverse.cookmind.domain.model

data class Instruction(
    val stepNumber: Int? = null,
    val description: String,
    val imageUrl: String? = null
)
