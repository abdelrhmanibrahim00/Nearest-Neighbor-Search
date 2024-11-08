package com.example.signalstrengthapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

// Data class to represent a point with x, y coordinates and an ID
data class StrengthPoint(val id: Int, val x: Int, val y: Int)

class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paint for points
    private val pointPaint = Paint().apply {
        color = Color.BLUE // Change color if needed
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // Paint for labels
    private val textPaint = Paint().apply {
        color = Color.DKGRAY // Change color if needed
        textSize = 32f
        isAntiAlias = true
    }

    // Paint for grid lines
    private val gridPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 2f
    }

    // Paint for axis lines
    private val axisPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
        textSize = 30f
        isAntiAlias = true
    }

    // List to hold points with labels
    var coordinates: List<Pair<String, StrengthPoint>> = emptyList()
        set(value) {
            field = value
            invalidate() // Redraw the view when data changes
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)
        drawCoordinates(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        val padding = 100f
        val xRange = 20
        val yRange = 70
        val xStep = (width - 2 * padding) / xRange
        val yStep = (height - 2 * padding) / yRange

        for (i in -10..10) {
            val x = padding + (i + 10) * xStep
            canvas.drawLine(x, padding, x, height - padding, gridPaint)
            canvas.drawText(i.toString(), x, height - padding + 30f, axisPaint)
        }

        for (j in -35..35 step 5) {
            val y = height - padding - (j + 35) * yStep
            canvas.drawLine(padding, y, width - padding, y, gridPaint)
            if (j != 0) canvas.drawText(j.toString(), padding - 50f, y + 10f, axisPaint)
        }

        canvas.drawLine(padding, height / 2f, width - padding, height / 2f, axisPaint)
        canvas.drawLine(width / 2f, padding, width / 2f, height - padding, axisPaint)
    }

    private fun drawCoordinates(canvas: Canvas) {
        val padding = 100f
        val xStep = (width - 2 * padding) / 20
        val yStep = (height - 2 * padding) / 70

        coordinates.forEach { (label, point) ->
            val x = padding + (point.x + 10) * xStep
            val y = height - padding - (point.y + 35) * yStep
            canvas.drawCircle(x, y, 10f, pointPaint)
            canvas.drawText(label, x + 15f, y, textPaint) // Label each point by list name
        }
    }
}
