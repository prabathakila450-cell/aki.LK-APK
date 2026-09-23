package com.example

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*

// ==============================================================================
// 🌟 FEATURE 2: INTERACTIVE FORMULA & MATH/PHYSICS STEP-BY-STEP SOLVER
// ==============================================================================

enum class SolverCategory(val titleSinhala: String, val icon: String, val color: Color) {
  QUADRATIC("වර්ගජ සමීකරණ", "📐", Color(0xFFEA580C)),
  TRIGONOMETRY("ත්‍රිකෝණමිතිය & පයිතගරස්", "📏", Color(0xFF0284C7)),
  MOTION("චලිත සමීකරණ (Newton)", "🚀", Color(0xFF16A34A)),
  ENERGY("කාර්යය & ශක්තිය", "⚡", Color(0xFFD97706)),
  VOLUME("පරිමාව & වර්ගඵලය", "🧊", Color(0xFF7C3AED))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaSolverScreen(
  onBack: () -> Unit
) {
  var selectedCategory by remember { mutableStateOf(SolverCategory.QUADRATIC) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "🧮 සූත්‍ර විසඳුම් මෙවලම (Step-by-Step Solver)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "ගණිතය & භෞතික විද්‍යාව ගැටලු පියවරෙන් පියවර විසඳුම්",
              fontSize = 10.sp,
              color = Color(0xFFBAE6FD)
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("formula_solver_back_btn")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
      )
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0B132B))
        .padding(padding)
    ) {
      // Category Navigation Tabs
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF1E293B))
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(SolverCategory.values()) { category ->
          val isSelected = selectedCategory == category
          Surface(
            onClick = { selectedCategory = category },
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) category.color else Color.White.copy(alpha = 0.08f),
            border = BorderStroke(1.dp, if (isSelected) category.color else Color.White.copy(alpha = 0.2f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(category.icon, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = category.titleSinhala,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color(0xFFCBD5E1)
              )
            }
          }
        }
      }

      // Solver Body
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(14.dp)
      ) {
        when (selectedCategory) {
          SolverCategory.QUADRATIC -> QuadraticEquationSolverView()
          SolverCategory.TRIGONOMETRY -> TrigonometryAndPythagorasSolverView()
          SolverCategory.MOTION -> MotionEquationsSolverView()
          SolverCategory.ENERGY -> WorkAndEnergySolverView()
          SolverCategory.VOLUME -> AreaAndVolumeSolverView()
        }
      }
    }
  }
}

