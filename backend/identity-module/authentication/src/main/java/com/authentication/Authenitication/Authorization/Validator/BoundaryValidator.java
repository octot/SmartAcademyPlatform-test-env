package com.authentication.Authenitication.Authorization.Validator;

import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;

import java.util.UUID;

public interface BoundaryValidator {

    Resource getSupportedResource();

    void validate(UUID userId, Long resourceId, Scope scope);


}
