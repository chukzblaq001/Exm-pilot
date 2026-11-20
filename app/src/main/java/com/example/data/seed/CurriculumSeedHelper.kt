package com.example.data.seed

import com.example.data.local.CurriculumJsonCodec
import com.example.data.local.QuestionEntity
import com.example.data.local.SubjectEntity
import com.example.data.local.TopicEntity
import com.example.model.LessonSection
import com.example.model.SubjectCategory

data class SeedBundle(
    val subjects: List<SubjectEntity>,
    val topics: List<TopicEntity>,
    val questions: List<QuestionEntity>,
)

fun seedTopic(
    id: String,
    subjectId: String,
    title: String,
    examTagsCsv: String,
    orderIndex: Int,
    estimatedMinutes: Int,
    summary: String,
    sections: List<LessonSection>,
    keyFormulas: List<String>,
    workedExample: String,
): TopicEntity = TopicEntity(
    id = id,
    subjectId = subjectId,
    title = title,
    examTagsCsv = examTagsCsv,
    orderIndex = orderIndex,
    estimatedMinutes = estimatedMinutes,
    summary = summary,
    lessonSectionsJson = CurriculumJsonCodec.encodeLessonSections(sections),
    keyFormulasJson = CurriculumJsonCodec.encodeStringList(keyFormulas),
    workedExample = workedExample,
)

fun seedQuestion(
    id: String,
    subjectId: String,
    topicId: String,
    examTag: String,
    prompt: String,
    options: List<String>,
    correctIndex: Int,
    explanation: String,
): QuestionEntity {
    require(options.size == 4) { "Question $id must have 4 options" }
    return QuestionEntity(
        id = id,
        subjectId = subjectId,
        topicId = topicId,
        examTag = examTag,
        prompt = prompt,
        optionA = options[0],
        optionB = options[1],
        optionC = options[2],
        optionD = options[3],
        correctIndex = correctIndex,
        explanation = explanation,
    )
}

object CurriculumSeedProvider {
    fun buildSeedBundle(): SeedBundle {
        val subjects = listOf(
            SubjectEntity(
                id = "eng",
                name = "Use of English",
                code = "ENG",
                categoryName = SubjectCategory.CORE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Concord, lexis & structure, oral contrasts, essay & comprehension rules.",
                sortOrder = 1,
            ),
            SubjectEntity(
                id = "mth",
                name = "Mathematics",
                code = "MTH",
                categoryName = SubjectCategory.CORE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Indices, logarithms, quadratics, trigonometry, circle theorems & calculus.",
                sortOrder = 2,
            ),
            SubjectEntity(
                id = "civic",
                name = "Civic Education",
                code = "CIV",
                categoryName = SubjectCategory.CORE.name,
                supportedExamsCsv = "WAEC,NECO",
                tagline = "National values, citizenship, human rights, democracy, and road safety.",
                sortOrder = 3,
            ),
            SubjectEntity(
                id = "bio",
                name = "Biology",
                code = "BIO",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Cell physiology, Mendelian genetics, ecological cycles, and adaptation.",
                sortOrder = 4,
            ),
            SubjectEntity(
                id = "chm",
                name = "Chemistry",
                code = "CHM",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Mole stoichiometry, bonding, acid-base equilibria, and salt analysis.",
                sortOrder = 5,
            ),
            SubjectEntity(
                id = "phy",
                name = "Physics",
                code = "PHY",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Kinematics, mechanics, current electricity, and modern nuclear physics.",
                sortOrder = 6,
            ),
            SubjectEntity(
                id = "agr",
                name = "Agricultural Science",
                code = "AGR",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Soil fertility, crop husbandry, pests, and sustainable farm management.",
                sortOrder = 7,
            ),
            SubjectEntity(
                id = "fmath",
                name = "Further Mathematics",
                code = "FMT",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,NECO",
                tagline = "Vectors, dot products, mechanics, and binomial probability distributions.",
                sortOrder = 8,
            ),
            SubjectEntity(
                id = "geo",
                name = "Geography",
                code = "GEO",
                categoryName = SubjectCategory.SCIENCE.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Map reading, contour gradients, weather instruments, and Nigerian relief.",
                sortOrder = 9,
            ),
            SubjectEntity(
                id = "eco",
                name = "Economics",
                code = "ECO",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Demand & elasticity, production costs, and national income accounting.",
                sortOrder = 10,
            ),
            SubjectEntity(
                id = "gov",
                name = "Government",
                code = "GOV",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Constitutional history, federalism, democracy, and regional bodies.",
                sortOrder = 11,
            ),
            SubjectEntity(
                id = "lit",
                name = "Literature-in-English",
                code = "LIT",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Poetic devices, dramatic structure, and unseen literary appreciation.",
                sortOrder = 12,
            ),
            SubjectEntity(
                id = "acc",
                name = "Financial Accounting",
                code = "ACC",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Double entry, cash books, trial balances, and final accounts adjustments.",
                sortOrder = 13,
            ),
            SubjectEntity(
                id = "com",
                name = "Commerce",
                code = "COM",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Home and foreign trade, banking, insurance, and business organizations.",
                sortOrder = 14,
            ),
            SubjectEntity(
                id = "crs",
                name = "Christian Religious Studies",
                code = "CRS",
                categoryName = SubjectCategory.ARTS_COMMERCIAL.name,
                supportedExamsCsv = "WAEC,JAMB,NECO",
                tagline = "Old Testament leadership, synoptic gospels, and the early apostolic church.",
                sortOrder = 15,
            ),
        )

        val topics = CoreSubjectsSeed.topics() +
            ScienceSubjectsSeed.topics() +
            ArtsCommercialSubjectsSeed.topics() +
            ExpandedSubjectsSeed.topics()

        val questions = CoreSubjectsSeed.questions() +
            ScienceSubjectsSeed.questions() +
            ArtsCommercialSubjectsSeed.questions() +
            ExpandedSubjectsSeed.questions()

        return SeedBundle(subjects = subjects, topics = topics, questions = questions)
    }
}
