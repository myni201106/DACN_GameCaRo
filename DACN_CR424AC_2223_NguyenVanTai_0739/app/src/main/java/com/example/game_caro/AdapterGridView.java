package com.example.game_caro;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AdapterGridView extends BaseAdapter {
    Context myContext;
    int myLayout;
    boolean isPlayer1Turn = true;

    ArrayList<String> arr;

    public AdapterGridView(Context myContext, int myLayout, ArrayList<String> arr) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout, null);
        CustomTextView customTextView = view.findViewById(R.id.custom_text);
        customTextView.setBackgroundResource(R.drawable.img1);
        return view;
    }
    public String getCellValue(int position) {
        if (position >= 0 && position < arr.size()) {
            return arr.get(position);
        }
        return ""; // Hoặc giá trị mặc định khác tùy vào yêu cầu của bạn
    }
}
