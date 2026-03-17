package com.authentication.Authenitication.Authorization.Validator;


import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BoundaryValidatorRegistry {

    private final Map<Resource,BoundaryValidator>validatorMap=new HashMap<>();

    public BoundaryValidatorRegistry(List<BoundaryValidator> validators){

        for(BoundaryValidator validator:validators){
            validatorMap.put(validator.getSupportedResource(),validator);
        }
    }

    public BoundaryValidator getValidator(Resource resource){
        BoundaryValidator validator=validatorMap.get(resource);
        if(validator==null){
            throw new AppException("AUTH_VALIDATOR_NOT_FOUND");
        }
        return validator;

    }

}
