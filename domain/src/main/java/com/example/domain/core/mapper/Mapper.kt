package com.example.domain.core.mapper

interface Mapper<In, Out> {
    operator fun invoke(input: In): Out
}