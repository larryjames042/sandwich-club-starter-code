package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TextView mainNameTv ;
    TextView alsoKnownAsTv;
    TextView placeOfOriginTv;
    TextView descriptionTv;
    TextView ingredientTv;
    Sandwich sandwich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

         mainNameTv = findViewById(R.id.main_name_tv);
         alsoKnownAsTv = findViewById(R.id.also_known_tv);
         placeOfOriginTv = findViewById(R.id.origin_tv);
         descriptionTv = findViewById(R.id.description_tv);
         ingredientTv = findViewById(R.id.ingredients_tv);


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

        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        if(sandwich.getImage()!= ""){
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
        }


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if(sandwich.getMainName()!=""){
            mainNameTv.setText(sandwich.getMainName());
        }else{
            mainNameTv.setText("NA");
        }

        if(sandwich.getPlaceOfOrigin()!="" ){
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }else{
            placeOfOriginTv.setText("NA");
        }

        if(sandwich.getDescription()!=""){
            descriptionTv.setText(sandwich.getDescription());
        }else{
            descriptionTv.setText("NA");
        }

        if(sandwich.getAlsoKnownAs()!=null){
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
            String alsoKnownAs = "";
            for(String s : alsoKnownAsList){
                alsoKnownAs += s + ", ";
            }
            alsoKnownAsTv.setText(alsoKnownAs);
        }else{
            alsoKnownAsTv.setText("NA");
        }

        if(sandwich.getIngredients()!=null){
            List<String> ingredientsList = sandwich.getIngredients();
            String ingredient = "";
            for(String s : ingredientsList){
                ingredient += s + ", ";
            }
            ingredientTv.setText(ingredient);
        }else{
            ingredientTv.setText("NA");
        }

    }
}
