package com.example.fitnessapplication.Reminder;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

// singleton
public class DAO {

    private static final String TAG = "DAO";
    private final String DATA_FILE_NAME = "reminder.txt";

    private static DAO dao = null;

    private Context context = null;
    private ArrayList<com.example.fitnessapplication.Reminder.Task> list = null;
    private long nextId; // we need it to assign id to tasks

    private static SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM d, yyyy");

    protected DAO() {}

    public static synchronized DAO getInstance(Context context)
    {
        Log.d(TAG, "getting DAO instance");
        if (dao == null)
        {
            dao = new DAO();
            dao.context = context.getApplicationContext();
            dao.load();
        }
        return dao;
    }


    private void load(){
        list = new ArrayList<com.example.fitnessapplication.Reminder.Task>(); // creating empty list
        nextId = 1;
        try
        {
            Log.d(TAG, "reading file");

            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            DataInputStream dis = new DataInputStream(fileInputStream);

            nextId = dis.readLong();
            int size = dis.readInt();

            for (int i = 0; i < size; i++)
            {
                com.example.fitnessapplication.Reminder.Task task = new com.example.fitnessapplication.Reminder.Task();
                task.deserialize(dis);
                list.add(task);
            }

            dis.close();
        }
        catch (IOException e)
        {
            Log.d(TAG, "Cant load tasks");
        }
    }

    public void save(){
        try
        {
            Log.d(TAG, "saving file");

            DataOutputStream dos = new DataOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));

            dos.writeLong(nextId);
            dos.writeInt(list.size());

            for (int i = 0; i < list.size(); i++)
                list.get(i).serialize(dos);

            dos.close();
        } catch (IOException e)
        {
            Log.d(TAG, "Cant save tasks");
        }
    }

    public int size()
    {
        return list.size();
    }

    public com.example.fitnessapplication.Reminder.Task get(int position)
    {
        return list.get(position);
    }

    public void add(com.example.fitnessapplication.Reminder.Task Task)
    {
        Task.setId(nextId++);
        list.add(Task);
        Collections.sort(list);
        save();
    }

    public void update(com.example.fitnessapplication.Reminder.Task Task)
    {
        Task.update();
        Collections.sort(list);
        save();
    }

    public void remove(int index)
    {
        list.remove(index);
        save();
    }

    public static String formatTime(com.example.fitnessapplication.Reminder.Task Task)
    {
        return timeFormat.format(new Date(Task.getDate()));
    }

    public static String formatDate(com.example.fitnessapplication.Reminder.Task Task)
    {
        return dateFormat.format(new Date(Task.getDate()));
    }
}
