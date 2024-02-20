package com.equijada95.domain.model

enum class Gender(val value: String) {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown");

    companion object {
        infix fun from(value: String): Gender = entries.firstOrNull { it.value == value } ?: UNKNOWN
    }
}