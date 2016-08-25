package com.akshaykant.com.twom_2.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;


@Database(version = MovieDatabase.VERSION)
public class MovieDatabase {

    public static final int VERSION = 1;

    @Table(MovieColumns.class)
    public static final String MOVIE = "movie";

    @Table(TrailerColumns.class)
    public static final String TRAILER = "trailer";

    @Table(ReviewColumns.class)
    public static final String REVIEW = "review";
}
