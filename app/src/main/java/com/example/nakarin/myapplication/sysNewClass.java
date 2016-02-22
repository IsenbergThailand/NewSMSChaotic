package com.example.nakarin.myapplication;

/**
 * Created by Nakarin on 2/22/2016.
 */

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 *
 * @author Nakarin
 */
public class sysNewClass {
    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }
        // System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }
    public static String AsciiToBinary(String asciiString){

        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            // binary.append(' ');
        }
        return binary.toString();
    }

    String[] genXascii( String s ) throws UnsupportedEncodingException{
        String key_serect = s;
// System.out.println("key 16 Chars: " + key_serect);
        String[] tmp1 = new String[10];
        String[] tmp2 = new String[10];
        String[] New1 = new String[10];
        int size,new_size = 0;
        int index,IndexEnd = 0;
        int index2 = 0;
        int length = 6;
        for(index=0;index<10;index++){
            IndexEnd=index2+length;
            if(IndexEnd>16){IndexEnd=16;}
            tmp1[index]=key_serect.substring(index2,IndexEnd);
            size=tmp1[index].length();
            if(size<6){
                new_size = 6-size;
                tmp2[index]=key_serect.substring(0,new_size);
                New1[index]=tmp1[index]+tmp2[index];
                index2=new_size;
            }else{
                New1[index]=tmp1[index];
                index2=IndexEnd;
            }
        }
        return New1;
    }
    String[] genYascii( String s ) throws UnsupportedEncodingException{
        String key_serect = s;
        key_serect =new StringBuilder(key_serect).reverse().toString();   // reverse
// System.out.println("key 16 Chars: " + key_serect);
        String[] tmp1 = new String[10];
        String[] tmp2 = new String[10];
        String[] New1 = new String[10];
        int size,new_size = 0;
        int index,IndexEnd = 0;
        int index2 = 0;
        int length = 6;
        for(index=0;index<10;index++){
            IndexEnd=index2+length;
            if(IndexEnd>16){IndexEnd=16;}
            tmp1[index]=key_serect.substring(index2,IndexEnd);
            size=tmp1[index].length();
            if(size<6){
                new_size = 6-size;
                tmp2[index]=key_serect.substring(0,new_size);
                New1[index]=tmp1[index]+tmp2[index];
                index2=new_size;
            }else{
                New1[index]=tmp1[index];
                index2=IndexEnd;
            }
        }
        return New1;
    }


    double[] KeyPlane( double C ,double D,double yIn) throws UnsupportedEncodingException{
        double[] toReturn = new double[12];
        double[] yyy = new double[14];

        Double C_cof = C; Double D_cof = D;

//    System.out.println("Cof1 : " + C + " Cof2 "+D);
        yyy[0]=yIn;
        yyy[1]=yIn;

        int k,j;
        for( k= 2; k < 12+2; k++){
            double lo=C_cof*yyy[k-2];
            double li=D_cof*yyy[k-1];
            double lp = li+lo;
            double r = ((lp+1) % 2);
            if(r<0){r +=2;}
            yyy[k]  = r-1;
            toReturn[k-2]=yyy[k];
        }
        return toReturn;
    }

    public static String hexToBinary(String hex) {
        String tmp1 = new BigInteger(hex, 16).toString(2);
        return tmp1;
    }

    public static double sum(double...values) {
        double result = 0;
        for (double value:values)
            result += value;
        return result;
    }
    public static String convBinToDec3( String s ){
        String str  =new StringBuilder(s).reverse().toString();   // reverse
        String mySub1 = str;
        int[] newIntInput=new int[s.length()];
        int indexOfbit=s.length();
        double[] valueOfBit=new double[s.length()];
        double[] decInt=new double[s.length()];
        double sum = 0;

        for ( indexOfbit=0;indexOfbit<=47;indexOfbit++){
            newIntInput[indexOfbit] = Integer.parseInt(str.substring(indexOfbit,indexOfbit+1));
        }
        for ( indexOfbit=1;indexOfbit<=5;indexOfbit++){
            valueOfBit[indexOfbit] = Math.pow(2,5-indexOfbit);
            decInt[indexOfbit]=newIntInput[indexOfbit]*valueOfBit[indexOfbit];
        }

        for ( indexOfbit=6;indexOfbit<=47;indexOfbit++){
            valueOfBit[indexOfbit] = Math.pow(2,-indexOfbit);
            decInt[indexOfbit]=newIntInput[indexOfbit]*valueOfBit[indexOfbit];
        }
        double sum4 = sum(decInt);

        if(newIntInput[0]==0){    sum4=sum4*1;
        }else{ sum4=sum4*(-1); }
        String total2 = String.valueOf(sum4);

        return total2;
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




}

