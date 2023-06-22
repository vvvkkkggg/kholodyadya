package com.vkg.kholodyadya;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
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
import com.vkg.kholodyadya.R;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;


public class QRActivity extends AppCompatActivity {
    private ImageView qrImageView;
    private TextView qrTextView;

    private Uri imageUri;

    private BarcodeScanner barcodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

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
                                    "Fatal error while scanning barcode",
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
        APIWrapper wrapper = new APIWrapper(this.getString(R.string.test_token), this.getString(R.string.server_url));
        for (Barcode barcode : barcodes) {
            wrapper.loadBarCode(barcode);
        }
    }

    public void openMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                                    .makeText(QRActivity.this, "Cancelled..",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
}