package com.example.data.seed

import com.example.data.local.QuestionEntity
import com.example.data.local.TopicEntity
import com.example.model.LessonSection

object ArtsCommercialSubjectsSeed {
    fun topics(): List<TopicEntity> = listOf(
        // Economics
        seedTopic(
            id = "eco_demand",
            subjectId = "eco",
            title = "Demand, Supply & Price Elasticity",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 13,
            summary = "Distinguish movement along a curve vs. shifts, market equilibrium, and price elasticity of demand calculations.",
            sections = listOf(
                LessonSection(
                    heading = "1. Movements vs. Shifts in Demand",
                    body = "A change in the commodity's own price causes a movement along the same demand curve (change in quantity demanded). Non-price factors like income, taste, population, or prices of substitutes shift the entire curve.",
                    keyPoints = listOf(
                        "Normal goods: demand rises when income rises; Inferior goods: demand falls when income rises.",
                        "Substitutes (e.g., Milo & Bournvita): positive cross elasticity.",
                        "Complements (e.g., car & petrol): negative cross elasticity.",
                    ),
                ),
                LessonSection(
                    heading = "2. Price Elasticity of Demand (PED)",
                    body = "PED measures responsiveness of quantity demanded to a percentage change in price. Ignore the negative sign when interpreting magnitude.",
                    keyPoints = listOf(
                        "PED > 1: Elastic demand (luxury goods, many substitutes).",
                        "PED < 1: Inelastic demand (salt, staple food, addictive goods).",
                        "PED = 1: Unitary elastic; PED = 0: Perfectly inelastic (vertical curve).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "PED = (% Change in Quantity Demanded) / (% Change in Price)",
                "PED = (ΔQ / Q₁) ÷ (ΔP / P₁)",
                "Equilibrium: Quantity Demanded (Qd) = Quantity Supplied (Qs)",
            ),
            workedExample = "If price rises from ₦100 to ₦120 (20% increase) and quantity demanded falls from 50 to 40 units (20% decrease), find PED.\nPED = 20% / 20% = 1.0 (Unitary elastic demand).",
        ),
        seedTopic(
            id = "eco_production",
            subjectId = "eco",
            title = "Theory of Production & Cost Curves",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 14,
            summary = "Analyze factors of production, law of diminishing returns, and short-run TC, AC, and MC relationships.",
            sections = listOf(
                LessonSection(
                    heading = "1. Law of Diminishing Marginal Returns",
                    body = "In the short run, at least one factor (e.g., land or machinery) is fixed. As successive units of a variable factor (labour) are added to fixed factors, Marginal Product (MP) eventually declines.",
                    keyPoints = listOf(
                        "Total Product (TP) is maximum when Marginal Product (MP) = 0.",
                        "Average Product (AP) = TP / L; MP = ΔTP / ΔL.",
                    ),
                ),
                LessonSection(
                    heading = "2. Short-Run Cost Structure",
                    body = "Total Cost (TC) splits into Fixed Cost (FC, independent of output like rent) and Variable Cost (VC, rises with output like raw materials).",
                    keyPoints = listOf(
                        "TC = FC + VC; AC = TC / Q = AFC + AVC.",
                        "MC = ΔTC / ΔQ; MC curve always cuts AC and AVC at their minimum points.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "TC = TFC + TVC  |  ATC = AFC + AVC",
                "MC = ΔTC / ΔQ  |  Profit (π) = TR - TC",
                "AP = TP / Variable Factor  |  MP = ΔTP / ΔL",
            ),
            workedExample = "A firm produces 10 units with Fixed Cost = ₦500 and Variable Cost = ₦1,500. Find Average Total Cost.\nTC = 500 + 1500 = ₦2,000.\nATC = 2000 / 10 = ₦200 per unit.",
        ),
        seedTopic(
            id = "eco_national",
            subjectId = "eco",
            title = "National Income, Money & Inflation",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 13,
            summary = "Compare GDP vs. GNP, income/output/expenditure accounting methods, and central bank monetary policy tools.",
            sections = listOf(
                LessonSection(
                    heading = "1. GDP, GNP & National Income Methods",
                    body = "Gross Domestic Product (GDP) counts output produced within Nigeria's borders regardless of owner nationality. GNP adds Net Factor Income from Abroad (NFIA).",
                    keyPoints = listOf(
                        "GNP = GDP + Net Factor Income from Abroad.",
                        "NNP = GNP - Depreciation (Capital Consumption).",
                        "Expenditure formula: Y = C + I + G + (X - M).",
                    ),
                ),
                LessonSection(
                    heading = "2. Inflation & Monetary Policy (CBN)",
                    body = "Demand-pull inflation occurs when aggregate demand exceeds supply ('too much money chasing too few goods'). The Central Bank of Nigeria (CBN) fights inflation via contractionary tools.",
                    keyPoints = listOf(
                        "Sell treasury bills via Open Market Operations (OMO) to mop up excess cash.",
                        "Raise Cash Reserve Ratio (CRR) and Monetary Policy Rate (MPR).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Y = C + I + G + (X - M)",
                "GNP = GDP + Net Factor Income from Abroad",
                "Per Capita Income = National Income / Total Population",
            ),
            workedExample = "Given C = ₦400m, I = ₦150m, G = ₦200m, Exports X = ₦80m, Imports M = ₦130m, find GDP.\nGDP = 400 + 150 + 200 + (80 - 130) = 750 - 50 = ₦700m.",
        ),
        // Government
        seedTopic(
            id = "gov_principles",
            subjectId = "gov",
            title = "Principles of Government, Democracy & Rule of Law",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 12,
            summary = "Examine sovereignty, separation of powers, Dicey's rule of law, and unitary vs. federal systems.",
            sections = listOf(
                LessonSection(
                    heading = "1. Rule of Law & Separation of Powers",
                    body = "Propounded by A.V. Dicey (1885), the Rule of Law rests on three pillars: Supremacy of the Law, Equality Before the Law, and Fundamental Human Rights.",
                    keyPoints = listOf(
                        "Montesquieu championed Separation of Powers to prevent tyranny across Legislature, Executive, and Judiciary.",
                        "Checks and balances ensure no arm becomes absolute.",
                    ),
                ),
                LessonSection(
                    heading = "2. Presidential vs. Parliamentary Systems",
                    body = "Nigeria uses a Presidential system (like the USA), where the President is both Head of State and Head of Government. Britain uses a Parliamentary system with collective ministerial responsibility.",
                    keyPoints = listOf(
                        "Presidential: strict separation of executive & legislature; fixed tenure.",
                        "Parliamentary: fusion of executive & legislature; Prime Minister leads cabinet.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "A.V. Dicey's Rule of Law = Supremacy + Equality + Civil Liberties",
                "Montesquieu = Separation of Powers (Executive, Legislature, Judiciary)",
                "Sovereignty = Supreme, absolute power of a state free from external control",
            ),
            workedExample = "Which principle prevents ministers from sitting in the Nigerian National Assembly at the same time?\nAnswer: Separation of Powers (characteristic of the Presidential system).",
        ),
        seedTopic(
            id = "gov_constitutions",
            subjectId = "gov",
            title = "Nigerian Constitutional Development (1922–1999)",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 15,
            summary = "Trace key milestones from Clifford (1922) through Lyttelton federalism (1954), Independence (1960), to the 1999 Constitution.",
            sections = listOf(
                LessonSection(
                    heading = "1. Colonial Constitutions (1922–1954)",
                    body = "Each colonial constitution marked a major structural shift in Nigerian governance and nationalist participation.",
                    keyPoints = listOf(
                        "Clifford (1922): Introduced the Elective Principle (3 seats Lagos, 1 Calabar); birthed NNDP (1923).",
                        "Richards (1946): Introduced Regionalism (North, West, East) and linked North & South in one Legislative Council.",
                        "Macpherson (1951): First constitution drafted after wide consultation with Nigerians.",
                        "Lyttelton (1954): Formally established true Federalism in Nigeria.",
                    ),
                ),
                LessonSection(
                    heading = "2. Independence & Republican Milestones",
                    body = "Nigeria became independent on October 1, 1960 (Queen Elizabeth II remained Head of State via Governor-General) and became a Republic on October 1, 1963.",
                    keyPoints = listOf(
                        "1963 Republican Constitution: Replaced the Queen with an indigenous ceremonial President (Nnamdi Azikiwe) and made the Nigerian Supreme Court the highest court (abolishing Privy Council appeals).",
                        "1979 & 1999 Constitutions: Adopted the Executive Presidential system.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "1922 Clifford = Elective Principle | 1946 Richards = Regionalism",
                "1954 Lyttelton = True Federalism | 1963 = Republic & Supreme Court Finality",
                "1979 & 1999 = Executive Presidential System",
            ),
            workedExample = "Which Nigerian constitution formally introduced federalism with Exclusive, Concurrent, and Residual lists?\nAnswer: The Lyttelton Constitution of 1954.",
        ),
        seedTopic(
            id = "gov_federalism",
            subjectId = "gov",
            title = "Federalism, Local Government & ECOWAS",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 12,
            summary = "Understand legislative lists, the 1976 Local Government Reform, and Nigeria's role in ECOWAS and the AU.",
            sections = listOf(
                LessonSection(
                    heading = "1. Legislative Lists & 1976 Local Government Reform",
                    body = "In Nigerian federalism, Exclusive List items (currency, defence, foreign affairs) belong solely to the Federal Government; Concurrent List (education, health) is shared; Residual List belongs to States.",
                    keyPoints = listOf(
                        "1976 Local Government Reform established local governments as the third tier of government nationwide.",
                        "Federal Character Principle promotes equitable representation across states.",
                    ),
                ),
                LessonSection(
                    heading = "2. ECOWAS & International Organizations",
                    body = "ECOWAS (Economic Community of West African States) was founded on May 28, 1975 in Lagos (Treaty of Lagos) largely through Nigeria and Togo's leadership.",
                    keyPoints = listOf(
                        "ECOWAS HQ: Abuja, Nigeria (15 member states; promotes free movement & trade).",
                        "ECOMOG: ECOWAS peacekeeping monitoring group.",
                        "OAU (1963) transformed into African Union (AU) in 2002.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Exclusive List = Federal Only | Concurrent = Federal + State (Federal prevails on conflict)",
                "ECOWAS Founded = May 28, 1975 (Treaty of Lagos) | HQ = Abuja",
                "1976 Reform = Standardized 3rd Tier Local Government",
            ),
            workedExample = "If a State law on a Concurrent List matter conflicts with a valid Federal Act, which prevails?\nAnswer: The Federal Act prevails under the Doctrine of Covering the Field.",
        ),
        // Literature-in-English
        seedTopic(
            id = "lit_devices",
            subjectId = "lit",
            title = "Figures of Speech & Poetic Sound Devices",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 12,
            summary = "Identify metaphor, oxymoron, irony, synecdoche, metonymy, and sound devices in WAEC and UTME passages.",
            sections = listOf(
                LessonSection(
                    heading = "1. Comparison & Contrast Devices",
                    body = "Exam questions frequently present a short line and ask you to name the exact figure of speech.",
                    keyPoints = listOf(
                        "Oxymoron places two contradictory words side by side ('bitter-sweet', 'open secret', 'living dead').",
                        "Paradox is an apparently absurd statement that holds deeper truth ('The child is father of the man').",
                        "Metonymy substitutes an associated object ('The Crown' = King; 'The Pen' = writer), while Synecdoche uses a part for the whole ('All hands on deck').",
                    ),
                ),
                LessonSection(
                    heading = "2. Sound & Rhythm Devices",
                    body = "Sound devices enrich poetry's musicality and mood.",
                    keyPoints = listOf(
                        "Alliteration: repetition of initial consonant sounds ('fair is foul').",
                        "Assonance: repetition of internal vowel sounds ('slow low road').",
                        "Onomatopoeia: words imitating natural sounds ('buzz', 'clang', 'hiss').",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Oxymoron = 2 adjacent opposite words | Paradox = self-contradictory true statement",
                "Synecdoche = Part ↔ Whole ('wheels' for car) | Metonymy = Close association ('White House' for US govt)",
                "Euphemism = Mild expression for harsh reality ('passed away')",
            ),
            workedExample = "Identify the device in: 'The pen is mightier than the sword.'\nBoth 'pen' (representing writing/diplomacy) and 'sword' (representing warfare/force) substitute associated objects.\nAnswer: Metonymy.",
        ),
        seedTopic(
            id = "lit_prose_drama",
            subjectId = "lit",
            title = "Elements of Prose, Drama & Tragedy",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 13,
            summary = "Master plot structure, dramatic irony, tragic flaw (hamartia), catharsis, and narrative point of view.",
            sections = listOf(
                LessonSection(
                    heading = "1. Dramatic Conventions & Aristotelian Tragedy",
                    body = "Drama is written for stage performance using dialogue and stage directions. In classical tragedy, a noble hero falls due to a tragic flaw (hamartia), evoking pity and fear (catharsis) in the audience.",
                    keyPoints = listOf(
                        "Soliloquy: character speaks thoughts aloud alone on stage.",
                        "Aside: brief remark heard by audience but not other characters.",
                        "Dramatic Irony: audience knows a crucial fact that characters on stage do not.",
                    ),
                ),
                LessonSection(
                    heading = "2. Prose Structure & Narrative Technique",
                    body = "Fictional prose relies on plot, setting, characterization, and point of view.",
                    keyPoints = listOf(
                        "Foreshadowing: hints about future plot events; Flashback: jumping back in time.",
                        "Omniscient narrator: knows every character's thoughts; First-person ('I'): limited perspective.",
                        "Bildungsroman: a coming-of-age novel tracing moral/psychological growth.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Hamartia = Tragic Flaw | Catharsis = Emotional Purgation (Pity & Fear)",
                "Protagonist = Central Hero | Foil = Contrasting Character",
                "Climax = Peak Conflict Turning Point | Denouement = Final Resolution",
            ),
            workedExample = "When a character speaks alone on stage to reveal inner conflict directly to the audience, what is it called?\nAnswer: Soliloquy (unlike a monologue, where other characters may be listening).",
        ),
        seedTopic(
            id = "lit_appreciation",
            subjectId = "lit",
            title = "Literary Appreciation & Unseen Poetry",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 12,
            summary = "Determine poem tone, mood, rhyme scheme, stanza forms, and persona from unseen texts.",
            sections = listOf(
                LessonSection(
                    heading = "1. Tone, Mood & Rhyme Scheme",
                    body = "Tone reflects the speaker's (persona's) attitude (e.g., nostalgic, satirical, elegiac), whereas mood is the atmosphere felt by the reader. Rhyme scheme maps matching end sounds using letters (abab, abba, aabb).",
                    keyPoints = listOf(
                        "Shakespearean Sonnet: 14 lines in iambic pentameter, rhyme scheme abab cdcd efef gg.",
                        "Elegy: mournful poem lamenting the dead; Ode: lyrical poem praising a subject.",
                        "Enjambment (run-on line): sentence flows across a line break without punctuation.",
                    ),
                ),
                LessonSection(
                    heading = "2. Stanza Forms & Satire",
                    body = "Recognize standard stanza lengths and satirical tone in unseen passages.",
                    keyPoints = listOf(
                        "Couplet (2 lines), Tercet (3), Quatrain (4), Sestet (6), Octave (8).",
                        "Satire uses wit, irony, and exaggeration to ridicule societal folly for reform.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Sonnet = 14 Lines (Octave 8 + Sestet 6, or 3 Quatrains + Heroic Couplet)",
                "Tone = Poet/Persona's Attitude | Mood = Reader's Emotional Atmosphere",
                "Enjambment = Continuation of syntax beyond line ending without pause",
            ),
            workedExample = "A 14-line poem divided into an octave (abbaabba) and a sestet (cdecde) is called a ____.\nAnswer: Petrarchan (or Italian) Sonnet.",
        ),
    )

    fun questions(): List<QuestionEntity> = listOf(
        // Economics
        seedQuestion(
            id = "q_eco_1", subjectId = "eco", topicId = "eco_demand", examTag = "JAMB",
            prompt = "A change in the quantity demanded of rice due to a fall in its own price is shown as a ____.",
            options = listOf("rightward shift of the demand curve", "movement along the same demand curve", "leftward shift of the supply curve", "vertical demand curve"),
            correctIndex = 1,
            explanation = "Changes in the good's own price cause movements along the existing demand curve; non-price factors shift the curve.",
        ),
        seedQuestion(
            id = "q_eco_2", subjectId = "eco", topicId = "eco_demand", examTag = "WAEC",
            prompt = "When the price of a commodity rises by 10% and quantity demanded falls by 25%, demand is said to be ____.",
            options = listOf("perfectly inelastic", "inelastic", "elastic", "unitary elastic"),
            correctIndex = 2,
            explanation = "PED = 25% / 10% = 2.5. Since PED > 1, demand is price elastic.",
        ),
        seedQuestion(
            id = "q_eco_3", subjectId = "eco", topicId = "eco_demand", examTag = "NECO",
            prompt = "Two goods X and Y are complements if an increase in the price of X causes ____.",
            options = listOf("an increase in demand for Y", "a decrease in demand for Y", "no change in demand for Y", "Y to become inferior"),
            correctIndex = 1,
            explanation = "Complements are used together (e.g., printer & ink); when X becomes costlier, demand for Y falls.",
        ),
        seedQuestion(
            id = "q_eco_4", subjectId = "eco", topicId = "eco_production", examTag = "JAMB",
            prompt = "When Marginal Product (MP) is zero, Total Product (TP) is ____.",
            options = listOf("zero", "falling rapidly", "at its maximum", "equal to Average Product"),
            correctIndex = 2,
            explanation = "Total Product rises as long as MP > 0, peaks at its maximum when MP = 0, and declines once MP becomes negative.",
        ),
        seedQuestion(
            id = "q_eco_5", subjectId = "eco", topicId = "eco_production", examTag = "WAEC",
            prompt = "Which of the following is an example of a Fixed Cost in the short run?",
            options = listOf("Wages of casual workers", "Raw materials", "Factory rent", "Electricity per unit produced"),
            correctIndex = 2,
            explanation = "Factory rent does not vary with output level in the short run, making it a fixed cost.",
        ),
        seedQuestion(
            id = "q_eco_6", subjectId = "eco", topicId = "eco_production", examTag = "NECO",
            prompt = "At output = 5 units, Total Cost is ₦250; at output = 6 units, Total Cost is ₦310. Find the Marginal Cost of the 6th unit.",
            options = listOf("₦50", "₦60", "₦310", "₦51.67"),
            correctIndex = 1,
            explanation = "MC = ΔTC / ΔQ = (310 - 250) / (6 - 5) = ₦60.",
        ),
        seedQuestion(
            id = "q_eco_7", subjectId = "eco", topicId = "eco_national", examTag = "JAMB",
            prompt = "Gross National Product (GNP) equals Gross Domestic Product (GDP) plus ____.",
            options = listOf("depreciation", "indirect taxes", "net factor income from abroad", "subsidies"),
            correctIndex = 2,
            explanation = "GNP = GDP + Net Factor Income from Abroad (income earned by citizens abroad minus income earned by foreigners domestically).",
        ),
        seedQuestion(
            id = "q_eco_8", subjectId = "eco", topicId = "eco_national", examTag = "WAEC",
            prompt = "Which monetary policy action can the Central Bank of Nigeria take to curb inflation?",
            options = listOf("Lower the bank rate", "Buy treasury bills from the public", "Sell treasury bills via Open Market Operations", "Reduce the cash reserve ratio"),
            correctIndex = 2,
            explanation = "Selling securities via OMO withdraws excess liquidity from commercial banks and reduces money supply.",
        ),
        seedQuestion(
            id = "q_eco_9", subjectId = "eco", topicId = "eco_national", examTag = "NECO",
            prompt = "If a country's National Income is ₦800 billion and its population is 40 million, what is the per capita income?",
            options = listOf("₦2,000", "₦20,000", "₦200,000", "₦32,000"),
            correctIndex = 1,
            explanation = "Per Capita Income = 800,000,000,000 / 40,000,000 = ₦20,000.",
        ),
        // Government
        seedQuestion(
            id = "q_gov_1", subjectId = "gov", topicId = "gov_principles", examTag = "JAMB",
            prompt = "Who popularized the concept of the Rule of Law in 1885?",
            options = listOf("Baron de Montesquieu", "A.V. Dicey", "Jean-Jacques Rousseau", "John Locke"),
            correctIndex = 1,
            explanation = "Professor A.V. Dicey formulated the modern doctrine of the Rule of Law in 'Introduction to the Study of the Law of the Constitution' (1885).",
        ),
        seedQuestion(
            id = "q_gov_2", subjectId = "gov", topicId = "gov_principles", examTag = "WAEC",
            prompt = "Collective responsibility of cabinet ministers is a defining feature of which system of government?",
            options = listOf("Presidential system", "Parliamentary system", "Confederal system", "Military regime"),
            correctIndex = 1,
            explanation = "In a parliamentary system, cabinet ministers are collectively accountable to parliament for government policy.",
        ),
        seedQuestion(
            id = "q_gov_3", subjectId = "gov", topicId = "gov_principles", examTag = "NECO",
            prompt = "Separation of powers as propounded by Montesquieu is primarily designed to ____.",
            options = listOf("abolish political parties", "prevent concentration of power and tyranny", "eliminate the judiciary", "increase taxation"),
            correctIndex = 1,
            explanation = "Dividing legislative, executive, and judicial authority prevents abuse of absolute power and protects liberty.",
        ),
        seedQuestion(
            id = "q_gov_4", subjectId = "gov", topicId = "gov_constitutions", examTag = "JAMB",
            prompt = "Which Nigerian constitution introduced the Elective Principle for the first time in 1922?",
            options = listOf("Richards Constitution", "Clifford Constitution", "Macpherson Constitution", "Lyttelton Constitution"),
            correctIndex = 1,
            explanation = "The 1922 Clifford Constitution introduced the elective principle (3 seats for Lagos, 1 for Calabar).",
        ),
        seedQuestion(
            id = "q_gov_5", subjectId = "gov", topicId = "gov_constitutions", examTag = "WAEC",
            prompt = "Which constitution formally established true federalism in Nigeria?",
            options = listOf("Clifford Constitution of 1922", "Richards Constitution of 1946", "Lyttelton Constitution of 1954", "Independence Constitution of 1960"),
            correctIndex = 2,
            explanation = "The 1954 Lyttelton Constitution created clear division of powers between the central and regional governments.",
        ),
        seedQuestion(
            id = "q_gov_6", subjectId = "gov", topicId = "gov_constitutions", examTag = "NECO",
            prompt = "Under the 1963 Republican Constitution, Nigeria's highest court of appeal became the ____.",
            options = listOf("Judicial Committee of the Privy Council", "Federal Court of Appeal", "Supreme Court of Nigeria", "West African Court of Appeal"),
            correctIndex = 2,
            explanation = "The 1963 Republican Constitution abolished appeals to the Privy Council in London and made the Supreme Court final.",
        ),
        seedQuestion(
            id = "q_gov_7", subjectId = "gov", topicId = "gov_federalism", examTag = "JAMB",
            prompt = "Where is the headquarters of the Economic Community of West African States (ECOWAS) located?",
            options = listOf("Lagos", "Accra", "Abuja", "Dakar"),
            correctIndex = 2,
            explanation = "Although ECOWAS was founded by the 1975 Treaty of Lagos, its permanent Commission headquarters is in Abuja, Nigeria.",
        ),
        seedQuestion(
            id = "q_gov_8", subjectId = "gov", topicId = "gov_federalism", examTag = "WAEC",
            prompt = "Which reform made Local Governments a uniform third tier of government across Nigeria?",
            options = listOf("1967 State Creation", "1976 Local Government Reform", "1988 Civil Service Reform", "1999 Constitutional Amendment"),
            correctIndex = 1,
            explanation = "The 1976 Local Government Reform standardized local government structure and funding nationwide.",
        ),
        seedQuestion(
            id = "q_gov_9", subjectId = "gov", topicId = "gov_federalism", examTag = "NECO",
            prompt = "Defence, currency, and foreign affairs in Nigeria fall under the ____.",
            options = listOf("Residual List", "Concurrent List", "Exclusive Legislative List", "State Assembly List"),
            correctIndex = 2,
            explanation = "Items vital to national sovereignty such as defence and currency belong exclusively to the Federal Government.",
        ),
        // Literature-in-English
        seedQuestion(
            id = "q_lit_1", subjectId = "lit", topicId = "lit_devices", examTag = "JAMB",
            prompt = "'O loving hate! O heavy lightness!' This line illustrates which figure of speech?",
            options = listOf("Simile", "Oxymoron", "Euphemism", "Synecdoche"),
            correctIndex = 1,
            explanation = "Placing contradictory words side by side ('loving hate', 'heavy lightness') is an oxymoron.",
        ),
        seedQuestion(
            id = "q_lit_2", subjectId = "lit", topicId = "lit_devices", examTag = "WAEC",
            prompt = "'All hands must be on deck to finish the harvest.' Here, 'hands' is an example of ____.",
            options = listOf("Metonymy", "Synecdoche", "Hyperbole", "Personification"),
            correctIndex = 1,
            explanation = "Synecdoche uses a part ('hands') to represent the whole person/workers.",
        ),
        seedQuestion(
            id = "q_lit_3", subjectId = "lit", topicId = "lit_devices", examTag = "NECO",
            prompt = "Repetition of identical vowel sounds within nearby words in a line of poetry is called ____.",
            options = listOf("Alliteration", "Assonance", "Onomatopoeia", "Pun"),
            correctIndex = 1,
            explanation = "Assonance is internal vowel repetition, whereas alliteration repeats initial consonant sounds.",
        ),
        seedQuestion(
            id = "q_lit_4", subjectId = "lit", topicId = "lit_prose_drama", examTag = "JAMB",
            prompt = "In classical tragedy, the emotional purging of pity and fear experienced by the audience is called ____.",
            options = listOf("Hamartia", "Hubris", "Catharsis", "Anagnorisis"),
            correctIndex = 2,
            explanation = "Aristotle defined catharsis as the purification/purging of pity and fear; hamartia is the tragic flaw.",
        ),
        seedQuestion(
            id = "q_lit_5", subjectId = "lit", topicId = "lit_prose_drama", examTag = "WAEC",
            prompt = "When the audience knows a secret that a character on stage is unaware of, this creates ____.",
            options = listOf("Verbal irony", "Dramatic irony", "Flashback", "Epilogue"),
            correctIndex = 1,
            explanation = "Dramatic irony occurs when the audience possesses crucial knowledge hidden from the characters.",
        ),
        seedQuestion(
            id = "q_lit_6", subjectId = "lit", topicId = "lit_prose_drama", examTag = "NECO",
            prompt = "A character who contrasts sharply with the protagonist to highlight the protagonist's traits is a ____.",
            options = listOf("Confidant", "Foil", "Chorus", "Flat narrator"),
            correctIndex = 1,
            explanation = "A foil contrasts with the protagonist (e.g., Laertes as a foil to Hamlet) to emphasize key qualities.",
        ),
        seedQuestion(
            id = "q_lit_7", subjectId = "lit", topicId = "lit_appreciation", examTag = "JAMB",
            prompt = "A poem of fourteen lines arranged in three quatrains and a concluding rhyming couplet is a ____.",
            options = listOf("Petrarchan sonnet", "Shakespearean sonnet", "Ballad", "Pastoral elegy"),
            correctIndex = 1,
            explanation = "A Shakespearean (English) sonnet follows abab cdcd efef gg (3 quatrains + 1 heroic couplet).",
        ),
        seedQuestion(
            id = "q_lit_8", subjectId = "lit", topicId = "lit_appreciation", examTag = "WAEC",
            prompt = "When the sense of a poetic line runs continuously into the next line without end punctuation, it is ____.",
            options = listOf("Caesura", "Enjambment", "Refrain", "Apostrophe"),
            correctIndex = 1,
            explanation = "Enjambment (run-on line) carries syntax across a line break without a pause.",
        ),
        seedQuestion(
            id = "q_lit_9", subjectId = "lit", topicId = "lit_appreciation", examTag = "NECO",
            prompt = "A mournful poem composed to lament the death of a loved one is called an ____.",
            options = listOf("Ode", "Elegy", "Epic", "Epigram"),
            correctIndex = 1,
            explanation = "An elegy is a solemn, reflective poem mourning someone's death.",
        ),
    )
}
