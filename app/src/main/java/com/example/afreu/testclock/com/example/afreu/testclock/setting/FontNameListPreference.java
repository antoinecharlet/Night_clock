package com.example.afreu.testclock.com.example.afreu.testclock.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.example.afreu.testclock.R;

import java.util.Locale;

/**
 * Created by tou-t on 05/02/2017.
 */

public class FontNameListPreference extends ListPreference {
    private int mClickedDialogEntryIndex;

    public FontNameListPreference(Context context) {
        super(context);
    }

    public FontNameListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        if (getEntries() == null || getEntryValues() == null) {
            super.onPrepareDialogBuilder(builder);
            return;
        }


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.preference_ar_font_name, getEntries()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //float fontSizePx = getContext().getResources().getDimension(R.dimen.def_font_size_list_pref);
                Typeface tf = null;
                CheckedTextView view = (CheckedTextView) convertView;
                if (view == null) {
                    view = (CheckedTextView) View.inflate(getContext(), R.layout.preference_ar_font_name, null);
                }
                String[] font_name = getContext().getResources().getStringArray(R.array.pref_font_list_titles_values_ar);
                AssetManager am = getContext().getAssets();
                tf = Typeface.createFromAsset(am,
                        String.format(Locale.FRANCE, "fonts/%s", font_name[position]));
                view.setText(getEntries()[position]);
                //view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizePx);
                view.setTypeface(tf);
                return view;
            }
        };

        mClickedDialogEntryIndex = findIndexOfValue(getValue());
        builder.setSingleChoiceItems(adapter, mClickedDialogEntryIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClickedDialogEntryIndex = which;
                FontNameListPreference.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(null, null);


    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult && mClickedDialogEntryIndex >= 0 && getEntryValues() != null) {
            String val = getEntryValues()[mClickedDialogEntryIndex].toString();
            if (callChangeListener(val)) {
                setValue(val);
            }
        }
    }
}
