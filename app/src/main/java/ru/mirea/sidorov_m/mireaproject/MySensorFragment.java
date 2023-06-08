package ru.mirea.sidorov_m.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.mirea.sidorov_m.mireaproject.databinding.FragmentMySensorBinding;

public class MySensorFragment extends Fragment implements SensorEventListener {

    private FragmentMySensorBinding binding;
    private TextView magneticFieldView;
    private SensorManager sensorManager;
    private Sensor magneticFieldSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMySensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Получаем список доступных датчиков на устройстве
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Ищем датчик магнитного поля в списке доступных датчиков
        for (Sensor sensor : sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldSensor = sensor;
                break;
            }
        }

        magneticFieldView = binding.magneticFieldView;
        magneticFieldView.setText("Magnetic field: ");

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float magneticField = event.values[0];
            magneticFieldView.setText(String.format("Magnetic field: %.1f μT", magneticField));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
