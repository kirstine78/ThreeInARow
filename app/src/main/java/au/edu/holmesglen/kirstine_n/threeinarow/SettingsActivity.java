package au.edu.holmesglen.kirstine_n.threeinarow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner spinnerDifficulty = (Spinner) findViewById(R.id.spinner_difficulty);

        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(
                this, R.array.difficulty, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDifficulty.setAdapter(adapterDifficulty);

        final Spinner spinnerGridSize = (Spinner) findViewById(R.id.spinner_grid_size);

        ArrayAdapter<CharSequence> adapterGridSize = ArrayAdapter.createFromResource(
                this, R.array.grid_size, android.R.layout.simple_spinner_item);
        adapterGridSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGridSize.setAdapter(adapterGridSize);

        final Spinner spinnerColor1 = (Spinner) findViewById(R.id.spinner_color1);

        ArrayAdapter<CharSequence> adapterColor1 = ArrayAdapter.createFromResource(
                this, R.array.colour_images1, android.R.layout.simple_spinner_item);
        adapterColor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColor1.setAdapter(adapterColor1);

        final Spinner spinnerColor2 = (Spinner) findViewById(R.id.spinner_color2);

        ArrayAdapter<CharSequence> adapterColor2 = ArrayAdapter.createFromResource(
                this, R.array.colour_images2, android.R.layout.simple_spinner_item);
        adapterColor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColor2.setAdapter(adapterColor2);

        final Button b = (Button) findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Button was clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }  // end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_screen, menu);
//        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;

        switch (item.getItemId())  // which menu item has been selected
        {
            case R.id.main:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_game:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.win_lose_record:
                intent = new Intent(this, WinLoseActivity.class);
                startActivity(intent);
                return true;
        }
        return false;  // nothing happened  no menu items has been selected
    }  // end onOptionsItemSelected


}
