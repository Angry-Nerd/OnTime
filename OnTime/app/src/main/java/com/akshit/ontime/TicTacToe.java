package com.akshit.ontime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToe extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        final Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        SharedPreferences sharedPreferences=this.getSharedPreferences("Myfile", Context.MODE_PRIVATE);
        int s=sharedPreferences.getInt("scores",0);


        SharedPreferences.Editor editor=sharedPreferences.edit();

        if(player1Points>s)
            editor.putInt("scores",player1Points);
        editor.commit();
        //Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard1("Player 1 wins");
//        if(interstitialAd.isLoaded())
//            interstitialAd.show();
//        else
//            Toast.makeText(getApplicationContext(),"Ad not loaded yet",Toast.LENGTH_LONG).show();
    }

    private void player2Wins() {
        player2Points++;
        SharedPreferences sharedPreferences=this.getSharedPreferences("Myfile", Context.MODE_PRIVATE);
        int s=sharedPreferences.getInt("scores",0);


        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(player2Points>s)
            editor.putInt("scores",player1Points);
        editor.commit();
        //Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard1("Player 2 wins");
//        if(interstitialAd.isLoaded())
//            interstitialAd.show();
//        else
//            Toast.makeText(getApplicationContext(),"Ad not loaded yet",Toast.LENGTH_LONG).show();
    }

    private void draw() {
        //Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard1("Draw");

    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
    private void resetBoard1(String s1)
    {
        Toast.makeText(getApplicationContext(),"Thanks For Playing",Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Game is Over").setCancelable(false).setMessage(s1);
        builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetBoard();
            }
        });
        builder.show();
        roundCount = 0;
        player1Turn = true;

    }
}
