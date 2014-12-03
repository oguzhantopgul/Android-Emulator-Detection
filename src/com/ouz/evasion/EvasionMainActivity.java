package com.ouz.evasion;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class EvasionMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evasion_main);
		
		TextView isEmulatorTextView = (TextView)findViewById(R.id.textView1);

		if (isEmulator(getApplicationContext()))
			isEmulatorTextView.setText("Emulator");
		else
			isEmulatorTextView.setText("Device");

	}

	public static Boolean isEmulator(Context paramContext)
	{
		Boolean isEmulator = false;

		try {
			Class SystemProperties = Class.forName("android.os.SystemProperties");
			TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
			if (getProperty(SystemProperties, "ro.secure").equalsIgnoreCase("0"))	
				isEmulator =  Boolean.valueOf(true);
			else if (getProperty(SystemProperties, "ro.kernel.qemu").equalsIgnoreCase("1"))
				isEmulator =  Boolean.valueOf(true);
			else if (Build.PRODUCT.contains("sdk"))
				isEmulator =  Boolean.valueOf(true);
			else if (Build.MODEL.contains("sdk"))
				isEmulator =  Boolean.valueOf(true);
			else if(localTelephonyManager.getSimOperatorName().equals("Android"))
				isEmulator =  Boolean.valueOf(true);
			else if(localTelephonyManager.getNetworkOperatorName().equals("Android"))
				isEmulator =  Boolean.valueOf(true);
			else
				isEmulator =  Boolean.valueOf(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isEmulator;
	}

	private static String getProperty(Class myClass, String propertyName) throws Exception {
		return (String) myClass.getMethod("get", String.class).invoke(myClass, propertyName);
	}


}
