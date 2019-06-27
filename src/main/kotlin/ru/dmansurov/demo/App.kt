package ru.dmansurov.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Mono
import ru.dmansurov.demo.person.Person
import ru.dmansurov.demo.person.repository.PersonRepository

@SpringBootApplication
@EnableWebFlux
class Application : CommandLineRunner {

    @Autowired
    private lateinit var personRepository: PersonRepository

    override fun run(vararg args: String?) {
        personRepository.saveAll(
                listOf(
                        Person(name = "Dan Newton", age = 25),
                        Person(name = "Laura So", age = 23)
                )
        ).log().subscribe()
        Thread.sleep(2000)
        personRepository.findAll().log().subscribe { log.info("findAll - $it") }
        personRepository.findAllById(Mono.just(1)).log().subscribe { log.info("findAllById - $it") }
        personRepository.findAllByName("Laura So").log().subscribe { log.info("findAllByName - $it") }
        personRepository.findAllByAge(25).log().subscribe { log.info("findAllByAge - $it") }
        Thread.sleep(5000)
    }

    private companion object {
        val log = LoggerFactory.getLogger(Application::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