@Composable
fun QuadraticEquationSolverView() {
  var aText by remember { mutableStateOf("1") }
  var bText by remember { mutableStateOf("-5") }
  var cText by remember { mutableStateOf("6") }

  val a = aText.toDoubleOrNull() ?: 1.0
  val b = bText.toDoubleOrNull() ?: 0.0
  val c = cText.toDoubleOrNull() ?: 0.0

  val discriminant = (b * b) - (4 * a * c)

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color(0xFFEA580C).copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "📐 වර්ගජ සමීකරණ ආකෘතිය: ax² + bx + c = 0",
            color = Color(0xFFFDBA74),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "a, b සහ c අගයන් ඇතුළත් කරන්න. සූත්‍ර ක්‍රමය මඟින් පියවරෙන් පියවර විසඳුම එවෙලෙම ගණනය වේ.",
            color = Color(0xFFCBD5E1),
            fontSize = 11.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedTextField(
              value = aText,
              onValueChange = { aText = it },
              label = { Text("a අගය", fontSize = 10.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              singleLine = true,
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
              )
            )
            OutlinedTextField(
              value = bText,
              onValueChange = { bText = it },
              label = { Text("b අගය", fontSize = 10.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              singleLine = true,
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
              )
            )
            OutlinedTextField(
              value = cText,
              onValueChange = { cText = it },
              label = { Text("c අගය", fontSize = 10.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              singleLine = true,
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
              )
            )
          }
        }
      }
    }

    // Step-by-Step Solution Card
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "📝 පියවරෙන් පියවර විසඳීමේ ක්‍රමය (Step-by-Step):",
            color = Color(0xFF38BDF8),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "පියවර 1: වර්ගජ සූත්‍රය යෙදීම\n   x = [-b ± √(b² - 4ac)] / 2a",
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
          )
          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "පියවර 2: විවේචකය (Discriminant Δ) සෙවීම\n   Δ = b² - 4ac\n   Δ = ($b)² - 4($a)($c) = $discriminant",
            color = Color(0xFFBAE6FD),
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace
          )
          Spacer(modifier = Modifier.height(6.dp))

          val natureOfRoots = when {
            discriminant > 0 -> "විවේචකය Δ > 0 බැවින් මූල දෙකක් ඇත (සැබෑ හා වෙනස් මූල)."
            discriminant == 0.0 -> "විවේචකය Δ = 0 බැවින් සමපාත මූල දෙකක් (තනි මූලයක්) ඇත."
            else -> "විවේචකය Δ < 0 බැවින් තාත්වික මූල නොපවතී (සංකීර්ණ මූල)."
          }
          Text(
            text = "පියවර 3: මූලයන්හි ස්වභාවය\n   $natureOfRoots",
            color = if (discriminant >= 0) Color(0xFF86EFAC) else Color(0xFFFCA5A5),
            fontSize = 11.5.sp
          )
          Spacer(modifier = Modifier.height(8.dp))

          if (discriminant >= 0) {
            val root1 = (-b + sqrt(discriminant)) / (2 * a)
            val root2 = (-b - sqrt(discriminant)) / (2 * a)
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFF10B981).copy(alpha = 0.2f),
              border = BorderStroke(1.dp, Color(0xFF10B981))
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(
                  text = "🎯 අවසන් මූලයන් (Roots):",
                  color = Color(0xFF86EFAC),
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "x₁ = ${String.format("%.3f", root1)}\nx₂ = ${String.format("%.3f", root2)}",
                  color = Color.White,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 14.sp,
                  fontFamily = FontFamily.Monospace
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun TrigonometryAndPythagorasSolverView() {
  var mode by remember { mutableStateOf(0) } // 0: Pythagoras, 1: Trig Ratios
  var aSide by remember { mutableStateOf("3") }
  var bSide by remember { mutableStateOf("4") }
  var angleDeg by remember { mutableStateOf("30") }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = { mode = 0 },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (mode == 0) Color(0xFF0284C7) else Color.White.copy(alpha = 0.1f)
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text("පයිතගරස් ප්‍රමේයය (a² + b² = c²)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Button(
          onClick = { mode = 1 },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (mode == 1) Color(0xFF0284C7) else Color.White.copy(alpha = 0.1f)
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text("ත්‍රිකෝණමිතික අනුපාත (Sin, Cos, Tan)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    if (mode == 0) {
      item {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
          border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text("📐 ඍජුකෝණී ත්‍රිකෝණයක කර්ණය (Hypotenuse c) සෙවීම:", color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              OutlinedTextField(
                value = aSide,
                onValueChange = { aSide = it },
                label = { Text("පාදය a", fontSize = 10.sp) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
              )
              OutlinedTextField(
                value = bSide,
                onValueChange = { bSide = it },
                label = { Text("පාදය b", fontSize = 10.sp) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val a = aSide.toDoubleOrNull() ?: 3.0
            val b = bSide.toDoubleOrNull() ?: 4.0
            val c = sqrt(a * a + b * b)

            Text("පියවර 1: c² = a² + b²", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
            Text("පියවර 2: c² = ($a)² + ($b)² = ${(a*a)} + ${(b*b)} = ${a*a + b*b}", color = Color(0xFFBAE6FD), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
            Text("පියවර 3: c = √${a*a + b*b} = ${String.format("%.3f", c)}", color = Color(0xFF86EFAC), fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
          }
        }
      }
    } else {
      item {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
          border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text("📏 කෝණය (θ) අංශක වලින් ඇතුළත් කරන්න:", color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
              value = angleDeg,
              onValueChange = { angleDeg = it },
              label = { Text("කෝණය θ (°)", fontSize = 10.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.fillMaxWidth(),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            Spacer(modifier = Modifier.height(10.dp))
            val deg = angleDeg.toDoubleOrNull() ?: 30.0
            val rad = Math.toRadians(deg)
            val sinVal = sin(rad)
            val cosVal = cos(rad)
            val tanVal = if (abs(cosVal) < 1e-6) Double.NaN else tan(rad)

            Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFF0F172A)) {
              Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Sin ($deg°) = ${String.format("%.4f", sinVal)}", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                Text("• Cos ($deg°) = ${String.format("%.4f", cosVal)}", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                Text("• Tan ($deg°) = ${if (tanVal.isNaN()) "අනන්තය (Undefined)" else String.format("%.4f", tanVal)}", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("විභාග ඉඟිය: Sin θ = සම්මුඛ පාදය / කර්ණය | Cos θ = බද්ධ පාදය / කර්ණය | Tan θ = සම්මුඛ / බද්ධ", color = Color(0xFFFDBA74), fontSize = 10.sp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun MotionEquationsSolverView() {
  var uText by remember { mutableStateOf("0") } // Initial velocity
  var aText by remember { mutableStateOf("9.8") } // Acceleration
  var tText by remember { mutableStateOf("5") } // Time

  val u = uText.toDoubleOrNull() ?: 0.0
  val a = aText.toDoubleOrNull() ?: 9.8
  val t = tText.toDoubleOrNull() ?: 5.0

  val v = u + (a * t)
  val s = (u * t) + (0.5 * a * t * t)

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color(0xFF16A34A).copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("🚀 ඒකාකාර ත්වරණයෙන් චලිතය වන වස්තුවක් සඳහා:", color = Color(0xFF86EFAC), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
              value = uText,
              onValueChange = { uText = it },
              label = { Text("ආරම්භක ප්‍රවේගය u (ms⁻¹)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            OutlinedTextField(
              value = aText,
              onValueChange = { aText = it },
              label = { Text("ත්වරණය a (ms⁻²)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            OutlinedTextField(
              value = tText,
              onValueChange = { tText = it },
              label = { Text("කාලය t (s)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
          }
        }
      }
    }

    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("📊 ගණනය කළ අවසන් ප්‍රතිඵල:", color = Color(0xFF86EFAC), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))

          Text("1. අවසාන ප්‍රවේගය (Final Velocity v):\n   v = u + at = $u + ($a × $t) = ${String.format("%.2f", v)} ms⁻¹", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Spacer(modifier = Modifier.height(6.dp))
          Text("2. විස්ථාපනය (Displacement s):\n   s = ut + ½at² = ($u × $t) + ½($a)($t)² = ${String.format("%.2f", s)} m", color = Color(0xFFBAE6FD), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Spacer(modifier = Modifier.height(6.dp))
          Text("3. v² = u² + 2as පරික්ෂාව:\n   v² = ${String.format("%.2f", v*v)} | u² + 2as = ${String.format("%.2f", u*u + 2*a*s)}", color = Color(0xFFFDE68A), fontFamily = FontFamily.Monospace, fontSize = 11.5.sp)
        }
      }
    }
  }
}

@Composable
fun WorkAndEnergySolverView() {
  var massText by remember { mutableStateOf("10") } // kg
  var velocityText by remember { mutableStateOf("4") } // ms-1
  var heightText by remember { mutableStateOf("5") } // m

  val m = massText.toDoubleOrNull() ?: 10.0
  val v = velocityText.toDoubleOrNull() ?: 4.0
  val h = heightText.toDoubleOrNull() ?: 5.0
  val g = 9.8

  val ek = 0.5 * m * v * v
  val ep = m * g * h

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("⚡ කාර්යය, චාලක ශක්තිය (Ek) සහ විභව ශක්තිය (Ep):", color = Color(0xFFFDE68A), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
              value = massText,
              onValueChange = { massText = it },
              label = { Text("ස්කන්ධය m (kg)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            OutlinedTextField(
              value = velocityText,
              onValueChange = { velocityText = it },
              label = { Text("ප්‍රවේගය v (ms⁻¹)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            OutlinedTextField(
              value = heightText,
              onValueChange = { heightText = it },
              label = { Text("උස h (m)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
          }
        }
      }
    }

    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("💡 පියවර සහ ශක්ති ගණනය:", color = Color(0xFFFDE68A), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))
          Text("• චාලක ශක්තිය (Ek) = ½mv²\n  Ek = ½ × $m × ($v)² = ${String.format("%.2f", ek)} J (ජූල්)", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Spacer(modifier = Modifier.height(6.dp))
          Text("• ගුරුත්වාකර්ෂණ විභව ශක්තිය (Ep) = mgh\n  Ep = $m × 9.8 × $h = ${String.format("%.2f", ep)} J (ජූල්)", color = Color(0xFFBAE6FD), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Spacer(modifier = Modifier.height(6.dp))
          Text("• මුළු යාන්ත්‍රික ශක්තිය E = Ek + Ep = ${String.format("%.2f", ek + ep)} J", color = Color(0xFF86EFAC), fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
        }
      }
    }
  }
}

@Composable
fun AreaAndVolumeSolverView() {
  var radiusText by remember { mutableStateOf("7") }
  var heightText by remember { mutableStateOf("10") }

  val r = radiusText.toDoubleOrNull() ?: 7.0
  val h = heightText.toDoubleOrNull() ?: 10.0
  val pi = 22.0 / 7.0

  val cylVol = pi * r * r * h
  val coneVol = (1.0 / 3.0) * pi * r * r * h
  val sphereVol = (4.0 / 3.0) * pi * r * r * r

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color(0xFF7C3AED).copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("🧊 සිලින්ඩර, කේතු සහ ගෝල පරිමා ගණකය:", color = Color(0xFFC4B5FD), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
              value = radiusText,
              onValueChange = { radiusText = it },
              label = { Text("අරය r (cm)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            OutlinedTextField(
              value = heightText,
              onValueChange = { heightText = it },
              label = { Text("උස h (cm)", fontSize = 9.sp) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier.weight(1f),
              colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
          }
        }
      }
    }

    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = BorderStroke(1.dp, Color(0xFFA78BFA).copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text("🎯 පරිමාවන් (Volume Calculation):", color = Color(0xFFC4B5FD), fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Text("1. සිලින්ඩර පරිමාව (V = πr²h):\n   V = 22/7 × ($r)² × $h = ${String.format("%.2f", cylVol)} cm³", color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Text("2. කේතු පරිමාව (V = ⅓πr²h):\n   V = ⅓ × 22/7 × ($r)² × $h = ${String.format("%.2f", coneVol)} cm³", color = Color(0xFFBAE6FD), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
          Text("3. ගෝල පරිමාව (V = ⁴⁄₃πr³):\n   V = ⁴⁄₃ × 22/7 × ($r)³ = ${String.format("%.2f", sphereVol)} cm³", color = Color(0xFF86EFAC), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        }
      }
    }
  }
}

// ==============================================================================
// 🌟 FEATURE 5: TRI-LINGUAL GLOSSARY (SCIENCE, MATHS, ICT, COMMERCE)
// ==============================================================================

data class GlossaryTermItem(
  val id: String,
  val englishTerm: String,
  val sinhalaTerm: String,
  val tamilTerm: String,
  val subject: String,
  val definitionSinhala: String,
  val examTip: String
)

val sampleGlossaryTerms: List<GlossaryTermItem>
  get() = TrilingualGlossaryRepository.allGlossaryTerms

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriLingualGlossaryScreen(
  onBack: () -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }
  var selectedSubjectFilter by remember { mutableStateOf("විද්‍යාව") }
  var selectedGroupFilter by remember { mutableIntStateOf(1) } // 0 = All, 1..10 = Groups of 10

  val subjectFilters = listOf("සියල්ල", "විද්‍යාව", "ගණිතය", "තොරතුරු තාක්ෂණය (ICT)", "වාණිජ (Commerce)")
  val groupLabels = listOf(
    0 to "සියලු කාණ්ඩ (1-100)",
    1 to "කාණ්ඩය 1 (1-10)",
    2 to "කාණ්ඩය 2 (11-20)",
    3 to "කාණ්ඩය 3 (21-30)",
    4 to "කාණ්ඩය 4 (31-40)",
    5 to "කාණ්ඩය 5 (41-50)",
    6 to "කාණ්ඩය 6 (51-60)",
    7 to "කාණ්ඩය 7 (61-70)",
    8 to "කාණ්ඩය 8 (71-80)",
    9 to "කාණ්ඩය 9 (81-90)",
    10 to "කාණ්ඩය 10 (91-100)"
  )

  val filteredTerms = remember(searchQuery, selectedSubjectFilter, selectedGroupFilter) {
    TrilingualGlossaryRepository.allGlossaryTerms.filter { item ->
      val matchesSearch = if (searchQuery.isBlank()) true else {
        item.englishTerm.contains(searchQuery, ignoreCase = true) ||
            item.sinhalaTerm.contains(searchQuery, ignoreCase = true) ||
            item.tamilTerm.contains(searchQuery, ignoreCase = true) ||
            item.definitionSinhala.contains(searchQuery, ignoreCase = true)
      }

      val matchesSubject = when (selectedSubjectFilter) {
        "සියල්ල" -> true
        "තොරතුරු තාක්ෂණය (ICT)" -> item.subject.contains("ICT") || item.subject.contains("තොරතුරු")
        "වාණිජ (Commerce)" -> item.subject.contains("වාණිජ") || item.subject.contains("Commerce")
        else -> item.subject.contains(selectedSubjectFilter)
      }

      val itemGroup = TrilingualGlossaryRepository.getGroupNumber(item)
      val matchesGroup = if (selectedGroupFilter == 0) true else itemGroup == selectedGroupFilter

      matchesSearch && matchesSubject && matchesGroup
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "📖 ත්‍රිභාෂා පාරිභාෂික ශබ්ද මාලාව (Glossary)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "O/L විද්‍යාව • ගණිතය • ICT • වාණිජ සිංහල-English Terms",
              fontSize = 10.sp,
              color = Color(0xFFBAE6FD)
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("glossary_back_btn")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
      )
    }
  ) { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0B132B))
        .padding(padding)
        .padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Search Bar
      item {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("වචනය සොයන්න (උදා: Osmosis, ආස්‍රැතිය, RAM)...", fontSize = 11.5.sp, color = Color(0xFF94A3B8)) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF38BDF8)) },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { searchQuery = "" }) {
                Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.White)
              }
            }
          },
          shape = RoundedCornerShape(12.dp),
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF1E293B),
            unfocusedContainerColor = Color(0xFF1E293B)
          )
        )
      }

      // Filter Chips
      item {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text("විෂය තෝරන්න:", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold)
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(subjectFilters) { filter ->
              val isSelected = selectedSubjectFilter == filter
              FilterChip(
                selected = isSelected,
                onClick = { selectedSubjectFilter = filter },
                label = { Text(filter, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFF0284C7),
                  selectedLabelColor = Color.White,
                  containerColor = Color(0xFF1E293B),
                  labelColor = Color(0xFFCBD5E1)
                )
              )
            }
          }

          Text("දහයේ කාණ්ඩ 10 (වචන 100):", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.SemiBold)
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(groupLabels) { (grpId, label) ->
              val isSelected = selectedGroupFilter == grpId
              FilterChip(
                selected = isSelected,
                onClick = { selectedGroupFilter = grpId },
                label = { Text(label, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFF059669),
                  selectedLabelColor = Color.White,
                  containerColor = Color(0xFF1E293B),
                  labelColor = Color(0xFFCBD5E1)
                )
              )
            }
          }
        }
      }

      // Terms List
      items(filteredTerms) { item ->
        val groupNum = TrilingualGlossaryRepository.getGroupNumber(item)
        val itemNum = TrilingualGlossaryRepository.getItemIndexInSubject(item)

        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
          border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.3f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.sinhalaTerm,
                  color = Color.White,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.ExtraBold
                )
                Text(
                  text = "${item.englishTerm} • ${item.tamilTerm}",
                  color = Color(0xFF38BDF8),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
              Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Surface(
                  color = Color(0xFF059669),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "කාණ්ඩය $groupNum (#$itemNum)",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
                Surface(
                  color = Color(0xFF0284C7),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = item.subject,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = item.definitionSinhala,
              color = Color(0xFFE2E8F0),
              fontSize = 11.5.sp,
              lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFF0F172A),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text("💡", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = item.examTip,
                  color = Color(0xFFFDBA74),
                  fontSize = 10.5.sp,
                  lineHeight = 14.sp,
                  modifier = Modifier.weight(1f)
                )
                IconButton(
                  onClick = {
                    clipboardManager.setText(AnnotatedString("${item.sinhalaTerm} (${item.englishTerm}): ${item.definitionSinhala}"))
                  },
                  modifier = Modifier.size(24.dp)
                ) {
                  Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
                }
              }
            }
          }
        }
      }
    }
  }
}

