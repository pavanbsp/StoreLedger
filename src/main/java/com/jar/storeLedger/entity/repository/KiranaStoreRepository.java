package com.jar.storeLedger.entity.repository;

import com.jar.storeLedger.entity.KiranaStore;
import org.springframework.stereotype.Repository;

@Repository
public class KiranaStoreRepository extends AbstractRepository<KiranaStore> {

    public KiranaStoreRepository() {
        super(KiranaStore.class);
    }

}