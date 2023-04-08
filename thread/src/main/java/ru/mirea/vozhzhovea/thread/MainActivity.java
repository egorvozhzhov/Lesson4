package ru.mirea.vozhzhovea.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import ru.mirea.vozhzhovea.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-01-20, НОМЕР ПО СПИСКУ: 8, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Зеленая книга");
        binding.textView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new Thread(new Runnable() {
                public void run() {
                    int numberThread = counter++;
                    Log.d("ThreadProject", String.format("Запущен поток No %d студентом группы No %s номер по списку No %s ", numberThread, "БСБО-01-20","8"));
                    long endTime = System.currentTimeMillis() + 20 * 1000; while (System.currentTimeMillis() < endTime) {
                        synchronized (this) { try {
                            wait(endTime - System.currentTimeMillis());
                            Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime); } catch (Exception e) {
                            throw new RuntimeException(e); }
                        }
                        Log.d("ThreadProject", "Выполнен поток No " + numberThread); }
                } }).start();
            } });


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        float les = Float.parseFloat(binding.etPar.getText().toString())/Float.parseFloat(binding.etDays.getText().toString());
                        binding.textView2.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.textView2.setText("Среднее кол-во пар в день " +Float.toString(les));
                            }
                        });
                    }

                };
                Thread thread  = new Thread(runnable);
                thread.start();


            }
        });


    }

}