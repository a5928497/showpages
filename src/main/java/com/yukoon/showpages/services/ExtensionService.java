package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Extension;
import com.yukoon.showpages.repos.ExtensionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionService {
    @Autowired
    private ExtensionRepo extensionRepo;

    public List<Extension> findAllbyBuesinessId(Integer id) {
        return extensionRepo.findAllByBusinessId(id);
    }
}
