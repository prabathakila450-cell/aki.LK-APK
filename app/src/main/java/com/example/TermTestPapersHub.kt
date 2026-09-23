package com.example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class TermTestPaperItem(
  val id: String,
  val grade: String,
  val subject: String,
  val term: Int, // 1 = 1st Term, 2 = 2nd Term, 3 = 3rd Term
  val year: Int, // 2024, 2023, 2022, 2021, 2020
  val titleSinhala: String,
  val sourceOrProvince: String,
  val timeDuration: String,
  val totalMarks: String,
  val paperStructure: String,
  val questionPaperUrl: String,
  val answerSchemeUrl: String,
  val unitCoverage: String
)

object TermTestPapersRepository {

  fun getTermTestPapers(grade: String, subjectNameSinhala: String, term: Int): List<TermTestPaperItem> {
    if (grade == "11" && (subjectNameSinhala.contains("නර්තන") || subjectNameSinhala.contains("Dance", ignoreCase = true))) {
      return listOf(
        TermTestPaperItem(
          id = "dancing_gr11_term3_paper_2023_nc",
          grade = "11",
          subject = "නර්තනය",
          term = 3,
          year = 2023,
          titleSinhala = "11 ශ්‍රේණිය - නර්තනය තෙවන වාර විභාග ප්‍රශ්න පත්‍රය (උතුරු මැද පළාත - 2023)",
          sourceOrProvince = "උතුරු මැද පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (North Central Province)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ හා කෙටි ප්‍රශ්න 40) + II කොටස (ප්‍රස්තාර, රචනා හා විචාර ප්‍රශ්න 07) සම්පූර්ණ ප්‍රශ්න පත්‍රය",
          questionPaperUrl = "https://drive.google.com/file/d/1dancing_gr11_term3_exam_paper_2023/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1dancing_gr11_term3_exam_paper_2023/preview",
          unitCoverage = "11 ශ්‍රේණිය නර්තනය සම්පූර්ණ විෂය නිර්දේශය (දේශීය නර්තන, භාරතීය සම්ප්‍රදාය, වාද්‍ය භාණ්ඩ, ශාන්තිකර්ම, මුද්‍රා නාට්‍ය)"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("සංගීත") || subjectNameSinhala.contains("Music", ignoreCase = true))) {
      return listOf(
        TermTestPaperItem(
          id = "music_gr11_ol_paper_2024_1",
          grade = "11",
          subject = "සංගීතය",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සංගීතය සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍රය 01",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ පෙරදිග සංගීතය විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1AdPsYNZ6512nKGfwJ2ldP3Ds6w35Atlb/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1AdPsYNZ6512nKGfwJ2ldP3Ds6w35Atlb/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ සංගීතය විෂය නිර්දේශය (O/L Music Syllabus)"
        ),
        TermTestPaperItem(
          id = "music_gr11_ol_paper_2024_2",
          grade = "11",
          subject = "සංගීතය",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සංගීතය සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍රය 02",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ පෙරදිග සංගීතය විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1bhV9Fb0AUCSWuCQfib785FwyU7fQPgg-/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1bhV9Fb0AUCSWuCQfib785FwyU7fQPgg-/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ සංගීතය විෂය නිර්දේශය (O/L Music Syllabus)"
        ),
        TermTestPaperItem(
          id = "music_gr11_ol_paper_2024_3",
          grade = "11",
          subject = "සංගීතය",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සංගීතය සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍රය 03",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ පෙරදිග සංගීතය විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1GCTmHkiNb4GQx5Ss-GeiAPZZHytI0cLx/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1GCTmHkiNb4GQx5Ss-GeiAPZZHytI0cLx/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ සංගීතය විෂය නිර්දේශය (O/L Music Syllabus)"
        ),
        TermTestPaperItem(
          id = "music_gr11_ol_paper_2024_4",
          grade = "11",
          subject = "සංගීතය",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සංගීතය සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍රය 04",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ පෙරදිග සංගීතය විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/13nXWcjZseUaveBnaafPfediwIYyO60IA/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/13nXWcjZseUaveBnaafPfediwIYyO60IA/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ සංගීතය විෂය නිර්දේශය (O/L Music Syllabus)"
        ),
        TermTestPaperItem(
          id = "music_gr11_ol_paper_2024_5",
          grade = "11",
          subject = "සංගීතය",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සංගීතය සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍රය 05",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ පෙරදිග සංගීතය විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1h0rMbmIDQMQizn8YieYLpWgXHoNwcKFJ/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1h0rMbmIDQMQizn8YieYLpWgXHoNwcKFJ/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ සංගීතය විෂය නිර්දේශය (O/L Music Syllabus)"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("විද්‍යාව") || subjectNameSinhala.contains("Science", ignoreCase = true))) {
      return listOf(
        TermTestPaperItem(
          id = "science_gr11_ol_paper_2024",
          grade = "11",
          subject = "විද්‍යාව",
          term = term,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - විද්‍යාව සාමාන්‍ය පෙළ (සා/පෙළ) විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (G.C.E. O/L Examination)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ සා/පෙළ විභාග ප්‍රශ්න පත්‍රය හා නිල ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1TuWE6z1n8Qhb_Rd5FsiHFTBhI2AUukGo/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1TuWE6z1n8Qhb_Rd5FsiHFTBhI2AUukGo/preview",
          unitCoverage = "10 සහ 11 ශ්‍රේණි සම්පූර්ණ විද්‍යාව විෂය නිර්දේශය (O/L Syllabus)"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("විද්‍යාව") || subjectNameSinhala.contains("Science", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "science_gr10_term2_paper_2024",
          grade = "10",
          subject = "විද්‍යාව",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - විද්‍යාව දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1zKtgd_badE2crpYDfX8xPnxjb-lT6VvD/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1zKtgd_badE2crpYDfX8xPnxjb-lT6VvD/preview",
          unitCoverage = "10 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("විද්‍යාව") || subjectNameSinhala.contains("Science", ignoreCase = true)) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "science_gr10_term3_paper_2024",
          grade = "10",
          subject = "විද්‍යාව",
          term = 3,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - විද්‍යාව තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1NDclAIyinjTEYML9IjrRv4QMORbZBggc/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1NDclAIyinjTEYML9IjrRv4QMORbZBggc/preview",
          unitCoverage = "10 ශ්‍රේණිය 3 වන වාරය සහ සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("සිංහල") || subjectNameSinhala.contains("Sinhala")) && term == 1) {
      return listOf(
        TermTestPaperItem(
          id = "sinhala_gr10_term1_paper",
          grade = "10",
          subject = "සිංහල භාෂාව හා සාහිත්‍යය",
          term = 1,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - සිංහල පළමු වාර පරීක්ෂණ ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ ප්‍රශ්න පත්‍රය",
          questionPaperUrl = "https://drive.google.com/file/d/1s9juSH9Kyv6rjI6BKE5v8vaHC_4t8hiY/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1s9juSH9Kyv6rjI6BKE5v8vaHC_4t8hiY/preview",
          unitCoverage = "1 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ඉංග්‍රීසි") || subjectNameSinhala.contains("English")) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "english_gr11_term2_paper_2024",
          grade = "11",
          subject = "ඉංග්‍රීසි",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ඉංග්‍රීසි දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "Paper I & Paper II ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1EGONbKc1CkpJQCJ5Ve24ik6T6s6xlMa6/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1EGONbKc1CkpJQCJ5Ve24ik6T6s6xlMa6/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ඉතිහාසය") || subjectNameSinhala.contains("History")) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "history_gr11_term2_paper_2024",
          grade = "11",
          subject = "ඉතිහාසය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ඉතිහාසය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍ර 01)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1-5nRVDmp5Yw5usg5mPuz4GGBHv36gZm5/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1-5nRVDmp5Yw5usg5mPuz4GGBHv36gZm5/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "history_gr11_term2_paper_2024_2",
          grade = "11",
          subject = "ඉතිහාසය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ඉතිහාසය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍ර 02)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1xDVjFIjfK8cTl1wmnin8BHZGN471d7Uq/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1xDVjFIjfK8cTl1wmnin8BHZGN471d7Uq/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("ගණිතය") || subjectNameSinhala.contains("Math", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "math_gr10_term2_paper_2024",
          grade = "10",
          subject = "ගණිතය",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - ගණිතය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1f9jYHMCPltGtG0NKICloYDdZFz-jiSW6/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1f9jYHMCPltGtG0NKICloYDdZFz-jiSW6/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("ඉතිහාසය") || subjectNameSinhala.contains("History")) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "history_gr10_term3_paper_2024",
          grade = "10",
          subject = "ඉතිහාසය",
          term = 3,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - ඉතිහාසය තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1WDen4EKvIu6TfH1QR1KOOzEzAt8IRGby/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1WDen4EKvIu6TfH1QR1KOOzEzAt8IRGby/preview",
          unitCoverage = "3 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("ව්‍යාපාර") || subjectNameSinhala.contains("ගිණුම්") || subjectNameSinhala.contains("Commerce") || subjectNameSinhala.contains("කොමස්")) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "commerce_gr10_term3_paper_2024",
          grade = "10",
          subject = "ව්‍යාපාර අධ්‍යයනය හා ගිණුම්කරණය",
          term = 3,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - ව්‍යාපාර හා ගිණුම්කරණ අධ්‍යයනය (කොමස්) තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1FWhvPon-0-Bu_8pwU3moG-Un2CWWWsnn/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1FWhvPon-0-Bu_8pwU3moG-Un2CWWWsnn/preview",
          unitCoverage = "3 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ගණිතය") || subjectNameSinhala.contains("Math", ignoreCase = true)) && term == 1) {
      return listOf(
        TermTestPaperItem(
          id = "math_gr11_term1_paper_2020_nwp",
          grade = "11",
          subject = "ගණිතය",
          term = 1,
          year = 2020,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය පළමු වාර විභාග ප්‍රශ්න පත්‍රය හා පිළිතුරු පත්‍රය (වයඹ පළාත 2020)",
          sourceOrProvince = "වයඹ පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education - NWP)",
          timeDuration = "පැය 03 යි (Paper I: 2h, Paper II: 3h 10m)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II ප්‍රශ්න පත්‍ර සම්පූර්ණයෙන් හා ලකුණු 100ක නිල ලකුණු දීමේ පිළිතුරු පත්‍රය (Marking Scheme)",
          questionPaperUrl = "https://drive.google.com/file/d/1_d2Y9X9y4M0R9K8Z8j9F7q2l6x7N1P5W/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1_d2Y9X9y4M0R9K8Z8j9F7q2l6x7N1P5W/preview",
          unitCoverage = "11 ශ්‍රේණිය 1 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය (වයඹ පළාත - 2020)"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ගණිතය") || subjectNameSinhala.contains("Math", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "math_gr11_term2_paper_2024_4",
          grade = "11",
          subject = "ගණිතය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 04)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1CFuLN_QGZYjXFIxRm2rKHuMRshui1lDy/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1CFuLN_QGZYjXFIxRm2rKHuMRshui1lDy/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය (Set 04)"
        ),
        TermTestPaperItem(
          id = "math_gr11_term2_paper_2024_1",
          grade = "11",
          subject = "ගණිතය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 01)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1OKp2dUeKwV7JJjuJRGaJFNwQstk_m9pT/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1OKp2dUeKwV7JJjuJRGaJFNwQstk_m9pT/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය (Set 01)"
        ),
        TermTestPaperItem(
          id = "math_gr11_term2_paper_2024_2",
          grade = "11",
          subject = "ගණිතය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 02)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1-nClu4y3LIYOCVemi9ymZUj8ZYRZbLOL/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1-nClu4y3LIYOCVemi9ymZUj8ZYRZbLOL/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය (Set 02)"
        ),
        TermTestPaperItem(
          id = "math_gr11_term2_paper_2024_3",
          grade = "11",
          subject = "ගණිතය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 03)",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/17MV6SPMSznDOjjFLmJkYUaqocd5z36cs/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/17MV6SPMSznDOjjFLmJkYUaqocd5z36cs/preview",
          unitCoverage = "2 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය (Set 03)"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ගණිතය") || subjectNameSinhala.contains("Math", ignoreCase = true)) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "math_gr11_term3_paper_2020_nwp",
          grade = "11",
          subject = "ගණිතය",
          term = 3,
          year = 2020,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා පිළිතුරු පත්‍රය (වයඹ පළාත 2020)",
          sourceOrProvince = "වයඹ පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education - NWP)",
          timeDuration = "පැය 03 යි (Paper I: 2h, Paper II: 3h 10m)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II ප්‍රශ්න පත්‍ර සම්පූර්ණයෙන් හා ලකුණු 100ක නිල ලකුණු දීමේ පිළිතුරු පත්‍රය (Marking Scheme)",
          questionPaperUrl = "https://drive.google.com/file/d/1GRuImL-TxYbVTtN7pJ7n3HFH4VPsF9Eq/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1GRuImL-TxYbVTtN7pJ7n3HFH4VPsF9Eq/preview",
          unitCoverage = "11 ශ්‍රේණිය 3 වන වාරය සහ සම්පූර්ණ විෂය නිර්දේශ ඒකක ආවරණය (වයඹ පළාත - 2020)"
        ),
        TermTestPaperItem(
          id = "math_gr11_term3_paper_2024",
          grade = "11",
          subject = "ගණිතය",
          term = 3,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1GRuImL-TxYbVTtN7pJ7n3HFH4VPsF9Eq/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1GRuImL-TxYbVTtN7pJ7n3HFH4VPsF9Eq/preview",
          unitCoverage = "3 වන වාරය සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ව්‍යාපාර") || subjectNameSinhala.contains("ගිණුම්") || subjectNameSinhala.contains("Commerce") || subjectNameSinhala.contains("කොමස්")) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "commerce_gr11_term3_paper_2024",
          grade = "11",
          subject = "ව්‍යාපාර අධ්‍යයනය හා ගිණුම්කරණය",
          term = 3,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ව්‍යාපාර හා ගිණුම්කරණ අධ්‍යයනය (කොමස්) තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1T-EbJmrXlq_1NlS8RK6G7roAQQqpg9VO/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1T-EbJmrXlq_1NlS8RK6G7roAQQqpg9VO/preview",
          unitCoverage = "11 ශ්‍රේණිය 3 වන වාරය සහ සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("ඉතිහාසය") || subjectNameSinhala.contains("History")) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "history_gr11_term3_paper_2024",
          grade = "11",
          subject = "ඉතිහාසය",
          term = 3,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - ඉතිහාසය තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ අවසාන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1vI4GmSXIgL50dtVJwPNgR1Wjifj3C82W/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1vI4GmSXIgL50dtVJwPNgR1Wjifj3C82W/preview",
          unitCoverage = "3 වන වාරය සහ සමස්ත විෂය නිර්දේශය ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("සෞඛ්‍ය") || subjectNameSinhala.contains("Health", ignoreCase = true)) && term == 3) {
      return listOf(
        TermTestPaperItem(
          id = "health_gr11_term3_paper_2024",
          grade = "11",
          subject = "සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය",
          term = 3,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය තෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ තෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1E3jiwML1OV1UONDcs2CXTKaFGtwW3pS7/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1E3jiwML1OV1UONDcs2CXTKaFGtwW3pS7/preview",
          unitCoverage = "11 ශ්‍රේණිය 3 වන වාරය සහ සම්පූර්ණ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("තොරතුරු") || subjectNameSinhala.contains("ICT", ignoreCase = true) || subjectNameSinhala.contains("සන්නිවේදන")) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "ict_gr11_term2_paper_2024",
          grade = "11",
          subject = "තොරතුරු හා සන්නිවේදන තාක්ෂණය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - තොරතුරු හා සන්නිවේදන තාක්ෂණය (ICT) දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 01",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1tWdUdL3DJTVUNWkmXfACdcIJKMjIZhLc/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1tWdUdL3DJTVUNWkmXfACdcIJKMjIZhLc/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "ict_gr11_term2_paper_2024_2",
          grade = "11",
          subject = "තොරතුරු හා සන්නිවේදන තාක්ෂණය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - තොරතුරු හා සන්නිවේදන තාක්ෂණය (ICT) දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 02",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ආදර්ශ පිළිතුරු",
          questionPaperUrl = "https://drive.google.com/file/d/1hNaVPfSS76_2Svm_f8H_J4DuBJaKvSYY/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1hNaVPfSS76_2Svm_f8H_J4DuBJaKvSYY/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("බුද්ධ") || subjectNameSinhala.contains("Buddhism", ignoreCase = true)) && term == 1) {
      return listOf(
        TermTestPaperItem(
          id = "buddhism_gr11_term1_paper_2024_1",
          grade = "11",
          subject = "බුද්ධ ධර්මය",
          term = 1,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - බුද්ධ ධර්මය පළමු වාර විභාග ප්‍රශ්න පත්‍ර 01",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ පළමු වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1TcxvDaBmSPTRLS-FVVkeNUyCEcgjA5C0/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1TcxvDaBmSPTRLS-FVVkeNUyCEcgjA5C0/preview",
          unitCoverage = "11 ශ්‍රේණිය 1 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "buddhism_gr11_term1_paper_2024_2",
          grade = "11",
          subject = "බුද්ධ ධර්මය",
          term = 1,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - බුද්ධ ධර්මය පළමු වාර විභාග ප්‍රශ්න පත්‍ර 02",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ පළමු වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/14E2Ty53fNwuAgBmGA1FJ7UwD76QsLhHg/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/14E2Ty53fNwuAgBmGA1FJ7UwD76QsLhHg/preview",
          unitCoverage = "11 ශ්‍රේණිය 1 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("බුද්ධ") || subjectNameSinhala.contains("Buddhism", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "buddhism_gr11_term2_paper_2024_1",
          grade = "11",
          subject = "බුද්ධ ධර්මය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - බුද්ධ ධර්මය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 01",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1lR-tAq0eW5UWfIBLrcNKXluTscyzPIDa/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1lR-tAq0eW5UWfIBLrcNKXluTscyzPIDa/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "buddhism_gr11_term2_paper_2024_2",
          grade = "11",
          subject = "බුද්ධ ධර්මය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - බුද්ධ ධර්මය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 02",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1AW-2VdUYt2OXUBFmKiB0mlDmha58RnJK/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1AW-2VdUYt2OXUBFmKiB0mlDmha58RnJK/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("සෞඛ්‍ය") || subjectNameSinhala.contains("Health", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "health_gr11_term2_paper_2024",
          grade = "11",
          subject = "සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1r_ZX6LYNYqLlhucK-wA84gbDgzh5wrCC/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1r_ZX6LYNYqLlhucK-wA84gbDgzh5wrCC/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "11" && (subjectNameSinhala.contains("පුරවැසි") || subjectNameSinhala.contains("Civic", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "civic_gr11_term2_paper_2024",
          grade = "11",
          subject = "පුරවැසි අධ්‍යාපනය",
          term = 2,
          year = 2024,
          titleSinhala = "11 ශ්‍රේණිය - පුරවැසි අධ්‍යාපනය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1x6ZULsQQXVR0Xgjl9X_CuVg2pSdHAxTY/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1x6ZULsQQXVR0Xgjl9X_CuVg2pSdHAxTY/preview",
          unitCoverage = "11 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("පුරවැසි") || subjectNameSinhala.contains("Civic", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "civic_gr10_term2_paper_2024",
          grade = "10",
          subject = "පුරවැසි අධ්‍යාපනය",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - පුරවැසි අධ්‍යාපනය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1pdYxCcT8h2aVnp2XToS7kBgqg2aglFC9/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1pdYxCcT8h2aVnp2XToS7kBgqg2aglFC9/preview",
          unitCoverage = "10 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    if (grade == "10" && (subjectNameSinhala.contains("බුද්ධ") || subjectNameSinhala.contains("Buddhism", ignoreCase = true)) && term == 2) {
      return listOf(
        TermTestPaperItem(
          id = "buddhism_gr10_term2_paper_2024_1",
          grade = "10",
          subject = "බුද්ධ ධර්මය",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - බුද්ධ ධර්මය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 01",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1gJ03_AT7XEPQEEAp2HLYHywIEaDO2ed1/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1gJ03_AT7XEPQEEAp2HLYHywIEaDO2ed1/preview",
          unitCoverage = "10 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "buddhism_gr10_term2_paper_2024_2",
          grade = "10",
          subject = "බුද්ධ ධර්මය",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - බුද්ධ ධර්මය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 02",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1L42lStEbPjLRYN8geeieFnWKH8_HgFA9/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1L42lStEbPjLRYN8geeieFnWKH8_HgFA9/preview",
          unitCoverage = "10 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        ),
        TermTestPaperItem(
          id = "buddhism_gr10_term2_paper_2024_3",
          grade = "10",
          subject = "බුද්ධ ධර්මය",
          term = 2,
          year = 2024,
          titleSinhala = "10 ශ්‍රේණිය - බුද්ධ ධර්මය දෙවන වාර විභාග ප්‍රශ්න පත්‍ර 03",
          sourceOrProvince = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව (Provincial Department of Education)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් සම්පූර්ණ දෙවන වාර විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1-MGv6Q8nI_t6Oiy74fxZhWj9T0GOWQx7/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1-MGv6Q8nI_t6Oiy74fxZhWj9T0GOWQx7/preview",
          unitCoverage = "10 ශ්‍රේණිය 2 වන වාරය සහ අදාළ විෂය ඒකක ආවරණය"
        )
      )
    }
    return emptyList()
  }
}

@Composable
fun TermTestPapersSection(
  grade: String,
  subject: SubjectItem,
  onOpenPdf: (title: String, url: String) -> Unit,
  onOpenGrade10Portal: ((Grade10WebSource) -> Unit)? = null,
  onOpenGrade11Portal: ((Grade11WebSource, String?, Int?) -> Unit)? = null
) {
  var selectedTerm by remember { mutableStateOf(1) } // 1: 1st Term, 2: 2nd Term, 3: 3rd Term
  var selectedYearFilter by remember { mutableStateOf<Int?>(null) } // null: All years

  val allTermPapers = remember(grade, subject.nameSinhala, selectedTerm) {
    TermTestPapersRepository.getTermTestPapers(grade, subject.nameSinhala, selectedTerm)
  }

  val filteredPapers = remember(allTermPapers, selectedYearFilter) {
    if (selectedYearFilter == null) allTermPapers
    else allTermPapers.filter { it.year == selectedYearFilter }
  }

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.2.dp, Color(0xFFE2E8F0)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("term_test_papers_hub")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // 1. Header with Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(Color(0xFF047857)),
            contentAlignment = Alignment.Center
          ) {
            Text("📑", fontSize = 16.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "වාර විභාග ප්‍රශ්න පත්‍ර (Term Test Papers)",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF0F172A)
            )
            Text(
              text = "${subject.nameSinhala} • $grade ශ්‍රේණිය • පසුගිය වසර 5 ක ප්‍රශ්න හා පිළිතුරු",
              fontSize = 10.sp,
              color = Color(0xFF047857),
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color(0xFFECFDF5),
          border = BorderStroke(1.dp, Color(0xFFA7F3D0))
        ) {
          Text(
            text = "වසර 5ක සංචිතය",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF047857),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 🌟 10 ශ්‍රේණිය නිල වාර විභාග ප්‍රශ්න පත්‍ර ආරක්ෂිත ද්වාරය (GovDoc.lk & e-Thaksalawa)
      if (grade.contains("10")) {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
          border = BorderStroke(1.2.dp, Color(0xFF10B981)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("grade10_secure_portal_banner")
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF10B981).copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text("🛡️", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "10 වසර වාර විභාග ප්‍රශ්න පත්‍ර ද්වාරය",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Text(
                    text = "GovDoc.lk & e-Thaksalawa (නිල වෙබ් අඩවි)",
                    fontSize = 9.5.sp,
                    color = Color(0xFFA7F3D0)
                  )
                }
              }

              Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0xFFDC2626)
              ) {
                Text(
                  text = "DRM PROTECTED",
                  fontSize = 8.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "🚫 ආරක්ෂිත කියවීම් මාදිලිය: Screenshot, Screen Record, Copy කිරීම සහ Download කිරීම සම්පූර්ණයෙන්ම අවහිර කර ඇත.",
              fontSize = 9.5.sp,
              color = Color(0xFFCBD5E1),
              lineHeight = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { onOpenGrade10Portal?.invoke(Grade10WebSource.GOVDOC) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("open_govdoc_grade10_btn")
              ) {
                Text(
                  text = "📄 GovDoc 10 Papers",
                  fontSize = 10.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }

              Button(
                onClick = { onOpenGrade10Portal?.invoke(Grade10WebSource.ETHAKSALAWA) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D4ED8)),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("open_ethaksalawa_grade10_btn")
              ) {
                Text(
                  text = "🏛️ e-Thaksalawa 10",
                  fontSize = 10.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
      }

      // 🌟 11 ශ්‍රේණිය නිල වාර විභාග ප්‍රශ්න පත්‍ර ආරක්ෂිත ද්වාරය (GovDoc.lk & e-Thaksalawa)
      if (grade.contains("11")) {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
          border = BorderStroke(1.2.dp, Color(0xFF10B981)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("grade11_secure_portal_banner")
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF10B981).copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text("🛡️", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "11 වසර වාර විභාග ප්‍රශ්න පත්‍ර ද්වාරය",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Text(
                    text = "${subject.nameSinhala} • $selectedTerm වන වාරය (GovDoc & e-Thaksalawa)",
                    fontSize = 9.5.sp,
                    color = Color(0xFFA7F3D0)
                  )
                }
              }

              Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0xFFDC2626)
              ) {
                Text(
                  text = "DRM PROTECTED",
                  fontSize = 8.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "🚫 ආරක්ෂිත කියවීම් මාදිලිය: Screenshot, Screen Record, Copy කිරීම සහ Download කිරීම සම්පූර්ණයෙන්ම අවහිර කර ඇත. මෙම විෂයට සහ $selectedTerm වන වාරයට අදාළ ප්‍රශ්න පත්‍ර පමණක් පෙන්වයි.",
              fontSize = 9.5.sp,
              color = Color(0xFFCBD5E1),
              lineHeight = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { onOpenGrade11Portal?.invoke(Grade11WebSource.GOVDOC, subject.nameSinhala, selectedTerm) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("open_govdoc_grade11_btn")
              ) {
                Text(
                  text = "📄 GovDoc (${subject.nameSinhala} - $selectedTerm වාරය)",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }

              Button(
                onClick = { onOpenGrade11Portal?.invoke(Grade11WebSource.ETHAKSALAWA, subject.nameSinhala, selectedTerm) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D4ED8)),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("open_ethaksalawa_grade11_btn")
              ) {
                Text(
                  text = "🏛️ e-Thaksalawa 11",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
      }

      // 2. Term Selector Tabs (1 වන වාරය | 2 වන වාරය | 3 වන වාරය)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf(
          Triple(1, "පළමු වාරය", "1st Term"),
          Triple(2, "දෙවන වාරය", "2nd Term"),
          Triple(3, "තෙවන වාරය", "3rd Term")
        ).forEach { (tNum, tTitle, tSub) ->
          val isSelected = selectedTerm == tNum
          Surface(
            onClick = {
              selectedTerm = tNum
              selectedYearFilter = null
            },
            shape = RoundedCornerShape(10.dp),
            color = if (isSelected) Color(0xFF047857) else Color(0xFFF1F5F9),
            border = BorderStroke(
              1.dp,
              if (isSelected) Color(0xFF047857) else Color(0xFFE2E8F0)
            ),
            modifier = Modifier
              .weight(1f)
              .testTag("term_tab_$tNum")
          ) {
            Column(
              modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = tTitle,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color(0xFF334155)
              )
              Text(
                text = tSub,
                fontSize = 9.sp,
                color = if (isSelected) Color(0xFFA7F3D0) else Color(0xFF64748B)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 3. Year Filter Chips (සියල්ල | 2024 | 2023 | 2022 | 2021 | 2020)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "වසර:",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF64748B)
        )

        val yearOptions = listOf<Int?>(null, 2024, 2023, 2022, 2021, 2020)
        yearOptions.forEach { yr ->
          val isSelected = selectedYearFilter == yr
          Surface(
            onClick = { selectedYearFilter = yr },
            shape = RoundedCornerShape(6.dp),
            color = if (isSelected) Color(0xFF065F46) else Color(0xFFF8FAFC),
            border = BorderStroke(
              1.dp,
              if (isSelected) Color(0xFF065F46) else Color(0xFFCBD5E1)
            ),
            modifier = Modifier.testTag("year_filter_${yr ?: "all"}")
          ) {
            Text(
              text = if (yr == null) "සියල්ල" else "$yr",
              fontSize = 10.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = if (isSelected) Color.White else Color(0xFF334155),
              modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 4. List of 5-Year Term Test Papers for Selected Term
      if (filteredPapers.isEmpty()) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFF8FAFC),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Description,
              contentDescription = null,
              tint = Color(0xFF94A3B8),
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "ප්‍රශ්න පත්‍ර ඇතුළත් කර නොමැත",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF475569)
            )
            Text(
              text = "මෙම විෂයට අදාළ වාර විභාග ප්‍රශ්න පත්‍ර පීඩීඑෆ් දැනට නොමැත.",
              fontSize = 10.5.sp,
              color = Color(0xFF94A3B8),
              textAlign = TextAlign.Center
            )
          }
        }
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          filteredPapers.forEach { paperItem ->
            TermTestPaperCard(
              item = paperItem,
              onOpenQuestionPaper = {
                onOpenPdf(
                  "${paperItem.year} ${paperItem.subject} (${selectedTerm} වන වාරය) ප්‍රශ්න පත්‍රය",
                  paperItem.questionPaperUrl
                )
              },
              onOpenAnswerScheme = {
                onOpenPdf(
                  "${paperItem.year} ${paperItem.subject} (${selectedTerm} වන වාරය) පිළිතුරු පත්‍රය (Marking Scheme)",
                  paperItem.answerSchemeUrl
                )
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun TermTestPaperCard(
  item: TermTestPaperItem,
  onOpenQuestionPaper: () -> Unit,
  onOpenAnswerScheme: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = Color(0xFFFAFAFA),
    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      // Top row: Year & Term Badge + Source Province
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFF047857)
          ) {
            Text(
              text = "${item.year} • ${item.term} වන වාරය",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFFE0F2FE)
          ) {
            Text(
              text = item.timeDuration,
              fontSize = 9.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF0369A1),
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(4.dp),
          color = Color(0xFFFEF3C7)
        ) {
          Text(
            text = item.totalMarks,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF92400E),
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Title & Province Source
      Text(
        text = item.titleSinhala,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        fontSize = 12.5.sp,
        color = Color(0xFF0F172A)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 2.dp)
      ) {
        Icon(
          imageVector = Icons.Default.School,
          contentDescription = "Source",
          tint = Color(0xFF64748B),
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = item.sourceOrProvince,
          fontSize = 10.sp,
          color = Color(0xFF64748B)
        )
      }

      Text(
        text = "📌 ${item.unitCoverage}",
        fontSize = 9.5.sp,
        color = Color(0xFF475569),
        modifier = Modifier.padding(top = 3.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Dual Action Buttons: [📄 ප්‍රශ්න පත්‍රය (Paper)] and [📝 පිළිතුරු පත්‍රය (Marking Scheme)]
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Question Paper Button
        Button(
          onClick = onOpenQuestionPaper,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("open_question_paper_${item.year}_${item.term}")
        ) {
          Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = "Question Paper",
            tint = Color(0xFFF87171),
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "ප්‍රශ්න පත්‍රය",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }

        // Marking Scheme Button
        Button(
          onClick = onOpenAnswerScheme,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("open_marking_scheme_${item.year}_${item.term}")
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Marking Scheme",
            tint = Color(0xFF86EFAC),
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "පිළිතුරු පත්‍රය",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}

data class GceOlPastPaperItem(
  val id: String,
  val subject: String,
  val year: Int,
  val isLatest: Boolean = false,
  val titleSinhala: String,
  val source: String = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
  val timeDuration: String = "පැය 03 යි",
  val totalMarks: String = "ලකුණු 100",
  val paperStructure: String = "I කොටස (බහුවරණ 40) + II කොටස (ව්‍යුහගත හා රචනා)",
  val questionPaperUrl: String,
  val answerSchemeUrl: String,
  val description: String = "අ.පො.ස. (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය"
)

object GceOlPastPapersRepository {

  fun getOlPastPapers(subjectNameSinhala: String): List<GceOlPastPaperItem> {
    if (subjectNameSinhala.contains("ඉතිහාසය") || subjectNameSinhala.contains("History")) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_history_past_paper_2024_2025_official",
          subject = "ඉතිහාසය",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ඉතිහාසය සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 01 (I පත්‍රය) + පැය 03 (II පත්‍රය)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ ප්‍රශ්න 1-40) + II කොටස (ලංකා හා ලෝක සිතියම්, ඓතිහාසික මූලාශ්‍ර රූප සටහන්, රචනා ප්‍රශ්න)",
          questionPaperUrl = "https://drive.google.com/file/d/1history_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1history_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) ඉතිහාසය 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (33 S I සහ 33 S II)"
        ),
        GceOlPastPaperItem(
          id = "ol_history_past_paper_2024",
          subject = "ඉතිහාසය",
          year = 2024,
          isLatest = false,
          titleSinhala = "11 ශ්‍රේණිය - ඉතිහාසය සාමාන්‍ය පෙළ ප්‍රශ්න පත්‍ර",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ 40) + II කොටස (ව්‍යුහගත හා රචනා)",
          questionPaperUrl = "https://drive.google.com/file/d/1wrTWGsV7mwS3muCKjZVubP1RMvcXg0e5/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1wrTWGsV7mwS3muCKjZVubP1RMvcXg0e5/preview",
          description = "අ.පො.ස. (සා.පෙළ) ඉතිහාසය නිල විභාග ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය"
        )
      )
    }
    if (subjectNameSinhala.contains("ගණිතය") || subjectNameSinhala.contains("Math")) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_2025_official",
          subject = "ගණිතය",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 02 (I පත්‍රය) + පැය 03 (II පත්‍රය)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (A කොටස 1-25 ප්‍රශ්න + B කොටස 1-5 ප්‍රශ්න) + II කොටස (A කොටස 1-6 සහ B කොටස 7-12 ප්‍රශ්න)",
          questionPaperUrl = "https://drive.google.com/file/d/1math_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1math_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (32 S I සහ 32 S II)"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_8",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 08)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/181c4-qtC3OoJBQVYdqUeUi_4e6EcLE6D/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/181c4-qtC3OoJBQVYdqUeUi_4e6EcLE6D/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍ර 01)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (A සහ B කොටස්) + II කොටස (A සහ B කොටස්)",
          questionPaperUrl = "https://drive.google.com/file/d/1JsrHtdUOpd9MtZgm6_cZFX9sbhhs4kCS/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1JsrHtdUOpd9MtZgm6_cZFX9sbhhs4kCS/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය නිල විභාග ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_7",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 07)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1xDkAhMMJX2f6SKQbsMLCNR31VECQqRzN/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1xDkAhMMJX2f6SKQbsMLCNR31VECQqRzN/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_6",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 06)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1AD4oy9etzQGpIINmusucjyW3gYq66lwL/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1AD4oy9etzQGpIINmusucjyW3gYq66lwL/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_5",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍රය 05)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1VhY-Of4Ub-ZuRRJHrq1RX4o3cOTsyXtM/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1VhY-Of4Ub-ZuRRJHrq1RX4o3cOTsyXtM/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_4",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (අතිරේක ප්‍රශ්න පත්‍රය)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1agtzpsIvSsF3IJyuV43RdWITGt_ruflb/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1agtzpsIvSsF3IJyuV43RdWITGt_ruflb/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_3",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (විශේෂ කට්ටලය)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ලකුණු දීමේ පටිපාටිය",
          questionPaperUrl = "https://drive.google.com/file/d/1adURMthHZSMi8KLCxgpY1AReZuy0kbd8/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1adURMthHZSMi8KLCxgpY1AReZuy0kbd8/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new_2",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (නව කට්ටලය)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා ආදර්ශ පිළිතුරු",
          questionPaperUrl = "https://drive.google.com/file/d/1tQ4TvRxbeIiLMVr7uEth0G9nHpOtT8tp/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1tQ4TvRxbeIiLMVr7uEth0G9nHpOtT8tp/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2024_new",
          subject = "ගණිතය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I සහ II කොටස් ඇතුළත් අ.පො.ස. සාමාන්‍ය පෙළ (O/L) ගණිතය විභාග ප්‍රශ්න පත්‍රය",
          questionPaperUrl = "https://drive.google.com/file/d/1pEE7prAIU-JTjsQFrGhF4kMPPQEAZJsA/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1pEE7prAIU-JTjsQFrGhF4kMPPQEAZJsA/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පරිපූර්ණ ලකුණු දීමේ පටිපාටිය"
        ),
        GceOlPastPaperItem(
          id = "ol_math_past_paper_2023",
          subject = "ගණිතය",
          year = 2023,
          isLatest = false,
          titleSinhala = "11 ශ්‍රේණිය - ගණිතය සාමාන්‍ය පෙළ පසුගිය ප්‍රශ්න පත්‍ර (ප්‍රශ්න පත්‍ර 02)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (A සහ B කොටස්) + II කොටස (A සහ B කොටස්)",
          questionPaperUrl = "https://drive.google.com/file/d/16guEVXq3IzLmSPRoMktnZUyI0y3XALYm/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/16guEVXq3IzLmSPRoMktnZUyI0y3XALYm/preview",
          description = "අ.පො.ස. (සා.පෙළ) ගණිතය විභාග ප්‍රශ්න පත්‍රය හා පිළිතුරු පත්‍රය"
        )
      )
    }
    if (subjectNameSinhala.contains("කෘෂි") || subjectNameSinhala.contains("Agriculture")) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_agri_past_paper_gr11_2024",
          subject = "කෘෂි හා ආහාර තාක්ෂණය",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - කෘෂි හා ආහාර තාක්ෂණය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ 40) + II කොටස (ව්‍යුහගත හා රචනා)",
          questionPaperUrl = "https://drive.google.com/file/d/1ZQVbVXMM92max_jQbPwuEkbWSyuJJqY4/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1ZQVbVXMM92max_jQbPwuEkbWSyuJJqY4/preview",
          description = "අ.පො.ස. (සා.පෙළ) කෘෂි හා ආහාර තාක්ෂණය නිල විභාග ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය"
        )
      )
    }
    if (subjectNameSinhala.contains("චිත්‍ර") || subjectNameSinhala.contains("Art", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_art_past_paper_gr11_2024",
          subject = "චිත්‍ර",
          year = 2024,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - චිත්‍ර සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ 40) + II කොටස (ප්‍රායෝගික හා න්‍යායාත්මක ප්‍රශ්න පත්‍රය)",
          questionPaperUrl = "https://drive.google.com/file/d/1sgIxFoMazNG1VeFlG0EcZH28oq-mrk9Y/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1sgIxFoMazNG1VeFlG0EcZH28oq-mrk9Y/preview",
          description = "අ.පො.ස. (සා.පෙළ) චිත්‍ර කලා නිල විභාග ප්‍රශ්න පත්‍රය සහ ප්‍රමිත ලකුණු දීමේ පටිපාටිය"
        )
      )
    }
    if (subjectNameSinhala.contains("සෞඛ්‍ය") || subjectNameSinhala.contains("Health", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_health_past_paper_gr11_2020",
          subject = "සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය",
          year = 2020,
          isLatest = false,
          titleSinhala = "11 ශ්‍රේණිය - සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය සාමාන්‍ය පෙළ (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර (2020)",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 03 යි (අමතර කියවීම් කාලය: මිනිත්තු 10)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ ප්‍රශ්න 40) + II කොටස (ව්‍යුහගත හා රචනා ප්‍රශ්න 05)",
          questionPaperUrl = "https://drive.google.com/file/d/1r_ZX6LYNYqLlhucK-wA84gbDgzh5wrCC/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1r_ZX6LYNYqLlhucK-wA84gbDgzh5wrCC/preview",
          description = "අ.පො.ස. (සා.පෙළ) සෞඛ්‍ය හා ශාරීරික අධ්‍යාපනය 2020 නිල විභාග ප්‍රශ්න පත්‍රය (Paper I සහ Paper II)"
        )
      )
    }
    if (subjectNameSinhala.contains("නර්තන") || subjectNameSinhala.contains("Dance", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_dancing_past_paper_2024_2025_official",
          subject = "නර්තනය",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - නැටුම් (දේශීය) සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 03 යි (අමතර කියවීම් කාලය: මිනිත්තු 10)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (පොදු ප්‍රශ්න 1-30 + උඩරට/පහතරට/සබරගමු තේරීම් ප්‍රශ්න 31-40) + II කොටස (අනිවාර්ය ප්‍රශ්න 1 ඇතුළු ප්‍රශ්න 5ක්)",
          questionPaperUrl = "https://drive.google.com/file/d/1dancing_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1dancing_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) නැටුම් (දේශීය) 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (44 S I, II)"
        ),
        GceOlPastPaperItem(
          id = "ol_dancing_past_paper_gr11_2023",
          subject = "නර්තනය",
          year = 2023,
          isLatest = false,
          titleSinhala = "11 ශ්‍රේණිය - නර්තනය තෙවන වාර විභාග ප්‍රශ්න පත්‍රය (උතුරු මැද පළාත - 2023)",
          source = "පළාත් අධ්‍යාපන දෙපාර්තමේන්තුව - උතුරු මැද පළාත (North Central Province)",
          timeDuration = "පැය 03 යි",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ 40) + II කොටස (ප්‍රස්තාර, රචනා හා විචාර ප්‍රශ්න 07)",
          questionPaperUrl = "https://drive.google.com/file/d/1dancing_gr11_term3_exam_paper_2023/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1dancing_gr11_term3_exam_paper_2023/preview",
          description = "11 ශ්‍රේණිය නර්තනය තෙවන වාර විභාග ප්‍රශ්න පත්‍රය - 2023 (Paper I සහ Paper II)"
        )
      )
    }
    if (subjectNameSinhala.contains("පුරවැසි") || subjectNameSinhala.contains("Civic", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_civic_past_paper_2024_2025_official",
          subject = "පුරවැසි අධ්‍යාපනය",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - පුරවැසි අධ්‍යාපනය සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 03 යි (අමතර කියවීම් කාලය: මිනිත්තු 10)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ ප්‍රශ්න 40) + II කොටස (අනිවාර්ය ප්‍රශ්න 1 ඇතුළු ප්‍රශ්න 5ක්)",
          questionPaperUrl = "https://drive.google.com/file/d/1civic_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1civic_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) පුරවැසි අධ්‍යාපනය 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (62 S I, II)"
        )
      )
    }
    if (subjectNameSinhala.contains("භූගෝල") || subjectNameSinhala.contains("Geography", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_geography_past_paper_2024_2025_official",
          subject = "භූගෝල විද්‍යාව",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - භූගෝල විද්‍යාව සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 03 යි (අමතර කියවීම් කාලය: මිනිත්තු 10)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (ලංකා හා ලෝක සිතියම් ලකුණු කිරීම, 1:50,000 මහියංගණ/සොරබොර වැව භූ ලක්ෂණ සිතියම් අභ්‍යාසය) + II කොටස (අනිවාර්ය ප්‍රශ්න 1 ඇතුළු ප්‍රශ්න 5ක්)",
          questionPaperUrl = "https://drive.google.com/file/d/1geography_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1geography_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) භූගෝල විද්‍යාව 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (61 S I, II)"
        )
      )
    }
    if (subjectNameSinhala.contains("ඉංග්‍රීසි") || subjectNameSinhala.contains("English", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_english_past_paper_2024_2025_official",
          subject = "ඉංග්‍රීසි භාෂාව",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ඉංග්‍රීසි භාෂාව සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 01 (Paper I) + පැය 02 (Paper II)",
          totalMarks = "ලකුණු 100",
          paperStructure = "Paper I (Test 1 - Test 8) + Paper II (Test 9 - Test 16: Reading, Writing, Grammar & Vocabulary)",
          questionPaperUrl = "https://drive.google.com/file/d/1english_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1english_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) ඉංග්‍රීසි භාෂාව (English Language) 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (31 E I, II)"
        )
      )
    }
    if ((subjectNameSinhala.contains("විද්‍යාව") && !subjectNameSinhala.contains("භූගෝල")) || subjectNameSinhala.contains("Science", ignoreCase = true)) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_science_past_paper_2024_2025_official",
          subject = "විද්‍යාව",
          year = 2025,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - විද්‍යාව සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2024(2025) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 01 (I පත්‍රය) + පැය 03 (II පත්‍රය)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ ප්‍රශ්න 1-40) + II කොටස (A කොටස ව්‍යුහගත ප්‍රශ්න 1-4 + B කොටස රචනා ප්‍රශ්න 5-9 න් 3ක්)",
          questionPaperUrl = "https://drive.google.com/file/d/1science_ol_2024_2025_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1science_ol_2024_2025_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) විද්‍යාව 2024(2025) නිල විභාග ප්‍රශ්න පත්‍රය (34 S I සහ 34 S II)"
        )
      )
    }
    if (subjectNameSinhala.contains("ව්‍යාපාර") || subjectNameSinhala.contains("ගිණුම්") || subjectNameSinhala.contains("Commerce", ignoreCase = true) || subjectNameSinhala.contains("Accounting", ignoreCase = true) || subjectNameSinhala.contains("කොමස්")) {
      return listOf(
        GceOlPastPaperItem(
          id = "ol_commerce_past_paper_2025_2026_official",
          subject = "ව්‍යාපාර අධ්‍යයනය හා ගිණුම්කරණය",
          year = 2026,
          isLatest = true,
          titleSinhala = "11 ශ්‍රේණිය - ව්‍යාපාර හා ගිණුම්කරණ අධ්‍යයනය සාමාන්‍ය පෙළ (සා.පෙළ) නිල විභාග ප්‍රශ්න පත්‍රය 2025(2026) - Paper I & II",
          source = "ශ්‍රී ලංකා විභාග දෙපාර්තමේන්තුව (Department of Examinations, Sri Lanka)",
          timeDuration = "පැය 03 යි (අමතර කියවීම් කාලය: මිනිත්තු 10)",
          totalMarks = "ලකුණු 100",
          paperStructure = "I කොටස (බහුවරණ ප්‍රශ්න 1-40) + II කොටස (පළමුවන ප්‍රශ්නය ඇතුළුව I කොටසෙන් ප්‍රශ්න 2ක් සහ II කොටසෙන් ප්‍රශ්න 2ක් බැගින් ප්‍රශ්න 5ක්)",
          questionPaperUrl = "https://drive.google.com/file/d/1commerce_ol_2025_2026_official_paper/preview",
          answerSchemeUrl = "https://drive.google.com/file/d/1commerce_ol_2025_2026_official_paper/preview",
          description = "අ.පො.ස. (සා.පෙළ) ව්‍යාපාර හා ගිණුම්කරණ අධ්‍යයනය (Business & Accounting Studies) 2025(2026) නිල විභාග ප්‍රශ්න පත්‍රය (60 S I, II)"
        )
      )
    }
    return emptyList()
  }
}

