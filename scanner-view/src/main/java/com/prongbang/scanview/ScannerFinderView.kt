package com.prongbang.scanview

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import me.dm7.barcodescanner.core.DisplayUtils
import me.dm7.barcodescanner.core.ViewFinderView

class ScannerFinderView : ViewFinderView {

	private var mFramingRect: Rect? = null

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
		setBorderCornerRadius(4)
		setBorderStrokeWidth(4)
		setBorderLineLength(60)
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

	override fun getFramingRect(): Rect? {
		return mFramingRect
	}

	override fun updateFramingRect() {
		val viewResolution = Point(width, height)
		var width: Int
		var height: Int
		val orientation = DisplayUtils.getScreenOrientation(context)

		if (mSquareViewFinder) {
			if (orientation != Configuration.ORIENTATION_PORTRAIT) {
				height = getHeight() - 50
				width = height
			} else {
				width = getWidth() - 50
				height = width
			}
		} else {
			if (orientation != Configuration.ORIENTATION_PORTRAIT) {
				height = getHeight() - 50
				width = (1.4f * height).toInt()
			} else {
				width = getWidth() - 50
				height = width
			}
		}

		if (width > getWidth()) {
			width = getWidth() - 50
		}

		if (height > getHeight()) {
			height = getHeight() - 50
		}

		val leftOffset = (viewResolution.x - width) / 2
		val topOffset = (viewResolution.y - height) / 2
		mFramingRect = Rect(leftOffset + 0, topOffset + 0, leftOffset + width - 0,
				topOffset + height - 0)
	}

	companion object {
		private const val TRADE_MARK_TEXT = ""
		private const val TRADE_MARK_TEXT_SIZE_SP = 4
		private val PAINT = Paint()
	}
}