// ==============================================================================
// 🌟 FEATURE 6: TIMED PAST PAPER SIMULATOR (YEAR-BY-YEAR MCQ EXAM)
// ==============================================================================

data class PastPaperQuestion(
  val id: Int,
  val questionText: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanationSinhala: String,
  val topicCategory: String = "",
  val year: String = "",
  val subject: String = ""
)

val samplePastPaperQuestions = listOf(
  PastPaperQuestion(
    id = 1,
    questionText = "ශාක සෛලයක පමණක් දක්නට ලැබෙන අතර සත්ත්ව සෛලවල නොමැති ඉන්ද්‍රයිකාව කුමක්ද?",
    options = listOf("මයිටොකොන්ඩ්‍රියා", "හරිතලව (Chloroplast)", "රයිබොසෝම", "න්‍යෂ්ටිය"),
    correctOptionIndex = 1,
    explanationSinhala = "හරිතලව සහ සෙලියුලෝස් සෛල බිත්තිය ශාක සෛලවලට පමණක් සීමා වූ ව්‍යුහයන් වේ.",
    topicCategory = "ජීව විද්‍යාව"
  ),
  PastPaperQuestion(
    id = 2,
    questionText = "ජලය විද්‍යුත් විච්ඡේදනය කිරීමේදී කැතෝඩය අසල පිටවන වායුව කුමක්ද?",
    options = listOf("ඔක්සිජන් වායුව", "හයිඩ්‍රජන් වායුව", "කාබන් ඩයොක්සයිඩ් වායුව", "නයිට්‍රජන් වායුව"),
    correctOptionIndex = 1,
    explanationSinhala = "කැතෝඩය (-) අසල හයිඩ්‍රජන් වායුවත් (H₂), ඇනෝඩය (+) අසල ඔක්සිජන් වායුවත් (O₂) පිටවේ. පරිමා අනුපාතය 2:1 කි.",
    topicCategory = "රසායන විද්‍යාව"
  ),
  PastPaperQuestion(
    id = 3,
    questionText = "නිව්ටන්ගේ දෙවන චලිත නියමය ගණිතමය ලෙස දක්වන්නේ කෙසේද?",
    options = listOf("F = ma", "v = u + at", "W = Fd", "P = W / t"),
    correctOptionIndex = 0,
    explanationSinhala = "F = ma (අසමතුලිත බලය = ස්කන්ධය × ත්වරණය).",
    topicCategory = "භෞතික විද්‍යාව"
  ),
  PastPaperQuestion(
    id = 4,
    questionText = "ලංකාවේ ප්‍රථම ඓතිහාසික රජු ලෙස මහාවංශයේ සැලකෙන්නේ කවුරුන්ද?",
    options = listOf("දේවානම්පියතිස්ස රජු", "විජය රජු", "පණ්ඩුකාභය රජු", "දුටුගැමුණු රජු"),
    correctOptionIndex = 1,
    explanationSinhala = "ක්‍රි.පූ. 543 දී ඉන්දියාවේ සිට පැමිණි විජය කුමරු ලංකාවේ ප්‍රථම ඓතිහාසික පාලකයා ලෙස සැලකේ.",
    topicCategory = "ඉතිහාසය"
  ),
  PastPaperQuestion(
    id = 5,
    questionText = "බුදුරජාණන් වහන්සේ ප්‍රථම ධර්ම දේශනාව වන ධම්මචක්කප්පවත්තන සූත්‍රය දේශනා කළේ කාහටද?",
    options = listOf("සැරියුත් මුගලන් දෙනමට", "පස්වග තවුසන්ට", "කාශ්‍යප සොහොයුරන්ට", "ආනන්ද හිමියන්ට"),
    correctOptionIndex = 1,
    explanationSinhala = "බරණැස ඉසිපතන මිගදායේදී කොණ්ඩඤ්ඤ, භද්දිය, වප්ප, මහානාම, අස්සජි යන පස්වග තවුසන්ට ප්‍රථම ධර්ම දේශනාව පවත්වන ලදී.",
    topicCategory = "බුද්ධ ධර්මය"
  )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimedPastPaperSimulatorScreen(
  onBack: () -> Unit
) {
  var selectedYear by remember { mutableStateOf("2023") }
  var selectedSubject by remember { mutableStateOf("විද්‍යාව (Science)") }
  var examDurationMinutes by remember { mutableIntStateOf(60) } // Default 60 Minutes as requested
  var isExamRunning by remember { mutableStateOf(false) }
  var isExamFinished by remember { mutableStateOf(false) }

  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  val studentAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> optionIndex
  var remainingSeconds by remember { mutableIntStateOf(60 * 60) } // 60 minutes = 3600 seconds

  // Fetch exactly 60 questions for the selected subject and year, strictly interleaved across diverse topics
  val examQuestions = remember(selectedSubject, selectedYear) {
    TimedPastPaperRepository.getQuestionsForExam(selectedSubject, selectedYear)
  }

  // Countdown Timer Effect
  LaunchedEffect(isExamRunning, remainingSeconds) {
    if (isExamRunning && remainingSeconds > 0) {
      delay(1000L)
      remainingSeconds--
    } else if (isExamRunning && remainingSeconds == 0) {
      isExamRunning = false
      isExamFinished = true
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "⏱️ Timed Past Paper Simulator",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = if (isExamRunning) {
                "විභාග කාලය: ${remainingSeconds / 60}m ${String.format("%02d", remainingSeconds % 60)}s • ප්‍රශ්න 60"
              } else {
                "$selectedSubject - $selectedYear O/L (ප්‍රශ්න 60 • විනාඩි 60)"
              },
              fontSize = 10.5.sp,
              color = if (isExamRunning && remainingSeconds < 300) Color(0xFFF87171) else Color(0xFFBAE6FD)
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
        },
        actions = {
          if (isExamRunning) {
            Button(
              onClick = {
                isExamRunning = false
                isExamFinished = true
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("අවසන් කරන්න", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
      )
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0B132B))
        .padding(padding)
        .padding(14.dp)
    ) {
      if (!isExamRunning && !isExamFinished) {
        // Pre-Exam Setup View
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
              border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "🎯 විභාග ප්‍රශ්න පත්‍රය සහ වසර තෝරන්න:",
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("විෂය (Subject):", color = Color(0xFFBAE6FD), fontSize = 11.5.sp)
                val subjects = listOf(
                  "විද්‍යාව (Science)", 
                  "ඉතිහාසය (History)", 
                  "බුද්ධ ධර්මය (Buddhism)", 
                  "සිංහල (Sinhala)",
                  "ගණිතය (Mathematics)",
                  "ඉංග්‍රීසි (English)"
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                  items(subjects) { subj ->
                    FilterChip(
                      selected = selectedSubject == subj,
                      onClick = { selectedSubject = subj },
                      label = { Text(subj, fontSize = 11.sp) }
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("වසර (Year):", color = Color(0xFFBAE6FD), fontSize = 11.5.sp)
                val years = listOf("2024", "2023", "2022", "2021", "2020", "2019", "2018")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                  items(years) { yr ->
                    FilterChip(
                      selected = selectedYear == yr,
                      onClick = { selectedYear = yr },
                      label = { Text(yr, fontSize = 11.sp) }
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("කාල සීමාව (Exam Duration):", color = Color(0xFFBAE6FD), fontSize = 11.5.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  FilterChip(
                    selected = examDurationMinutes == 60,
                    onClick = { examDurationMinutes = 60 },
                    label = { Text("🏛️ 60 Min Standard (ප්‍රශ්න 60)", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                  )
                  FilterChip(
                    selected = examDurationMinutes == 45,
                    onClick = { examDurationMinutes = 45 },
                    label = { Text("⚡ 45 Min Speed Run", fontSize = 11.sp) }
                  )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = Color(0xFF0284C7).copy(alpha = 0.15f),
                  border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.3f)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text("💡", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = "ප්‍රශ්න පත්‍රයේ ප්‍රශ්න 60 ක් අන්තර්ගත වන අතර, එකම මාතෘකාවේ ප්‍රශ්න එක ළඟ නොයන පරිදි විෂය නිර්දේශයේ සියලු ඒකක මනාව බෙදා මිශ්‍ර කර (Interleaved) ඇත.",
                      color = Color(0xFFE2E8F0),
                      fontSize = 11.sp,
                      lineHeight = 16.sp
                    )
                  }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                  onClick = {
                    remainingSeconds = examDurationMinutes * 60
                    studentAnswers.clear()
                    currentQuestionIndex = 0
                    isExamRunning = true
                    isExamFinished = false
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text("විභාගය ආරම්භ කරන්න (විනාඩි 60 • ප්‍රශ්න 60) 🚀", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      } else if (isExamRunning) {
        // Active Question View (Ensuring safe index within 60 questions)
        val questionsList = examQuestions.ifEmpty { samplePastPaperQuestions }
        val currentQ = questionsList[currentQuestionIndex.coerceIn(0, questionsList.size - 1)]

        Column(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Question Progress & Timer Bar
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "ප්‍රශ්න අංක ${currentQuestionIndex + 1} / ${questionsList.size}",
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                  )
                  if (currentQ.topicCategory.isNotEmpty()) {
                    Text(
                      text = "🏷️ ${currentQ.topicCategory}",
                      color = Color(0xFFFEF08A),
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = if (remainingSeconds < 300) Color(0xFFEF4444).copy(alpha = 0.2f) else Color(0xFF10B981).copy(alpha = 0.2f),
                  border = BorderStroke(1.dp, if (remainingSeconds < 300) Color(0xFFEF4444) else Color(0xFF10B981))
                ) {
                  Text(
                    text = "⏳ ඉතිරි කාලය: ${remainingSeconds / 60}:${String.format("%02d", remainingSeconds % 60)}",
                    color = if (remainingSeconds < 300) Color(0xFFFCA5A5) else Color(0xFF86EFAC),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.5.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Linear Progress Bar across 60 questions
              LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / questionsList.size },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(6.dp),
                color = Color(0xFF38BDF8),
                trackColor = Color(0xFF334155)
              )
            }
          }

          // Quick Question Jump Bar (1..60)
          Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
          ) {
            LazyRow(
              modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              items(questionsList.size) { idx ->
                val qId = questionsList[idx].id
                val isAnswered = studentAnswers.containsKey(qId)
                val isCurrent = idx == currentQuestionIndex
                Surface(
                  onClick = { currentQuestionIndex = idx },
                  shape = RoundedCornerShape(6.dp),
                  color = when {
                    isCurrent -> Color(0xFF0284C7)
                    isAnswered -> Color(0xFF059669)
                    else -> Color(0xFF1E293B)
                  },
                  border = BorderStroke(
                    1.dp, 
                    if (isCurrent) Color(0xFF38BDF8) else if (isAnswered) Color(0xFF34D399) else Color(0xFF475569)
                  ),
                  modifier = Modifier.size(30.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = "${idx + 1}",
                      fontSize = 10.sp,
                      fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Medium,
                      color = Color.White
                    )
                  }
                }
              }
            }
          }

          // Active Question Surface
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f, fill = false)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = currentQ.questionText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp
              )

              Spacer(modifier = Modifier.height(12.dp))

              currentQ.options.forEachIndexed { optIndex, optionText ->
                val isSelected = studentAnswers[currentQ.id] == optIndex
                Surface(
                  onClick = {
                    studentAnswers[currentQ.id] = optIndex
                  },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSelected) Color(0xFF0284C7).copy(alpha = 0.35f) else Color.White.copy(alpha = 0.05f),
                  border = BorderStroke(1.dp, if (isSelected) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.15f)),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.5.dp)
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = "(${optIndex + 1})",
                      color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = optionText,
                      color = Color.White,
                      fontSize = 12.5.sp
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(14.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                OutlinedButton(
                  onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                  enabled = currentQuestionIndex > 0,
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                  Text("පෙර ප්‍රශ්නය", fontSize = 11.sp)
                }
                if (currentQuestionIndex < questionsList.size - 1) {
                  Button(
                    onClick = { currentQuestionIndex++ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                  ) {
                    Text("ඊළඟ ප්‍රශ්නය", fontSize = 11.sp)
                  }
                } else {
                  Button(
                    onClick = {
                      isExamRunning = false
                      isExamFinished = true
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                  ) {
                    Text("පිළිතුරු භාරදෙන්න ✅", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }
      } else {
        // Result Sheet & Detailed Explanations
        val questionsList = examQuestions.ifEmpty { samplePastPaperQuestions }
        var score = 0
        questionsList.forEach { q ->
          if (studentAnswers[q.id] == q.correctOptionIndex) score++
        }
        val percent = if (questionsList.isNotEmpty()) (score * 100) / questionsList.size else 0
        val grade = when {
          percent >= 75 -> "A (විශිෂ්ට සාමාර්ථ්‍යයක්)"
          percent >= 65 -> "B (ඉතා හොඳ සාමාර්ථ්‍යයක්)"
          percent >= 55 -> "C (සම්මාන සාමාර්ථ්‍යයක්)"
          percent >= 35 -> "S (සාමාන්‍ය සාමාර්ථ්‍යයක්)"
          else -> "W (නැවත පුහුණු වන්න)"
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
              border = BorderStroke(1.dp, Color(0xFF10B981))
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text("🏆 විභාග ලකුණු වාර්තාව (Exam Results)", color = Color(0xFF86EFAC), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("$score / ${questionsList.size}", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("ප්‍රතිශතය: $percent% • ශ්‍රේණිය: $grade", fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
                Text("විභාගය: $selectedSubject ($selectedYear) • විනාඩි 60", fontSize = 11.sp, color = Color(0xFF94A3B8))
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                  onClick = {
                    isExamFinished = false
                    isExamRunning = false
                  },
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                  Text("නැවත විභාගයක් කරන්න 🔄", fontSize = 11.5.sp)
                }
              }
            }
          }

          item {
            Text(
              text = "📝 ප්‍රශ්නෝත්තර සහ සිංහල විවරණ (ප්‍රශ්න 60):", 
              color = Color.White, 
              fontWeight = FontWeight.Bold, 
              fontSize = 13.sp
            )
          }

          items(questionsList) { q ->
            val userAns = studentAnswers[q.id]
            val isCorrect = userAns == q.correctOptionIndex
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
              border = BorderStroke(1.dp, if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444).copy(alpha = 0.6f))
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = "ප්‍රශ්නය ${q.id}:",
                    color = Color(0xFF38BDF8),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                  if (q.topicCategory.isNotEmpty()) {
                    Text(
                      text = q.topicCategory,
                      color = Color(0xFFFEF08A),
                      fontSize = 9.5.sp
                    )
                  }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                  text = q.questionText,
                  color = Color.White,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                val ansText = if (userAns != null && userAns < q.options.size) q.options[userAns] else "පිළිතුරක් සලකුණු කර නැත"
                val statusEmoji = if (isCorrect) "✅" else "❌"
                Text(
                  text = "ඔබේ පිළිතුර: $ansText $statusEmoji",
                  color = if (isCorrect) Color(0xFF86EFAC) else Color(0xFFFCA5A5),
                  fontSize = 11.sp
                )
                if (!isCorrect) {
                  Text(
                    text = "නිවැරදි පිළිතුර: (${q.correctOptionIndex + 1}) ${q.options[q.correctOptionIndex]}",
                    color = Color(0xFF86EFAC),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "විවරණය: ${q.explanationSinhala}",
                  color = Color(0xFFBAE6FD),
                  fontSize = 10.5.sp
                )
              }
            }
          }
        }
      }
    }
  }
}

// ==============================================================================
// 🌟 FEATURE 7: OFFLINE VAULT & CUSTOM LOCAL STUDY NOTES
// ==============================================================================

data class OfflineSummaryNote(
  val id: String,
  val title: String,
  val subject: String,
  val category: String,
  val summaryContent: String
)

val defaultOfflineSummaries = listOf(
  OfflineSummaryNote(
    id = "off_sci_1",
    title = "විද්‍යාව මූලික නියම සහ සූත්‍ර සංග්‍රහය",
    subject = "විද්‍යාව",
    category = "නීති & සූත්‍ර",
    summaryContent = """
      1. නිව්ටන්ගේ චලිත නියම:
         • 1 වන නියමය: බාහිර අසමතුලිත බලයක් නොයෙදෙන තාක් වස්තුවක් නිශ්චලතාවයේ හෝ ඒකාකාර ප්‍රවේගයෙන් පවතී (අවස්ථිතිය).
         • 2 වන නියමය: F = ma (බලය = ස්කන්ධය × ත්වරණය).
         • 3 වන නියමය: සෑම ක්‍රියාවකටම විශාලත්වයෙන් සමාන දිශාවෙන් ප්‍රතිවිරුද්ධ ප්‍රතික්‍රියාවක් ඇත.
      2. ඕම්ගේ නියමය (Ohm's Law):
         • V = IR (විභව අන්තරය = ධාරාව × ප්‍රතිරෝධය).
      3. ආස්‍රැතිය:
         • වරණීය පාරගම්‍ය පටලයක් හරහා වැඩි ජල විභවයේ සිට අඩු ජල විභවයට ජලය ගමන් කිරීම.
    """.trimIndent()
  ),
  OfflineSummaryNote(
    id = "off_math_1",
    title = "ගණිතය O/L අනිවාර්ය ප්‍රමේය සහ සූත්‍ර",
    subject = "ගණිතය",
    category = "ප්‍රමේය",
    summaryContent = """
      1. ජ්‍යාමිතිය මූලික ප්‍රමේය:
         • ත්‍රිකෝණයක අභ්‍යන්තර කෝණවල එකතුව 180° කි.
         • ඍජුකෝණී ත්‍රිකෝණයේ පයිතගරස්: a² + b² = c².
         • වෘත්තයක කේන්ද්‍රයේ සිට ජ්‍යායට අඳින ලම්බය මගින් ජ්‍යාය සමච්ඡේද වේ.
      2. වර්ගජ සූත්‍රය:
         • x = [-b ± √(b² - 4ac)] / 2a.
      3. පරිමාවන්:
         • සිලින්ඩරය = πr²h.
         • කේතුව = ⅓πr²h.
         • ගෝලය = ⁴⁄₃πr³.
    """.trimIndent()
  ),
  OfflineSummaryNote(
    id = "off_hist_1",
    title = "ඉතිහාසය O/L වැදගත් කාල රේඛාව සහ සංගායනා",
    subject = "ඉතිහාසය",
    category = "කාල රේඛා",
    summaryContent = """
      1. වැදගත් යුග:
         • ක්‍රි.පූ. 543: විජය කුමරු ලංකාවට පැමිණීම.
         • ක්‍රි.පූ. 307: දේවානම්පියතිස්ස රජ දවස මහින්දාගමනය සිදුවීම.
         • ක්‍රි.පූ. 161 - 137: දුටුගැමුණු රජු ලංකාව එක්සේසත් කිරීම (රුවන්වැලි සෑය ඉදිකිරීම).
         • ක්‍රි.පූ. 1 වන සියවස: වළගම්බා රජ දවස මාතලේ අලුවිහාරයේදී ත්‍රිපිටකය ග්‍රන්ථාරූඪ කිරීම.
         • ක්‍රි.ව. 1153 - 1186: මහා පරාක්‍රමබාහු රජු (පරාක්‍රම සමුද්‍රය, කෘෂිකාර්මික ස්වයංපෝෂිතය).
    """.trimIndent()
  ),
  OfflineSummaryNote(
    id = "off_sin_1",
    title = "සිංහල ව්‍යාකරණ අත්‍යවශ්‍ය නීති රීති",
    subject = "සිංහල",
    category = "ව්‍යාකරණ",
    summaryContent = """
      1. උක්ත ආඛ්‍යාත පද සම්බන්ධය:
         • ඒකවචන උක්තයට ඒකවචන ආඛ්‍යාතයක්ද, බහුවචන උක්තයට බහුවචන ආඛ්‍යාතයක්ද යෙදිය යුතුය.
         • උදා: ගොවියා කුඹුරට යයි (ඒක). ගොවියෝ කුඹුරට යති (බහු).
      2. න/ණ සහ ල/ළ භේදය:
         • ඥ, ඤ, ණ, න, ම මූර්ධජ හා දන්තජ අක්ෂර නිවැරදිව භාවිතය.
      3. කර්මකාරක වාක්‍ය:
         • කර්මය උක්ත වන විට ප්‍රත්‍යය 'ලැබේ', 'ලබති' ලෙස යෙදේ.
    """.trimIndent()
  )
)

@Composable
fun OfflineVaultView() {
  val context = LocalContext.current
  var selectedTab by remember { mutableStateOf(0) } // 0: Pre-cached, 1: Custom Notes
  var customNotes by remember { mutableStateOf(loadCustomNotes(context)) }
  var showAddNoteDialog by remember { mutableStateOf(false) }

  Column(modifier = Modifier.fillMaxSize()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = { selectedTab = 0 },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedTab == 0) Color(0xFF0284C7) else Color.White.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Text("💾 Data රහිත කෙටි සටහන්", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
      }
      Button(
        onClick = { selectedTab = 1 },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedTab == 1) Color(0xFF0284C7) else Color.White.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Text("✍️ මගේ පෞද්ගලික සටහන්", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
      }
    }

    if (selectedTab == 0) {
      LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
          Surface(
            color = Color(0xFF10B981).copy(alpha = 0.15f),
            border = BorderStroke(1.dp, Color(0xFF10B981)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
              Text("⚡", fontSize = 14.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "මෙම සටහන් ඔබගේ දුරකථනයේම සුරැකී ඇති බැවින් Internet හෝ Mobile Data නොමැතිව වුවද කියවිය හැක.",
                color = Color(0xFFA7F3D0),
                fontSize = 10.5.sp
              )
            }
          }
        }

        items(defaultOfflineSummaries) { note ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(note.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFF0284C7)) {
                  Text(note.subject, color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = note.summaryContent,
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp,
                lineHeight = 16.sp
              )
            }
          }
        }
      }
    } else {
      Column {
        Button(
          onClick = { showAddNoteDialog = true },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
        ) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("නව සටහනක් ලියා සුරකින්න (Offline)", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
        }

        if (customNotes.isEmpty()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("📝", fontSize = 36.sp)
              Spacer(modifier = Modifier.height(8.dp))
              Text("තවමත් පෞද්ගලික සටහන් එක් කර නොමැත.", color = Color(0xFF94A3B8), fontSize = 12.sp)
            }
          }
        } else {
          LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(customNotes) { note ->
              Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                border = BorderStroke(1.dp, Color(0xFF64748B))
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(note.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    IconButton(
                      onClick = {
                        val updated = customNotes.filter { it.id != note.id }
                        customNotes = updated
                        saveCustomNotes(context, updated)
                      },
                      modifier = Modifier.size(24.dp)
                    ) {
                      Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                    }
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(note.summaryContent, color = Color(0xFFCBD5E1), fontSize = 11.sp, lineHeight = 15.sp)
                }
              }
            }
          }
        }
      }
    }
  }

  if (showAddNoteDialog) {
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    AlertDialog(
      onDismissRequest = { showAddNoteDialog = false },
      title = { Text("නව පෞද්ගලික සටහන", fontSize = 14.sp, fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("මාතෘකාව (Title)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newContent,
            onValueChange = { newContent = it },
            label = { Text("සටහන (Note body)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newTitle.isNotBlank() && newContent.isNotBlank()) {
              val newNote = OfflineSummaryNote(
                id = "custom_${System.currentTimeMillis()}",
                title = newTitle.trim(),
                subject = "පෞද්ගලික",
                category = "Note",
                summaryContent = newContent.trim()
              )
              val updated = customNotes + newNote
              customNotes = updated
              saveCustomNotes(context, updated)
              showAddNoteDialog = false
            }
          }
        ) {
          Text("සුරකින්න")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddNoteDialog = false }) {
          Text("අවලංගුයි")
        }
      }
    )
  }
}