@Composable
fun GceOlPastPapersSection(
  subject: SubjectItem,
  onOpenPdf: (title: String, url: String) -> Unit
) {
  var selectedYearFilter by remember { mutableStateOf<Int?>(null) } // null: All 6 years

  val allOlPapers = remember(subject.nameSinhala) {
    GceOlPastPapersRepository.getOlPastPapers(subject.nameSinhala)
  }

  val filteredPapers = remember(allOlPapers, selectedYearFilter) {
    if (selectedYearFilter == null) allOlPapers
    else allOlPapers.filter { it.year == selectedYearFilter }
  }

  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFDFE)),
    border = BorderStroke(1.5.dp, Color(0xFFBFDBFE)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("gce_ol_past_papers_section")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // 1. Header with Golden/Blue Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(Color(0xFF1D4ED8)),
            contentAlignment = Alignment.Center
          ) {
            Text("🏆", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "අ.පො.ස. (සා.පෙළ) විභාග ප්‍රශ්න පත්‍ර",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF0F172A),
              fontSize = 13.5.sp
            )
            Text(
              text = "${subject.nameSinhala} • පසුගිය විභාග ප්‍රශ්න හා පිළිතුරු පත්‍ර",
              fontSize = 10.5.sp,
              color = Color(0xFF1D4ED8),
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFEFF6FF),
          border = BorderStroke(1.dp, Color(0xFF93C5FD))
        ) {
          Text(
            text = "O/L PAPERS",
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E40AF),
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 2. List or Empty State
      if (filteredPapers.isEmpty()) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFF8FAFC),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.MenuBook,
              contentDescription = null,
              tint = Color(0xFF94A3B8),
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "සාමාන්‍ය පෙළ ප්‍රශ්න පත්‍ර නොමැත",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF475569)
            )
            Text(
              text = "මෙම විෂයට අදාළ O/L ප්‍රශ්න පත්‍ර හා පිළිතුරු පත්‍ර දැනට ඇතුළත් කර නොමැත.",
              fontSize = 10.5.sp,
              color = Color(0xFF94A3B8),
              textAlign = TextAlign.Center
            )
          }
        }
      } else {
        // Year Filter Chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(5.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "වර්ෂය:",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
          )

          val yearOptions = listOf<Int?>(null, 2024, 2023, 2022, 2021, 2020, 2019)
          yearOptions.forEach { yr ->
            val isSelected = selectedYearFilter == yr
            Surface(
              onClick = { selectedYearFilter = yr },
              shape = RoundedCornerShape(6.dp),
              color = if (isSelected) Color(0xFF1D4ED8) else Color(0xFFF1F5F9),
              border = BorderStroke(
                1.dp,
                if (isSelected) Color(0xFF1D4ED8) else Color(0xFFCBD5E1)
              ),
              modifier = Modifier.testTag("ol_year_filter_${yr ?: "all"}")
            ) {
              Text(
                text = when (yr) {
                  null -> "සියල්ල"
                  2024 -> "2024 මෙවර"
                  else -> "$yr"
                },
                fontSize = 9.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else Color(0xFF334155),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List of O/L Exam Papers
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          filteredPapers.forEach { olPaper ->
            GceOlPastPaperCard(
              item = olPaper,
              onOpenQuestionPaper = {
                onOpenPdf(
                  "${olPaper.year} අ.පො.ස. (සා.පෙළ) ${olPaper.subject} ප්‍රශ්න පත්‍රය (Paper I & II)",
                  olPaper.questionPaperUrl
                )
              },
              onOpenAnswerScheme = {
                onOpenPdf(
                  "${olPaper.year} අ.පො.ස. (සා.පෙළ) ${olPaper.subject} නිල පිළිතුරු පත්‍රය (Marking Scheme)",
                  olPaper.answerSchemeUrl
                )
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun GceOlPastPaperCard(
  item: GceOlPastPaperItem,
  onOpenQuestionPaper: () -> Unit,
  onOpenAnswerScheme: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color.White,
    border = BorderStroke(
      1.2.dp,
      if (item.isLatest) Color(0xFF93C5FD) else Color(0xFFE2E8F0)
    ),
    shadowElevation = if (item.isLatest) 2.dp else 1.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      // Top row: Year Badge + Duration & Marks
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = if (item.isLatest) Color(0xFF1D4ED8) else Color(0xFF0F172A)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              if (item.isLatest) {
                Text("🔥", fontSize = 10.sp)
                Spacer(modifier = Modifier.width(3.dp))
              }
              Text(
                text = if (item.isLatest) "${item.year} මෙවර විභාගය" else "${item.year} සාමාන්‍ය පෙළ",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }

          Spacer(modifier = Modifier.width(6.dp))

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFFF0FDF4)
          ) {
            Text(
              text = item.timeDuration,
              fontSize = 9.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF15803D),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color(0xFFFEF3C7),
          border = BorderStroke(0.5.dp, Color(0xFFFDE68A))
        ) {
          Text(
            text = item.totalMarks,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF92400E),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Title & Source
      Text(
        text = item.titleSinhala,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        fontSize = 12.5.sp,
        color = Color(0xFF0F172A)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 2.dp)
      ) {
        Icon(
          imageVector = Icons.Default.AccountBalance,
          contentDescription = "Source",
          tint = Color(0xFF64748B),
          modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = item.source,
          fontSize = 10.sp,
          color = Color(0xFF64748B)
        )
      }

      Text(
        text = "📌 ${item.description}",
        fontSize = 9.5.sp,
        color = Color(0xFF475569),
        lineHeight = 14.sp,
        modifier = Modifier.padding(top = 4.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Action Buttons: [📄 O/L ප්‍රශ්න පත්‍රය] and [📝 පිළිතුරු පත්‍රය (Marking Scheme)]
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Question Paper Button
        Button(
          onClick = onOpenQuestionPaper,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("open_ol_paper_${item.year}")
        ) {
          Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = "Question Paper",
            tint = Color(0xFFF87171),
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "O/L ප්‍රශ්න පත්‍රය",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }

        // Marking Scheme Button
        Button(
          onClick = onOpenAnswerScheme,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (item.isLatest) Color(0xFF1D4ED8) else Color(0xFF047857)
          ),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("open_ol_marking_scheme_${item.year}")
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Marking Scheme",
            tint = Color(0xFF93C5FD),
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "පිළිතුරු පත්‍රය",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}

