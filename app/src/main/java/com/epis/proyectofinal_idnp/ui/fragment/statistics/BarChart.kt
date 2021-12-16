package com.epis.proyectofinal_idnp.ui.fragment.statistics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class BarChart(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Data
    private var datalabel: MutableList<String> = ArrayList()
    private var data: MutableList<Int> = ArrayList()
    private var color = Color.rgb(0, 0, 0)
    private var labelColor = Color.rgb(0, 0, 0)
    // Lienzo para dibujar
    private lateinit var bitmap: Bitmap
    private lateinit var canvasGraphicBar: Canvas
    // Pincel
    private val brush = Paint()
    // Limites auxiliares
    private var maxAxisY = 0
    private var maxAxisX = 0
    private var widthGraphic = 0
    private var heightGraphic = 0
    private var padding = 50

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Inicializamos el bitmap
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        // Asignamos el bitmap a nuestro canvas
        canvasGraphicBar = Canvas(bitmap)
        // Obtenemos el espacio para graficar
        widthGraphic = w - padding
        heightGraphic = h - padding
    }

    override fun onDraw(canvas: Canvas) {
        // Asignamos el bitmap y el pincel
        canvas.drawBitmap(bitmap, 0f, 0f, brush)
        // Dibujamos los ejes X e Y
        drawAxis()
    }

    fun drawBarChart() {
        if (datalabel.size != data.size || datalabel.size == 0) return
        // Limpiamos el canvas antes de dibujar
        clearCanvas()
        // Calculamos unidad equivalente
        val unitX = kotlin.math.ceil((widthGraphic / (maxAxisX + 2)).toDouble())
        val unitY = kotlin.math.ceil((heightGraphic / maxAxisY).toDouble())
        // Dibujamos par por par
        var index = 1
        for(i in 0 until datalabel.size) {
            // Esquina superior izquierda
            val x1 = (index * unitX).toFloat()
            val y1 = (heightGraphic - (data[i] * unitY)).toFloat()
            // Esquina superior derecha
            val x2 = (x1 + unitX).toFloat()
            val y2 = heightGraphic.toFloat()
            // Definimos el estilo del pincel para la barra
            brush.style = Paint.Style.FILL
            brush.color = color
            // Graficamos la barra
            canvasGraphicBar.drawRect(x1 + padding.toFloat(), y1, x2 + (padding - 10).toFloat(), y2, brush)
            // Definimos el estilo del pincel para los labels
            brush.color = labelColor
            brush.textSize = 18F
            brush.textAlign = Paint.Align.LEFT
            // Graficamos los labels
            canvasGraphicBar.drawText(data[i].toString(), 0F, y1 + 20F, brush)
            canvasGraphicBar.drawText(datalabel[i], x1 + (unitX - 10).toFloat(), (heightGraphic + (padding / 2)).toFloat(), brush)
            index += 1
        }
    }

    private fun clearCanvas() {
        // Limpia el canvas
        canvasGraphicBar.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawAxis()
    }

    private fun drawAxis() {
        // Dibuja los ejes X e Y
        brush.color = labelColor
        brush.style = Paint.Style.STROKE
        brush.strokeWidth = 2F
        val p = Path()
        // Eje Y
        p.moveTo(padding.toFloat(), 0F)
        p.lineTo(padding.toFloat(), heightGraphic.toFloat())
        p.moveTo(padding.toFloat() - 20, 20F)
        p.lineTo(padding.toFloat(), 0F)
        p.moveTo(padding.toFloat(), 0F)
        p.lineTo(padding.toFloat() + 20F, 20F)
        // Eje X
        p.moveTo(padding.toFloat(), heightGraphic.toFloat())
        p.lineTo(width.toFloat(), heightGraphic.toFloat())
        p.moveTo((width - 20).toFloat(), (heightGraphic - 20).toFloat())
        p.lineTo(width.toFloat(), heightGraphic.toFloat())
        p.moveTo(width.toFloat(), heightGraphic.toFloat())
        p.lineTo((width - 20).toFloat(), (heightGraphic + 20).toFloat())
        // Dibujamos los ejes X e Y en el canvas
        canvasGraphicBar.drawPath(p, brush)
    }

    fun addBar(bar: BarChartItem) {
        // Agrega nuevos valores
        if (bar.y < 0) throw Exception("No se admiten valores negativos.")
        datalabel.add(bar.label)
        data.add(bar.y)
        maxAxisX = data.size
        if (bar.y > maxAxisY)
            maxAxisY = bar.y
    }

    fun setColor(colorBars: Int) {
        // Cambia de color
        color = colorBars
    }

    fun setLabelColor(_labelColor: Int) {
        // Cambia de color
        labelColor = _labelColor
    }

    fun resetData() {
        // Reinicia la data
        datalabel.clear()
        data.clear()
        maxAxisX = 0
        maxAxisY = 0
        clearCanvas()
    }

}