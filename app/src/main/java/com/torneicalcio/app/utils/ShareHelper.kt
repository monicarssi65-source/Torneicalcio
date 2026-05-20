package com.torneicalcio.app.utils
import android.content.Context
import android.content.Intent

object ShareHelper {
    fun shareWhatsApp(ctx: Context, text: String) {
        val i = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, text); `package` = "com.whatsapp" }
        try { ctx.startActivity(i) } catch (e: Exception) { shareGeneric(ctx, text) }
    }
    fun shareEmail(ctx: Context, subject: String, text: String) {
        ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply { type = "message/rfc822"; putExtra(Intent.EXTRA_SUBJECT, subject); putExtra(Intent.EXTRA_TEXT, text) }, "Invia email"))
    }
    fun shareGeneric(ctx: Context, text: String) {
        ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, text) }, "Condividi"))
    }
}