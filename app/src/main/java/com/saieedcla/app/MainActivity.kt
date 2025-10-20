package com.saieedcla.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saieedcla.app.ui.theme.SAIEEDclaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SAIEEDclaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController, this@MainActivity) }
                    composable("cbm") { CBMScreen(navController, this@MainActivity) }
                    composable("cost") { CostScreen(navController, this@MainActivity) }
                    composable("settings") { SettingsScreen(navController, this@MainActivity) }
                }
            }
        }
    }
}

@Composable
fun MainScreen(nav: NavHostController, ctx: Context) {
    val pref = ctx.getSharedPreferences("saieed_prefs", Context.MODE_PRIVATE)
    val lang = pref.getString("lang", "ar") ?: "ar"

    val textMain = if (lang == "ar") mapOf(
        "title" to "SAIEEDcla",
        "cbm" to "حساب الحجم",
        "cost" to "حساب التكلفة",
        "settings" to "الإعدادات"
    ) else mapOf(
        "title" to "SAIEEDcla",
        "cbm" to "CBM Calculator",
        "cost" to "Cost Calculator",
        "settings" to "Settings"
    )

    Scaffold(topBar = {
        TopAppBar(title = { Text(textMain["title"]!!, fontWeight = FontWeight.Bold) })
    }) { inner ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { nav.navigate("cbm") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(textMain["cbm"]!!)
            }
            Button(onClick = { nav.navigate("cost") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(textMain["cost"]!!)
            }
            Button(onClick = { nav.navigate("settings") }, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(textMain["settings"]!!)
            }
        }
    }
}

@Composable
fun CBMScreen(nav: NavHostController, ctx: Context) {
    val pref = ctx.getSharedPreferences("saieed_prefs", Context.MODE_PRIVATE)
    val lang = pref.getString("lang", "ar") ?: "ar"

    var length by remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(0f) }
    var result by remember { mutableStateOf(0f) }

    val L = if (lang=="ar") "الطول (سم)" else "Length (cm)"
    val W = if (lang=="ar") "العرض (سم)" else "Width (cm)"
    val H = if (lang=="ar") "الارتفاع (سم)" else "Height (cm)"
    val calc = if (lang=="ar") "احسب" else "Calculate"
    val back = if (lang=="ar") "رجوع" else "Back"
    val cbmText = if (lang=="ar") "المتر المكعب" else "CBM"

    Scaffold(topBar = {
        TopAppBar(title = { Text(if (lang=="ar") "حساب الحجم" else "CBM Calculator") })
    }) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = if (length==0f) "" else length.toString(),
                onValueChange = { length = it.toFloatOrNull() ?: 0f },
                label = { Text(L) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (width==0f) "" else width.toString(),
                onValueChange = { width = it.toFloatOrNull() ?: 0f },
                label = { Text(W) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (height==0f) "" else height.toString(),
                onValueChange = { height = it.toFloatOrNull() ?: 0f },
                label = { Text(H) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                // CBM = (L*W*H) / 1,000,000
                result = (length * width * height) / 1000000f
            }, modifier = Modifier.fillMaxWidth()) {
                Text(calc)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "${'{'}cbmText{'}'}: ${'{'}String.format("%.6f", result){'}'}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { nav.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
                Text(back)
            }
        }
    }
}

