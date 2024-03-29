package com.thecyberian.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        linearLayout = findViewById(R.id.linearLayout);

        Intent intent = getIntent();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", intent.getStringExtra("username"));
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0){
                    for(ParseObject obj:objects){
                        ParseFile file = (ParseFile) obj.get("image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e == null && data != null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));

                                    imageView.setImageBitmap(bitmap);

                                    linearLayout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
