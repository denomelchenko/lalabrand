package com.lalabrand.ecommerce.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(getErrorType(ex))
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    private ErrorType getErrorType(Throwable ex) {
        if (ex instanceof ConstraintViolationException || ex instanceof IllegalArgumentException) {
            return ErrorType.BAD_REQUEST;
        } else if (ex instanceof BadCredentialsException) {
            return ErrorType.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            return ErrorType.FORBIDDEN;
        } else if (ex instanceof EntityNotFoundException || ex instanceof UsernameNotFoundException || ex instanceof UserAlreadyExistException) {
            return ErrorType.NOT_FOUND;
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }
}
