package com.storm.designpatterns;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingletonFragment extends Fragment implements View.OnClickListener {


    public SingletonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_singleton, container, false);
        /*Button singletonButton = (Button) v.findViewById(R.id.singleton_button);
        singletonButton.setOnClickListener(this);*/

        return v;

    }


    @Override
    public void onClick(View v) {

    }


}

class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;

    public volatile static ChocolateBoiler boilerInstance;

    private ChocolateBoiler() {
        empty = true;
        boiled = false;

    }

    public ChocolateBoiler getInstance() {
        if (boilerInstance == null) {
            synchronized (ChocolateBoiler.class) {
                if (boilerInstance == null)
                    boilerInstance = new ChocolateBoiler();
            }
        }
        return boilerInstance;
    }

    public void fill() {
        empty = true;
        boiled = false;
    }

    public void boil() {
        if (!isEmpty() && !isBoiled())
            boiled = true;
    }

    public void drain() {
        if (!isEmpty() && isBoiled()) {
            empty = true;
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isBoiled() {
        return boiled;
    }


}
