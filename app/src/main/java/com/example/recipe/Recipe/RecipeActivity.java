package com.example.recipe.Recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
    IngredientsRecord ingredientsRecord;
    double totalPrice;
    TextView TVTotal;

    private Button alertBtn;
    Button BtnAddToCart, BtnYoutube;
    private int PERMISSION_CODE = 1;

    RatingBar ratingStar;
    final float[] myRating = {0};

    FloatingActionButton BtnShare, BtnLike, BtnAdd, BtnFAQ;
    Boolean isFabOpen = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setupActionBar();


        //SETUP FAB BUTTON
        BtnShare = findViewById(R.id.BtnShare);
        BtnLike = findViewById(R.id.BtnLike);
        BtnAdd = findViewById(R.id.BtnAdd);
        BtnFAQ = findViewById(R.id.BtnFAQ);

        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!isFabOpen){
                   showFabMenu();
               }else{
                   closeFabMenu();
               }
            }
        });

        //SETUP SHARE BUTTON
        BtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Download this App";
                String Sub = "http://play.google.com";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));

            }
        });

        //SETUP LIKE BUTTON
        BtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Add to Favorite");
            }

        });

        //SETUP ADD TO CART BUTTON
        BtnAddToCart = (Button) findViewById(R.id.BtnAddToCart);
        BtnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("ADDED TO CART");
            }
        });

        // SETUP RATING BAR
        ratingStar = findViewById(R.id.BarRate);
        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int rate = (int) rating;
                String message = null;

                myRating[0] = ratingBar.getRating();

                switch (rate){
                    case 1:
                        message = "Sorry to hear that! :(";
                        break;

                    case 2:
                        message = "We always accept any suggestions to improve!";
                        break;

                    case 3:
                        message = "Good enough!";
                        break;

                    case 4:
                        message = "Great! Thank You.";
                        break;

                    case 5:
                        message = "Awesome!";
                        break;
                }

                Toast.makeText(RecipeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        //SETUP INGREDIENT
        ingredientsRecord = new IngredientsRecord();
        TVTotal = findViewById(R.id.TVTotal);

        //SETUP ALERT BUTTON FOR INGREDIENTS
        alertBtn = (Button) findViewById(R.id.BtnInfo);
        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeActivity.this);

                builder.setTitle("IMPORTANT INFORMATION: ");
                builder.setMessage("Ingredients other than CHICKEN, MEAT, and FISH are sold in a certain amount of grams or oz.\n" +
                        "\nPlease refer FAQs for more details.");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        //SETUP YOUTUBE BUTTON
        BtnYoutube = (Button) findViewById(R.id.BtnYoutube);
        BtnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    gotoUrl("https://www.youtube.com/watch?v=2K_GE4dMRrM");
            }
        });

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }

    //TOAST FOR FAVORITE and ADD_TO_CART BUTTON
    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //FAB BUTTON ANIMATION
    private void showFabMenu() {
        isFabOpen = true;
        BtnAdd.animate().rotationBy(45);
        BtnFAQ.animate().translationY(-getResources().getDimension(R.dimen.standard_70));
        BtnShare.animate().translationY(-getResources().getDimension(R.dimen.standard_140));
        BtnLike.animate().translationY(-getResources().getDimension(R.dimen.standard_210));
    }
    private void closeFabMenu() {
        isFabOpen = false;
        BtnAdd.animate().rotationBy(45);
        BtnFAQ.animate().translationY(0);
        BtnShare.animate().translationY(0);
        BtnLike.animate().translationY(0);
    }

    //SETUP ACTION BAR
    private void setupActionBar(){
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setSupportActionBar(Toolbar toolbar) {
    }

    /***
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        Drawable favoriteItemColor = favoriteItem.getIcon();
        setupColorActionBarIcon(favoriteItemColor);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ***/

    //INGREDIENTS
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.CBChicken:
                if (checked)
                    ingredientsRecord.setChicken(9.30);
                else
                    ingredientsRecord.setChicken(0.0);
                break;

            case R.id.CBFlour:
                if (checked)
                    ingredientsRecord.setFlour(2.40);
                else
                    ingredientsRecord.setFlour(0.0);
                break;

            case R.id.CBGarlic:
                if (checked)
                    ingredientsRecord.setGarlic(2.00);
                else
                    ingredientsRecord.setGarlic(0.0);
                break;

            case R.id.CBHoney:
                if (checked)
                    ingredientsRecord.setHoney(12.00);
                else
                    ingredientsRecord.setHoney(0.0);
                break;

            case R.id.CBSoySos:
                if (checked)
                    ingredientsRecord.setSoySos(4.30);
                else
                    ingredientsRecord.setSoySos(0.0);
                break;
            case R.id.CBButter:
                if (checked)
                    ingredientsRecord.setButter(5.00);
                else
                    ingredientsRecord.setButter(0.0);
                break;
        }

        TVTotal.setText("TOTAL: RM " + calculateTotal());

    }
    private String calculateTotal(){
        DecimalFormat format = new DecimalFormat("###,###,##0.00");

        totalPrice = ingredientsRecord.getButter() + ingredientsRecord.getFlour() + ingredientsRecord.getChicken()
                + ingredientsRecord.getGarlic() + ingredientsRecord.getHoney() + ingredientsRecord.getSoySos();
        return format.format(totalPrice);

    }
}

