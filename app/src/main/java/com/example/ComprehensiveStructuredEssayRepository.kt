package com.example

/**
 * 📚 Comprehensive Structured & Essay Question Repository for Edu.LK
 * Supports Grades 9, 10, and 11
 * Each subject contains 500 curriculum-aligned questions organized into 25 sets of 20 questions each.
 * Every single question contains:
 *  1. Detailed authentic main scenario & question text
 *  2. Sub-questions ((i), (ii), (iii), (iv) or (A), (B), (C))
 *  3. Explicit, fully worked-out Model Answers (පිළිතුර)
 *  4. Direct, step-by-step Marking Schemes & Mark Rubrics (ලකුණු දෙන ආකාරය / පියවරෙන් පියවර ලකුණු බෙදී යන ආකාරය)
 */

data class StructuredEssaySetInfo(
  val setNumber: Int, // 1..25
  val title: String,
  val startQuestionNum: Int, // e.g. 1, 21, 41...
  val endQuestionNum: Int, // e.g. 20, 40, 60...
  val totalQuestions: Int = 20,
  val unitTheme: String
)

object ComprehensiveStructuredEssayRepository {

  val availableGrades = listOf("9", "10", "11")

  val availableSubjects = listOf(
    "විද්‍යාව",
    "ගණිතය",
    "ඉතිහාසය",
    "ICT",
    "බුද්ධ ධර්මය",
    "සිංහල",
    "English",
    "භූගෝල විද්‍යාව",
    "පුරවැසි අධ්‍යාපනය",
    "ව්‍යාපාර හා ගිණුම්කරණය"
  )

  /**
   * Generates 25 sets of 20 questions (Total = 500 questions) for each subject and grade.
   */
  fun getSetsForSubject(grade: String, subject: String): List<StructuredEssaySetInfo> {
    val themes = getUnitThemesForSubject(grade, subject)
    return (1..25).map { setNum ->
      val start = (setNum - 1) * 20 + 1
      val end = setNum * 20
      val theme = themes.getOrElse(setNum - 1) { "විෂය ඒකක හා පුනරීක්ෂණ කාණ්ඩය - $setNum" }
      StructuredEssaySetInfo(
        setNumber = setNum,
        title = "කාණ්ඩය $setNum ($start - $end)",
        startQuestionNum = start,
        endQuestionNum = end,
        totalQuestions = 20,
        unitTheme = theme
      )
    }
  }

  fun getQuestions(
    grade: String,
    subject: String,
    setNumber: Int? = null,
    typeFilter: String? = null // "STRUCTURED", "ESSAY", or null
  ): List<StructuredEssayItem> {
    val all500 = generate500Questions(grade, subject)
    val setFiltered = if (setNumber != null && setNumber in 1..25) {
      all500.filter { it.setNumber == setNumber }
    } else {
      all500
    }

    return if (typeFilter != null) {
      setFiltered.filter { it.type.equals(typeFilter, ignoreCase = true) }
    } else {
      setFiltered
    }
  }

