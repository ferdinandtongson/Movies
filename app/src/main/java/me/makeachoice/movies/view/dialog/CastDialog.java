package me.makeachoice.movies.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.item.CastItem;


/**
 * CastDialog extends Dialog and is used to display a cast member of the movie
 *
 * It uses other classes in creating the Dialog:
 *      DetailHelper.ViewHolder - holds the layout and child view ids of the Dialog
 *
 * Methods from Dialog:
 *      void onCreate(Bundle) - called when the dialog is shown
 */
public class CastDialog extends Dialog{

/**************************************************************************************************/
/**
 * Class Variables
 *      Activity mActivity - activity displaying the dialog
 *      CastItem mReview - cast item data to be displayed by the dialog
 */
/**************************************************************************************************/

    //mActivity - activity displaying the dialog
    private Activity mActivity;

    //mCast - cast item data to be displayed by the dialog
    private CastItem mCast;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewDialog(Activity, ReviewItem) - constructor
 * @param activity - activity showing the dialog
 * @param cast - cast item data to be displayed by the dialog
 */
    public CastDialog(Activity activity, CastItem cast) {
        super(activity);
        //save current activity displaying dialog to class variable
        mActivity = activity;

        //save cast item data to class variable
        mCast = cast;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Dialog Class Methods:
 *      void onCreate(Bundle) - called before Dialog is shown to user
 */
/**************************************************************************************************/
/**
 * void onCreate(Bundle) - called before Dialog is shown to user
 * @param savedInstanceState - bundle data from activity
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove Title from dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //get set layout
        setContentView(DetailHelper.DIA_CAST_LAYOUT_ID);

        //get textView for actors' name
        TextView txtActor = (TextView)findViewById(DetailHelper.DIA_CAST_TXT_ACTOR);
        //set author
        txtActor.setText(mCast.name);

        //get textView for character in movie
        TextView txtCharacter = (TextView)findViewById(DetailHelper.DIA_CAST_TXT_CHARACTER);
        //set review
        txtCharacter.setText(mCast.character);



        //get imageView for actors' photo
        ImageView imgActor = (ImageView)findViewById(DetailHelper.DIA_CAST_IMG_ACTOR);

        Picasso.with(mActivity)
                .load(mCast.profilePath)
                .placeholder(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .error(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .into(imgActor);


    }

/**************************************************************************************************/

}