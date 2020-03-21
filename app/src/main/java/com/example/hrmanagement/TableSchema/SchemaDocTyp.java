package com.example.hrmanagement.TableSchema;

public interface SchemaDocTyp {
    static final String TABLE_DOC_TYP = "TAB_DOC_TYP";

    public static final String SQL_CREATE_DOC_TYP = "CREATE TABLE IF NOT EXISTS " +
            TABLE_DOC_TYP +
            " (" +
            "doc_id INTEGER, type_id INTEGER, " +
            "FOREIGN KEY(doc_id) REFERENCES TAB_DOCUMENT(doc_id), " +
            "FOREIGN KEY(type_id) REFERENCES TAB_TYPE(type_id))";

    public static final String SQL_DROP_DOC_TYP = "DROP TABLE IF EXISTS " + TABLE_DOC_TYP;
}