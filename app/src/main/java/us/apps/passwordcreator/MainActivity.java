package us.apps.passwordcreator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    private final static String TAG="MainActivity";
    private TextView password;
    private EditText keyword;
    private RadioGroup optionGroup;
    private RadioButton optW,optN,optS;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ExtendedFloatingActionButton createBtn = (ExtendedFloatingActionButton) findViewById(R.id.createBtn);
        CardView copyCardView = (CardView) findViewById(R.id.copyBtn);
        password=(TextView)findViewById(R.id.passwordDis);
        keyword=(EditText)findViewById(R.id.keyword);
        optionGroup=(RadioGroup)findViewById(R.id.optionGroup);
        optW=(RadioButton)findViewById(R.id.weakLvl);
        optN=(RadioButton)findViewById(R.id.normalLvl);
        optS=(RadioButton)findViewById(R.id.strongLvl);

        copyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= String.valueOf(password.getText());
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password:", code);
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this,R.string.copied,Toast.LENGTH_LONG).show();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keywordTxt=keyword.getText().toString();
                if (TextUtils.isEmpty(keywordTxt)) {
                    keyword.setError("Enter any word!");
                    return;
                }
                if (keywordTxt.length()<6) {
                    keyword.setError("World length should be 6 character long!");
                    return;
                }

                int selectedId = optionGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                if (radioButton ==optW){
                    difficulty= "weak";
                    levelSelector(difficulty);
                }
                else if (radioButton ==optN){
                    difficulty= "normal";
                    levelSelector(difficulty);
                }
                else if (radioButton ==optS){
                    difficulty= "strong";
                    levelSelector(difficulty);
                }
                else {
                    Toast.makeText(MainActivity.this,R.string.noneLvlDis,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void levelSelector(String level){
        String[] sign ={"!","#","@","&","*"};
        String unqCHARAC,unqALPHA,unqNUM;
        int position;
        char characterAlpha;

        Random randNum = new Random(),randNumTwice = new Random(),rABC = new Random(),random=new Random();

        unqNUM=String.valueOf(randNum.nextInt(99))+String.valueOf(randNumTwice.nextInt(9));
        position= random.nextInt(4);
        unqCHARAC=sign[position];

        characterAlpha=(char) (rABC.nextInt(26)+'a');
        unqALPHA=String.valueOf(characterAlpha).toUpperCase();

        String key= String.valueOf(keyword.getText());
//        Log.d(TAG, "1st num:"+randNum+" |2nd num:"+randNumTwice+" |array:"+random);

        switch (String.valueOf(level)){
            case "weak":
                passCreator(key,unqNUM,"","");
                break;
            case "normal":
                passCreator(key,unqNUM,unqALPHA,"");
                break;
            case "strong":
                passCreator(key,unqNUM,unqALPHA,unqCHARAC);
                break;
        }
    }

    private void passCreator(String extraKey,String rNum,String rLet,String rChar){
        String hify=extraKey+rNum+rLet+rChar;
//        Log.d(TAG, "passCreator: ");
        password.setText(hify);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}