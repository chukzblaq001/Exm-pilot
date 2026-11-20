package com.example.data.seed

import com.example.data.local.QuestionEntity
import com.example.data.local.TopicEntity
import com.example.model.LessonSection

object ExpandedSubjectsSeed {
    fun topics(): List<TopicEntity> = listOf(
        // Exam-specific English topics
        seedTopic(
            id = "eng_essay_summary",
            subjectId = "eng",
            title = "Essay Writing, Letters & Summary Skills",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 4,
            estimatedMinutes = 14,
            summary = "Structure WAEC/NECO formal letters, argumentative essays, and concise topic-sentence summaries without lifting verbatim.",
            sections = listOf(
                LessonSection(
                    heading = "1. Formal vs. Semi-Formal Letter Conventions",
                    body = "WASSCE and NECO Paper 1 deduct mechanical accuracy marks for incorrect salutations, missing writer/recipient addresses, and mismatched subscriptions.",
                    keyPoints = listOf(
                        "Formal letter: two addresses (writer top-right, recipient top-left), 'Dear Sir/Madam', centred/underlined heading, 'Yours faithfully' + signature + full name.",
                        "Informal letter: writer's address only, 'Dear Ada', 'Yours sincerely' + first name.",
                        "Target 450+ words organized into introduction, 3–4 cohesive body paragraphs, and conclusion.",
                    ),
                ),
                LessonSection(
                    heading = "2. Summary Writing Rules (Paper 2)",
                    body = "WAEC and NECO summary questions require complete grammatical sentences containing only the main point—never mindless lifting or preamble fragments.",
                    keyPoints = listOf(
                        "Write each point in one clear, complete sentence.",
                        "Exclude examples, illustrations, and parenthetical adjectives from summary sentences.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Formal Subscription: Yours faithfully + Signature + Full Name",
                "Summary Rule: Own words + Complete sentence + Zero preamble",
                "Minimum Essay Length: 450 words (WAEC & NECO standard)",
            ),
            workedExample = "Question: Summarize in two sentences why youths migrate to cities.\nGood Answer:\n1. Youths migrate to cities in search of better employment opportunities.\n2. They also relocate to access higher-quality educational and social amenities.",
        ),
        seedTopic(
            id = "eng_cloze_register",
            subjectId = "eng",
            title = "Register Cloze Passages & Comprehension Inference",
            examTagsCsv = "JAMB",
            orderIndex = 5,
            estimatedMinutes = 12,
            summary = "Master JAMB UTME domain-specific vocabulary registers (medicine, law, banking, agriculture) and rapid CBT comprehension inference.",
            sections = listOf(
                LessonSection(
                    heading = "1. Domain Register Cloze Tests",
                    body = "JAMB UTME opens with a comprehension passage and a 10-gap cloze passage drawn from specialized Nigerian and global fields like judiciary, banking, journalism, or healthcare.",
                    keyPoints = listOf(
                        "Read the entire passage once before picking options to lock in the field register.",
                        "Check grammatical class (noun vs. verb vs. participle) and collocation around each blank.",
                    ),
                ),
                LessonSection(
                    heading = "2. UTME Inference & Author's Tone",
                    body = "UTME comprehension questions rarely lift answers word-for-word; instead, they test implied meaning, contextual vocabulary replacement, and the writer's attitude.",
                    keyPoints = listOf(
                        "Substitute your chosen synonym back into the sentence to verify tone and grammar.",
                        "Distinguish satirical/ironic praise from genuine approval.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Judiciary Register: plaintiff, defendant, adjournment, subpoena, verdict",
                "Banking Register: collateral, overdraft, liquidity, ledger, interest",
                "Cloze Strategy = Context Meaning + Register Fit + Part of Speech",
            ),
            workedExample = "Passage gap: 'Because the suspect failed to appear in court, the magistrate issued a ____.'\nOptions: (A) dividend (B) subpoena (C) diagnosis (D) manifesto.\nAnswer: subpoena (legal writ ordering court appearance).",
        ),

        // Exam-specific Mathematics topics
        seedTopic(
            id = "mth_circle_theorems",
            subjectId = "mth",
            title = "Euclidean Geometry & Circle Theorems",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 4,
            estimatedMinutes = 16,
            summary = "Prove and apply angles at the centre, cyclic quadrilaterals, alternate segment theorem, and tangent properties for SSCE theory & objectives.",
            sections = listOf(
                LessonSection(
                    heading = "1. Inscribed & Cyclic Quadrilateral Theorems",
                    body = "Circle theorems are guaranteed marks in WAEC and NECO Paper 2. Always state the theorem reason in brackets when writing step-by-step geometry proofs.",
                    keyPoints = listOf(
                        "Angle at centre = 2 × angle at circumference subtended by the same arc.",
                        "Angle in a semicircle = 90°.",
                        "Opposite angles of a cyclic quadrilateral sum to 180°.",
                    ),
                ),
                LessonSection(
                    heading = "2. Tangents & Alternate Segment Theorem",
                    body = "A tangent meets a radius at 90° at the point of contact. Two tangents from an external point to a circle are equal in length.",
                    keyPoints = listOf(
                        "Alternate segment theorem: angle between tangent and chord equals angle in the alternate segment.",
                        "Angles in the same segment are equal.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Centre Angle = 2 × Circumference Angle",
                "Cyclic Quad: ∠A + ∠C = 180° | Exterior Angle = Interior Opposite Angle",
                "Radius ⊥ Tangent (90°) | Angle in Semicircle = 90°",
            ),
            workedExample = "In a cyclic quadrilateral PQRS, ∠P = 78°. Find ∠R.\nReason: Opposite angles of a cyclic quadrilateral are supplementary.\n∠R = 180° - 78° = 102°.",
        ),
        seedTopic(
            id = "mth_calculus_matrices",
            subjectId = "mth",
            title = "Matrices, Determinants & Elementary Calculus",
            examTagsCsv = "JAMB",
            orderIndex = 5,
            estimatedMinutes = 15,
            summary = "Compute 2×2 determinants, inverses, power-rule derivatives, stationary points, and definite integrals tested in JAMB UTME.",
            sections = listOf(
                LessonSection(
                    heading = "1. Matrices & 2×2 Determinants",
                    body = "JAMB UTME frequently tests matrix multiplication, singular matrices (det = 0), and finding the inverse of a 2×2 matrix.",
                    keyPoints = listOf(
                        "For A = [[a, b], [c, d]], det(A) = ad - bc.",
                        "A matrix is singular when det(A) = 0 (no inverse exists).",
                        "A⁻¹ = (1 / det(A)) × [[d, -b], [-c, a]].",
                    ),
                ),
                LessonSection(
                    heading = "2. Differentiation & Integration Basics",
                    body = "Use differentiation to find gradients, velocities, and maxima/minima (where dy/dx = 0). Integration reverses differentiation to find areas under curves.",
                    keyPoints = listOf(
                        "d/dx (axⁿ) = n·a·xⁿ⁻¹; derivative of a constant is 0.",
                        "∫ xⁿ dx = xⁿ⁺¹ / (n + 1) + C (n ≠ -1).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "det([[a,b],[c,d]]) = ad - bc | Singular ⇒ ad - bc = 0",
                "d/dx(xⁿ) = n·xⁿ⁻¹ | Turning point ⇒ dy/dx = 0",
                "∫ xⁿ dx = xⁿ⁺¹/(n+1) + C",
            ),
            workedExample = "Find the value of k if [[k, 3], [4, 6]] is singular.\nSet determinant = 0: 6k - (3 × 4) = 0 ⇒ 6k = 12 ⇒ k = 2.",
        ),

        // Exam-specific Science topics
        seedTopic(
            id = "bio_adaptation",
            subjectId = "bio",
            title = "Adaptation, Evolution & Practical Specimens",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 4,
            estimatedMinutes = 13,
            summary = "Identify structural adaptations, camouflage, and WAEC/NECO Alternative to Practical specimen classifications and food tests.",
            sections = listOf(
                LessonSection(
                    heading = "1. Food Tests & Practical Specimens",
                    body = "WAEC and NECO Biology papers examine reagent colour changes and adaptive features of common Nigerian specimens.",
                    keyPoints = listOf(
                        "Starch + Iodine solution → Blue-black colour.",
                        "Reducing sugar + Benedict's/Fehling's solution + heat → Brick-red precipitate.",
                        "Protein + Biuret reagent (CuSO₄ + NaOH) → Violet/purple colour.",
                    ),
                ),
                LessonSection(
                    heading = "2. Adaptive Features & Natural Selection",
                    body = "Organisms survive via structural, behavioural, or physiological adaptations such as xerophyte sunken stomata or chameleon camouflage.",
                    keyPoints = listOf(
                        "Xerophytes conserve water via waxy cuticles, spines, and reduced leaves.",
                        "Hydrophytes possess aerenchyma air spaces for buoyancy.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Starch = Iodine → Blue-Black | Protein = Biuret → Purple",
                "Reducing Sugar = Benedict's + Heat → Brick-Red",
                "Fats/Oils = Sudan III → Red | Grease Spot → Translucent mark",
            ),
            workedExample = "When Biuret reagent is added to egg albumen solution, what colour appears?\nAnswer: Violet/Purple, confirming the presence of peptide bonds (protein).",
        ),
        seedTopic(
            id = "chm_qualitative",
            subjectId = "chm",
            title = "Qualitative Salt Analysis & Volumetric Titration",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 4,
            estimatedMinutes = 14,
            summary = "Recognize cation/anion precipitates with NaOH and NH₃(aq), gas tests, and acid-base indicator end-points for SSCE practicals.",
            sections = listOf(
                LessonSection(
                    heading = "1. Cation Identification (NaOH & Aqueous NH₃)",
                    body = "Adding NaOH(aq) or NH₃(aq) dropwise then in excess distinguishes Zn²⁺, Al³⁺, Pb²⁺, Cu²⁺, Fe²⁺, Fe³⁺, and Ca²⁺.",
                    keyPoints = listOf(
                        "Cu²⁺ + NaOH → Light blue precipitate (insoluble in excess NaOH; dissolves in excess NH₃ to deep blue).",
                        "Fe²⁺ → Dirty green ppt; Fe³⁺ → Reddish-brown ppt.",
                        "Zn²⁺ forms white ppt soluble in excess NaOH AND excess NH₃(aq).",
                    ),
                ),
                LessonSection(
                    heading = "2. Gas Tests & Titration Indicators",
                    body = "Choose methyl orange for strong acid vs. weak base, and phenolphthalein for weak acid vs. strong base.",
                    keyPoints = listOf(
                        "CO₂ turns limewater milky; H₂ burns with a pop sound; NH₃ turns damp red litmus blue.",
                        "Methyl orange: pink/red in acid, yellow in base, orange at end-point.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Cu²⁺ + Excess NH₃(aq) → Deep blue solution",
                "SO₄²⁻ + BaCl₂ + dil. HCl → White ppt (BaSO₄)",
                "Cl⁻ + AgNO₃ + dil. HNO₃ → White curdy ppt (soluble in NH₃)",
            ),
            workedExample = "A salt solution gives a dirty green precipitate with NaOH(aq) that turns brown on standing in air. Identify the cation.\nAnswer: Fe²⁺ (oxidized slowly to Fe³⁺).",
        ),
        seedTopic(
            id = "phy_modern",
            subjectId = "phy",
            title = "Photoelectric Effect, Half-Life & Nuclear Physics",
            examTagsCsv = "JAMB",
            orderIndex = 4,
            estimatedMinutes = 14,
            summary = "Calculate threshold frequency, work function, Einstein's photoelectric equation, and radioactive decay half-life for JAMB UTME.",
            sections = listOf(
                LessonSection(
                    heading = "1. Quantum Energy & Photoelectric Emission",
                    body = "Electrons are ejected from a metal surface only when incident photon frequency f exceeds the threshold frequency f₀ (or energy hf > work function W₀).",
                    keyPoints = listOf(
                        "E = hf = hc / λ.",
                        "KE_max = hf - W₀ = hf - hf₀.",
                        "Stopping potential V_s satisfies e·V_s = KE_max.",
                    ),
                ),
                LessonSection(
                    heading = "2. Radioactive Half-Life & Decay",
                    body = "Half-life (T½) is the time taken for half of the unstable nuclei in a sample to decay. After n half-lives, remaining fraction is (1/2)ⁿ.",
                    keyPoints = listOf(
                        "n = Total time / T½.",
                        "Remaining mass N = N₀ × (1/2)ⁿ.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "KE_max = hf - W₀  |  W₀ = hf₀",
                "N = N₀ · (1/2)ⁿ  where n = t / T½",
                "Alpha (⁴₂He): A - 4, Z - 2 | Beta (⁰₋₁e): A unchanged, Z + 1",
            ),
            workedExample = "A radioactive sample of mass 80 g has a half-life of 5 days. What mass remains after 15 days?\nn = 15 / 5 = 3 half-lives. Remaining mass = 80 × (1/2)³ = 80 / 8 = 10 g.",
        ),

        // Civic Education (WAEC, NECO)
        seedTopic(
            id = "civ_values",
            subjectId = "civic",
            title = "National Values, Integrity & Citizenship",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 1,
            estimatedMinutes = 11,
            summary = "Understand honesty, contentment, citizenship acquisition under the 1999 Nigerian Constitution, and civic obligations.",
            sections = listOf(
                LessonSection(
                    heading = "1. Core Values & Selflessness",
                    body = "Civic Education promotes societal stability through honesty, discipline, tolerance, and selfless community service.",
                    keyPoints = listOf(
                        "Citizenship in Nigeria is acquired by Birth, Registration, or Naturalisation.",
                        "Dual citizenship is permitted for Nigerian citizens by birth.",
                    ),
                ),
                LessonSection(
                    heading = "2. Duties & Obligations of Citizens",
                    body = "Rights carry reciprocal civic duties: paying taxes, obeying laws, voting responsibly, and protecting public property.",
                    keyPoints = listOf(
                        "Respecting the National Anthem, Pledge, and Flag fosters unity.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Nigerian Citizenship Modes: Birth | Registration | Naturalisation",
                "Key Pillars of Integrity: Probity, Honesty, Accountability, Fairness",
            ),
            workedExample = "Under the 1999 Nigerian Constitution, which mode of citizenship requires at least 15 years of continuous residency and good character?\nAnswer: Citizenship by Naturalisation.",
        ),
        seedTopic(
            id = "civ_rights_road",
            subjectId = "civic",
            title = "Human Rights, Rule of Law & Road Safety",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 2,
            estimatedMinutes = 12,
            summary = "Examine fundamental human rights limitations, NAPTIP/human trafficking prevention, and FRSC traffic regulations.",
            sections = listOf(
                LessonSection(
                    heading = "1. Fundamental Human Rights & Emergency Limits",
                    body = "Chapter IV of the 1999 Constitution guarantees rights to life, dignity, fair hearing, and expression—subject to lawful limits during public emergencies or court convictions.",
                    keyPoints = listOf(
                        "NAPTIP combats human trafficking and child labour in Nigeria.",
                        "FRSC (Federal Road Safety Corps) enforces highway safety, speed limits, and seat-belt compliance.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "UDHR = Universal Declaration of Human Rights (Dec 10, 1948)",
                "FRSC = Federal Road Safety Corps | NAPTIP = Anti-Trafficking Agency",
            ),
            workedExample = "Which Nigerian agency is mandated to investigate and prosecute cases of human trafficking?\nAnswer: NAPTIP (National Agency for the Prohibition of Trafficking in Persons).",
        ),

        // Agricultural Science (WAEC, JAMB, NECO)
        seedTopic(
            id = "agr_soil",
            subjectId = "agr",
            title = "Soil Formation, Texture, pH & Fertility",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 13,
            summary = "Analyze physical/chemical weathering, soil profile horizons, NPK deficiency symptoms, and liming acidic soils.",
            sections = listOf(
                LessonSection(
                    heading = "1. Soil Types & Profile Horizons",
                    body = "Loamy soil combines balanced sand, silt, and clay with humus, making it ideal for crop cultivation. Soil profiles show Topsoil (Horizon A), Subsoil (Horizon B), Weathered parent material (C), and Bedrock (D).",
                    keyPoints = listOf(
                        "Clay retains water well but drains poorly; Sandy soil drains fast with low nutrient retention.",
                        "Agricultural lime (CaCO₃ / CaO) neutralizes excess soil acidity.",
                    ),
                ),
                LessonSection(
                    heading = "2. Macro-Nutrients (N, P, K)",
                    body = "Nitrogen promotes lush vegetative leaf growth; Phosphorus strengthens roots and flowering; Potassium aids tuber formation and disease resistance.",
                    keyPoints = listOf(
                        "Nitrogen deficiency causes chlorosis (yellowing of older leaves).",
                        "Crop rotation with legumes replenishes soil nitrates via Rhizobium.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Topsoil = Horizon A (highest humus) | Bedrock = Horizon D",
                "N = Leaf growth | P = Root & fruit | K = Tuber & disease resistance",
            ),
            workedExample = "Which farming practice naturally restores soil nitrogen while controlling soil-borne pests?\nAnswer: Crop rotation incorporating leguminous crops (e.g., cowpea, groundnut).",
        ),
        seedTopic(
            id = "agr_crops",
            subjectId = "agr",
            title = "Crop Husbandry, Pests & Disease Control",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 12,
            summary = "Classify West African cash/arable crops, viral/fungal diseases, and integrated pest management methods.",
            sections = listOf(
                LessonSection(
                    heading = "1. Major Nigerian Crops & Vectors",
                    body = "Know botanical/common names and vectors: Cassava mosaic virus is transmitted by whiteflies (Bemisia tabaci); Swollen shoot of cocoa by mealybugs.",
                    keyPoints = listOf(
                        "Black pod disease of cocoa is caused by a fungus (Phytophthora palmivora).",
                        "Cultural control includes early planting, rogueing infected plants, and resistant varieties.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Cassava Mosaic Vector = Whitefly | Cocoa Swollen Shoot Vector = Mealybug",
                "Storage Pest of Maize/Cowpea = Weevil (Sitophilus / Callosobruchus)",
            ),
            workedExample = "Which insect vector transmits the Cassava Mosaic Virus?\nAnswer: Whitefly.",
        ),

        // Further Mathematics (WAEC, NECO)
        seedTopic(
            id = "fmth_vectors",
            subjectId = "fmath",
            title = "Vectors, Dot Product & Motion in a Plane",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 1,
            estimatedMinutes = 15,
            summary = "Compute unit vectors, scalar (dot) products, angles between vectors, and relative velocity components.",
            sections = listOf(
                LessonSection(
                    heading = "1. Magnitude, Unit Vector & Scalar Product",
                    body = "For vector a = xi + yj, magnitude |a| = √(x² + y²), and unit vector â = a / |a|. Two non-zero vectors are perpendicular if and only if their dot product is zero.",
                    keyPoints = listOf(
                        "a · b = x₁x₂ + y₁y₂ = |a||b|cosθ.",
                        "Perpendicular vectors ⇒ a · b = 0.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "a · b = |a||b|cosθ = x₁x₂ + y₁y₂",
                "a ⊥ b ⇔ a · b = 0",
            ),
            workedExample = "Given u = 3i + 4j and v = 4i - 3j, find u · v.\nu · v = (3×4) + (4×-3) = 12 - 12 = 0 ⇒ vectors are perpendicular.",
        ),
        seedTopic(
            id = "fmth_probability",
            subjectId = "fmath",
            title = "Binomial Distribution & Conditional Probability",
            examTagsCsv = "WAEC,NECO",
            orderIndex = 2,
            estimatedMinutes = 14,
            summary = "Apply nCr combinations, binomial probability P(X = r), and conditional probability P(A|B).",
            sections = listOf(
                LessonSection(
                    heading = "1. Binomial Probability Formula",
                    body = "For n independent Bernoulli trials with success probability p and failure q = 1 - p, P(X = r) = ⁿC_r p^r q^(n-r).",
                    keyPoints = listOf(
                        "Mean μ = np; Variance σ² = npq.",
                        "Conditional probability: P(A|B) = P(A ∩ B) / P(B).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "P(X = r) = ⁿC_r · p^r · q^(n-r)  |  q = 1 - p",
                "Mean = np  |  Variance = npq  |  SD = √(npq)",
            ),
            workedExample = "A fair coin is tossed 4 times. Find the probability of getting exactly 2 heads.\nP(X=2) = ⁴C₂ × (1/2)² × (1/2)² = 6 × 1/16 = 3/8.",
        ),

        // Financial Accounting (WAEC, JAMB, NECO)
        seedTopic(
            id = "acc_double_entry",
            subjectId = "acc",
            title = "Double Entry, Cash Book & Trial Balance",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 14,
            summary = "Apply debit/credit rules, three-column cash books with contra entries, and identify errors revealed or hidden by a trial balance.",
            sections = listOf(
                LessonSection(
                    heading = "1. Golden Rules & Contra Entries",
                    body = "Every transaction has a dual effect: Debit the receiver / asset / expense; Credit the giver / liability / income. Contra entries (C) occur when cash is banked or withdrawn from bank for office use.",
                    keyPoints = listOf(
                        "Discount Allowed is debited; Discount Received is credited.",
                        "Errors NOT affecting Trial Balance: Omission, Commission, Principle, Original Entry, Compensating, Complete Reversal.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Accounting Equation: Assets = Capital + Liabilities",
                "Contra Entry (C): Cash deposited into Bank OR Cash withdrawn from Bank",
            ),
            workedExample = "Cash of ₦20,000 paid into the bank requires what entry in a three-column cash book?\nAnswer: Debit Bank column ₦20,000 and Credit Cash column ₦20,000 (Contra 'C').",
        ),
        seedTopic(
            id = "acc_final_accounts",
            subjectId = "acc",
            title = "Final Accounts, Adjustments & Ratios",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 15,
            summary = "Compute Cost of Goods Sold, Gross Profit, Net Profit, accruals/prepayments, and working capital ratios.",
            sections = listOf(
                LessonSection(
                    heading = "1. Trading & Profit/Loss Adjustments",
                    body = "Accrued expenses are added to current period expense (and shown as current liability); prepaid expenses are deducted (and shown as current asset).",
                    keyPoints = listOf(
                        "COGS = Opening Stock + Net Purchases - Closing Stock.",
                        "Gross Profit = Net Sales - COGS.",
                        "Working Capital = Current Assets - Current Liabilities.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Gross Profit = Sales - Returns Inwards - COGS",
                "Working Capital = Current Assets - Current Liabilities",
            ),
            workedExample = "If Opening Stock = ₦10,000, Purchases = ₦50,000, Closing Stock = ₦15,000, and Sales = ₦70,000, find Gross Profit.\nCOGS = 10,000 + 50,000 - 15,000 = ₦45,000.\nGross Profit = 70,000 - 45,000 = ₦25,000.",
        ),

        // Commerce (WAEC, JAMB, NECO)
        seedTopic(
            id = "com_trade",
            subjectId = "com",
            title = "Home Trade, Foreign Trade & Documents",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 12,
            summary = "Compare wholesale/retail channels, international trade barriers, and shipping documents like Bill of Lading and Proforma Invoice.",
            sections = listOf(
                LessonSection(
                    heading = "1. Trade Documents & Terms",
                    body = "Commerce facilitates the exchange of goods from producers to final consumers via trade and aids to trade (banking, insurance, transport, warehousing, advertising).",
                    keyPoints = listOf(
                        "Bill of Lading: document of title to goods shipped by sea.",
                        "Proforma Invoice: sent before goods are supplied to state terms/prices (also used for customs).",
                        "Balance of Trade = Visible Exports - Visible Imports.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Terms of Trade = (Export Price Index / Import Price Index) × 100",
                "FOB = Free On Board | CIF = Cost, Insurance & Freight",
            ),
            workedExample = "Which document serves as a contract of carriage, receipt for goods, and document of title in sea transport?\nAnswer: Bill of Lading.",
        ),
        seedTopic(
            id = "com_banking",
            subjectId = "com",
            title = "Banking, Insurance & Business Units",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 12,
            summary = "Distinguish sole proprietorship, limited liability companies, cheques, and insurance principles (utmost good faith, indemnity, subrogation).",
            sections = listOf(
                LessonSection(
                    heading = "1. Principles of Insurance & Business Ownership",
                    body = "Insurance spreads risk across policyholders. Except for life assurance, contracts follow the principle of Indemnity—restoring the insured without profit.",
                    keyPoints = listOf(
                        "Subrogation: insurer steps into the insured's shoes to recover from a third party.",
                        "Private Ltd (Ltd): 2–50 members, shares not traded publicly; Public Ltd (Plc): min 2 (no upper limit), shares traded on NGX.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Insurance Principles: Indemnity, Insurable Interest, Uberrimae Fidei, Proximate Cause",
                "Crossed Cheque = Payable ONLY through a bank account",
            ),
            workedExample = "Which principle requires both parties in an insurance contract to disclose all material facts truthfully?\nAnswer: Utmost Good Faith (Uberrimae Fidei).",
        ),

        // Christian Religious Studies (WAEC, JAMB, NECO)
        seedTopic(
            id = "crs_leadership",
            subjectId = "crs",
            title = "Leadership in Israel: Moses, Joshua & Deborah",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 12,
            summary = "Study the call of Moses, Joshua's succession, Deborah's courage as Judge, and moral lessons of servant leadership.",
            sections = listOf(
                LessonSection(
                    heading = "1. Godly Leadership & Obedience",
                    body = "Old Testament leadership narratives emphasize faith in God's call, courage in crisis, and delegation of responsibility (as Jethro advised Moses).",
                    keyPoints = listOf(
                        "Moses encountered God at the burning bush on Mount Horeb (Midian).",
                        "Deborah served as both Prophetess and Judge, leading Barak to victory over Sisera.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Mount Horeb = Call of Moses | Jethro's Advice = Delegation of Judges",
                "Deborah & Barak = Victory at River Kishon over Sisera",
            ),
            workedExample = "Who advised Moses to appoint able men as judges over thousands, hundreds, fifties, and tens?\nAnswer: Jethro (Moses' father-in-law).",
        ),
        seedTopic(
            id = "crs_gospels",
            subjectId = "crs",
            title = "Synoptic Gospels, Parables & Early Church",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 12,
            summary = "Examine the Sermon on the Mount, parables of the Kingdom, Pentecost, and apostolic missionary journeys.",
            sections = listOf(
                LessonSection(
                    heading = "1. Beatitudes, Parables & Pentecost",
                    body = "Matthew, Mark, and Luke are called the Synoptic Gospels because they share a common narrative structure. Acts records the birth of the Church at Pentecost.",
                    keyPoints = listOf(
                        "Parable of the Prodigal Son teaches unconditional divine forgiveness.",
                        "Seven Deacons (led by Stephen and Philip) were chosen to oversee daily food distribution.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Synoptic Gospels = Matthew, Mark, Luke",
                "First Christian Martyr = Stephen | Apostle to the Gentiles = Paul",
            ),
            workedExample = "Why did the apostles appoint seven deacons in Acts 6?\nAnswer: To handle the daily distribution to widows so the apostles could focus on prayer and preaching.",
        ),

        // Geography (WAEC, JAMB, NECO)
        seedTopic(
            id = "geo_map_reading",
            subjectId = "geo",
            title = "Map Reading, Scale, Contours & Bearings",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 13,
            summary = "Convert Representative Fraction (RF) scales, recognize landforms from contour spacing, and compute gradients.",
            sections = listOf(
                LessonSection(
                    heading = "1. Map Scales & Contour Landforms",
                    body = "Map scale expresses map distance to ground distance. Closely spaced contours denote steep slopes; widely spaced contours denote gentle slopes.",
                    keyPoints = listOf(
                        "RF 1:50,000 means 1 cm on map = 50,000 cm = 0.5 km on ground.",
                        "Gradient = Vertical Interval (VI) / Horizontal Equivalent (HE).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "1 km = 100,000 cm | RF 1:50,000 ⇒ 2 cm = 1 km",
                "Gradient = Vertical Interval / Horizontal Equivalent (same units)",
            ),
            workedExample = "On a 1:50,000 map, two towns are 6 cm apart. Find the actual ground distance.\n1 cm = 0.5 km ⇒ 6 cm = 6 × 0.5 = 3.0 km.",
        ),
        seedTopic(
            id = "geo_weather_climate",
            subjectId = "geo",
            title = "Weather Instruments, Climate & Nigeria Relief",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 12,
            summary = "Match meteorological instruments, Nigeria's tropical wet/dry seasons (ITCZ), and major highland/river basins.",
            sections = listOf(
                LessonSection(
                    heading = "1. Instruments & Nigerian Seasons",
                    body = "Nigeria's climate is governed by two air masses: moist Tropical Maritime (SW winds from Atlantic bringing rain) and dry dusty Tropical Continental (NE Harmattan winds from Sahara).",
                    keyPoints = listOf(
                        "Wind vane shows direction; Anemometer measures speed; Rain gauge measures rainfall.",
                        "Isobars join equal pressure; Isohyets join equal rainfall; Isotherms join equal temperature.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Isohyets = Equal Rainfall | Isobars = Equal Pressure | Contours = Equal Height",
                "Harmattan = NE Trade Winds (Dry) | Monsoon = SW Winds (Wet)",
            ),
            workedExample = "Lines drawn on a weather map joining places of equal atmospheric pressure are called ____.\nAnswer: Isobars.",
        ),
    )

    fun questions(): List<QuestionEntity> = listOf(
        // Exam-specific English
        seedQuestion(
            id = "q_eng_13", subjectId = "eng", topicId = "eng_essay_summary", examTag = "WAEC",
            prompt = "Which closing subscription is correct for a formal letter addressed to 'Dear Sir'?",
            options = listOf("Yours sincerely,", "Yours affectionately,", "Yours faithfully,", "Your's faithfully,"),
            correctIndex = 2,
            explanation = "Formal letters opening with 'Dear Sir/Madam' must close with 'Yours faithfully,' (no apostrophe) followed by signature and full name.",
        ),
        seedQuestion(
            id = "q_eng_14", subjectId = "eng", topicId = "eng_essay_summary", examTag = "NECO",
            prompt = "In WAEC/NECO summary writing, candidates are penalized if they ____.",
            options = listOf("use complete sentences", "copy whole passages verbatim without synthesis", "state one point per sentence", "avoid unnecessary examples"),
            correctIndex = 1,
            explanation = "Mindless lifting (copying large chunks verbatim) loses marks; express the core idea concisely in your own complete sentence.",
        ),
        seedQuestion(
            id = "q_eng_15", subjectId = "eng", topicId = "eng_cloze_register", examTag = "JAMB",
            prompt = "Judiciary Register: After hearing the defence counsel's plea, the judge ____ the case till next Monday.",
            options = listOf("adjourned", "prescribed", "liquidated", "vaccinated"),
            correctIndex = 0,
            explanation = "In legal register, postponing a court sitting to a later date is 'adjourned'.",
        ),
        seedQuestion(
            id = "q_eng_16", subjectId = "eng", topicId = "eng_cloze_register", examTag = "JAMB",
            prompt = "Banking Register: Before approving the business loan, the bank requested valuable land documents as ____.",
            options = listOf("dividend", "collateral", "tariff", "verdict"),
            correctIndex = 1,
            explanation = "Asset security pledged by a borrower against a bank loan is called 'collateral'.",
        ),
        // Exam-specific Maths
        seedQuestion(
            id = "q_mth_13", subjectId = "mth", topicId = "mth_circle_theorems", examTag = "WAEC",
            prompt = "An arc subtends an angle of 110° at the centre of a circle. What angle does it subtend at the circumference?",
            options = listOf("220°", "55°", "70°", "90°"),
            correctIndex = 1,
            explanation = "Angle at the circumference is half the angle at the centre subtended by the same arc: 110° / 2 = 55°.",
        ),
        seedQuestion(
            id = "q_mth_14", subjectId = "mth", topicId = "mth_circle_theorems", examTag = "NECO",
            prompt = "In a cyclic quadrilateral ABCD, ∠A = 85°. What is the measure of the opposite angle ∠C?",
            options = listOf("85°", "95°", "105°", "275°"),
            correctIndex = 1,
            explanation = "Opposite angles of a cyclic quadrilateral add up to 180°: 180° - 85° = 95°.",
        ),
        seedQuestion(
            id = "q_mth_15", subjectId = "mth", topicId = "mth_calculus_matrices", examTag = "JAMB",
            prompt = "Find the derivative dy/dx of y = 3x⁴ - 5x + 7.",
            options = listOf("12x³ - 5", "12x⁴ - 5", "3x³ - 5", "12x³ + 7"),
            correctIndex = 0,
            explanation = "Using the power rule: d/dx(3x⁴) = 12x³, d/dx(-5x) = -5, d/dx(7) = 0.",
        ),
        seedQuestion(
            id = "q_mth_16", subjectId = "mth", topicId = "mth_calculus_matrices", examTag = "JAMB",
            prompt = "For what value of x is the matrix [[x, 2], [6, 4]] singular?",
            options = listOf("2", "3", "4", "12"),
            correctIndex = 1,
            explanation = "A singular matrix has determinant 0: 4x - (2 × 6) = 0 ⇒ 4x = 12 ⇒ x = 3.",
        ),
        // Exam-specific Biology, Chemistry, Physics
        seedQuestion(
            id = "q_bio_10", subjectId = "bio", topicId = "bio_adaptation", examTag = "WAEC",
            prompt = "Which reagent produces a blue-black colouration when added to boiled yam extract?",
            options = listOf("Biuret reagent", "Iodine solution", "Sudan III", "Fehling's solution"),
            correctIndex = 1,
            explanation = "Yam is rich in starch, which turns blue-black in the presence of iodine solution.",
        ),
        seedQuestion(
            id = "q_chm_10", subjectId = "chm", topicId = "chm_qualitative", examTag = "WAEC",
            prompt = "Which cation gives a light blue precipitate with NaOH(aq) that dissolves in excess aqueous ammonia to form a deep blue solution?",
            options = listOf("Zn²⁺", "Fe²⁺", "Cu²⁺", "Ca²⁺"),
            correctIndex = 2,
            explanation = "Cu²⁺ forms Cu(OH)₂ light blue ppt, which complexes with excess NH₃(aq) to yield deep blue [Cu(NH₃)₄]²⁺.",
        ),
        seedQuestion(
            id = "q_phy_10", subjectId = "phy", topicId = "phy_modern", examTag = "JAMB",
            prompt = "A radioactive isotope has a half-life of 4 hours. What fraction of the original nuclei remains after 12 hours?",
            options = listOf("1/2", "1/4", "1/8", "1/16"),
            correctIndex = 2,
            explanation = "Number of half-lives n = 12 / 4 = 3. Remaining fraction = (1/2)³ = 1/8.",
        ),
        // Civic Education
        seedQuestion(
            id = "q_civ_1", subjectId = "civic", topicId = "civ_values", examTag = "WAEC",
            prompt = "Which type of Nigerian citizenship is open to a foreigner who has lived continuously in Nigeria for 15 years and shown good character?",
            options = listOf("Citizenship by birth", "Citizenship by registration", "Citizenship by naturalisation", "Honorary citizenship"),
            correctIndex = 2,
            explanation = "Section 27 of the 1999 Constitution specifies naturalisation requirements including 15 years residency.",
        ),
        seedQuestion(
            id = "q_civ_2", subjectId = "civic", topicId = "civ_rights_road", examTag = "NECO",
            prompt = "Which Nigerian agency is specifically established to combat human trafficking and child exploitation?",
            options = listOf("EFCC", "NAPTIP", "NAFDAC", "FRSC"),
            correctIndex = 1,
            explanation = "NAPTIP (National Agency for the Prohibition of Trafficking in Persons) leads anti-trafficking enforcement.",
        ),
        // Agricultural Science
        seedQuestion(
            id = "q_agr_1", subjectId = "agr", topicId = "agr_soil", examTag = "JAMB",
            prompt = "Yellowing of older plant leaves (chlorosis) is a classic deficiency symptom of which nutrient?",
            options = listOf("Nitrogen", "Phosphorus", "Calcium", "Potassium"),
            correctIndex = 0,
            explanation = "Nitrogen is essential for chlorophyll synthesis; deficiency produces chlorosis starting in older leaves.",
        ),
        seedQuestion(
            id = "q_agr_2", subjectId = "agr", topicId = "agr_crops", examTag = "WAEC",
            prompt = "Black pod disease of cocoa is caused by which pathogen?",
            options = listOf("Bacterium", "Virus", "Fungus", "Nematode"),
            correctIndex = 2,
            explanation = "Cocoa black pod disease is caused by the fungus Phytophthora palmivora.",
        ),
        // Further Mathematics
        seedQuestion(
            id = "q_fmth_1", subjectId = "fmath", topicId = "fmth_vectors", examTag = "WAEC",
            prompt = "If vectors a = 2i + kj and b = 4i - 2j are perpendicular, find the value of k.",
            options = listOf("4", "-4", "2", "1"),
            correctIndex = 0,
            explanation = "Perpendicular vectors satisfy a · b = 0: (2)(4) + k(-2) = 0 ⇒ 8 - 2k = 0 ⇒ k = 4.",
        ),
        seedQuestion(
            id = "q_fmth_2", subjectId = "fmath", topicId = "fmth_probability", examTag = "NECO",
            prompt = "For a binomial distribution B(n = 20, p = 0.25), what is the mean?",
            options = listOf("4", "5", "15", "3.75"),
            correctIndex = 1,
            explanation = "The mean of a binomial distribution is μ = np = 20 × 0.25 = 5.",
        ),
        // Financial Accounting
        seedQuestion(
            id = "q_acc_1", subjectId = "acc", topicId = "acc_double_entry", examTag = "WAEC",
            prompt = "Which error does NOT affect the agreement of a Trial Balance?",
            options = listOf("Single-entry posting", "Error of principle", "Wrong addition in sales day book", "Unequal debit and credit amounts"),
            correctIndex = 1,
            explanation = "An error of principle posts the correct debit and credit amounts to the wrong class of account, so Trial Balance totals still agree.",
        ),
        seedQuestion(
            id = "q_acc_2", subjectId = "acc", topicId = "acc_final_accounts", examTag = "JAMB",
            prompt = "Given Current Assets = ₦120,000 and Current Liabilities = ₦45,000, calculate Working Capital.",
            options = listOf("₦165,000", "₦75,000", "₦2.67", "₦45,000"),
            correctIndex = 1,
            explanation = "Working Capital = Current Assets - Current Liabilities = ₦120,000 - ₦45,000 = ₦75,000.",
        ),
        // Commerce
        seedQuestion(
            id = "q_com_1", subjectId = "com", topicId = "com_trade", examTag = "JAMB",
            prompt = "Which international trade document serves as a document of title to goods shipped by sea?",
            options = listOf("Consignment note", "Bill of lading", "Debit note", "Delivery note"),
            correctIndex = 1,
            explanation = "A Bill of Lading is issued by the shipping company and acts as receipt, carriage contract, and document of title.",
        ),
        seedQuestion(
            id = "q_com_2", subjectId = "com", topicId = "com_banking", examTag = "NECO",
            prompt = "Two parallel transverse lines drawn across the face of a cheque indicate that it is ____.",
            options = listOf("dishonoured", "stale", "a crossed cheque payable only through a bank account", "an open bearer cheque"),
            correctIndex = 2,
            explanation = "A crossed cheque cannot be cashed over the counter; it must be paid into a bank account.",
        ),
        // CRS
        seedQuestion(
            id = "q_crs_1", subjectId = "crs", topicId = "crs_leadership", examTag = "WAEC",
            prompt = "Who led the Israelite army alongside Prophetess Deborah to defeat Sisera at River Kishon?",
            options = listOf("Gideon", "Barak", "Jephthah", "Caleb"),
            correctIndex = 1,
            explanation = "Deborah summoned Barak son of Abinoam from Kedesh-Naphtali to lead Israel against Sisera.",
        ),
        seedQuestion(
            id = "q_crs_2", subjectId = "crs", topicId = "crs_gospels", examTag = "JAMB",
            prompt = "Which three books of the New Testament are known as the Synoptic Gospels?",
            options = listOf("Matthew, Mark, John", "Matthew, Mark, Luke", "Mark, Luke, Acts", "Luke, John, Romans"),
            correctIndex = 1,
            explanation = "Matthew, Mark, and Luke are called the Synoptic Gospels because they view Jesus' ministry from a closely parallel outline.",
        ),
        // Geography
        seedQuestion(
            id = "q_geo_1", subjectId = "geo", topicId = "geo_map_reading", examTag = "JAMB",
            prompt = "On a map with scale 1:50,000, what ground distance is represented by 4 cm?",
            options = listOf("1.0 km", "2.0 km", "4.0 km", "20.0 km"),
            correctIndex = 1,
            explanation = "1:50,000 means 1 cm = 50,000 cm = 0.5 km. Therefore 4 cm = 4 × 0.5 = 2.0 km.",
        ),
        seedQuestion(
            id = "q_geo_2", subjectId = "geo", topicId = "geo_weather_climate", examTag = "WAEC",
            prompt = "Which wind system brings dry, dusty Harmattan conditions across Northern Nigeria between November and February?",
            options = listOf("South-West Monsoon", "North-East Trade Winds", "Westerlies", "Land Breeze"),
            correctIndex = 1,
            explanation = "The Tropical Continental (cT) air mass / North-East Trade Winds blow from the Sahara Desert across Nigeria.",
        ),
    )
}
