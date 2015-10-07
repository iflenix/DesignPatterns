package com.storm.designpatterns;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommandFragment extends Fragment implements View.OnClickListener {

    RemoteControl mainRemote;
    Light livingRoomLight, kitchenLight;


    public CommandFragment() {
        mainRemote = new RemoteControl();

        // Required empty public constructor


    }

    Switch livRoomLightSw;
    View parentView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_command, container, false);

        Button undoButton = (Button) parentView.findViewById(R.id.undoButton);
        undoButton.setOnClickListener(this);


        livingRoomLight = new LivingRoomLight(getActivity());
        kitchenLight = new KitchenLight(getActivity());
        Stereo panasonic = new Stereo(getActivity());
        mainRemote.setCommand(0, new LightOnCommand(livingRoomLight), new LightOffCommand(livingRoomLight));
        mainRemote.setCommand(1, new LightOnCommand(kitchenLight), new LightOffCommand(kitchenLight));
        mainRemote.setCommand(2, new StereoOnWithCDCommand(panasonic), new StereoOffCommand(panasonic));

        //switches initialization
        switchInit(parentView);

        return parentView;
    }

    private void switchInit(View view) {
        livRoomLightSw = (Switch) parentView.findViewById(R.id.livingRoomLightSwitch);
        livRoomLightSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mainRemote.onButtonWasPushed(0);
                } else {
                    mainRemote.offButtonWasPushed(0);
                }
            }
        });

        Switch kitchenLightSw = (Switch) parentView.findViewById(R.id.kitchenLightSw);
        kitchenLightSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mainRemote.onButtonWasPushed(1);
                } else {
                    mainRemote.offButtonWasPushed(1);
                }
            }
        });
        Switch StereoSw = (Switch) parentView.findViewById(R.id.stereoSw);
        StereoSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mainRemote.onButtonWasPushed(2);
                } else {
                    mainRemote.offButtonWasPushed(2);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.undoButton) {
            mainRemote.undoButtonWasPushed();
        }

        class IteratorEnumAdapter implements Enumeration {
            Iterator iter;

            IteratorEnumAdapter(Iterator iter) {
                this.iter = iter;
            }

            @Override
            public boolean hasMoreElements() {

                return iter.hasNext();
            }


            @Override
            public Object nextElement() {
                return iter.next();
            }
        }

        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            al.add(i);
        }

        Enumeration<Integer> alEnum = new IteratorEnumAdapter(al.iterator());

        String text = "";
        while (alEnum.hasMoreElements()) {
            text+=alEnum.nextElement();
        }

        Toast.makeText(v.getContext(),text,Toast.LENGTH_SHORT).show();


    }
}

interface Command {
    void execute();

    void undo();
}

class NoCommand implements Command {
    @Override
    public void execute() {
    }

    @Override
    public void undo() {

    }
}

class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}

class LightOffCommand implements Command {
    Light light;

    LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}

class LightOnCommand implements Command {
    Light light;

    LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();


    }
}

interface Light {
    void on();

    void off();
}

class LivingRoomLight implements Light {
    Activity activity;

    LivingRoomLight(Activity activity) {
        this.activity = activity;

    }

    @Override
    public void on() {
        Toast.makeText(activity, "Living room light is ON!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void off() {
        Toast.makeText(activity, "Living room light is OFF!", Toast.LENGTH_SHORT).show();

    }


}

class KitchenLight implements Light {
    Activity activity;

    KitchenLight(Activity activity) {
        this.activity = activity;

    }

    @Override
    public void on() {
        Toast.makeText(activity, "Kitche light is ON!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void off() {
        Toast.makeText(activity, "Kitchen room light is OFF!", Toast.LENGTH_SHORT).show();

    }
}

class Stereo {
    Activity activity;
    int volume = 0;

    Stereo(Activity activity) {
        this.activity = activity;

    }

    public void on() {
        Toast.makeText(activity, "Stereo is ON!", Toast.LENGTH_SHORT).show();

    }

    public void off() {
        Toast.makeText(activity, "Stereo is OFF!", Toast.LENGTH_SHORT).show();
    }

    public void setCD() {
        Toast.makeText(activity, "Stereo mode is CD!", Toast.LENGTH_SHORT).show();
    }

    public void setVolume(int value) {
        volume = value;
        Toast.makeText(activity, "Stereo volume is " + value, Toast.LENGTH_SHORT).show();
    }
}

class StereoOnWithCDCommand implements Command {
    Stereo stereo;

    StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);

    }

    @Override
    public void undo() {
        stereo.setVolume(0);
        stereo.off();


    }
}

class StereoOffCommand implements Command {
    Stereo stereo;

    StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();

    }

    @Override
    public void undo() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);

    }
}


