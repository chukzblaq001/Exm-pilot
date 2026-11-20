package com.example.data.seed

import com.example.data.local.QuestionEntity
import com.example.data.local.TopicEntity
import com.example.model.LessonSection

object CoreSubjectsSeed {
    fun topics(): List<TopicEntity> = listOf(
        // Use of English
        seedTopic(
            id = "eng_concord",
            subjectId = "eng",
            title = "Concord & Subject-Verb Agreement",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 12,
            summary = "Master grammatical agreement between subjects and verbs across tricky compound, collective, and indefinite structures.",
            sections = listOf(
                LessonSection(
                    heading = "1. Proximity & Correlative Conjunctions",
                    body = "When two subjects are joined by 'either...or', 'neither...nor', or 'not only...but also', the verb agrees with the subject nearer to it (Rule of Proximity).",
                    keyPoints = listOf(
                        "Neither the teachers nor the principal WAS present.",
                        "Neither the principal nor the teachers WERE present.",
                        "Parenthetical phrases like 'as well as', 'along with', and 'together with' do NOT make a singular subject plural.",
                    ),
                ),
                LessonSection(
                    heading = "2. Indefinite Pronouns & Subjunctive Mood",
                    body = "Indefinite pronouns such as 'each', 'everyone', 'everybody', 'nobody', and 'none of' take singular verbs in formal WAEC and UTME grammar. After 'It is high time' or 'I wish', use the past tense (subjunctive).",
                    keyPoints = listOf(
                        "Each of the candidates HAS a registration slip.",
                        "It is high time we LEFT for the examination hall.",
                        "'A number of' + plural noun = plural verb; 'The number of' + plural noun = singular verb.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "S1 + (as well as / along with / together with) + S2 → Verb agrees with S1",
                "Either/Neither + S1 + or/nor + S2 → Verb agrees with S2 (nearest)",
                "The number of + plural noun → Singular Verb | A number of + plural noun → Plural Verb",
                "It is high time + Subject → Past Tense Verb (e.g., went, studied)",
            ),
            workedExample = "Question: The Vice-Chancellor, together with his registrars, ____ attending the convocation.\nStep 1: Identify the true subject ('The Vice-Chancellor', singular).\nStep 2: Ignore the parenthetical phrase 'together with his registrars'.\nAnswer: 'is' (singular verb).",
        ),
        seedTopic(
            id = "eng_lexis",
            subjectId = "eng",
            title = "Lexis, Structure & Idiomatic Expressions",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 14,
            summary = "Decode Nigerian exam collocations, prepositional verbs, antonyms in context, and standard English idioms.",
            sections = listOf(
                LessonSection(
                    heading = "1. Fixed Prepositional Collocations",
                    body = "WAEC and JAMB repeatedly test exact preposition pairings. Verbs like 'congratulate' pair with 'on', 'comply' with 'with', 'prefer' with 'to', and 'averse' with 'to'.",
                    keyPoints = listOf(
                        "Congratulate someone ON (not for) their success.",
                        "Comprise takes NO 'of' in active voice ('The committee comprises five members').",
                        "Prefer X TO Y (never 'than' when comparing nouns/gerunds).",
                    ),
                ),
                LessonSection(
                    heading = "2. High-Frequency Exam Idioms",
                    body = "Idioms carry figurative rather than literal meanings. Read the full sentence context to eliminate literal distractors.",
                    keyPoints = listOf(
                        "'To beat about the bush' = avoid the main point.",
                        "'To smell a rat' = suspect trickery or foul play.",
                        "'At loggerheads' = in strong disagreement.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Conducive + TO | Oblivious + OF | Accused + OF | Acquitted + OF",
                "In accordance + WITH | On behalf + OF | Result + IN (outcome) / FROM (cause)",
                "Hardly / Scarcely ... WHEN | No sooner ... THAN",
            ),
            workedExample = "Question: No sooner had the bell rung ____ the students submitted their scripts.\nRule: 'No sooner' always pairs with 'than', whereas 'hardly/scarcely' pairs with 'when'.\nAnswer: 'than'.",
        ),
        seedTopic(
            id = "eng_oral",
            subjectId = "eng",
            title = "Oral English: Vowel, Consonant & Stress Patterns",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 10,
            summary = "Distinguish English monophthongs, diphthongs, silent consonants, and primary word/emphatic stress.",
            sections = listOf(
                LessonSection(
                    heading = "1. Silent Letters & Consonant Contrasts",
                    body = "Focus on words whose spelling hides silent letters or unusual consonant sounds tested in Oral English sections.",
                    keyPoints = listOf(
                        "Silent 'b': debt, subtle, plumber, tomb.",
                        "Silent 'p': psychology, receipt, pneumonia, coup.",
                        "'ch' as /ʃ/ (machine, chef, champagne) vs /k/ (chaos, chemist, choir).",
                    ),
                ),
                LessonSection(
                    heading = "2. Word Stress & Emphatic Stress",
                    body = "Words ending in '-tion', '-sion', '-ic', and '-ity' place primary stress on the syllable immediately BEFORE the suffix (e.g., edu-CA-tion, geo-GRA-phic, uni-VER-sity). In emphatic stress, the right question contradicts the capitalized word.",
                    keyPoints = listOf(
                        "Suffix rule: '-ic', '-ion', '-ian', '-ity' → stress penultimate syllable.",
                        "Suffix rule: '-ate', '-ise', '-fy', '-cal' → stress 3rd syllable from the end.",
                        "Emphatic stress: Pick the option that replaces the stressed word, keeping everything else identical.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Stress before -TION / -SION / -IC / -ITY: e.g. pro-DUC-tion, e-LEC-tric",
                "Two-syllable Noun/Adj = 1st syllable (IM-port) | Verb = 2nd syllable (im-PORT)",
                "Emphatic Stress Answer = contradicts ONLY the capitalized word",
            ),
            workedExample = "Question: Which word has the same vowel sound as 'bought' /ɔː/? Options: (A) hot (B) court (C) cut (D) coat.\nAnalysis: 'court' has the long /ɔː/ sound just like 'bought'. 'hot' is /ɒ/, 'cut' is /ʌ/, 'coat' is /əʊ/.\nAnswer: court.",
        ),
        // Mathematics
        seedTopic(
            id = "mth_indices",
            subjectId = "mth",
            title = "Indices, Logarithms & Surds",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 15,
            summary = "Simplify exponential expressions, solve logarithmic equations, and rationalize surd denominators accurately.",
            sections = listOf(
                LessonSection(
                    heading = "1. Laws of Indices & Exponential Equations",
                    body = "Equating bases is the fastest route to solving exponential equations in UTME and WASSCE Objective papers. Remember that any non-zero number raised to power 0 equals 1, and negative exponents represent reciprocals.",
                    keyPoints = listOf(
                        "a^m × a^n = a^(m+n) and a^m ÷ a^n = a^(m-n).",
                        "a^(-n) = 1 / a^n and a^(p/q) = (q√a)^p.",
                        "If a^x = a^y (a > 0, a ≠ 1), then x = y.",
                    ),
                ),
                LessonSection(
                    heading = "2. Logarithms & Rationalizing Surds",
                    body = "Logarithm is the inverse of exponentiation: log_b(x) = y means b^y = x. To rationalize a binomial surd denominator (a + √b), multiply numerator and denominator by its conjugate (a - √b).",
                    keyPoints = listOf(
                        "log(AB) = log A + log B; log(A/B) = log A - log B.",
                        "log_a(a) = 1 and log_a(1) = 0.",
                        "Conjugate of (√5 + √2) is (√5 - √2), giving product 5 - 2 = 3.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "(a^m)^n = a^(mn)  |  a^(1/n) = ⁿ√a",
                "log_b(M^k) = k·log_b(M)  |  log_b(x) = y ⇔ b^y = x",
                "(√a + √b)(√a - √b) = a - b",
            ),
            workedExample = "Solve 2^(x+1) = 16.\nStep 1: Rewrite 16 as 2^4.\nStep 2: 2^(x+1) = 2^4 ⇒ x + 1 = 4.\nAnswer: x = 3.",
        ),
        seedTopic(
            id = "mth_quadratics",
            subjectId = "mth",
            title = "Quadratic Equations & Sum/Product of Roots",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 15,
            summary = "Solve quadratic equations by factorization, completing the square, formula, and Vieta's root relationships.",
            sections = listOf(
                LessonSection(
                    heading = "1. Discriminant & Nature of Roots",
                    body = "For ax² + bx + c = 0 (a ≠ 0), the discriminant Δ = b² - 4ac tells us the nature of the roots without solving the equation.",
                    keyPoints = listOf(
                        "Δ > 0 → two distinct real roots.",
                        "Δ = 0 → equal / repeated real roots (perfect square).",
                        "Δ < 0 → no real roots (complex roots).",
                    ),
                ),
                LessonSection(
                    heading = "2. Sum and Product of Roots",
                    body = "If α and β are roots of ax² + bx + c = 0, you can compute α² + β² or 1/α + 1/β directly using α + β = -b/a and αβ = c/a.",
                    keyPoints = listOf(
                        "α + β = -b / a",
                        "αβ = c / a",
                        "α² + β² = (α + β)² - 2αβ",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "x = [-b ± √(b² - 4ac)] / (2a)",
                "α + β = -b/a  |  αβ = c/a",
                "Equation from roots: x² - (α + β)x + αβ = 0",
            ),
            workedExample = "Find the quadratic equation whose roots are 2 and -5.\nSum = 2 + (-5) = -3; Product = 2 × (-5) = -10.\nEquation: x² - (-3)x + (-10) = 0 ⇒ x² + 3x - 10 = 0.",
        ),
        seedTopic(
            id = "mth_trig",
            subjectId = "mth",
            title = "Trigonometry, Bearings & Mensuration",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 16,
            summary = "Apply SOHCAHTOA, special angles, sine/cosine rules, three-figure bearings, and solid mensuration formulas.",
            sections = listOf(
                LessonSection(
                    heading = "1. Right-Angled Triangles & Special Angles",
                    body = "Memorize exact surd values for 30°, 45°, and 60° because JAMB UTME prohibits scientific calculators with trig functions.",
                    keyPoints = listOf(
                        "sin 30° = cos 60° = 1/2; cos 30° = sin 60° = √3/2.",
                        "sin 45° = cos 45° = √2/2; tan 45° = 1.",
                        "Pythagorean identity: sin²θ + cos²θ = 1.",
                    ),
                ),
                LessonSection(
                    heading = "2. Sine Rule, Cosine Rule & Mensuration",
                    body = "Use the Sine Rule when given two angles and one side (AAS/ASA) or two sides and a non-included angle. Use the Cosine Rule for SAS or SSS triangles.",
                    keyPoints = listOf(
                        "Area of triangle = ½ab sin C.",
                        "Volume of cylinder = πr²h; Volume of cone = ⅓πr²h.",
                        "Total surface area of closed cylinder = 2πr(r + h).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "a / sin A = b / sin B = c / sin C",
                "a² = b² + c² - 2bc cos A",
                "Sector Area = (θ/360) × πr²  |  Arc Length = (θ/360) × 2πr",
            ),
            workedExample = "If sin θ = 3/5 and θ is acute, find tan θ.\nOpposite = 3, Hypotenuse = 5 ⇒ Adjacent = √(5² - 3²) = 4.\nTherefore, tan θ = Opposite / Adjacent = 3/4.",
        ),
    )

    fun questions(): List<QuestionEntity> = listOf(
        // English Concord
        seedQuestion(
            id = "q_eng_1", subjectId = "eng", topicId = "eng_concord", examTag = "JAMB",
            prompt = "Neither the biology teacher nor the students ____ aware of the timetable change.",
            options = listOf("was", "is", "were", "has been"),
            correctIndex = 2,
            explanation = "With 'neither...nor', the verb agrees with the nearer subject ('the students', plural), so 'were' is correct.",
        ),
        seedQuestion(
            id = "q_eng_2", subjectId = "eng", topicId = "eng_concord", examTag = "WAEC",
            prompt = "The Principal, together with all the heads of department, ____ inspecting the science laboratory.",
            options = listOf("are", "were", "is", "have been"),
            correctIndex = 2,
            explanation = "Phrases introduced by 'together with' are parenthetical; the verb agrees with the main singular subject 'The Principal'.",
        ),
        seedQuestion(
            id = "q_eng_3", subjectId = "eng", topicId = "eng_concord", examTag = "NECO",
            prompt = "It is high time the candidates ____ revising for the UTME.",
            options = listOf("start", "started", "are starting", "have started"),
            correctIndex = 1,
            explanation = "The expression 'It is high time + subject' takes the past subjunctive form ('started') to indicate urgency.",
        ),
        seedQuestion(
            id = "q_eng_4", subjectId = "eng", topicId = "eng_concord", examTag = "JAMB",
            prompt = "The number of applicants for Medicine this year ____ unprecedented.",
            options = listOf("are", "were", "is", "have been"),
            correctIndex = 2,
            explanation = "'The number of + plural noun' refers to a single figure/quantity and therefore takes a singular verb ('is').",
        ),
        // English Lexis
        seedQuestion(
            id = "q_eng_5", subjectId = "eng", topicId = "eng_lexis", examTag = "WAEC",
            prompt = "My aunt congratulated me ____ my distinction in Mathematics.",
            options = listOf("for", "about", "on", "with"),
            correctIndex = 2,
            explanation = "In standard English collocation, 'congratulate' always takes the preposition 'on' (or 'upon'), never 'for'.",
        ),
        seedQuestion(
            id = "q_eng_6", subjectId = "eng", topicId = "eng_lexis", examTag = "JAMB",
            prompt = "No sooner had the invigilator entered the hall ____ silence fell everywhere.",
            options = listOf("when", "than", "then", "that"),
            correctIndex = 1,
            explanation = "'No sooner' is a comparative correlative and must pair with 'than'.",
        ),
        seedQuestion(
            id = "q_eng_7", subjectId = "eng", topicId = "eng_lexis", examTag = "NECO",
            prompt = "After noticing the altered figures on the ledger, the auditor began to smell a rat. This means the auditor ____.",
            options = listOf("caught a rodent", "became suspicious of foul play", "resigned immediately", "lost his sense of smell"),
            correctIndex = 1,
            explanation = "'To smell a rat' is an idiom meaning to suspect deceit, trickery, or something wrong.",
        ),
        seedQuestion(
            id = "q_eng_8", subjectId = "eng", topicId = "eng_lexis", examTag = "WAEC",
            prompt = "The hostel environment is not conducive ____ serious academic study.",
            options = listOf("for", "with", "to", "in"),
            correctIndex = 2,
            explanation = "The adjective 'conducive' is strictly followed by the preposition 'to'.",
        ),
        // English Oral
        seedQuestion(
            id = "q_eng_9", subjectId = "eng", topicId = "eng_oral", examTag = "JAMB",
            prompt = "Choose the word that has the same vowel sound as the underlined letters in 'f**oo**d'.",
            options = listOf("book", "foot", "rude", "flood"),
            correctIndex = 2,
            explanation = "'food' and 'rude' share the long /uː/ vowel sound; 'book' and 'foot' have /ʊ/, while 'flood' has /ʌ/.",
        ),
        seedQuestion(
            id = "q_eng_10", subjectId = "eng", topicId = "eng_oral", examTag = "WAEC",
            prompt = "Identify the word with the primary stress on the SECOND syllable.",
            options = listOf("educate", "photographic", "university", "important"),
            correctIndex = 3,
            explanation = "im-POR-tant carries primary stress on the second syllable. 'ED-u-cate' (1st), 'pho-to-GRAPH-ic' (3rd), 'u-ni-VER-si-ty' (3rd).",
        ),
        seedQuestion(
            id = "q_eng_11", subjectId = "eng", topicId = "eng_oral", examTag = "NECO",
            prompt = "In which of the following words is the consonant 'b' silent?",
            options = listOf("timber", "subtle", "robust", "symbol"),
            correctIndex = 1,
            explanation = "In 'subtle' (/ˈsʌt.əl/), the letter 'b' is silent. In timber, robust, and symbol, 'b' is pronounced.",
        ),
        seedQuestion(
            id = "q_eng_12", subjectId = "eng", topicId = "eng_oral", examTag = "JAMB",
            prompt = "Emphatic stress: 'Tunde bought a BLUE textbook yesterday.' Which question fits this response?",
            options = listOf(
                "Did Musa buy a blue textbook yesterday?",
                "Did Tunde borrow a blue textbook yesterday?",
                "Did Tunde buy a red textbook yesterday?",
                "Did Tunde buy a blue notebook yesterday?",
            ),
            correctIndex = 2,
            explanation = "Because 'BLUE' is stressed, the question must contrast the colour ('red') while keeping Tunde, bought, textbook, and yesterday unchanged.",
        ),
        // Maths Indices
        seedQuestion(
            id = "q_mth_1", subjectId = "mth", topicId = "mth_indices", examTag = "JAMB",
            prompt = "Solve for x if 3^(2x - 1) = 27.",
            options = listOf("1", "2", "3", "4"),
            correctIndex = 1,
            explanation = "Rewrite 27 as 3³. Equating exponents: 2x - 1 = 3 ⇒ 2x = 4 ⇒ x = 2.",
        ),
        seedQuestion(
            id = "q_mth_2", subjectId = "mth", topicId = "mth_indices", examTag = "WAEC",
            prompt = "Evaluate log₁₀(25) + log₁₀(4).",
            options = listOf("1", "2", "10", "100"),
            correctIndex = 1,
            explanation = "Using the product rule: log₁₀(25 × 4) = log₁₀(100) = log₁₀(10²) = 2.",
        ),
        seedQuestion(
            id = "q_mth_3", subjectId = "mth", topicId = "mth_indices", examTag = "NECO",
            prompt = "Simplify 6 / (√5 - √2) by rationalizing the denominator.",
            options = listOf("2(√5 + √2)", "2(√5 - √2)", "3(√5 + √2)", "√5 + √2"),
            correctIndex = 0,
            explanation = "Multiply top and bottom by (√5 + √2): 6(√5 + √2) / (5 - 2) = 6(√5 + √2) / 3 = 2(√5 + √2).",
        ),
        seedQuestion(
            id = "q_mth_4", subjectId = "mth", topicId = "mth_indices", examTag = "JAMB",
            prompt = "Simplify (16)^(-3/4).",
            options = listOf("8", "1/8", "-8", "1/4"),
            correctIndex = 1,
            explanation = "16^(-3/4) = 1 / (⁴√16)³ = 1 / 2³ = 1/8.",
        ),
        // Maths Quadratics
        seedQuestion(
            id = "q_mth_5", subjectId = "mth", topicId = "mth_quadratics", examTag = "WAEC",
            prompt = "If α and β are the roots of 2x² - 5x + 3 = 0, find the value of α + β.",
            options = listOf("-5/2", "5/2", "3/2", "-3/2"),
            correctIndex = 1,
            explanation = "Sum of roots α + β = -b/a = -(-5)/2 = 5/2.",
        ),
        seedQuestion(
            id = "q_mth_6", subjectId = "mth", topicId = "mth_quadratics", examTag = "JAMB",
            prompt = "For what value of k does x² + kx + 9 = 0 have equal roots?",
            options = listOf("3 only", "±6", "±3", "9"),
            correctIndex = 1,
            explanation = "Equal roots require b² - 4ac = 0 ⇒ k² - 4(1)(9) = 0 ⇒ k² = 36 ⇒ k = ±6.",
        ),
        seedQuestion(
            id = "q_mth_7", subjectId = "mth", topicId = "mth_quadratics", examTag = "NECO",
            prompt = "Find the quadratic equation whose roots are 3 and -4.",
            options = listOf("x² + x - 12 = 0", "x² - x - 12 = 0", "x² + 7x + 12 = 0", "x² - 7x - 12 = 0"),
            correctIndex = 0,
            explanation = "Sum = 3 + (-4) = -1; Product = 3(-4) = -12. Equation: x² - (-1)x + (-12) = x² + x - 12 = 0.",
        ),
        seedQuestion(
            id = "q_mth_8", subjectId = "mth", topicId = "mth_quadratics", examTag = "WAEC",
            prompt = "If α and β are roots of x² - 4x + 2 = 0, find α² + β².",
            options = listOf("12", "16", "20", "8"),
            correctIndex = 0,
            explanation = "α + β = 4 and αβ = 2. Using α² + β² = (α + β)² - 2αβ = 4² - 2(2) = 16 - 4 = 12.",
        ),
        // Maths Trigonometry
        seedQuestion(
            id = "q_mth_9", subjectId = "mth", topicId = "mth_trig", examTag = "JAMB",
            prompt = "Given that cos θ = 5/13 and θ is acute, calculate tan θ.",
            options = listOf("12/13", "5/12", "12/5", "13/5"),
            correctIndex = 2,
            explanation = "Using Pythagoras (5-12-13 triple), opposite = √(13² - 5²) = 12. So tan θ = opposite/adjacent = 12/5.",
        ),
        seedQuestion(
            id = "q_mth_10", subjectId = "mth", topicId = "mth_trig", examTag = "WAEC",
            prompt = "Calculate the area of a sector of radius 7 cm subtending an angle of 90° at the centre. (Take π = 22/7)",
            options = listOf("38.5 cm²", "77.0 cm²", "19.25 cm²", "154.0 cm²"),
            correctIndex = 0,
            explanation = "Area = (90/360) × (22/7) × 7 × 7 = (1/4) × 154 = 38.5 cm².",
        ),
        seedQuestion(
            id = "q_mth_11", subjectId = "mth", topicId = "mth_trig", examTag = "NECO",
            prompt = "Evaluate sin 30° + cos 60° without using tables.",
            options = listOf("√3/2", "1", "1/2", "√3"),
            correctIndex = 1,
            explanation = "sin 30° = 1/2 and cos 60° = 1/2, so 1/2 + 1/2 = 1.",
        ),
        seedQuestion(
            id = "q_mth_12", subjectId = "mth", topicId = "mth_trig", examTag = "JAMB",
            prompt = "A cone has base radius 3 cm and vertical height 7 cm. Find its volume. (Take π = 22/7)",
            options = listOf("66 cm³", "198 cm³", "22 cm³", "132 cm³"),
            correctIndex = 0,
            explanation = "Volume of a cone = ⅓πr²h = (1/3) × (22/7) × 3² × 7 = 22 × 3 = 66 cm³.",
        ),
    )
}
