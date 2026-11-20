package com.example.data.seed

import com.example.data.local.QuestionEntity
import com.example.data.local.TopicEntity
import com.example.model.LessonSection

object ScienceSubjectsSeed {
    fun topics(): List<TopicEntity> = listOf(
        // Biology
        seedTopic(
            id = "bio_cell",
            subjectId = "bio",
            title = "Cell Structure, Diffusion & Osmosis",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 13,
            summary = "Compare plant and animal cell organelles and understand passive transport across selectively permeable membranes.",
            sections = listOf(
                LessonSection(
                    heading = "1. Organelles & Plant vs. Animal Cells",
                    body = "Eukaryotic cells contain membrane-bound organelles with specialized roles. Plant cells possess a cellulose cell wall, chloroplasts, and a large central vacuole, which animal cells lack.",
                    keyPoints = listOf(
                        "Mitochondrion: site of aerobic cellular respiration (ATP synthesis).",
                        "Ribosome: site of protein synthesis (non-membrane-bound).",
                        "Golgi apparatus: packaging and secretion of proteins/enzymes.",
                    ),
                ),
                LessonSection(
                    heading = "2. Osmosis, Turgidity & Plasmolysis",
                    body = "Osmosis is the movement of water molecules from a region of higher water potential (dilute solution) to lower water potential (concentrated solution) across a semi-permeable membrane.",
                    keyPoints = listOf(
                        "Hypotonic solution → plant cell becomes turgid; animal cell may haemolyse (burst).",
                        "Hypertonic solution → plant cell undergoes plasmolysis; animal cell crenates (shrinks).",
                        "Active transport requires ATP to move solutes against a concentration gradient.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Hypotonic medium → Water enters cell (Turgidity / Haemolysis)",
                "Hypertonic medium → Water leaves cell (Plasmolysis / Crenation)",
                "Isotonic medium → Zero net water movement",
            ),
            workedExample = "A peeled yam strip placed in a concentrated salt solution loses mass and becomes flabby.\nReason: The salt solution is hypertonic to the yam cell sap, so water moves out of the cells by osmosis (plasmolysis).",
        ),
        seedTopic(
            id = "bio_genetics",
            subjectId = "bio",
            title = "Genetics, Heredity & Blood Groups",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 15,
            summary = "Solve Mendelian monohybrid crosses, sex-linked inheritance, and ABO blood group compatibility problems.",
            sections = listOf(
                LessonSection(
                    heading = "1. Mendelian Monohybrid Ratios",
                    body = "In complete dominance, crossing two heterozygotes (Tt × Tt) produces an F2 phenotypic ratio of 3:1 and a genotypic ratio of 1:2:1.",
                    keyPoints = listOf(
                        "Genotype = genetic makeup (e.g., Tt); Phenotype = observable trait (e.g., Tall).",
                        "Test cross = crossing an unknown dominant phenotype with a homozygous recessive (tt).",
                        "Codominance occurs in ABO blood group alleles I^A and I^B.",
                    ),
                ),
                LessonSection(
                    heading = "2. Sex Determination & Sex-Linked Traits",
                    body = "Humans have 46 chromosomes (22 pairs autosomes + 1 pair sex chromosomes: XX female, XY male). Colour blindness and haemophilia are carried on the X chromosome.",
                    keyPoints = listOf(
                        "The father's sperm (X or Y) determines the biological sex of the offspring.",
                        "Sickle-cell anaemia is an autosomal recessive trait (Hb^S Hb^S).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Monohybrid F2 (Tt × Tt): Genotype = 1 TT : 2 Tt : 1 tt | Phenotype = 3 : 1",
                "ABO Blood Group O = ii (Universal Donor) | AB = I^A I^B (Universal Recipient)",
                "AS × AS carriers: 25% AA (Normal), 50% AS (Carrier), 25% SS (Sickle cell)",
            ),
            workedExample = "If a man with blood group A (I^A i) marries a woman with blood group B (I^B i), what blood groups can their children have?\nCross: I^A i × I^B i → I^A I^B (AB), I^A i (A), I^B i (B), ii (O).\nAnswer: All four blood groups (A, B, AB, and O) are possible.",
        ),
        seedTopic(
            id = "bio_ecology",
            subjectId = "bio",
            title = "Ecology, Food Chains & Nutrient Cycles",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 12,
            summary = "Understand trophic energy flow, ecological instruments, and bacterial roles in the nitrogen cycle.",
            sections = listOf(
                LessonSection(
                    heading = "1. Trophic Levels & Energy Pyramid",
                    body = "Energy enters ecosystems via green plant producers through photosynthesis. Only about 10% of energy is transferred to each higher trophic level, limiting food chains to 4–5 links.",
                    keyPoints = listOf(
                        "Producers → Primary Consumers (Herbivores) → Secondary Consumers → Decomposers.",
                        "Anemometer measures wind speed; Hygrometer measures relative humidity; Secchi disc measures water turbidity.",
                    ),
                ),
                LessonSection(
                    heading = "2. The Nitrogen Cycle",
                    body = "Atmospheric nitrogen (N₂) is unusable by plants until converted to nitrates by nitrogen-fixing bacteria or lightning.",
                    keyPoints = listOf(
                        "Rhizobium in root nodules of legumes fixes N₂ into ammonia.",
                        "Nitrosomonas converts ammonia (NH₃) → Nitrites (NO₂⁻); Nitrobacter converts Nitrites → Nitrates (NO₃⁻).",
                        "Denitrifying bacteria (Pseudomonas) return nitrates back to N₂ in waterlogged soil.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Nitrogen Fixation: N₂ → NH₃ (Rhizobium / Azotobacter)",
                "Nitrification: NH₄⁺ ─(Nitrosomonas)→ NO₂⁻ ─(Nitrobacter)→ NO₃⁻",
                "Population Density = Total Individuals / Area",
            ),
            workedExample = "Which bacterium oxidizes nitrites into nitrates for plant absorption?\nAnswer: Nitrobacter (Nitrosomonas converts ammonium to nitrites first).",
        ),
        // Chemistry
        seedTopic(
            id = "chm_mole",
            subjectId = "chm",
            title = "Stoichiometry & The Mole Concept",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 14,
            summary = "Convert mass, molar volume, concentration, and Avogadro particle counts accurately in chemical reactions.",
            sections = listOf(
                LessonSection(
                    heading = "1. Moles, Molar Mass & Gas Volume",
                    body = "One mole of any substance contains 6.02 × 10²³ elementary particles. At Standard Temperature and Pressure (s.t.p.), 1 mole of any ideal gas occupies 22.4 dm³.",
                    keyPoints = listOf(
                        "Moles (n) = Mass (g) / Molar Mass (g/mol).",
                        "Moles of gas at s.t.p. = Volume (dm³) / 22.4 dm³.",
                        "Moles in solution = Concentration (mol/dm³) × Volume (dm³).",
                    ),
                ),
                LessonSection(
                    heading = "2. Empirical vs. Molecular Formula",
                    body = "Empirical formula gives the simplest whole-number ratio of atoms. Divide the given molar mass by the empirical formula mass to find multiplier n.",
                    keyPoints = listOf(
                        "Molecular Formula = (Empirical Formula)ₙ.",
                        "Titration equation: (CₐVₐ) / (C_b V_b) = nₐ / n_b.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "n = m / M  |  n = V / 22.4 dm³ (at s.t.p.)",
                "n = C × V (in dm³)  |  (CₐVₐ) / (C_b V_b) = nₐ / n_b",
                "Avogadro's Constant Nₐ = 6.02 × 10²³ mol⁻¹",
            ),
            workedExample = "Calculate the number of moles in 5.6 dm³ of CO₂ at s.t.p.\nStep 1: n = Volume / 22.4 dm³ = 5.6 / 22.4 = 0.25 mol.",
        ),
        seedTopic(
            id = "chm_bonding",
            subjectId = "chm",
            title = "Chemical Bonding & Periodic Trends",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 13,
            summary = "Distinguish ionic, covalent, coordinate, and hydrogen bonding and predict periodic properties across periods and groups.",
            sections = listOf(
                LessonSection(
                    heading = "1. Types of Chemical Bonds",
                    body = "Ionic (electrovalent) bonds form by electron transfer between metals and non-metals, giving high-melting crystalline solids that conduct electricity when molten or aqueous. Covalent bonds share electron pairs.",
                    keyPoints = listOf(
                        "Dative (coordinate) bond: both shared electrons come from one atom (e.g., NH₄⁺, H₃O⁺).",
                        "Hydrogen bonding explains why H₂O has a much higher boiling point than H₂S.",
                    ),
                ),
                LessonSection(
                    heading = "2. Trends Across Periods & Down Groups",
                    body = "Across a period (left to right), nuclear charge increases while shell number stays constant: atomic radius decreases, while ionization energy and electronegativity increase.",
                    keyPoints = listOf(
                        "Down a group: atomic radius increases; ionization energy and electronegativity decrease.",
                        "Group 1 reactivity increases down the group; Group 7 (halogens) reactivity decreases down the group.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "Across Period (→): Atomic Radius ↓ | Ionization Energy ↑ | Electronegativity ↑",
                "Down Group (↓): Atomic Radius ↑ | Ionization Energy ↓ | Metallic Character ↑",
                "Bonding: Ionic (Transfer) | Covalent (Equal/Polar Share) | Dative (One-sided Share)",
            ),
            workedExample = "Which of NH₃, CH₄, H₂O, and CO₂ contains hydrogen bonding?\nBoth NH₃ and H₂O contain H bonded directly to highly electronegative N or O; H₂O has stronger intermolecular hydrogen bonding (2 lone pairs + 2 H).",
        ),
        seedTopic(
            id = "chm_acids",
            subjectId = "chm",
            title = "Acids, Bases, Salts & pH Scale",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 12,
            summary = "Apply Brønsted-Lowry definitions, calculate pH from [H⁺], and classify normal, acid, basic, and double salts.",
            sections = listOf(
                LessonSection(
                    heading = "1. Acid-Base Theories & pH Calculations",
                    body = "An Arrhenius acid produces H⁺ in water; a Brønsted-Lowry acid is a proton (H⁺) donor while a base is a proton acceptor. The pH scale runs from 0 to 14 at 25 °C.",
                    keyPoints = listOf(
                        "Strong acids (HCl, HNO₃, H₂SO₄) ionize completely; weak acids (CH₃COOH) ionize partially.",
                        "pH = -log₁₀[H⁺]; pH + pOH = 14.",
                    ),
                ),
                LessonSection(
                    heading = "2. Types of Salts & Deliquescence",
                    body = "Normal salts (NaCl) replace all replaceable H⁺; acid salts (NaHSO₄) retain partial H⁺; double salts (potash alum) crystallize two simple salts together.",
                    keyPoints = listOf(
                        "Hygroscopic = absorbs moisture without dissolving (conc. H₂SO₄, CaO).",
                        "Deliquescent = absorbs moisture to form a solution (NaOH, CaCl₂).",
                        "Efflorescent = loses water of crystallization to air (Na₂CO₃·10H₂O).",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "pH = -log₁₀[H⁺]  |  pH + pOH = 14",
                "Neutralization: Acid + Base → Salt + Water",
                "Basicity of H₂SO₄ = 2 | H₃PO₄ = 3 | CH₃COOH = 1",
            ),
            workedExample = "Find the pH of a 0.001 mol/dm³ HCl solution.\nHCl is a strong monobasic acid: [H⁺] = 10⁻³ mol/dm³.\npH = -log₁₀(10⁻³) = 3.",
        ),
        // Physics
        seedTopic(
            id = "phy_motion",
            subjectId = "phy",
            title = "Equations of Motion & Projectiles",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 1,
            estimatedMinutes = 15,
            summary = "Solve uniformly accelerated motion, free-fall problems, and horizontal/angled projectile range questions.",
            sections = listOf(
                LessonSection(
                    heading = "1. SUVAT Equations & Motion Graphs",
                    body = "For motion with constant acceleration 'a', use the three core kinematic equations. Remember: the gradient of a velocity-time graph gives acceleration, and the area under it equals displacement.",
                    keyPoints = listOf(
                        "v = u + at",
                        "s = ut + ½at²",
                        "v² = u² + 2as",
                    ),
                ),
                LessonSection(
                    heading = "2. Projectile Motion",
                    body = "A projectile experiences zero horizontal acceleration (constant u cos θ) and vertical acceleration g downward. Maximum horizontal range occurs at launch angle θ = 45°.",
                    keyPoints = listOf(
                        "Time to maximum height: t = (u sin θ) / g.",
                        "Total time of flight: T = (2u sin θ) / g.",
                        "Maximum height: H = (u² sin² θ) / (2g); Range: R = (u² sin 2θ) / g.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "v = u + at  |  s = ut + ½at²  |  v² = u² + 2as",
                "T = 2u·sinθ / g  |  H = u²·sin²θ / 2g  |  R = u²·sin2θ / g",
                "Max Range occurs at θ = 45°",
            ),
            workedExample = "A stone is projected at 20 m/s at 30° to the horizontal (g = 10 m/s²). Find the maximum height.\nH = (20² × sin²30°) / (2 × 10) = (400 × 0.25) / 20 = 100 / 20 = 5.0 m.",
        ),
        seedTopic(
            id = "phy_work",
            subjectId = "phy",
            title = "Work, Energy, Power & Simple Machines",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 2,
            estimatedMinutes = 14,
            summary = "Compute mechanical work, kinetic/potential energy conservation, mechanical advantage, velocity ratio, and efficiency.",
            sections = listOf(
                LessonSection(
                    heading = "1. Conservation of Mechanical Energy",
                    body = "Work is done when a force displaces a body in the direction of the force (W = Fs cos θ). In free fall without air resistance, potential energy lost equals kinetic energy gained.",
                    keyPoints = listOf(
                        "KE = ½mv² and PE = mgh.",
                        "Power P = Work / Time = Force × Velocity.",
                    ),
                ),
                LessonSection(
                    heading = "2. Simple Machines Efficiency",
                    body = "Velocity Ratio (VR) depends solely on geometry (e.g., number of pulleys or inclined plane 1/sin θ), while Mechanical Advantage (MA) decreases when friction is present.",
                    keyPoints = listOf(
                        "MA = Load / Effort.",
                        "VR = Distance moved by Effort / Distance moved by Load.",
                        "Efficiency = (MA / VR) × 100%.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "W = F·s·cosθ  |  KE = ½mv²  |  PE = mgh",
                "P = W/t = F·v (Watts)",
                "Efficiency (%) = (MA / VR) × 100%",
            ),
            workedExample = "A machine of velocity ratio 5 lifts a load of 200 N with an effort of 50 N. Find its efficiency.\nMA = 200 / 50 = 4.\nEfficiency = (4 / 5) × 100% = 80%.",
        ),
        seedTopic(
            id = "phy_electricity",
            subjectId = "phy",
            title = "Current Electricity, Ohm's Law & Circuits",
            examTagsCsv = "WAEC,JAMB,NECO",
            orderIndex = 3,
            estimatedMinutes = 15,
            summary = "Analyze series/parallel resistors, internal resistance of cells, electrical power, and energy billing in kWh.",
            sections = listOf(
                LessonSection(
                    heading = "1. Ohm's Law & Internal Resistance",
                    body = "At constant temperature, current through an ohmic conductor is proportional to potential difference (V = IR). Real cells have internal resistance 'r', causing terminal p.d. V = E - Ir.",
                    keyPoints = listOf(
                        "Series resistors: R_total = R₁ + R₂ (same current).",
                        "Parallel resistors: 1/R_total = 1/R₁ + 1/R₂ (same voltage).",
                        "E = I(R + r), where E is e.m.f. and r is internal resistance.",
                    ),
                ),
                LessonSection(
                    heading = "2. Electrical Energy & Power",
                    body = "Power dissipated in a resistor can be written in three equivalent forms. Utility bills measure energy in kilowatt-hours (kWh).",
                    keyPoints = listOf(
                        "P = IV = I²R = V²/R.",
                        "Energy (Joules) = IVt.",
                        "1 kWh = 1000 W × 3600 s = 3.6 × 10⁶ J.",
                    ),
                ),
            ),
            keyFormulas = listOf(
                "V = IR  |  E = I(R + r)",
                "Parallel (2 resistors): R_eq = (R₁·R₂) / (R₁ + R₂)",
                "P = IV = I²R = V²/R  |  Energy (kWh) = (Watts / 1000) × Hours",
            ),
            workedExample = "Two resistors 6 Ω and 3 Ω are connected in parallel across a 6 V battery. Find the total current.\nR_eq = (6 × 3) / (6 + 3) = 18 / 9 = 2 Ω.\nCurrent I = V / R_eq = 6 / 2 = 3.0 A.",
        ),
    )

    fun questions(): List<QuestionEntity> = listOf(
        // Biology
        seedQuestion(
            id = "q_bio_1", subjectId = "bio", topicId = "bio_cell", examTag = "JAMB",
            prompt = "Which organelle is primarily responsible for aerobic respiration and ATP production?",
            options = listOf("Ribosome", "Golgi body", "Mitochondrion", "Lysosome"),
            correctIndex = 2,
            explanation = "Mitochondria are the powerhouse of eukaryotic cells where aerobic respiration generates ATP.",
        ),
        seedQuestion(
            id = "q_bio_2", subjectId = "bio", topicId = "bio_cell", examTag = "WAEC",
            prompt = "What happens to red blood cells when placed in distilled water?",
            options = listOf("They undergo plasmolysis", "They swell and burst (haemolysis)", "They crenate", "Nothing changes"),
            correctIndex = 1,
            explanation = "Distilled water is hypotonic to cell cytoplasm; lacking a rigid cell wall, animal RBCs swell and burst (haemolysis).",
        ),
        seedQuestion(
            id = "q_bio_3", subjectId = "bio", topicId = "bio_cell", examTag = "NECO",
            prompt = "Which structure is present in a plant leaf cell but absent in an animal cheek cell?",
            options = listOf("Cell membrane", "Nucleus", "Cellulose cell wall", "Mitochondrion"),
            correctIndex = 2,
            explanation = "Plant cells have a rigid cellulose cell wall and chloroplasts, whereas animal cells only have a plasma membrane.",
        ),
        seedQuestion(
            id = "q_bio_4", subjectId = "bio", topicId = "bio_genetics", examTag = "JAMB",
            prompt = "If two sickle-cell trait carriers (AS × AS) marry, what is the probability of their child having sickle-cell anaemia (SS)?",
            options = listOf("0%", "25%", "50%", "75%"),
            correctIndex = 1,
            explanation = "Crossing AS × AS gives 1 AA : 2 AS : 1 SS, meaning 1 in 4 (25%) chance of SS.",
        ),
        seedQuestion(
            id = "q_bio_5", subjectId = "bio", topicId = "bio_genetics", examTag = "WAEC",
            prompt = "A person with blood group AB is called a universal recipient because their blood plasma contains ____.",
            options = listOf("both A and B antibodies", "neither anti-A nor anti-B antibodies", "only anti-A antibodies", "O antigens"),
            correctIndex = 1,
            explanation = "Blood group AB individuals have A and B antigens on RBCs and lack both anti-A and anti-B antibodies in plasma, preventing agglutination.",
        ),
        seedQuestion(
            id = "q_bio_6", subjectId = "bio", topicId = "bio_genetics", examTag = "NECO",
            prompt = "In a Mendelian monohybrid cross between two heterozygous tall pea plants (Tt × Tt), the phenotypic ratio is ____.",
            options = listOf("1 : 2 : 1", "3 : 1", "9 : 3 : 3 : 1", "1 : 1"),
            correctIndex = 1,
            explanation = "TT, Tt, and Tt are phenotypically tall while tt is short, giving a 3 tall : 1 short phenotypic ratio.",
        ),
        seedQuestion(
            id = "q_bio_7", subjectId = "bio", topicId = "bio_ecology", examTag = "JAMB",
            prompt = "Which ecological instrument is used to measure the relative humidity of the atmosphere?",
            options = listOf("Anemometer", "Hygrometer", "Barometer", "Secchi disc"),
            correctIndex = 1,
            explanation = "A hygrometer measures relative humidity; an anemometer measures wind speed; a barometer measures atmospheric pressure.",
        ),
        seedQuestion(
            id = "q_bio_8", subjectId = "bio", topicId = "bio_ecology", examTag = "WAEC",
            prompt = "Which bacterium converts nitrites (NO₂⁻) into nitrates (NO₃⁻) during nitrification?",
            options = listOf("Rhizobium", "Nitrosomonas", "Nitrobacter", "Pseudomonas"),
            correctIndex = 2,
            explanation = "Nitrosomonas oxidizes ammonium to nitrites, and Nitrobacter oxidizes nitrites to nitrates.",
        ),
        seedQuestion(
            id = "q_bio_9", subjectId = "bio", topicId = "bio_ecology", examTag = "NECO",
            prompt = "Why do food chains rarely exceed four or five trophic levels?",
            options = listOf(
                "Decomposers eat all producers",
                "About 90% of energy is lost at each trophic transfer",
                "Sunlight cannot reach secondary consumers",
                "Herbivores cannot digest proteins",
            ),
            correctIndex = 1,
            explanation = "Only ~10% of energy passes from one trophic level to the next; the rest is lost as heat in respiration and waste.",
        ),
        // Chemistry
        seedQuestion(
            id = "q_chm_1", subjectId = "chm", topicId = "chm_mole", examTag = "JAMB",
            prompt = "How many moles are present in 10 g of calcium carbonate, CaCO₃? (Ca=40, C=12, O=16)",
            options = listOf("0.01 mol", "0.10 mol", "1.00 mol", "0.25 mol"),
            correctIndex = 1,
            explanation = "Molar mass of CaCO₃ = 40 + 12 + (3 × 16) = 100 g/mol. Moles = 10 / 100 = 0.10 mol.",
        ),
        seedQuestion(
            id = "q_chm_2", subjectId = "chm", topicId = "chm_mole", examTag = "WAEC",
            prompt = "What volume does 0.5 mole of oxygen gas occupy at s.t.p.? (Molar volume = 22.4 dm³)",
            options = listOf("5.6 dm³", "11.2 dm³", "22.4 dm³", "44.8 dm³"),
            correctIndex = 1,
            explanation = "Volume = moles × 22.4 dm³ = 0.5 × 22.4 = 11.2 dm³.",
        ),
        seedQuestion(
            id = "q_chm_3", subjectId = "chm", topicId = "chm_mole", examTag = "NECO",
            prompt = "A compound has empirical formula CH₂ and molar mass 42 g/mol. What is its molecular formula? (C=12, H=1)",
            options = listOf("C₂H₄", "C₃H₆", "C₄H₈", "CH₂"),
            correctIndex = 1,
            explanation = "Empirical mass of CH₂ = 14. Multiplier n = 42 / 14 = 3, giving molecular formula C₃H₆.",
        ),
        seedQuestion(
            id = "q_chm_4", subjectId = "chm", topicId = "chm_bonding", examTag = "JAMB",
            prompt = "Which type of bond is formed when ammonia (NH₃) combines with a hydrogen ion (H⁺) to form NH₄⁺?",
            options = listOf("Ionic bond", "Dative covalent bond", "Metallic bond", "Van der Waals bond"),
            correctIndex = 1,
            explanation = "Nitrogen donates both electrons of its lone pair to H⁺, forming a coordinate (dative covalent) bond.",
        ),
        seedQuestion(
            id = "q_chm_5", subjectId = "chm", topicId = "chm_bonding", examTag = "WAEC",
            prompt = "Moving from left to right across Period 3 of the periodic table, atomic radius generally ____.",
            options = listOf("increases", "decreases", "remains constant", "doubles"),
            correctIndex = 1,
            explanation = "Effective nuclear charge increases across a period while electrons enter the same shell, pulling electrons closer and decreasing atomic radius.",
        ),
        seedQuestion(
            id = "q_chm_6", subjectId = "chm", topicId = "chm_bonding", examTag = "NECO",
            prompt = "Why is the boiling point of water (100 °C) much higher than that of hydrogen sulphide (-60 °C)?",
            options = listOf(
                "Water has a higher molar mass",
                "Water molecules form strong intermolecular hydrogen bonds",
                "Sulphur is more electronegative than oxygen",
                "Water is an ionic compound",
            ),
            correctIndex = 1,
            explanation = "Oxygen's high electronegativity enables strong hydrogen bonding between H₂O molecules, requiring more heat to vaporize.",
        ),
        seedQuestion(
            id = "q_chm_7", subjectId = "chm", topicId = "chm_acids", examTag = "JAMB",
            prompt = "What is the pH of a 0.01 mol/dm³ H₂SO₄ solution assuming complete ionization?",
            options = listOf("2.0", "1.7", "4.0", "12.0"),
            correctIndex = 1,
            explanation = "H₂SO₄ is dibasic: [H⁺] = 2 × 0.01 = 0.02 mol/dm³. pH = -log₁₀(0.02) = 2 - log₁₀(2) ≈ 1.70.",
        ),
        seedQuestion(
            id = "q_chm_8", subjectId = "chm", topicId = "chm_acids", examTag = "WAEC",
            prompt = "Sodium hydroxide pellets absorb moisture from the air and turn into a solution. This property is called ____.",
            options = listOf("efflorescence", "hygroscopy", "deliquescence", "sublimation"),
            correctIndex = 2,
            explanation = "Deliquescent substances (like NaOH and CaCl₂) absorb enough atmospheric moisture to dissolve into an aqueous solution.",
        ),
        seedQuestion(
            id = "q_chm_9", subjectId = "chm", topicId = "chm_acids", examTag = "NECO",
            prompt = "According to Brønsted-Lowry theory, an acid is a ____.",
            options = listOf("proton donor", "proton acceptor", "electron pair donor", "hydroxide producer"),
            correctIndex = 0,
            explanation = "Brønsted-Lowry defines an acid as a proton (H⁺) donor and a base as a proton acceptor.",
        ),
        // Physics
        seedQuestion(
            id = "q_phy_1", subjectId = "phy", topicId = "phy_motion", examTag = "JAMB",
            prompt = "A car starts from rest and accelerates uniformly at 4 m/s² for 5 s. What distance does it cover?",
            options = listOf("20 m", "40 m", "50 m", "100 m"),
            correctIndex = 2,
            explanation = "Using s = ut + ½at² with u = 0: s = 0 + ½(4)(5²) = 2 × 25 = 50 m.",
        ),
        seedQuestion(
            id = "q_phy_2", subjectId = "phy", topicId = "phy_motion", examTag = "WAEC",
            prompt = "At what angle of projection does a projectile achieve its maximum horizontal range?",
            options = listOf("30°", "45°", "60°", "90°"),
            correctIndex = 1,
            explanation = "Range R = (u² sin 2θ)/g is maximum when sin 2θ = 1, which gives 2θ = 90° ⇒ θ = 45°.",
        ),
        seedQuestion(
            id = "q_phy_3", subjectId = "phy", topicId = "phy_motion", examTag = "NECO",
            prompt = "What physical quantity is represented by the area under a velocity-time graph?",
            options = listOf("Acceleration", "Displacement", "Force", "Momentum"),
            correctIndex = 1,
            explanation = "The area under a velocity-time graph gives displacement, while its slope gives acceleration.",
        ),
        seedQuestion(
            id = "q_phy_4", subjectId = "phy", topicId = "phy_work", examTag = "JAMB",
            prompt = "A body of mass 4 kg moves with a velocity of 5 m/s. Calculate its kinetic energy.",
            options = listOf("20 J", "40 J", "50 J", "100 J"),
            correctIndex = 2,
            explanation = "KE = ½mv² = ½ × 4 × 5² = 2 × 25 = 50 J.",
        ),
        seedQuestion(
            id = "q_phy_5", subjectId = "phy", topicId = "phy_work", examTag = "WAEC",
            prompt = "An inclined plane of length 5 m raises a load to a height of 2 m. What is its velocity ratio?",
            options = listOf("0.4", "2.5", "3.0", "10.0"),
            correctIndex = 1,
            explanation = "For an inclined plane, VR = length / height = 1 / sin θ = 5 / 2 = 2.5.",
        ),
        seedQuestion(
            id = "q_phy_6", subjectId = "phy", topicId = "phy_work", examTag = "NECO",
            prompt = "A pump lifts 60 kg of water vertically through 10 m in 20 s (g = 10 m/s²). Calculate its power output.",
            options = listOf("300 W", "600 W", "3000 W", "12000 W"),
            correctIndex = 0,
            explanation = "Work = mgh = 60 × 10 × 10 = 6000 J. Power = Work / time = 6000 / 20 = 300 W.",
        ),
        seedQuestion(
            id = "q_phy_7", subjectId = "phy", topicId = "phy_electricity", examTag = "JAMB",
            prompt = "Three resistors of 4 Ω each are connected in parallel. What is their effective resistance?",
            options = listOf("12 Ω", "4/3 Ω", "3/4 Ω", "1 Ω"),
            correctIndex = 1,
            explanation = "1/R_eq = 1/4 + 1/4 + 1/4 = 3/4 ⇒ R_eq = 4/3 Ω.",
        ),
        seedQuestion(
            id = "q_phy_8", subjectId = "phy", topicId = "phy_electricity", examTag = "WAEC",
            prompt = "A cell of e.m.f. 1.5 V and internal resistance 0.5 Ω is connected to a 2.5 Ω resistor. Find the current.",
            options = listOf("0.5 A", "0.6 A", "1.0 A", "3.0 A"),
            correctIndex = 0,
            explanation = "I = E / (R + r) = 1.5 / (2.5 + 0.5) = 1.5 / 3.0 = 0.5 A.",
        ),
        seedQuestion(
            id = "q_phy_9", subjectId = "phy", topicId = "phy_electricity", examTag = "NECO",
            prompt = "An electric pressing iron rated 1000 W is used for 3 hours. How much energy does it consume in kWh?",
            options = listOf("0.3 kWh", "3.0 kWh", "30 kWh", "3000 kWh"),
            correctIndex = 1,
            explanation = "1000 W = 1 kW. Energy = 1 kW × 3 h = 3.0 kWh.",
        ),
    )
}
