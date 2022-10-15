package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.converterfile.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.converterfile.IdFilenameDTO
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class ConverterFileControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Order(1)
    @Test
    fun `getFileList without param 'user' will return HttpStatus = BAD_REQUEST`() {
        val requestEntity = RequestEntity.get("/api/v1/files")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Order(2)
    @Test
    fun `getFileList will return empty List`() {
        val uri = UriComponentsBuilder.fromUriString("/api/v1/files")
            .queryParam("user", "testUser")
            .encode()
            .toUriString()
        val requestEntity = RequestEntity.get(uri)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, List::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Order(3)
    @Test
    fun `getFile with incorrect id will return HttpStatus = NO_CONTENT`() {
        val requestEntity = RequestEntity.get("/api/v1/files/100")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Order(4)
    @Test
    fun `setFile with incorrect params will return HttpStatus = BAD_REQEST`() {
        val requestEntity = RequestEntity.post("/api/v1/files")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Order(5)
    @Test
    fun `setFile with correct params will return HttpStatus = OK`() {
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

    @Order(6)
    @Test
    @Suppress("UNCHECKED_CAST")
    fun `getFileList will return not empty List`() {
        val uri = UriComponentsBuilder.fromUriString("/api/v1/files")
            .queryParam("user", "testUser")
            .encode()
            .toUriString()
        val requestEntity = RequestEntity.get(uri)
            .build()

        val answer = testRestTemplate.exchange(
            requestEntity,
            object : ParameterizedTypeReference<List<IdFilenameDTO>>() {}
        )

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(answer.body).isNotNull
        val returnList = answer.body!!
        Assertions.assertThat(returnList.size).isEqualTo(1)
        Assertions.assertThat(returnList[0].id).isEqualTo(1)
        Assertions.assertThat(returnList[0].fileName).isEqualTo("test.mp3")
    }

    @Order(7)
    @Test
    fun `getFile with correct id will return HttpStatus = OK and file data`() {
        val requestEntity = RequestEntity.get("/api/v1/files/1")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, FilenameBytearrayDTO::class.java)

        Assertions.assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(answer.body).isNotNull
        val returnData = answer.body!!
        Assertions.assertThat(returnData.fileName).isEqualTo("test.mp3")
        Assertions.assertThat(returnData.file.decodeToString()).contains("LAME")
    }
}