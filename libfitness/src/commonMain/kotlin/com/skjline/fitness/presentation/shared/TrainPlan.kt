package com.skjline.fitness.presentation.shared

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path

fun List<Pair<Float, Float>>.toBitmap(
    color: Color,
    canvasWidth: Float = 0f,
    canvasHeight: Float = 0f
): ImageBitmap {
    val size = Size(canvasWidth, canvasHeight) // simple example of 400px by 400px image
    val bitmap = ImageBitmap(
        size.width.toInt(),
        size.height.toInt(),
    )

    val canvas = Canvas(bitmap)

    val wRate = canvasWidth / last().first

    var index = 0
    while (index < this.size - 1) {
        var start: Pair<Float, Float> = this[index]
        var end: Pair<Float, Float> = this[index + 1]

        val path = Path().apply {
            moveTo(start.first * wRate, canvasHeight)
            lineTo(start.first * wRate, canvasHeight - start.second)
            lineTo(end.first * wRate, canvasHeight - end.second)
            lineTo(end.first * wRate, canvasHeight)
            lineTo(start.first * wRate, canvasHeight)
        }

        canvas.drawPath(path, Paint().apply {
            this.color = color
        })

        index = index.inc()
        index = index.inc()
    }

    return bitmap
}

fun List<Float>.toHalfPieBitmap(
    canvasSize: Float = 800f,
    graphWidth: Float = 96f,
    colorlist: List<Color> = listOf(Color.Black)
): ImageBitmap {
    val size = Size(canvasSize, canvasSize / 2)
    val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())

    val canvas = Canvas(bitmap)
    val rect = Rect(graphWidth / 2, graphWidth / 2, size.width - graphWidth / 2, size.height * 2)

    val offset = 180f
    val start = 0f
    val end = -1 * (this[0] * offset)
    drawPieGraph(canvas, rect, start, end, colorlist[0], graphWidth)

    forEachIndexed { index, data ->
        val start = -1 * (this[index] * offset)
        val end = -1f * (if (index == (this.size - 1)) {
            offset
        } else {
            (this[index + 1] * offset)
        } + start)
        val color = colorlist[(index + 1) % colorlist.size]
        drawPieGraph(canvas, rect, start, end, color, graphWidth)
    }

    return bitmap
}

fun drawPieGraph(
    canvas: Canvas,
    rect: Rect,
    start: Float,
    end: Float,
    c: Color,
    graphWidth: Float
) {

    val paint = androidx.compose.ui.graphics.Paint().apply {
        color = c
        style = PaintingStyle.Stroke
        strokeWidth = graphWidth
    }

    canvas.drawArc(
        rect = rect,
        startAngle = start,
        sweepAngle = end,
        useCenter = false,
        paint = paint,
    )
}