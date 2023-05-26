package ru.mirea.sidorov_m.mireaproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.mirea.sidorov_m.mireaproject.FileFragment;
import ru.mirea.sidorov_m.mireaproject.R;

public class MyDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("файлы").setMessage("Выберите действие").setIcon(ru.mirea.sidorov_m.mireaproject.R.mipmap.ic_launcher).setPositiveButton("open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FileFragment) getParentFragment()).onOpenClicked();
                        dialog.cancel();
                    }
                })
                .setNeutralButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FileFragment) getParentFragment()).onSaveClicked();
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}