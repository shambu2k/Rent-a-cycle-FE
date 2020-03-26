package com.example.rent_a_cycle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.List;

public class BTLE_Scanner {
    private Activity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private boolean mScanning;
    private Handler mHandler;
    private long scanPeriod;
    private int signalStrength;
    private static final String TAG = "BTLE_Scanner";

    private ScanCallback mLeScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    public BTLE_Scanner(Activity mActivity, long scanPeriod, int signalStrength) {
        this.mActivity = mActivity;
        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        mHandler = new Handler();

        final BluetoothManager mBluetoothManager =
                (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }

    public boolean isScanning() {
        return mScanning;
    }

    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public void BTon(){
        if(mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()){
            Intent enableBTintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(enableBTintent, 37);
        }
    }

    public void BToff(){
        if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
            Log.d(TAG, "Disabled, bluetooth state: OFF");
        } else {

        }
    }

    public void startBTLEscan(){
        if(!mBluetoothAdapter.isEnabled()){
            BTon();
            scanDevice(true);
        } else {
            scanDevice(true);
        }

    }

    public void stopBTLEscan(){
        scanDevice(false);
    }

    private void scanDevice(final boolean enable){
        if(enable && !mScanning){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothLeScanner.stopScan(mLeScanCallback);
                }
            }, scanPeriod);

            mScanning = true;
            mBluetoothLeScanner.startScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothLeScanner.stopScan(mLeScanCallback);
        }
    }
}
