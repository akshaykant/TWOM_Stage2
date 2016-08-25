package com.akshaykant.com.twom_2.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akshaykant.com.twom_2.entity.Review;


public class ReviewCursor extends CursorWrapper {

    public ReviewCursor(Cursor cursor) {
        super(cursor);
    }

    public Review getReview() {
        Review review = new Review();
        review._id = getInt(getColumnIndex(ReviewColumns._ID));
        review.author = getString(getColumnIndex(ReviewColumns.AUTHOR));
        review.url = getString(getColumnIndex(ReviewColumns.URL));
        review.movie = getInt(getColumnIndex(ReviewColumns.MOVIE));

        return review;
    }
}
