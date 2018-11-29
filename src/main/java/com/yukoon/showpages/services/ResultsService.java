package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Results;
import com.yukoon.showpages.repos.ResultsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultsService {
    @Autowired
    private ResultsRepo resultsRepo;

    @Transactional
    public Results saveResults(Results results) {
        if (results.getIsNew() == null) {
            results.setIsNew(1);
        }
        return resultsRepo.saveAndFlush(results);
    }

    public List<Results> findNewsByBussinessId(Integer businessId) {
        return resultsRepo.findNewByBusinessId(businessId);
    }

    public List<Results> findAllByBusinessId(Integer id) {
        return resultsRepo.findAllByBusinessId(id);
    }

    public void setOld(List<Results> results) {
        for (Results result : results) {
            result.setIsNew(0);
            saveResults(result);
        }
    }
}
