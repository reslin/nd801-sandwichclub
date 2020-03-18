package com.example.android.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwichclub.model.Sandwich;
import com.example.android.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originTv;
    private TextView alsoKnownTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;

    //helper values, same order as in Sandwich()-constructor
    private String alsoKnownAs;
    private String placeOfOrigin;
    private String description;
    private String ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView sandwichIv = findViewById(R.id.sandwich_iv);

        originTv = findViewById(R.id.origin_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        setTitle(sandwich.getMainName());

        Picasso.with(this)
            .load(sandwich.getImage())
            .into(sandwichIv);

        //trim off '[' and ']' from toString() result
        alsoKnownAs = sandwich.getAlsoKnownAs().toString().substring(1).replace("]", "");
        placeOfOrigin = sandwich.getPlaceOfOrigin();
        description = sandwich.getDescription();
        ingredients = sandwich.getIngredients().toString().substring(1).replace("]", "");

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        originTv.setText(placeOfOrigin);
        alsoKnownTv.setText(alsoKnownAs);
        ingredientsTv.setText(ingredients);
        descriptionTv.setText(description);
    }
}
