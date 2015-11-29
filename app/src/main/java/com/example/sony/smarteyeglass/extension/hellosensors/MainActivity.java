package io.keiji.gyro2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int i=0,flag=0,count=0;
    double result;
    double rresult=-2;
    double startTime;
    double stopTime;
    double sec;
    double one_beat;
    double bpm;
    double kekka;
    MediaPlayer mp1=null,mp2=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp1 = MediaPlayer.create(this, R.raw.kouryou);
        mp2 = MediaPlayer.create(this, R.raw.daruma);
        // ボタンのオブジェクトを取得
        Button btn = (Button)findViewById(R.id.button);

        // クリックイベントを受け取れるようにする
        btn.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                // ここにクリックされたときの処理を記述
                //ID:txt01にテキストを表示
                TextView warota = (TextView) findViewById(R.id.txt01);
                //これが入力する値(ｙなど)
                result = Math.sin(i);
                //resultという変数を表示させる
                warota.setText(String.valueOf(result));
                //サインを動かすための処理なのでこれはいらない
                i+=30;

                //一つ前の値が現在の値を下回った瞬間に
                if(rresult>=result && flag==0) {
                    //計測開始
                    startTime = System.currentTimeMillis();
                    flag=1;
                }
                else if(rresult>=result && flag==1) {
                    //何回かビートを取る
                    count++;
                }

                //この回数分で
                if(count==15){
                    //計測終了＆BPM解析開始
                    stopTime = System.currentTimeMillis();
                    sec=stopTime-startTime;
                    one_beat=sec;
                    //後ろの3.25はアンドロイドでの微調整値なのでグラス用に書き直す必要あり
                    //アンドロイドでは4回タップでsinでの1回計測だったため、およそ4倍してる
                    bpm=60.0/one_beat*10000*3.25;
                    kekka=bpm;

                }
                else if(count>15) {
                    //計測終了後、要らぬカウントでBPM解析の精度を落とさないための予防策
                    //ここから別な処理へ移動させるといいかも?
                    TextView yabai = (TextView) findViewById(R.id.txt02);
                    yabai.setText(String.valueOf(kekka));

                    //計測したBPMに応じて再生
                    if(kekka<=150){
                        //再生するための関数ももしかしたらglass用に書き換える必要があるかも
                        mp1.start();
                    }
                    else if(150<kekka){
                        mp2.start();
                    }

                }

                rresult=result;
            }
        });
    }

}
