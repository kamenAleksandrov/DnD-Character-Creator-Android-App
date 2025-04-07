package com.example.dndCharacterCreator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ConfirmDeletionAlertDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        TextView textView = getView().findViewById(R.id.character_creation_list_textView);
                        SharedPreferences characterNamesSharedPreferences =
                                getActivity().getSharedPreferences("Character Names", Context.MODE_PRIVATE);
                        SharedPreferences.Editor characterNamesEditor = characterNamesSharedPreferences.edit();
                        String characterToDelete = textView.getText().toString();
                        characterNamesEditor.remove(characterToDelete);
                        characterNamesEditor.apply();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Objects.requireNonNull(ConfirmDeletionAlertDialog.this.getDialog()).cancel();
                    }
                });
        return builder.create();
    }
}