package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Extension;
import com.yukoon.showpages.repos.ExtensionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExtensionService {
    @Autowired
    private ExtensionRepo extensionRepo;

    public List<Extension> findAllbyBuesinessId(Integer id) {
        return extensionRepo.findAllByBusinessId(id);
    }

    public Extension findById(Integer id) {
        return extensionRepo.findOne(id);
    }

    @Transactional
    public Extension saveExtension(Extension extension) {
        return extensionRepo.saveAndFlush(extension);
    }
}
