package jp.ac.titech.hum.mr.android.sample.textviewarrange;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final String SAMPLE_TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
            "It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. " +
            "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
            "and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 描画する Layout を取得
        RelativeLayout contentWrapper = (RelativeLayout) findViewById(R.id.content_wrapper);

        // 画面の横幅を取得
        final Point size = new Point();
        final WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        display.getSize(size);

        // Padding を考慮して表示する横幅 (layoutWidth) を決定
        int layoutWidth = size.x;
        layoutWidth -= contentWrapper.getPaddingLeft();
        layoutWidth -= contentWrapper.getPaddingRight();

        // 現時点での行の横幅 (totalWidth) を準備
        int totalWidth = 0;

        // ダミーテキストを配列化
        final String[] words = SAMPLE_TEXT.split(" ");

        for (int i = 0; i < words.length; i++) {
            final TextView wordTextView = new TextView(this);
            wordTextView.setText(words[i] + " ");   // Margin 算出が面倒なので普通に空白をつける
            wordTextView.setId(i + 1);  // id は 1 以上で無いとダメ

            // 要素の横幅を取得
            int thisWidth = (int) Layout.getDesiredWidth(wordTextView.getText(), wordTextView.getPaint());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            // 要素を追加してレイアウトの横幅を超えないかをチェック
            if (layoutWidth - (totalWidth + thisWidth) > 0) {
                // 右に追加
                params.addRule(RelativeLayout.ALIGN_TOP, i);
                params.addRule(RelativeLayout.RIGHT_OF, i);
            } else {
                totalWidth = 0; // 行の横幅をリセット

                // 改行して新規行に追加
                params.addRule(RelativeLayout.BELOW, i);
                params.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
            }

            contentWrapper.addView(wordTextView, params);
            totalWidth += thisWidth;
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
}
