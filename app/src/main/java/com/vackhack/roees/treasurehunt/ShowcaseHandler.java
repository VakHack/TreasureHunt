package com.vackhack.roees.treasurehunt;

import android.app.Activity;
import android.util.Pair;
import android.view.View;
import java.util.Vector;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class ShowcaseHandler {
    private Vector<Pair<View, Pair<String, String>>> instructions;
    private Activity activity;
    private int num = 0;

    public ShowcaseHandler(Activity activity, Vector<Pair<View, Pair<String, String>>> instructions) {
        this.activity = activity;
        this.instructions = instructions;
    }

    public void initShowcase(){
        num = 0;
    }

    public void callRelevantShowcase() {
        if(instructions.size()<=num) return;
        Pair<View, Pair<String, String>> current = instructions.get(num);
        new MaterialTapTargetPrompt.Builder(activity)
                .setTarget(current.first)
                .setPrimaryText(current.second.first)
                .setSecondaryText(current.second.second)
                .setFocalColour(244)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                            // User has pressed the prompt target
                        }
                    }
                })
                .show();

        num++;
    }
}
