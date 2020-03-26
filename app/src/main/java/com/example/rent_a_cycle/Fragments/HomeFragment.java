package com.example.rent_a_cycle.Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.rent_a_cycle.BTLE_Scanner;
import com.example.rent_a_cycle.PermissionsChecker;
import com.example.rent_a_cycle.R;
import com.example.rent_a_cycle.RidingActivity;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private PermissionsChecker checker;
    private BTLE_Scanner btle_scanner;
    public static final String QR_code = "ABCDEFG";
    private boolean resultOK = false;

    private CodeScanner mCodeScanner;

    @BindView(R.id.scanner_view)
    CodeScannerView scanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        checker = new PermissionsChecker(getActivity());
        btle_scanner = new BTLE_Scanner(getActivity(), 10000, 10);
        checker.getPermissions();

        if (checker.isAllGranted() && !btle_scanner.getmBluetoothAdapter().isEnabled()) {
            btle_scanner.BTon();
        }
        mCodeScanner = new CodeScanner(getContext(), scanner);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.getText().equals(QR_code) && !resultOK){
                            if(btle_scanner.getmBluetoothAdapter().isEnabled()){
                                startActivity(new Intent(getContext(), RidingActivity.class));
                                resultOK = true;
                            } else {
                                btle_scanner.BTon();
                            }
                        }
                    }
                });
            }
        });

        return view;
    }

    @OnClick(R.id.scanner_view)
    void scannerOnClick() {
        if (checker.isAllGranted()) {
            mCodeScanner.startPreview();
        } else {
            checker.getPermissions();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resultOK = false;
        if (checker.isAllGranted()) {
            mCodeScanner.startPreview();
        } else {
            checker.getPermissions();
        }
    }

    @Override
    public void onPause() {
        if (checker.isAllGranted()) {
            mCodeScanner.startPreview();
        } else {
            checker.getPermissions();
        }
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        checker.setAllGranted(true);
        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            checker.setAllGranted(false);
                            return;
                        }
                    }
                }
            }
        }
        if (checker.isAllGranted()) {
            mCodeScanner.startPreview();
        } else {
            checker.getPermissions();
        }
    }
}
