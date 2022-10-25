# "Converter: PDF to MP3" - Data

![GitHub commit activity](https://img.shields.io/github/commit-activity/m/he1ex-tG/ConverterAPI?logo=GitHub) ![GitHub last commit](https://img.shields.io/github/last-commit/he1ex-tG/ConverterAPI?logo=GitHub) ![GitHub issues](https://img.shields.io/github/issues/he1ex-tG/ConverterAPI?logo=GitHub) ![GitHub pull requests](https://img.shields.io/github/issues-pr/he1ex-tG/ConverterAPI?logo=GitHub) ![GitHub](https://img.shields.io/github/license/he1ex-tg/converterapi?logo=GitHub)

This is a study project aimed at gaining practical experience at
working with the Spring Framework, designing and developing multi-component
systems.

## Note

This is the part of [PDFReader](https://github.com/he1ex-tG/PDFReader) project.

## Structure

This module provides an [API](#1-api) for saving files to the 
[database](#2-files-store) and extracting.

### 1. API

The API is built using the features provided by 
[Spring Boot](https://spring.io/projects/spring-boot). It provides some 
endpoints that can be used by third party services:

| __Method__ | __Endpoint__       | __Description__                                                                                               |
|------------|--------------------|---------------------------------------------------------------------------------------------------------------|
| GET        | /                  | Get info                                                                                                      |
| GET        | /api/v1            | Get API info (e.g. request method, content type, incoming data format and data type that returns in response) |
| GET        | /api/v1/files      | Get a list of user files that is specified in the request parameter                                           |
| GET        | /api/v1/files/{id} | Get file by id                                                                                                |
| POST       | /api/v1/files      | Upload file                                                                                                   |

More info in [usage](#usage) section.

### 2. Files store

File storage is based on [Spring Data](https://spring.io/projects/spring-data) 
project.  [PostgreSQL](https://www.postgresql.org/) database. 

#### 2.1. Entities
#### 2.2. DTO
#### 2.3. Repositories
#### 2.4. Service



Converting a PDF file into text (array of bytes) is made using the
[ITextPDF](https://itextpdf.com/) library. Then the text is converted by 
the [FreeTTS](https://freetts.sourceforge.io/) library.

> __Note__. By default, FreeTTS does not provide the ability to
output the audio stream as a `ByteArrayInputStream` or `ByteArray`, for example.
So, I made my own implementation of the `AudioPlayer` interface.
This approach makes it possible to avoid intermediate saving of
audio data to a file, as well as to hot convert from WAV to MP3 using
[Lame](https://lame.sourceforge.io/).

### 3. Tests

Functional tests are located in `./src/test` directory.

## Build Instructions

To successfully build the project, you should first compile Lame yourself. After 
compilation, it will be placed in the Maven local repository (included in the 
`repositories` section in the `build.gradle.kts` file). Compilation instructions 
and source codes can be found on the [Lame](https://lame.sourceforge.io/) website.

The project is built after the dependency issues are resolved. For example,

from command line (in project root directory)

    # ./gradlew bootJar

or manually run build task `bootJar` from IDE. 

The compiled Jar is in `./build/libs/ConverterAPI-[version]-SNAPSHOT.jar`.

## Usage

Here:
- [host] is the host where the project is running, [host] = `localhost` by 
default.
- [port] is the port. It can be changed in the `application.yaml` settings 
file. [port] = `8081` by default.

Files conversion:



    # curl -F file=@C:/hw.pdf -o C:/hw.mp3 http://[host]:[port]/api/v1/file

Text conversion:



    # curl -d "text=Hello world!" -o C:/hw.mp3 http://[host]:[port]/api/v1/text

## TODO

- [x] Conversion process
  - [x] PDF file to text
  - [x] Text to audio format (WAV by default)
    - [x] WAV to MP3
    - [ ] Male/female voice choosing
- [x] REST API endpoints
  - [x] File conversion
  - [x] Text conversion

## Technologies Used:

1. [Spring Boot](https://spring.io/projects/spring-boot)
2. [ITextPDF](https://itextpdf.com/)
3. [FreeTTS](https://freetts.sourceforge.io/)
4. [Lame](https://lame.sourceforge.io/)
