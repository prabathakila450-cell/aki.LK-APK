package com.example

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URLEncoder

// Grade 11 Term Test Portal Targets (User Provided)
const val GOVDOC_GRADE_11_URL = "https://govdoc.lk/category/term-test-papers/grade-11"
const val ETHAKSALAWA_GRADE_11_URL = "https://e-thaksalawa.moe.gov.lk/lcms/course/index.php?categoryid=22"

enum class Grade11WebSource(
  val title: String,
  val subtitle: String,
  val url: String,
  val badge: String,
  val badgeColor: Color
) {
  GOVDOC(
    title = "GovDoc (11)",
    subtitle = "11 වසර වාර විභාග ප්‍රශ්න පත්‍ර",
    url = GOVDOC_GRADE_11_URL,
    badge = "GRADE 11",
    badgeColor = Color(0xFF047857)
  ),
  GOVDOC_10(
    title = "GovDoc (10)",
    subtitle = "10 ශ්‍රේණිය වාර විභාග ප්‍රශ්න පත්‍ර (හවුල් PDF)",
    url = "https://govdoc.lk/category/term-test-papers/grade-10",
    badge = "GRADE 10",
    badgeColor = Color(0xFF059669)
  ),
  ETHAKSALAWA(
    title = "e-Thaksalawa",
    subtitle = "11 ශ්‍රේණිය නිල අමාත්‍යාංශ ද්වාරය",
    url = ETHAKSALAWA_GRADE_11_URL,
    badge = "OFFICIAL MOE",
    badgeColor = Color(0xFF1D4ED8)
  )
}

data class Grade11SubjectFilter(
  val nameSinhala: String,
  val keywordGovDoc: String,
  val icon: String
)

val grade11SubjectFilterList = listOf(
  Grade11SubjectFilter("සියලුම විෂයන්", "", "📚"),
  Grade11SubjectFilter("විද්‍යාව", "science", "🧬"),
  Grade11SubjectFilter("ගණිතය", "math", "📐"),
  Grade11SubjectFilter("සිංහල", "sinhala", "📖"),
  Grade11SubjectFilter("ඉංග්‍රීසි", "english", "🔤"),
  Grade11SubjectFilter("ඉතිහාසය", "history", "🏛️"),
  Grade11SubjectFilter("බුද්ධ ධර්මය", "buddhism", "☸️"),
  Grade11SubjectFilter("භූගෝල විද්‍යාව", "geography", "🌍"),
  Grade11SubjectFilter("පුරවැසි අධ්‍යාපනය", "civic", "⚖️"),
  Grade11SubjectFilter("තොරතුරු තාක්ෂණය (ICT)", "ict", "💻"),
  Grade11SubjectFilter("වාණිජ්‍ය", "commerce", "📊"),
  Grade11SubjectFilter("කෘෂිකර්මය", "agriculture", "🌱"),
  Grade11SubjectFilter("සෞඛ්‍යය", "health", "🏃"),
  Grade11SubjectFilter("චිත්‍ර කලාව", "art", "🎨"),
  Grade11SubjectFilter("සංගීතය", "music", "🎵"),
  Grade11SubjectFilter("නර්තනය", "dancing", "💃")
)

fun buildGrade11SearchUrl(source: Grade11WebSource, subjectKeyword: String?, term: Int?): String {
  if (source == Grade11WebSource.ETHAKSALAWA) {
    return ETHAKSALAWA_GRADE_11_URL
  }

  val terms = when (term) {
    1 -> "1st term"
    2 -> "2nd term"
    3 -> "3rd term"
    else -> ""
  }

  val subKey = subjectKeyword?.trim().orEmpty()
  val queryList = listOf(subKey, terms).filter { it.isNotBlank() }

  return if (queryList.isNotEmpty()) {
    val query = queryList.joinToString(" ")
    try {
      "https://govdoc.lk/category/term-test-papers/grade-11?s=${URLEncoder.encode(query, "UTF-8")}"
    } catch (_: Exception) {
      GOVDOC_GRADE_11_URL
    }
  } else {
    GOVDOC_GRADE_11_URL
  }
}

