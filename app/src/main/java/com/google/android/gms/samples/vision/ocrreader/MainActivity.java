/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */
public class MainActivity extends AppCompatActivity {


    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    private ArrayList<JSONObject> businessCards = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //statusMessage = (TextView)findViewById(R.id.status_message);
        //textValue = (TextView)findViewById(R.id.text_value);

        //autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        //useFlash = (CompoundButton) findViewById(R.id.use_flash);

        testingJsonCards();
        ListAdapter businessCardAdapter = new CustomAdapter(this, businessCards);
        ListView BusinessCardList = (ListView) findViewById(R.id.BusinessCardList);
        BusinessCardList.setAdapter(businessCardAdapter);


        //When a business card/name is clicked

        BusinessCardList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        if (v.getId() == R.id.openCamera) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            //intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            //intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    Log.d("RESULT.ADOMAS: ", "Text Read Successfully");
                    //statusMessage.setText(R.string.ocr_success);
                    Log.d("RESULT.ADOMAS: ", text);
                } else {
                    Log.d("RESULT.ADOMAS: ", "No text Captured");
                }
            } else {
                Log.d("RESULT.ADOMAS: ", getString(R.string.ocr_error)+
                        CommonStatusCodes.getStatusCodeString(resultCode));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void testingJsonCards() {
        this.businessCards.add(DataParse.makeCard("Gauri", "Ramesh", "gauri.ramesh15@gmail.com", "MLH", "Hacker"));
        this.businessCards.add(DataParse.makeCard("Adomas", "Hassan", "adomas@gmail.com", "Blackrock", "Analyst"));
        this.businessCards.add(DataParse.makeCard("Theo", "Panagiotopoulos", "gdsjgsndg@gmail.com", "Microsoft", "Software Engineer"));
        this.businessCards.add(DataParse.makeCard("Troy", "Nguyen", "troynguyen8@gmail.com", "Goldman Sachs", "Investor"));
        this.businessCards.add(DataParse.makeCard("Bob", "Ramson", "eqgegqg@gmail.com", "Finra", "Data Analyst"));
        this.businessCards.add(DataParse.makeCard("Janice", "Kim", "fefqegg@gmail.com", "GM", "Engineer"));
        this.businessCards.add(DataParse.makeCard("Lisa", "Rodriguez", "ekqngjfnqjef@gmail.com", "Honeywell", "Contractor"));
        this.businessCards.add(DataParse.makeCard("Robert", "Cadon", "lkandfjkef@gmail.com", "GE Digital", "Engineer"));
        this.businessCards.add(DataParse.makeCard("Chris", "Henderson", "qkenfeqfefq@gmail.com", "Qualtrics", "Computer Engineer"));
        this.businessCards.add(DataParse.makeCard("Jackson", "Hill", "fkenfkeqg8@gmail.com", "Siemens", "Salesman"));
        this.businessCards.add(DataParse.makeCard("Emily", "Gold", "jqeknfjqekf@gmail.com", "Delta", "Pilot"));

        String cardJSON = DataParse.getJSONString(this.businessCards);
        this.businessCards = DataParse.parseCardJSON(cardJSON);
    }
}
