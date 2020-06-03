package com.example.caro3x3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private  Dialog dialog;

    private int[][] posititonwinnings = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    private int[] boardState ={2,2,2,2,2,2,2,2,2};

    private int roundcount = 0;

    private int player = 0;

    private boolean isContinue = true;

    private boolean isOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void  playAgain(View view){ //reset all attributes
        player = 0;
        roundcount = 0;
        isContinue = true;
        isOver = false;
        for ( int i = 0 ; i < boardState.length ; i++) boardState[i]=2;
        GridLayout gridLayout = findViewById(R.id.gv_table);
        for ( int i = 0 ; i < gridLayout.getChildCount(); i++ ){
            ImageView chess = (ImageView)gridLayout.getChildAt(i);
            chess.setImageResource(0);
        }
    }

    public void dropIn(View view){ //action when clicking
        ImageView chess = (ImageView) view;
        Play(chess);
        isWinner(posititonwinnings);
        isDraw(roundcount,isOver);
    }

    public void showDialog(String winner){ //show the result

        dialog = new Dialog(MainActivity.this);
        if (winner == "red") dialog.setTitle("__________The winner is RED________");
        else if (winner == "blue") dialog.setTitle("_________The winner is BLUE________");
        else dialog.setTitle("_____________DRAW_____________");
        dialog.setContentView(R.layout.dialogwin);
        dialog.show();

    }

    public void isWinner(int[][] posititonwinnings){ // find the winner
        for (int[] positionwinning : posititonwinnings){
            if (boardState[positionwinning[0]] == boardState[positionwinning[1]] && boardState[positionwinning[1]] == boardState[positionwinning[2]] && boardState[positionwinning[2]] != 2){
                String winner ;
                if (boardState[positionwinning[2]] == 0) winner = "red";
                else winner = "blue";
                showDialog(winner);
                isContinue = false;
                isOver = true;
            }

        }
    }

    public void isDraw(int roundcount, boolean isOver){ // Draw
        if (roundcount == 9 && isOver == false ){
            showDialog("draw");
        }
    }

    public void Play(ImageView chess){ //action when playing
        roundcount++;
        int position = Integer.parseInt(chess.getTag().toString());
        if (boardState[position] == 2 && isContinue) {
            chess.setTranslationX(-1000f);
            boardState[position] = player;
            if (player == 0) {
                chess.setImageResource(R.mipmap.chuneon);
                player = 1;
            } else {
                chess.setImageResource(R.mipmap.chuoneon);
                player = 0;
            }
            chess.animate().translationXBy(1000f).rotationBy(360).setDuration(300);
        }
    }

}
