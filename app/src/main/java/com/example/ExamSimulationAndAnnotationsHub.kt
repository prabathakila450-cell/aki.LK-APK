package com.example

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

// ==============================================================================
// 1. LIVE MOCK EXAM HALL SIMULATION WITH OMR BUBBLE SHEET (සැබෑ විභාග ශාලා අනුකරණය)
// ==============================================================================

data class MockExamQuestion(
  val id: Int,
  val questionText: String,
  val options: List<String>,
  val correctOption: Int, // 1, 2, 3, 4
  val explanation: String,
  val topicName: String
)

data class MockExamPaper(
  val id: String,
  val titleSinhala: String,
  val subject: String,
  val grade: String,
  val term: Int = 1,
  val paperNumber: Int = 1,
  val durationMinutes: Int = 60,
  val totalQuestions: Int = 60,
  val syllabusCoverageSummary: String = "",
  val questions: List<MockExamQuestion>,
  val pdfUri: String? = null
)

object MockExamRepository {
  fun getAvailableMockExams(grade: String, term: Int = 1, subject: String = "විද්‍යාව"): List<MockExamPaper> {
    return MockExamDataBank.getPapersForTermAndSubject(grade, term, subject)
  }

  fun getAvailableMockExams(grade: String): List<MockExamPaper> {
    return MockExamDataBank.getPapersForTermAndSubject(grade, 1, "විද්‍යාව")
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMockExamHallScreen(
  grade: String,
  onBack: () -> Unit,
  onOpenPdfModal: (url: String, title: String) -> Unit = { _, _ -> },
  onOpenMistakeNotebook: () -> Unit = {}
) {
  val availableGrades = listOf("11", "10")
  var selectedGrade by remember(grade) {
    val clean = if (grade.contains("10")) "10" else "11"
    mutableStateOf(clean)
  }

  var selectedTerm by remember { mutableStateOf(1) } // 1, 2, 3
  var selectedSubject by remember { mutableStateOf("විද්‍යාව") }

  val mockExams = remember(selectedGrade, selectedTerm, selectedSubject) {
    MockExamDataBank.getPapersForTermAndSubject(selectedGrade, selectedTerm, selectedSubject)
  }

  val syllabusSummary = remember(selectedSubject, selectedTerm) {
    MockExamDataBank.getSyllabusSummary(selectedSubject, selectedTerm)
  }

  var selectedExam by remember { mutableStateOf<MockExamPaper?>(null) }

  if (selectedExam == null) {
    // EXAM LOBBY SCREEN
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Column {
              Text("⏱️ සැබෑ විභාග ශාලා අනුකරණය", fontWeight = FontWeight.Bold, fontSize = 16.sp)
              Text("Live Mock Exam Hall • 60Q per paper • 3 Terms", fontSize = 11.sp, color = NeutralMedium)
            }
          },
          navigationIcon = {
            IconButton(onClick = onBack) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
          },
          actions = {
            TextButton(
              onClick = onOpenMistakeNotebook,
              modifier = Modifier.padding(end = 4.dp)
            ) {
              Icon(Icons.Default.WarningAmber, contentDescription = "Mistakes", tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Mistake Bank", color = Color(0xFFDC2626), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
      }
    ) { padding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .background(Color(0xFFF8FAFC))
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Hero Card
        item {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = Color(0xFFEF4444)
                ) {
                  Text(
                    text = "OMR LIVE EXAM HALL",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("🔥 ප්‍රශ්න 60 පූර්ණ පෙරහුරුව", color = Color(0xFFFDE047), fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "ශ්‍රේණියේ වාර 3 ට අදාළ සම්පූර්ණ විෂය නිර්දේශය ආවරණය වන ප්‍රශ්න 60 පත්‍රිකා 10ක් එක් විෂයකට!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 22.sp
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "✓ ප්‍රශ්න 60 සම්පූර්ණ OMR පත්‍රය • ✓ මිනිත්තු 60 Countdown • ✓ වාර විෂය නිර්දේශ ආවරණය • ✓ විස්තරාත්මක විවරණ",
                fontSize = 11.sp,
                color = Color(0xFF94A3B8)
              )
            }
          }
        }

        // Grade Selector Filter Row
        item {
          Column {
            Text("ශ්‍රේණිය තෝරන්න (Select Grade):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeutralDark)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(availableGrades) { gr ->
                val isSelected = selectedGrade == gr
                Surface(
                  onClick = { selectedGrade = gr },
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) BluePrimary else Color.White,
                  border = BorderStroke(1.dp, if (isSelected) BluePrimary else Color(0xFFCBD5E1))
                ) {
                  Text(
                    text = "$gr ශ්‍රේණිය",
                    color = if (isSelected) Color.White else NeutralDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                  )
                }
              }
            }
          }
        }

        // Term Selector (1st, 2nd, 3rd Term)
        item {
          Column {
            Text("පාසල් වාරය තෝරන්න (School Term):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeutralDark)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listOf(
                Pair(1, "1 වන වාරය (Term 1)"),
                Pair(2, "2 වන වාරය (Term 2)"),
                Pair(3, "3 වන වාරය (Term 3)")
              ).forEach { (tNum, tLabel) ->
                val isSelected = selectedTerm == tNum
                Surface(
                  onClick = { selectedTerm = tNum },
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) Color(0xFF1E293B) else Color.White,
                  border = BorderStroke(1.5.dp, if (isSelected) Color(0xFF0F172A) else Color(0xFFCBD5E1)),
                  modifier = Modifier.weight(1f)
                ) {
                  Column(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = if (isSelected) "🌟 $tLabel" else tLabel,
                      color = if (isSelected) Color(0xFFFDE047) else NeutralDark,
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp,
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }
          }
        }

        // Subject Selector
        item {
          Column {
            Text("විෂය තෝරන්න (Select Subject):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeutralDark)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(MockExamDataBank.AVAILABLE_SUBJECTS) { subj ->
                val isSelected = selectedSubject == subj
                val iconStr = when (subj) {
                  "විද්‍යාව" -> "🔬"
                  "ගණිතය" -> "📐"
                  "ඉතිහාසය" -> "🏛️"
                  "සිංහල" -> "📖"
                  "ඉංග්‍රීසි" -> "🇬🇧"
                  else -> "☸️"
                }
                Surface(
                  onClick = { selectedSubject = subj },
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) Color(0xFF2563EB) else Color.White,
                  border = BorderStroke(1.dp, if (isSelected) Color(0xFF2563EB) else Color(0xFFCBD5E1))
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(iconStr, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = subj,
                      color = if (isSelected) Color.White else NeutralDark,
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp
                    )
                  }
                }
              }
            }
          }
        }

        // Syllabus Coverage Info Card
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
            border = BorderStroke(1.dp, Color(0xFFBFDBFE)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.MenuBook, contentDescription = "Syllabus", tint = Color(0xFF1D4ED8), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "🎯 $selectedTerm වන වාර විෂය නිර්දේශ ආවරණය ($selectedSubject):",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = Color(0xFF1E40AF)
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = syllabusSummary,
                fontSize = 11.sp,
                color = Color(0xFF1E3A8A),
                lineHeight = 17.sp
              )
              Spacer(modifier = Modifier.height(6.dp))
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFDBEAFE)
              ) {
                Text(
                  text = "✓ පහත සෑම ප්‍රශ්න පත්‍රයකම මෙම ඒකකවලින් සමන්විත ප්‍රශ්න 60ක් අන්තර්ගතයි.",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF1D4ED8),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }
          }
        }

        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "ආදර්ශ ප්‍රශ්න පත්‍ර (පත්‍ර 10ක් අන්තර්ගතයි):",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = NeutralDark
            )
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFDCFCE7)
            ) {
              Text(
                text = "ප්‍රශ්න 60 බැගින්",
                color = Color(0xFF15803D),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }

        // List of 10 mock exams
        items(mockExams) { exam ->
          val focusTitle = MockExamDataBank.PAPER_FOCUS_TITLES.getOrElse(exam.paperNumber - 1) { "විෂය නිර්දේශ ආදර්ශ පත්‍රය" }

          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF0F172A)
                  ) {
                    Text(
                      text = "ප්‍රශ්න පත්‍රය ${String.format("%02d", exam.paperNumber)}",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Black,
                      color = Color(0xFFFDE047),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (exam.subject) {
                      "විද්‍යාව" -> Color(0xFFE8F5E9)
                      "ගණිතය" -> Color(0xFFE3F2FD)
                      else -> Color(0xFFF3E8FF)
                    }
                  ) {
                    Text(
                      text = "${exam.term} වන වාරය",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = when (exam.subject) {
                        "විද්‍යාව" -> Color(0xFF1B5E20)
                        "ගණිතය" -> Color(0xFF0D47A1)
                        else -> Color(0xFF6B21A8)
                      },
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                  }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.Timer, contentDescription = "Time", tint = Color(0xFFD97706), modifier = Modifier.size(15.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("${exam.durationMinutes} min", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = exam.titleSinhala,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NeutralDark
              )

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = "විශේෂ ඉලක්කය: $focusTitle",
                fontSize = 11.sp,
                color = Color(0xFF475569)
              )

              Spacer(modifier = Modifier.height(8.dp))

              // Badges row
              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0xFFF1F5F9)
                ) {
                  Text(
                    text = "🎯 ප්‍රශ්න 60යි (MCQ)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0xFFF1F5F9)
                ) {
                  Text(
                    text = "⭕ OMR Bubble Sheet",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0xFFF1F5F9)
                ) {
                  Text(
                    text = "💡 සම්පූර්ණ විවරණ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                if (exam.pdfUri != null) {
                  OutlinedButton(
                    onClick = { onOpenPdfModal(exam.pdfUri, exam.titleSinhala) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ප්‍රශ්න පත්‍රය (PDF)", fontSize = 11.sp)
                  }
                }

                Button(
                  onClick = { selectedExam = exam },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.weight(1.4f).testTag("start_mock_exam_${exam.id}")
                ) {
                  Icon(Icons.Default.PlayArrow, contentDescription = "Start", modifier = Modifier.size(18.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("විභාගය අරඹන්න (60Q)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
              }
            }
          }
        }
      }
    }
  } else {
    // ACTIVE EXAM SESSION / GRADING VIEW
    ActiveMockExamSession(
      exam = selectedExam!!,
      onExit = { selectedExam = null },
      onOpenMistakeNotebook = onOpenMistakeNotebook
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveMockExamSession(
  exam: MockExamPaper,
  onExit: () -> Unit,
  onOpenMistakeNotebook: () -> Unit = {}
) {
  val context = LocalContext.current
  var remainingSeconds by remember { mutableStateOf(exam.durationMinutes * 60) }
  var isTimerRunning by remember { mutableStateOf(true) }
  var currentQuestionIndex by remember { mutableStateOf(0) }
  val answersMap = remember { mutableStateMapOf<Int, Int>() } // Question ID -> Selected Bubble (1..4)
  val flaggedQuestions = remember { mutableStateListOf<Int>() }
  var isExamSubmitted by remember { mutableStateOf(false) }
  var showConfirmSubmitDialog by remember { mutableStateOf(false) }
  var activeTab by remember { mutableStateOf(0) } // 0: ප්‍රශ්න විචාරය (Question View), 1: OMR Sheet Matrix
  var mistakesSavedToNotebook by remember { mutableStateOf(false) }

  // Auto-save wrong questions to Mistake Notebook upon exam submission
  LaunchedEffect(isExamSubmitted) {
    if (isExamSubmitted && !mistakesSavedToNotebook) {
      val wrongList = exam.questions.filter { answersMap[it.id] != it.correctOption }
      if (wrongList.isNotEmpty()) {
        val mistakeItems = wrongList.map { q ->
          MistakeItem(
            id = "exam_${exam.id}_q_${q.id}",
            subject = exam.subject,
            topic = q.topicName,
            grade = exam.grade,
            questionText = q.questionText,
            options = q.options,
            correctOptionIndex = q.correctOption - 1,
            explanation = q.explanation,
            userWrongAnswerIndex = (answersMap[q.id] ?: 0) - 1
          )
        }
        MistakeNotebookRepository.addMistakes(context, mistakeItems)
        mistakesSavedToNotebook = true
      }
    }
  }

  // Timer Effect
  LaunchedEffect(isTimerRunning, isExamSubmitted) {
    while (isTimerRunning && !isExamSubmitted && remainingSeconds > 0) {
      delay(1000L)
      remainingSeconds--
    }
    if (remainingSeconds == 0 && !isExamSubmitted) {
      isExamSubmitted = true
    }
  }

  val minutes = remainingSeconds / 60
  val seconds = remainingSeconds % 60
  val timeString = String.format("%02d:%02d", minutes, seconds)

  if (isExamSubmitted) {
    // ----------------------------------------------------
    // EXAM RESULTS & REVIEW SCREEN
    // ----------------------------------------------------
    val correctCount = exam.questions.count { answersMap[it.id] == it.correctOption }
    val totalCount = exam.questions.size
    val scorePercentage = ((correctCount.toFloat() / totalCount) * 100).toInt()
    val gradeStatus = when {
      scorePercentage >= 75 -> "A (විශිෂ්ට සාමාර්ථ්‍යයක්!)"
      scorePercentage >= 65 -> "B (ඉතා හොඳ සාමාර්ථ්‍යයක්)"
      scorePercentage >= 50 -> "C (සම්මාන සාමාර්ථ්‍යයක්)"
      scorePercentage >= 35 -> "S (සාමාන්‍ය සාමාර්ථ්‍යයක්)"
      else -> "W (නැවත උත්සාහ කරන්න)"
    }

    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("📊 විභාග ප්‍රතිඵල සහ විවරණය", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
          navigationIcon = {
            IconButton(onClick = onExit) {
              Icon(Icons.Default.Close, contentDescription = "Close")
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
      }
    ) { padding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .background(Color(0xFFF8FAFC))
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (scorePercentage >= 50) Color(0xFFF0FDF4) else Color(0xFFFEF2F2)
            ),
            border = BorderStroke(1.5.dp, if (scorePercentage >= 50) Color(0xFF86EFAC) else Color(0xFFFECACA)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = if (scorePercentage >= 75) "🎉 සුබ පැතුම්! විශිෂ්ට ජයග්‍රහණයක්!" else if (scorePercentage >= 50) "👏 සාර්ථකයි! දිගටම පුහුණු වන්න" else "💪 ධෛර්යයෙන් නැවත උත්සාහ කරන්න!",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (scorePercentage >= 50) Color(0xFF15803D) else Color(0xFFB91C1C)
              )

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "$scorePercentage%",
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = if (scorePercentage >= 50) Color(0xFF15803D) else Color(0xFFB91C1C)
              )

              Text(
                text = "සාමාර්ථය: $gradeStatus",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NeutralDark
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text("නිවැරදි පිළිතුරු", fontSize = 11.sp, color = NeutralMedium)
                  Text("✅ $correctCount / $totalCount", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF15803D))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text("වැරදුණු පිළිතුරු", fontSize = 11.sp, color = NeutralMedium)
                  Text("❌ ${totalCount - correctCount}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFB91C1C))
                }
              }
            }
          }
        }

        val wrongQuestions = exam.questions.filter { answersMap[it.id] != it.correctOption }
        if (wrongQuestions.isNotEmpty()) {
          item {
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
              border = BorderStroke(1.dp, Color(0xFFFECDD3)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(32.dp)
                      .clip(CircleShape)
                      .background(Color(0xFFE11D48)),
                    contentAlignment = Alignment.Center
                  ) {
                    Text("⚠️", fontSize = 16.sp)
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = "වැරදුණු ප්‍රශ්න ${wrongQuestions.size}ක් Mistake Bank එකට සුරැකිණි",
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.5.sp,
                      color = Color(0xFF9F1239)
                    )
                    Text(
                      text = "ඔබට මෙම ප්‍රශ්න පමණක් තෝරා නැවත පුහුණු විය හැක",
                      fontSize = 10.5.sp,
                      color = Color(0xFFBE123C)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                  onClick = onOpenMistakeNotebook,
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Icon(Icons.Default.Refresh, contentDescription = "Revision", modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("⚠️ වැරදුණු ප්‍රශ්න පමණක් නැවත පුහුණු වන්න", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }

        item {
          Text(
            text = "ප්‍රශ්න විවරණය සහ නිවැරදි පිළිතුරු (Marking Scheme Review):",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = NeutralDark
          )
        }

        itemsIndexed(exam.questions) { idx, q ->
          val userChoice = answersMap[q.id]
          val isCorrect = userChoice == q.correctOption

          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, if (isCorrect) Color(0xFFBBF7D0) else Color(0xFFFECDD3)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "ප්‍රශ්නය ${idx + 1} (${q.topicName})",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C)
                )

                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFFE4E6)
                ) {
                  Text(
                    text = if (isCorrect) "නිවැරදියි (+1)" else "වැරදියි (0)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color(0xFF15803D) else Color(0xFFBE123C),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = q.questionText,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NeutralDark
              )

              Spacer(modifier = Modifier.height(8.dp))

              q.options.forEachIndexed { optIdx, optText ->
                val bubbleNum = optIdx + 1
                val isSelectedByUser = userChoice == bubbleNum
                val isThisCorrectAnswer = q.correctOption == bubbleNum

                val bubbleBg = when {
                  isThisCorrectAnswer -> Color(0xFFDCFCE7)
                  isSelectedByUser -> Color(0xFFFFE4E6)
                  else -> Color(0xFFF8FAFC)
                }

                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = bubbleBg,
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = "($bubbleNum) $optText",
                      fontSize = 12.sp,
                      fontWeight = if (isThisCorrectAnswer || isSelectedByUser) FontWeight.Bold else FontWeight.Normal,
                      color = if (isThisCorrectAnswer) Color(0xFF15803D) else if (isSelectedByUser) Color(0xFFBE123C) else NeutralDark
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (isThisCorrectAnswer) {
                      Text("✓ නිවැරදි පිළිතුර", fontSize = 10.sp, color = Color(0xFF15803D), fontWeight = FontWeight.Bold)
                    } else if (isSelectedByUser) {
                      Text("ඔබගේ තේරීම", fontSize = 10.sp, color = Color(0xFFBE123C))
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF1F5F9),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = "💡 විවරණය: ${q.explanation}",
                  fontSize = 11.sp,
                  color = Color(0xFF334155),
                  modifier = Modifier.padding(8.dp)
                )
              }
            }
          }
        }

        item {
          Button(
            onClick = onExit,
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
          ) {
            Text("පෙරහුරු විභාග මෙනුවට ආපසු යන්න", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  } else {
    // ----------------------------------------------------
    // LIVE EXAM RUNNING SCREEN (OMR + QUESTION NAVIGATOR)
    // ----------------------------------------------------
    Scaffold(
      topBar = {
        Surface(
          color = Color(0xFF0F172A),
          shadowElevation = 4.dp
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .statusBarsPadding()
              .padding(horizontal = 14.dp, vertical = 10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = exam.titleSinhala,
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
                Text(
                  text = "පිළිතුරු සැපයූ ප්‍රශ්න: ${answersMap.size} / ${exam.questions.size}",
                  fontSize = 11.sp,
                  color = Color(0xFF94A3B8)
                )
              }

              // Live Countdown Pill with Official Time Warning
              val isLowTime = remainingSeconds < 300
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isLowTime) Color(0xFFDC2626) else Color(0xFF1E293B),
                border = BorderStroke(1.dp, if (isLowTime) Color(0xFFF87171) else Color(0xFF334155))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = if (isLowTime) Icons.Default.WarningAmber else Icons.Default.HourglassBottom,
                    contentDescription = "Timer",
                    tint = if (isLowTime) Color(0xFFFEF08A) else Color.White,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = timeString,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                  )
                  if (isLowTime && remainingSeconds > 0) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "⚠️ 5 min",
                      fontSize = 10.sp,
                      color = Color(0xFFFEF08A),
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sub-Tabs: Single Question View vs Full OMR Sheet Matrix
            TabRow(
              selectedTabIndex = activeTab,
              containerColor = Color(0xFF1E293B),
              contentColor = Color.White,
              indicator = {}
            ) {
              Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("📝 ප්‍රශ්නය සහ විකල්ප", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
              )
              Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("⭕ OMR පිළිතුරු පත්‍රය (${answersMap.size}/${exam.questions.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
              )
            }
          }
        }
      },
      bottomBar = {
        Surface(
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier.navigationBarsPadding()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedButton(
              onClick = {
                if (currentQuestionIndex > 0) currentQuestionIndex--
              },
              enabled = currentQuestionIndex > 0,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("පෙර ප්‍රශ්නය", fontSize = 11.sp)
            }

            Button(
              onClick = { showConfirmSubmitDialog = true },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("submit_exam_button")
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = "Submit", modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("විභාගය අවසන් කරන්න", fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }

            Button(
              onClick = {
                if (currentQuestionIndex < exam.questions.size - 1) currentQuestionIndex++
              },
              enabled = currentQuestionIndex < exam.questions.size - 1,
              colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("ඊළඟ ප්‍රශ්නය", fontSize = 11.sp)
            }
          }
        }
      }
    ) { padding ->
      if (activeTab == 0) {
        // SINGLE QUESTION + OMR BUBBLE BAR
        val q = exam.questions[currentQuestionIndex]
        val selectedBubble = answersMap[q.id]
        val isFlagged = flaggedQuestions.contains(q.id)

        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color(0xFFF8FAFC))
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Question Header & Flag Toggle
          item {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFE2E8F0)
              ) {
                Text(
                  text = "ප්‍රශ්නය ${currentQuestionIndex + 1} / ${exam.questions.size}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = NeutralDark,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
              }

              Surface(
                onClick = {
                  if (isFlagged) flaggedQuestions.remove(q.id) else flaggedQuestions.add(q.id)
                },
                shape = RoundedCornerShape(8.dp),
                color = if (isFlagged) Color(0xFFFEF3C7) else Color.White,
                border = BorderStroke(1.dp, if (isFlagged) Color(0xFFF59E0B) else Color(0xFFCBD5E1))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = if (isFlagged) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Review",
                    tint = if (isFlagged) Color(0xFFD97706) else NeutralMedium,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = if (isFlagged) "නැවත බැලීමට (Marked)" else "නැවත බැලීමට ලකුණු කරන්න",
                    fontSize = 10.sp,
                    color = if (isFlagged) Color(0xFFD97706) else NeutralMedium
                  )
                }
              }
            }
          }

          // Question Card
          item {
            Card(
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = q.questionText,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = NeutralDark,
                  lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                q.options.forEachIndexed { optIdx, optText ->
                  val bubbleNum = optIdx + 1
                  val isSelected = selectedBubble == bubbleNum

                  Surface(
                    onClick = {
                      answersMap[q.id] = bubbleNum
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color(0xFFEFF6FF) else Color(0xFFF8FAFC),
                    border = BorderStroke(1.5.dp, if (isSelected) BluePrimary else Color(0xFFE2E8F0)),
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 4.dp)
                  ) {
                    Row(
                      modifier = Modifier.padding(12.dp),
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      // OMR Circle Bubble
                      Box(
                        modifier = Modifier
                          .size(28.dp)
                          .clip(CircleShape)
                          .background(if (isSelected) Color(0xFF1E293B) else Color.White)
                          .border(2.dp, if (isSelected) Color(0xFF1E293B) else Color(0xFF94A3B8), CircleShape),
                        contentAlignment = Alignment.Center
                      ) {
                        Text(
                          text = "$bubbleNum",
                          fontWeight = FontWeight.Black,
                          fontSize = 12.sp,
                          color = if (isSelected) Color.White else Color(0xFF64748B)
                        )
                      }

                      Spacer(modifier = Modifier.width(12.dp))

                      Text(
                        text = optText,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) BluePrimary else NeutralDark
                      )
                    }
                  }
                }
              }
            }
          }

          // Fast OMR Bubble Strip at the bottom
          item {
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("OMR තිත් තැබීම:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeutralDark)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  (1..4).forEach { bNum ->
                    val isSelected = selectedBubble == bNum
                    Box(
                      modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF0F172A) else Color.White)
                        .border(1.5.dp, if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8), CircleShape)
                        .clickable { answersMap[q.id] = bNum },
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = "($bNum)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else NeutralDark
                      )
                    }
                  }
                }
              }
            }
          }
        }
      } else {
        // FULL OMR SHEET MATRIX (All 1..20/40 questions bubble sheet)
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color(0xFFF8FAFC))
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
              border = BorderStroke(1.dp, Color(0xFFFDE68A)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "සැබෑ OMR පත්‍රයක මෙන් එක් එක් ප්‍රශ්නයට අදාළ අංකය මත ක්ලික් කර පිළිතුරු සලකුණු කරන්න.",
                  fontSize = 11.sp,
                  color = Color(0xFF92400E)
                )
              }
            }
          }

          itemsIndexed(exam.questions) { idx, q ->
            val selected = answersMap[q.id]
            val isFlagged = flaggedQuestions.contains(q.id)

            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (selected != null) Color(0xFFF0FDF4) else Color.White
              ),
              border = BorderStroke(1.dp, if (selected != null) Color(0xFFBBF7D0) else Color(0xFFE2E8F0)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                  currentQuestionIndex = idx
                  activeTab = 0
                }) {
                  Text(
                    text = "Q${idx + 1}.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = if (selected != null) Color(0xFF15803D) else NeutralDark
                  )
                  if (isFlagged) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("🚩", fontSize = 10.sp)
                  }
                }

                // 4 OMR Bubbles
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  (1..4).forEach { bNum ->
                    val isChosen = selected == bNum
                    Box(
                      modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(if (isChosen) Color(0xFF0F172A) else Color.White)
                        .border(1.5.dp, if (isChosen) Color(0xFF0F172A) else Color(0xFFCBD5E1), CircleShape)
                        .clickable { answersMap[q.id] = bNum },
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = "$bNum",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isChosen) Color.White else NeutralDark
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Confirm Submit Dialog
  if (showConfirmSubmitDialog) {
    AlertDialog(
      onDismissRequest = { showConfirmSubmitDialog = false },
      title = { Text("විභාගය අවසන් කරන්නද?", fontWeight = FontWeight.Bold) },
      text = {
        Text("ඔබ ප්‍රශ්න ${exam.questions.size} න් ${answersMap.size} කට පිළිතුරු සපයා ඇත. විභාගය අවසන් කර ප්‍රතිඵල සහ ලකුණු විවරණය බැලීමට අවශ්‍යද?")
      },
      confirmButton = {
        Button(
          onClick = {
            showConfirmSubmitDialog = false
            isExamSubmitted = true
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
        ) {
          Text("ඔව්, අවසන් කරන්න")
        }
      },
      dismissButton = {
        TextButton(onClick = { showConfirmSubmitDialog = false }) {
          Text("තවදුරටත් ලියන්න")
        }
      }
    )
  }
}

// ==============================================================================
// 2. EXAM SPOT TOPICS & TREND PREDICTOR (විභාග ඉලක්කගත අනුමාන මාතෘකා)
// ==============================================================================

data class SpotTopicItem(
  val id: String,
  val topicSinhala: String,
  val probabilityPercent: Int, // e.g. 95%
  val priorityBadge: String, // 🔥 ඉහළම සම්භාවිතාව, 🎯 නිශ්චිත රචනා, ⭐️ සූත්‍ර හා ගණනය
  val questionFormat: String, // e.g. "I පත්‍රයේ MCQ 3ක් සහ II පත්‍රයේ ව්‍යුහගත රචනා B කොටසේ 1 ප්‍රශ්නයක්"
  val keyConcepts: List<String>,
  val pastExamFrequency: String, // e.g. "2019, 2021, 2023, 2024 වසරවල අඛණ්ඩව ප්‍රශ්න පැමිණ ඇත."
  val pdfUri: String? = null
)

object SpotTopicsRepository {
  fun getSpotTopics(subject: String, grade: String): List<SpotTopicItem> {
    return when (subject) {
      "විද්‍යාව" -> listOf(
        SpotTopicItem(
          id = "spot_sci_1",
          topicSinhala = "ප්‍රභාසංශ්ලේෂණය සහ ජීවයේ රසායනික පදනම",
          probabilityPercent = 95,
          priorityBadge = "🔥 95% ඉහළම සම්භාවිතාව",
          questionFormat = "Part I හි MCQ 2-3ක් සහ Part II හි ව්‍යුහගත රචනා ප්‍රශ්න අංක 1",
          keyConcepts = listOf("ආලෝක හා අඳුරු ප්‍රතික්‍රියා සංසන්දනය", "ප්‍රභාසංශ්ලේෂණ අනුපාතය කෙරෙහි බලපාන සාධක පරීක්ෂණ", "ATP සහ NADPH භාවිතය"),
          pastExamFrequency = "පසුගිය වසර 7 තුළම ප්‍රශ්න පත්‍රයේ නිශ්චිතව අන්තර්ගත විය.",
          pdfUri = "https://drive.google.com/file/d/1IQntv3Yh1Oaxh42-btqYaFNNA9uijfx_/preview"
        ),
        SpotTopicItem(
          id = "spot_sci_2",
          topicSinhala = "ධාරා විද්‍යුතය සහ ඕම්ගේ නියමය ප්‍රස්තාරික විශ්ලේෂණය",
          probabilityPercent = 92,
          priorityBadge = "🎯 නිශ්චිත ප්‍රායෝගික පරීක්ෂණ",
          questionFormat = "Part II හි ව්‍යුහගත ප්‍රශ්න අංක 3 හෝ 4",
          keyConcepts = listOf("ශ්‍රේණිගත හා සමාන්තරගත ප්‍රතිරෝධ සමක සෙවීම", "V-I ප්‍රස්තාරයෙන් ප්‍රතිරෝධය සෙවීම", "විද්‍යුත් බලය P = VI සූත්‍ර ගණනය කිරීම්"),
          pastExamFrequency = "2018 සිට 2024 දක්වා සෑම වාර විභාගයකම ප්‍රධාන ප්‍රශ්නයකි.",
          pdfUri = "https://drive.google.com/file/d/1V3y65z_15X6zjruQ_I11WhG4EOfDHGm-/preview"
        ),
        SpotTopicItem(
          id = "spot_sci_3",
          topicSinhala = "මූලද්‍රව්‍ය ආවර්තිතාවය සහ රසායනික බන්ධන",
          probabilityPercent = 88,
          priorityBadge = "⭐️ වැදගත් රසායන විද්‍යා ඉලක්ක",
          questionFormat = "Part I MCQ 3ක් සහ Part II රචනා ප්‍රශ්න",
          keyConcepts = listOf("අයනික හා සහසංයුජ බන්ධන ලක්ෂ්‍ය-ලක්ෂ ක්‍රමය", "විද්‍යුත් සෘණතාව සහ අයනීකරණ ශක්ති රටා", "ලෝහවල ප්‍රතික්‍රියාශීලීතා ශ්‍රේණිය"),
          pastExamFrequency = "පසුගිය වසරවල අඛණ්ඩව ප්‍රශ්න අන්තර්ගත විය.",
          pdfUri = "https://drive.google.com/file/d/1IQntv3Yh1Oaxh42-btqYaFNNA9uijfx_/preview"
        )
      )
      "ගණිතය" -> listOf(
        SpotTopicItem(
          id = "spot_math_1",
          topicSinhala = "වෘත්ත ජ්‍යාමිතිය සහ ස්පර්ශක ප්‍රමේය (Theorems)",
          probabilityPercent = 98,
          priorityBadge = "🔥 98% නිශ්චිත ප්‍රමේය සාධන",
          questionFormat = "Part II B කොටසේ ලකුණු 10ක අනිවාර්ය ජ්‍යාමිතික සාධනය",
          keyConcepts = listOf("කේන්ද්‍ර කෝණය පරිධි කෝණය මෙන් දෙගුණයකි", "චක්‍රීය චතුරස්‍රවල සම්මුඛ කෝණ පරිපූරක වේ", "ඒකාන්තර ඛණ්ඩ ප්‍රමේයය (Alternate Segment Theorem)"),
          pastExamFrequency = "සාමාන්‍ය පෙළ සහ වාර විභාගවල 100% ක් අනිවාර්ය ප්‍රශ්නයකි.",
          pdfUri = "https://drive.google.com/file/d/1V3y65z_15X6zjruQ_I11WhG4EOfDHGm-/preview"
        ),
        SpotTopicItem(
          id = "spot_math_2",
          topicSinhala = "ත්‍රිකෝණමිතිය උන්නතාංශ හා අවනතාංශ ගැටලු",
          probabilityPercent = 90,
          priorityBadge = "🎯 ලකුණු 10ක රචනා ගැටලුව",
          questionFormat = "Part II B කොටසේ ලකුණු 10ක ප්‍රශ්නයක්",
          keyConcepts = listOf("sin, cos, tan අනුපාත යෙදීම", "උන්නතාංශ හා අවනතාංශ රූපසටහන් ඇඳීම", "ලඝුගණක මගින් සුළු කිරීම්"),
          pastExamFrequency = "සෑම වසරකම Part II ප්‍රශ්න පත්‍රයට පැමිණේ.",
          pdfUri = "https://drive.google.com/file/d/1V3y65z_15X6zjruQ_I11WhG4EOfDHGm-/preview"
        ),
        SpotTopicItem(
          id = "spot_math_3",
          topicSinhala = "සමාන්තර හා ගුණෝත්තර ශ්‍රේඪි (Series)",
          probabilityPercent = 89,
          priorityBadge = "⭐️ සූත්‍ර හා පද සෙවීම්",
          questionFormat = "Part I කෙටි ප්‍රශ්න 1ක් සහ Part II හි ලකුණු 10ක ප්‍රශ්නයක්",
          keyConcepts = listOf("Tn = a + (n-1)d සහ Sn = n/2 [2a + (n-1)d]", "ගුණෝත්තර ශ්‍රේඪි Tn = ar^(n-1)", "වචන ගැටලු සමීකරණ බවට පත්කිරීම"),
          pastExamFrequency = "2020-2024 අඛණ්ඩව ප්‍රශ්න අන්තර්ගත විය.",
          pdfUri = "https://drive.google.com/file/d/1V3y65z_15X6zjruQ_I11WhG4EOfDHGm-/preview"
        )
      )
      "ඉතිහාසය" -> listOf(
        SpotTopicItem(
          id = "spot_hist_1",
          topicSinhala = "අනුරාධපුර වාරි කර්මාන්තය සහ මහා පරාක්‍රමබාහු රජු",
          probabilityPercent = 96,
          priorityBadge = "🔥 96% ප්‍රධාන රචනා ඉලක්කය",
          questionFormat = "Part II හි ලකුණු 15ක ප්‍රධාන රචනා ප්‍රශ්නයක් + සිතියම් ලකුණු කිරීම්",
          keyConcepts = listOf("මහා පරාක්‍රමබාහු රජුගේ වාරි හා කෘෂිකාර්මික ප්‍රතිසංස්කරණ", "පරාක්‍රම සමුද්‍රය හා ඇළ මාර්ග", "සිතියමේ වාරි වැව් 3ක් ලකුණු කිරීම"),
          pastExamFrequency = "සෑම වසරකම පාහේ රචනා හෝ සිතියම් ප්‍රශ්නයක් ලෙස පැමිණේ.",
          pdfUri = "https://drive.google.com/file/d/1Ry6utaFim_tZl8OkTG5hoD6oB4RB8Uxl/preview"
        ),
        SpotTopicItem(
          id = "spot_hist_2",
          topicSinhala = "1818 සහ 1848 නිදහස් අරගල සංසන්දනය",
          probabilityPercent = 91,
          priorityBadge = "🎯 වැදගත් ඓතිහාසික පසුබිම",
          questionFormat = "Part II රචනා ප්‍රශ්න",
          keyConcepts = listOf("1818 කැරැල්ලට හේතු සහ කැප්පෙටිපොළ නිලමේගේ නායකත්වය", "1848 මාතලේ කැරැල්ල හා පුරන් අප්පු", "බ්‍රිතාන්‍ය ප්‍රතිපත්තිවල බලපෑම"),
          pastExamFrequency = "පසුගිය වසරවල මාරුවෙන් මාරුවට අසන ප්‍රධාන මාතෘකාවකි.",
          pdfUri = "https://drive.google.com/file/d/1Ry6utaFim_tZl8OkTG5hoD6oB4RB8Uxl/preview"
        )
      )
      else -> listOf(
        SpotTopicItem(
          id = "spot_gen_1",
          topicSinhala = "$subject - ප්‍රධාන විෂය නිර්දේශ ඉලක්ක හා සංකල්ප",
          probabilityPercent = 90,
          priorityBadge = "🔥 ඉහළ සම්භාවිතාව",
          questionFormat = "Part I MCQ සහ Part II ව්‍යුහගත රචනා",
          keyConcepts = listOf("ප්‍රධාන නියම සහ අර්ථ දැක්වීම්", "ප්‍රායෝගික උදාහරණ හා යෙදීම්", "කෙටි සටහන් සම්පූර්ණ කියවීම"),
          pastExamFrequency = "විභාග ප්‍රශ්න පත්‍රයේ ප්‍රධාන කොටසකි.",
          pdfUri = "https://drive.google.com/file/d/1IQntv3Yh1Oaxh42-btqYaFNNA9uijfx_/preview"
        )
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamSpotTopicsScreen(
  grade: String,
  onBack: () -> Unit,
  onOpenPdfModal: (url: String, title: String) -> Unit = { _, _ -> }
) {
  val subjects = listOf("විද්‍යාව", "ගණිතය", "ඉතිහාසය", "සිංහල", "තොරතුරු තාක්ෂණය (ICT)", "බුද්ධ ධර්මය", "භූගෝල විද්‍යාව", "වාණිජ")
  var selectedSubject by remember { mutableStateOf("විද්‍යාව") }
  val spotTopics = remember(selectedSubject, grade) { SpotTopicsRepository.getSpotTopics(selectedSubject, grade) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text("🎯 විභාග ඉලක්කගත අනුමාන මාතෘකා", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("O/L & Term Exam Spot Topics & Trends", fontSize = 11.sp, color = NeutralMedium)
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    }
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .background(Color(0xFFF8FAFC))
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Header Banner
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF4338CA)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("🔥", fontSize = 18.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text("පසුගිය වසර 10 ප්‍රශ්න රටා විශ්ලේෂණය", color = Color(0xFFFDE047), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "විභාගයට ඒමට වැඩිම සම්භාවිතාවක් ඇති ප්‍රධාන පාඩම් සහ රචනා ප්‍රශ්න රටාවන් මෙහි දක්වා ඇත.",
              color = Color.White,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )
          }
        }
      }

      // Subject Selector Chips
      item {
        Text("විෂය තෝරන්න:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeutralDark)
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(subjects) { sub ->
            FilterChip(
              selected = selectedSubject == sub,
              onClick = { selectedSubject = sub },
              label = { Text(sub, fontSize = 11.sp, fontWeight = if (selectedSubject == sub) FontWeight.Bold else FontWeight.Normal) }
            )
          }
        }
      }

      items(spotTopics) { item ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                border = BorderStroke(1.dp, Color(0xFFFECACA))
              ) {
                Text(
                  text = item.priorityBadge,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFDC2626),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }

              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFEEF2FF)
              ) {
                Text(
                  text = "සම්භාවිතාව: ${item.probabilityPercent}%",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF4338CA),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = item.topicSinhala,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = NeutralDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFF1F5F9),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text("📋 විභාග ප්‍රශ්න ආකෘතිය:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                Text(item.questionFormat, fontSize = 12.sp, color = Color(0xFF1E293B))
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("🎯 අනිවාර්යයෙන් පාඩම් කළ යුතු ප්‍රධාන සංකල්ප:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeutralDark)
            Spacer(modifier = Modifier.height(4.dp))
            item.keyConcepts.forEach { concept ->
              Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                Text("• ", fontSize = 12.sp, color = BluePrimary, fontWeight = FontWeight.Bold)
                Text(concept, fontSize = 12.sp, color = Color(0xFF475569))
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.HistoryEdu, contentDescription = "Past Trends", tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(item.pastExamFrequency, fontSize = 11.sp, color = Color(0xFFB45309))
            }

            if (item.pdfUri != null) {
              Spacer(modifier = Modifier.height(12.dp))
              Button(
                onClick = { onOpenPdfModal(item.pdfUri, item.topicSinhala) },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(38.dp)
              ) {
                Icon(Icons.Default.MenuBook, contentDescription = "Read Note", modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("අදාළ කෙටි සටහන බලන්න (PDF)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// ==============================================================================
// 3. SMART PDF DIGITAL STICKY NOTES & HIGHLIGHT TOOL (සටහන් Highlight කිරීම)
// ==============================================================================

data class PdfStickyNote(
  val id: String,
  val pdfTitle: String,
  val noteText: String,
  val colorHex: Long, // Color int
  val timestamp: String,
  val tag: String
)

object PdfAnnotationManager {
  val notesList = mutableStateListOf(
    PdfStickyNote(
      id = "note_1",
      pdfTitle = "විද්‍යාව කෙටි සටහන්",
      noteText = "ආලෝක ප්‍රතික්‍රියාව සිදුවන්නේ තයිලකොයිඩ පටලයේය. අඳුරු ප්‍රතික්‍රියාව ස්ට්‍රෝමාවේය. විභාගයට අනිවාර්යයි!",
      colorHex = 0xFFFEF08A, // Yellow
      timestamp = "අද, පෙ.ව. 10:30",
      tag = "🔥 විභාග ඉලක්ක"
    ),
    PdfStickyNote(
      id = "note_2",
      pdfTitle = "ගණිතය ජ්‍යාමිතිය",
      noteText = "වෘත්තයක කේන්ද්‍රයේ සිට ජ්‍යායකට අඳින ලම්භය මගින් ජ්‍යාය සමච්ඡේද වේ. සාධනය සූදානම් කරගන්න.",
      colorHex = 0xFFBBF7D0, // Green
      timestamp = "ඊයේ, ප.ව. 4:15",
      tag = "⭐️ ප්‍රමේයය"
    )
  )

  fun addNote(pdfTitle: String, text: String, colorHex: Long, tag: String) {
    val sdf = SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
    notesList.add(
      0,
      PdfStickyNote(
        id = System.currentTimeMillis().toString(),
        pdfTitle = pdfTitle,
        noteText = text,
        colorHex = colorHex,
        timestamp = sdf.format(Date()),
        tag = tag
      )
    )
  }

  fun deleteNote(id: String) {
    notesList.removeAll { it.id == id }
  }
}

@Composable
fun SmartPdfStickyNotesOverlay(
  pdfTitle: String,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(false) }
  var newNoteText by remember { mutableStateOf("") }
  var selectedColorHex by remember { mutableStateOf(0xFFFEF08A) } // Yellow
  var selectedTag by remember { mutableStateOf("🔥 විභාග ඉලක්ක") }

  val relevantNotes = PdfAnnotationManager.notesList.filter {
    it.pdfTitle.contains(pdfTitle, ignoreCase = true) || pdfTitle.contains(it.pdfTitle, ignoreCase = true)
  }

  Box(modifier = modifier) {
    if (!isExpanded) {
      // Floating Sticky Notes Button
      Surface(
        onClick = { isExpanded = true },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFEF08A),
        border = BorderStroke(1.dp, Color(0xFFFACC15)),
        shadowElevation = 6.dp,
        modifier = Modifier.padding(12.dp).testTag("pdf_sticky_notes_toggle")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("📝", fontSize = 16.sp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Sticky Notes (${relevantNotes.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = Color(0xFF713F12)
          )
        }
      }
    } else {
      // Expanded Notes Panel Dialog / Bottom Drawer
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
          .fillMaxWidth(0.92f)
          .heightIn(max = 420.dp)
          .padding(8.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          // Header
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("📝", fontSize = 18.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text("මගේ සටහන් & Highlights", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeutralDark)
            }

            IconButton(onClick = { isExpanded = false }, modifier = Modifier.size(26.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = NeutralMedium)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Color Palette Picker (Yellow, Green, Pink, Blue)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("වර්ණය:", fontSize = 10.sp, color = NeutralMedium)
            listOf(
              0xFFFEF08A to "කහ",
              0xFFBBF7D0 to "කොළ",
              0xFFFBCFE8 to "රෝස",
              0xFFBFDBFE to "නිල්"
            ).forEach { (cHex, _) ->
              Box(
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(Color(cHex))
                  .border(
                    if (selectedColorHex == cHex) 2.dp else 1.dp,
                    if (selectedColorHex == cHex) Color.Black else Color.LightGray,
                    CircleShape
                  )
                  .clickable { selectedColorHex = cHex }
              )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Quick Tag Chip
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFFF1F5F9)
            ) {
              Text(
                text = selectedTag,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = BluePrimary,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Add Note Field
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            androidx.compose.material3.OutlinedTextField(
              value = newNoteText,
              onValueChange = { newNoteText = it },
              placeholder = { Text("වැදගත් කරුණක් මෙහි ලියා සුරකින්න...", fontSize = 11.sp) },
              modifier = Modifier.weight(1f),
              singleLine = false,
              maxLines = 2
            )
            Spacer(modifier = Modifier.width(6.dp))
            Button(
              onClick = {
                if (newNoteText.isNotBlank()) {
                  PdfAnnotationManager.addNote(pdfTitle, newNoteText.trim(), selectedColorHex, selectedTag)
                  newNoteText = ""
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(selectedColorHex)),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
              Text("Save", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          HorizontalDivider(color = Color(0xFFF1F5F9))
          Spacer(modifier = Modifier.height(6.dp))

          // Saved Notes List
          LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            if (relevantNotes.isEmpty()) {
              item {
                Text(
                  text = "තවමත් මෙම PDF එක සඳහා සටහන් එකතු කර නැත.",
                  fontSize = 11.sp,
                  color = NeutralMedium,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                )
              }
            }
            items(relevantNotes) { note ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(note.colorHex),
                border = BorderStroke(1.dp, Color(note.colorHex).copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(8.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(note.tag, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black.copy(alpha = 0.7f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(note.timestamp, fontSize = 9.sp, color = Color.Black.copy(alpha = 0.5f))
                      Spacer(modifier = Modifier.width(4.dp))
                      Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier
                          .size(14.dp)
                          .clickable { PdfAnnotationManager.deleteNote(note.id) }
                      )
                    }
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(note.noteText, fontSize = 11.sp, color = Color.Black, lineHeight = 15.sp)
                }
              }
            }
          }
        }
      }
    }
  }
}
