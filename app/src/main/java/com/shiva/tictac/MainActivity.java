package com.shiva.tictac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 1: Red , 2 : Yellow
    private int currentPlayer;

    String player[] = {"","Red", "Yellow"};
    int[] board = new int[9];
    int[][] winMatrix = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPlayer = 1;
        updateStatus(currentPlayer);
    }

    public void placeCoin(View v){
        ImageView current = (ImageView) v;

        if(currentPlayer==-1 || current.getDrawable()!=null){
            Toast.makeText(this, "Invalid Place", Toast.LENGTH_SHORT);
            return;
        }

        int tag = Integer.parseInt(current.getTag().toString());
        board[tag] = currentPlayer;

        int lastPlayer = currentPlayer;

        current.setTranslationY(-1500);
        if(currentPlayer==1){
            current.setImageResource(R.drawable.red);
            currentPlayer = 2;
        }else{
            current.setImageResource(R.drawable.yellow);
            currentPlayer = 1;
        }
        updateStatus(currentPlayer);

        current.animate().translationY(0).setDuration(300);
        if(isWinner(lastPlayer)){
            currentPlayer=-1;
            TextView status = findViewById(R.id.txtStatus);
            status.setText(player[lastPlayer]+" has won !!!");
            return;
        }

        if(isBoardFull()){
            currentPlayer=-1;
            TextView status = findViewById(R.id.txtStatus);
            status.setText("Match Drawn");
            return;
        }

    }

    public boolean isBoardFull(){
        for(int i=0;i<board.length;i++){
            if(board[i]==0){
                return false;
            }
        }
        return true;
    }
    public boolean isWinner(int lastPlayer){
        for(int i=0; i<winMatrix.length; i++){
            if(board[winMatrix[i][0]]==lastPlayer && board[winMatrix[i][1]]==lastPlayer && board[winMatrix[i][2]]==lastPlayer){
                return true;
            }
        }
        return false;
    }

    public void updateStatus(int p){
        TextView status = findViewById(R.id.txtStatus);
        status.setText(player[p]+"'s Turn");
    }

    public void reset(View v) {
        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gl.getChildCount(); i++) {
            ImageView iv = (ImageView) gl.getChildAt(i);
           // iv.animate().translationX(-1500).setDuration(1000);
            iv.setImageDrawable(null);
        }
        board = new int[9];
        currentPlayer = 1;
        updateStatus(currentPlayer);
    }
}
