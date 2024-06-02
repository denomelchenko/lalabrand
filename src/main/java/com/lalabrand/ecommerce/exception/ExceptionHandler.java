package com.lalabrand.ecommerce.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        ErrorType errorType = determineErrorType(ex);
        String errorMessage = determineErrorMessage(ex, errorType);
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(errorMessage)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    private ErrorType determineErrorType(Throwable ex) {
        if (ex instanceof IllegalArgumentException
                || ex instanceof ConstraintViolationException
                || ex instanceof TokenExpiredException) {
            return ErrorType.BAD_REQUEST;
        } else if (ex instanceof BadCredentialsException) {
            return ErrorType.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException
                || ex instanceof org.springframework.security.access.AccessDeniedException) {
            return ErrorType.FORBIDDEN;
        } else if (ex instanceof EntityNotFoundException
                || ex instanceof UsernameNotFoundException
                || ex instanceof UserAlreadyExistException
                || ex instanceof org.springframework.security.core.userdetails.UsernameNotFoundException) {
            return ErrorType.NOT_FOUND;
        } else {
            return ErrorType.INTERNAL_ERROR;
        }
    }

    private String determineErrorMessage(Throwable ex, ErrorType errorType) {
        if (errorType == ErrorType.INTERNAL_ERROR) {
            logger.error("Error with message has handled: {}", ex.getMessage());
            return "An error occurred during the operation";
        }
        return ex.getLocalizedMessage();
    }
}
