package com.equijada95.domain.mapper

interface BaseMapper<T, K> {

    fun map(toMap: T?): K?

}