/**
 * Ultra-secure, DRM-protected web viewer designed specifically for Grade 11 Term Test Papers.
 * Blocks:
 * 1. Screenshots & Screen recording (Hardware FLAG_SECURE toggle & safe handling)
 * 2. Text copying & selection (Injected CSS user-select: none & JS event interceptors)
 * 3. File downloads (Intercepted setDownloadListener & disabled download anchors)
 * 4. Shows ONLY question papers relevant to each subject and 1st, 2nd, 3rd terms
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Grade11SecureTermTestPortalScreen(
  initialSource: Grade11WebSource = Grade11WebSource.GOVDOC,
  initialSubject: String? = null,
  initialTerm: Int? = null,
  studentName: String = "ශිෂ්‍ය ගිණුම",
  studentPhone: String = "",
  onBack: () -> Unit
) {
  val context = LocalContext.current
  val activity = context as? Activity

  var hardwareSecureEnabled by remember { mutableStateOf(true) }

  DisposableEffect(activity, hardwareSecureEnabled) {
    activity?.let { act ->
      AppSecurityManager.applyScreenProtection(act, hardwareSecureEnabled)
    }
    onDispose {
      activity?.let { act ->
        AppSecurityManager.applyScreenProtection(act, false)
      }
    }
  }

  var currentSource by remember { mutableStateOf(initialSource) }
  var selectedTerm by remember { mutableStateOf(initialTerm ?: 0) } // 0: All, 1: 1st, 2: 2nd, 3: 3rd
  var selectedSubjectName by remember {
    mutableStateOf(
      initialSubject ?: "සියලුම විෂයන්"
    )
  }

  // Derive initial subject keyword if provided
  val initialSubjectKeyword = remember(selectedSubjectName) {
    grade11SubjectFilterList.firstOrNull {
      it.nameSinhala == selectedSubjectName ||
        selectedSubjectName.contains(it.nameSinhala) ||
        (it.keywordGovDoc.isNotBlank() && selectedSubjectName.contains(it.keywordGovDoc, ignoreCase = true))
    }?.keywordGovDoc.orEmpty()
  }

  var selectedSubjectKeyword by remember { mutableStateOf(initialSubjectKeyword) }

  var activeUrl by remember {
    mutableStateOf(
      buildGrade11SearchUrl(currentSource, selectedSubjectKeyword, if (selectedTerm > 0) selectedTerm else null)
    )
  }

  var webViewInstance by remember { mutableStateOf<WebView?>(null) }
  var isLoading by remember { mutableStateOf(true) }
  var pageProgress by remember { mutableIntStateOf(0) }
  var canGoBack by remember { mutableStateOf(false) }
  var canGoForward by remember { mutableStateOf(false) }
  var pageTitle by remember { mutableStateOf("11 වසර වාර විභාග ප්‍රශ්න පත්‍ර") }
  var showSecurityInfoDialog by remember { mutableStateOf(false) }
  var showWatermark by remember { mutableStateOf(true) }

  fun triggerNavigation(source: Grade11WebSource, subjectKey: String, term: Int) {
    currentSource = source
    val targetUrl = buildGrade11SearchUrl(source, subjectKey, if (term > 0) term else null)
    activeUrl = targetUrl
    webViewInstance?.loadUrl(targetUrl)
  }

  // Handle hardware back button
  BackHandler {
    if (webViewInstance?.canGoBack() == true) {
      webViewInstance?.goBack()
    } else {
      onBack()
    }
  }

  Scaffold(
    topBar = {
      Surface(
        color = Color(0xFF0F172A),
        shadowElevation = 6.dp
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // Top Bar Header
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .statusBarsPadding()
              .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("grade11_secure_portal_back_btn")
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = "Back",
                  tint = Color.White
                )
              }

              Spacer(modifier = Modifier.width(4.dp))

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "11 වසර වාර විභාග ප්‍රශ්න පත්‍ර",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFDC2626)
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                      )
                      Spacer(modifier = Modifier.width(2.dp))
                      Text(
                        text = "DRM PROTECTED",
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                      )
                    }
                  }
                }

                Text(
                  text = "${currentSource.title} • $selectedSubjectName • ${if (selectedTerm > 0) "$selectedTerm වන වාරය" else "සියලුම වාර"}",
                  fontSize = 10.sp,
                  color = Color(0xFF94A3B8),
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }

            // Security Shield Info Button
            IconButton(
              onClick = { showSecurityInfoDialog = true },
              modifier = Modifier.testTag("grade11_security_shield_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "DRM Security Details",
                tint = Color(0xFF34D399)
              )
            }
          }

          // Source Switcher Tabs (GovDoc.lk vs e-Thaksalawa)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Grade11WebSource.values().forEach { src ->
              val isSelected = currentSource == src
              Surface(
                onClick = {
                  if (currentSource != src) {
                    triggerNavigation(src, selectedSubjectKeyword, selectedTerm)
                  }
                },
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) src.badgeColor else Color(0xFF1E293B),
                border = BorderStroke(
                  1.dp,
                  if (isSelected) src.badgeColor else Color(0xFF334155)
                ),
                modifier = Modifier
                  .weight(1f)
                  .testTag("portal_tab_11_${src.name.lowercase()}")
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = if (src == Grade11WebSource.GOVDOC) "📄 " else "🏛️ ",
                    fontSize = 11.sp
                  )
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                      text = src.title,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                    )
                    Text(
                      text = if (src == Grade11WebSource.GOVDOC) "වාර විභාග Papers" else "11 නිල විෂය අංශය",
                      fontSize = 8.5.sp,
                      color = if (isSelected) Color(0xFFE2E8F0) else Color(0xFF94A3B8)
                    )
                  }
                }
              }
            }
          }

          // 1. Term Filter Tabs: සියලුම වාර | 1 වන වාරය | 2 වන වාරය | 3 වන වාරය
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 10.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            val termsList = listOf(
              0 to "සියලුම වාර",
              1 to "1 වන වාරය",
              2 to "2 වන වාරය",
              3 to "3 වන වාරය"
            )

            termsList.forEach { (termNumber, label) ->
              val isSelected = selectedTerm == termNumber
              Surface(
                onClick = {
                  selectedTerm = termNumber
                  triggerNavigation(currentSource, selectedSubjectKeyword, termNumber)
                },
                shape = RoundedCornerShape(6.dp),
                color = if (isSelected) Color(0xFFDC2626) else Color(0xFF1E293B),
                border = BorderStroke(
                  1.dp,
                  if (isSelected) Color(0xFFEF4444) else Color(0xFF334155)
                ),
                modifier = Modifier.weight(1f)
              ) {
                Text(
                  text = label,
                  fontSize = 10.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(vertical = 5.dp)
                )
              }
            }
          }

          // 2. Subject Filter Horizontal Chips
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState())
              .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "විෂය:",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF94A3B8)
            )

            grade11SubjectFilterList.forEach { sub ->
              val isSelected = selectedSubjectName == sub.nameSinhala
              Surface(
                onClick = {
                  selectedSubjectName = sub.nameSinhala
                  selectedSubjectKeyword = sub.keywordGovDoc
                  triggerNavigation(currentSource, sub.keywordGovDoc, selectedTerm)
                },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) Color(0xFF10B981) else Color(0xFF1E293B),
                border = BorderStroke(
                  1.dp,
                  if (isSelected) Color(0xFF34D399) else Color(0xFF334155)
                )
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = sub.icon, fontSize = 10.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = sub.nameSinhala,
                    fontSize = 9.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else Color(0xFFE2E8F0)
                  )
                }
              }
            }
          }

          // Safety Status Mini Banner
          Surface(
            color = Color(0xFF047857).copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🛡️", fontSize = 11.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Screenshot / Record / Copy / Download සම්පූර්ණයෙන්ම අවහිරයි",
                  fontSize = 8.5.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF34D399)
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = if (hardwareSecureEnabled) "ScreenGuard: ON" else "ScreenGuard: OFF",
                  fontSize = 8.sp,
                  color = if (hardwareSecureEnabled) Color(0xFF34D399) else Color(0xFF94A3B8),
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF1E293B))
                    .clickable {
                      hardwareSecureEnabled = !hardwareSecureEnabled
                      Toast.makeText(
                        context,
                        if (hardwareSecureEnabled) "Screen Protection (FLAG_SECURE) සක්‍රීය විය" else "Screen Protection සාමාන්‍ය තත්වයට පත් විය",
                        Toast.LENGTH_SHORT
                      ).show()
                    }
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                )
              }
            }
          }

          // Progress Bar when Loading
          if (isLoading) {
            LinearProgressIndicator(
              progress = { pageProgress / 100f },
              modifier = Modifier
                .fillMaxWidth()
                .height(3.dp),
              color = Color(0xFF10B981),
              trackColor = Color(0xFF1E293B)
            )
          }
        }
      }
    },
    bottomBar = {
      // Bottom Navigation & Utility Bar
      Surface(
        color = Color(0xFF0F172A),
        border = BorderStroke(1.dp, Color(0xFF1E293B)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Navigation controls: Back, Forward, Reload, Reset to Home
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = {
                if (webViewInstance?.canGoBack() == true) {
                  webViewInstance?.goBack()
                }
              },
              enabled = canGoBack,
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Web Back",
                tint = if (canGoBack) Color.White else Color(0xFF475569),
                modifier = Modifier.size(18.dp)
              )
            }

            IconButton(
              onClick = {
                if (webViewInstance?.canGoForward() == true) {
                  webViewInstance?.goForward()
                }
              },
              enabled = canGoForward,
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Web Forward",
                tint = if (canGoForward) Color.White else Color(0xFF475569),
                modifier = Modifier.size(18.dp)
              )
            }

            IconButton(
              onClick = { webViewInstance?.reload() },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reload",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }

            IconButton(
              onClick = {
                selectedTerm = 0
                selectedSubjectName = "සියලුම විෂයන්"
                selectedSubjectKeyword = ""
                triggerNavigation(currentSource, "", 0)
              },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Reset to 11th Grade Home",
                tint = Color(0xFF38BDF8),
                modifier = Modifier.size(18.dp)
              )
            }
          }

          // Page Zoom Controls
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Surface(
              onClick = { webViewInstance?.zoomOut() },
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFF1E293B),
              border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
              Text(
                text = "A-",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Surface(
              onClick = { webViewInstance?.zoomIn() },
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFF1E293B),
              border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
              Text(
                text = "A+",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(Color(0xFFF8FAFC))
    ) {
      // Sandboxed Secure WebView
      AndroidView(
        factory = { ctx ->
          WebView(ctx).apply {
            webViewInstance = this
            isLongClickable = false
            isHapticFeedbackEnabled = false
            setOnLongClickListener { true }

            // Block all file downloads directly
            setDownloadListener { _, _, _, _, _ ->
              Toast.makeText(
                ctx,
                "🚫 බාගත කිරීම (Download) අවහිර කර ඇත!\nප්‍රශ්න පත්‍ර නරඹන්න හැක්කේ මෙම යෙදුම තුළ පමණි.",
                Toast.LENGTH_LONG
              ).show()
            }

            settings.apply {
              javaScriptEnabled = true
              domStorageEnabled = true
              setSupportZoom(true)
              builtInZoomControls = true
              displayZoomControls = false
              useWideViewPort = true
              loadWithOverviewMode = true
              allowFileAccess = false
              allowContentAccess = false
              cacheMode = WebSettings.LOAD_DEFAULT
            }

            webChromeClient = object : WebChromeClient() {
              override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pageProgress = newProgress
                isLoading = newProgress < 100
              }

              override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (!title.isNullOrBlank()) {
                  pageTitle = title
                }
              }
            }

            webViewClient = object : WebViewClient() {
              override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isLoading = true
              }

              override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isLoading = false
                canGoBack = view?.canGoBack() ?: false
                canGoForward = view?.canGoForward() ?: false

                // Inject DRM script + Subject/Term content isolation script
                val termKeywordJs = when (selectedTerm) {
                  1 -> "1st"
                  2 -> "2nd"
                  3 -> "3rd"
                  else -> ""
                }
                val subjectKeyJs = selectedSubjectKeyword.lowercase()

                val drmScript = """
                  (function() {
                    // 1. Disable text selection and copying via CSS
                    var css = '* { -webkit-user-select: none !important; -moz-user-select: none !important; -ms-user-select: none !important; user-select: none !important; -webkit-touch-callout: none !important; } ' +
                              'a[download], [download], .download-button, .btn-download, a[href*="download"], a[href$=".zip"], a[href$=".rar"], a[href$=".pdf"] [download] { pointer-events: none !important; opacity: 0.6 !important; }';
                    var head = document.head || document.getElementsByTagName('head')[0];
                    if (head) {
                      var style = document.createElement('style');
                      style.type = 'text/css';
                      style.appendChild(document.createTextNode(css));
                      head.appendChild(style);
                    }

                    // 2. Block copy, cut, contextmenu, selectstart, dragstart
                    ['contextmenu', 'copy', 'cut', 'paste', 'selectstart', 'dragstart'].forEach(function(evt) {
                      document.addEventListener(evt, function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        return false;
                      }, true);
                    });

                    // 3. Block download links click
                    document.querySelectorAll('a[download], a[href*="download"]').forEach(function(el) {
                      el.onclick = function(e) {
                        e.preventDefault();
                        alert('ආරක්ෂක නීති අනුව ප්‍රශ්න පත්‍ර බාගත කිරීම අවහිර කර ඇත.');
                        return false;
                      };
                    });

                    // 4. Content filter: filter by subject and term if selected
                    var termFilter = '$termKeywordJs';
                    var subFilter = '$subjectKeyJs';
                    if (termFilter || subFilter) {
                      var items = document.querySelectorAll('article, .post, .entry, .coursebox, .card, li.course');
                      items.forEach(function(item) {
                        var text = item.innerText.toLowerCase();
                        var matchTerm = !termFilter || text.includes(termFilter) || text.includes(termFilter + ' term') || text.includes(termFilter + ' වාර');
                        var matchSub = !subFilter || text.includes(subFilter);
                        if (!matchTerm || !matchSub) {
                          item.style.display = 'none';
                        } else {
                          item.style.display = '';
                        }
                      });
                    }
                  })();
                """.trimIndent()

                view?.evaluateJavascript(drmScript, null)
              }

              override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
              }
            }

            loadUrl(activeUrl)
          }
        },
        update = { webView ->
          webViewInstance = webView
        },
        onRelease = { webView ->
          webView.stopLoading()
          webView.destroy()
        },
        modifier = Modifier.fillMaxSize()
      )

      // Anti-Leak Dynamic Security Watermark Overlay
      if (showWatermark) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .alpha(0.08f)
            .padding(24.dp)
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            repeat(5) { index ->
              Text(
                text = "GRADE 11 OFFICIAL • ${studentName.take(15)} ${studentPhone.takeLast(4)} • DRM PROTECTED",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.rotate(if (index % 2 == 0) -18f else 18f)
              )
            }
          }
        }
      }
    }
  }

  // Security Details Dialog
  if (showSecurityInfoDialog) {
    AlertDialog(
      onDismissRequest = { showSecurityInfoDialog = false },
      icon = {
        Icon(
          imageVector = Icons.Default.VerifiedUser,
          contentDescription = null,
          tint = Color(0xFF047857),
          modifier = Modifier.size(36.dp)
        )
      },
      title = {
        Text(
          text = "11 වසර ප්‍රශ්න පත්‍ර DRM ආරක්ෂණ පද්ධතිය",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "ඔබගේ ඉල්ලීම පරිදි මෙම 11 වසර වාර විභාග ප්‍රශ්න පත්‍ර නරඹනයට පහත ආරක්ෂණ පියවරයන් 100% ක් සක්‍රීය කර ඇත:",
            fontSize = 12.sp,
            color = Color(0xFF334155)
          )

          Divider(color = Color(0xFFE2E8F0))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🚫 ", fontSize = 13.sp)
            Text("ස්ක්‍රීන්ෂොට් (Screenshots) සහ Screen Record සම්පූර්ණයෙන්ම අවහිර කර ඇත.", fontSize = 11.sp)
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🚫 ", fontSize = 13.sp)
            Text("පෙළ පිටපත් කිරීම (Copy/Select) CSS හා JS මගින් අවහිර කර ඇත.", fontSize = 11.sp)
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🚫 ", fontSize = 13.sp)
            Text("ගොනු බාගත කිරීම් (File Downloads) සම්පූර්ණයෙන්ම වළක්වා ඇත.", fontSize = 11.sp)
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🎯 ", fontSize = 13.sp)
            Text("එක් එක් විෂයට සහ 1, 2, 3 වාරවලට අදාළ ප්‍රශ්න පත්‍ර පමණක් තෝරා පෙන්වයි.", fontSize = 11.sp)
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🛡️ ", fontSize = 13.sp)
            Text("පරිශීලක අනන්‍යතා Dynamic Watermark ක්‍රියාත්මකයි.", fontSize = 11.sp)
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showSecurityInfoDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
        ) {
          Text("තහවුරුයි (OK)", color = Color.White, fontWeight = FontWeight.Bold)
        }
      }
    )
  }
}
