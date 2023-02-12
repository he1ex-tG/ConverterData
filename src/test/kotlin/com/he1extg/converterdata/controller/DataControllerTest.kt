package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdFilenameDTO
import com.he1extg.converterdata.dto.FileUploadDTO
import com.he1extg.converterdata.exception.ApiError
import org.assertj.core.api.Assertions.assertThat
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
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class DataControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}
    @Order(1)
    @Test
    fun `getFileList without param 'user' - return empty list`() {
        val requestEntity = RequestEntity.get("/api/v1/files")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, typeReference<List<IdFilenameDTO>>())

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it).isEmpty()
        }
    }

    @Order(2)
    @Test
    fun `getFileList with no legal user - return empty List`() {
        val uri = UriComponentsBuilder.fromUriString("/api/v1/files")
            .queryParam("username", "testUser")
            .encode()
            .toUriString()
        val requestEntity = RequestEntity.get(uri)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, typeReference<List<IdFilenameDTO>>())

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it).isEmpty()
        }
    }

    @Order(3)
    @Test
    fun `getFile with incorrect id - return BAD_REQUEST`() {
        val requestEntity = RequestEntity.get("/api/v1/files/100")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("id")
        }
    }

    @Order(4)
    @Test
    fun `setFile with incorrect params - return BAD_REQEST`() {
        val fileUploadDTO = FileUploadDTO(
            byteArrayOf(1, 2, 3),
            "",
            "aaa"
        )
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.APPLICATION_JSON)
            .body(fileUploadDTO)

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Order(5)
    @Test
    fun `setFile with correct params - return OK`() {
        val fileResource = FileSystemResource("E:/test.mp3")
        val fileUploadDTO = FileUploadDTO(
            fileResource.file.readBytes(),
            "test.mp3",
            "testUser"
        )
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.APPLICATION_JSON)
            .body(fileUploadDTO)

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Order(6)
    @Test
    fun `getFileList - return not empty List`() {
        val uri = UriComponentsBuilder.fromUriString("/api/v1/files")
            .queryParam("username", "testUser")
            .encode()
            .toUriString()
        val requestEntity = RequestEntity.get(uri)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, typeReference<List<IdFilenameDTO>>())

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        val returnList = answer.body!!
        assertThat(returnList.size).isEqualTo(1)
        assertThat(returnList[0].id).isEqualTo(1)
        assertThat(returnList[0].filename).isEqualTo("test.mp3")
    }

    @Order(7)
    @Test
    fun `getFile with correct id = 1 - return OK and file data`() {
        val requestEntity = RequestEntity.get("/api/v1/files/1")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, FilenameBytearrayDTO::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        val returnData = answer.body!!
        assertThat(returnData.filename).isEqualTo("test.mp3")
        assertThat(returnData.content.decodeToString()).contains("LAME")
    }

    @Order(8)
    @Test
    fun `setFile via null JSON transfer object - return BAD_REQUEST`() {
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.APPLICATION_JSON)
            .build()

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Order(9)
    @Test
    fun `setFile via incorrect JSON transfer object with empty content - return BAD_REQUEST`() {
        val fileUploadDTO = FileUploadDTO(
            byteArrayOf(),
            "",
            "testUser"
        )
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileUploadDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, ApiError::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(answer.body).isNotNull
        answer.body?.let {
            assertThat(it.message).contains("invalid argument")
            assertThat(it.subErrors?.size).isEqualTo(2)
            assertThat(it.subErrors?.get(1)?.message).contains("Must")
        }
    }

    @Order(10)
    @Test
    fun `setFile via correct JSON transfer object - return OK`() {
        val fileUploadDTO = FileUploadDTO(
            byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            "testFilename",
            "testUser"
        )
        val requestEntity = RequestEntity.post("/api/v1/files")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                fileUploadDTO
            )

        val answer = testRestTemplate.exchange(requestEntity, Unit::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Order(11)
    @Test
    fun `getFile with correct id = 2 - return OK and file data`() {
        val requestEntity = RequestEntity.get("/api/v1/files/2")
            .build()

        val answer = testRestTemplate.exchange(requestEntity, FilenameBytearrayDTO::class.java)

        assertThat(answer.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(answer.body).isNotNull
        val returnData = answer.body!!
        assertThat(returnData.filename).isEqualTo("testFilename")
        assertThat(returnData.content.size).isEqualTo(10)
    }
}