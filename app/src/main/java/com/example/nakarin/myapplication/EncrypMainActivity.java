package com.example.nakarin.myapplication;

import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.telephony.SmsManager;  // sms-manager
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;

public class EncrypMainActivity extends Activity {
/* pull up user interface data */
	 EditText recNum;
	 EditText msgContent;
	 EditText passwd; 
	 Button send;
	 Button cancel;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sender);
		
/* initial parameter */		
		recNum = (EditText) findViewById(R.id.editText1); 
	    passwd = (EditText) findViewById(R.id.editText2); 
		msgContent = (EditText) findViewById(R.id.editText3); 
		send = (Button) findViewById(R.id.button1); 
		cancel = (Button) findViewById(R.id.button2); 
		
/*  Action of button */
	   cancel.setOnClickListener(new View.OnClickListener() { 
			 public void onClick(View v) { 
			 finish(); 
			 } 
	    }); 		// end cancel action.
		
	   send.setOnClickListener(new View.OnClickListener() { 
			 public void onClick(View v) {
			 String recNumString = recNum.getText().toString();  // phone-number
			 String msgContentString = msgContent.getText().toString();  // sms-text
			 String key_serect =passwd.getText().toString();	   // password
			 EncrypMainActivity str = new EncrypMainActivity(); 	 
			 try {
				 sysNewClass sysSTR = new sysNewClass();
				 String binKey =sysNewClass.AsciiToBinary(key_serect);
				 // initial value by sum of Key //
				 double[] beginIn=new double[binKey.length()];
				 for ( int i=0;i<=(binKey.length())-1;i++){
					 beginIn[i] = Integer.parseInt(binKey.substring(i,i+1));
				 }
				 double sum5 = (sysNewClass.sum(beginIn))/16;
				 int lenSMStext = msgContentString.length();

				 String[] aX = new String[lenSMStext];
				 String[] aY = new String[lenSMStext];
				 String[] cofX = new String[lenSMStext];
				 String[] cofY = new String[lenSMStext];

				 aX=sysSTR.genXascii(key_serect);
				 aY=sysSTR.genYascii(key_serect);

				 for(int i=0;i<lenSMStext;i++){
					 cofX[i]=sysNewClass.convBinToDec3(sysNewClass.AsciiToBinary(aX[i]));
					 cofY[i]=sysNewClass.convBinToDec3(sysNewClass.AsciiToBinary(aY[i]));
				 }

				 double[] finP = new double[lenSMStext];
				 String[] KP = new String[finP.length];

				 for(int i=0;i<lenSMStext;i++){
					 double C = Double.parseDouble(cofX[i])-sum5;
					 double D = Double.parseDouble(cofX[i])-sum5;
					 finP=sysSTR.KeyPlane(C, D, sum5);
					 KP[i] = String.valueOf(finP[i]);
				 }

				 String[] decode_bin =sysSTR.convDec2ToBin(KP);

				 StringBuilder sbf = new StringBuilder();
				 if(decode_bin.length > 0){
					 sbf.append(decode_bin[0]);
					 for(int i=1; i < decode_bin.length; i++){
						 sbf.append("").append(decode_bin[i]);  } }
				 String strKey = sbf.toString();

				 String inBin=sysNewClass.AsciiToBinary(msgContentString);
				 int[] binInArray=new int[inBin.length()];
				 int[] binKEYArray=new int[strKey.length()];
				 int[] binOutputArray=new int[strKey.length()];
				 int[] binCipArray=new int[strKey.length()];
				 int[] binPlainArray=new int[strKey.length()];
				 for (int i=0;i<inBin.length();i++){
					 binInArray[i] = Integer.parseInt(inBin.substring(i,i+1));
					 binKEYArray[i] = Integer.parseInt(strKey.substring(i,i+1));
					 binOutputArray[i]=binInArray[i] ^binKEYArray[i] ;

				 }

				 StringBuilder sb = new StringBuilder(binOutputArray.length);
				 for (int i : binOutputArray) {
					 sb.append(i);
				 }
				 String s = sb.toString();
				 String hexString = new BigInteger(s, 2).toString(16);

				 
				 sendSMS(recNumString,hexString);
				 
				 finish(); 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 
			 
		    // sendSMS(recNumString,msgContentString); 
			 
			 finish(); 
			} 
			 }); 
	   
	   	   
	} // end onCreate
	 
} // end class
