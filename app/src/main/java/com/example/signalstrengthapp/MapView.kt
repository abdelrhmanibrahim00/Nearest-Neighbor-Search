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

    // Dynamic axis ranges
    private var xMin: Int = -10
    private var xMax: Int = 10
    private var yMin: Int = -35
    private var yMax: Int = 35

    // List to hold points with labels
    var coordinates: List<Pair<String, StrengthPoint>> = emptyList()
        set(value) {
            field = value
            invalidate() // Redraw the view when data changes
        }

    // Method to set dynamic axis ranges
    fun setAxisRanges(xMin: Int, xMax: Int, yMin: Int, yMax: Int) {
        this.xMin = xMin
        this.xMax = xMax
        this.yMin = yMin
        this.yMax = yMax
        invalidate() // Redraw the view when ranges change
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)
        drawCoordinates(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        val padding = 100f
        val xRange = xMax - xMin
        val yRange = yMax - yMin
        val xStep = (width - 2 * padding) / xRange
        val yStep = (height - 2 * padding) / yRange

        // Draw vertical grid lines and X-axis labels
        for (i in xMin..xMax) {
            val x = padding + (i - xMin) * xStep
            canvas.drawLine(x, padding, x, height - padding, gridPaint)
            canvas.drawText(i.toString(), x, height - padding + 30f, axisPaint)
        }

        // Draw horizontal grid lines and Y-axis labels
        for (j in yMin..yMax) {
            val y = height - padding - (j - yMin) * yStep
            canvas.drawLine(padding, y, width - padding, y, gridPaint)
            if (j != 0) canvas.drawText(j.toString(), padding - 50f, y + 10f, axisPaint)
        }

        // Draw X and Y axes
        val centerX = padding + (0 - xMin) * xStep
        val centerY = height - padding - (0 - yMin) * yStep
        canvas.drawLine(padding, centerY, width - padding, centerY, axisPaint) // X-axis
        canvas.drawLine(centerX, padding, centerX, height - padding, axisPaint) // Y-axis
    }

    private fun drawCoordinates(canvas: Canvas) {
        val padding = 100f
        val xRange = xMax - xMin
        val yRange = yMax - yMin
        val xStep = (width - 2 * padding) / xRange
        val yStep = (height - 2 * padding) / yRange

        coordinates.forEach { (label, point) ->
            val x = padding + (point.x - xMin) * xStep
            val y = height - padding - (point.y - yMin) * yStep
            canvas.drawCircle(x, y, 10f, pointPaint)
            canvas.drawText(label, x + 15f, y, textPaint) // Label each point by list name
        }
    }
}
