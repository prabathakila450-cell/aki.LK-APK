package com.example

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

// Grade 10 Term Test Portal Targets (User Provided)
const val GOVDOC_GRADE_10_URL = "https://govdoc.lk/category/term-test-papers/grade-10"
const val ETHAKSALAWA_GRADE_10_URL = "https://e-thaksalawa.moe.gov.lk/lcms/course/index.php?categoryid=21"

enum class Grade10WebSource(
  val title: String,
  val subtitle: String,
  val url: String,
  val badge: String,
  val badgeColor: Color
) {
  GOVDOC(
    title = "GovDoc (10)",
    subtitle = "10 වසර වාර විභාග ප්‍රශ්න පත්‍ර",
    url = GOVDOC_GRADE_10_URL,
    badge = "GRADE 10",
    badgeColor = Color(0xFF047857)
  ),
  GOVDOC_11(
    title = "GovDoc (11)",
    subtitle = "11 ශ්‍රේණිය වාර විභාග ප්‍රශ්න පත්‍ර (හවුල් PDF)",
    url = "https://govdoc.lk/category/term-test-papers/grade-11",
    badge = "GRADE 11",
    badgeColor = Color(0xFF0284C7)
  ),
  ETHAKSALAWA(
    title = "e-Thaksalawa",
    subtitle = "10/11 ශ්‍රේණි නිල අමාත්‍යාංශ ද්වාරය",
    url = ETHAKSALAWA_GRADE_10_URL,
    badge = "OFFICIAL MOE",
    badgeColor = Color(0xFF1D4ED8)
  )
}

