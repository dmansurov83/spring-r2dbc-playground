package ru.dmansurov.demo.person.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.dmansurov.demo.person.Person

@Repository
interface PersonRepository : R2dbcRepository<Person, Int> {
    @Query("SELECT * FROM persons WHERE name = $1")
    fun findAllByName(name: String): Flux<Person>

    @Query("SELECT * FROM persons WHERE age = $1")
    fun findAllByAge(age: Int): Flux<Person>

    @Query("SELECT * FROM persons WHERE id = $1")
    fun findOne(id:Int): Mono<Person>
}