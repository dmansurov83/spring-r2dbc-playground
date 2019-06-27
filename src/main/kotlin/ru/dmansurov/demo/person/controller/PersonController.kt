package ru.dmansurov.demo.person.controller

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty
import ru.dmansurov.demo.person.Person
import ru.dmansurov.demo.person.repository.PersonRepository
import java.lang.Exception

@RestController
@RequestMapping("persons")
class PersonController(
        private val personsRepository: PersonRepository
) {
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Mono<Person> {
        return personsRepository.findOne(id).switchIfEmpty { throw Exception("Not found") }
    }
}