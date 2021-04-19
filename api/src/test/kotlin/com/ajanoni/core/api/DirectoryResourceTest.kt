package com.ajanoni.core.api

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
class DirectoryResourceTest {

    val testDir = DirectoryResourceTest::class.java.getResource("/testdir")

    @Test
    fun testDirectoryList() {
        given()
            .`when`().get("/directory?directoryPath=${testDir?.path}")
            .then()
            .statusCode(200)
            .body("items[0].name", equalTo("test.txt"),
                "items[1].name", equalTo("*dir2"),
                "items[2].name", equalTo("*dir1"))
    }

    @Test
    fun testDirectoryListBlankParameter() {
        given()
            .`when`().get("/directory")
            .then()
            .statusCode(400)
            .body(`is`("{ \"messages\": [\"directoryPath parameter required\"] }"))
    }

    @Test
    fun testDirectoryListNotFound() {
        given()
            .`when`().get("/directory?directoryPath=notfoundidr")
            .then()
            .statusCode(404)
            .body(`is`("{ \"messages\": [\"Directory not found: /notfoundidr\"] }"))
    }

}
