package pub.tanzby.easycolorselect;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ColorOperateBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        board=findViewById(R.id.ColorOperateBoard);
        board.setOnColorOperateListener(new ColorOperateBoard.OnColorOperateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onColorChange(int color) {
                tv.setText("changing: "+ String.format("%x",color));
                tv.setTextColor(color);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onColorSelect(int color) {
                tv.setText("select: "+ String.format("%x",color));
                tv.setTextColor(color);
            }
        });
    }
}
