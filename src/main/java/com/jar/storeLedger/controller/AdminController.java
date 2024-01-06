package com.jar.storeLedger.controller;

import com.jar.storeLedger.model.KiranaStoreRequest;
import com.jar.storeLedger.model.KiranaStoreResponse;
import com.jar.storeLedger.service.KiranaStoreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    KiranaStoreService kiranaStoreService;

    @PostMapping("add/store")
    public KiranaStoreResponse addStore(@RequestBody KiranaStoreRequest kiranaStoreRequest) {
        return kiranaStoreService.addKiranaStore(kiranaStoreRequest);
    }

}
