package com.he1extg.converterdata.dao.file

import com.he1extg.converterdata.dto.file.ContentDTO

data class ContentDAO(
    val filename: String,
    val content: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentDAO

        if (filename != other.filename) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filename.hashCode()
        result = 31 * result + content.contentHashCode()
        return result
    }

    fun toContentDTO(): ContentDTO = ContentDTO(filename, content)
}