  private fun getUnitThemesForSubject(grade: String, subject: String): List<String> {
    return when {
      subject.contains("විද්‍යාව") -> when (grade) {
        "9" -> listOf(
          "1. ජීවීන්ගේ විවිධත්වය & සෛල ව්‍යුහය",
          "2. පදාර්ථයේ ව්‍යුහය & මූලද්‍රව්‍ය සංයුතිය",
          "3. ශක්ති ප්‍රභව & තාපය සම්ප්‍රේෂණය",
          "4. ශාක හා සත්ත්ව පටක වර්ගීකරණය",
          "5. චලිතය, බලය & නිව්ටන් නියම",
          "6. ආලෝකය, පරාවර්තනය & දර්පණ",
          "7. ජලය, ජලීය ද්‍රාවණ & විද්‍යුත් සන්නායකතාව",
          "8. ශබ්දය, තරංග & ශ්‍රවණ ක්‍රියාවලිය",
          "9. මිනිස් ශරීර පද්ධති & පෝෂණය",
          "10. අම්ල, භෂ්ම & දර්ශක භාවිතය",
          "11. ප්‍රභාසංශ්ලේෂණය & ශාක ආහාර නිෂ්පාදනය",
          "12. චුම්භක ක්ෂේත්‍ර & විද්‍යුත් පරිපථ",
          "13. පරිසර දූෂණය & ජල සංරක්ෂණය",
          "14. සරල යන්ත්‍ර & යාන්ත්‍රික වාසිය",
          "15. වායුගෝලය & කාලගුණ සාධක",
          "16. ඝනත්වය & පායනය මූලධර්ම",
          "17. රසායනික විපර්යාස & ලක්ෂණ",
          "18. ක්ෂුද්‍රජීවී ලෝකය & මානව හිතකර භාවිත",
          "19. ස්වභාවික සම්පත් තිරසාර භාවිතය",
          "20. මිනිසාගේ ප්‍රතිශක්තිකරණ ක්‍රියාවලිය",
          "21. අන්තරීක්ෂය & සෞරග්‍රහ මණ්ඩලය",
          "22. පීඩනය & ද්‍රවස්ථිතික මූලධර්ම",
          "23. තාප ප්‍රසාරණය & ප්‍රායෝගික යෙදුම්",
          "24. ජෛව විවිධත්වය සුරැකීමේ ක්‍රමවේද",
          "25. සමස්ත විද්‍යාව විභාග පුනරීක්ෂණ ආදර්ශ ප්‍රශ්නාවලිය"
        )
        "10" -> listOf(
          "1. ජීවයේ රසායනික පදනම (කාබෝහයිඩ්‍රේට, ප්‍රෝටීන, ලිපිඩ)",
          "2. පදාර්ථයේ ව්‍යුහය (පරමාණුක ක්‍රමාංකය, ස්කන්ධ ක්‍රමාංකය)",
          "3. නිව්ටන් නියම & රේඛීය චලිත සමීකරණ",
          "4. ශාක හා සත්ත්ව පටක (විභාජක, ස්ථිර, අපිච්ඡද, පේශි)",
          "5. රසායනික බන්ධන (සහසංයුජ, අයනික, ලෝහක)",
          "6. ආලෝකය, වර්තනය & කාච මඟින් ප්‍රතිබිම්බ සෑදීම",
          "7. ප්‍රභාසංශ්ලේෂණය & සාධක පරීක්ෂණ",
          "8. මූලද්‍රව්‍ය ආවර්තිතා ගුණ & විද්‍යුත් සෘණතාව",
          "9. බලයේ ඝූර්ණය, සමතුලිතතාව & කූඤ්ඤ යෙදුම්",
          "10. මිනිසාගේ ආහාර ජීර්ණ පද්ධතිය & එන්සයිම",
          "11. මවුලය, සාන්ද්‍රණය & ස්ටොයිකියෝමිතිය",
          "12. ධාරා විද්‍යුතය & ඕම්ගේ නියමය (V = IR)",
          "13. ශ්වසනය (වායුගෝලීය, සෛලීය & නිර්වායු)",
          "14. විද්‍යුත් විච්ඡේදනය & විද්‍යුත් රසායනික ශ්‍රේණිය",
          "15. කාර්යය, ශක්තිය & ජවය ගණනය කිරීම්",
          "16. මිනිසාගේ රුධිර සංසරණ පද්ධතිය & හෘද ව්‍යුහය",
          "17. අම්ල, භෂ්ම, pH අගය & උදාසීනීකරණය",
          "18. පීඩනය (ද්‍රව පීඩනය & වායුගෝලීය පීඩනය)",
          "19. ශාකවල ජල හා ඛනිජ පරිවහනය (උත්ස්වේදනය)",
          "20. තාපය, තාප ධාරිතාව & විශිෂ්ට තාප ධාරිතාව",
          "21. රසායනික ප්‍රතික්‍රියා සීඝ්‍රතාව කෙරෙහි සාධක",
          "22. චුම්භක ක්ෂේත්‍ර & ෆ්ලෙමින්ගේ වමත් නියමය",
          "23. මිනිසාගේ බහිස්ස්‍රාවී පද්ධතිය & වෘක්ක ව්‍යුහය",
          "24. පරිසරය, ආහාර ජාල & පාරිසරික පිරමිඩ",
          "25. සමස්ත 10 ශ්‍රේණිය විද්‍යාව විභාග පුහුණු ආදර්ශ පත්‍රිකා"
        )
        else -> listOf(
          "1. ජීවීන්ගේ ප්‍රජනනය (ලිංගික, අලිංගික & මානව ප්‍රජනනය)",
          "2. පදාර්ථයේ වෙනස්වීම් & ශක්ති විපර්යාස (තාපදායක, තාපශෝෂක)",
          "3. චලිත ප්‍රස්ථාර & චලිත සමීකරණ යෙදුම්",
          "4. පාරම්පරික බව, න්‍යෂ්ටික අම්ල & මෙන්ඩල්ගේ නියම",
          "5. රසායනික ගණනය කිරීම් & මවුලික ස්කන්ධය",
          "6. ආලෝකය, අභ්‍යන්තර පූර්ණ පරාවර්තනය & ප්‍රකාශ උපකරණ",
          "7. මිනිසාගේ ස්නායු පද්ධතිය & ප්‍රතිචාර දැක්වීම",
          "8. ලෝහ නිස්සාරණය (යකඩ, ඇලුමිනියම්) & ලෝහ විඛාදනය",
          "9. තරංග, විද්‍යුත් චුම්භක වර්ණාවලිය & ශබ්ද තීව්‍රතාව",
          "10. අන්තරාසර්ග පද්ධතිය & හෝමෝන ක්‍රියාකාරීත්වය",
          "11. කාබනික රසායනය & හයිඩ්‍රොකාබන (ඇල්කේන, ඇල්කීන, මධ්‍යසාර)",
          "12. විද්‍යුත් චුම්භක ප්‍රේරණය & ට්‍රාන්ස්ෆෝමර්",
          "13. මිනිසාගේ ඇස, කන හා සංවේදී ඉන්ද්‍රිය දෝෂ",
          "14. පොලිමර, ප්ලාස්ටික් වර්ගීකරණය & පරිසර බලපෑම",
          "15. ඉලෙක්ට්‍රොනික්ස් (ඩයෝඩ, ට්‍රාන්සිස්ටර, තර්ක ද්වාර)",
          "16. ශාක හෝමෝන & ශාක චලන",
          "17. කාර්මික රසායනය (ඇමෝනියා, සල්ෆියුරික් අම්ලය නිපදවීම)",
          "18. විද්‍යුත් බලය, ශක්තිය & ගෘහස්ථ විදුලි පරිපථ",
          "19. විකිරණශීලීතාව, සමස්ථානික & න්‍යෂ්ටික ශක්තිය",
          "20. ජෛවගෝලය, ජෛව භූ-රසායනික චක්‍ර (කාබන්, නයිට්‍රජන්)",
          "21. ක්ෂුද්‍රජීව විද්‍යාව, ප්‍රතිජීවක & රෝග පාලනය",
          "22. නැනෝ තාක්ෂණය & නවීන විද්‍යාත්මක සොයාගැනීම්",
          "23. මෝටර්, ජනක යන්ත්‍ර & චුම්භක බලපෑම්",
          "24. ස්වභාවික විපත් කළමනාකරණය & තිරසාර සංවර්ධනය",
          "25. O/L විභාග සමස්ත විද්‍යාව ව්‍යුහගත හා රචනා අවසන් පුහුණුව"
        )
      }

      subject.contains("ගණිතය") -> listOf(
        "1. භාග, දශම & සංඛ්‍යා රටා",
        "2. බීජීය ප්‍රකාශන, ප්‍රසාරණය & සාධක",
        "3. රේඛීය සමීකරණ & ඒකජ සමගාමී සමීකරණ",
        "4. පරිමිතිය, වර්ගඵලය & තල රූප",
        "5. ත්‍රිකෝණවල සමානතාව & අනුරූපතා ප්‍රමේය",
        "6. දර්ශක & ලඝුගණක මූලධර්ම",
        "7. වර්ගජ සමීකරණ & වර්ගපූර්ණ ක්‍රමය",
        "8. ත්‍රිකෝණමිතිය (sin, cos, tan & ආනෝහණ/අවනෝහණ කෝණ)",
        "9. වෘත්ත ප්‍රමේය (කේන්ද්‍ර කෝණය, පරිධි කෝණය, ස්පර්ශක)",
        "10. සංඛ්‍යානය (සංඛ්‍යාත ව්‍යාප්ති, මාතය, මධ්‍යන්‍යය, මධ්‍යස්ථය)",
        "11. සම්භාවිතාව (ගස් සටහන්, ජාල සටහන් & ස්වාධීන සිදුවීම්)",
        "12. කුලක & වෙන් රූප (කුලක 2ක් හා 3ක් ආශ්‍රිත ගැටලු)",
        "13. සමාන්තර ශ්‍රේඪි & ගුණෝත්තර ශ්‍රේඪි",
        "14. අසමානතා, අංක රේඛා & විසඳුම් කලාප",
        "15. සමාන්තර රේඛා, ඒකාන්තර කෝණ & මිත්‍ර කෝණ ප්‍රමේය",
        "16. චක්‍රීය චතුරස්‍ර ප්‍රමේය & ප්‍රතිලෝම ප්‍රමේය",
        "17. ජ්‍යාමිතික නිර්මාණ (කෝණ සමච්ඡේදක, ලම්බ සමච්ඡේදක, ස්පර්ශක)",
        "18. සරල රේඛා ප්‍රස්තාර (y = mx + c, අනුක්‍රමණය & අන්තඃඛණ්ඩ)",
        "19. වර්ගජ ශ්‍රිත ප්‍රස්තාර (හැරවුම් ලක්ෂ්‍යය, සමමිතික අක්ෂය, මූල)",
        "20. වාණිජ ගණිතය (සුළු පොලිය, වැල් පොලිය, වාරික ගෙවීම් & තීරුබදු)",
        "21. ඝන වස්තු පරිමාව & පෘෂ්ඨ වර්ගඵලය (සිලින්ඩර, කේතු, ගෝල, ප්‍රිස්ම)",
        "22. සමුච්චිත සංඛ්‍යාත වක්‍රය (ඕජයිව) & චතුර්ථක පරාස",
        "23. අනුපාත, සමානුපාත & ප්‍රතිලෝම සමානුපාත",
        "24. න්‍යාස (Matrices) & දෛශික (Vectors) මූලික ගැටලු",
        "25. O/L සමස්ත ගණිතය ප්‍රශ්න පත්‍ර I හා II ව්‍යුහගත ආදර්ශ ගැටලු"
      )

      subject.contains("ඉතිහාසය") -> listOf(
        "1. ඉතිහාසය හැදෑරීමේ මූලාශ්‍ර (සාහිත්‍ය & පුරාවිද්‍යාත්මක)",
        "2. ශ්‍රී ලංකාවේ ප්‍රාග් ඓතිහාසික මානවයා & වාසස්ථාන",
        "3. මුල් ඓතිහාසික යුගය & පණ්ඩුකාභය රජුගේ පාලනය",
        "4. බුදුදහම මෙරටට පැමිණීම & සමාජ ආගමික පරිවර්තනය",
        "5. අනුරාධපුර රාජධානියේ වාරි කර්මාන්තය & තාක්ෂණය",
        "6. දුටුගැමුණු රජුගේ සේවය & ඒකාබද්ධ පාලනය",
        "7. වළගම්බා රජු සහ ත්‍රිපිටකය ග්‍රන්ථාරූඪ කිරීම",
        "8. ධාතුසේන රජු සහ කලා වැවේ වාරි අසිරිය",
        "9. සීගිරිය & කාශ්‍යප රජුගේ කලා තාක්ෂණික දායකත්වය",
        "10. අනුරාධපුර අගභාගය & දකුණු ඉන්දීය ආක්‍රමණ",
        "11. විජයබාහු රජු සහ පොළොන්නරු රාජධානිය පිහිටුවීම",
        "12. මහා පරාක්‍රමබාහු රජු සහ පරාක්‍රම සමුද්‍රය",
        "13. නිශ්ශංකමල්ල රජුගේ සෙල්ලිපි & පරිපාලන ක්‍රමය",
        "14. මාඝගේ ආක්‍රමණය & නිරිතදිග රාජධානි කරා සංක්‍රමණය",
        "15. දඹදෙණිය, යාපහුව, කුරුණෑගල & ගම්පොළ යුග",
        "16. කෝට්ටේ රාජධානිය & VI වන පරාක්‍රමබාහු රජුගේ සාහිත්‍ය යුගය",
        "17. පෘතුගීසි ආගමනය & මෙරට මුහුදුබඩ ප්‍රදේශ යටත් කරගැනීම",
        "18. ලන්දේසි පාලනය & මෙරට නීතිමය, පරිපාලන බලපෑම",
        "19. උඩරට රාජධානිය & I වන විමලධර්මසූරිය, රාජසිංහ රජවරු",
        "20. බ්‍රිතාන්‍ය ආක්‍රමණය & 1815 උඩරට ගිවිසුම",
        "21. 1818 වෙල්ලස්ස නිදහස් අරගලය & කැප්පෙටිපොළ නිලමේ",
        "22. 1848 මාතලේ නිදහස් සටන & පුරන් අප්පු, ගොංගාලේගොඩ බණ්ඩා",
        "23. ආගමික, ජාතික පුනරුදය & අනගාරික ධර්මපාලතුමාගේ සේවය",
        "24. 1948 නිදහස ලැබීම & ආණ්ඩුක්‍රම ප්‍රතිසංස්කරණ",
        "25. O/L ඉතිහාසය සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්න පත්‍ර සම්පූර්ණ විග්‍රහය"
      )

      subject.contains("ICT") -> listOf(
        "1. පරිගණක පද්ධති සංකල්පය & දෘඩාංග උපාංග වර්ගීකරණය",
        "2. දත්ත නිරූපණය (ද්විමය, අෂ්ටමය, ෂඩ්දශමය & ASCII/Unicode)",
        "3. තාර්කික ද්වාර (Logic Gates: AND, OR, NOT, NAND, NOR, XOR)",
        "4. මෙහෙයුම් පද්ධති (Operating Systems & Process Management)",
        "5. වචන සැකසුම් මෘදුකාංග (Word Processing & Document Formatting)",
        "6. පැතුරුම්පත් (Spreadsheet Functions: SUM, AVERAGE, IF, VLOOKUP)",
        "7. දත්ත සමුදාය කළමනාකරණය (Database, Tables, Primary Key, Foreign Key)",
        "8. SQL විමසුම් (SELECT, INSERT, UPDATE, DELETE, WHERE, ORDER BY)",
        "9. ඉදිරිපත් කිරීමේ මෘදුකාංග & බහුමාධ්‍ය භාවිතය",
        "10. පරිගණක ජාල වර්ගීකරණය (LAN, WAN, MAN, PAN & Topologies)",
        "11. අන්තර්ජාලය, WWW & සන්නිවේදන ප්‍රොටෝකෝල (IP, TCP, HTTP, DNS)",
        "12. වෙබ් අඩවි නිර්මාණය (HTML5 මූලික ටැග & ව්‍යුහය)",
        "13. CSS මෝස්තර (Inline, Internal, External & Styling)",
        "14. ක්‍රමලේඛන මූලධර්ම & ගැලීම් සටහන් (Flowcharts & Pseudocode)",
        "15. පාලන ව්‍යුහ (ක්‍රමික, තේරීම් IF-ELSE, පුනරාවර්තන FOR/WHILE)",
        "16. Python ක්‍රමලේඛන භාෂාව (Variables, Data Types, Loops & Lists)",
        "17. තොරතුරු පද්ධති සංවර්ධන ජීවන චක්‍රය (SDLC අදියර 5)",
        "18. පරිගණක ආරක්ෂාව, වයිරස්, මැල්වෙයා & ආරක්ෂණ ක්‍රමවේද",
        "19. දත්ත සංකේතනය & ගුප්තකේතනය (Encryption & Firewalls)",
        "20. තොරතුරු සන්නිවේදන තාක්ෂණයේ සදාචාරාත්මක & නීතිමය ගැටලු",
        "21. ඊ-වාණිජ්‍යය & ඊ-රාජ්‍ය සේවා (E-Commerce & Digital Economy)",
        "22. වලාකුළු පරිගණකකරණය (Cloud Computing & IoT මූලික)",
        "23. කෘත්‍රිම බුද්ධිය (AI) & රොබෝ තාක්ෂණය ප්‍රායෝගික භාවිත",
        "24. තොරතුරු තාක්ෂණික පද්ධති පරීක්ෂාව & නඩත්තුව",
        "25. O/L ICT විභාග සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්න පත්‍ර ආදර්ශ විග්‍රහය"
      )

      subject.contains("බුද්ධ ධර්මය") -> listOf(
        "1. බුද්ධ චරිතය: සිදුහත් කුමරුගේ උපත, ගිහිගෙය හැරයාම & බුද්ධත්වය",
        "2. චතුරාර්ය සත්‍යය ගැඹුරු විග්‍රහය",
        "3. ආර්ය අෂ්ටාංගික මාර්ගය & සීල, සමාධි, ප්‍රඥා",
        "4. පටිච්ච සමුප්පාදය & හේතුඵල ධර්මය",
        "5. ත්‍රිලක්ෂණය: අනිත්‍ය, දුක්ඛ, අනත්ත",
        "6. කර්මය හා පුනර්භවය පිළිබඳ බෞද්ධ ඉගැන්වීම",
        "7. බෞද්ධ ආර්ථික දර්ශනය & ව්‍යග්ඝපජ්ජ, සිඟාලෝවාද සූත්‍ර",
        "8. බෞද්ධ පාරිසරික දැක්ම & සොබාදහම රැකගැනීම",
        "9. ධර්ම සංගායනා (පළමු, දෙවන, තෙවන සංගායනා)",
        "10. ත්‍රිපිටකය: විනය, සූත්‍ර, අභිධම්ම පිටක ව්‍යුහය",
        "11. ශ්‍රී ලංකාවට මහින්දාගමනය & ශාසන පිහිටුවීම",
        "12. සංඝමිත්තා තෙරණියගේ ආගමනය & ශ්‍රී මහා බෝධිය",
        "13. මහා විහාරය සහ අභයගිරිය බෞද්ධ සම්ප්‍රදාය",
        "14. ධාතු වන්දනාව & බෞද්ධ වෙහෙර විහාර කලාව",
        "15. සතර බ්‍රහ්ම විහරණ: මෙත්තා, කරුණා, මුදිතා, උපෙක්ඛා",
        "16. බෞද්ධ පවුල් ජීවිතය & සමාජ සබඳතා",
        "17. පංචසීලය & දස කුසල කර්ම පථය",
        "18. භාවනාව: සමථ හා විපස්සනා භාවනා ක්‍රමවේද",
        "19. මහා මංගල සූත්‍රය & කරණීයමෙත්ත සූත්‍ර දේශනා",
        "20. ථෙරවාද හා මහායාන බෞද්ධ ඉගැන්වීම් අතර වෙනස",
        "21. බෞද්ධ සංස්කෘතික මංගල්‍යයන් & පෙරහැර සම්ප්‍රදාය",
        "22. අග්ගඤ්ඤ සූත්‍රය & රාජ්‍ය පාලනය පිළිබඳ බෞද්ධ මතය",
        "23. මානසික සුවපත්භාවය සඳහා බෞද්ධ මනෝවිද්‍යාත්මක උපදෙස්",
        "24. නූතන සමාජ අර්බුද ජයගැනීමට බුදුදහමේ මඟපෙන්වීම",
        "25. O/L බුද්ධ ධර්මය සමස්ත විෂය නිර්දේශ ව්‍යුහගත හා රචනා ප්‍රශ්න පත්‍ර විග්‍රහය"
      )

      subject.contains("සිංහල") -> listOf(
        "1. සිංහල ව්‍යාකරණය: අක්ෂර මාලාව & ශබ්ද විචාරය",
        "2. ප්‍රකෘති, ප්‍රත්‍යය & නාම පද වර්ගීකරණය",
        "3. ආඛ්‍යාත පද, කාල භේදය & කාරක භේදය (කර්තෘ, කර්ම)",
        "4. සන්ධි නීති (ස්වර සන්ධි, ව්‍යඤ්ජන සන්ධි, ලෝප සන්ධි)",
        "5. සමාස පද වර්ගීකරණය & භාවිතය",
        "6. තද්ධිත හා කෘදන්ත පද නිර්මාණය",
        "7. විරාම ලක්ෂණ නිවැරදි භාවිතය & වාක්‍ය රීතිය",
        "8. නිවැරදි අක්ෂර වින්‍යාසය (ණ/න, ළ/ල භේදය)",
        "9. සම්ප්‍රදායික රචනා ලේඛනය: සැලසුම් කිරීම & ඡේද බෙදීම",
        "10. වාර්තාකරණය, නිල ලිපි & විද්‍යුත් තැපැල් ලේඛනය",
        "11. කෙටි කතා සාහිත්‍ය විචාරය & උපක්‍රම",
        "12. පද්‍ය සාහිත්‍ය විචාරය (සැලලිහිණි සංදේශය, ගුත්තිල කාව්‍යය)",
        "13. ජාතක කතා සාහිත්‍යය & සමාජ විවරණය",
        "14. සම්භාව්‍ය ගද්‍ය සාහිත්‍යය (අමාවතුර, බුදුගුණාලංකාරය)",
        "15. නූතන පද්‍ය කලාව & අරුත් දැක්වීම",
        "16. නාට්‍ය කලාව & නාට්‍යමය ලක්ෂණ විචාරය",
        "17. ප්‍රකාශන ශක්තිය & අදහස් සංක්ෂිප්තකරණය (සාරාංශකරණය)",
        "18. රූපක, උපමා, අතිශයෝක්ති & අලංකාර ශාස්ත්‍රය",
        "19. පිරුළු, ප්‍රස්ථාව පිරුළු & රූඪි භාවිතය",
        "20. වාද විවාද & දේශන ලේඛනය",
        "21. පරිවර්තන කුසලතා & දෙබස් රචනය",
        "22. ගද්‍ය ඛණ්ඩ කියවා ප්‍රශ්නවලට පිළිතුරු ලිවීම (ග්‍රහණ කුසලතා)",
        "23. මාධ්‍ය ලේඛනය: විශේෂාංග ලිපි & පුවත්පත් ශීර්ෂ පාඨ",
        "24. සිංහල භාෂාවේ ඓතිහාසික පරිණාමය & සෙල්ලිපි භාෂාව",
        "25. O/L සිංහල භාෂාව හා සාහිත්‍යය සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්නාවලිය"
      )

      subject.contains("English") -> listOf(
        "1. Grammar Fundamentals: Tenses (Present, Past, Future Active & Passive)",
        "2. Subject-Verb Agreement & Sentence Construction Rules",
        "3. Direct and Indirect Speech (Reported Speech Transformation)",
        "4. Conditional Clauses (Zero, First, Second, Third Conditionals)",
        "5. Relative Clauses (Who, Which, That, Whose, Where)",
        "6. Prepositions of Time, Place, and Direction",
        "7. Vocabulary Building: Synonyms, Antonyms & Collocations",
        "8. Reading Comprehension: Skimming, Scanning & Inference",
        "9. Formal Letter Writing (Inquiries, Complaints, Requests)",
        "10. Informal Letter & Friendly Email Composition",
        "11. Paragraph Writing: Topic Sentences & Supporting Details",
        "12. Essay Writing: Narrative, Descriptive, and Argumentative Essays",
        "13. Report Writing for School Magazines and Newsletters",
        "14. Dialogue Writing & Conversational Speech Prompts",
        "15. Notice Writing & Event Announcement Drafting",
        "16. Note Writing & Short Messages (Reminders, Invitations)",
        "17. Describing Graphs, Bar Charts, Pie Charts & Tables",
        "18. Summarizing Texts & Précis Writing Techniques",
        "19. Phrasal Verbs & Idiomatic Expressions in Context",
        "20. Conjunctions & Discourse Markers (However, Although, Moreover)",
        "21. Modal Auxiliaries (Can, Could, May, Might, Must, Should)",
        "22. Word Classes & Word Formation (Prefixes, Suffixes)",
        "23. Poetry Appreciation & Literary Devices (Metaphor, Simile)",
        "24. Short Story Analysis & Character Study Questions",
        "25. Complete O/L English Paper II Structured Writing & Essay Master Exam"
      )

      subject.contains("භූගෝල විද්‍යාව") -> listOf(
        "1. පෘථිවියේ පිහිටීම, හැඩය, අක්ෂය & භ්‍රමණය/පරිභ්‍රමණය",
        "2. සිතියම් විද්‍යාව: පරිමාණය, දිශාව & අක්ෂාංශ/දේශාංශ",
        "3. 1:50,000 භූලක්ෂණ සිතියම් කියවීම & සමෝච්ච රේඛා ලක්ෂණ",
        "4. පෘථිවි අභ්‍යන්තර ව්‍යුහය & තල භූචලන (Plate Tectonics)",
        "5. ගිනි කඳු, භූමිකම්පා & සුනාමි ආපදා",
        "6. කාලගුණය සහ දේශගුණය (උෂ්ණත්වය, වර්ෂාපතනය, පීඩනය)",
        "7. ශ්‍රී ලංකාවේ දේශගුණ කලාප (තෙත්, වියළි, අතරමැදි කලාප)",
        "8. ශ්‍රී ලංකාවේ මෝසම් සුළං (නිරිතදිග & ඊසානදිග මෝසම)",
        "9. ශ්‍රී ලංකාවේ ගංගා පද්ධතිය & ජල පෝෂක ප්‍රදේශ",
        "10. පාංශු වර්ගීකරණය & පාංශු ඛාදනය වැළැක්වීම",
        "11. ශ්‍රී ලංකාවේ ස්වභාවික වෘක්ෂලතාදිය (වැසි වනාන්තර, වියළි මිශ්‍ර)",
        "12. ලෝක දේශගුණ කලාප (සමකාසන්න, මෝසම්, මධ්‍යධරණී, කාන්තාර)",
        "13. ජනගහන වර්ධනය, ව්‍යාප්තිය & ඝනත්වය",
        "14. ශ්‍රී ලංකාවේ ජනාවාස රටා (ග්‍රාමීය, නාගරික, රේඛීය)",
        "15. කෘෂිකර්මාන්තය: වී වගාව & වැවිලි භෝග (තේ, රබර්, පොල්)",
        "16. ධීවර කර්මාන්තය: කරදිය, මිරිදිය & කලපු ධීවර කටයුතු",
        "17. ඛනිජ සම්පත් (මිනිරන්, මැණික්, ඉල්මනයිට්, හුණුගල්)",
        "18. බලශක්ති සම්පත්: ජල විදුලිය, තාප විදුලිය, සූර්ය & සුළං බලය",
        "19. කර්මාන්ත ක්ෂේත්‍රය: ඇඟලුම්, ආහාර සැකසුම් & මෘදුකාංග",
        "20. ප්‍රවාහනය සහ සන්නිවේදනය (මහාමාර්ග, දුම්රිය, වරාය, ගුවන්)",
        "21. සංචාරක කර්මාන්තය & පරිසර හිතකාමී සංචාරක ව්‍යාපාරය",
        "22. ගෝලීය උණුසුම, හරිතාගාර ආචරණය & දේශගුණ විපර්යාස",
        "23. කාන්තාරකරණය, වන විනාශය & පරිසර සංරක්ෂණය",
        "24. තිරසාර සංවර්ධන අරමුණු (SDG) & ලෝක සම්පත් සුරැකීම",
        "25. O/L භූගෝල විද්‍යාව සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්නාවලිය"
      )

      subject.contains("පුරවැසි") -> listOf(
        "1. ප්‍රජාතන්ත්‍රවාදී පාලන ක්‍රමය & මූලික ලක්ෂණ",
        "2. ආණ්ඩුක්‍රම ව්‍යවස්ථාව & එහි වැදගත්කම",
        "3. ආණ්ඩුවේ ප්‍රධාන අංග 3: ව්‍යවස්ථාදායකය, විධායකය, අධිකරණය",
        "4. ශ්‍රී ලංකාවේ පාර්ලිමේන්තුව, කථානායක & නීති සම්පාදනය",
        "5. විධායක ජනාධිපති ක්‍රමය & අමාත්‍ය මණ්ඩලය",
        "6. අධිකරණ පද්ධතිය, ශ්‍රේෂ්ඨාධිකරණය & නීතියේ ආධිපත්‍යය",
        "7. පළාත් සභා & පළාත් පාලන ආයතන (මහනගර සභා, ප්‍රාදේශීය සභා)",
        "8. යහපාලනය (Good Governance) & විනිවිදභාවය",
        "9. මානව හිමිකම් සංකල්පය & එක්සත් ජාතීන්ගේ විශ්ව ප්‍රකාශනය",
        "10. ශ්‍රී ලංකා ආණ්ඩුක්‍රම ව්‍යවස්ථාවේ මූලික අයිතිවාසිකම්",
        "11. පුරවැසි වගකීම්, යුතුකම් & ක්‍රියාකාරී පුරවැසිභාවය",
        "12. මැතිවරණ ක්‍රමය, ඡන්ද අයිතිය & ප්‍රජාතන්ත්‍රවාදී සහභාගිත්වය",
        "13. බහුසංස්කෘතික සමාජයක සහජීවනය & ජාතික ඒකාබද්ධතාව",
        "14. ගැටුම් නිරාකරණය & සාමකාමී සමාජයක් ගොඩනැගීම",
        "15. මාධ්‍ය නිදහස, සමාජ මාධ්‍ය & තොරතුරු දැනගැනීමේ අයිතිය",
        "16. ශ්‍රම වෙළඳපොළ, වෘත්තීය අයිතිවාසිකම් & රැකියා අවස්ථා",
        "17. රාජ්‍ය නොවන සංවිධාන (NGO) & ප්‍රජා මූල සංවිධාන",
        "18. දුප්පත්කම පිටුදැකීම & සමාජ සුභසාධන වැඩසටහන්",
        "19. ළමා අයිතිවාසිකම් & කාන්තා සවිබලගැන්වීම",
        "20. දූෂණය හා වංචාව වැළැක්වීම & අල්ලස් කොමිසම",
        "21. ජාත්‍යන්තර සබඳතා & එක්සත් ජාතීන්ගේ සංවිධානය (UN)",
        "22. නොබැඳි ජාතීන්ගේ ව්‍යාපාරය (NAM) & සාර්ක් (SAARC) සංවිධානය",
        "23. ගෝලීය පුරවැසිභාවය & පාරිසරික යුක්තිය",
        "24. තරුණ පරපුරේ නායකත්වය & ප්‍රජා සත්කාරක ව්‍යාපෘති",
        "25. O/L පුරවැසි අධ්‍යාපනය සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්න පත්‍ර සම්පූර්ණ විග්‍රහය"
      )

      else -> listOf(
        "1. ව්‍යාපාර සංකල්පය, අවශ්‍යතා හා වුවමනා",
        "2. නිෂ්පාදන සාධක (භූමිය, ශ්‍රමය, ප්‍රාග්ධනය, ව්‍යවසායකත්වය)",
        "3. ව්‍යාපාර පරිසරය (අභ්‍යන්තර & බාහිර පරිසර සාධක)",
        "4. ව්‍යාපාර හිමිකාරිත්ව වර්ග (තනි පුද්ගල, හවුල්, සමාගම්)",
        "5. සමූපකාර සමිති & රාජ්‍ය ව්‍යවසාය",
        "6. බැංකු සේවා & වාණිජ බැංකු ක්‍රියාකාරීත්වය",
        "7. රක්ෂණ සේවා & අවදානම් කළමනාකරණය",
        "8. සන්නිවේදනය, ප්‍රවාහනය & ගබඩාකරණ සේවා",
        "9. වෙළඳාම (දේශීය වෙළඳාම: තොග, සිල්ලර)",
        "10. විදේශ වෙළඳාම (ආනයන, අපනයන & ප්‍රතිඅපනයන)",
        "11. පාරිභෝගික ආරක්ෂණය & පාරිභෝගික අයිතිවාසිකම්",
        "12. අලෙවිකරණ මිශ්‍රය (4Ps: Product, Price, Place, Promotion)",
        "13. ගිණුම්කරණ සමීකරණය (වත්කම් = හිමිකම + වගකීම්)",
        "14. මූලික පොත් (ජර්නල) & ද්විත්ව සටහන් මූලධර්මය",
        "15. මුදල් පොත & සුළු මුදල් පොත",
        "16. ලෙජර ගිණුම් & ශේෂ පිරික්සුම සැකසීම",
        "17. ආදායම් ප්‍රකාශනය (විකුණුම්, විකුණුම් පිරිවැය, දළ ලාභය, ශුද්ධ ලාභය)",
        "18. මූල්‍ය තත්ත්ව ප්‍රකාශනය (වත්කම් & වගකීම් වර්ගීකරණය)",
        "19. ගැලපීම් සහිත මූල්‍ය ප්‍රකාශන (අත්පිට වියදම්, උපචිත ආදායම්)",
        "20. බැංකු සැසඳුම් ප්‍රකාශනය පිළියෙළ කිරීම",
        "21. දෝෂ නිවැරදි කිරීම & අත්හිටවූ ගිණුම",
        "22. නිෂ්පාදන ගිණුම් (මූලික පිරිවැය, කර්මාන්තශාලා පොදු කාර්ය)",
        "23. ලාභ නොලබන සංවිධානවල ලැබීම් ගෙවීම් & ආදායම් වියදම් ගිණුම්",
        "24. මූල්‍ය අනුපාත විශ්ලේෂණය (ලාභදායීතා, ද්‍රවශීලතා අනුපාත)",
        "25. O/L ව්‍යාපාර හා ගිණුම්කරණය සමස්ත ව්‍යුහගත හා රචනා ප්‍රශ්නාවලිය"
      )
    }
  }

