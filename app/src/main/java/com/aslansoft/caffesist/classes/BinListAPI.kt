package com.aslansoft.caffesist.classes
import android.annotation.SuppressLint
import android.content.Context

data class BinInfo(
    val bin: String,
    val bankCode: String,
    val bankName: String,
    val type: String,
    val subType: String?,
    val virtual: String?,
    val prepaid: String?,
    val primary: String?,
    val ey: String?
)

@SuppressLint("SuspiciousIndentation")
fun loadBinData(context: Context): Map<String, BinInfo> {
    val binmap = mutableMapOf<String, BinInfo>()
    context.assets.open("binlist.csv").bufferedReader().useLines { lines ->
        lines.drop(1).forEach { line ->  // İlk satırı atla (başlıklar)
            val parts = line.split(",")  // CSV'yi virgülle ayır

            if (parts.size >= 9) {
                val bin = parts[0].trim()
                if (bin.isNotBlank()) {
                    val binInfo = BinInfo(
                        bin = bin,
                        bankCode = parts[1].trim(),
                        bankName = parts[2].trim(),
                        type = parts[3].trim(),
                        subType = parts.getOrNull(4)?.trim(),
                        virtual = parts.getOrNull(5)?.trim(),
                        prepaid = parts.getOrNull(6)?.trim(),
                        primary = parts.getOrNull(7)?.trim(),
                        ey = parts.getOrNull(8)?.trim()
                    )
                    binmap[bin] = binInfo
                }
            }
        }
    }
    return binmap
}
