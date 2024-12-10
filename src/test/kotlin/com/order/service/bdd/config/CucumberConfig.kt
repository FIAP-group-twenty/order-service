package com.order.service.bdd.config

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import com.order.service.OrderServiceApplication

@CucumberContextConfiguration
@SpringBootTest(classes = [OrderServiceApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CucumberSpringConfiguration