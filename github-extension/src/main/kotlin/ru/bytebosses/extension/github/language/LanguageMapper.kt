package ru.bytebosses.extension.github.language

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

class LanguageMapper(val mappingTable: Map<String, String> = hashMapOf()) {
    fun map(key: String): String {
        return mappingTable[key]!!
    }

    companion object {
        fun ofResource(name: String): LanguageMapper {
            val resource = LanguageMapper::class.java.getResourceAsStream(name)
            return LanguageMapper(YAMLMapper().readValue(resource, object : TypeReference<Map<String, String>>() {}))
        }

        fun default(): LanguageMapper {
            return ofResource("/ru-RU.yaml")
        }
    }
}
