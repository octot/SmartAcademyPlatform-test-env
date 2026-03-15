package com.authentication.Authenitication.Authorization.service;

public interface AuthorizationService {

    void authorize(Long userId,String permission);
}