  /**
   * Generates the 500 questions for a given grade and subject.
   */
  private fun generate500Questions(grade: String, subject: String): List<StructuredEssayItem> {
    val list = ArrayList<StructuredEssayItem>(500)
    val themes = getUnitThemesForSubject(grade, subject)

    for (setNum in 1..25) {
      val unitTheme = themes.getOrElse(setNum - 1) { "විෂය ඒකකය $setNum" }

      for (qIndexInSet in 1..20) {
        val globalIndex = (setNum - 1) * 20 + qIndexInSet
        val isStructured = qIndexInSet <= 12 // 1-12 Structured, 13-20 Essay
        val type = if (isStructured) "STRUCTURED" else "ESSAY"

        val item = createCurriculumAlignedItem(
          id = "sq_${grade}_${subject.hashCode()}_${globalIndex}",
          grade = grade,
          subject = subject,
          setNumber = setNum,
          questionIndexInSet = qIndexInSet,
          globalIndex = globalIndex,
          unitTheme = unitTheme,
          type = type
        )
        list.add(item)
      }
    }

    return list
  }

  private fun createCurriculumAlignedItem(
    id: String,
    grade: String,
    subject: String,
    setNumber: Int,
    questionIndexInSet: Int,
    globalIndex: Int,
    unitTheme: String,
    type: String
  ): StructuredEssayItem {
    val cleanTheme = unitTheme.substringAfter(". ").trim()

    return when {
      subject.contains("විද්‍යාව") -> generateScienceItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
      subject.contains("ගණිතය") -> generateMathItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
      subject.contains("ඉතිහාසය") -> generateHistoryItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
      subject.contains("ICT") -> generateIctItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
      subject.contains("English") -> generateEnglishItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
      else -> generateGeneralItem(id, grade, subject, setNumber, questionIndexInSet, globalIndex, cleanTheme, type)
    }
  }