@Composable
fun CostScreen(nav: NavHostController, ctx: Context) {
    val pref = ctx.getSharedPreferences("saieed_prefs", Context.MODE_PRIVATE)
    val lang = pref.getString("lang", "ar") ?: "ar"

    var rmbPrice by remember { mutableStateOf(0f) }
    var packing by remember { mutableStateOf(1f) }
    var cbm by remember { mutableStateOf(0f) }
    var shippingPerMeter by remember { mutableStateOf(pref.getFloat("shipping_per_m3", 10f)) }
    var rmbToUsd by remember { mutableStateOf(pref.getFloat("rmb_to_usd", 0.14f)) }
    var usdToIqd by remember { mutableStateOf(pref.getFloat("usd_to_iqd", 1500f)) }

    var shipCost by remember { mutableStateOf(0f) }
    var costUsd by remember { mutableStateOf(0f) }
    var costIqd by remember { mutableStateOf(0f) }

    val labels = if (lang=="ar") mapOf(
        "rmb" to "السعر بالرممبي",
        "pack" to "التعبئة",
        "cbm" to "الحجم (متر مكعب)",
        "calc" to "احسب السعر",
        "back" to "رجوع",
        "ship" to "سعر الشحن",
        "usd" to "كلفة الوصول بالدولار",
        "iqd" to "كلفة الوصول بالدينار"
    ) else mapOf(
        "rmb" to "Price (RMB)",
        "pack" to "Packing factor",
        "cbm" to "Volume (CBM)",
        "calc" to "Calculate",
        "back" to "Back",
        "ship" to "Shipping Price",
        "usd" to "Cost (USD)",
        "iqd" to "Cost (IQD)"
    )

    Scaffold(topBar = {
        TopAppBar(title = { Text(if (lang=="ar") "حساب التكلفة" else "Cost Calculator") })
    }) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = if (rmbPrice==0f) "" else rmbPrice.toString(),
                onValueChange = { rmbPrice = it.toFloatOrNull() ?: 0f },
                label = { Text(labels["rmb"]!!) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (packing==0f) "" else packing.toString(),
                onValueChange = { packing = it.toFloatOrNull() ?: 1f },
                label = { Text(labels["pack"]!!) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (cbm==0f) "" else cbm.toString(),
                onValueChange = { cbm = it.toFloatOrNull() ?: 0f },
                label = { Text(labels["cbm"]!!) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                // shipCost = (cbm * price_per_m3) / packing
                shipCost = (cbm * shippingPerMeter) / packing
                // costUsd = (rmbPrice / rmbToUsd) + shipCost
                costUsd = (rmbPrice / rmbToUsd) + shipCost
                // costIqd = costUsd * usdToIqd
                costIqd = costUsd * usdToIqd
            }, modifier = Modifier.fillMaxWidth()) {
                Text(labels["calc"]!!)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "${'{'}labels["ship"]{'}'}: ${'{'}String.format("%.2f", shipCost){'}'}", fontWeight = FontWeight.Bold)
            Text(text = "${'{'}labels["usd"]{'}'}: ${'{'}String.format("%.2f", costUsd){'}'}", fontWeight = FontWeight.Bold)
            Text(text = "${'{'}labels["iqd"]{'}'}: ${'{'}String.format("%.2f", costIqd){'}'}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { nav.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
                Text(labels["back"]!!)
            }
        }
    }
}

@Composable
fun SettingsScreen(nav: NavHostController, ctx: Context) {
    val pref = ctx.getSharedPreferences("saieed_prefs", Context.MODE_PRIVATE)
    val langPref = pref.getString("lang", "ar") ?: "ar"

    var lang by remember { mutableStateOf(langPref) }
    var rmbToUsd by remember { mutableStateOf(pref.getFloat("rmb_to_usd", 0.14f)) }
    var usdToIqd by remember { mutableStateOf(pref.getFloat("usd_to_iqd", 1500f)) }
    var shippingPerM3 by remember { mutableStateOf(pref.getFloat("shipping_per_m3", 10f)) }

    val saveText = if (lang=="ar") "احفظ" else "Save"
    val title = if (lang=="ar") "الإعدادات" else "Settings"
    val back = if (lang=="ar") "رجوع" else "Back"

    Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (lang=="ar") "اللغة:" else "Language:", modifier = Modifier.weight(1f))
                Button(onClick = { lang = "ar" }, modifier = Modifier.padding(4.dp)) { Text("عربية") }
                Button(onClick = { lang = "en" }, modifier = Modifier.padding(4.dp)) { Text("English") }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = rmbToUsd.toString(), onValueChange = { rmbToUsd = it.toFloatOrNull() ?: 0f }, label = { Text(if (lang=="ar") "سعر تصريف RMB→USD" else "RMB → USD rate") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = usdToIqd.toString(), onValueChange = { usdToIqd = it.toFloatOrNull() ?: 0f }, label = { Text(if (lang=="ar") "سعر تصريف USD→IQD" else "USD → IQD rate") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = shippingPerM3.toString(), onValueChange = { shippingPerM3 = it.toFloatOrNull() ?: 0f }, label = { Text(if (lang=="ar") "سعر متر الشحن" else "Shipping price per m³") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                pref.edit().putString("lang", lang).putFloat("rmb_to_usd", rmbToUsd).putFloat("usd_to_iqd", usdToIqd).putFloat("shipping_per_m3", shippingPerM3).apply()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(saveText)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { nav.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
                Text(back)
            }
        }
    }
}
