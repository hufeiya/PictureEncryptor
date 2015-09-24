package com.hufeiya.example;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hufeiya.utils.Encryptor;

public class MainActivity extends AppCompatActivity {

    private Button encryptButton;
    private Button decryptButton;
    private ImageView image;
    private Drawable drawable;
    private byte[] encryptedDrawableBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptedDrawableBytes = Encryptor.encrypt(drawable);
                image.setImageDrawable(null);
            }
        });
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable result = Encryptor.decrypt(encryptedDrawableBytes);
                image.setImageDrawable(result);
            }
        });

    }
    private void init(){
        encryptButton = (Button)findViewById(R.id.encrypt);
        decryptButton = (Button)findViewById(R.id.decrypt);
        image = (ImageView)findViewById(R.id.image);
        drawable = getResources().getDrawable(R.drawable.example);
        image.setImageDrawable(drawable);
    }
}
