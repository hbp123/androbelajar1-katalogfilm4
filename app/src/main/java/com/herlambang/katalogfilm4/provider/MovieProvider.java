package com.herlambang.katalogfilm4.provider;import android.content.ContentProvider;import android.content.ContentValues;import android.content.UriMatcher;import android.database.Cursor;import android.net.Uri;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import com.herlambang.katalogfilm4.data.local.DatabaseContract;import com.herlambang.katalogfilm4.data.local.MovieHelper;import static com.herlambang.katalogfilm4.data.local.DatabaseContract.AUTHORITY;import static com.herlambang.katalogfilm4.data.local.DatabaseContract.CONTENT_URI;public class MovieProvider extends ContentProvider{    static final int Film = 1;    static final int film_id = 2;    MovieHelper movieHelper;    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);    static {        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE, Film);        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE+"/#", film_id);    }    @Override    public boolean onCreate() {        movieHelper = new MovieHelper(getContext());        movieHelper.open();        return true;    }    @Nullable    @Override    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {        Cursor cursor;        switch (uriMatcher.match(uri)){            case Film :                cursor= movieHelper.queryProvider();                break;            case film_id :                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());                break;            default:                cursor = null;                break;        }        if (cursor != null){            cursor.setNotificationUri(getContext().getContentResolver(), uri);        }        return cursor;    }    @Nullable    @Override    public String getType(@NonNull Uri uri) {        return null;    }    @Nullable    @Override    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {        long tambah;        switch (uriMatcher.match(uri)){            case Film :                tambah = movieHelper.insertProvider(values);                break;            default :                tambah = 0;                break;        }        if (tambah > 0){            getContext().getContentResolver().notifyChange(uri, null);        }        return Uri.parse(CONTENT_URI +"/"+ tambah);    }    @Override    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {        int hapus;        switch (uriMatcher.match(uri)){            case film_id :                hapus = movieHelper.deleteProvider(uri.getLastPathSegment());                break;            default:                hapus = 0;                break;        }        if (hapus > 0){            getContext().getContentResolver().notifyChange(uri, null);        }        return hapus;    }    @Override    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {        int ubah;        switch (uriMatcher.match(uri)){            case film_id :                ubah = movieHelper.updateProvider(uri.getLastPathSegment(), values);                break;            default:                ubah = 0;                break;        }        if (ubah > 0){            getContext().getContentResolver().notifyChange(uri, null);        }        return ubah;    }}