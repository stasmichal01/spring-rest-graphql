package com.example.restandgraphql.book;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BookNotFoundException extends RuntimeException implements GraphQLError {
    private final Map<String, Object> extensions = new HashMap<>();
    public BookNotFoundException(String message, Long invalidBookId) {
        super(message);
        extensions.put("invalidBookId", invalidBookId);
    }
    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }
    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }
    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
