package com.example.clanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link friendsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class friendsListFragment extends Fragment {

    ArrayList<Memberinfo> list = new ArrayList<>();

    public friendsListFragment() {
        // Required empty public constructor
    }

    public static friendsListFragment newInstance() {
        friendsListFragment fragment = new friendsListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayoutManager LNmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false );
        friendsListRecyclerView adapter = new friendsListRecyclerView(getActivity(),list,"friendList");

        View friendView = inflater.inflate(R.layout.fragment_friends_list, container, false);
        RecyclerView RCView = friendView.findViewById(R.id.friendListRCView_friendListFragment);
        RCView.setLayoutManager(LNmanager);
        RCView.setAdapter(adapter);


        return friendView;
    }
}