/**
 * Ultra-secure, DRM-protected web viewer designed specifically for Grade 10 Term Test Papers.
 * Blocks:
 * 1. Screenshots (Hardware FLAG_SECURE)
 * 2. Screen recording (Blank black screen via FLAG_SECURE)
 * 3. Text copying & selection (Injected CSS & JS)
 * 4. File downloads (Intercepted download listener)
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Grade10SecureTermTestPortalScreen(
  initialSource: Grade10WebSource = Grade10WebSource.GOVDOC,
  studentName: String = "ශිෂ්‍ය ගිණුම",
  studentPhone: String = "",
  onBack: () -> Unit
) {
  val context = LocalContext.current
  val activity = context as? Activity

  // Keep hardware screen protection active on real devices while keeping emulator preview visible
  DisposableEffect(activity) {
    activity?.let { act ->
      AppSecurityManager.applyScreenProtection(act, true)
    }
    onDispose {
      activity?.let { act ->
        AppSecurityManager.applyScreenProtection(act, false)
      }
    }
  }

  var currentSource by remember { mutableStateOf(initialSource) }
  var activeUrl by remember { mutableStateOf(initialSource.url) }
  var webViewInstance by remember { mutableStateOf<WebView?>(null) }
  var isLoading by remember { mutableStateOf(true) }
  var pageProgress by remember { mutableIntStateOf(0) }
  var canGoBack by remember { mutableStateOf(false) }
  var canGoForward by remember { mutableStateOf(false) }
  var pageTitle by remember { mutableStateOf("10 වසර වාර විභාග ප්‍රශ්න පත්‍ර") }
  var showSecurityInfoDialog by remember { mutableStateOf(false) }
  var showWatermark by remember { mutableStateOf(true) }

  // Handle hardware back button to navigate back in web history first
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
                modifier = Modifier.testTag("grade10_secure_portal_back_btn")
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
                    text = "10 වසර වාර විභාග ප්‍රශ්න පත්‍ර",
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
                  text = "${currentSource.title} • ආරක්ෂිත කියවීම් මාදිලිය",
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
              modifier = Modifier.testTag("grade10_security_shield_btn")
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
              .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Grade10WebSource.values().forEach { src ->
              val isSelected = currentSource == src
              Surface(
                onClick = {
                  if (currentSource != src) {
                    currentSource = src
                    activeUrl = src.url
                    webViewInstance?.loadUrl(src.url)
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
                  .testTag("portal_tab_${src.name.lowercase()}")
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 7.dp, horizontal = 8.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = if (src == Grade10WebSource.GOVDOC) "📄 " else "🏛️ ",
                    fontSize = 12.sp
                  )
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                      text = src.title,
                      fontSize = 11.5.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                    )
                    Text(
                      text = if (src == Grade10WebSource.GOVDOC) "වාර විභාග Papers" else "නිල විෂය අංශය",
                      fontSize = 8.5.sp,
                      color = if (isSelected) Color(0xFFE2E8F0) else Color(0xFF94A3B8)
                    )
                  }
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
                .padding(horizontal = 12.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🛡️", fontSize = 11.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Screenshot / Record / Copy / Download සම්පූර්ණයෙන්ම අවහිර කර ඇත",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF34D399)
                )
              }

              Text(
                text = if (showWatermark) "Watermark: ON" else "Watermark: OFF",
                fontSize = 8.sp,
                color = Color(0xFF94A3B8),
                modifier = Modifier.clickable { showWatermark = !showWatermark }
              )
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
                activeUrl = currentSource.url
                webViewInstance?.loadUrl(currentSource.url)
              },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Reset to 10th Grade Home",
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
      // 2. Embedded Secure Sandboxed WebView
      AndroidView(
        factory = { ctx ->
          WebView(ctx).apply {
            webViewInstance = this
            // Long click and selection disabled
            isLongClickable = false
            isHapticFeedbackEnabled = false
            setOnLongClickListener { true }

            // 3. Intercept and completely BLOCK all direct downloads
            setDownloadListener { url, _, _, _, _ ->
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

                // 4. Inject DRM Script: Disables selection, right-click, copy, cut, drag, and hides download elements
                val drmScript = """
                  (function() {
                    // Disable selection via CSS
                    var css = '* { -webkit-user-select: none !important; -moz-user-select: none !important; -ms-user-select: none !important; user-select: none !important; -webkit-touch-callout: none !important; } ' +
                              'a[download], [download], .download-button, .btn-download, a[href*="download"], a[href$=".zip"], a[href$=".rar"], a[href$=".pdf"] [download] { pointer-events: none !important; opacity: 0.6 !important; }';
                    var head = document.head || document.getElementsByTagName('head')[0];
                    if (head) {
                      var style = document.createElement('style');
                      style.type = 'text/css';
                      style.appendChild(document.createTextNode(css));
                      head.appendChild(style);
                    }

                    // Block copy, cut, contextmenu, selectstart, dragstart
                    ['contextmenu', 'copy', 'cut', 'paste', 'selectstart', 'dragstart'].forEach(function(evt) {
                      document.addEventListener(evt, function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        return false;
                      }, true);
                    });

                    // Alert on clicking download links
                    document.querySelectorAll('a[download], a[href*="download"]').forEach(function(el) {
                      el.onclick = function(e) {
                        e.preventDefault();
                        alert('ආරක්ෂක නීති අනුව ප්‍රශ්න පත්‍ර බාගත කිරීම අවහිර කර ඇත.');
                        return false;
                      };
                    });
                  })();
                """.trimIndent()

                view?.evaluateJavascript(drmScript, null)
              }

              override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val reqUrl = request?.url?.toString() ?: ""
                // Keep navigation internal to the secure webview
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

      // 5. Anti-Leak Dynamic Security Watermark Overlay
      if (showWatermark) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .alpha(0.09f)
            .padding(24.dp)
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            repeat(5) { index ->
              Text(
                text = "GRADE 10 OFFICIAL • ${studentName.take(15)} ${studentPhone.takeLast(4)} • DRM PROTECTED",
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

  // Security Information Dialog
  if (showSecurityInfoDialog) {
    AlertDialog(
      onDismissRequest = { showSecurityInfoDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            tint = Color(0xFF047857),
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "ප්‍රශ්න පත්‍ර ආරක්ෂණ පද්ධතිය",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
          )
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "ඔබගේ ඉල්ලීම පරිදි මෙම 10 වසර වාර විභාග ප්‍රශ්න පත්‍ර නරඹනයට පහත ආරක්ෂණ පියවරයන් 100% ක් සක්‍රීය කර ඇත:",
            fontSize = 12.sp,
            color = Color(0xFF334155)
          )

          listOf(
            "🚫 තිර ආරක්ෂාව හා Anti-Leak" to "අන්තර්ගතය ආරක්ෂා කිරීමට සහ තිරය පැහැදිලිව පෙන්වීමට Anti-Copy සහ Watermark ක්‍රමවේද ක්‍රියාත්මකයි.",
            "📋 Copying & Text Selection අවහිර කිරීම" to "වෙබ් අඩවියේ අන්තර්ගත පිටපත් කිරීම (Copy/Cut/Select) සම්පූර්ණයෙන්ම අක්‍රිය කර ඇත.",
            "⬇️ Download කිරීම අවහිර කිරීම" to "බාහිර මතකයට ගොනු බාගත කිරීම (Direct Download) අවහිර කර ඇති අතර ඇප් එක තුළ පමණක් කියවිය හැක.",
            "👤 Anti-Leak Watermark" to "වෙනත් කැමරාවකින් ඡායාරූප ගැනීම වැළැක්වීමට විනිවිද පෙනෙන ශිෂ්‍ය අනන්‍යතා Watermark එකක් ක්‍රියාත්මකයි."
          ).forEach { (itemTitle, itemDesc) ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFF1F5F9),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(8.dp)) {
                Text(
                  text = itemTitle,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF0F172A)
                )
                Text(
                  text = itemDesc,
                  fontSize = 10.sp,
                  color = Color(0xFF475569)
                )
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showSecurityInfoDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
        ) {
          Text("තහවුරුයි", color = Color.White, fontSize = 12.sp)
        }
      }
    )
  }
}
