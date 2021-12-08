package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class listOfFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_friends);

        Toolbar Tb = findViewById(R.id.Toolbar_listOfFriendsActivity);

        setSupportActionBar(Tb);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("친구 관리");

        ViewPager2 pager = findViewById(R.id.recyclerviewPager_listOfFriendsActivity);
        FragmentAdapter adapter = new FragmentAdapter(this,2);
        pager.setAdapter(adapter);

        final List<String> tabname = Arrays.asList("친구 목록","친구 신청 목록");
        TabLayout tab = findViewById(R.id.tablayout);
        new TabLayoutMediator(tab, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabname.get(position));
            }
        }).attach();



    }
}
