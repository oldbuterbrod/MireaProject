package ru.mirea.sidorov_m.mireaproject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFragment extends Fragment {

    public NewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_new, container, false);

        ListView listView = view.findViewById(R.id.listView);

        PackageManager packageManager = requireActivity().getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        List<String> appNames = new ArrayList<>();
        for (ApplicationInfo appInfo : installedApplications) {
            appNames.add(appInfo.loadLabel(packageManager).toString());
        }

        Collections.sort(appNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, appNames);
        listView.setAdapter(adapter);

        return view;
    }
}
