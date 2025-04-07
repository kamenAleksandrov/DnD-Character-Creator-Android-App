package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.MainActivity.imagesFolderToPass;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.dndCharacterCreator.databinding.FragmentUserDrawingCanvasBinding;
import com.google.android.material.slider.RangeSlider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class UserDrawingCanvas extends Fragment {

    private FragmentUserDrawingCanvasBinding binding;

    private DrawView paint;

    private AppCompatButton save, color, stroke, undo, brush, clearAll;

    private RangeSlider rangeSlider;

    public UserDrawingCanvas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDrawingCanvasBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        paint = binding.drawView;
        undo = binding.undoBtnButton;
        save = binding.saveBtnButton;
        brush = binding.brushButton;
        clearAll = binding.clearAllBtnButton;
        //rangeSlider = binding.rangeSlider;
        //color = binding.colorBtnButton;
        //stroke = binding.strokeBtnButton;

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.brush();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Clear all?")
                        .setMessage("Are you sure you want to clear drawing?")
                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                paint.clearAll();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bmp = paint.save();

                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir(imagesFolderToPass, Context.MODE_PRIVATE);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File mypath = new File(directory, characterNameToPass + ".png");

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    Toast.makeText(getContext(), "Saved!",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("SAVE_IMAGE", e.getMessage(), e);
                    Toast.makeText(getContext(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // pass the height and width of the custom view
        // to the init method of the DrawView object

        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Bitmap bmp = paint.save();

        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir(imagesFolderToPass, Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, characterNameToPass + ".png");

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
        }
        binding = null;
    }
    /*
    public void onBackPressed() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getContext());
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure you want to exit? Any unsaved progress will be lost!");
        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        AlertDialog alert=alertdialog.create();
        alertdialog.show();

    }

     */
}