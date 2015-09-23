package com.storm.designpatterns;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class ObserverFragment extends Fragment implements View.OnClickListener {

    private WeatherStation ws;
    private CurrentConditionsDisplay ccd;

    public ObserverFragment() {
        ws = new WeatherStation();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_observer, container, false);

        Button changeWeatherButton = (Button) v.findViewById(R.id.btnChangeWeather);
        changeWeatherButton.setOnClickListener(this);
        ccd = new CurrentConditionsDisplay(ws, v);

        return v;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangeWeather:
                float temp = (float) Math.random() * 40;
                float hum = (float) Math.random() * 100;
                float pres = (float) (Math.random() * 100 + 700);
                ws.setWeather(temp, hum, pres);
                break;
        }
    }
}


interface Observable {
    void RegisterObserver(Observer obs);

    void UnregisterObserver(Observer obs);

}

interface Observer {
    void update(Observable obsrvbl);
}

class WeatherStation implements Observable {
    ArrayList<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getTemperature() {
        return temperature;
    }


    WeatherStation() {
        observers = new ArrayList<>();
        temperature = 0f;
        humidity = 0f;
        pressure = 0f;
    }

    @Override
    public void RegisterObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void UnregisterObserver(Observer obs) {
        observers.remove(obs);

    }

    public void measurementsChanged() {
        for (Observer obs : observers) {
            obs.update(this);
        }

    }

    public void setWeather(float temp, float hum, float pres) {
        this.temperature = temp;
        this.humidity = hum;
        this.pressure = pres;
        measurementsChanged();
    }

}

class CurrentConditionsDisplay implements Observer {
    Observable observable;
    float temp, hum, pres;
    View displayView;

    CurrentConditionsDisplay(Observable obsrvbl, View v) {
        this.observable = obsrvbl;
        observable.RegisterObserver(this);
        temp = ((WeatherStation) obsrvbl).getTemperature();
        hum = ((WeatherStation) obsrvbl).getHumidity();
        pres = ((WeatherStation) obsrvbl).getPressure();
        this.displayView = v;
    }

    @Override
    public void update(Observable obsrvbl) {

        if (obsrvbl instanceof WeatherStation) {
            temp = ((WeatherStation) obsrvbl).getTemperature();
            hum = ((WeatherStation) obsrvbl).getHumidity();
            pres = ((WeatherStation) obsrvbl).getPressure();

            display();
        }


    }

    public void display() {
        TextView tvTemp = (TextView) displayView.findViewById(R.id.tvTemp);
        tvTemp.setText("Temp: " + String.valueOf(temp));
        TextView tvHum = (TextView) displayView.findViewById(R.id.tvHumidity);
        tvHum.setText("Humidity: " + String.valueOf(hum));
        TextView tvPres = (TextView) displayView.findViewById(R.id.tvPressure);
        tvPres.setText("Pressure: " + String.valueOf(pres));


    }
}