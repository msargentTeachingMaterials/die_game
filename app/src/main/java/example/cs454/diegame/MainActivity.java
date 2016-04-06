package example.cs454.diegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int playerScore, computerScore, playerTurnScore, computerTurnScore, roll;
    private final String PLAYER_SCORE = "Player score: ";
    private final String COMPUTER_SCORE = " Computer score: ";
    private final String PLAYER_ROLL = "your turn score: ";
    private final String COMPUTER_ROLL = "computer turn score: ";
    private Random random = new Random();
    private ImageView die;
    private TextView score;
    private Button rollButton, hold, reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        die = (ImageView) findViewById(R.id.die);
        score = (TextView) findViewById(R.id.score);
        rollButton = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);


    }

    public void onReset(View v) {
        playerTurnScore = playerScore = computerTurnScore = computerScore = 0;
        score.setText(PLAYER_SCORE + playerTurnScore + COMPUTER_SCORE + computerScore);
        setDieImage(1);
    }

    public void onHold(View v) {
        playerScore += playerTurnScore;
        score.setText(PLAYER_SCORE + playerTurnScore + COMPUTER_SCORE + computerScore);
        playerTurnScore = 0;
        endPlayer();
    }

    public void onRoll(View v) {
        roll = 1 + random.nextInt(5);
        setDieImage(roll);
        if (roll != 1) {
            playerTurnScore += roll;
            setPlayerTurnText(playerScore, computerScore, playerTurnScore);

        } else {
            playerTurnScore = 0;
            setPlayerTurnText(playerScore, computerScore, playerTurnScore);
            endPlayer();

        }

    }


    public boolean doComputerTurn() {
        boolean result = false;
        int roll = 1 + random.nextInt(6);
        setDieImage(roll);
        if (roll != 1) {
            computerTurnScore += roll;
            result = computerTurnScore < 11 ? true : false;

        } else {
            computerTurnScore = 0;
            result = false;
        }
        setComputerTurnText(playerScore, computerScore, computerTurnScore);
        return result;
    }

    public void endPlayer(){

        enableButtons(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean repeat = doComputerTurn();

                if (repeat) {
                    handler.postDelayed(this, 500);

                }
                else {
                    computerScore += computerTurnScore;
                    computerTurnScore = 0;
                    setPlayerTurnText(playerScore, computerScore, playerTurnScore);
                }

            }
        }, 500);

        enableButtons(true);
        setPlayerTurnText(playerScore, computerScore, playerTurnScore);

    }

    public void enableButtons(boolean b) {
        rollButton.setEnabled(b);
        hold.setEnabled(b);
        reset.setEnabled(b);
    }

    public void setPlayerTurnText(int yours, int comps, int turn) {
        score.setText(PLAYER_SCORE + yours + COMPUTER_SCORE + comps + "\n" + PLAYER_ROLL + turn);
    }

    public void setComputerTurnText(int yours, int comps, int turn) {
        score.setText(PLAYER_SCORE + yours + COMPUTER_SCORE + comps + "\n" + COMPUTER_ROLL + turn);
    }


    public void setDieImage(int roll) {
        switch (roll) {
            case 1:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice1));
                break;

            case 2:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice2));
                break;

            case 3:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice3));
                break;

            case 4:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice4));
                break;

            case 5:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice5));
                break;

            case 6:
                die.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dice6));
                break;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
