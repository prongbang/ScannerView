package com.prongbang.scanview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import me.dm7.barcodescanner.core.ViewFinderView

class ScannerFinderView : ViewFinderView {

	constructor(context: Context) : super(context) {
		init()
	}

	constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
		init()
	}

	private fun init() {
		val textPixelSize = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP,
				TRADE_MARK_TEXT_SIZE_SP.toFloat(),
				resources.displayMetrics
		)
		PAINT.apply {
			color = Color.WHITE
			isAntiAlias = true
			textSize = textPixelSize
		}
		setSquareViewFinder(true)
		setBorderColor(Color.WHITE)
		setBorderCornerRounded(true)
		setBorderCornerRadius(30)
		setBorderStrokeWidth(4)
		setBorderLineLength(45)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		drawTradeMark(canvas)
	}

	private fun drawTradeMark(canvas: Canvas) {
		val framingRect = framingRect
		val tradeMarkTop: Float
		val tradeMarkLeft: Float
		if (framingRect != null) {
			tradeMarkTop = framingRect.bottom + PAINT.textSize + 10
			tradeMarkLeft = framingRect.left.toFloat()
		} else {
			tradeMarkTop = 10f
			tradeMarkLeft = canvas.height - PAINT.textSize - 10
		}
		canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT)
	}

	companion object {
		private const val TRADE_MARK_TEXT = ""
		private const val TRADE_MARK_TEXT_SIZE_SP = 40
		private val PAINT = Paint()
	}
}