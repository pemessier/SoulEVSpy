package org.hexpresso.soulevspy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hexpresso.soulevspy.fragment.AboutFragment;

/**
 * Created by pemessier on 2015-10-05.
 */
public class AboutActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AboutFragment())
                .commit();
    }
}