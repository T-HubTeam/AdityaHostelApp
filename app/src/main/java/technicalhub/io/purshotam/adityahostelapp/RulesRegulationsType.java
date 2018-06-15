package technicalhub.io.purshotam.adityahostelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RulesRegulationsType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_regulations_type);
    }

    public void AdmissionAndTermination(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Admission and Termination");
        startActivity(intent);
    }

    public void AttendanceHostelTimingsLeaveAndVisitors(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Attendance, Hostel Timings, Leave and Visitors");
        startActivity(intent);
    }

    public void MessAndDiningFacility(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Mess and Dining Facility");
        startActivity(intent);
    }

    public void General(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","General");
        startActivity(intent);
    }

    public void StrictlyProhibitedActivities(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Strictly Prohibited Activities");
        startActivity(intent);
    }

    public void ViolationOfHostelRules(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Violation of Hostel Rules");
        startActivity(intent);
    }

    public void SuggestionBoxRegister(View view) {
        Intent intent = new Intent(RulesRegulationsType.this,RulesRegulation.class);
        intent.putExtra("category","Suggestion Box/Register");
        startActivity(intent);
    }
}
