package com.authentication.Authenitication.Authorization.service;

public interface AuthorizationService {

    void authorize(String permission);
    void authorize(String permission,Long resourceId);

}
