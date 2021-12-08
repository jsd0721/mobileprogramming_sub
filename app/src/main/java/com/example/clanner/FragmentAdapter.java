package com.example.clanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clanner.friendsListFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public int mCount;

    public FragmentAdapter(@NonNull FragmentActivity fragment,int count) {
        super(fragment);
        this.mCount = count;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0 :
                return new friendsListFragment();
            case 1 :
                return new stayFriendsListFragment();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
