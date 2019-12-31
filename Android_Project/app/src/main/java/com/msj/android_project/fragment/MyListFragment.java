package com.msj.android_project.fragment;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.msj.android_project.R;
import com.msj.android_project.utils.MToast;

import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends ListFragment {
    private List<String> lists;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        lists = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            lists.add("item"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,lists);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        MToast.showToast("点击了"+lists.get(position));
    }
}
