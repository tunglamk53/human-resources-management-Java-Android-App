package com.example.hrmanagement.TableSchema;

public interface SchemaDocument {
    public static final String TABLE_DOCUMENT = "TAB_DOCUMENT";
    static final String COLUMN_DOC_ID = "doc_id";
    static final String COLUMN_DOC_BIN = "doc_bin";


    public static final String SQL_CREATE_DOCUMENT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DOCUMENT
            + " ("
            + COLUMN_DOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DOC_BIN + " INTEGER"
            + ")";

    public static final String SQL_DROP_DOCUMENT = "DROP TABLE IF EXISTS " + TABLE_DOCUMENT;

    String[] DOCUMENT_COLUMNS = new String[] { COLUMN_DOC_ID, COLUMN_DOC_BIN };
}
