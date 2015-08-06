package com.storm.designpatterns;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Works!", Toast.LENGTH_SHORT).show();
        ArrayList<Button> arrayList = new ArrayList<Button>();
        arrayList.iterator();



        return super.onStartCommand(intent, flags, startId);

    }

    private final IAdd.Stub mBinder = new IAdd.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}
