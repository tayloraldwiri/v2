package com.ithub.nativeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

private val navy = Color(0xFF080F21)
private val card = Color(0xFF14233C)
private val card2 = Color(0xFF1B3151)
private val blue = Color(0xFF48B5FF)
private val white = Color(0xFFF7FAFF)
private val gray = Color(0xFFC2D0E2)
private val teal = Color(0xFF58E2CE)
private val purple = Color(0xFFA895FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(8,15,33)
        window.navigationBarColor = android.graphics.Color.rgb(8,15,33)
        setContent { MainApp() }
    }
}

@Composable
private fun MainApp() {
    var selected by remember { mutableIntStateOf(0) }
    var budget by remember { mutableFloatStateOf(10f) }
    val context = LocalContext.current
    val contact = {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/46764911868")))
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(colorScheme = darkColorScheme(background = navy, surface = card, primary = blue, onBackground = white, onSurface = white, onPrimary = navy)) {
            Scaffold(
                containerColor = navy,
                topBar = {
                    Row(Modifier.fillMaxWidth().background(navy).padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("IT HUB", color = blue, fontSize = 23.sp, fontWeight = FontWeight.Black)
                            Text("حلول رقمية لنمو أعمالك", color = gray, fontSize = 11.sp)
                        }
                        Text(listOf("الرئيسية", "الحاسبات", "خدماتنا", "تواصل معنا")[selected], color = white, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    }
                },
                bottomBar = {
                    NavigationBar(containerColor = card, tonalElevation = 0.dp) {
                        listOf("الرئيسية", "الحاسبات", "خدماتنا", "تواصل").forEachIndexed { index, label ->
                            NavigationBarItem(
                                selected = selected == index,
                                onClick = { selected = index },
                                icon = { Text(listOf("⌂", "▥", "✦", "✉")[index], fontSize = 23.sp, fontWeight = FontWeight.Bold) },
                                label = { Text(label, fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = blue, selectedTextColor = white, unselectedIconColor = gray, unselectedTextColor = gray, indicatorColor = card2)
                            )
                        }
                    }
                }
            ) { insets ->
                Column(Modifier.fillMaxSize().padding(insets).verticalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    when (selected) {
                        0 -> HomeScreen(budget, onCalculator = { selected = 1 }, onServices = { selected = 2 }, onContact = contact)
                        1 -> CalculatorScreen(budget, { budget = it }, onContact = contact)
                        2 -> ServicesScreen(onContact = contact)
                        else -> ContactScreen(onContact = contact)
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun Panel(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(card).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
}

@Composable
private fun SectionHeading(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, fontSize = 24.sp, color = white, fontWeight = FontWeight.Bold)
        Text(subtitle, fontSize = 13.sp, color = gray)
    }
}

@Composable
private fun HomeScreen(budget: Float, onCalculator: () -> Unit, onServices: () -> Unit, onContact: () -> Unit) {
    Panel {
        Text("IT HUB  /  DIGITAL STUDIO", color = teal, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text("موقعك. جمهورك. نموّك.", color = white, fontSize = 30.sp, lineHeight = 38.sp, fontWeight = FontWeight.Black)
        Text("نبني حضورك الرقمي من موقعك الإلكتروني إلى تطبيقك وحملاتك الإعلانية.", color = gray, fontSize = 15.sp, lineHeight = 25.sp)
        Button(onClick = onContact, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = navy)) { Text("ابدأ مشروعك معنا", fontWeight = FontWeight.Bold) }
    }
    SectionHeading("اكتشف فرص نموك", "أرقام توضيحية تساعدك على التخطيط")
    Panel {
        Text("ميزانية تجريبية", color = gray)
        Text("$${budget.roundToInt()}", color = blue, fontSize = 40.sp, fontWeight = FontWeight.Black)
        Text("جرّب أدواتنا التفاعلية لمقارنة المنصات وتوزيع الميزانية.", color = gray)
        OutlinedButton(onClick = onCalculator, modifier = Modifier.fillMaxWidth()) { Text("افتح الحاسبات ←", color = white) }
    }
    SectionHeading("ما الذي نقدّمه؟", "حلول متكاملة في مكان واحد")
    listOf("تصميم المواقع" to "مواقع سريعة وعصرية", "الإعلانات الممولة" to "حملات تسويق تناسب جمهورك", "تطبيقات الجوال" to "تطبيقات Android وiOS").forEach { (name, detail) ->
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(card).clickable(onClick = onServices).padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, color = white, fontWeight = FontWeight.Bold)
            Text(detail, color = gray, fontSize = 12.sp)
        }
    }
}

@Composable
private fun CalculatorScreen(budget: Float, onBudget: (Float) -> Unit, onContact: () -> Unit) {
    SectionHeading("مختبر التسويق", "حاسبات ورسوم تفاعلية لتخطيط حملتك")
    Panel {
        Text("ميزانيتك الإعلانية", color = white, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("$${budget.roundToInt()}", color = blue, fontSize = 44.sp, fontWeight = FontWeight.Black)
        Slider(value = budget, onValueChange = { onBudget(it.roundToInt().toFloat()) }, valueRange = 1f..100f, steps = 98, colors = SliderDefaults.colors(thumbColor = blue, activeTrackColor = blue, inactiveTrackColor = card2))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("$1", color = gray); Text("$100", color = gray) }
        Text("كل الرسوم والنتائج تتحدث تلقائياً", color = teal, fontSize = 12.sp)
    }
    Panel {
        Text("01  /  وزّع ميزانيتك", color = white, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("توزيع مقترح للإنفاق الإعلاني", color = gray, fontSize = 13.sp)
        DonutChart()
        listOf(Triple("الإعلانات الممولة", .7f, blue), Triple("تصميم المحتوى", .2f, teal), Triple("الاختبار والتحسين", .1f, purple)).forEach { (label, part, color) ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, color = white)
                Text("${(part * 100).roundToInt()}%  •  $${"%.2f".format(java.util.Locale.US, budget * part)}", color = color, fontWeight = FontWeight.Bold)
            }
            LinearProgressIndicator(progress = { part }, modifier = Modifier.fillMaxWidth().height(7.dp).clip(RoundedCornerShape(8.dp)), color = color, trackColor = card2)
        }
    }
    Panel {
        Text("02  /  خطّط بالأرقام", color = white, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("اختر مدة حملتك", color = gray)
        var days by remember { mutableIntStateOf(7) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            listOf(3,7,14,30).forEach { d ->
                FilterChip(selected = days == d, onClick = { days = d }, label = { Text("$d أيام", fontSize = 11.sp) }, modifier = Modifier.weight(1f), colors = FilterChipDefaults.filterChipColors(selectedContainerColor = blue, selectedLabelColor = navy, labelColor = white))
            }
        }
        Text("الإنفاق اليومي", color = gray)
        Text("$${"%.2f".format(java.util.Locale.US, budget / days)}", color = blue, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        DailyBars(days, budget)
    }
    Panel {
        Text("03  /  قارن المنصات", color = white, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("تقديرات مرات الظهور حسب تكلفة الألف ظهور", color = gray, fontSize = 13.sp)
        val platforms = listOf("Facebook" to 5f, "Instagram" to 6f, "TikTok" to 4f, "X" to 8f)
        platforms.forEach { (name, cpm) ->
            val impressions = (budget * 1000 / cpm).roundToInt()
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(name, color = white, fontWeight = FontWeight.SemiBold)
                Text("${impressions} ظهور", color = blue, fontWeight = FontWeight.Bold)
            }
            LinearProgressIndicator(progress = { (4f / cpm).coerceIn(0f,1f) }, modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(8.dp)), color = teal, trackColor = card2)
        }
        Text("أرقام تعليمية فقط؛ الأسعار والنتائج تختلف حسب السوق والجمهور.", color = gray, fontSize = 12.sp)
    }
    Panel {
        Text("04  /  من المشاهدة إلى الاستفسار", color = white, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("مثال افتراضي لمسار العميل", color = gray)
        val views = (budget * 200).roundToInt()
        val clicks = (views * .015f).roundToInt()
        val leads = (clicks * .05f).roundToInt()
        FunnelBar("مرات الظهور", views, 1f, blue)
        FunnelBar("النقرات", clicks, .52f, teal)
        FunnelBar("الاستفسارات", leads, .25f, purple)
        Text("افتراضات: 1.5% نسبة نقر و5% استفسارات من النقرات. ليست نتائج مضمونة.", color = gray, fontSize = 12.sp)
    }
    Button(onClick = onContact, modifier = Modifier.fillMaxWidth()) { Text("ناقش ميزانيتك معنا") }
}

@Composable
private fun DonutChart() {
    Box(Modifier.fillMaxWidth().height(170.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(155.dp)) {
            val colors = listOf(blue, teal, purple)
            val fractions = listOf(.7f, .2f, .1f)
            var start = -90f
            fractions.forEachIndexed { i, value ->
                drawArc(colors[i], start, value * 360f - 3f, useCenter = false, style = Stroke(width = 25.dp.toPx(), cap = StrokeCap.Round))
                start += value * 360f
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("70%", color = white, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text("للإعلانات", color = gray, fontSize = 11.sp)
        }
    }
}

@Composable
private fun DailyBars(days: Int, budget: Float) {
    Row(Modifier.fillMaxWidth().height(90.dp), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.Bottom) {
        repeat(days.coerceAtMost(14)) { i ->
            val factor = .48f + ((i * 7) % 9) / 18f
            Box(Modifier.weight(1f).fillMaxHeight(factor).clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)).background(if (i % 3 == 0) teal else blue))
        }
    }
    Text("رسم توضيحي لتوزيع الأيام • إجمالي الميزانية $${budget.roundToInt()}", color = gray, fontSize = 11.sp)
}

@Composable
private fun FunnelBar(label: String, number: Int, fraction: Float, color: Color) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = white)
        Text("$number", color = color, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
    Box(Modifier.fillMaxWidth().height(19.dp).clip(RoundedCornerShape(12.dp)).background(card2)) {
        Box(Modifier.fillMaxWidth(fraction).fillMaxHeight().clip(RoundedCornerShape(12.dp)).background(color))
    }
}

@Composable
private fun ServicesScreen(onContact: () -> Unit) {
    SectionHeading("خدماتنا", "كل ما يحتاجه مشروعك للنمو الرقمي")
    listOf(
        Triple("01  /  WEB", "تصميم وتطوير المواقع", "مواقع احترافية متجاوبة، صفحات مخصصة، وربط بوسائل التواصل الاجتماعي."),
        Triple("02  /  MARKETING", "التسويق والإعلانات الممولة", "إدارة الحملات وتصميم المحتوى ومتابعة الأداء والتقارير."),
        Triple("03  /  MOBILE", "تطبيقات Android وiOS", "واجهات سلسة وتطوير مخصص وربط بالخدمات والأنظمة."),
        Triple("04  /  BRAND & CODE", "الهوية والحلول البرمجية", "هوية بصرية واضحة وحلول تقنية تناسب احتياجات مشروعك.")
    ).forEach { (tag, title, detail) ->
        Panel {
            Text(tag, color = teal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(title, color = white, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Text(detail, color = gray, fontSize = 14.sp, lineHeight = 23.sp)
            Text("اطلب الخدمة ←", color = blue, modifier = Modifier.clickable(onClick = onContact), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ContactScreen(onContact: () -> Unit) {
    SectionHeading("لنبدأ مشروعك", "من الفكرة إلى التنفيذ والدعم")
    Panel {
        Text("حزمة البداية", color = white, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("$35", color = blue, fontSize = 52.sp, fontWeight = FontWeight.Black)
        Text("تشمل موقع إلكتروني خاص بالشركة", color = white, fontSize = 17.sp)
        Text("تواصل معنا لتحديد نطاق العمل والتفاصيل قبل البدء.", color = gray)
        Button(onClick = onContact, modifier = Modifier.fillMaxWidth()) { Text("استفسر عن الحزمة") }
    }
    SectionHeading("كيف نعمل معاً؟", "خطوات واضحة من البداية إلى التسليم")
    listOf("01" to "نتعرّف على مشروعك", "02" to "نحدّد الحل المناسب", "03" to "ننفّذ وندعمك").forEach { (num, label) ->
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(card).padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(num, color = blue, fontWeight = FontWeight.Black, fontSize = 22.sp)
            Text(label, color = white, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        }
    }
    Panel {
        Text("جاهز لخطوتك الرقمية القادمة؟", color = white, fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text("أخبرنا عن فكرتك، ولنبحث معاً عن الحل المناسب.", color = gray)
        Button(onClick = onContact, modifier = Modifier.fillMaxWidth()) { Text("تواصل معنا عبر واتساب") }
    }
    Text("© 2026 IT Hub — جميع الحقوق محفوظة", color = gray, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
}
