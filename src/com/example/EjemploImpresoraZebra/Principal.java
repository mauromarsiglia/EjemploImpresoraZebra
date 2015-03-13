package com.example.EjemploImpresoraZebra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

public class Principal extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impresion);
	}
	public void Imprimir(View v){
		performTest();
	}
	 public void performTest() {
	        new Thread(new Runnable() {
	            @Override
				public void run() {
	                Looper.prepare();
	                sendFile();
	                Looper.loop();
	                Looper.myLooper().quit();
	            }
	        }).start();

	    }

	    private void sendFile() {
	        Connection connection = null;
	        connection = new BluetoothConnection("00:22:58:3B:C7:97");
	        try {
	            connection.open();
	            ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
	            testSendFile(printer);
	            connection.close();
	        } catch (ConnectionException e) {
	        } catch (ZebraPrinterLanguageUnknownException e) {}
	    }

	    private void testSendFile(ZebraPrinter printer) {
	        try {
	            //File filepath = getFileStreamPath("TEST.LBL");
	            //createDemoFile(printer, "TEST.LBL");
	        	InputStream i=getAssets().open("Modelo1.lbl");
	        	File filepath=new File("Modelo1.lbl");
	        	URL fileURL = getClass().getClassLoader().getResource("Modelo1.lbl");
	        	  String fileName = fileURL.getFile();
	        	  String filePath = fileURL.getPath();
	        	String ap=filepath.getAbsolutePath();
	        	if(filepath.exists())
	        		printer.sendFileContents(filepath.getAbsolutePath());
	     
	        } catch (Exception e) {}
	    }

	    private void createDemoFile(ZebraPrinter printer, String fileName) throws IOException {

	        FileOutputStream os = this.openFileOutput(fileName, Context.MODE_PRIVATE);
	        String impresion= "! 0 200 200 639 1\r\n"+
				"PW 830\r\n"+
				"TONE 0\r\n"+
				"SPEED 0\r\n"+
				"ON-FEED IGNORE\r\n"+
				"NO-PACE\r\n"+
				"BAR-SENSE\r\n"+
				"T 7 0 315 144 Var_bodega\r\n"+
				"T 7 0 542 241 Var_fechall\r\n"+
				"T 7 0 529 137 Var_medidas\r\n"+
				"T 7 0 313 243 Var_contenedor\r\n"+
				"T 7 0 104 240 Var_huacal\r\n"+
				"T 0 3 544 214 F.LLEGADA:\r\n"+
				"T 7 0 104 144 Var_lotesap\r\n"+
				"T 0 3 531 110 MEDIDAS:\r\n"+
				"T 0 3 315 215 CONTENEDOR:\r\n"+
				"T 0 3 315 112 BODEGA:\r\n"+
				"T 0 3 105 212 HUACAL:\r\n"+
				"T 7 0 104 59 30021832 - GRIS 4MM 1.829*3.302 PK\r\n"+
				"T 0 3 104 114 LOTE SAP:\r\n"+
				"T 0 3 102 26 DESCRIPCION DEL MATERIAL\r\n"+
				"BT 7 0 3\r\n"+
				"B 128 2 30 30 206 323 ABCDEF12345\r\n"+
				"BT 7 0 3\r\n"+
				"B 128 2 30 30 220 417 ABCDEF123456\r\n"+
				"PRINT\r\n";
	        byte[] configLabel = null;
            String cpclConfigLabel = impresion;
            configLabel = cpclConfigLabel.getBytes();
	        os.write(configLabel);
	        os.flush();
	        os.close();
	    }

}
