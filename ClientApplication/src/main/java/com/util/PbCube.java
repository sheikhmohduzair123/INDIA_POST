package com.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
// import com.sun.;

public interface PbCube extends Library {
	PbCube cube = (PbCube) Native.load(("PrinterandScaleAPI_x64"),
			PbCube.class);

	int ConnectPrinterEx(int nInterface, String szPortName, int nBaudRate, int nDataBits, int nParity, int nStopBits);

	int CheckStatus();

	int GetScaleWeight(byte[] pszWeight);

	boolean SetScaleUnit();

	boolean DisconnectPrinter();
}
