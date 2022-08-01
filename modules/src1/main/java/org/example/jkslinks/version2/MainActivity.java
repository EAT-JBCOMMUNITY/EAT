package com.fari.jkslinks;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.telephony.emergency.EmergencyNumber;

//@EAT({"modules/testcases/jdkAll/Android/jkslinks/JKSLinks/app/src/main/java/com/fari/jkslinks#29.0.0})
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToWikipedia(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/Jang_Keun-suk"));
        startActivity(browser);
    }

    public void goToOfficial(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.princejks.com/"));
        startActivity(browser);
    }

    public void goToFacebook(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/AsiaPrinceJKS0804"));
        startActivity(browser);
    }

    public void goToInstagram(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/_asia_prince_jks/"));
        startActivity(browser);
    }

    public void goToYoutube(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/user/PrinceJKS"));
        startActivity(browser);
    }

    public void goToTwitter(View view)
    {
        Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/AsiaPrince_JKS"));
        startActivity(browser);
    }
}
