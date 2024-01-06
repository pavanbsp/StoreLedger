package com.jar.storeLedger.service;

import com.jar.storeLedger.config.ApplicationProperties;
import com.jar.storeLedger.config.CustomAuthentication;
import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.entity.repository.KiranaStoreRepository;
import com.jar.storeLedger.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service("permissionService")
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    KiranaStoreRepository kiranaStoreRepository;
    ApplicationProperties applicationProperties;

    public boolean verifyAdminAccess() {
        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return Objects.equals(authentication.getPrincipal(), applicationProperties.getAdminMobile());
    }

    public boolean verifyAccess(Long kiranaStoreId) throws ApiException {
        KiranaStore kiranaStore = kiranaStoreRepository.get(kiranaStoreId);
        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return Objects.equals(authentication.getPrincipal(), kiranaStore.getMobile());
    }

}
