# "Converter: PDF to MP3" - Data

![GitHub commit activity](https://img.shields.io/github/commit-activity/m/he1ex-tG/ConverterData?logo=GitHub) ![GitHub last commit](https://img.shields.io/github/last-commit/he1ex-tG/ConverterData?logo=GitHub) ![GitHub issues](https://img.shields.io/github/issues/he1ex-tG/ConverterData?logo=GitHub) ![GitHub pull requests](https://img.shields.io/github/issues-pr/he1ex-tG/ConverterData?logo=GitHub) ![GitHub](https://img.shields.io/github/license/he1ex-tg/converterdata?logo=GitHub)

This is a study project aimed at gaining practical experience at
working with the Spring Framework, designing and developing multi-component
systems.

## Note

This is the part of [PDFReader](https://github.com/he1ex-tG/PDFReader) project.

## Structure

This module provides an [API](#1-api) for saving/getting files. Interaction 
with the [database](#2-files-store) is performed via the [service](#3-service) 
layer.

### 1. API

The API is built using the features provided by 
[Spring Boot](https://spring.io/projects/spring-boot). It has some 
endpoints that can be used by third party services:

| __Method__ | __Endpoint__            | __Description__                                                                                               |
|------------|-------------------------|---------------------------------------------------------------------------------------------------------------|
| GET        | /                       | Get info                                                                                                      |
| GET        | /api/v1                 | Get API info (e.g. request method, content type, incoming data format and data type that returns in response) |
| GET        | /api/v1/files           | Get a list of user files that is specified in the request parameter                                           |
| GET        | /api/v1/files/{id}      | Get file by id                                                                                                |
| POST       | /api/v1/files           | Upload file via JSON transfer data                                                                            |
| POST       | /api/v1/files/multipart | Upload multipart file                                                                                         |

More info in the [usage](#usage) section.

### 2. Files store

File storage is based on [Spring Data](https://spring.io/projects/spring-data) 
and [Postgres](https://www.postgresql.org/). 

[Spring Data](https://spring.io/projects/spring-data) searches in project 
files all [entities](#21-entities) and builds database structure according to 
them.

> __Note__
> 
> By default, all data is erased and database structure is rebuild on each 
> project restart. It can be configured by changing corresponding parameter in 
> `application.yaml` or passing it by another way.

[Entities](#21-entities) are used by [repositories](#23-repositories) to exchange 
data with database. Lightweight [DTOs](#22-dto) are used instead of entire 
[entities](#21-entities) to reduce traffic and increase performance. 
[DTOs](#22-dto) are also used by [API](#1-api) in some responses. Project logic 
is in the [service](#3-service) layer.

#### 2.1. Entities

There is just one entity - `ConverterFile`. It's enough to achieve the project 
goals. Class `ConverterFile` contains fields that describe uploaded file.

Values specified for each file:

    var filename: String,
    @Lob val file: ByteArray,
    var converterUser: String,

Autogenerated values:

    var timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
    @Id @GeneratedValue var id: Long? = null,

The `converterUser` parameter is `String` type. But in future it can be moved 
out as an independent entity.

#### 2.2. DTO

Using DTOs is not necessary, but it's good practice, I guess. 

In early editions (without DTO) [entities](#21-entities) from the database 
were passed in entirely to the consumer on any request. It was redundant. 

Now there are DTOs:

  `FilenameBytearrayDTO` - contains file name and body. It is used when consumer 
requests the file;

  `IdFilenameDTO` - this and the next DTO have no file body. It's used to 
provide to consumer information about stored files.

  `IdTimestampDTO` - is used to control the files amount (and its main purpose 
of poor [service](#3-service) layer).

  `FileUploadDTO` - is used to upload structured file data instead multipart file 
form.

The first two DTOs are in the form of classes, the last one is in the form of 
an interface. [SpringData](https://spring.io/projects/spring-data) automatically 
implements the interface into a wrapper class and fills it with data from 
the database. This is convenient to use when actions with the interface are 
performed inside this module. But there may be problems with their interpretation 
on the recipient's side. Therefore, the DTOs that are used in responses to the 
consumer are classes. The [repositories](#23-repositories) section has examples 
of using both classes and interfaces. Still, this project is educational, 
as mentioned earlier.

#### 2.3. Repositories

Repository interface extends `CrudRepository` interface. It allows to auto create 
methods that are needed. But also it is possible to define queries directly. Both 
approaches are presented in the code. For instance,

    /**
     * Using custom query and class DTO
     */
    @Query("select new com.he1extg.converterdata.dto.converterfile.IdFilenameDTO(c.id, c.fileName) from ConverterFile c where c.converterUser = :converterUser")
    fun getConverterFileListByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<IdFilenameDTO>

and

    /**
     * Using auto built query and interface DTO
     * Query looks like this: @Query("select c.id as id, c.timestamp as timestamp from ConverterFile c where c.converterUser = :converterUser")
     */
    fun getConverterFileTimestampByConverterUser(converterUser: String): List<IdTimestampDTO>

Even though the repository class is based on `ConverterFile` [entity](#21-entities), 
it returns [DTOs](#22-dto) as responses on queries. The main benefit of this 
is that `ByteArray`s of stored files are not transferred to consumer when he 
requests only IDs and filenames, for example. This reduces traffic and increases 
entire performance.

Repositories are used on [service](#3-service) layer.

### 3. Service

In this module, the service layer is quite simple. But its presence is necessary
to build a clean architecture. It serves to access the 
[database](#23-repositories) and pass the results to the [controller](#1-api). 
The only complication is the implementation of the function that controls the 
maximum number of files uploaded by the user.

### 4. Tests

Functional tests are located in `./src/test` directory.

## Build Instructions

Execute from command line (in project root directory)

    # ./gradlew bootJar

or manually run build task `bootJar` from IDE. 

The compiled Jar is in `./build/libs/ConverterAPI-[version]-SNAPSHOT.jar`.

## Usage

Here:
- [host] is the host where the project is running, [host] = `localhost` by 
default.
- [port] is the port. It can be changed in the `application.yaml` settings 
file. [port] = `8081` by default.

Remember that the controller returns a DTO. So it's impossible to save a byte 
array from the database to a file directly.

Get file list for user:



    # curl http://[host]:[port]/api/v1/files?user=SuperUser

Upload file:



    # curl -F file=@C:/hw.mp3 -F user=SuperUser http://[host]:[port]/api/v1/files/multipart

Download file:


    # curl http://[host]:[port]/api/v1/files/1

## TODO

- [x] Main structure
  - [x] Entities
    - [x] ConverterFile entity
    - [ ] User entity
    - [x] DTO
  - [x] Repositories
  - [x] Service
- [x] REST API endpoints
  - [x] Get file list (DTO)
  - [x] Upload file
  - [x] Download file (DTO)
    - [ ] Download byte array 

## Technologies Used:

1. [Spring Boot](https://spring.io/projects/spring-boot)
2. [Spring Data](https://spring.io/projects/spring-data)
3. [Postgres](https://www.postgresql.org/)
