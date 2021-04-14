package edu.byu.cs240.familymapclient.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import edu.byu.cs240.familymapclient.R;

public final class IconUtils {

    public static Drawable getGenderIcon(Context context, String gender) {
        if (gender.equals("f")) {
            return new IconDrawable(context, FontAwesomeIcons.fa_female).colorRes(R.color.female_pink).sizeDp(40);
        }
        return new IconDrawable(context, FontAwesomeIcons.fa_male).colorRes(R.color.male_blue).sizeDp(40);
    }

    public static Drawable getEventIcon(Context context) {
        return new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.black).sizeDp(40);
    }
}
