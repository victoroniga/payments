package tech.form3.payments.ejb.model;

/**
 * Created by Victor Oniga
 */
public enum ErrorCode {
    // Unexpected error; this might be a programming issue
    UNEXPECTED_ERROR,

    // Unexpected database error encountered; this could be related to a grammar exception or database offlie
    UNEXPECTED_DATABASE_ERROR,

    ENTITY_NOT_FOUND,

    CONSTRAINTS,

    OBJECT_ALREADY_MODIFIED,

    UNAUTHORIZED
}
