package ru.dmansurov.demo.person

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("persons")
data class Person(
        @Id val id: Int? = null,
        val name: String,
        val age: Int
)