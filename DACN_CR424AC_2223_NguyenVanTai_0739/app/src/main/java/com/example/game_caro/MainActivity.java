package com.example.game_caro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GridView grid;
    private TextView txt1, txt2, txt_kQ;
    Button btnReset, btnUndo, btnExit;
    AdapterGridView adapter;
    ArrayList<String> arr;
    private boolean isPlayer1Turn = true;
    private CustomTextView customTextView;
    private Button[][] list = new Button[8][8];
    private int player1, player2, roundCount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addData();

    }

    private void addData() {
        for (int i = 0; i < 64; i++) {
            arr.add("");
        }
        adapter.notifyDataSetChanged();
    }

    private void init() {
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt_kQ = findViewById(R.id.txt_KQ);
        grid = findViewById(R.id.grid);
        arr = new ArrayList<>();
        adapter = new AdapterGridView(this, R.layout.activity_item, arr);
        grid.setAdapter(adapter);
        customTextView = findViewById(R.id.custom_text);
        btnReset = findViewById(R.id.btnReset);
        btnUndo = findViewById(R.id.btnUndo);
        btnExit = findViewById(R.id.btnExit);
        roundCount = 0;
        player1 = 0;
        player2 = 0;
        list = new Button[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // Tính vị trí tương ứng trong Adapter
                int position = i * 8 + j;

                // Lấy dữ liệu từ AdapterGridView tại vị trí (i, j)
                String cellValue = adapter.getCellValue(position);

                // Lưu giá trị từ Adapter vào biến list[i][j]
                list[i][j].setText(cellValue);
                // Sử dụng giá trị tại ô list[i][j] theo nhu cầu của bạn
            }
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSetGame();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UndoGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (isGameEnded()) {
            // Trò chơi đã kết thúc, không cho phép đánh tiếp
            return;
        }

        int row = 0;
        int col = 0;

        // Tìm ra hàng và cột của ô được nhấn dựa trên ID của nó
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (v == list[i][j]) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row != -1 && col != -1 && arr.get(row * 8 + col).isEmpty()) {
            if (isPlayer1Turn) {
                list[row][col].setText("X");
                arr.set(row * 8 + col, "X");
//                customTextView.setText("X");
//                customTextView.setTextColor(Color.RED);
                // Thêm xử lý khi người chơi 1 đánh X vào ô (row, col) ở đây
            } else {
                list[row][col].setText("O");
                arr.set(row * 8 + col, "O");
//                customTextView.setText("O");
//                customTextView.setTextColor(Color.BLUE);
                // Thêm xử lý khi người chơi 2 đánh O vào ô (row, col) ở đây
            }

            isPlayer1Turn = !isPlayer1Turn;
            roundCount++;

            if (checkWin(col, row)) {
                if (isPlayer1Turn) {
                    player1Win();
                } else {
                    player2Win();
                }
                // Kết thúc trò chơi nếu có người chiến thắng

            } else if (roundCount == 64) {
                // Đã hết tất cả các ô và không ai chiến thắng
                reSetGame();
                roundCount = 0;
            }
        }
    }
    private boolean isGameEnded() {
        // Kiểm tra xem trò chơi đã kết thúc chưa, bằng cách kiểm tra thắng hoặc hòa
        return roundCount == 64 || checkWin(0, 0);
    }


    private boolean checkWin(int i, int j) {
            int d = 0, k = i, h;
            // kiểm tra hàng
        while (list[k][j].getText().equals(list[i][j].getText())) {
                d++;
                k++;
            }
            k = i - 1;
        while (list[k][j].getText().equals(list[i][j].getText())) {
                d++;
                k--;
            }
            if (d > 4) return true;
            d = 0; h = j;
            // kiểm tra cột
        while (list[i][h].getText().equals(list[i][j].getText())) {
                d++;
                h++;
            }
            h = j - 1;
        while (list[i][h].getText().equals(list[i][j].getText())) {
                d++;
                h--;
            }
            if (d > 4) return true;
            // kiểm tra đường chéo 1
            h = i; k = j; d = 0;
        while (list[i][j].getText().equals(list[h][k].getText())) {
                d++;
                h++;
                k++;
            }
            h = i - 1; k = j - 1;
        while (list[i][j].getText().equals(list[h][k].getText())) {
                d++;
                h--;
                k--;
            }
            if (d > 4) return true;
            // kiểm tra đường chéo 2
            h = i; k = j; d = 0;
        while (list[i][j].getText().equals(list[h][k].getText())) {
                d++;
                h++;
                k--;
            }
            h = i - 1; k = j + 1;
            while (list[i][j].getText().equals(list[h][k].getText())) {
                    d++;
                    h--;
                    k++;
                }
                if (d > 4) return true;
                // nếu không đương chéo nào thỏa mãn thì trả về false.
                return false;
        }
    private void reSetGame(){
        // Đặt lại trò chơi bằng cách xóa dữ liệu hiện tại và cập nhật giao diện
        arr.clear(); // Xóa dữ liệu hiện tại
        for (int i = 0; i < 64; i++) {
            arr.add("");
        }
        adapter.notifyDataSetChanged(); // Cập nhật giao diện
    }
    private void player1Win(){
        txt_kQ.setText("Player 1 Win");
        player1++;
        txt1.setText(" "+player1);
    }
    private void player2Win(){
        txt_kQ.setText("Player 2 Win");
        player2++;
        txt2.setText(" "+player2);
    }
    private void UndoGame() {
        if (roundCount > 0) {
            int lastMovePosition = -1;
            for (int i = arr.size() - 1; i >= 0; i--) {
                if (!arr.get(i).isEmpty()) {
                    lastMovePosition = i;
                    break;
                }
            }

            if (lastMovePosition >= 0) {
                int row = lastMovePosition / 8;
                int col = lastMovePosition % 8;

                // Xóa dữ liệu hiện tại tại vị trí undo
                arr.set(lastMovePosition, "");
                list[row][col].setText("");

                // Đặt lại lượt người chơi
                isPlayer1Turn = !isPlayer1Turn;
                roundCount--;

                // Thêm xử lý khác nếu cần
            }
        }
    }
}