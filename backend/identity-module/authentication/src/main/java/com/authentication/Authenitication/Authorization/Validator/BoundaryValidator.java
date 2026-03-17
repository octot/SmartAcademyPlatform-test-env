package com.authentication.Authenitication.Authorization.Validator;

import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;

public interface BoundaryValidator {

    Resource getSupportedResource();

    void validate(Long userId, Long resourceId, Scope scope);


}
