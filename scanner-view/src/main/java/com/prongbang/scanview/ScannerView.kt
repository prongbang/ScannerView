package com.prongbang.scanview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView

fun ComponentActivity?.addObserver(observer: LifecycleObserver) {
	this?.lifecycle?.addObserver(observer)
}

class ScannerView @JvmOverloads constructor(
		context: Context,
		attrs: AttributeSet? = null,
		defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ZXingScannerView.ResultHandler, LifecycleObserver {

	init {
		initView(context)
	}

	private var onResult: (result: String) -> Unit = {}

	private lateinit var scannerView: ZXingScannerView

	private fun initView(context: Context?) {
		scannerView = object : ZXingScannerView(context) {
			override fun createViewFinderView(context: Context): IViewFinder =
					ScannerFinderView(context)
		}
		scannerView.apply {
			setFormats(listOf(
					BarcodeFormat.AZTEC,
					BarcodeFormat.CODABAR,
					BarcodeFormat.CODE_39,
					BarcodeFormat.CODE_93,
					BarcodeFormat.CODE_128,
					BarcodeFormat.DATA_MATRIX,
					BarcodeFormat.EAN_8,
					BarcodeFormat.EAN_13,
					BarcodeFormat.ITF,
					BarcodeFormat.MAXICODE,
					BarcodeFormat.PDF_417,
					BarcodeFormat.QR_CODE,
					BarcodeFormat.RSS_14,
					BarcodeFormat.RSS_EXPANDED,
					BarcodeFormat.UPC_A,
					BarcodeFormat.UPC_E,
					BarcodeFormat.UPC_EAN_EXTENSION
			))
			setAspectTolerance(0.5f)
		}
		addView(scannerView)
	}

	override fun handleResult(result: Result?) {
		onResult.invoke(result?.text ?: "")
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	fun showScannerView() {
		scannerView.apply {
			setResultHandler(this@ScannerView)
			startCamera()
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	fun stopScannerView() {
		scannerView.stopCamera()
	}

	fun resumeScannerView() {
		scannerView.resumeCameraPreview(this)
	}

	fun onResultListener(onResult: (result: String) -> Unit) {
		this.onResult = onResult
	}
}