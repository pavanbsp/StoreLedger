package com.jar.storeLedger.controller;

import com.jar.storeLedger.model.KiranaStoreRequest;
import com.jar.storeLedger.model.KiranaStoreResponse;
import com.jar.storeLedger.service.KiranaStoreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Endpoint to add a new Kirana store.
     *
     * @param kiranaStoreRequest The request containing details of the Kirana store to be added.
     * @return A response containing details of the added Kirana store.
     */
    @PreAuthorize("@permissionService.verifyAdminAccess()")
    @PostMapping("add/store")
    public KiranaStoreResponse addStore(@RequestBody KiranaStoreRequest kiranaStoreRequest) {
        return kiranaStoreService.addKiranaStore(kiranaStoreRequest);
    }

}
