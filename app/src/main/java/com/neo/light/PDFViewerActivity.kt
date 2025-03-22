package com.neo.light

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import java.io.File
import java.io.IOException
import java.io.InputStream


class PDFViewerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfviewer)
        val pdfName = intent.getStringExtra(PDF_NAME)?.replace(" ","_")

        startActivity(PdfViewerActivity.launchPdfFromPath(
            context = this,
            path = "$pdfName.pdf",
            pdfTitle = intent.getStringExtra(PDF_NAME),
            saveTo = saveTo.ASK_EVERYTIME,
            fromAssets = true
        ))
        finish()
    }

    companion object {
        const val PDF_NAME = "pdf_name"
    }
}

@Composable
fun PDFViewerScreen(pdfName: String?) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var currentPageIndex by remember { mutableStateOf(0) }
    val pdfRenderer = remember { mutableStateOf<PdfRenderer?>(null) }
    val assetManager = LocalContext.current.assets

    var zoomFactor by remember { mutableStateOf(1f) }

    LaunchedEffect(Unit) {
        try {
            val inputStream: InputStream = assetManager.open("${pdfName}.pdf") // PDF file name in assets
            val fileDescriptor = getParcelFileDescriptor(inputStream)
            pdfRenderer.value = PdfRenderer(fileDescriptor)
            renderPage(currentPageIndex, pdfRenderer.value!!, zoomFactor, bitmap = { bitmap = it })
        }  catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context,"No PDF found",Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        bitmap?.let {
            // Enable scrolling and zooming
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Optional padding to make zoom work more easily
            ) {
                // Scrollable container to enable scrolling both vertically and horizontally
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()) // Horizontal scrolling
                        .verticalScroll(rememberScrollState())   // Vertical scrolling
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "PDF Page",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }

        // Controls for navigation (next page)
        Button(
            onClick = {
                if (pdfRenderer.value != null && currentPageIndex < (pdfRenderer.value?.pageCount ?: 0) - 1) {
                    currentPageIndex++
                    renderPage(currentPageIndex, pdfRenderer.value!!, zoomFactor) { bitmap = it }
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text(text = "Next Page")
        }

        // Controls for zoom reset
        Button(
            onClick = {
                zoomFactor = (zoomFactor*1.2).toFloat()
                renderPage(currentPageIndex, pdfRenderer.value!!, zoomFactor) { bitmap = it }
            },
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text(text = "Reset Zoom")
        }
    }
}

fun getParcelFileDescriptor(inputStream: InputStream): ParcelFileDescriptor {
    val file = File.createTempFile("pdf", "tmp")
    file.deleteOnExit()

    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }

    return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
}

fun renderPage(index: Int, pdfRenderer: PdfRenderer, zoomFactor: Float, bitmap: (Bitmap) -> Unit) {
    if (index < pdfRenderer.pageCount) {
        val page = pdfRenderer.openPage(index)

        // Scale the width and height of the page by zoom factor
        val scaledWidth = (page.width * zoomFactor).toInt()
        val scaledHeight = (page.height * zoomFactor).toInt()

        val bmp = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rect = Rect(0, 0, canvas.width, canvas.height)
        canvas.drawBitmap(bmp, null, rect, null)

        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        bitmap(bmp)

        page.close()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPDFViewer() {
    PDFViewerScreen("")
}




