/**
 * @file DetailsActivity.java
 * @brief User Details.
 * @date 01.08.2018
 * @autor M.Gusev
 */

package com.example.john.users;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/** @class DetailsActivity
 *  @brief User Details. */
public class DetailsActivity extends Activity {
    private UserItem user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        user = (UserItem) this.getIntent().getSerializableExtra("user");

        if (user != null) {
            ImageView picUser = (ImageView) findViewById(R.id.pictUser);
            new ImageDownloader(picUser).execute(user.getPicLargeUrl());

            TextView nameUser = (TextView) findViewById(R.id.nameUser);
            TextView natUser = (TextView) findViewById(R.id.natUser);
            TextView locUser = (TextView) findViewById(R.id.locUser);
            TextView phoneUser = (TextView) findViewById(R.id.phoneUser);
            TextView emailUser = (TextView) findViewById(R.id.emailUser);

            nameUser.setText(user.getNameLast() + ", " + user.getNameFirst());
            natUser.setText(user.getNation());
            locUser.setText(user.getLocation());
            phoneUser.setText(user.getPhone());
            emailUser.setText(user.getEmail());
        }

    }

//    @TargetApi(Build.VERSION_CODES.M)
    public void onClickPhone(View v) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getPhone()));
//        if (checkSelfPermission(Manifest.permission.CALL_PHONE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
        startActivity(dialIntent);
    }

    public void onClickEmail(View v) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { user.getEmail() });
        startActivity(Intent.createChooser(shareIntent, "Sharing something."));
    }
}

