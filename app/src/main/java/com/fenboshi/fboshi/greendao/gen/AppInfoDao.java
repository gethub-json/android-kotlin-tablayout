package com.fenboshi.fboshi.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fenboshi.fboshi.db.AppInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APP_INFO".
*/
public class AppInfoDao extends AbstractDao<AppInfo, Void> {

    public static final String TABLENAME = "APP_INFO";

    /**
     * Properties of entity AppInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ShowLoadImages = new Property(0, boolean.class, "showLoadImages", false, "SHOW_LOAD_IMAGES");
    }


    public AppInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AppInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APP_INFO\" (" + //
                "\"SHOW_LOAD_IMAGES\" INTEGER NOT NULL );"); // 0: showLoadImages
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APP_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AppInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getShowLoadImages() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AppInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getShowLoadImages() ? 1L: 0L);
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public AppInfo readEntity(Cursor cursor, int offset) {
        AppInfo entity = new AppInfo( //
            cursor.getShort(offset + 0) != 0 // showLoadImages
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AppInfo entity, int offset) {
        entity.setShowLoadImages(cursor.getShort(offset + 0) != 0);
     }
    
    @Override
    protected final Void updateKeyAfterInsert(AppInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(AppInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(AppInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
