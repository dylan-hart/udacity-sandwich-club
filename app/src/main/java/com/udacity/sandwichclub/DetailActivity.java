package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsLabelTextView;
    private TextView mPlaceOfOriginLabelTextView;
    private TextView mIngredientsLabelTextView;
    private TextView mDescriptionLabelTextView;

    private TextView mAlsoKnownAsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAlsoKnownAsLabelTextView = findViewById(R.id.also_known_label_tv);
        mPlaceOfOriginLabelTextView = findViewById(R.id.origin_label_tv);
        mIngredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        mDescriptionLabelTextView = findViewById(R.id.description_label_tv);

        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populates UI elements using an instance of Sandwich.
     * @param sandwich The Sandwich object used to populate UI elements.
     */
    private void populateUI(Sandwich sandwich) {
        // Populate the "Also known as" field. If none, hide the related TextViews.
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            mAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownAsLabelTextView.setVisibility(View.GONE);
        }
        else {
            mAlsoKnownAsTextView.setText(alsoKnownAs.get(0));
            for (int i = 1; i < alsoKnownAs.size(); i++) {
                mAlsoKnownAsTextView.append(", " + alsoKnownAs.get(i));
            }
        }

        // Populate the "Place of origin" field. If not provided, hide the related TextViews.
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.isEmpty()) {
            mPlaceOfOriginTextView.setVisibility(View.GONE);
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
        } else {
            mPlaceOfOriginTextView.setText(placeOfOrigin);
        }

        // Populate the "Ingredients" field. If not provided, hide the related TextViews.
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.isEmpty()) {
            mIngredientsTextView.setVisibility(View.GONE);
            mIngredientsLabelTextView.setVisibility(View.GONE);
        }
        else {
            mIngredientsTextView.setText(ingredients.get(0));
            for (int i = 1; i < ingredients.size(); i++) {
                mIngredientsTextView.append(", " + ingredients.get(i));
            }
        }

        // Populate the "Description" field. If not provided, hide the related TextViews.
        String description = sandwich.getDescription();
        if (description == null || description.isEmpty()) {
            mDescriptionTextView.setVisibility(View.GONE);
            mDescriptionLabelTextView.setVisibility(View.GONE);
        } else {
            mDescriptionTextView.setText(sandwich.getDescription());
        }
    }
}
