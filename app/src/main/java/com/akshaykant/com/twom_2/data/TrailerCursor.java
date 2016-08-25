package com.akshaykant.com.twom_2.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akshaykant.com.twom_2.entity.Trailer;


public class TrailerCursor extends CursorWrapper {

    public TrailerCursor(Cursor cursor) {
        super(cursor);
    }

    public Trailer getTrailer() {
        Trailer trailer = new Trailer();
        trailer._id = getInt(getColumnIndex(TrailerColumns._ID));
        trailer.key = getString(getColumnIndex(TrailerColumns.KEY));
        trailer.name = getString(getColumnIndex(TrailerColumns.NAME));
        trailer.movie = getInt(getColumnIndex(TrailerColumns.MOVIE));

        return trailer;
    }
}
