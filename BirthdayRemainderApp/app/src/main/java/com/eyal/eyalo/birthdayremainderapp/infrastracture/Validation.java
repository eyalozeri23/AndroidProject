package com.eyal.eyalo.birthdayremainderapp.infrastracture;

import android.widget.EditText;

/**
 * Created by eyalo on 7/4/2017.
 */

public class Validation {

    private static boolean validateNoSpace(String str) {
        return !str.isEmpty() && !str.contains(" ");
    }


    // -- check if the Edit Text is not empty -- //
    public static boolean validateLogin(EditText firstName, EditText lastName, EditText phone, EditText birthday, EditText birthdayText) {
        boolean valid = true;
        String first = firstName.getText().toString().trim();
        String last = lastName.getText().toString().trim();
        String number = phone.getText().toString().trim();
        String dob = birthday.getText().toString().trim();
        String BdayText = birthdayText.getText().toString().trim();
        if (!validateNoSpace(first)) {
            firstName.setError("text cannot stay empty");
            valid = false;
        }
        if (!validateNoSpace(last)) {
            lastName.setError("text cannot stay empty");
            valid = false;
        }
        if (!validateNoSpace(number)) {
            phone.setError("text cannot stay empty");
            valid = false;
        }
        if (!validateNoSpace(dob)) {
            birthday.setError("text cannot stay empty");
            valid = false;
        }
        if (!validateNoSpace(BdayText)) {
            birthdayText.setError("text cannot stay empty");
            valid = false;
        }
        return valid;
    }

}
