package com.icandothisallday.clickgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button startpage;
    ImageView clearButton;
    Button[] buttons=new Button[25];
    TextView findingNumber;
    TextView nowfinding;
    int count;
    char count2='A';//2단계 알파벳 초기값
    char count3='✰';//3단계 특수문자 초기값✰/✪

    Drawable cbalpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbalpha = ((ImageView)findViewById(R.id.clearbutton)).getBackground();
        cbalpha.setAlpha(50);//clearButton 투명도
        ActionBar ab=getSupportActionBar();
        ab.setTitle("");//액션바 제목 없애기

        startpage=findViewById(R.id.startpage);
        startpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               start(v);
            }
        });



        clearButton=findViewById(R.id.clearbutton);
        for(int i=0;i<25;i++){
            buttons[i]=findViewById(R.id.b01+i);
        } clearButton.setEnabled(false);
        findingNumber=findViewById(R.id.nowfidingnumber);
        nowfinding=findViewById(R.id.nowfinding);

        initial1();

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==75){
                    clear(v);
                }
                else if(count==50){
                    clear(v);
                }
                else if(count>50){
                    buttonClick3(v);
                }
                else if(count==25){
                    clear(v);
                }
                else if(count>25){
                    cbalpha.setAlpha(50);
                    clearButton.setEnabled(false);
                    buttonClick2(v);

                }
                else buttonClick(v);
            }
        };


        for(int i=0;i<buttons.length;i++) buttons[i].setOnClickListener(listener);


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==76)     ending(v);
                else  nextStage(v);
            }
        });


    }//onCreate

    public void start(View v){
        MediaPlayer player=MediaPlayer.create(this,R.raw.nextstage);
        player.start();
        startpage.setVisibility(View.GONE);
    }

    void initial1(){
        count=1;

        ArrayList<Integer> numbers= new ArrayList<>();
        for(int i=1;i<26;i++) numbers.add(i);
        Collections.shuffle(numbers);

        for(int i=0;i<buttons.length;i++){
            buttons[i].setText(numbers.get(i)+"");
        }
    }

    void initial2(){
        LinearLayout full=findViewById(R.id.fullActivity);
        full.setBackgroundResource(R.drawable.background2);

        ArrayList<Character> alphabets= new ArrayList<>();
        for(int i=0;i<25;i++) alphabets.add((char)(count2+i));
        Collections.shuffle(alphabets);
        for(int i=0;i<buttons.length;i++) {
            buttons[i].setVisibility(View.VISIBLE);
            buttons[i].setText((char)alphabets.get(i)+"");
        }
        findingNumber.setText(count2+"");
    }

    void initial3(){
        LinearLayout full=findViewById(R.id.fullActivity);
        full.setBackgroundResource(R.drawable.background3);

        ArrayList<Character> alphabets= new ArrayList<>();
        for(int i=0;i<25;i++) alphabets.add((char)(count3+i));
        Collections.shuffle(alphabets);
        for(int i=0;i<buttons.length;i++) {
            buttons[i].setVisibility(View.VISIBLE);
            buttons[i].setText((char)alphabets.get(i)+"");
        }
        findingNumber.setText(count3+"");
    }

    public void buttonClick(View v){
        Button clickedButton=(Button)v;
        String s=((Button) v).getText().toString();
        int userNum=Integer.parseInt(s);
        if(userNum==count){
            clickedButton.setText("");
            //clickedButton.setBackgroundColor(Color.TRANSPARENT);
            clickedButton.setVisibility(View.INVISIBLE);
            count++;
            findingNumber.setText(count+"");
        }
    }

    public void buttonClick2(View v){
        Button clickedButton=(Button)v;
        String s=((Button) v).getText().toString();
        char user=s.charAt(0);

        if(user==count2){
            clickedButton.setText("");
            //clickedButton.setBackgroundColor(Color.TRANSPARENT);
            clickedButton.setVisibility(View.INVISIBLE);
            findingNumber.setText(((char)(count2+1))+"");
            count2++;       count++;

        }
    }

    public void buttonClick3(View v){
        Button clickedButton=(Button)v;
        String s=((Button) v).getText().toString();
        char user=s.charAt(0);

        if(user==count3){
            clickedButton.setText("");
            //clickedButton.setBackgroundColor(Color.TRANSPARENT);
            clickedButton.setVisibility(View.INVISIBLE);
            findingNumber.setText(((char)(count3+1))+"");
            count3++;       count++;

        }
    }

    public void clear(View v){
        count++;
        findingNumber.setText("ಟ");
        cbalpha.setAlpha(250);
        clearButton.setEnabled(true);
        Button clickedButton=(Button)v;
        clickedButton.setText("");
        clickedButton.setVisibility(View.INVISIBLE);
    }

    public void nextStage(View v){
        MediaPlayer player=MediaPlayer.create(this,R.raw.clearsound);
        player.start();
        cbalpha.setAlpha(50);
        clearButton.setEnabled(false);
        if(count<50) initial2();
        else initial3();
    }

    public void ending(View v){
        LinearLayout full=findViewById(R.id.fullActivity);
        full.setBackgroundResource(R.drawable.endingbackground);
        MediaPlayer player=MediaPlayer.create(this,R.raw.endingsound);
        player.start();
        findingNumber.setText("");
        nowfinding.setText("");
        cbalpha.setAlpha(0);
    }


}//MainActivity..
