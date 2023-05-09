package com.example.qrapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView qrImageView;
    private TextView qrTextView;

    private Uri imageUri;

    private BarcodeScanner barcodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scanButton);
        qrTextView = findViewById(R.id.qrData);
        qrImageView = findViewById(R.id.qrImage);

        BarcodeScannerOptions barcodeScannerOptions =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build();
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);

        scanButton.setOnClickListener(view -> pickImageCamera());
    }

    private void detectResultFromImage() {
        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);
            barcodeScanner.process(inputImage)
                    .addOnSuccessListener(this::extractQrCodeInfo)
                    .addOnFailureListener(e
                            -> Toast
                            .makeText(this,
                                    "Some error while barcode scanning..",
                                    Toast.LENGTH_SHORT)
                            .show());
        } catch (IOException e) {
            Toast
                    .makeText(
                            this, "Some error while get photo..", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void extractQrCodeInfo(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            qrTextView.setText(barcode.getRawValue());
        }
    }

    private void pickImageCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "PhotoTitle");
        contentValues.put(
                MediaStore.Images.Media.DESCRIPTION, "PhotoDescription");

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(), result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            detectResultFromImage();
                            qrImageView.setImageURI(imageUri);
                        } else {
                            Toast
                                    .makeText(MainActivity.this, "Cancelled..",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
}