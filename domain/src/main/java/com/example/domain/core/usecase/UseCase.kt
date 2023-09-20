package com.example.domain.core.usecase

interface UseCase<T> {
    suspend operator fun invoke(): T
}