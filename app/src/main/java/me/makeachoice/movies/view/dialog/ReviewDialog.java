package me.makeachoice.movies.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.model.item.ReviewItem;


/**
 * ReviewDialog extends Dialog and is used to display movie reviews of a Movie
 *
 * It uses other classes in creating the Dialog:
 *      DetailHelper.ViewHolder - holds the layout and child view ids of the Dialog
 *
 * Methods from Dialog:
 *      void onCreate(Bundle) - called when the dialog is shown
 */
public class ReviewDialog extends Dialog{

/**************************************************************************************************/
/**
 * Class Variables
 *      Activity mActivity - activity displaying the dialog
 *      ReviewItem mReview - review item data to be displayed by the dialog
 */
/**************************************************************************************************/

    //mActivity - activity displaying the dialog
    private Activity mActivity;

    //mReview - review item data to be displayed by the dialog
    private ReviewItem mReview;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewDialog(Activity, ReviewItem) - constructor
 * @param activity - activity showing the dialog
 * @param review - review item data to be displayed by the dialog
 */
    public ReviewDialog(Activity activity, ReviewItem review) {
        super(activity);
        //save current activity displaying dialog to class variable
        mActivity = activity;

        //save review item data to class variable
        mReview = review;
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
        setContentView(DetailHelper.DIA_REVIEW_LAYOUT_ID);

        //get textView for author
        TextView txtAuthor = (TextView)findViewById(DetailHelper.DIA_REVIEW_TXT_AUTHOR);
        //set author
        txtAuthor.setText(mReview.author);

        //get textView for review
        TextView txtReview = (TextView)findViewById(DetailHelper.DIA_REVIEW_TXT_REVIEW);
        //set review
        txtReview.setText(mReview.review);

    }

/**************************************************************************************************/

}