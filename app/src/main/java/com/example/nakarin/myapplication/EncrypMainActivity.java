package com.example.nakarin.myapplication;

import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.telephony.SmsManager;  // sms-manager
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import java.io.UnsupportedEncodingException;
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
			 String recNumString = recNum.getText().toString(); 
			 String msgContentString = msgContent.getText().toString(); 
			 String cc1=passwd.getText().toString();	 
			 EncrypMainActivity str = new EncrypMainActivity(); 	 
			 try {
				 String init="0000";
				 String inin=init+msgContentString;
				 byte[] bytes_in = inin.getBytes("TIS-620");
				 byte[] bytes_pass = cc1.getBytes("TIS-620");
				 String bin_in = str.toBinary(bytes_in);  
				 String bin_pass = str.toBinary(bytes_pass);   
				 String[] cof_in =str.convBinToCof(bin_pass);
		         String  ccc1 = cof_in[0];
		         String  ccc2 = cof_in[1];
				 
				 String[] ddd =str.convBinToDec2(bin_in);
				 String[] gggg =str.choticE(ddd,ccc1,ccc2);
				 StringBuilder builder5 = new StringBuilder();
				 for(String so : gggg) {
					    builder5.append(so);
					}
				 String output=builder5.toString();
				 
				 sendSMS(recNumString,output); 
				 
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

/*#####################################################
 ######################################################
 # ####################################################
 ##################  SMS Function #####################  
 # ####################################################
 #####################################################*/
	 public static void sendSMS(String recNumString, String encryptedMsg) {
		 try {

		 // get a SmsManager
		 SmsManager smsManager = SmsManager.getDefault();

		 // Message may exceed 160 characters
		 // need to divide the message into multiples
		 ArrayList<String> parts = smsManager.divideMessage(encryptedMsg);
		 smsManager.sendMultipartTextMessage(recNumString, null, parts,null, null);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 }
//################ END Function1 ######################	 

	 
/* FUNCTION 2 */
	 /*########## convert byte to binary ##########*/        
     String toBinary( byte[] bytes ){ 
{
StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
  sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
return sb.toString();
}
    }
/*############### convert binary to byte  ##########*/      
    byte[] fromBinary( String s )
{
int sLen = s.length();
byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
char c;
for( int i = 0; i < sLen; i++ )
  if( (c = s.charAt(i)) == '1' )
      toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
  else if ( c != '0' )
      throw new IllegalArgumentException();
return toReturn;
}
    
/*#################################################
##### convert binary to Dec -1<x<1  
##### example 10100001 -> 1.0100001 -> -0.256
################################################ */     
  String[] convBinToDec2( String s ){
String str = s.toString();
int len= str.length(); 
int jack = len/8; 
String[] toReturn = new String[jack];
for(int ii = 0; ii <jack; ii++){      
int mj = ii*8;        
  String mySub1 = str.substring(mj,mj+8); 
  int[] ss=new int[8];
   double[] mm=new double[8];
   double[] result=new double[8];
   double sum = 0;
ss[0] = Integer.parseInt(mySub1.substring(0,1));
for (int joker=1;joker<8;joker++){
ss[joker] = Integer.parseInt(mySub1.substring(joker,joker+1));
mm[joker] = Math.pow(2,-joker);
result[joker] = ss[joker]*mm[joker] ;
sum = sum + result[joker];
}
double summer2;
if(ss[0]==1)  
{
  summer2=sum*-1;
}else summer2=sum*1;
String temp_string=""+summer2;
int lent_g = temp_string.length();
for (int index = lent_g;index<10;index++){
temp_string=temp_string + "0";
}
toReturn[ii] = ""+temp_string;
toReturn[0]="0";
toReturn[1]="0";

}
return toReturn;

}

/*############# Encryption Chaotic ##############
##################################################*/
String[] choticE( String[] s,String v,String j )
{
int len = s.length;
String[] toReturn = new String[len];
double I;
double c1=Double.parseDouble(v);double c2=Double.parseDouble(j);
double[] yyy = new double[len];double[] inum = new double[len];
for(int kk = 2; kk <len; kk++){
inum[kk]=Double.parseDouble(s[kk]);
double lo=c2*yyy[kk-2];double li=c1*yyy[kk-1];
double lp = li+lo;       I =inum[kk]+lp;
double r = ((I+1) % 2);
if(r<0){r +=2;} 
yyy[kk]  = r-1;
toReturn[kk] = ""+yyy[kk];
  int con =toReturn[kk].length();
if(con>10){
toReturn[kk] = toReturn[kk].substring(0,10);
}
for (int in=toReturn[kk].length();in<10;in++){            //check for 10digi
  toReturn[kk] =toReturn[kk]+"0";}       
toReturn[0]="0";toReturn[1]="0";
}
return toReturn;
}    


/*############# Decryption Chaotic ##############
##################################################*/   
String[] choticD( String[] s ,String v,String j)
{
int len = s.length;
String[] toReturn = new String[len];
double c1=Double.parseDouble(v);
double c2=Double.parseDouble(j);
double I;
double[] yyy = new double[len];
double inum,inum2,inum3 ;
yyy[0] = 1;
yyy[1] = 1;     
for(int kk = 2; kk <len; kk++){
inum=Double.parseDouble(s[kk]);
inum2=Double.parseDouble(s[kk-2]);
inum3=Double.parseDouble(s[kk-1]);   
 double lo=c2*inum2;
double li=c1*inum3;
double lp = li+lo;
 I =inum-lp;
     double r = ((I+1) % 2);
if(r<0){ r +=2;  }  
yyy[kk]  = r-1;
toReturn[kk] = ""+yyy[kk];
toReturn[0]="0";
toReturn[1]="0";
}
return toReturn;
}    

/*################################################
##### convert Dec to Bin  -1<x<1  
##### example 10100001 <- 1.0100001 <- -0.256
################################################*/    
String[] convDec2ToBin( String[] s )
{
int len = s.length;
String[] toReturn = new String[len];
StringBuilder sb = new StringBuilder();
String[] yyy = new String[len];
double[] inum = new double[len];
int[] temper = new int[9];
double[] temp = new double[9];
double superman;
String s4;
for(int kk = 0; kk <len; kk++){ 
inum[kk]=Double.parseDouble(s[kk]);    
if(inum[kk]<0){temper[0]=1;superman=inum[kk]*-1;}else{temper[0]=0;superman=inum[kk];}
sb.append(temper[0]);     
    for(int hh=1;hh<9;hh++){    
temp[0]=superman;
temp[hh]=temp[hh-1]*2;    
if(temp[hh]<1)    {temper[hh]=0;}    else {temper[hh]=1;temp[hh]=temp[hh]-1;}
sb.append(temper[hh]);
}
String con = sb.toString().substring(0,8);
String con2 = sb.toString().substring(8,9);
    int number0 = Integer.parseInt(con, 2)+Integer.parseInt(con2, 2);         
String s3 = String.format("%8s",Integer.toBinaryString(number0));
s4 = s3.replace(" ","0");
    
             
yyy[kk]  =s4 ;
sb.delete(0, sb.length());     
toReturn[kk] = ""+yyy[kk];
}

return toReturn;
}    

/*################################################
##### stringToArray
################################################*/     
          String[] stringToArray(String str){ 
String newj = removeCharAt(str,0);
String newj1 = removeCharAt(newj,0);
int len= newj1.length(); 
    int jack = len/10; 
    String[] toReturn = new String[jack];
    for(int i = 0; i <jack; i++){ 
        int mj = i*10; 
      toReturn[i] = newj1.substring(mj,mj+10); 
    }         
return toReturn; 
} 
          
/*################################################
##### removeCharAt
################################################*/                 
     String removeCharAt(String s, int pos) {
return s.substring(0, pos) + s.substring(pos + 1);
}	 
     /* ##############################*/
String[] convBinToCof( String s ){
String str = s.toString();
int len= str.length(); 
int jack = len/2; 
   int[] ss=new int[8];
/// C1 ///
   String mySub1 = str.substring(0,5); 
  ss[0] = Integer.parseInt(mySub1.substring(0,1)); 
   int decimalValue = Integer.parseInt(mySub1, 2);
   String mySub2 = str.substring(6,jack); 
   int decimalValue2 = Integer.parseInt(mySub2, 2);
   int c1;
   if (ss[0]==0) { c1=-decimalValue;} else { c1=decimalValue;}
/// C2 /// 
   String mySub3 = str.substring(jack+1,jack+6); 
   //int[] sss=new int[8];
   ss[1] = Integer.parseInt(mySub3.substring(0,1)); 
   int decimalValue3 = Integer.parseInt(mySub3, 2);
   String mySub4 = str.substring(jack+7,len); 
   int decimalValue4 = Integer.parseInt(mySub4, 2);
   int c2;
   if (ss[1]==0) { c2=-decimalValue3;} else { c2=decimalValue3;}        
   if ((c1<1)&&(c1>-1)){
       c1=1/c1;
   }      
String toReturn = ""+c1+"."+decimalValue2;
String toReturn1 = ""+c2+"."+decimalValue4;

return new String[] {toReturn, toReturn1};
//return toReturn;
}	 
	 
} // end class
