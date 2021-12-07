package com.example.clanner;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewEmptySupport extends RecyclerView {

    private View emptyView;
    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if(adapter != null && emptyView != null){
                if(adapter.getItemCount() == 0){
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerViewEmptySupport.this.setVisibility(View.GONE);
                }else{
                    emptyView.setVisibility(View.GONE);
                    recyclerViewEmptySupport.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter){
        super.setAdapter(adapter);
        if(adapter == null){
            adapter.registerAdapterDataObserver(adapterDataObserver);
        }
        adapterDataObserver.onChanged();
    }

    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
    }

    public recyclerViewEmptySupport(@NonNull Context context) {
        super(context);
    }

    public recyclerViewEmptySupport(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public recyclerViewEmptySupport(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