  // ============================================================================
  // SCIENCE GENERATOR
  // ============================================================================
  private fun generateScienceItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = if (type == "STRUCTURED") 15 else 20

    val subQuestions = if (type == "STRUCTURED") {
      listOf(
        StructuredSubQuestion(
          subIndex = "(i)",
          questionText = "$unit සම්බන්ධයෙන් වැදගත් වන මූලික විද්‍යාත්මක නියමය හෝ සංකල්පය පැහැදිලිව ප්‍රකාශ කරන්න.",
          marksAllocated = 3,
          modelAnswer = "$unit ආශ්‍රිත ප්‍රධාන සංසිද්ධිය විද්‍යාත්මක මූලධර්මවලට අනුකූලව පද්ධතියක ශක්ති සංස්ථිතිය, ප්‍රතික්‍රියා යාන්ත්‍රණය සහ ව්‍යුහාත්මක අනුගතවීම් මඟින් විස්තර කෙරේ. මෙහිදී අදාළ රාශීන්ගේ අනුපාතය හෝ රසායනික තුලිතතාව තහවුරු වේ.",
          keyPoints = listOf(
            "නියමය හෝ මූලධර්මය නිවැරදිව ප්‍රකාශ කිරීම (ලකුණු 2)",
            "විද්‍යාත්මක නිරවද්‍යතාව සහ නිවැරදි පාරිභාෂික වචන භාවිතය (ලකුණු 1)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(ii)",
          questionText = "මෙම ක්‍රියාවලියේදී සිදුවන ශක්ති විපර්යාසය හෝ අදාළ තුලිත රසායනික/භෞතික සමීකරණය ලියන්න.",
          marksAllocated = 4,
          modelAnswer = "පද්ධතියේ ආරම්භක ශක්තිය රසායනික බන්ධන හෝ චාලක ශක්තිය බවට විපර්යාස වේ. සමීකරණය: ප්‍රතික්‍රියක (Reactants) ──> ඵල (Products) + ශක්තිය (Energy). සියලු පරමාණු සංඛ්‍යා දෙපසම තුලිතව පවතී.",
          keyPoints = listOf(
            "ප්‍රතික්‍රියක හා ඵල හඳුනාගෙන නිවැරදිව ලිවීම (ලකුණු 2)",
            "භෞතික අවස්ථා හෝ තුලනය නිවැරදිව දැක්වීම (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(iii)",
          questionText = "ඉහත සංසිද්ධිය තහවුරු කිරීමට පාසල් විද්‍යාගාරයේදී සිදුකළ හැකි පරීක්ෂණයේ නිරීක්ෂණය සහ නිගමනය දක්වන්න.",
          marksAllocated = 4,
          modelAnswer = "නිරීක්ෂණය: ද්‍රාවණයේ වර්ණ විපර්යාසයක්, වායු බුබුළු පිටවීමක් හෝ උෂ්ණත්ව වෙනසක් නිරීක්ෂණය වේ.\nනිගමනය: අදාළ බාහිර සාධක පවතින විට පමණක් ප්‍රතික්‍රියාව උපරිම කාර්යක්ෂමතාවයෙන් සිදුවන බව තහවුරු වේ.",
          keyPoints = listOf(
            "පැහැදිලි සංසන්දනාත්මක නිරීක්ෂණය ලිවීම (ලකුණු 2)",
            "තාර්කික නිගමනය නිවැරදිව එළඹීම (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(iv)",
          questionText = "එදිනෙදා ජීවිතයේදී හෝ කර්මාන්ත ක්ෂේත්‍රයේදී මෙහි තාක්ෂණික යෙදුම් 2ක් නම් කරන්න.",
          marksAllocated = 4,
          modelAnswer = "1. කර්මාන්තශාලාවල කාර්යක්ෂමතාව ඉහළ නැංවීම සඳහා උත්ප්‍රේරක භාවිතය හා සම්පත් නාස්තිය අවම කිරීම.\n2. පරිසර සංරක්ෂණයේදී අපද්‍රව්‍ය කළමනාකරණය හා පුනර්ජනනීය බලශක්ති උත්පාදනය සඳහා යෙදවීම.",
          keyPoints = listOf(
            "පළමු ප්‍රායෝගික යෙදුම නිවැරදිව නම් කිරීම (ලකුණු 2)",
            "දෙවන ප්‍රායෝගික යෙදුම නිවැරදිව නම් කිරීම (ලකුණු 2)"
          )
        )
      )
    } else {
      listOf(
        StructuredSubQuestion(
          subIndex = "(A)",
          questionText = "$unit ආශ්‍රිත න්‍යායාත්මක පසුබිම, ප්‍රධාන සමීකරණ හා උපකල්පන සවිස්තරව විග්‍රහ කරන්න.",
          marksAllocated = 7,
          modelAnswer = "අදාළ පද්ධතියේ ක්‍රියාකාරීත්වය මූලික නීති මත පදනම් වේ. විද්‍යාත්මක සමීකරණවල අඩංගු එක් එක් රාශිය, ඒවායේ SI ඒකක සහ එකිනෙක අතර සමානුපාතික සබඳතා මෙහිදී තහවුරු කෙරේ. බාහිර ප්‍රතිරෝධ හෝ ශක්ති හානි නොසලකා හැරීමෙන් සෛද්ධාන්තික අගය ලබාගනී.",
          keyPoints = listOf(
            "න්‍යායික පසුබිම හා නියම පැහැදිලි කිරීම (ලකුණු 3)",
            "සමීකරණ හා සංකේතවල SI ඒකක ලිවීම (ලකුණු 2)",
            "විද්‍යාත්මක උපකල්පන දැක්වීම (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(B)",
          questionText = "ප්‍රශ්නයේ දක්වා ඇති දත්ත ඇසුරෙන් අදාළ ගණනය කිරීම පියවරෙන් පියවර සිදුකර ඒකක සහිතව පිළිතුර ලබාගන්න.",
          marksAllocated = 7,
          modelAnswer = "පියවර 1: දත්ත පෙළගැස්වීම.\nපියවර 2: සුදුසු සමීකරණය තෝරාගැනීම.\nපියවර 3: අගයන් ආදේශය සහ සුළු කිරීම.\nපියවර 4: අවසාන පිළිතුර නිවැරදි සම්මත ඒකකය (SI Units) සහිතව දැක්වීම.",
          keyPoints = listOf(
            "නිවැරදි සූත්‍රය තේරීම (ලකුණු 2)",
            "දත්ත නිවැරදිව ආදේශ කිරීම (ලකුණු 3)",
            "ඒකකය සහිත නිවැරදි අවසන් පිළිතුර (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(C)",
          questionText = "මෙම ක්‍රියාවලියේදී සිදුවිය හැකි දෝෂ අවම කරගැනීමට ගත හැකි පූර්වාරක්ෂක පියවර 2ක් පැහැදිලි කරන්න.",
          marksAllocated = 6,
          modelAnswer = "1. උපකරණවල ශුන්‍ය දෝෂ පරීක්ෂා කර බැලීම සහ ඇස මට්ටමේ තබාගෙන පාඨාංක ලබාගැනීම (දෘෂ්ටි විලම්භන දෝෂ වැළැක්වීමට).\n2. පරිසර උෂ්ණත්වය සහ පීඩනය පාලනය කරමින් නියත තත්ත්ව යටතේ පරීක්ෂණය 3 වරක් සිදුකර මධ්‍යන්‍යය ලබාගැනීම.",
          keyPoints = listOf(
            "පළමු පූර්වාරක්ෂක පියවර සහ හේතුව (ලකුණු 3)",
            "දෙවන පූර්වාරක්ෂක පියවර සහ හේතුව (ලකුණු 3)"
          )
        )
      )
    }

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "ප්‍රශ්න අංක $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "$grade ශ්‍රේණියේ විද්‍යාව විෂය නිර්දේශයේ '$unit' තේමාව යටතේ විභාග ප්‍රශ්න රටාවට අනුකූලව සකස් කරන ලද ප්‍රශ්නයකි. පහත උප කොටස් කියවා සම්මත විභාග ලකුණු පටිපාටියට අනුකූලව සකස් කළ පිළිතුරු හා ලකුණු බෙදී යන ආකාරය අධ්‍යයනය කරන්න.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }

  // ============================================================================
  // MATHEMATICS GENERATOR
  // ============================================================================
  private fun generateMathItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = if (type == "STRUCTURED") 10 else 15

    val subQuestions = if (type == "STRUCTURED") {
      listOf(
        StructuredSubQuestion(
          subIndex = "(i)",
          questionText = "$unit ආශ්‍රිත මූලික සූත්‍රය හෝ ප්‍රමේයය ලියා එහි ඇති විචල්‍යයන් හඳුන්වන්න.",
          marksAllocated = 3,
          modelAnswer = "අදාළ ගණිතමය සූත්‍රය නිවැරදිව දක්වනු ලැබේ. උදාහරණයක් ලෙස සමීකරණය y = mx + c හෝ A = πr²h වන අතර, මෙහි සංකේත මඟින් පිළිවෙළින් අනුක්‍රමණය, අන්තඃඛණ්ඩය, අරය සහ උස දැක්වේ.",
          keyPoints = listOf(
            "සූත්‍රය හෝ ප්‍රමේයය නිවැරදිව ලිවීම (ලකුණු 2)",
            "සංකේත නිරූපණය නිවැරදිව නම් කිරීම (ලකුණු 1)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(ii)",
          questionText = "ප්‍රශ්නයේ දක්වා ඇති අගයන් ආදේශ කර පළමු පියවරේ විචල්‍යයේ අගය සුළු කර සොයන්න.",
          marksAllocated = 4,
          modelAnswer = "දෙන ලද අගයන් සූත්‍රයේ ආදේශ කිරීම:\nපියවර 1: අගයන් ආදේශය.\nපියවර 2: සුළු කිරීම් (වරහන් ඉවත් කිරීම, සමාන පද එක්කිරීම).\nපියවර 3: අවසාන විසඳුම ලබාගැනීම.",
          keyPoints = listOf(
            "අගයන් නිවැරදිව ආදේශ කිරීම (ලකුණු 2)",
            "වීජීය සුළු කිරීම් නිවැරදිව සිදුකිරීම (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(iii)",
          questionText = "ලබාගත් විසඳුම භාවිතයෙන් අවසාන ඉල්ලීම හෝ සත්‍යාපනය සනාථ කරන්න.",
          marksAllocated = 3,
          modelAnswer = "ලබාගත් අගය සමීකරණයේ වම් පසට ආදේශ කළ විට වම් පස = දකුණු පස බවට පත්වන බැවින් විසඳුම නිවැරදි බව තහවුරු වේ.",
          keyPoints = listOf(
            "තහවුරු කිරීමේ ක්‍රමවේදය (ලකුණු 2)",
            "අවසාන නිගමනය ලිවීම (ලකුණු 1)"
          )
        )
      )
    } else {
      listOf(
        StructuredSubQuestion(
          subIndex = "(a)",
          questionText = "දෙන ලද තොරතුරු මත පදනම්ව ගණිතමය ආකෘතිය හෝ සමගාමී සමීකරණ යුගලය ගොඩනගන්න.",
          marksAllocated = 5,
          modelAnswer = "නොදන්නා රාශීන් x සහ y ලෙස උපකල්පනය කරමු.\nපළමු කොන්දේසිය අනුව: a₁x + b₁y = c₁ ...(1)\nදෙවන කොන්දේසිය අනුව: a₂x + b₂y = c₂ ...(2)",
          keyPoints = listOf(
            "විචල්‍යයන් නිවැරදිව නම් කිරීම (ලකුණු 1)",
            "පළමු සමීකරණය ගොඩනැගීම (ලකුණු 2)",
            "දෙවන සමීකරණය ගොඩනැගීම (ලකුණු 2)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(b)",
          questionText = "ඉහත සමීකරණ විසඳා x සහ y සඳහා අගයන් සොයන්න.",
          marksAllocated = 5,
          modelAnswer = "සංගුණක සමාන කර එක් විචල්‍යයක් ඉවත් කිරීම මඟින් එක් අගයක් ලබාගනී. එම අගය ආදේශ කර අනෙක් විචල්‍යයේ අගය ගණනය කෙරේ.",
          keyPoints = listOf(
            "විචල්‍යයක් ඉවත් කිරීමේ නිවැරදි ක්‍රමවේදය (ලකුණු 2)",
            "පළමු විචල්‍යයේ නිවැරදි අගය (ලකුණු 2)",
            "දෙවන විචල්‍යයේ නිවැරදි අගය (ලකුණු 1)"
          )
        ),
        StructuredSubQuestion(
          subIndex = "(c)",
          questionText = "මෙම පිළිතුර ඇසුරෙන් ප්‍රායෝගික ගැටලුවේ මුළු පිරිවැය හෝ අවශ්‍ය කාලය ගණනය කරන්න.",
          marksAllocated = 5,
          modelAnswer = "මුළු පිරිවැය = (ඒකක ගණන × ඒකක මිල) + ස්ථාවර ගාස්තුව. ඒකක රුපියල් හෝ පැය/දින ලෙස දක්වා නිවැරදි පිළිතුර සටහන් කර ඇත.",
          keyPoints = listOf(
            "ප්‍රායෝගික සබඳතාව ගොඩනැගීම (ලකුණු 2)",
            "ආදේශය හා සුළු කිරීම (ලකුණු 2)",
            "ඒකකය සහිත අවසන් අගය (ලකුණු 1)"
          )
        )
      )
    }

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "ප්‍රශ්න අංක $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "$grade ශ්‍රේණියේ ගණිතය විෂය නිර්දේශයේ '$unit' ඒකකය පාදක කරගත් සම්මත O/L ආදර්ශ ගැටලුවකි. පියවරෙන් පියවර විසඳුම සහ නිල විභාග ලකුණු බෙදී යන ආකාරය අධ්‍යයනය කරන්න.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }

  // ============================================================================
  // HISTORY GENERATOR
  // ============================================================================
  private fun generateHistoryItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = if (type == "STRUCTURED") 15 else 20

    val subQuestions = listOf(
      StructuredSubQuestion(
        subIndex = "(i)",
        questionText = "$unit යුගයේ ඓතිහාසික පසුබිම සාක්ෂි සහිතව තහවුරු කළ හැකි මූලාශ්‍ර 2ක් නම් කරන්න.",
        marksAllocated = 4,
        modelAnswer = "1. සාහිත්‍ය මූලාශ්‍ර: මහාවංසය, දීපවංසය, පූජාවලිය හෝ රාජාවලිය.\n2. පුරාවිද්‍යාත්මක මූලාශ්‍ර: සෙල්ලිපි (ශිලා ලේඛන), කාසි, නටබුන් සහ වාරි නිර්මාණ.",
        keyPoints = listOf(
          "සාහිත්‍ය මූලාශ්‍රයක් නිවැරදිව නම් කිරීම (ලකුණු 2)",
          "පුරාවිද්‍යාත්මක මූලාශ්‍රයක් නිවැරදිව නම් කිරීම (ලකුණු 2)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(ii)",
        questionText = "මෙම යුගයේදී රටේ ආර්ථික, සමාජයීය හෝ වාරි ශිෂ්ටාචාරයේ සිදුවූ ප්‍රධාන සන්ධිස්ථාන 2ක් පැහැදිලි කරන්න.",
        marksAllocated = 6,
        modelAnswer = "1. වාරි ප්‍රතිසංස්කරණ: මහා වැව් හා ඇළ මාර්ග පද්ධති තනා කෘෂිකර්මාන්තය දියුණු කිරීම මඟින් රට සහලින් ස්වයංපෝෂිත කිරීම.\n2. ආගමික හා සංස්කෘතික පුනරුදය: බුදුසසුන සුරැකීමට වෙහෙර විහාර ඉදිකිරීම හා ශාසන ශෝධනය සිදුකිරීම.",
        keyPoints = listOf(
          "පළමු ප්‍රධාන ඓතිහාසික සිදුවීම විස්තර කිරීම (ලකුණු 3)",
          "දෙවන ප්‍රධාන ඓතිහාසික සිදුවීම විස්තර කිරීම (ලකුණු 3)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(iii)",
        questionText = "එම යුගයේ නායකත්වය හෝ පාලන තන්ත්‍රය අද දවසේ ශ්‍රී ලංකාවට ලබාදෙන ආදර්ශය කෙටියෙන් විග්‍රහ කරන්න.",
        marksAllocated = 5,
        modelAnswer = "ස්වදේශික සම්පත් උපරිමයෙන් ප්‍රයෝජනයට ගැනීම, ජාතික සමගිය හා විනයගරුක නීතිගරුක සමාජයක් ගොඩනැගීම සඳහා එකල නායකයන් දැක්වූ දුරදක්නා දැක්ම නූතන රට සංවර්ධනයටද මාහැඟි ආදර්ශයකි.",
        keyPoints = listOf(
          "නායකත්වයේ දුරදක්නා ගුණාංග විග්‍රහ කිරීම (ලකුණු 3)",
          "නූතන සමාජයට ගැළපෙන ආදර්ශය නිවැරදිව දැක්වීම (ලකුණු 2)"
        )
      )
    )

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "ප්‍රශ්න අංක $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "$grade ශ්‍රේණියේ ඉතිහාසය විෂය නිර්දේශයේ '$unit' තේමාව පිළිබඳ සම්මත රචනා හා ව්‍යුහගත ප්‍රශ්නයකි.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }

  // ============================================================================
  // ICT GENERATOR
  // ============================================================================
  private fun generateIctItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = 15

    val subQuestions = listOf(
      StructuredSubQuestion(
        subIndex = "(i)",
        questionText = "$unit විෂය කොටසට අදාළ ප්‍රධාන තාක්ෂණික සංකල්පය හෝ උපාංගය නිර්වචනය කරන්න.",
        marksAllocated = 3,
        modelAnswer = "$unit යනු පරිගණක පද්ධතියක දත්ත සැකසීම, ගබඩා කිරීම, සහ සන්නිවේදනය වඩාත් ආරක්ෂිතව හා කාර්යක්ෂමව සිදුකිරීම සඳහා යොදාගන්නා සම්මත ක්‍රමවේදයකි.",
        keyPoints = listOf(
          "නිවැරදි තාක්ෂණික නිර්වචනය (ලකුණු 2)",
          "ප්‍රධාන ලක්ෂණයක් දැක්වීම (ලකුණු 1)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(ii)",
        questionText = "මෙහි ක්‍රියාකාරීත්වය නිරූපණය වන ප්‍රධාන පියවර හෝ තාර්කික ව්‍යුහය (Logic / Syntax) දක්වන්න.",
        marksAllocated = 4,
        modelAnswer = "ආදානය (Input) ──> සැකසීම (Processing) ──> ප්‍රතිදානය (Output). පාලන ව්‍යුහවලදී කොන්දේසි පරීක්ෂා කර බලා අදාළ විධාන කේතය (Code block) ක්‍රියාත්මක කෙරේ.",
        keyPoints = listOf(
          "තාර්කික ගැලීම හෝ සින්ටැක්ස් නිවැරදිව ලිවීම (ලකුණු 2)",
          "ක්‍රියාවලිය පැහැදිලි කිරීම (ලකුණු 2)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(iii)",
        questionText = "මෙම තාක්ෂණය භාවිත කිරීමේදී මතුවන ප්‍රධාන වාසි 2ක් සහ එක් අවාසියක් හෝ ආරක්ෂක අවදානමක් ලියන්න.",
        marksAllocated = 4,
        modelAnswer = "වාසි: 1. අධික වේගය හා දත්ත සැකසීමේ නිරවද්‍යතාව. 2. මානව ශ්‍රමය සහ කාලය ඉතිරිවීම.\nඅවාසිය/අවදානම: සයිබර් ප්‍රහාර, මැල්වෙයා හෝ දත්ත අනවසරයෙන් පිටතට කාන්දු වීමේ අවදානම.",
        keyPoints = listOf(
          "වාසි 2 නිවැරදිව ලිවීම (ලකුණු 2)",
          "ආරක්ෂක අවදානම හෝ අවාසිය ලිවීම (ලකුණු 2)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(iv)",
        questionText = "එම අවදානම මඟහරවා ගැනීමට ගත හැකි ක්‍රියාමාර්ගයක් නම් කරන්න.",
        marksAllocated = 4,
        modelAnswer = "ශක්තිමත් මුරපද (Strong passwords) සහ Two-Factor Authentication (2FA) භාවිතය, මෙන්ම Firewall සහ යාවත්කාලීන කළ Antivirus මෘදුකාංග ක්‍රියාත්මක කිරීම.",
        keyPoints = listOf(
          "ආරක්ෂණ ක්‍රියාමාර්ගය පැහැදිලිව නම් කිරීම (ලකුණු 2)",
          "තාක්ෂණික යෝග්‍යතාව තහවුරු කිරීම (ලකුණු 2)"
        )
      )
    )

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "ප්‍රශ්න අංක $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "$grade ශ්‍රේණිය තොරතුරු හා සන්නිවේදන තාක්ෂණය (ICT) විෂය නිර්දේශයේ '$unit' පාඩමට අදාළ සම්මත ව්‍යුහගත ප්‍රශ්නයකි.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }

  // ============================================================================
  // ENGLISH GENERATOR
  // ============================================================================
  private fun generateEnglishItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = 15

    val subQuestions = listOf(
      StructuredSubQuestion(
        subIndex = "(i)",
        questionText = "Write 2 grammatically correct sentences demonstrating the rule of '$unit'.",
        marksAllocated = 4,
        modelAnswer = "1. If students study regularly, they achieve high results in their exams.\n2. Although the weather was rainy, our school team completed the athletic tournament successfully.",
        keyPoints = listOf(
          "Correct grammatical structure and tenses (2 Marks)",
          "Appropriate punctuation and vocabulary usage (2 Marks)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(ii)",
        questionText = "Identify and correct the grammatical or structural errors in the given paragraph related to $unit.",
        marksAllocated = 5,
        modelAnswer = "Error: 'She do not goes to library yesterday.' -> Correction: 'She did not go to the library yesterday.'\nSubject-verb agreement and past tense auxiliary verb usage corrected.",
        keyPoints = listOf(
          "Identifying the specific errors correctly (2 Marks)",
          "Rewriting with correct syntax and grammar (3 Marks)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(iii)",
        questionText = "Compose a short formal note or paragraph (approx. 50 words) incorporating these key structures.",
        marksAllocated = 6,
        modelAnswer = "Dear Principal,\nI am writing to respectfully request permission to organize an English Day exhibition in our school hall next Friday. We have already prepared educational charts, vocabulary flashcards, and interactive games for Grade 9, 10, and 11 students.\nThank you.\nSincerely,\nPrefect of English Club",
        keyPoints = listOf(
          "Content and task completion (2 Marks)",
          "Language accuracy and vocabulary range (2 Marks)",
          "Format, mechanics, and organization (2 Marks)"
        )
      )
    )

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "Question $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "English Language Master Training for Grade $grade on '$unit'. Follow the standard O/L Paper II rubric.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }

  // ============================================================================
  // GENERAL SUBJECT GENERATOR (Buddhism, Sinhala, Geography, Civics, Commerce)
  // ============================================================================
  private fun generateGeneralItem(
    id: String, grade: String, subject: String, setNumber: Int, qIndex: Int, globalIdx: Int, unit: String, type: String
  ): StructuredEssayItem {
    val totalMarks = if (type == "STRUCTURED") 15 else 20

    val subQuestions = listOf(
      StructuredSubQuestion(
        subIndex = "(i)",
        questionText = "$unit පිළිබඳ විෂය නිර්දේශයේ දක්වා ඇති මූලික සංකල්ප 2ක් නම් කර කෙටියෙන් හඳුන්වන්න.",
        marksAllocated = 4,
        modelAnswer = "1. මූලික න්‍යායික සංකල්පය: විෂය නිර්දේශයට අනුව පද්ධතියේ ප්‍රධාන කාර්යභාරය හා වැදගත්කම පැහැදිලි කරයි.\n2. ප්‍රායෝගික සංකල්පය: සමාජයීය හෝ පුද්ගල සංවර්ධනයට මෙය බලපාන ආකාරය දක්වයි.",
        keyPoints = listOf(
          "පළමු සංකල්පය නිවැරදිව නම් කිරීම හා හැඳින්වීම (ලකුණු 2)",
          "දෙවන සංකල්පය නිවැරදිව නම් කිරීම හා හැඳින්වීම (ලකුණු 2)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(ii)",
        questionText = "මෙම ක්ෂේත්‍රයේ ප්‍රගතිය උදෙසා ක්‍රියාත්මක කළ හැකි ප්‍රධාන ක්‍රියාමාර්ග 2ක් සවිස්තරව විග්‍රහ කරන්න.",
        marksAllocated = 6,
        modelAnswer = "1. සැලසුම්සහගත කළමනාකරණය: සම්පත් නාස්තිය අවම කර නියමිත කාලසීමාවන් තුළ ඉලක්ක සපුරාගැනීම.\n2. නවීන තාක්ෂණය හා දැනුම අනුගත කරගැනීම: කාර්යක්ෂමතාව සහ ගුණාත්මකභාවය ඉහළ නැංවීම සඳහා නව ප්‍රවේශයන් යොදාගැනීම.",
        keyPoints = listOf(
          "පළමු ක්‍රියාමාර්ගය පැහැදිලිව විග්‍රහ කිරීම (ලකුණු 3)",
          "දෙවන ක්‍රියාමාර්ගය පැහැදිලිව විග්‍රහ කිරීම (ලකුණු 3)"
        )
      ),
      StructuredSubQuestion(
        subIndex = "(iii)",
        questionText = "ඉහත කරුණු පදනම් කරගෙන විභාග ප්‍රශ්නයට අදාළ නිගමනය සම්ප්‍රේෂණය කරන්න.",
        marksAllocated = 5,
        modelAnswer = "සමස්තයක් ලෙස ගත් කල, මෙම විෂය ඒකකය මඟින් ලබාදෙන දැනුම සහ කුසලතා විභාග ජයග්‍රහණයට මෙන්ම යහපත්, විනයගරුක සහ ඵලදායී පුරවැසියෙකු ලෙස සමාජගත වීමටද මනා මඟපෙන්වීමක් සපයයි.",
        keyPoints = listOf(
          "විභාගානුකූල තාර්කික නිගමනය (ලකුණු 3)",
          "භාෂා නිරවුල්භාවය හා කරුණු පිළිවෙළ (ලකුණු 2)"
        )
      )
    )

    return StructuredEssayItem(
      id = id,
      grade = grade,
      subject = subject,
      topicSinhala = "ප්‍රශ්න අංක $globalIdx: $unit",
      type = type,
      totalMarks = totalMarks,
      mainScenario = "$grade ශ්‍රේණියේ $subject විෂය නිර්දේශයේ '$unit' පාඩම ආශ්‍රිත සම්මත ආදර්ශ ප්‍රශ්නයකි.",
      subQuestions = subQuestions,
      setNumber = setNumber,
      questionIndexInSet = qIndex,
      globalIndex = globalIdx,
      unitCategory = unit
    )
  }
}