fun loadCustomNotes(context: Context): List<OfflineSummaryNote> {
  val prefs = context.getSharedPreferences("offline_study_notes", Context.MODE_PRIVATE)
  val count = prefs.getInt("note_count", 0)
  val list = mutableListOf<OfflineSummaryNote>()
  for (i in 0 until count) {
    val id = prefs.getString("note_id_$i", "") ?: ""
    val title = prefs.getString("note_title_$i", "") ?: ""
    val content = prefs.getString("note_content_$i", "") ?: ""
    if (id.isNotEmpty() && title.isNotEmpty()) {
      list.add(OfflineSummaryNote(id, title, "පෞද්ගලික", "Note", content))
    }
  }
  return list
}

fun saveCustomNotes(context: Context, notes: List<OfflineSummaryNote>) {
  val prefs = context.getSharedPreferences("offline_study_notes", Context.MODE_PRIVATE)
  val editor = prefs.edit()
  editor.putInt("note_count", notes.size)
  notes.forEachIndexed { i, n ->
    editor.putString("note_id_$i", n.id)
    editor.putString("note_title_$i", n.title)
    editor.putString("note_content_$i", n.summaryContent)
  }
  editor.apply()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineNotesVaultScreen(
  onBack: () -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "💾 Offline Notes Vault & මගේ සටහන්",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "Data රහිත සටහන් සහ පෞද්ගලික කෙටි සටහන් එකතුව",
              fontSize = 10.sp,
              color = Color(0xFFBAE6FD)
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
      )
    }
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0B132B))
        .padding(padding)
        .padding(14.dp)
    ) {
      OfflineVaultView()
    }
  }
}

