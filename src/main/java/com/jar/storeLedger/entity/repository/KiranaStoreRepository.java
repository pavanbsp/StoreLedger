package com.jar.storeLedger.entity.repository;

import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.exception.ErrorCode;
import org.springframework.stereotype.Repository;

@Repository
public class KiranaStoreRepository extends AbstractRepository<KiranaStore> {

    public KiranaStoreRepository() {
        super(KiranaStore.class);
    }

    public KiranaStore get(Long kiranaStoreId) throws ApiException {
        KiranaStore kiranaStore = select(kiranaStoreId);
        if (kiranaStore == null) {
            throw new ApiException("Kirana store doesn't exists", ErrorCode.NOT_FOUND);
        }
        return kiranaStore;
    }

    public KiranaStore getByMobile(String mobile) throws ApiException {
        KiranaStore kiranaStore = select("mobile", mobile);
        if (kiranaStore == null) {
            throw new ApiException("No kirana store exists with the mobile number", ErrorCode.NOT_FOUND);
        }
        return kiranaStore;
    }

}