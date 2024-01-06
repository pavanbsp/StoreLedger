package com.jar.storeLedger.mapper;

import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.model.KiranaStoreResponse;

public class KiranaStoreMapper {

    public static KiranaStoreResponse convertToKiranaStoreResponse(KiranaStore kiranaStore) {
        KiranaStoreResponse response = new KiranaStoreResponse();
        response.setId(kiranaStore.getId());
        response.setStoreName(kiranaStore.getStoreName());
        response.setMobile(kiranaStore.getMobile());
        return response;
    }

}
