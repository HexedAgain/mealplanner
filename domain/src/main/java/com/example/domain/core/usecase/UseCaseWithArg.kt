package com.example.domain.core.usecase

interface UseCaseWithArg<S, T> {
    suspend operator fun invoke(arg: S): T
}