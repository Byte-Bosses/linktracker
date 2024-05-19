package ru.bytebosses.extension.api.mapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

class YamlTextMapper(private val map: Map<String, String>) : TextMapper {
    override fun map(text: String): String {
        return map[text] ?: text
    }

    companion object {
        fun ofResource(name: String, provider: Class<*>): YamlTextMapper {
            val resource = provider.getResourceAsStream(name)
            return YamlTextMapper(YAMLMapper().readValue(resource, object : TypeReference<Map<String, String>>() {}))
        }

        fun default(provider: Class<*>): YamlTextMapper {
            return ofResource("ru-RU.yaml", provider)
        }
    }
}
