package com.jar.storeLedger.service;

import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.entity.repository.KiranaStoreRepository;
import com.jar.storeLedger.mapper.KiranaStoreMapper;
import com.jar.storeLedger.model.KiranaStoreRequest;
import com.jar.storeLedger.model.KiranaStoreResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KiranaStoreService {

    KiranaStoreRepository kiranaStoreRepository;

    public KiranaStoreResponse addKiranaStore(KiranaStoreRequest kiranaStoreRequest) {
        KiranaStore kiranaStore = new KiranaStore();
        kiranaStore.setStoreName(kiranaStoreRequest.getStoreName());
        kiranaStore.setMobile(kiranaStoreRequest.getMobile());
        kiranaStoreRepository.save(kiranaStore);
        return KiranaStoreMapper.convertToKiranaStoreResponse(kiranaStore);
    }

}
