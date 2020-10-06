package com.prongbang.scannerview

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ViewfinderView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.prongbang.scanview.addObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initLoad()
	}

	private fun initLoad() {
		Dexter.withContext(this)
				.withPermission(Manifest.permission.CAMERA)
				.withListener(object : PermissionListener {
					override fun onPermissionGranted(response: PermissionGrantedResponse) {
						startScanner()
					}

					override fun onPermissionDenied(response: PermissionDeniedResponse) {
						Toast.makeText(this@MainActivity,
								"Permission ${response.permissionName} Denied.",
								Toast.LENGTH_SHORT)
								.show()
					}

					override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?,
					                                                token: PermissionToken?) {
						token?.continuePermissionRequest()
					}
				})
				.check()
	}

	private fun startScanner() {
		scannerView?.apply {
			addObserver(this)
			onResultListener {
				Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT)
						.show()
			}
		}
	}
}