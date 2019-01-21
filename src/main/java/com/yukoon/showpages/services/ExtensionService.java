package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Extension;
import com.yukoon.showpages.repos.ExtensionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ExtensionService {
    @Autowired
    private ExtensionRepo extensionRepo;

    public List<Extension> findAllByBuesinessId(Integer id) {
        return extensionRepo.findAllByBusinessId(id);
    }

    //根据排序返回扩展链接
    public List<Extension> findAllAndSortByBuesinessId(Integer id) {
        List<Extension> extensions = findAllByBuesinessId(id);
        Collections.sort(extensions, new Comparator<Extension>() {
            @Override
            public int compare(Extension o1, Extension o2) {
                if (null == o1.getOrder() || null == o2.getOrder()) return -1;
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
        return extensions;
    }

    //获得该用户下所有扩展链接排序最后的数值
    public Integer getLastOrderByBusinessId(Integer id) {
        List<Extension> extensions = findAllAndSortByBuesinessId(id);
        if (extensions.size() < 1) return 1;
        Integer lastOrder = extensions.get(extensions.size()-1).getOrder();
        return lastOrder+1;
    }

    public Extension findById(Integer id) {
        return extensionRepo.findOne(id);
    }

    @Transactional
    public Extension saveExtension(Extension extension) {
        return extensionRepo.saveAndFlush(extension);
    }

    @Transactional
    public void delExtension(Integer id) {
        extensionRepo.delete(id);
    }
}
