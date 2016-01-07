package de.nkp_media.vertretungsplanappandroid.fab;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.nkp_media.vertretungsplanappandroid.MainActivity;
import de.nkp_media.vertretungsplanappandroid.R;

/**
 * Created by paul on 18.08.15.
 */
public class FABListener implements View.OnClickListener {
    private final MainActivity parentActivity;

    public FABListener(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onClick(View v) {
        /* Get ImageView Object */
//        ImageView iv = (ImageView) this.parentActivity.findViewById(R.id.imageButton);

        /* Create Animation */
        Animation rotation = AnimationUtils.loadAnimation(this.parentActivity, R.anim.button_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
//        this.parentActivity.feedDataManager.loadDataFromServer(iv);

        /* start Animation */
//        iv.startAnimation(rotation);
    }
}
