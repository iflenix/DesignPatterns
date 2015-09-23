package com.storm.designpatterns;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.*;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;


public class PatternsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patterns);

        PatternsActivityFragment activityFragment = new PatternsActivityFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.patternsMainFrame, activityFragment)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patterns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDecorButtonClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DecoratorFragment decoratorFragment = new DecoratorFragment();
        ft.replace(R.id.patternsMainFrame, decoratorFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        //ft.show(fm.findFragmentById(R.id.decoratorFragment));
        ft.commit();

    }

    public void onGetCostClick(View view) {
        double cost;
        String description = "";

        Beverage beverage = null;

        RadioGroup rg = (RadioGroup) findViewById(R.id.beverageType);
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbEspresso:
                beverage = new Espresso();
                break;
            case R.id.rbHouseBlend:
                beverage = new HouseBlend();
        }

        if (((Switch) (findViewById(R.id.swWhip))).isChecked()) {
            beverage = new Whip(beverage);
        }

        if (((Switch) (findViewById(R.id.swSoy))).isChecked()) {
            beverage = new Soy(beverage);
        }

        if (((Switch) (findViewById(R.id.swMocha))).isChecked()) {
            beverage = new Mocha(beverage);
        }

        if (beverage != null) {
            String order = "You ordered: " + beverage.getDescription() + ", which costs: " + Double.toString(beverage.cost());
            Toast.makeText(this,order,Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MyService.class);
        sendBroadcast(intent);

    }

    public void startServiceButtonClick(View view) {

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        int firstDigit, secondDigit;

        EditText first = (EditText) findViewById(R.id.edFirstDigit);
        firstDigit = Integer.getInteger(first.getText().toString());
        secondDigit = Integer.getInteger(((EditText) findViewById(R.id.etSecondDigit)).getText().toString());

    }

    public void onObserverButtonClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ObserverFragment observerFragment = new ObserverFragment();
        ft.replace(R.id.patternsMainFrame, observerFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        //ft.show(fm.findFragmentById(R.id.decoratorFragment));
        ft.commit();

    }

    public void onChangeWeatherClick(View view) {

    }
}
