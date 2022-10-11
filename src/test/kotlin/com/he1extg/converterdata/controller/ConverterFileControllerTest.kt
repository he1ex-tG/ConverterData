package com.he1extg.converterdata.controller

import com.he1extg.converterdata.entity.dto.CfIdAndFilenameDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class ConverterFileControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `getFileList without param 'user' will return HttpStatus = BAD_REQUEST`() {
        val requestEntity = RequestEntity.get("/api/v1/files")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `getFileList will return empty List`() {
        val queryParams = hashMapOf<String, String>().apply {
            set("queryParam1", "testUser")
        }
        val requestEntity = RequestEntity.get("/api/v1/files?user={queryParam1}", queryParams)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, List::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `getFile with incorrect id will return HttpStatus = NO_CONTENT`() {
        val requestEntity = RequestEntity.get("/api/v1/files/100")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `setFile with incorrect params will return HttpStatus = BAD_REQEST`() {
        val requestEntity = RequestEntity.post("/api/v1/files")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Order(1)
    @Test
    fun `setFile with will return HttpStatus = OK`() {
        println("1111")
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(
                LinkedMultiValueMap<String, Any>().apply {
                    add("user", "testUser")
                    add("file", FileSystemResource("E:/test.mp3"))
                }
            )

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `getFileList will return not List`() {
        println("2222")
        val queryParams = hashMapOf<String, String>().apply {
            set("queryParam1", "testUser")
        }
        //val requestEntity = RequestEntity.get("/api/v1/files?user={queryParam1}", queryParams)
        val requestEntity = RequestEntity.get("/api/v1/files?user=testUser")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, List::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
    }
}