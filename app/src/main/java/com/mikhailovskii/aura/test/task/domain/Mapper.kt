package com.mikhailovskii.aura.test.task.domain

interface Mapper<I, O> {
    operator fun invoke(input: I): O
}