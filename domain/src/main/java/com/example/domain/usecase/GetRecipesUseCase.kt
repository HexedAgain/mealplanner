package com.example.domain.usecase

import com.example.data.database.model.dao.StepDao
import javax.inject.Inject

interface UseCase<T> {
    suspend operator fun invoke(): T
}

interface GetRecipesUseCase: UseCase<Int>

class GetRecipesUseCaseImpl @Inject constructor(
    private val stepDao: StepDao
): GetRecipesUseCase {
    override suspend fun invoke(): Int {
        val things = stepDao.findById("some-id")
        return things?.id ?: 0
    }
}