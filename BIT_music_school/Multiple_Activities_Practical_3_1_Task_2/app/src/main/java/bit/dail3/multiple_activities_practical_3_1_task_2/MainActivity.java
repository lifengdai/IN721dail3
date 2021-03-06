package bit.dail3.multiple_activities_practical_3_1_task_2;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnConfirm;
    private ChoseSpinnerItem choseSpinnerItem;
    private WhichOneHasChecked whichOneHasChecked;
    private ConfirmEnrole confirmEnrole;
    private TextView txtvConfirmNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind the radiogroup with rgInstruments
        //set listener to the radiogroup
        RadioGroup rgInstruments = (RadioGroup) findViewById(R.id.rgInstruments);
        whichOneHasChecked = new WhichOneHasChecked();
        rgInstruments.setOnCheckedChangeListener(whichOneHasChecked);

        //Bind the button with btnConfirm
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        Spinner spnMonth = (Spinner) findViewById(R.id.spnMonth);
        ArrayAdapter<CharSequence> spnMonthAdapter = ArrayAdapter.createFromResource(this,
                R.array.MonthForSpinner, android.R.layout.simple_spinner_item);
        spnMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(spnMonthAdapter);
        choseSpinnerItem = new ChoseSpinnerItem();
        spnMonth.setOnItemSelectedListener(choseSpinnerItem);
    }

    //pass whatever the view to ConfirmEnrole
    //and set onclicklistener
    private void passControlToButton(View view)
    {
        confirmEnrole = new ConfirmEnrole(view);
        btnConfirm.setEnabled(true);
        btnConfirm.setOnClickListener(confirmEnrole);
    }

    //Bind textview and settext to it
    private void passNotificationAfterButtonClicked(String notification)
    {
        //Bind the textview with txtvConfirmNotification
        txtvConfirmNotification = (TextView) findViewById(R.id.txtvConfirmNotification);
        String finalReponed = notification + choseSpinnerItem.whichMonth;
        txtvConfirmNotification.setText(finalReponed);
    }

    public String course()
    {
        return getResources().getString(R.string.check)+ " " + confirmEnrole.instrumentName;
    }

    public void giveMeMyData(boolean YorN)
    {
        if (YorN)
        {
            String notification = getResources().getString(R.string.EnrolledNotification) + " " + confirmEnrole.instrumentName + " ";
            //pass the string
            passNotificationAfterButtonClicked(notification);
        }
        else
        {
            txtvConfirmNotification.setText(getResources().getString(R.string.oh));
        }
    }

    //Inner class that implements the onclicklistener
    class ConfirmEnrole implements View.OnClickListener, IShowDialogFragment
    {

        private View control;
        private String instrumentName;

        //Constructor that passes a control
        public ConfirmEnrole(View control)
        {
            this.control = control;
        }

        //get that control's text and store it
        @Override
        public void onClick(View v) {
            instrumentName = ((RadioButton) control).getText().toString();
            showDialog();
        }

        @Override
        public void showDialog() {
            FragmentManager fm = getFragmentManager();
            ConfirmDialogFragment cfdf = new ConfirmDialogFragment();
            cfdf.show(fm, getResources().getString(R.string.Confirm));
        }
    }

    //Inner class that implements oncheckedchangelistener
    class WhichOneHasChecked implements RadioGroup.OnCheckedChangeListener
    {
        private RadioButton radioButton;

        //get the radio button that checked
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            radioButton = (RadioButton) findViewById(checkedId);
            //pass that radiobutton
            passControlToButton(radioButton);
        }
    }

    class ChoseSpinnerItem implements AdapterView.OnItemSelectedListener
    {

        private String whichMonth;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            whichMonth = getResources().getString(R.string.WordIn) + " "
                    + parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(MainActivity.this, R.string.Worning, Toast.LENGTH_SHORT).show();
        }
    }
}
