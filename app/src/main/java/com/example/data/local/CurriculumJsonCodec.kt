package com.example.data.local

import com.example.model.LessonSection
import com.example.model.ReviewAnswerRecord
import org.json.JSONArray
import org.json.JSONObject

object CurriculumJsonCodec {
    fun encodeLessonSections(sections: List<LessonSection>): String {
        val array = JSONArray()
        sections.forEach { section ->
            val pointsArray = JSONArray()
            section.keyPoints.forEach { pointsArray.put(it) }
            val obj = JSONObject()
                .put("heading", section.heading)
                .put("body", section.body)
                .put("keyPoints", pointsArray)
            array.put(obj)
        }
        return array.toString()
    }

    fun decodeLessonSections(json: String): List<LessonSection> {
        if (json.isBlank()) return emptyList()
        val array = JSONArray(json)
        return List(array.length()) { index ->
            val obj = array.getJSONObject(index)
            val pointsArray = obj.optJSONArray("keyPoints") ?: JSONArray()
            val points = List(pointsArray.length()) { pIdx -> pointsArray.getString(pIdx) }
            LessonSection(
                heading = obj.getString("heading"),
                body = obj.getString("body"),
                keyPoints = points,
            )
        }
    }

    fun encodeStringList(items: List<String>): String {
        val array = JSONArray()
        items.forEach { array.put(it) }
        return array.toString()
    }

    fun decodeStringList(json: String): List<String> {
        if (json.isBlank()) return emptyList()
        val array = JSONArray(json)
        return List(array.length()) { index -> array.getString(index) }
    }

    fun encodeReviewItems(items: List<ReviewAnswerRecord>): String {
        val array = JSONArray()
        items.forEach { item ->
            val optionsArray = JSONArray()
            item.options.forEach { optionsArray.put(it) }
            val obj = JSONObject()
                .put("questionId", item.questionId)
                .put("subjectId", item.subjectId)
                .put("subjectName", item.subjectName)
                .put("prompt", item.prompt)
                .put("options", optionsArray)
                .put("selectedIndex", item.selectedIndex ?: -1)
                .put("correctIndex", item.correctIndex)
                .put("explanation", item.explanation)
                .put("isFlagged", item.isFlagged)
            array.put(obj)
        }
        return array.toString()
    }

    fun decodeReviewItems(json: String): List<ReviewAnswerRecord> {
        if (json.isBlank()) return emptyList()
        val array = JSONArray(json)
        return List(array.length()) { index ->
            val obj = array.getJSONObject(index)
            val optionsArray = obj.getJSONArray("options")
            val selectedRaw = obj.optInt("selectedIndex", -1)
            ReviewAnswerRecord(
                questionId = obj.getString("questionId"),
                subjectId = obj.getString("subjectId"),
                subjectName = obj.getString("subjectName"),
                prompt = obj.getString("prompt"),
                options = List(optionsArray.length()) { oIdx -> optionsArray.getString(oIdx) },
                selectedIndex = if (selectedRaw < 0) null else selectedRaw,
                correctIndex = obj.getInt("correctIndex"),
                explanation = obj.getString("explanation"),
                isFlagged = obj.optBoolean("isFlagged", false),
            )
        }
    }
}
